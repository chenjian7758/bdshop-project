package com.bigdatan.b2c.service.role;

import java.util.List;

import com.bigdatan.b2c.entity.role.Role;
import com.bigdatan.b2c.entity.roleModule.RoleModule;
import com.bigdatan.b2c.service.base.IBaseService;

/**
 * 管理员角色服务接口定义
 */
public interface IRoleService extends IBaseService<Role> {

	/**
	 * 授权
	 * 
	 * @param role
	 *            角色对象
	 * @param moduleList
	 *            模块列表
	 */
	void grantPowerToRole(Role role, List<RoleModule> moduleList) throws Exception;
}
