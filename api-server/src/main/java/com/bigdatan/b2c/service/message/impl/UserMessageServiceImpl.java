package com.bigdatan.b2c.service.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.message.IUserMessageMapper;
import com.bigdatan.b2c.dao.user.UserMapper;
import com.bigdatan.b2c.entity.message.UserMessage;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.message.IUserMessageService;

@Service
public class UserMessageServiceImpl extends BaseServiceImpl<UserMessage> implements IUserMessageService{
@Resource
private IUserMessageMapper userMessageMapper;
@Resource
private UserMapper frontUserMapper;
	@Override
	protected IBaseDao<UserMessage> getMapper() {
		return userMessageMapper;
	}
	@Override
	public int addMessageRecord(Integer messageId,String userIds){
		//List<Integer> userIdList=usserDao.getAllFrontUser();
		String[] userIdList=userIds.split(",");
		List<UserMessage> list=new ArrayList<UserMessage>();
		for(String userId:userIdList){
			UserMessage userMessageModel=new UserMessage();
			userMessageModel.setUserId(Integer.valueOf(userId));
			userMessageModel.setMessageId(messageId);
			userMessageModel.setCreateTime(new Date());
			userMessageModel.setUpdateTime(new Date());
			list.add(userMessageModel);
		}
		return userMessageMapper.addMessageRecord(list);
	}
	@Override
	public int getNoReadCount(int userId) {
		return userMessageMapper.getNoReadCount(userId);
	}
	@Override
	public List<Integer> getMessageCount(int userId) {
		return userMessageMapper.getMessageCount(userId);
	}
	@Override
	public UserMessage getOneById(Integer userMessageId) {
		return userMessageMapper.getOneById(userMessageId);
	}
	
	
}
