package com.liyuting.drug.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;
/**
 * 修改市场活动
 * @author Administrator
 *
 */
public class EditDrugController extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("欢迎进入修改药品信息页");
		//获取参数
		String idStr=req.getParameter("id");
		//调用service方法，查询数据
		/*DrugService us=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		List<User> userList=us.queryAllUsers();
		*/
		DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		System.out.println("qqqqqqqq");
		int id = Integer.valueOf(idStr);
		System.out.println(id);
		Drug drug=ds.queryDrugById(id);
		//把userList和activity封装成map
		Map<String,Object> map=new HashMap<String,Object>();
		/*map.put("userList", userList);*/
		map.put("drug", drug);
		
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(map);
		req.setAttribute("data", json);
		req.getRequestDispatcher("/data.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
