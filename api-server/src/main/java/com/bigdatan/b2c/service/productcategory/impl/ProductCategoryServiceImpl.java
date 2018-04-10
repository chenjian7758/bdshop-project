package com.bigdatan.b2c.service.productcategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.productcategory.ProductCategoryMapper;
import com.bigdatan.b2c.entity.productcategory.ProductCategory;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.productcategory.IProductCategoryService;

@Service
public class ProductCategoryServiceImpl extends
		BaseServiceImpl<ProductCategory> implements IProductCategoryService {

	@Resource
	private ProductCategoryMapper productCategoryMapper;

	@Override
	protected IBaseDao<ProductCategory> getMapper() {
		return productCategoryMapper;
	}

	@Override
	public List<ProductCategory> getAllChildCategoryByCategoryId(int categoryId) {
		List<ProductCategory> result=productCategoryMapper.getChildCategoryByCategoryId(categoryId, 3);
		return result;
	}

	@Override
	public List<ProductCategory> getAllNormalChildCategoryByCategoryId(
			int categoryId) {
		List<ProductCategory> result=productCategoryMapper.getChildCategoryByCategoryIdAndState(categoryId, 1);
		return result;
	}

	@Override
	public ProductCategory getParentCategoryByCategoryId(int categoryId) {
		ProductCategory result=productCategoryMapper.selectByPrimaryKey(categoryId);
		return result;
	}

	@Override
	public List<ProductCategory> getAllFirstLevelCategory() {
		List<ProductCategory> result=productCategoryMapper.getFirstLevelCategory(3);
		return result;
	}

	@Override
	public List<ProductCategory> getAllNormalFirstLevelCategory() {
		List<ProductCategory> result=productCategoryMapper.getFirstLevelCategoryByState(1);
		return result;
	}

	@Override
	public List<ProductCategory> getHomePageCategory() {
		List<ProductCategory> result=productCategoryMapper.getHomePageCategory();
		return result;
	}

	
}
