package com.liyuting.drug.service.impl;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.commons.util.SqlSessionUtil;
import com.liyuting.drug.dao.UserDao;
import com.liyuting.drug.domain.User;
import com.liyuting.drug.service.UserService;
/**
 * 用户业务处理类
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService {
	private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
	/**
	 * 根据账号和密码查询用户
	 */
	@Override
	public User queryUserByLoginActPwd(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDao.queryUserByLoginActPwd(map);
	}
	/**
	 * 查询所有用户
	 */
	@Override
	public List<User> queryAllUsers() {
		// TODO Auto-generated method stub
		return userDao.queryAllUsers();
	}

}
