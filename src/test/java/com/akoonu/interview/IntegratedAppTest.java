package com.akoonu.interview;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
                AmazonS3Test.class,
                AppTest.class 
             })

public class IntegratedAppTest {
	
	public static void main(String[] args) {
	    Result result = JUnitCore.runClasses(AppTest.class, AmazonS3Test.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }
	    
	    
	  }

}