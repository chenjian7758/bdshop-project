package com.bigdatan.b2c.service.message.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.message.IMessageMapper;
import com.bigdatan.b2c.entity.message.Message;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.message.IMessageService;
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements IMessageService{
	@Resource
	private IMessageMapper messageMapper;
	@Override
	protected IBaseDao<Message> getMapper() {
		return messageMapper;
	}
	@Override
	public List<Message> getMessageLevel(Integer userId) {
		return messageMapper.getMessageLevel(userId);
	}

}
