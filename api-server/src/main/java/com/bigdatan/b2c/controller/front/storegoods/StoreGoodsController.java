package com.bigdatan.b2c.controller.front.storegoods;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.SessionUtil;
import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.storegoods.StoreGoods;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.storegoods.IStoreGoodsService;

import constant.SystemCode;

/**
 * 收藏controller
 */
@Controller
@RequestMapping("/front/storegoods/storegoods")
public class StoreGoodsController extends AbstractController {

	@Resource
	private IStoreGoodsService storeGoodsService;

	/**
	 * 收藏或取消收藏一个商品，用state标志操作类型，1为收藏，其余为取消收藏
	 * 
	 * @param storeGoods
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrSubStoreGoods")
	public JsonResponse<StoreGoods> queryShoppingCartInfo(
			StoreGoods storeGoods, HttpServletRequest request) {
		JsonResponse<StoreGoods> result = new JsonResponse<StoreGoods>();
		User user = SessionUtil.getUser(request);
		if (null == user) {
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		}
		if (storeGoods == null) {
			result.setRes(SystemCode.FAILURE);
			return result;
		}
		try {
			storeGoods.setUserId(user.getUserId());
			if (storeGoods.getState() == 1) {
				// 收藏
				StoreGoods temp = storeGoodsService
						.queryStoreGoodsByUserIdAndGoodsId(storeGoods);
				if (temp == null) {
					// 没有收藏过
					storeGoods.setCreateTime(new Date());
					storeGoods.setUpdateTime(new Date());
					storeGoods.setState(1);
					storeGoodsService.insertSelective(storeGoods);
				} else {
					// 已经收藏过
					temp.setUpdateTime(new Date());
					storeGoodsService.updateByPrimaryKeySelective(temp);
				}
			} else {
				// 取消收藏
				storeGoodsService.delete(storeGoods);
			}
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception ex) {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

}
