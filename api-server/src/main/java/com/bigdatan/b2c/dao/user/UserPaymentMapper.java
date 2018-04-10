package com.bigdatan.b2c.dao.user;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.user.UserPayment;

/**
 * 用户优惠
 */
public interface UserPaymentMapper extends IBaseDao<UserPayment> {
	List<UserPayment> selectByUserId(int userId);
}
