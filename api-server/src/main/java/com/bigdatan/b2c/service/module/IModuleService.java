package com.bigdatan.b2c.service.module;

import java.util.List;

import com.bigdatan.b2c.entity.module.Module;
import com.bigdatan.b2c.service.base.IBaseService;

/**
 * 系统模块服务接口定义
 */
public interface IModuleService extends IBaseService<Module> {
	/**
	 * 查询所有有效的系统模块
	 * 
	 * @return
	 */
	List<Module> queryAllValidModule();
	/**
	 * 查询该角色拥有的有效系统模块
	 */
	List<Module> queryRoleModule(int roleId);
	List<Module> queryModulesByAdminId(int adminId);
}
