package com.bigdatan.b2c.dao.user;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.user.UserPrivilege;

/**
 * 用户优惠
 *
 */
public interface UserPrivilegeMapper extends IBaseDao<UserPrivilege> {
	/**
	 * 查询用户优惠
	 * 
	 * @param userId 用户id
	 * @return
	 */
	UserPrivilege selectByUserId(int userId);
	
	UserPrivilege selectAllByUserId(int userId);
}
