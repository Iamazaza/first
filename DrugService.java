package com.liyuting.drug.service;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.commons.domain.PaginationVO;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.domain.EnterDrug;
import com.liyuting.drug.domain.Inventory;

public interface DrugService {
	/**
	 * 查询药品信息进入导出
	 * @param map
	 * @return
	 */
	public List<Drug> queryDrugByCondition(Map<String,Object> map);
	/**
	 * 保存修改的药品信息
	 * @param drug
	 * @return
	 */
	public int saveEditDrug(Inventory inventory,EnterDrug enterdrug);
	/**
	 * 根据id查询药品信息
	 * @param id
	 * @return
	 */
	public Drug queryDrugById(int id);
	/**
	 * 保存进药信息
	 * @param enterDrug
	 * @return
	 */
	public int saveEnterDrug(EnterDrug enterDrug,Inventory inventory);
	
	/**
	 * 根据ids售药
	 * @param ids
	 * @return
	 */
	public int SellDrug(String[] ids);
	/**
	 * 根据条件分页查询药品列表
	 * @param map
	 * @return
	 */
	public PaginationVO queryDrugForPageByCondition(Map<String,Object> map);
}
