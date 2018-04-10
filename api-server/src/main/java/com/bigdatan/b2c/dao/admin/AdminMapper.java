package com.bigdatan.b2c.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.admin.Admin;

public interface AdminMapper extends IBaseDao<Admin> {
	public Admin login(Admin admin);
	public int forgetPassword(Admin admin);
	public int getCountByAdminName(String name);
	//测试导出数据到excel
	public List<Admin> getAdminByCondition(@Param("state")Byte state);
}