package com.bigdatan.b2c.controller.admin.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.PageResult;
import util.SessionUtil;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.dao.message.IUserMessageMapper;
import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.entity.message.Message;
import com.bigdatan.b2c.service.message.IMessageService;
import com.bigdatan.b2c.service.message.IUserMessageService;
import com.bigdatan.b2c.service.user.IUserService;

import constant.SystemCode;

/**
 *
 *
 *  后台消息管理
 */
@Controller
@RequestMapping("/admin/message/messageAdmin")
public class MessageAdminController extends AbstractController {
	@Resource
	private IMessageService messageService;
	@Resource
	private IUserMessageService userMessageService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserMessageMapper userMessageMapper;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 
	 * @param messageId
	 * @return 查看消息推送 记录 已经推送
	 */
	@ResponseBody
	@RequestMapping("getUserMessageRecord")
	public List<Map<String, Object>> getUserMessageRecord(int messageId) {
		List<Map<String, Object>> map = userMessageMapper.getUserListByMessageId(messageId);
		return map;
	}

	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @return 查询消息发送的用户列表 还未推送前
	 */
//	@ResponseBody
//	@RequestMapping("/getUserMessage")
//	public JsonResponse<User> getUserMessage(String userIds) {
//		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
//		result.setRes(SystemCode.SUCCESS);
//		result.setList(UserMapper.getAllUserInfo(userIds));
//		return result;
//	}

	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @return 查询消息详情
	 */
//	@ResponseBody
//	@RequestMapping("/getMessage")
//	public JsonResponse<Message> getMessage(Integer messageId) {
//		JsonResponse<Message> result = new JsonResponse<Message>(SystemCode.FAILURE);
//		Message model = messageService.getOneById(messageId);
//		if (model != null) {
//			result.setRes(SystemCode.SUCCESS);
//			result.setObj(model);
//		}
//		return result;
//	}

	/**
	 * 
	 * @param
	 * @return 查询消息列表 分页
	 */
	@ResponseBody
	@RequestMapping("/getMessageByPage")
	public JsonResponse<PageResult<Message>> getMessageByPage(PageResult<Message> page, Message messageModel,
			HttpServletRequest request) {
		JsonResponse<PageResult<Message>> result = new JsonResponse<PageResult<Message>>(SystemCode.FAILURE);
		// User user=SessionUtil.getAdminUser(request);
		/*
		 * if(user.getRoleType()==2){ //操作员
		 * messageModel.setUserId(user.getUserId()); }
		 */
		messageService.queryByPage(page, messageModel);
		if (page.getTotal() != 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(page);
		}
		return result;
	}
	
	/**
	 * 
	 * @param
	 * @return 编辑消息
	 */
	@ResponseBody
	@RequestMapping("/getMessageById")
	public JsonResponse<Message> getMessageById(Integer messageId, HttpServletRequest request) {
		JsonResponse<Message> result = new JsonResponse<Message>();
		Admin admin = SessionUtil.getAdminUser(request);
		try {
			Message msg = messageService.selectByPrimaryKey(messageId);
			result.setObj(msg);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",编辑消息异常]", e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 
	 * @param
	 * @return 添加消息 state 1所有用户 2指定用户
	 */
	@ResponseBody
	@RequestMapping("/addMessage")
	public JsonResponse<String> addMessage(final Message messageModel, HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		Admin admin = SessionUtil.getAdminUser(request);
		messageModel.setAdminId(admin.getAdminId());
		messageModel.setCreateTime(new Date());
		messageModel.setUpdateTime(new Date());
		String content = messageModel.getMessageContext();
		messageModel.setMessageContext(content);
		if (content != null && content.length() > 15) {
			messageModel.setMessageShortContext(content.substring(0, 15) + "...");
		} else {
			messageModel.setMessageShortContext(content);
		}
		try {
			if (messageModel.getIsAll() == 1) {
				messageModel.setUserIds(userService.getAllFrontUser());
			}
			if (messageModel.getPushNow() == 1) {
				// 立即推送
				messageModel.setPush((byte) 1);
				messageModel.setPushTime(new Date());
				messageService.insert(messageModel);
				// 另开一个线程 批量添加 用户-消息 记录
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						userMessageService.addMessageRecord(messageModel.getMessageId(), messageModel.getUserIds());
					}
				});
			} else {
				messageService.insert(messageModel);
			}
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",新增消息异常]", e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 
	 * @param
	 * @return 编辑消息
	 */
	@ResponseBody
	@RequestMapping("/editMessage")
	public JsonResponse<String> editMessage(final Message messageModel, HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		Admin admin = SessionUtil.getAdminUser(request);
		messageModel.setUpdateTime(new Date());
		String content = messageModel.getMessageContext();
		messageModel.setMessageContext(content);
		if (content != null && content.length() > 15) {
			messageModel.setMessageShortContext(content.substring(0, 15) + "...");
		}
		try {
			if (messageModel.getIsAll() == 1) {
				messageModel.setUserIds(userService.getAllFrontUser());
			}
			if (messageModel.getPushNow() == 1) {
				// 立即推送
				messageModel.setPush((byte) 1);
				messageModel.setPushTime(new Date());
				// 另开一个线程 批量添加 用户-消息 记录
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						userMessageService.addMessageRecord(messageModel.getMessageId(), messageModel.getUserIds());
					}
				});
			}
			messageService.updateByPrimaryKeySelective(messageModel);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",编辑消息异常]", e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @return 推送消息
	 */
	@ResponseBody
	@RequestMapping("/pushMessage")
	public JsonResponse<String> pushMessage(final Message messageModel, HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		Admin admin = SessionUtil.getAdminUser(request);
		messageModel.setUpdateTime(new Date());
		messageModel.setPushTime(new Date());
		messageModel.setPush((byte) 1);
		if (messageModel.getIsAll() == 1) {
			messageModel.setUserIds(userService.getAllFrontUser());
		}
		try {
			messageService.updateByPrimaryKeySelective(messageModel);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",编辑消息异常]", e);
		}
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				userMessageService.addMessageRecord(messageModel.getMessageId(), messageModel.getUserIds());
			}
		});
		result.setRes(SystemCode.SUCCESS);
		return result;
	}

	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @return 删除消息
	 */
	@ResponseBody
	@RequestMapping("/delMessage")
	public JsonResponse<String> delMessage(Integer messageId) {
		JsonResponse<String> result = new JsonResponse<String>();
		Message messageModel = new Message();
		messageModel.setUpdateTime(new Date());
		messageModel.setMessageId(messageId);
		messageModel.setDelState((byte) 1);
		try {
			messageService.updateByPrimaryKeySelective(messageModel);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}
	
	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @return 删除消息
	 */
	@ResponseBody
	@RequestMapping("/getMsgUser")
	public JsonResponse<String> getMsgUser(Integer messageId) {
		JsonResponse<String> result = new JsonResponse<String>();
		Message messageModel = new Message();
		messageModel.setUpdateTime(new Date());
		messageModel.setMessageId(messageId);
		messageModel.setDelState((byte) 1);
		try {
			messageService.updateByPrimaryKeySelective(messageModel);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}
}
