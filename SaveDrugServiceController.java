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
import com.liyuting.drug.domain.EnterDrug;
import com.liyuting.drug.domain.Inventory;
import com.liyuting.drug.domain.User;
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;

public class SaveDrugServiceController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("欢迎进入保存药品信息列");
		//获取参数
		
		String drug_idStr         =req.getParameter("drug_id");
		String purchase_priceStr  =req.getParameter("purchase_price");
		String purchase_numberStr =req.getParameter("purchase_number");
		String purchase_date   =req.getParameter("purchase_date");
		String provider        =req.getParameter("provider");
	
		/*String drug_id       =req.getParameter("drug_id");
		*/
		String drug_name     =req.getParameter("drug_name");
		String inventoryStr     =req.getParameter("inventory");
		String producer      =req.getParameter("producer");
		String specification =req.getParameter("specification");
		String priceStr         =req.getParameter("price");

		
		//封装参数
		EnterDrug ed = new EnterDrug();
		Inventory in = new Inventory();
		
		/*User user=(User)req.getSession().getAttribute("user");*/
		Integer drug_id = Integer.valueOf(drug_idStr);
		Double purchase_price = 0.0;
		Integer purchase_number = 0;;
		if(purchase_priceStr != null){
			
			purchase_price = Double.valueOf(purchase_priceStr);
		}
		
		if(purchase_numberStr != null){
			
			purchase_number = Integer.valueOf(purchase_numberStr);
		}
		/*ed.setBills_id(Integer.valueOf(UUIDUtil.getUUID().toString()));*/
		ed.setDrug_id(drug_id);
		ed.setProvider(provider);
		ed.setPurchase_date(purchase_date);
		ed.setPurchase_number(purchase_number);
		ed.setPurchase_price(purchase_price);
		
		in.setDrug_id(drug_id);
		in.setDrug_name(drug_name);
		//in.setInventory(Integer.valueOf(purchase_number));
		in.setPrice(Double.valueOf(priceStr));
		in.setProducer(producer);
		in.setSpecification(specification);
		//调用service方法，保存数据
		DrugService eds = (DrugService) ServiceFactory.getServiceProxy(new DrugServiceImpl());
		
		
		int ret=eds.saveEnterDrug(ed,in);
		/*int ret2=eds.saveInventory(in);*/
		
		//根据处理结果，返回响应信息(json)
		Map<String,Object> map=new HashMap<String,Object>();
		if(ret>0){
			map.put("success",true);
		}else{
			map.put("success",false);
		}
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(map);
		
		req.setAttribute("data", json);
		req.getRequestDispatcher("/data.jsp").forward(req, resp);
		/*resp.setContentType("text/json;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.write(json);
		out.flush();*/
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
