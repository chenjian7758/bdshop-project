package com.bigdatan.b2c.service.user;

import java.util.List;

import util.PageResult;

import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.entity.user.UserPayment;
import com.bigdatan.b2c.entity.user.UserPrivilege;
import com.bigdatan.b2c.entity.user.UserVo;
import com.bigdatan.b2c.service.base.IBaseService;

public interface IUserService extends IBaseService<User> {
	public User getOneByOpenid(String openid);

	public User getOneByPhone(String phone);

	public int updatePhone(String tel, String openid);

	public int getCountByPhone(String phone);

	void grantPrivilege(UserPrivilege userPrivilege, List<UserPayment> userPayments) throws Exception;

	String getAllFrontUser();

	PageResult<User> getUsersByUserIds(PageResult<User> page, String userIds);

	PageResult<User> queryUserByPage(PageResult<User> page, UserVo userVo);

}
