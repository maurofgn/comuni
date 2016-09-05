package org.mf.web.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort.Order;

//import org.hibernate.criterion.Order;

public class ColumnsDataTable {
	
	private static final String DRAW = "draw";
	private static final String START = "start";
	private static final String LENGTH = "length";
	
	Integer draw = 1;		//valore da ritornare x permettere la syncro
	Integer start = 0;		//first record (0 based) to read
	Integer length = 10;	//Number of records that the table can display in the current draw

	ColumnDataTable[] columns;
	ColumnOrderDataTable[] columnsOrder;
	
	public ColumnsDataTable(HttpServletRequest request) {
		super();
		
		draw = getPara(request, DRAW, 1);			//valore da ritornare x permettere la syncro
		start = getPara(request, START, 0);			//first record (0 based) to read
		length = getPara(request, LENGTH, 10);		//Number of records that the table can display in the current draw

		loadColumns(request);
		loadOrders(request);
	}
	
	private Integer getPara(HttpServletRequest request, String paraKey, Integer defaultValue) {
		String p = request.getParameter(paraKey);
		Integer retValue = defaultValue;
		try {
			retValue = Integer.parseInt(p);
		} catch (NumberFormatException e) {
			retValue = defaultValue;
		}
		
		return retValue;
	}

	private void loadOrders(HttpServletRequest request) {
		List<ColumnOrderDataTable> retValue = new LinkedList<ColumnOrderDataTable>();
		int i = 0;
		while (i >= 0) {
			
			ColumnOrderDataTable c = new ColumnOrderDataTable(request, i++);
			if (c.isValid())
				retValue.add(c);
			else
				break;
		}
		
		columnsOrder = new ColumnOrderDataTable[retValue.size()];
		retValue.toArray(columnsOrder);
	}

	private void loadColumns(HttpServletRequest request) {
		
		List<ColumnDataTable> retValue = new LinkedList<ColumnDataTable>();
		
		int i = 0;
		while (i >= 0) {
			
			ColumnDataTable c = new ColumnDataTable(request, i++);
			if (c.isValid())
				retValue.add(c);
			else
				break;
		}
		columns = new ColumnDataTable[retValue.size()];
		retValue.toArray(columns);
	}

	public Integer getDraw() {
		return draw;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getLength() {
		return length;
	}

	public ColumnDataTable[] getColumns() {
		return columns;
	}
	
	public ColumnOrderDataTable[] getColumnsOrder() {
		return columnsOrder;
	}

	public List<Order> getOrder() {
		
		List<Order> retValue = new LinkedList<Order>();
		
		if (columnsOrder == null)
			return retValue;
		
		for (int i = 0; i < columnsOrder.length; i++) {
			Order order = columnsOrder[i].getOrder(columns);
			if (order != null)
				retValue.add(order);
		}
		return retValue;
	}
	
	@Override
	public String toString() {
		return "ColumnOrder [columns=" + Arrays.toString(columns) + "]";
	}
	
}
