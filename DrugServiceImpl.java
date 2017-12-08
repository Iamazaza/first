package com.liyuting.drug.service.impl;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.commons.domain.PaginationVO;
import com.liyuting.drug.commons.util.SqlSessionUtil;
import com.liyuting.drug.dao.DrugDao;
import com.liyuting.drug.dao.EnterDrugDao;
import com.liyuting.drug.dao.InventoryDao;
import com.liyuting.drug.dao.SellDrugDao;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.domain.EnterDrug;
import com.liyuting.drug.domain.Inventory;
import com.liyuting.drug.service.DrugService;

public class DrugServiceImpl implements DrugService {

	private EnterDrugDao enterDrug = SqlSessionUtil.getSqlSession().getMapper(EnterDrugDao.class);
	private SellDrugDao sellDrug = SqlSessionUtil.getSqlSession().getMapper(SellDrugDao.class);
	private InventoryDao inventoryDao = SqlSessionUtil.getSqlSession().getMapper(InventoryDao.class);
	private DrugDao drugDao = SqlSessionUtil.getSqlSession().getMapper(DrugDao.class);
	
	@Override
	public int saveEnterDrug(EnterDrug Drug,Inventory inventory) {
		int ret1=inventoryDao.saveInventory(inventory);
		int ret2=enterDrug.saveEnterDrug(Drug);
		int ret = 0;
		if(ret1>0&&ret2>0){
			ret=2;
		}
		return ret;
	}
	@Override
	public int SellDrug(String[] ids) {
		return sellDrug.SellDrug(ids);
	}
	@Override
	public PaginationVO queryDrugForPageByCondition(Map<String, Object> map) {
		List<Drug> drugList=inventoryDao.queryDrugForPageByCondition(map);
		long count = inventoryDao.queryCountOfDrugByCondition(map);
		PaginationVO pvo=new PaginationVO();
		pvo.setDataList(drugList);
		pvo.setCount(count);
		
		return pvo;
	}
	@Override
	public Drug queryDrugById(int id) {
		return drugDao.queryDrugById(id);
	}
	@Override
	public int saveEditDrug(Inventory inventory,EnterDrug enterdrug) {
		int ret1=inventoryDao.saveEditInventory(inventory);
		int ret2=enterDrug.saveEditEnterDrug(enterdrug);
		int ret=0;
		if(ret1>0&&ret2>0){
			ret=2;
		}
		return ret;
	}
	@Override
	public List<Drug> queryDrugByCondition(Map<String, Object> map) {
		return drugDao.queryDrugByCondition(map);
	}
}
