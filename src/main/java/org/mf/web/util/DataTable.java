package org.mf.web.util;

import java.io.Serializable;
import java.util.List;

public class DataTable<T extends Serializable> {
	
	public static final String DEFAULT_LINES_PER_PAGE = "10";

	Integer draw;			 	//The draw counter that this object is a response to - from the draw parameter sent as part of the data request. Note that it is strongly recommended for security reasons that you cast this parameter to an integer, rather than simply echoing back to the client what it sent in the draw parameter, in order to prevent Cross Site Scripting (XSS) attacks.
	Long recordsTotal;		 	//Total records, before filtering (i.e. the total number of records in the database)
	Integer recordsFiltered;	//Total records, after filtering (i.e. the total number of records after filtering has been applied - not just the number of records being returned for this page of data).
	List<T> data;			 	//The data to be displayed in the table. This is an array of data source objects, one for each row, which will be used by DataTables. Note that this parameter's name can be changed using the ajax option's dataSrc property.
	String error;			 	//Optional: If an error occurs during the running of the server-side processing script, you can inform the user of this error by passing back the error message to be displayed using this parameter. Do not include if there is no error.
	
	public DataTable() {
	}

	public DataTable(Long recordsTotal, Integer recordsFiltered, List<T> data, Integer draw) {
		super();
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
		this.draw = draw;
	}
	
	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
