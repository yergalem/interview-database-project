package com.akoonu.interview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table {

	String tableName;
	//List<Table> tables;
	List<String> columns;
	Map<Integer, Record> records;

	public Table(String tableName, List<String> columns) {
		this.tableName = tableName;
		this.columns = columns;
		this.records = new LinkedHashMap<>();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<Integer,Record> getRecords() {
		return records;
	}

	public void setRecords(Map<Integer,Record> records) {
		this.records = records;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public void addColumn(String column) {

		columns.add(column);
	}

	public Integer addRecord( Record record) {
        
		int size = records.size();
		record.setKey( size );
		
		records.put( size, record );

		return  records.containsKey(size )? record.getKey() : null;
	}

	public static Table getTableInstance(String tableName, List<String> columns) {
		
		return new Table(tableName, columns);
	}
	
	public Record getRecord( Integer key ) {
		
		Record existingRecord = records.get(key);
		return existingRecord;
	}
	
   // Checks if new record matches with existing record
	public boolean containsRecord(Record row) {
		boolean recordExists  = false;
		
		for (Map.Entry<Integer, Record> recordEntry : records.entrySet()) {
             if( recordEntry.getValue().equals( row )  ) {
            	 recordExists = true;
            	 break;
             }
            	 
		}
		
		return recordExists;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
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
		Table other = (Table) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

}
