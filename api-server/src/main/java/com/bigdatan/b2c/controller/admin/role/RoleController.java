package com.bigdatan.b2c.controller.admin.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonResponse;
import util.PageResult;
import util.SessionUtil;

import com.bigdatan.b2c.controller.AbstractController;
import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.entity.module.Module;
import com.bigdatan.b2c.entity.role.Role;
import com.bigdatan.b2c.entity.role.RoleVo;
import com.bigdatan.b2c.entity.roleModule.RoleModule;
import com.bigdatan.b2c.service.admin.IAdminService;
import com.bigdatan.b2c.service.module.IModuleService;
import com.bigdatan.b2c.service.role.IRoleService;
import constant.SystemCode;

/**
 * 管理员角色Controller
 */
@Controller
@RequestMapping("/admin/role/role")
public class RoleController extends AbstractController {
	@Resource
	private IRoleService roleService;
	@Resource
	private IAdminService adminService;
	@Resource
	private IModuleService moduleService;

	@ResponseBody
	@RequestMapping("/queryRoleByPage")
	public JsonResponse<PageResult<RoleVo>> queryRoleByPage(PageResult<RoleVo> page, Role role,
			HttpServletRequest request) {
		JsonResponse<PageResult<RoleVo>> result = new JsonResponse<PageResult<RoleVo>>();
		Admin admin = SessionUtil.getAdminUser(request);
		if (null == admin) {
			result.setRes(SystemCode.NO_LOGIN);
			return result;
		}
		PageResult<Role> rolePage = new PageResult<Role>();
		rolePage.setPageNo(page.getPageNo());
		rolePage.setPages(page.getPages());
		rolePage.setPageSize(page.getPageSize());
		rolePage.setTotal(page.getTotal());

		List<RoleVo> roleVoList = new ArrayList<RoleVo>();
		RoleVo roleVo = null;
		int count = 0;

		rolePage = roleService.queryByPage(rolePage, role);
		List<Role> roleList = rolePage.getDataList();
		if (null != roleList) {
			for (Role tempRole : roleList) {
				// 查询该角色下的管理员数量
				Admin tempAdmin = new Admin();
				tempAdmin.setRole(tempRole);
				count = adminService.getCount(tempAdmin);

				roleVo = new RoleVo();
				roleVo.setCount(count);
				roleVo.setRoleId(tempRole.getRoleId());
				roleVo.setName(tempRole.getName());
				roleVo.setDescription(tempRole.getDescription());
				roleVo.setState(tempRole.getState());
				roleVo.setCreateTime(tempRole.getCreateTime());
				roleVo.setUpdateTime(tempRole.getUpdateTime());

				roleVoList.add(roleVo);
			}
		}
		page.setDataList(roleVoList);
		result.setObj(page);
		result.setRes(SystemCode.SUCCESS);

		return result;
	}

	@ResponseBody
	@RequestMapping("addRole")
	public JsonResponse<String> addRole(Role role, HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		Admin admin = SessionUtil.getAdminUser(request);
		try {
			Date now = new Date();
			role.setCreateTime(now);
			role.setUpdateTime(now);
			roleService.insertSelective(role);

			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[添加角色]", e);
			result.setRes(SystemCode.FAILURE);
		}
		return result;
	}

	/**
	 * 编辑角色
	 */
	@ResponseBody
	@RequestMapping("/editRole")
	public JsonResponse<Role> editAdmin(HttpServletRequest request, Role role) {
		JsonResponse<Role> result = new JsonResponse<Role>(SystemCode.FAILURE);
		try {
			roleService.updateByPrimaryKeySelective(role);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			logError(request, "[用户信息编辑失败]", e);
			result.setRes(SystemCode.OBJ_EXISTS);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delRoleById")
	public JsonResponse<Role> delRoleById(Role role, HttpServletRequest request) {
		JsonResponse<Role> result = new JsonResponse<Role>();
		Admin admin = SessionUtil.getAdminUser(request);
		try {
			Admin tempAdmin = new Admin();
			tempAdmin.setRole(role);
			tempAdmin.setDelState((byte) 2);
			// 查询是否具备删除角色的条件：没有具有该角色的管理员
			int count = adminService.getCount(tempAdmin);
			if (count <= 0) {
				roleService.delete(role);
				result.setRes(SystemCode.SUCCESS);
			} else {
				result.setRes(SystemCode.FAILURE);
				result.setMsg("存在该角色的管理员，不能删除");
			}
			
		} catch (Exception e) {
			logError(request, "[admin:" + admin.getAdminName() + ",删除角色异常]", e);
			result.setRes(SystemCode.FAILURE);
		}

		return result;
	}

	/**
	 * 
	 * @param request
	 * @return 获取角色信息
	 */
	@ResponseBody
	@RequestMapping("/getRoleInfo")
	public JsonResponse<Role> getRoleInfo(Integer roleId, HttpServletRequest request) {
		JsonResponse<Role> result = new JsonResponse<Role>();
		Role role = roleService.selectByPrimaryKey(roleId);
		result.setRes(SystemCode.SUCCESS);
		result.setObj(role);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryAllModule")
	public JsonResponse<List<Module>> queryAllModule(HttpServletRequest request) {
		JsonResponse<List<Module>> result = new JsonResponse<List<Module>>();
		List<Module> moduleList = moduleService.queryAllValidModule();
		result.setObj(moduleList);
		result.setRes(SystemCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/grantModules")
	public JsonResponse<String> grantModules(Integer roleId, String moduleIds, HttpServletRequest request) {
		JsonResponse<String> result = new JsonResponse<String>();
		Role roleFromDB = roleService.selectByPrimaryKey(roleId);
		if (null == roleFromDB || roleFromDB.getState() == 2) {
			result.setMsg("角色不存在或者已关闭");
			return result;
		}
		RoleModule module = null;
		List<RoleModule> modules = new ArrayList<RoleModule>();
		if (StringUtils.isNotBlank(moduleIds)) {
			int index = moduleIds.indexOf(",");
			if (index > 0) {
				String[] strs = moduleIds.split(",");
				for (String str : strs) {
					module = new RoleModule();
					int moduleId = Integer.parseInt(str);
					module.setModuleId(moduleId);
					module.setRoleId(roleId);
					modules.add(module);
				}
			} else {
				module = new RoleModule();
				int moduleId = Integer.parseInt(moduleIds);
				module.setModuleId(moduleId);
				module.setRoleId(roleId);
				modules.add(module);
			}
		}
		try {
			roleService.grantPowerToRole(roleFromDB, modules);
			result.setRes(SystemCode.SUCCESS);
		} catch (Exception e) {
			result.setRes(SystemCode.FAILURE);
			result.setMsg("添加失败");
		}
		return result;
	}
}
