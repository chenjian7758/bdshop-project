package com.bigdatan.b2c.service.payment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.payment.PaymentMapper;
import com.bigdatan.b2c.entity.payment.Payment;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.payment.IPaymentService;

/**
 * 付款方式接口实现类
 */
@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment> implements IPaymentService {
	@Resource
	private PaymentMapper paymentMapper;

	@Override
	protected IBaseDao<Payment> getMapper() {
		return this.paymentMapper;
	}

	@Override
	public List<Payment> queryUserPayment(User user) {
		return paymentMapper.queryUserPayment(user.getUserId());
	}
}
