package com.akoonu.interview.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.akoonu.interview.Record;
import com.akoonu.interview.SimpleDatabase;
import com.akoonu.interview.Table;

public class SimpleDatabaseImpl implements SimpleDatabase {

	private static Map<String, Table> database;

	private static SimpleDatabaseImpl dbInstance = null;

	private SimpleDatabaseImpl() { 
		database = new HashMap<>();
	}

	public static SimpleDatabaseImpl getInstance() {
		if ( dbInstance == null) {
			dbInstance = new SimpleDatabaseImpl();	
			
		}
		return dbInstance;
	}
	
	public static Map<String, Table> getDataBase(){
		return database;
	}

	@Override
	public void createTable(String tableName, List<String> columns) throws SimpleDatabaseImplException {

		Table newTable = Table.getTableInstance(tableName, columns);
		if ( !checkTableExists( newTable.getTableName() ) ) {
			database.put(tableName, newTable);			
		}
		
		else throw new SimpleDatabaseImplException("Table Already Exists");
	}

	@Override
	public Integer insert(String tableName, Record row) throws SimpleDatabaseImplException {

		Table registeredTable = database.get(tableName);
		Integer recordKey = registeredTable.addRecord(row);
		
		if( recordKey == null )
			throw new SimpleDatabaseImplException("Employee Record Not Inserted!" );
		
		return recordKey;
	}

	@Override
	public void update(String tableName, Integer key, Record row) throws SimpleDatabaseImplException {

		Table registeredTable = database.get(tableName);
		Record record = registeredTable.getRecord(key);

		if( record == null ) {
			
			throw new SimpleDatabaseImplException("No Record with Key - " + key + " Found");
		}
		for (Map.Entry<String, String> columnEntry : row.getFields().entrySet()) {

			record.put(columnEntry.getKey(), columnEntry.getValue());

		}

	}

	@Override
	public void delete(String tableName, Integer key) throws SimpleDatabaseImplException {

		Table registeredTable = database.get(tableName);

		Record row = registeredTable.getRecords().remove(key);
		
		if( row == null ) 
			throw new SimpleDatabaseImplException("Table Data Not Affected!");
	}

	@Override
	public Record selectByKey(String tableName, Integer key) throws Exception {

		Table registeredTable = database.get(tableName);

		return registeredTable.getRecord(key);

	}

	@Override
	public List<Record> select(String tableName, String field, String value) throws SimpleDatabaseImplException {

		Table registeredTable = database.get(tableName);
		Map<Integer, Record> records = registeredTable.getRecords();
		List<Record> matchingRecords = new ArrayList<>();

		for (Map.Entry<Integer, Record> columnEntry : records.entrySet()) {
			Record exitingRecord = columnEntry.getValue();

			if (exitingRecord.get(field).equals(value))
				matchingRecords.add(exitingRecord);
		}
		
		if( matchingRecords.size() == 0 ) 
			throw new SimpleDatabaseImplException("No mathing data found!" );
		
		return matchingRecords;
	}

	public boolean checkTableExists(String tableName) {
		return database.get(tableName) != null;
	}

}
