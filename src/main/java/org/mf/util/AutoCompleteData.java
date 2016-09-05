package org.mf.util;

public class AutoCompleteData {
	
	private String label;
	private String value;
	private int id;
	
	public AutoCompleteData(String label, int value) {
		this(label, label, value);
	}
	
	public AutoCompleteData(String label, String value, int id) {
		super();
		this.label = label;
		this.value = value;
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
