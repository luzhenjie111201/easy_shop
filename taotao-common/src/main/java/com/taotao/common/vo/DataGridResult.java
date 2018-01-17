package com.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable  {
	//返回的数据格式必须含有total,rows
	private Long total;
	private List<?> rows;
	public DataGridResult() {
		super();
	}
	public DataGridResult(Long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
