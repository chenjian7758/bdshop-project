package com.bigdatan.b2c.dao.shortMsg;

import org.apache.ibatis.annotations.Param;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.shortMsg.ShortMessageModel;

public interface IShortMessageDao extends IBaseDao<ShortMessageModel> {
	public ShortMessageModel getCodeByPhone(@Param("phone")String phone);
}