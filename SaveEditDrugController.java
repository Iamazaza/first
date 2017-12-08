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
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;
/**
 * 保存修改的药品信息
 * @author Administrator
 *
 */
public class SaveEditDrugController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("欢迎进入保存修改药品信息页");
		//获取参数
	
		String drug_idStr=req.getParameter("drug_id");
		String drug_name=req.getParameter("drug_name");
		String purchase_priceStr=req.getParameter("purchase_price");
		String purchase_numberStr=req.getParameter("purchase_number");
		String purchase_date=req.getParameter("purchase_date");
		String provider=req.getParameter("provider");
		String producer=req.getParameter("producer");
		String specification=req.getParameter("specification");
		String description=req.getParameter("description");
		String priceStr=req.getParameter("price");
		String inventoryStr=req.getParameter("inventory");
		System.out.println("drug_idStr="+drug_idStr);
		System.out.println("drug_name="+drug_name);
		System.out.println("purchase_priceStr="+purchase_priceStr);
		System.out.println("purchase_numberStr="+purchase_numberStr);
		System.out.println("purchase_date="+purchase_date);
		System.out.println("provider="+provider);
		System.out.println("producer="+producer);
		System.out.println("specification="+specification);
		System.out.println("description="+description);
		System.out.println("priceStr="+priceStr);
		System.out.println("inventoryStr="+inventoryStr);
		
		//封装参数
		EnterDrug drug=new EnterDrug();
	
		int inventory=0;
		if(inventoryStr!=null&&inventoryStr.length()>0){
			inventory=Integer.valueOf(inventoryStr);
		}
		Inventory in = new Inventory();
		drug.setDrug_id(Integer.valueOf(drug_idStr));
		drug.setProvider(provider);
		drug.setPurchase_date(purchase_date);
		drug.setPurchase_number(Integer.valueOf(purchase_numberStr));
		drug.setPurchase_price(Double.valueOf(purchase_priceStr));
		in.setDrug_id(Integer.valueOf(drug_idStr));
		in.setDrug_name(drug_name);
		in.setInventory(inventory);
		in.setPrice(Double.valueOf(priceStr));
		in.setProducer(producer);
		in.setSpecification(specification);
		//调用service方法，保存数据
		DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		int ret=ds.saveEditDrug(in,drug);
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
