package com.liyuting.drug.commons.domain;

import java.util.List;

/**
 * 分页查询实体类
 * 
 * @author Administrator
 *
 */
public class PaginationVO<T> {
	private long count;
	private List<T> dataList;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
