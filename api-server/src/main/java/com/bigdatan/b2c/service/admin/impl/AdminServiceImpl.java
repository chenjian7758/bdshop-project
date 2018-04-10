package com.bigdatan.b2c.service.admin.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;


import com.bigdatan.b2c.dao.admin.AdminMapper;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.service.admin.IAdminService;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements IAdminService {

	@Resource
	private AdminMapper adminMapper;

	@Override
	protected IBaseDao<Admin> getMapper() {
		return adminMapper;
	}

	@Override
	public Admin login(Admin user) {
		return adminMapper.login(user);
	}

	@Override
	public int forgetPassword(Admin user) {
		return adminMapper.forgetPassword(user);
	}

	@Override
	public int getCountByAdminName(String name) {
		return adminMapper.getCountByAdminName(name);
	}
	
}
