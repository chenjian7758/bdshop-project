package com.bigdatan.b2c.dao.logistics;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.logistics.Logistics;

public interface LogisticsMapper extends IBaseDao<Logistics> {
	//搜索物流列表
	public List<Logistics> getListLogistics();
	public Logistics getFirstLogistics();
}