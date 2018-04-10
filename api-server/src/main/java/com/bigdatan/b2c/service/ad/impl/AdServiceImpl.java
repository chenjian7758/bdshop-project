package com.bigdatan.b2c.service.ad.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.ad.AdMapper;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.ad.Ad;
import com.bigdatan.b2c.service.ad.IAdService;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;

/**
 * 广告服务接口实现
 */
@Service
public class AdServiceImpl extends BaseServiceImpl<Ad> implements IAdService {
	@Resource
	private AdMapper adMapper;

	@Override
	protected IBaseDao<Ad> getMapper() {
		return this.adMapper;
	}
}
