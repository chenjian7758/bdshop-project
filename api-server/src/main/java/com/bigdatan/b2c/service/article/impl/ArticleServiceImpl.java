package com.bigdatan.b2c.service.article.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.article.ArticleMapper;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.article.Article;
import com.bigdatan.b2c.service.article.IArticleService;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements IArticleService {

	@Resource
	private ArticleMapper articleMapper;

	@Override
	protected IBaseDao<Article> getMapper() {
		return articleMapper;
	}

	@Override
	public int writeArticle(Article article) throws Exception {
		return articleMapper.insertSelective(article);
	}

	@Override
	public int editArticle(Article article) throws Exception {
		articleMapper.updateByPrimaryKey(article);
		return 0;
	}

}
