package com.bigdatan.b2c.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.PageResult;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.order.OrderDetailsMapper;
import com.bigdatan.b2c.entity.order.OrderDetails;
import com.bigdatan.b2c.entity.order.OrderDetailsAdminSearchVO;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.order.IOrderDetailsService;
import com.github.pagehelper.PageHelper;

/**
 *  TODO
 */
@Service
public class OrderDetailsServiceImpl extends BaseServiceImpl<OrderDetails> implements IOrderDetailsService{
	@Resource
	private OrderDetailsMapper orderDetailsMapper;
	@Override
	protected IBaseDao<OrderDetails> getMapper() {
		return orderDetailsMapper;
	}
	
	@Override
	public long getTotalDetailsAmountByOrderDetailsAdminSearchVO(
			OrderDetailsAdminSearchVO entity) {
		
		return (long)orderDetailsMapper.getTotalDetailsAmountByOrderDetailsAdminSearchVO(entity);
	}
	@Override
	public long getTotalNumAmountByOrderDetailsAdminSearchVO(
			OrderDetailsAdminSearchVO entity) {
		
		return (long)orderDetailsMapper.getTotalNumAmountByOrderDetailsAdminSearchVO(entity);
	}
	@Override
	public PageResult<OrderDetails> getPageByOrderDetailsAdminSearchVO(
			PageResult<OrderDetails> t, OrderDetailsAdminSearchVO entity) {
		int pageNo=t.getPageNo();
    	int pageSize=t.getPageSize();
		pageNo = pageNo == 0?1:pageNo;
		pageSize = pageSize == 0?10:pageSize;
		PageHelper.startPage(pageNo,pageSize); 
		return PageResult.toPageResult(orderDetailsMapper.getPageByOrderDetailsAdminSearchVO(entity),t);
	}

	@Override
	public List<OrderDetails> getDetailReportByOrderDetailsAdminSearchVO(
			OrderDetailsAdminSearchVO entity) {
		return orderDetailsMapper.getDetailReportByOrderDetailsAdminSearchVO(entity);
	}

}
