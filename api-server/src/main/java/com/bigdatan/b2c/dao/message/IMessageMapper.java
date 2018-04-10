package com.bigdatan.b2c.dao.message;

import java.util.List;

import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.entity.message.Message;

public interface IMessageMapper  extends IBaseDao<Message>{
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);
    
    public List<Message> getMessageLevel(Integer userId);
}