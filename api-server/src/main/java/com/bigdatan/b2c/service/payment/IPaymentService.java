package com.bigdatan.b2c.service.payment;

import java.util.List;

import com.bigdatan.b2c.entity.payment.Payment;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.base.IBaseService;

/**
 * 付款方式接口定义
 */
public interface IPaymentService extends IBaseService<Payment> {
	
	/**查询用户所拥有的付款方式，包括通用的和拥有的高级付款方式
	 * @param user 用户对象
	 * @return
	 */
	List<Payment> queryUserPayment(User user);
	
}
