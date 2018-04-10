package com.bigdatan.b2c.service.receive.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bigdatan.b2c.dao.base.IBaseDao;
import com.bigdatan.b2c.dao.receive.ReceiveMapper;
import com.bigdatan.b2c.entity.receive.Receive;
import com.bigdatan.b2c.entity.user.User;
import com.bigdatan.b2c.service.base.impl.BaseServiceImpl;
import com.bigdatan.b2c.service.receive.IReceiveService;

@Service
public class ReceiveServiceImpl extends BaseServiceImpl<Receive> implements
		IReceiveService {

	@Resource
	private ReceiveMapper receiveMapper;

	@Override
	protected IBaseDao<Receive> getMapper() {
		return receiveMapper;
	}

	@Override
	public Receive queryDefaultReceive(User user) {
		return receiveMapper.selectDefaultReceive(user.getUserId());
	}

	@Override
	public List<Receive> queryReceive(User user) {
		return receiveMapper.selectReceive(user.getUserId());
	}

	/**
	 * 设置默认地址
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, noRollbackFor = { Exception.class })
	public void setDefaultReceive(Receive receive, Integer receiveId)
			throws Exception {
		if (receive != null && !receive.getReceiveId().equals(receiveId)) {
			receive.setIsDefault((byte) 0);
			receive.setUpdateTime(new Date());
			updateByPrimaryKeySelective(receive);
		}
		Receive defaultReceive = selectByPrimaryKey(receiveId);
		defaultReceive.setIsDefault((byte) 1);
		updateByPrimaryKeySelective(defaultReceive);
	}

	/**
	 * 添加地址
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, noRollbackFor = { Exception.class })
	public void addReceive(Receive receive, User user) throws Exception {
		Receive defaultReceive = queryDefaultReceive(user);
		if (defaultReceive != null) {
			defaultReceive.setIsDefault((byte) 0);
			defaultReceive.setUpdateTime(new Date());
			updateByPrimaryKeySelective(defaultReceive);
		}
		insertSelective(receive);
	}
}
