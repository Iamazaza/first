package com.liyuting.drug.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;

/**
 * 导出药品信息
 * @author Administrator
 *
 */
public class ExportMarketActivityController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取参数
		
		String drug_idStr=req.getParameter("drug_id");
		String drug_name=req.getParameter("drug_name");
		String purchase_priceStr=req.getParameter("purchase_price");
		String purchase_numberStr=req.getParameter("purchase_number");
		String purchase_date=req.getParameter("purchase_date");
		
		//封装参数
		Map<String,Object> map=new HashMap<String,Object>();
		
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
		//调用service方法，查询市场活动
		DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
		List<Drug> drugList=ds.queryDrugByCondition(map);
		//使用apache-poi生成excel文件
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("药品信息列表");
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=row.createCell(0);
		cell.setCellValue("药品编码");
		cell=row.createCell(1);
		cell.setCellValue("药品名称");
		cell=row.createCell(2);
		cell.setCellValue("采购价");
		cell=row.createCell(3);
		cell.setCellValue("零售价");
		cell=row.createCell(4);
		cell.setCellValue("采购数量");
		cell=row.createCell(5);
		cell.setCellValue("库存");
		cell=row.createCell(6);
		cell.setCellValue("采购日期");
		cell=row.createCell(7);
		cell.setCellValue("供应商");
		cell=row.createCell(8);
		cell.setCellValue("生产厂家");
		cell=row.createCell(9);
		cell.setCellValue("规格");
		cell=row.createCell(10);
		cell.setCellValue("描述");
		
		
		//遍历list，创建数据行
		if(drugList!=null&&drugList.size()>0){
			Drug drug=null;
			for(int i=0;i<drugList.size();i++){
				row=sheet.createRow(i+1);
				drug=drugList.get(i);
				
				cell=row.createCell(0);
				cell.setCellValue(drug.getDrug_id());
				cell=row.createCell(1);
				cell.setCellValue(drug.getDrug_name());
				cell=row.createCell(2);
				cell.setCellValue(drug.getPurchase_price());
				cell=row.createCell(3);
				cell.setCellValue(drug.getPrice());
				cell=row.createCell(4);
				cell.setCellValue(drug.getPurchase_number());
				cell=row.createCell(5);
				cell.setCellValue(drug.getInventory());
				cell=row.createCell(6);
				cell.setCellValue(drug.getPurchase_date());
				cell=row.createCell(7);
				cell.setCellValue(drug.getProvider());
				cell=row.createCell(8);
				cell.setCellValue(drug.getProducer());
				cell=row.createCell(9);
				cell.setCellValue(drug.getSpecification());
				cell=row.createCell(10);
				cell.setCellValue(drug.getDescription());
				
			}
		}
		/*OutputStream os=new FileOutputStream("d:/aaa.xls");
		wb.write(os);*/
		
		//把excel文件输出到客户端
		resp.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outs=resp.getOutputStream();
		String browser=req.getHeader("Drug-Agent");
		String fileName=URLEncoder.encode("药品信息列表", "UTF-8");
		if(browser.toLowerCase().contains("firefox")){
			fileName=new String("药品信息列表".getBytes("UTF-8"),"ISO8859-1");
		}
		resp.addHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
		/*InputStream is=new FileInputStream("d:/aaa.xls");
		byte[] buff=new byte[256];
		int len;
		while((len=is.read(buff))!=-1){
			outs.write(buff, 0, len);
		}*/
		wb.write(outs);
		outs.flush();
		//is.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
