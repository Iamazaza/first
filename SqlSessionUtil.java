package com.liyuting.drug.commons.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * SqlSession对象工具类
 * @author Administrator
 *
 */
public class SqlSessionUtil {
	static SqlSessionFactory sqlSessionFactory;
	static ThreadLocal<SqlSession> local=new ThreadLocal<SqlSession>();
	
	static{
		String resource = "mybatis.xml";
		InputStream inputStream=null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	/**
	 * 获取线程安全的SqlSession
	 * @return
	 */
	public static SqlSession getSqlSession(){
		SqlSession sqlSession=local.get();
		if(sqlSession==null){
			sqlSession=sqlSessionFactory.openSession();
			local.set(sqlSession);
		}
		return sqlSession;
	}
	/**
	 * 关闭SqlSession
	 * @param sqlSession
	 */
	public static void closeSqlSession(SqlSession sqlSession){
		if(sqlSession!=null){
			sqlSession.close();
			local.remove();
		}
	}
}
