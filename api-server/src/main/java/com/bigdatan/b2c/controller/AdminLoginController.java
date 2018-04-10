package com.bigdatan.b2c.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.SessionUtil;
import util.Encryp.MD5Util;

import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.service.admin.IAdminService;

import constant.SystemCode;

/**
 *
 *
 *  后台用户登陆退出
 *
 */
@Controller
@RequestMapping("/adminLogin")
public class AdminLoginController {
	
	@Resource
	private IAdminService adminService;
	
	
	/**
	 * @param request
	 * @param admin 
	 *  登录
	 * @return adminName,password
	 */
	@ResponseBody
	@RequestMapping("/toLogin")
	private JsonResponse<Admin> toLogin(HttpServletRequest request,Admin admin) {
		JsonResponse<Admin> result = new JsonResponse<Admin>(SystemCode.FAILURE);
		Admin model = adminService.login(admin);
		//首先判断用户状态是否为关闭
		if(model.getState() == 2) {
			return result;
		}
		if (model != null) {
			// 登录成功
			String password = MD5Util.encoder(admin.getPassword(),model.getRand());
			if (password.equals(model.getPassword())) {
				
				SessionUtil.setAdminUser(request, model);
				// 登陆成功
				result.setRes(SystemCode.SUCCESS);
				result.setObj(model);
				result.setResult(SystemCode.GetErrorDesc(SystemCode.SUCCESS));
				return result;
			}
		}
		// 登录失败
		result.setRes(SystemCode.NO_OBJ_ERROR_PASS);
		result.setResult(SystemCode.GetErrorDesc(SystemCode.NO_OBJ_ERROR_PASS));
		return result;
	}

	@ResponseBody
	@RequestMapping("/loginOut")
	public JsonResponse<String> loginOut(HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		request.getSession().invalidate();
		result.setRes(SystemCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/isLogin")
	public JsonResponse<String> isLogin(HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>(SystemCode.FAILURE);
		Admin admin = SessionUtil.getAdminUser(request);
		if (admin != null) {
			result.setRes(SystemCode.SUCCESS);
		}
		return result;
	}

}
