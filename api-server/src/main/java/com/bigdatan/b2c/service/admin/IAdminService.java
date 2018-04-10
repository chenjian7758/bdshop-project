package com.bigdatan.b2c.service.admin;

import com.bigdatan.b2c.entity.admin.Admin;
import com.bigdatan.b2c.service.base.IBaseService;

public interface IAdminService extends IBaseService<Admin>{
	public Admin login(Admin admin);
	public int forgetPassword(Admin admin);
	public int getCountByAdminName(String name);
}
