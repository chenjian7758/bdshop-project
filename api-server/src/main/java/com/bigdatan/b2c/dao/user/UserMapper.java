package com.bigdatan.b2c.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.entity.user.UserVo;

public interface UserMapper extends IBaseDao<User>{
	public User getOneByOpenid(String openid);
	public User getOneByPhone(String phone);
	public int updatePhone(@Param("phone")String phone,@Param("openid")String openid);
	public int getCountByPhone(String phone);
	
	String getAllFrontUser();
	
	List<User> getUsersByPage(String userIds);
	List<User> queryUserByPage(UserVo entity);
}