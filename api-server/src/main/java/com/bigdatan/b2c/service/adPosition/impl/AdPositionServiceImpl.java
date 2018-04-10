package com.bigdatan.b2c.service.adPosition.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.adPosition.AdPositionMapper;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.adPosition.AdPosition;
import com.bigdatan.b2c.service.adPosition.IAdPositionService;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;

/**
 * 广告位置服务接口实现
 *
 */
@Service
public class AdPositionServiceImpl extends BaseServiceImpl<AdPosition> implements IAdPositionService {
	@Resource
	private AdPositionMapper adPositionMapper;
	
	@Override
	protected IBaseDao<AdPosition> getMapper() {
		return this.adPositionMapper;
	}

}
