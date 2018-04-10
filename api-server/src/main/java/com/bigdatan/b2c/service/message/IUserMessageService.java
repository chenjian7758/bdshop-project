package com.bigdatan.b2c.service.message;

import java.util.List;

import com.bigdatan.b2c.entity.message.UserMessage;
import com.bigdatan.b2c.service.base.IBaseService;

public interface IUserMessageService extends IBaseService<UserMessage> {
	public int addMessageRecord(Integer messageId, String userIdList);

	/**
	 * 
	 * @param userId
	 *            前端用户id
	 * @return 获取用户 消息未读数
	 */
	public int getNoReadCount(int userId);

	/**
	 * 
	 * @param userId
	 * @return 获取消息已读数和未读数
	 */
	public List<Integer> getMessageCount(int userId);

	UserMessage getOneById(Integer userMessageId);
}
