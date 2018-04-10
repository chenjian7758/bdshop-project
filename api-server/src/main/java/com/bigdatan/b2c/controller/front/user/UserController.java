package com.bigdatan.b2c.controller.front.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.shortMsg.IShortMessageService;
import com.bigdatan.b2c.service.user.IUserService;

import util.JsonResponse;
import util.SessionUtil;
import constant.SystemCode;

@Controller
@RequestMapping("/front/user/user")
public class UserController extends AbstractController {
	private Logger lo = Logger.getLogger(UserController.class);

	@Resource
	private IUserService userService;

	@Resource
	private IShortMessageService messageService;

	@ResponseBody
	@RequestMapping("phoneIsExit")
	public JsonResponse<String> phoneIsExit(String phone) {
		JsonResponse<String> result = new JsonResponse<String>();
		int count = userService.getCountByPhone(phone);
		/*
		 * if(count!=0){ //存在 result.setRes(SystemCode.PHONE_EXISTS); return
		 * result; }
		 */// by yucs
		result.setRes(SystemCode.PHONE_NOT_EXISTS);
		return result;
	}

	/**
	 * 
	 * @return 发送短息
	 * 
	 */
	@ResponseBody
	@RequestMapping("/sendCode")
	public JsonResponse<String> sendCode(String phone,
			HttpServletRequest request) {
		JsonResponse<String> result = messageService
				.sendMessage(request, phone);
		return result;
	}

	/**
	 * 检验短信验证码 成功保存手机号到当前用户
	 */
	@ResponseBody
	@RequestMapping("/checkCode")
	public JsonResponse<String> checkCode(String phone, Integer code,
			HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>(
				SystemCode.FAILURE);
		// 检验验证码
		result = messageService.checkCode(phone, code);
		try {
			if (result.getRes() == SystemCode.SUCCESS) {
				// by yucs
				if (SessionUtil.getUser(request) == null) {
					lo.info("用户为空...");
					User user = userService.getOneByPhone(phone);
					if (user == null) {
						User model = new User();
						model.setPhone(phone);
						model.setCreateTime(new Date());
						model.setUpdateTime(new Date());
						model.setState(1);
						userService.insertSelective(model);
						model = userService.getOneByPhone(phone);
						SessionUtil.setUser(request, model);
					} else {
						SessionUtil.setUser(request, user);
					}
					result.setRes(SystemCode.SUCCESS);
				} else {
					lo.info("用户不为空..."
							+ SessionUtil.getUser(request).getUserId());
					User user = userService.selectByPrimaryKey(SessionUtil
							.getUser(request).getUserId());
					user.setPhone(phone);
					userService.updateByPrimaryKeySelective(user);
					SessionUtil.setUser(request, user);
				}
				result.setRes(SystemCode.SUCCESS);
			}

		} catch (Exception e) {
			lo.error("更新用户失败", e);
			logError(request, "[修改用户手机失败]", e);
		}

		return result;
	}

	/**
	 * 会员详情
	 */
	@ResponseBody
	@RequestMapping("/checkUserPhone")
	public JsonResponse<User> checkUserPhone(Integer userId,
			HttpServletRequest request) {
		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
		lo.info("getUserById...");
		User user = SessionUtil.getUser(request);
		if (null == user) {
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		} else {
			if (StringUtils.isBlank(user.getPhone())) {
				lo.info("user.getPhone()为空或者null");
				result.setRes(SystemCode.NO_LOGIN);
				return result;
			} else {
				user = userService.selectByPrimaryKey(user.getUserId());

				if (user != null) {
					result.setRes(SystemCode.SUCCESS);
					result.setObj(user);
				}
			}
		}
		return result;
	}

	/**
	 * 会员详情
	 */
	@ResponseBody
	@RequestMapping("/getUserById")
	public JsonResponse<User> getUserById(Integer userId,
			HttpServletRequest request) {
		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
		lo.info("getUserById...");
		User user = SessionUtil.getUser(request);
		if (null == user) {
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		} else {
			user = userService.selectByPrimaryKey(user.getUserId());
			if (user != null) {
				result.setRes(SystemCode.SUCCESS);
				result.setObj(user);
			}
		}
		return result;
	}

	/**
	 * @param request
	 * @param admin
	 *            登录
	 * @return adminName,password
	 */
	@ResponseBody
	@RequestMapping("/toLogin")
	private JsonResponse<User> toLogin(HttpServletRequest request, User user) {
		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
		User model = userService.selectByPrimaryKey(user.getUserId());
		if (model != null) {
			// 登录成功
			SessionUtil.setUser(request, model);
			request.getSession().setAttribute("adaYangphone", model.getPhone());
			result.setRes(SystemCode.SUCCESS);

		}

		return result;
	}

	/**
	 * @param request
	 * @param admin
	 *            登录
	 * @return adminName,password
	 */
	@ResponseBody
	@RequestMapping("/test")
	public JsonResponse<String> test(HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>(
				SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		if (null == user) {
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		} else {
			result.setRes(SystemCode.SUCCESS);
		}
		return result;
	}

	/**
	 * @param request
	 * @param admin
	 *            退出
	 * @return adminName,password
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public JsonResponse<User> logout(HttpServletRequest request) {
		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		if (null != user) {
			request.getSession().invalidate();
		}
		result.setRes(SystemCode.SUCCESS);
		return result;
	}

	/**
	 * 更新用户信息
	 */
	@ResponseBody
	@RequestMapping("/updateUser")
	public JsonResponse<User> updateUser(HttpServletRequest request,
			String headimgurl, String nickname, String phone) {
		JsonResponse<User> result = new JsonResponse<User>(SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		if (null != headimgurl) {
			user.setHeadimgurl(headimgurl);
		}
		if (null != nickname) {
			user.setNickname(nickname);
		}
		if (null != phone) {
			user.setPhone(phone);
		}

		try {
			userService.updateByPrimaryKeySelective(user);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			lo.error("修改用户头像失败", e);
			logError(request, "[修改用户头像失败]", e);
		}

		return result;
	}

}
