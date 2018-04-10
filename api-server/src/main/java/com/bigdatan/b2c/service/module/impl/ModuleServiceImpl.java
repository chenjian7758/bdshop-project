package com.bigdatan.b2c.service.module.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.module.ModuleMapper;
import com.bigdatan.b2c.entity.module.Module;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.module.IModuleService;

/**
 * 系统模块服务接口实现
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module> implements IModuleService {
	@Resource
	private ModuleMapper moduleMapper;

	@Override
	protected IBaseDao<Module> getMapper() {
		return this.moduleMapper;
	}

	@Override
	public List<Module> queryAllValidModule() {
		Module module = new Module();
		module.setState((byte) 1);
		return moduleMapper.getAllBySelect(module);
	}

	@Override
	public List<Module> queryRoleModule(int roleId) {
		return moduleMapper.selectByRoleId(roleId);

	}

	@Override
	public List<Module> queryModulesByAdminId(int adminId) {
		return moduleMapper.queryModulesByAdminId(adminId);
	}

}
