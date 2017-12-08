package com.liyuting.drug.dao;

import java.util.List;
import java.util.Map;

import com.liyuting.drug.commons.domain.PaginationVO;
import com.liyuting.drug.domain.Drug;
import com.liyuting.drug.domain.Inventory;


public interface InventoryDao {
	/**
	 * 保存进药信息到库存
	 * @param inventory
	 * @return
	 */
	public int saveInventory(Inventory inventory);
	/**
	 * 根据条件分页查询药品列表
	 * @param map
	 * @return
	 */
	public List<Drug> queryDrugForPageByCondition(Map<String,Object> map);
	/**
	 * 根据条件查询药品的总条数
	 * @param map
	 * @return
	 */
	public long queryCountOfDrugByCondition(Map<String,Object> map);
	/**
	 * 保存修改的药品信息到库存表
	 * @param Inventory
	 * @return
	 */
	public int saveEditInventory(Inventory inventory);
}
