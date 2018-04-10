package com.bigdatan.b2c.controller.admin.goodsprice;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JsonResponse;
import util.SessionUtil;
import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.entity.goodsprice.GoodsPrice;
import com.bigdatan.b2c.service.goodsprice.IGoodsPriceService;
import constant.SystemCode;


@Controller
@RequestMapping("/admin/goodsPrice/goodsPriceAdmin")
public class GoodsPriceAdminController extends AbstractController {

	@Resource
	private IGoodsPriceService goodsPriceService;

	/**
	 * 添加商品规格
	 */
	@ResponseBody
	@RequestMapping("/addGoodsPrice")
	public JsonResponse<GoodsPrice> addGoodsPrice(GoodsPrice goodsPrice,
			HttpServletRequest request) {
		JsonResponse<GoodsPrice> result = new JsonResponse<GoodsPrice>();
		Admin admin = SessionUtil.getAdminUser(request);
		goodsPrice.setAdminId(admin.getAdminId());
		goodsPrice.setCreateTime(new Date());
		goodsPrice.setUpdateTime(new Date());
		goodsPrice.setState((byte) 1);
		try {
			goodsPriceService.insertSelective(goodsPrice);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",新增商品规格异常]",
					e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 编辑商品
	 */
	@ResponseBody
	@RequestMapping("/editGoodsPrice")
	public JsonResponse<GoodsPrice> editGoodsPrice(GoodsPrice goodsPrice,
			HttpServletRequest request) {
		JsonResponse<GoodsPrice> result = new JsonResponse<GoodsPrice>();
		// String src=request.getParameter("image");
		Admin admin = SessionUtil.getAdminUser(request);
		goodsPrice.setAdminId(admin.getAdminId());
		goodsPrice.setUpdateTime(new Date());
		// System.out.println(src);
		try {
			goodsPriceService.updateByPrimaryKeySelective(goodsPrice);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",编辑商品规格异常]",
					e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 查找商品
	 */
	@ResponseBody
	@RequestMapping("/getGoodsPriceById")
	public JsonResponse<GoodsPrice> getGoodsPriceById(Integer priceId,
			HttpServletRequest request) {
		JsonResponse<GoodsPrice> result = new JsonResponse<GoodsPrice>();
		GoodsPrice goodsPrice = goodsPriceService.selectByPrimaryKey(priceId);
		if (goodsPrice != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(goodsPrice);
		}
		return result;
	}

	/**
	 * 查找商品规格，已启用的
	 */
	@ResponseBody
	@RequestMapping("/getGoodsPriceListByGoodsId")
	public JsonResponse<List<GoodsPrice>> getGoodsPriceByGoodsId(Integer goodsId,
			HttpServletRequest request) {
		JsonResponse<List<GoodsPrice>> result = new JsonResponse<List<GoodsPrice>>();
		List<GoodsPrice> goodsPrice = goodsPriceService.findAllByGoodsIdAndNotState(goodsId,(byte)2);
		if (goodsPrice != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(goodsPrice);
		}
		return result;
	}
}
