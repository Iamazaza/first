package com.liyuting.drug.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuting.drug.commons.util.ServiceFactory;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.domain.EnterDrug;
import com.liyuting.drug.domain.Inventory;
import com.liyuting.drug.service.DrugService;
import com.liyuting.drug.service.impl.DrugServiceImpl;
/**
 * 导入市场活动
 * @author Administrator
 *
 */
public class ImportMarketActivityController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int ret=0;
		Map<String,Object> map=new HashMap<String,Object>();
		//使用apache-fileupload获取参数
		//1.创建磁盘文件工厂对象
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//2.设置临时目录
		ServletContext context=this.getServletContext();
		String tempPath=context.getRealPath("/tempFile");//根据虚拟路径获取相对应的实际路径
		factory.setRepository(new File(tempPath));
		//3.创建处理文件上传的核心处理类对象
		ServletFileUpload upload=new ServletFileUpload(factory);
		try{
			//4.解析请求，获取数据
			List<FileItem> itemList=upload.parseRequest(req);
			if(itemList!=null&&itemList.size()>0){
				for(FileItem item:itemList){
					if(item.isFormField()){
						String name=item.getFieldName();
						String value=item.getString("UTF-8");
						System.out.println(name+"="+value);
					}else{
						String upPath=context.getRealPath("/upFile");
						String fileName=item.getName();//文件名 
						/*item.write(new File(upPath+"/"+fileName));
						
						//使用apache-poi解析excel文件
						InputStream is=new FileInputStream(upPath+"/"+fileName);*/
						InputStream is=item.getInputStream();
						
						HSSFWorkbook wb=new HSSFWorkbook(is);
						HSSFSheet sheet=wb.getSheetAt(0);
						HSSFRow row=null;
						EnterDrug edrug=null;
						Inventory inventory =null;
						HSSFCell cell=null;
						List<EnterDrug> edrugList=new ArrayList<EnterDrug>();
						List<Inventory> invList=new ArrayList<Inventory>();
						for(int i=1;i<=sheet.getLastRowNum();i++){
							row=sheet.getRow(i);
							
							edrug=new EnterDrug();
							inventory=new Inventory();
							for(int j=0;j<row.getLastCellNum();j++){
								cell=row.getCell(j);
								String value=getCellValue4Str(cell);
								if(j==0){
									edrug.setDrug_id(Integer.valueOf(value));
									inventory.setDrug_id(Integer.valueOf(value));
								}else if(j==1){
									inventory.setDrug_name(value);
								}else if(j==2){
									edrug.setPurchase_price(Double.valueOf(value));
								}else if(j==3){
									inventory.setPrice(Double.valueOf(value));
								}else if(j==4){
									edrug.setPurchase_number(Integer.valueOf(value));
								}else if(j==5){
									inventory.setInventory(Integer.valueOf(value));
								}else if(j==6){
									edrug.setPurchase_date(value);
								}else if(j==7){
									edrug.setProvider(value);
								}else if(j==8){
									inventory.setProducer(value);
								}else if(j==9){
									inventory.setSpecification(value);
								}
							}
							/*drug.setDrug_id(drug_id);
							ma.setId(UUIDUtil.getUUID());
							ma.setType("d127fa495fc64b2e8edfb4becfc5f375");
							ma.setState("67a6d0c74e2d409881dd0416a9dfbe97");
							ma.setOwner(((User)req.getSession().getAttribute("user")).getId());
							ma.setCreateBy(((User)req.getSession().getAttribute("user")).getId());
							ma.setCreateTime(com.bjpowernode.crm.commons.util.DateUtil.formatDateTime(new Date()));
							for(int j=0;j<row.getLastCellNum();j++){
								cell=row.getCell(j);
								String value=getCellValue4Str(cell);
								if(j==0){
									ma.setName(value);
								}else if(j==1){
									ma.setStartDate(value);
								}else if(j==2){
									ma.setEndDate(value);
								}else if(j==3){
									if(value!=null&&value.trim().length()>0){
										ma.setBudgetCost((long)Double.parseDouble(value.trim()));
									}
								}else if(j==4){
									if(value!=null&&value.trim().length()>0){
										ma.setActualCost((long)Double.parseDouble(value.trim()));
									}
								}else if(j==5){
									ma.setDescription(value);
								}
							}*/
							edrugList.add(edrug);
							invList.add(inventory);
							//每3条批量保存一次
							if(edrugList.size()%3==0&&invList.size()%3==0){
								//调用service方法，保存数据
								DrugService ds=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
								ret+=ds.saveEnterDrug(edrug, inventory);
								System.out.println("批量保存ret="+ret);
								// 清空list
								edrugList.clear();
							}
						}
						//最后保存一次
						if(edrugList.size()>0){
							//调用service方法，保存数据
							DrugService mas=(DrugService)ServiceFactory.getServiceProxy(new DrugServiceImpl());
							ret+=mas.saveEnterDrug(edrug, inventory);
							System.out.println("最后保存ret="+ret);
						}
						
						//根据处理结果，返回响应信息(json)
						map.put("success", true);
						map.put("count", ret);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("count", ret);
		}
		
		//把map转换为json，返回客户端
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(map);
		req.setAttribute("data", json);
		req.getRequestDispatcher("/data.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public static String getCellValue4Str(HSSFCell cell){
		String retStr="";
		switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_STRING:
				retStr=cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				retStr=String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)){
					Date d=cell.getDateCellValue();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					retStr=sdf.format(d);
				}else{
					retStr=String.valueOf(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				retStr=cell.getCellFormula();
				break;
		    default:
		    	retStr="";
		}
		return retStr;
	}
	
}
