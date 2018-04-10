package com.bigdatan.b2c.service.storegoods.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.storegoods.StoreGoodsMapper;
import com.bigdatan.b2c.entity.storegoods.StoreGoods;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.storegoods.IStoreGoodsService;

@Service
public class StoreGoodsServiceImpl extends BaseServiceImpl<StoreGoods>
		implements IStoreGoodsService {

	@Resource
	private StoreGoodsMapper storeGoodsMapper;

	@Override
	protected IBaseDao<StoreGoods> getMapper() {
		return storeGoodsMapper;
	}

	@Override
	public List<StoreGoods> queryAllStoreGoodsByUserId(StoreGoods storeGoods) {
		return storeGoodsMapper.queryAllStoreGoodsByUserId(storeGoods);
	}

	@Override
	public StoreGoods queryStoreGoodsByUserIdAndGoodsId(StoreGoods storeGoods) {
		return storeGoodsMapper.queryStoreGoodsByUserIdAndGoodsId(storeGoods);
	}

}
