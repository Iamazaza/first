package com.liyuting.drug.dao;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.domain.EnterDrug;
import com.liyuting.drug.domain.Inventory;

public interface DrugDao {

	/**
	 * 查询药品信息进入导出
	 * @param map
	 * @return
	 */
	public List<Drug> queryDrugByCondition(Map<String,Object> map);
	/**
	 * 根据id查询药品信息
	 * @param id
	 * @return
	 */
	public Drug queryDrugById(int id);
}
