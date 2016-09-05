package org.mf.web.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;


public class ColumnOrderDataTable {

	/**
	order[0][column]|-->[0]
	order[0][dir]|-->[asc]
	 * 
	 */

	int pos;			//nr colonna su cui va fatto l'ordinamenti
	String direction;	//asc o desc
	
	public ColumnOrderDataTable(HttpServletRequest request, int i) {
		super();
		String tmp = request.getParameter("order[" + i +"][column]");
		pos = tmp != null ? Integer.valueOf(tmp) : -1;
		if (pos >= 0)
			direction = request.getParameter("order[" + i +"][dir]");
	}

	public int getPos() {
		return pos;
	}

	public String getDirection() {
		return direction;
	}
	
	public boolean isValid() {
		return pos >= 0;
	}
	
	/**
	 * 
	 * @param columns
	 * @return org.hibernate.criterion.Order 
	 */
	public Order getOrder(ColumnDataTable[] columns) {
		
		if (pos < 0 || pos >= columns.length)
			return null;	//pos fuori range
		
		String colName = columns[pos].getData();
		
		return new Order(Direction.fromString(direction), colName);
//		return "desc".equalsIgnoreCase(direction) ? Order.desc(colName) : Order.asc(colName);
	}

	@Override
	public String toString() {
		return "ColumnOrder [" + (isValid() 
				? "pos=" + pos + ", direction=" + direction
				: "not valid"
				 + "]");
	}

}
