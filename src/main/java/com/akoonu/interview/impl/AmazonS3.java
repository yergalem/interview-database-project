package com.akoonu.interview.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.akoonu.interview.Record;
import com.akoonu.interview.Table;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AmazonS3 {
	
	private static final String TABLE_NAME = "EmployeeData";
		
	/**
	 * Stores Columns of a Table
	 * */
	private static String[] DBSCHEMA;
	
	public static void main(String[] args)  {
	
		 try {
			Table tbl = fetchEmployeeDataFromAWS();
			
			for (Map.Entry<Integer, Record> columnEntry : tbl.getRecords().entrySet()) {
				Record exitingRecord = columnEntry.getValue();

                System.out.println( exitingRecord );
			}
		
		 } catch (Exception e) {
			System.out.println( e.getMessage());
		}
		
	}
	
	public static String getTableName() {
		return TABLE_NAME;
	}

	public static Table fetchEmployeeDataFromAWS() throws SimpleDatabaseImplException {
		
		boolean tblCreated = false;
		Map<Integer, Record> empDataTbl = null;
		// credentials object identifying user for authentication
		AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJN2EXRGMZDKCEBKQ", 
				"5vpWW71HmBT82koUcDK/x6T99HfWHQlNgNqRY0+a");
		
		
		AmazonS3Client s3Client = new AmazonS3Client( credentials);       
		S3Object object = s3Client.getObject(new GetObjectRequest("akoonu-engineering", "Interview-Contacts.csv"));
		
		BufferedReader reader = new BufferedReader(new 
        		InputStreamReader( object.getObjectContent() ));
		// Process the objectData stream.
		try {
			
			String line , cvsSplitBy = ",";
			int counter = 1;
			while ((line =  reader.readLine()) != null) {
			    String[] employeeRecord = line.split( cvsSplitBy );
			    
			    if( counter == 1 )	
			    	tblCreated = createTable( employeeRecord );
			    else if( counter > 1 )
			    	empDataTbl = populateTable( employeeRecord );
                ++counter;

            }
			
			if( !tblCreated || empDataTbl == null)			 
			    throw new SimpleDatabaseImplException("Failure: Table not populated from remote AmazonWS CSV file");
			
		} catch (  IOException e) {
			 e.printStackTrace();
		} finally {
			
			try {
				reader.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		return SimpleDatabaseImpl.getDataBase().get(TABLE_NAME);
		
	}

	public static Map<Integer, Record> populateTable(String[] employeeRecord) {
		SimpleDatabaseImpl employeeDB = SimpleDatabaseImpl.getInstance();		
		Record row = new Record();
		Table dataTable = null;
		
		for (int i = 0; i < DBSCHEMA.length; i++) {
			row.put( DBSCHEMA[i], employeeRecord[i] );
		}
		
		try {
		    dataTable = SimpleDatabaseImpl.getDataBase().get(TABLE_NAME);
			
			if( !dataTable.containsRecord(row) )
				employeeDB.insert( TABLE_NAME , row );
			else  throw new SimpleDatabaseImplException("Duplicate Employee Entry");
			
		} catch (Exception e) {
			
			System.out.println( "AmazonS3.populateTable: "+ e.getMessage() );
		}
		
		return dataTable.getRecords();
	}

	public static boolean createTable(String[] headers) throws SimpleDatabaseImplException {
		
		SimpleDatabaseImpl employeeDB = SimpleDatabaseImpl.getInstance();
		DBSCHEMA = headers;
		if( !tableExist() ) {
		   employeeDB.createTable( TABLE_NAME, Arrays.asList( DBSCHEMA ));
		   
		   return SimpleDatabaseImpl.getDataBase().get(TABLE_NAME) != null ;
		}
		
		else  throw new SimpleDatabaseImplException("Table already Exists");
		         
 	}

	private static boolean tableExist() {
		SimpleDatabaseImpl employeeDB = SimpleDatabaseImpl.getInstance();		
		return employeeDB.checkTableExists(TABLE_NAME);
	}
	
	
	public List<Record> selectEmployeeByDepartment( String deptStr, String deptVal ) throws SimpleDatabaseImplException {

		Table employeeDataTbl = fetchEmployeeDataFromAWS();
		Map<Integer,Record> records  = employeeDataTbl.getRecords();
		List<Record> matchingRecords = new ArrayList<>();
		
		for (Map.Entry<Integer, Record> columnEntry : records.entrySet()) {
			Record exitingRecord = columnEntry.getValue();

			if ( exitingRecord.get( deptStr ).equalsIgnoreCase(deptVal) )
				matchingRecords.add(exitingRecord);
		}
		
		if( matchingRecords.size() == 0 ) 
			throw new SimpleDatabaseImplException("No mathing data found!" );
		
		return matchingRecords;
	}
}
	