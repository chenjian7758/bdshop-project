package com.bigdatan.b2c.service.logistics.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.logistics.LogisticsMapper;
import com.bigdatan.b2c.entity.logistics.Logistics;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.logistics.ILogisticsService;

@Service
public class LogisticsServiceImpl extends BaseServiceImpl<Logistics> implements ILogisticsService {

	@Resource LogisticsMapper logisticsMapper;
	@Override
	protected IBaseDao<Logistics> getMapper() {
		return logisticsMapper;
	}
	@Override
	public List<Logistics> getListLogistics() {
		return logisticsMapper.getListLogistics();
	}

}
