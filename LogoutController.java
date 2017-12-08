package com.liyuting.drug.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 安全退出
 * @author Administrator
 *
 */
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//销毁session
		HttpSession session=req.getSession(false);
		if(session!=null){
			session.invalidate();
		}
		//销毁cookie
		Cookie c1=new Cookie("loginAct","");
		c1.setMaxAge(0);
		resp.addCookie(c1);
		Cookie c2=new Cookie("loginPwd","");
		c2.setMaxAge(0);
		resp.addCookie(c2);
		//跳转到欢迎页面
		resp.sendRedirect(req.getContextPath());   
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
