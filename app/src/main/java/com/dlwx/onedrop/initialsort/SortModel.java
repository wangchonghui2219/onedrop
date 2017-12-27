package com.dlwx.onedrop.initialsort;

public class SortModel extends BaseProBean{

	private String name;//显示的数据
	private String sortLetters;//显示数据拼音的首字母
	private String pro;//省

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	
	

}
