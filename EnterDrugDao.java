package com.liyuting.drug.dao;

import com.liyuting.drug.domain.EnterDrug;

public interface EnterDrugDao {
	
	/**
	 * 保存进药信息
	 * @param enterDrug
	 * @return
	 */
	public int saveEnterDrug(EnterDrug enterDrug);
	/**
	 * 保存修改的药品信息到进药表
	 * @param drug
	 * @return
	 */
	public int saveEditEnterDrug(EnterDrug enterdrug);
}
