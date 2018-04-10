package com.bigdatan.b2c.service.logistics;

import java.util.List;

import com.bigdatan.b2c.entity.logistics.Logistics;
import com.bigdatan.b2c.service.base.IBaseService;

public interface ILogisticsService extends IBaseService<Logistics>  {
	public List<Logistics> getListLogistics();
}
