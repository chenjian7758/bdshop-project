package com.bigdatan.b2c.dao.order;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.order.JoinOrder;

public interface JoinOrderMapper extends IBaseDao<JoinOrder> {
	JoinOrder selectByJoinOrderNumber(JoinOrder joinOrder);
}