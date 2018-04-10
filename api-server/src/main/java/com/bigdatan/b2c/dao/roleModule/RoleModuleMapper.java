package com.bigdatan.b2c.dao.roleModule;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.roleModule.RoleModule;

/**
 * 管理员角色模块Mapper
 */
public interface RoleModuleMapper extends IBaseDao<RoleModule> {
	/**
	 * 删除所有roleId的记录
	 * 
	 * @param roleId
	 */
	void deleteByRoleModuleId(int roleModuleId);
	/**
	 * 根据roleId查询记录
	 * @param roleId
	 */
	List<RoleModule>  selectByRoleId(int roleId);
}
