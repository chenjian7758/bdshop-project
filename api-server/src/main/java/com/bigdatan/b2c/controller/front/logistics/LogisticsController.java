package com.bigdatan.b2c.controller.front.logistics;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.logistics.Logistics;
import com.bigdatan.b2c.service.logistics.ILogisticsService;

import constant.SystemCode;

/**
 *
 *
 *
 *  配送管理模块  前端
 */
@Controller
@RequestMapping("/front/logistics/logistic")
public class LogisticsController extends AbstractController {
	
	@Resource
	private ILogisticsService logisticsService;
	
	/**
	 * @param request
	 * @return 查看配送列表
	 */
	@ResponseBody 
	@RequestMapping("/getListLogistics")
	public JsonResponse<Logistics> getListLogistics(HttpServletRequest request) {
		JsonResponse<Logistics> result = new JsonResponse<Logistics> (SystemCode.FAILURE);
		List<Logistics> listLogistics = logisticsService.getListLogistics();
		if(!listLogistics.isEmpty()) {
			result.setList(listLogistics);
			result.setRes(SystemCode.SUCCESS);
		}
		return result;
	}

}
