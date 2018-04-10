package com.bigdatan.b2c.controller.front.payment;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.SessionUtil;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.payment.Payment;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.payment.IPaymentService;

import constant.SystemCode;

/**
 * 获取付款方式控制器
 *
 */
@Controller
@RequestMapping("/front/payment/payment")
public class PaymentController extends AbstractController {
	@Resource
	private IPaymentService paymentService;

	@ResponseBody
	@RequestMapping("/queryUserPayment")
	public JsonResponse<List<Payment>> queryUserPayment(HttpServletRequest request) {
		JsonResponse<List<Payment>> result = new JsonResponse<List<Payment>>(SystemCode.FAILURE);
		User user = SessionUtil.getUser(request);
		if(null == user){
			return result;
		} 
		List<Payment> paymentList = paymentService.queryUserPayment(user);
		if(null != paymentList){
			result.setRes(SystemCode.SUCCESS);
			result.setObj(paymentList);
		}
		return result;
	}
}
