package com.bigdatan.b2c.dao.module;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.module.Module;
/**
 * 系统模块Mapper

 */
public interface ModuleMapper extends IBaseDao<Module> {
	/**
	 * 查询该角色拥有的系统模块
	 */
	List<Module> selectByRoleId(int roleId);

	List<Module> queryModulesByAdminId(int adminId);
}
