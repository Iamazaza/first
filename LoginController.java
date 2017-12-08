package com.liyuting.drug.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuting.drug.commons.util.DateUtil;
import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.domain.User;
import com.liyuting.drug.service.UserService;
import com.liyuting.drug.service.impl.UserServiceImpl;
/**
 * 用户登录
 * @author Administrator
 *
 */
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("欢迎进入药品信息管理系统");
		//获取参数
		String loginAct=req.getParameter("loginAct");
		String loginPwd=req.getParameter("loginPwd");
		String isRemPwd=req.getParameter("isRemPwd");
		//封装参数
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("loginAct", loginAct);
		map.put("loginPwd",loginPwd);
		//调用service方法，查询用户
		UserService us=(UserService)ServiceFactory.getServiceProxy(new UserServiceImpl());
		User user=us.queryUserByLoginActPwd(map);
		//根据处理结果，返回响应信息(json)
		Map<String,Object> retMap=new HashMap<String,Object>();
		if(user!=null){
			if(user.getLockStatus()!=null&&"1".equals(user.getLockStatus())){
				retMap.put("success", false);
				retMap.put("msg", "用户被锁定！");
			}else if(!user.getAllowIps().contains(req.getRemoteAddr())){
				retMap.put("success", false);
				retMap.put("msg", "ip不允许访问！");
			}else if(DateUtil.formatDateTime(new Date()).compareTo(user.getExpireTime())>0){
				retMap.put("success", false);
				retMap.put("msg", "账号已失效！");
			}else{
				retMap.put("success", true);
				
				if("true".equals(isRemPwd)){
					Cookie c1=new Cookie("loginAct",loginAct);
					c1.setMaxAge(60*60*24*10);
					resp.addCookie(c1);
					Cookie c2=new Cookie("loginPwd",loginPwd);
					c2.setMaxAge(60*60*24*10);
					resp.addCookie(c2);
				}else{
					Cookie c1=new Cookie("loginAct","");
					c1.setMaxAge(0);
					resp.addCookie(c1);
					Cookie c2=new Cookie("loginPwd","");
					c2.setMaxAge(0);
					resp.addCookie(c2);
				}
				//把user对象保存到session中
				req.getSession().setAttribute("user", user);
			}
		}else{
			retMap.put("success", false);
			retMap.put("msg", "账号或密码错误！");
		}
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(retMap);
		req.setAttribute("data", json);
		req.getRequestDispatcher("/data.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
