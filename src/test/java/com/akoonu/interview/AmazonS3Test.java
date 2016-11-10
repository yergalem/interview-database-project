package com.akoonu.interview;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.akoonu.interview.impl.AmazonS3;
import com.akoonu.interview.impl.SimpleDatabaseImplException;

/**
 * Unit test for simple App.
 */
public class AmazonS3Test {

	/**
	 *   TEST INDIVIDUALLY THE COMMENTED ONES 
	 *   FOR TABLE ALREADY EXISTS IS WHAT YOU GET BECAUSE OF DUPLICATE TABLE CREATION	
	 *
	 */
	/*    
    @Test
	public void createTableFromCSVFetchedDataHeader() {
    	
    	try {
			boolean tblCreated = AmazonS3.createTable(new String[]{"ID","FirstName","LastName","Title","Department",
					                                               "LastUpdatedDate","Industry"});
			
			
			Assert.assertTrue( SimpleDatabaseImpl.getDataBase().containsKey(AmazonS3.getTableName()  ));
			
		} catch (Exception e) {
			System.out.println( e.getMessage() );
		}
    	
    }
	@Test
	public void populateCSVFetchedEmployeeData_ToInMemoryDBTable() {
		
		 Map<Integer, Record> empDataTbl = AmazonS3.populateTable( new String[] { "50338430","Thomas","Campbell",
				                           "Content Marketing Specialist", "Marketing","2013-10-28","Other"} );
		 
		 Assert.assertNotNull(empDataTbl);
		 
	}
	
	@Test
	public void fetchEmployeeDataFromAWS() {
		Table tblFromCSV;
		
    	try {
    		tblFromCSV = AmazonS3.fetchEmployeeDataFromAWS();			
			
			Assert.assertTrue( SimpleDatabaseImpl.getDataBase().containsKey(tblFromCSV.getTableName()  ));
			Assert.assertNotNull(tblFromCSV);
			Assert.assertNotEquals(0, tblFromCSV.getRecords().size());
		    
			
		} catch (Exception e) {
			System.out.println( e.getMessage() );
		}
    	
    }  */
	
	@Test 
	 public void selectEmployeeByDepartment()  { 
		  AmazonS3  employeeData = new AmazonS3();
		  List<Record> recordKey;
		  
		try {
			
			recordKey = employeeData.selectEmployeeByDepartment("Department", "Marketing" );
			Assert.assertNotNull(recordKey);
			
			ListIterator<Record> lstItr = recordKey.listIterator();
            Record contactRecord;
              while( lstItr.hasNext()  ) {
            	  
            	  contactRecord = lstItr.next();
            	  System.out.println(contactRecord);
              }
						
		} catch (SimpleDatabaseImplException e) {

              System.out.println( "AmazonS3.selectEmployeeByDepartment:"+ e.getMessage());
		}
		
	}

}
