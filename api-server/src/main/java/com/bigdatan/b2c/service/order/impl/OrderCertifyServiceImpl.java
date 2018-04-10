package com.bigdatan.b2c.service.order.impl;

import javax.annotation.Resource;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.order.OrderCertifyMapper;
import com.bigdatan.b2c.entity.order.OrderCertify;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.order.IOrderCertifyService;

/**
 * 订单收款凭证服务实现类
 */
public class OrderCertifyServiceImpl extends BaseServiceImpl<OrderCertify> implements IOrderCertifyService {

	@Resource
	private OrderCertifyMapper orderCertifyMapper;

	@Override
	protected IBaseDao<OrderCertify> getMapper() {
		return orderCertifyMapper;
	}

}
