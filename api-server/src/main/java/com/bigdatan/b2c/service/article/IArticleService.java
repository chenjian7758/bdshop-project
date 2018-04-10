package com.bigdatan.b2c.service.article;

import com.bigdatan.b2c.entity.article.Article;
import com.bigdatan.b2c.service.base.IBaseService;

public interface IArticleService  extends IBaseService<Article>{
	int writeArticle(Article article) throws Exception;
	int editArticle(Article article) throws Exception;
}
