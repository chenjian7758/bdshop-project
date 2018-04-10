package com.bigdatan.b2c.controller.front.message;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.PageResult;
import util.SessionUtil;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.message.Message;
import com.bigdatan.b2c.entity.message.UserMessage;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.message.IMessageService;
import com.bigdatan.b2c.service.message.IUserMessageService;

import constant.SystemCode;

/**
 * 
 *
 *
 *  前端消息模块
 */
@Controller
@RequestMapping("/front/message/message")
public class MessageController extends AbstractController {
	@Resource
	private IMessageService messageService;
	@Resource
	private IUserMessageService userMessageService;

	/**************************** 消息实体 **********************************/
	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @param userMessageId
	 *            消息记录id
	 * @return 获取消息详情
	 */
	@ResponseBody
	@RequestMapping("/getMessage")
	public JsonResponse<Message> getMessage(Integer messageId) {
		JsonResponse<Message> result = new JsonResponse<Message>(SystemCode.FAILURE);
		Message model = messageService.selectByPrimaryKey(messageId);
		if (model != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(model);
		}
		return result;
	}

	/**
	 * 
	 * @param messageId
	 *            消息id
	 * @param userMessageId
	 *            消息记录id
	 * @return 获取消息详情 并将消息记录置为已读
	 */
	@ResponseBody
	@RequestMapping("/getMessageAndRead")
	public JsonResponse<Message> getMessageAndRead(Integer messageId, Integer userMessageId, HttpServletRequest request) {
		JsonResponse<Message> result = new JsonResponse<Message>(SystemCode.FAILURE);
		Message model = messageService.selectByPrimaryKey(messageId);
		if (model == null && model.getDelState() != 2) {
			result.setRes(SystemCode.OBJ_NOT_EXISTS);
			return result;
		}
		UserMessage userMessageFromDB = userMessageService.getOneById(userMessageId);
		UserMessage userMessage = new UserMessage();
		userMessage.setUserMessageId(userMessageId);
		userMessage.setState((byte) 1);
		userMessage.setUpdateTime(new Date());
		try {
			if (userMessageFromDB.getState() != 1) {
				userMessageService.updateByPrimaryKeySelective(userMessage);
			}
		} catch (Exception e) {
			logError(request, "[消息记录置为已读发生异常]", e);
		}
		model = messageService.selectByPrimaryKey(messageId);
		if (model != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(model);
		}
		return result;
	}

	/**
	 * 
	 * @return 获取最新5条消息
	 */
	@ResponseBody
	@RequestMapping("/getMessageLevel")
	public JsonResponse<Message> getMessageLevel(HttpServletRequest request) {
		JsonResponse<Message> result = new JsonResponse<Message>(SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		if(null == user){
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		}
		List<Message> messageList = messageService.getMessageLevel(user.getUserId());
		if (messageList != null && messageList.size() > 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setList(messageList);
		}
		return result;
	}

	/**
	 * 
	 * @return 获取消息 列表分页 state 1 已读 2 未读
	 */
	@ResponseBody
	@RequestMapping("/getMessageByPage")
	public JsonResponse<PageResult<Message>> getMessageByPage(PageResult<Message> page, Message Message,
			HttpServletRequest request) {
		JsonResponse<PageResult<Message>> result = new JsonResponse<PageResult<Message>>(SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		Message.setUserId(user.getUserId());
		messageService.queryByPageFront(page, Message);
		if (page.getTotal() != 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(page);
		}
		return result;
	}

	/************************* 消息记录 ********************************************/

	/**
	 * 获取 当前用户未读消息数
	 * 
	 * @param userId
	 *            前端用户id
	 */
	@ResponseBody
	@RequestMapping("/getNoReadCount")
	public JsonResponse<Integer> getNoReadCount(HttpServletRequest request) {
		JsonResponse<Integer> result = new JsonResponse<Integer>();
		User user = SessionUtil.getUser(request);
		int count = userMessageService.getNoReadCount(user.getUserId());
		result.setRes(SystemCode.SUCCESS);
		result.setObj(count);
		return result;
	}

	/**
	 * 获取 当前用户消息数
	 * 
	 * @param userId
	 *            前端用户id
	 */
	@ResponseBody
	@RequestMapping("/getMessageCount")
	public JsonResponse<Integer> getMessageCount(HttpServletRequest request) {
		JsonResponse<Integer> result = new JsonResponse<Integer>();
		User user = SessionUtil.getUser(request);
		List<Integer> count = userMessageService.getMessageCount(user.getUserId());
		result.setRes(SystemCode.SUCCESS);
		result.setList(count);
		return result;
	}
	/**
	 * 
	 * @param userMessageId
	 *            消息记录id
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/toReadMessage") public JsonResponse<String>
	 * toReadMessage(HttpServletRequest request,UserMessage userMessage){
	 * JsonResponse<String> result=new JsonResponse<String>();
	 * userMessage.setState((byte)1); userMessage.setUpdateTime(new Date()); try
	 * { userMessageService.update(userMessage);
	 * result.setRes(SystemCode.SUCCESS); } catch (Exception e) {
	 * logError(request,"[更改消息记录状态为已读 法发生异常]", e);
	 * result.setRes(SystemCode.FAILURE); } return result; }
	 */

}
