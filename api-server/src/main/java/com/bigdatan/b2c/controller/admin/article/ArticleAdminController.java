package com.bigdatan.b2c.controller.admin.article;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.PageResult;
import util.SessionUtil;
import util.Encryp.MD5Util;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.entity.article.Article;
import com.bigdatan.b2c.entity.goods.Goods;
import com.bigdatan.b2c.service.article.IArticleService;

import constant.SystemCode;

/**
 */
@Controller
@RequestMapping("/admin/article/articleAdmin")
public class ArticleAdminController extends AbstractController {
	@Resource
	private IArticleService articleService;

	/**
	 * 文章列表
	 */
	/**
	 * @param page
	 * @param article
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getArticleByPage")
	public JsonResponse<PageResult<Article>> getArticleByPage(PageResult<Article> page, Article article,
			HttpServletRequest request) {
		System.out.println("page:"+page.getTotal());
		JsonResponse<PageResult<Article>> result = new JsonResponse<PageResult<Article>>();
		articleService.queryByPage(page, article);
		if (page.getTotal() != 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(page);
		} else {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}
	/**
	 * 删除文章
	 */
	/**
	 * @param article
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delArticleById")
	public JsonResponse<Article> delArticleById(Article article,
			HttpServletRequest request) {
		JsonResponse<Article> result = new JsonResponse<Article>();
		Admin admin = SessionUtil.getAdminUser(request);
		article.setAdminId(admin.getAdminId());
		article.setDelState((byte) 1);
		article.setUpdateTime(new Date());
		try {
			articleService.updateByPrimaryKeySelective(article);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",删除文章异常]", e);
			result.setRes(SystemCode.FAILURE);
		}

		return result;
	}

	/**
	 * 文章详情
	 */
	/**
	 * @param articleId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getArticleById")
	public JsonResponse<Article> getArticleById(Integer articleId, HttpServletRequest request) {
		JsonResponse<Article> result = new JsonResponse<Article>();
		Article article = articleService.selectByPrimaryKey(articleId);
		if (article != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(article);
		}
		return result;
	}

	/**
	 * 编辑文章
	 */
	/**
	 * @param article
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editArticle")
	public JsonResponse<Goods> editArticle(Article article, HttpServletRequest request) {
		JsonResponse<Goods> result = new JsonResponse<Goods>();
		Admin admin = SessionUtil.getAdminUser(request);
		article.setAdminId(admin.getAdminId());
		article.setUpdateTime(new Date());
		try {
			articleService.editArticle(article);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logInfo(request, "[编辑文章异常]");
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 新建文章
	 */
	/**
	 * @param article
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addArticle")
	public JsonResponse<String> addArticle(Article article, HttpServletRequest request) {
		System.out.println(article);
		JsonResponse<String> result = new JsonResponse<String>();
		System.out.println("article:" + article);
		try {
			Admin admin = SessionUtil.getAdminUser(request);
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			article.setAdminId(admin.getAdminId());
			int count = articleService.writeArticle(article);
			if (count == 1) {
				result.setRes(SystemCode.SUCCESS);
			} else {
				logInfo(request, "[添加文章异常(database)]");
				result.setRes(SystemCode.FAILURE);
			}
		} catch (Exception e) {
			logError(request, "[添加文章异常(system)]", e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.getRand());
	}
}
