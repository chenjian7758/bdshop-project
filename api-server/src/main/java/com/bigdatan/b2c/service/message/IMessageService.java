package com.bigdatan.b2c.service.message;

import java.util.List;

import com.bigdatan.b2c.entity.message.Message;
import com.bigdatan.b2c.service.base.IBaseService;


public interface IMessageService extends IBaseService<Message>{

	/**
	 * @param limit 获取最新limit条数据
	 * @param integer 
	 * @return 获取最新动态的消息
	 */
	public List<Message> getMessageLevel(Integer integer);
}
