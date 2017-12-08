package com.liyuting.drug.commons.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;
/**
 * 获取代理对象 
 * @author Administrator
 *
 */
public class TransactionInvocationHandler implements InvocationHandler {
	
	private Object target;
	
	public TransactionInvocationHandler(Object target){
		this.target=target;
	}
	/**
	 * 代理方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		SqlSession sqlSession=null;
		Object ret=null;
		try{
			sqlSession=SqlSessionUtil.getSqlSession();//session
			ret=method.invoke(target, args);
			sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
			sqlSession.rollback();
			throw e;
		}finally{
			SqlSessionUtil.closeSqlSession(sqlSession);
		}
		
		return ret;
	}
	/**
	 * 获取代理对象
	 * @return
	 */
	public Object getProxy(){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
}
