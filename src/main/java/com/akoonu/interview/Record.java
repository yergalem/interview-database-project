package com.akoonu.interview;

import java.util.HashMap;

public class Record {

	private Integer key;

	private final HashMap<String, String> fields = new HashMap<>();

	public Record() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public HashMap<String, String> getFields() {
		return fields;
	}

	public String get(String fieldName) {
		return fields.get(fieldName);
	}

	public void put(String fieldName, String value) {
		if (fieldName != null) {
			fields.put(fieldName, value);
		}
	}

	@Override
	public String toString() {
		return "Record [key=" + key + ", fields=" + fields + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}
	
	
	
	

}
