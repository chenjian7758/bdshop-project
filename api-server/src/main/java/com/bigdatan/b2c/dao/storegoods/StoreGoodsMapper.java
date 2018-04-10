package com.bigdatan.b2c.dao.storegoods;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.storegoods.StoreGoods;
/**
 * 收藏dao
 */
public interface StoreGoodsMapper extends IBaseDao<StoreGoods> {
	/**
	 * 依据用户id，查询用户所有收藏的条目
	 * @param storeGoods
	 * @return
	 */
	public List<StoreGoods> queryAllStoreGoodsByUserId(StoreGoods storeGoods);
	
	/**
	 * 依据用户id和商品id，查询收藏条目
	 * @param storeGoods
	 * @return
	 */
	public StoreGoods queryStoreGoodsByUserIdAndGoodsId(StoreGoods storeGoods);
}