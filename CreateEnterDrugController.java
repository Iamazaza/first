package com.liyuting.drug.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.domain.User;
import com.liyuting.drug.service.UserService;
import com.liyuting.drug.service.impl.UserServiceImpl;

public class CreateEnterDrugController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("欢迎进入进药列表页");
		//调用service方法，查询所有用户
				UserService us=(UserService)ServiceFactory.getServiceProxy(new UserServiceImpl());
				List<User> userList=us.queryAllUsers();
				
				
				
				//根据查询结果，返回响应信息(json)
				ObjectMapper mapper=new ObjectMapper();
				String json=mapper.writeValueAsString(userList);
				
				request.setAttribute("data", json);
				request.getRequestDispatcher("/data.jsp").forward(request, response);
				
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
