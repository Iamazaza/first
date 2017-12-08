package com.liyuting.drug.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuting.drug.commons.domain.PaginationVO;
import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;
/**
 * 根据条件分页查询市场活动
 * @author Administrator
 *
 */
public class QueryDrugForPageByConditionController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("欢迎进入分页查询页面");
		//获取参数
		String pageNoStr=req.getParameter("pageNo");
		String pageSizeStr=req.getParameter("pageSize");
		String drug_idStr=req.getParameter("drug_id");
		String drug_name=req.getParameter("drug_name");
		String purchase_priceStr=req.getParameter("purchase_price");
		String purchase_numberStr=req.getParameter("purchase_number");
		String purchase_date=req.getParameter("purchase_date");
		/*String provider=req.getParameter("provider");
		String producer=req.getParameter("producer");
		String specification=req.getParameter("specification");
		String description=req.getParameter("description");
		String price=req.getParameter("price");*/
		System.out.println("pageNoStr"+pageNoStr);
		System.out.println("pageSizeStr"+pageSizeStr);
		System.out.println("drug_id"+drug_idStr);
		System.out.println("drug_name"+drug_name);
		System.out.println("purchase_price"+purchase_priceStr);
		System.out.println("purchase_number"+purchase_numberStr);
		System.out.println("purchase_date"+purchase_date);
		/*System.out.println("provider"+provider);
		System.out.println("producer"+producer);
		System.out.println("specification"+specification);
		System.out.println("description"+description);
		System.out.println("price"+price);*/
		
		//封装参数
		Map<String,Object> map=new HashMap<String,Object>();
		
		long pageNo=1;
		if(pageNoStr!=null&&pageNoStr.trim().length()>0){
			pageNo=Long.parseLong(pageNoStr.trim());
		}
		int pageSize=10;
		if(pageSizeStr!=null&&pageSizeStr.trim().length()>0){
			pageSize=Integer.parseInt(pageSizeStr.trim());
		}
		
		
		long beginNo=(pageNo-1)*pageSize;
		map.put("beginNo", beginNo);
		map.put("pageSize", pageSize);
		map.put("drug_id", "");
		if(drug_idStr!=null&&drug_idStr.trim().length()>0){
			map.put("drug_id", Integer.valueOf(drug_idStr));
		}
		map.put("drug_name", drug_name);
		map.put("purchase_price", "");
		if(purchase_priceStr!=null&&purchase_priceStr.trim().length()>0){
			map.put("purchase_price", Double.valueOf(purchase_priceStr));
		}
		map.put("purchase_number", "");
		if(purchase_numberStr!=null&&purchase_numberStr.trim().length()>0){
			map.put("purchase_number", Integer.valueOf(purchase_numberStr));
		}
		
		map.put("purchase_date", purchase_date);
		/*map.put("provider", provider);
		map.put("specification", specification);
		map.put("description", description);
		map.put("price", price);
		*/
		//调用service方法，查询数据
		DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		PaginationVO vo = ds.queryDrugForPageByCondition(map);
		//根据查询结果，返回响应信息(json)
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(vo);
		req.setAttribute("data", json);
		req.getRequestDispatcher("/data.jsp").forward(req, resp);
		/*
		resp.setContentType("text/json;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.write(json);
		out.flush();*/
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
