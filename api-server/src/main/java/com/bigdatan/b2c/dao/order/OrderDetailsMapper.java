package com.bigdatan.b2c.dao.order;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.order.OrderDetails;
import com.bigdatan.b2c.entity.order.OrderDetailsAdminSearchVO;

public interface OrderDetailsMapper extends IBaseDao<OrderDetails> {
	public Integer insertBatch(List<OrderDetails> list);
	
	/**
	 * 依据商品订单查询类，查询商品订单详细列表
	 * @param entity
	 * @return
	 */
	public List<OrderDetails> getPageByOrderDetailsAdminSearchVO(OrderDetailsAdminSearchVO entity);
	
	
	/**
	 * 依据商品订单查询类，查询商品订单详细报表
	 * @param entity
	 * @return
	 */
	public List<OrderDetails> getDetailReportByOrderDetailsAdminSearchVO(OrderDetailsAdminSearchVO entity);
	
	/**
	 * 依据商品订单查询类，获取商品销售总额
	 * @param entity
	 * @return
	 */
	public long getTotalDetailsAmountByOrderDetailsAdminSearchVO(OrderDetailsAdminSearchVO entity);
	
	/**
	 * 依据商品订单查询类，获取商品销售总数
	 * @param entity
	 * @return
	 */
	public long getTotalNumAmountByOrderDetailsAdminSearchVO(OrderDetailsAdminSearchVO entity);

}