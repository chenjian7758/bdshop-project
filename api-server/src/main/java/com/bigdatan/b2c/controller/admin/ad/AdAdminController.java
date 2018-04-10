package com.bigdatan.b2c.controller.admin.ad;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.PageResult;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.ad.Ad;
import com.bigdatan.b2c.entity.adPosition.AdPosition;
import com.bigdatan.b2c.service.ad.IAdService;
import com.bigdatan.b2c.service.adPosition.IAdPositionService;

import constant.SystemCode;

/**
 * 广告Controller
 *
 */
@Controller
@RequestMapping("/admin/ad/ad")
public class AdAdminController extends AbstractController {
	@Resource
	private IAdPositionService adPositionService;
	@Resource
	private IAdService adService;

	@ResponseBody
	@RequestMapping("/getAdPositionByPage")
	public JsonResponse<PageResult<AdPosition>> getAdPositionByPage(PageResult<AdPosition> page, AdPosition adPosition,
			HttpServletRequest request) {
		JsonResponse<PageResult<AdPosition>> result = new JsonResponse<PageResult<AdPosition>>();
		adPositionService.queryByPage(page, adPosition);
		if (page.getTotal() != 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(page);
		} else {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getAdPositionById")
	public JsonResponse<AdPosition> getAdPositionById(Integer adPositionId,HttpServletRequest request) {
		JsonResponse<AdPosition> result = new JsonResponse<AdPosition>();
		AdPosition adposition=adPositionService.selectByPrimaryKey(adPositionId);
		if (adposition != null) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(adposition);
		} 
		return result;
	}

	@ResponseBody
	@RequestMapping("/editAdPosition")
	public JsonResponse<AdPosition> editAdPosition(AdPosition adPosition, HttpServletRequest request) {
		JsonResponse<AdPosition> result = new JsonResponse<AdPosition>();
		try {
			adPositionService.updateByPrimaryKeySelective(adPosition);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[用户信息编辑失败]", e);
			result.setRes(SystemCode.FAILURE);
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/getAdByPage")
	public JsonResponse<PageResult<Ad>> getAdByPage(PageResult<Ad> page, Ad ad, HttpServletRequest request) {
		JsonResponse<PageResult<Ad>> result = new JsonResponse<PageResult<Ad>>();
		adService.queryByPage(page, ad);
		if (page.getTotal() != 0) {
			result.setRes(SystemCode.SUCCESS);
			result.setObj(page);
		} else {
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/queryAdByAdId")
	public JsonResponse<Ad> queryAdByAdId(Integer adId, HttpServletRequest request) {
		JsonResponse<Ad> result = new JsonResponse<Ad>();
		try {
			Ad ad = adService.selectByPrimaryKey(adId);
			result.setRes(SystemCode.SUCCESS);
			result.setObj(ad);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/editAd")
	public JsonResponse<Ad> editAd(Ad ad, HttpServletRequest request) {
		JsonResponse<Ad> result = new JsonResponse<Ad>();
		try {
			Date now = new Date();
			ad.setUpdateTime(now);
			adService.updateByPrimaryKeySelective(ad);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addAd")
	public JsonResponse<Ad> addAd(Ad ad, HttpServletRequest request) {
		JsonResponse<Ad> result = new JsonResponse<Ad>();
		try {
			Date now = new Date();
			ad.setCreateTime(now);
			ad.setUpdateTime(now);
//			ad.setDelState((byte) 2);
			adService.insertSelective(ad);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delAd")
	public JsonResponse<Ad> delAd(Ad ad, HttpServletRequest request) {
		JsonResponse<Ad> result = new JsonResponse<Ad>();
		try {
			adService.delete(ad);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
		}
		
		return result;
	}
}
