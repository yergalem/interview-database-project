package com.akoonu.interview;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.akoonu.interview.impl.SimpleDatabaseImpl;
import com.akoonu.interview.impl.SimpleDatabaseImplException;

/**
 *  Tests the CRUD operations .
 */
public class AppTest {

	SimpleDatabaseImpl employeeDB;
	String[] data;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
    @Before
    public void setUp() {    	
    	employeeDB = SimpleDatabaseImpl.getInstance();
    	data  =  new String[] { "ID", "FirstName", "LastName" };
    }
	
	@Test
	public void createTable() {
		
		try {
			employeeDB.createTable("AppTestNewTable", Arrays.asList(data) );
			
			Assert.assertNotNull( SimpleDatabaseImpl.getDataBase().get("AppTestNewTable") );
			
		} catch (SimpleDatabaseImplException e) {
			
			/* thrown.expect(SimpleDatabaseImplException.class);
			   thrown.expectMessage("Table Creation Failed");  */
		}
		
	    
	}

	 @Test 
	 public void insertEmployeeRecord() { 
		 
		 Record record = new Record(); 
		 
		 record.put("ID", "AKN0001");
		 record.put("FirstName", "Yergalem");	
		 record.put("LastName", "Kahsay"); 
		 
		 Record record2 = new Record(); 
		 
		 record2.put("ID", "AKN0002");
		 record2.put("FirstName", "Alexandar");	
		 record2.put("LastName", "Bill"); 

		try {
			employeeDB.createTable("AppTestNewTable", Arrays.asList(data) );
			Integer recordKey  = employeeDB.insert("AppTestNewTable", record );
			Integer recordKey2 = employeeDB.insert("AppTestNewTable", record2 );
			
			Assert.assertNotNull(recordKey);
			Assert.assertNotNull(recordKey2);

		} catch (SimpleDatabaseImplException e) {

			/*
			 * thrown.expect(SimpleDatabaseImplException.class);
			 * thrown.expectMessage("Table Insertion Failed");
			 */
		}			
		 
	}
	 /**
	  *  Updates Record Employee Record. Unit tests to update record of Alexandar.
	  * 
	  *
	  */
	 @Test 
	 public void updateEmployeeRecord() { 
		 	
		 Record record = new Record(); 
		 
		 record.put("ID", "AKN0001");
		 record.put("FirstName", "Alexandar");	
		 		
		try {
			
			employeeDB.update("AppTestNewTable", 0, record);
			record = SimpleDatabaseImpl.getDataBase().get("AppTestNewTable").getRecords().get(0);
			Assert.assertNotEquals("Yergalem", record.get("FirstName"));
			Assert.assertEquals("Kahsay", record.get("LastName"));
					

		} catch (SimpleDatabaseImplException e) {

			/*
			 * thrown.expect(SimpleDatabaseImplException.class);
			 * thrown.expectMessage("Table Creation Failed");
			 */
		}			
		 
	}
	 
	 @Test 
	 public void deleteEmployeeRecord() { 
		 		 		
		try {
			
			employeeDB.delete("AppTestNewTable", 1 );
			Table tbl = SimpleDatabaseImpl.getDataBase().get( "AppTestNewTable");
			
			Assert.assertNull( tbl.getRecords().get(1) );

		} catch (SimpleDatabaseImplException e) {
			
			 thrown.expect(SimpleDatabaseImplException.class);
			 thrown.expectMessage("Table Creation Failed");
			 
		}			
		 
	}
	 
	 
	 @Test 
	 public void selectEmployeeRecord() { 
		 		 		
		try {
			
			List<Record> recordKey        = employeeDB.select("AppTestNewTable", "FirstName","Alexandar" );
			/*List<Record> updatedRecordKey = employeeDB.select("AppTestNewTable", "FirstName","Yergalem" );
			  List<Record> anotherRecordKey = employeeDB.select("AppTestNewTable", "FirstName","Sara" );*/

			Assert.assertNotNull(recordKey);
			Assert.assertEquals("Kahsay",  recordKey.get(0).get("LastName"));     /* Kept as is */
			Assert.assertEquals("Alexandar",  recordKey.get(0).get("FirstName")); /* Modified by update query */
			Assert.assertEquals(2, recordKey.size());
			
			/*Assert.assertNull(updatedRecordKey);
			Assert.assertNotNull(anotherRecordKey);*/

		} catch (SimpleDatabaseImplException e) {

			/*
			 * thrown.expect(SimpleDatabaseImplException.class);
			 * thrown.expectMessage("Table Creation Failed");
			 */
		}			
		 
	}
	 
	 

}
