package com.epam.mantisbt.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.epam.mantisbt.common.AbstractPageObject;
import com.epam.mantisbt.common.Xls_Reader;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 *  Thid is a Base Class for Every Test Class which takes care of preparing test data by reading from xls file.
 *  Also, clening up the resources once the all the tests in the test class finishes execution.
 * </p>
 *
 */
public class BaseTest {
	
	Logger logger;
	String filepath;
	Map<String, String> testdatamap; 
	Xls_Reader xlsreader;
	
	//default constructor
	public BaseTest(){
		
	}
	//Construct which sets test data file
	public BaseTest(String filepath){
		this.filepath = filepath;
		testdatamap = new HashMap<String, String>();
	}
	
	@BeforeTest
	public void setup(){
		//BasicConfigurator.configure();
		logger = Logger.getLogger(this.getClass());
		logger.info("Inside BaseTest setup method...");
		xlsreader = new Xls_Reader(this.filepath);
		testdatamap = xlsreader.readData(0);
	}
	
	@AfterTest
	public void tearDown(){
		logger.info("Inside BaseTest tearDown method. Killing the driver");
		AbstractPageObject.quit();
	}
}
