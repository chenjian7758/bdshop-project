package com.bigdatan.b2c.dao.receive;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.receive.Receive;

public interface ReceiveMapper extends IBaseDao<Receive> {
	public Receive selectUserReceive(String openid);
	
	public Receive selectDefaultReceive(int userId);
	public List<Receive> selectReceive(int userId);
}