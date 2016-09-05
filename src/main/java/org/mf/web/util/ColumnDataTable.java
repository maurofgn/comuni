package org.mf.web.util;

import javax.servlet.http.HttpServletRequest;

public class ColumnDataTable {
	
	String data;
	String name;
	Boolean searchable;
	Boolean orderable;
	String searchValue;
	Boolean searchRegex;
	
	
	/**
	 * 
columns[0][data]|-->[created]
columns[0][name]|-->[]
columns[0][searchable]|-->[true]
columns[0][orderable]|-->[true]
columns[0][search][value]|-->[]
columns[0][search][regex]|-->[false]
	 * 
	 */
	
	
	public ColumnDataTable(HttpServletRequest request, int pos) {
		super();
		data = request.getParameter("columns[" + pos +"][data]");
		if (data != null) {
			name = request.getParameter("columns[" + pos +"][name]");
			searchable = Boolean.valueOf(request.getParameter("columns[" + pos +"][searchable]"));
			orderable = Boolean.valueOf(request.getParameter("columns[" + pos +"][orderable]"));
			searchValue = request.getParameter("columns[" + pos +"][searchValue]");
			searchRegex = Boolean.valueOf(request.getParameter("columns[" + pos +"][searchRegex]"));
		}
	}
	
	public boolean isValid() {
		return data != null;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public boolean isOrderable() {
		return orderable;
	}
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public boolean isSearchRegex() {
		return searchRegex;
	}
	public void setSearchRegex(boolean searchRegex) {
		this.searchRegex = searchRegex;
	}


	@Override
	public String toString() {
		return "Column [" + (isValid() 
				? "data=" + data + ", name=" + name
					+ ", searchable=" + searchable + ", orderable=" + orderable
					+ ", searchValue=" + searchValue + ", searchRegex=" + searchRegex
				: "not valid"
				 + "]");
	}
	
	
	
}
