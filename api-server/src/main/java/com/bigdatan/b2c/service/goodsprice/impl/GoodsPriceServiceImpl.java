package com.bigdatan.b2c.service.goodsprice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.goodsprice.GoodsPriceMapper;
import com.bigdatan.b2c.entity.goodsprice.GoodsPrice;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.goodsprice.IGoodsPriceService;

/**
 *
 * 商品规格服务实现类
 */
@Service
public class GoodsPriceServiceImpl extends BaseServiceImpl<GoodsPrice>
		implements IGoodsPriceService {
	@Resource
	private GoodsPriceMapper goodsPriceMapper;

	@Override
	public GoodsPrice findGoodsPriceByPriceIdAndState(int priceId, Byte state) {
		return goodsPriceMapper.findGoodsPriceByPriceIdAndState(priceId, state);
	}

	@Override
	public List<GoodsPrice> findAllByGoodsIdAndNotState(int goodsId,
			Byte notState) {
		return goodsPriceMapper.findAllByGoodsIdAndNotState(goodsId, notState);
	}

	@Override
	public List<GoodsPrice> findAllNormalGoodsPriceByGoodsId(int goodsId) {
		return goodsPriceMapper.findAllByGoodsIdAndNotState(goodsId, (byte) 2);
	}

	@Override
	public GoodsPrice findNormalGoodsPriceByPriceId(int priceId) {
		return goodsPriceMapper.findGoodsPriceByPriceIdAndState(priceId,
				(byte) 1);
	}

	@Override
	protected IBaseDao<GoodsPrice> getMapper() {
		return goodsPriceMapper;
	}

}
