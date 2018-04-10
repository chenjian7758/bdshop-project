package com.bigdatan.b2c.service.shortMsg;

import javax.servlet.http.HttpServletRequest;

import util.JsonResponse;

import com.bigdatan.b2c.entity.shortMsg.ShortMessageModel;
import com.bigdatan.b2c.service.base.IBaseService;

public interface IShortMessageService extends IBaseService<ShortMessageModel> {
	public ShortMessageModel getCodeByPhone(String phone);
	
	public JsonResponse<String> sendMessage(HttpServletRequest request,String phone);
	public JsonResponse<String> checkCode(String phone,Integer code);
}