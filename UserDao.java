package com.liyuting.drug.dao;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.domain.User;

/**
 * 用户持久化操作接口
 * @author Administrator
 *
 */
public interface UserDao {
	/**
	 * 根据登录账号和密码查询用户
	 * @param map
	 * @return
	 */
	public User queryUserByLoginActPwd(Map<String,Object> map);
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> queryAllUsers();
}
