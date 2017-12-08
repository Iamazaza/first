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
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;
/**
 * 删除市场活动
 * @author Administrator
 *
 */
public class DeleteDrugController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取参数
		String[] ids=req.getParameterValues("id");
		//调用service方法，删除数据
		DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		int ret=ds.SellDrug(ids);
		//根据处理结果，返回响应信息(json)
		Map<String,Object> map=new HashMap<String,Object>();
		if(ret>0){
			map.put("success", true);
		}else{
			map.put("success", false);
		}
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
