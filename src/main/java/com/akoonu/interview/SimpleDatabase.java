package com.akoonu.interview;

import java.util.List;

public interface SimpleDatabase {

	 
	
	/*
	 * Creates a new 'table' in the database and sets the restriction on which fields/columns
	 * can be saved to the database.
	 */
	void createTable (String tableName, List<String> columns) throws Exception;
	
	/*
	 * Inserts a new record to the database and returns a new unique key for the record. 
	 * 
	 * If the inserted record includes fields not allowed by the table definition, an error will result.
	 */
	Integer insert(String tableName, Record row) throws Exception;
	
	/*
	 * Updates an existing record in the database. For any fields in the input row, the 
	 * existing record in the database will be updated. Any fields not included in the input 
	 * row should remain unchanged.
	 */
	void update(String tableName, Integer key, Record row) throws Exception;
	
	/*
	 * Deletes the record at the specified key.
	 */
	void delete(String tableName, Integer key) throws Exception;
	
	/*
	 * Retrieves the record at the specified key.
	 * 
	 * If there is no record for the specified key this will return NULL.
	 */
	Record selectByKey(String tableName, Integer key) throws Exception;
	
	/*
	 *  Searches the database for records in which the specified field is equal to the specified value.
	 *  
	 *  If null is passed for the value parameter, any records for which that field is not set will be returned.
	 */
	List<Record> select(String tableName, String field, String value) throws Exception; 
}
