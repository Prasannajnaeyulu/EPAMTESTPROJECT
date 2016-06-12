package com.epam.mantisbt.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.epam.mantisbt.common.AbstractPageObject;
import com.epam.mantisbt.common.TableHelper;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 * This is the Page Class for Mantisbt Stable Page. It contains all the functionalities that can be performed on this page.
 * Added the Functionality which will get the version(Name) of the File most downloaded and its count.
 * 
 */
public class MantisStablePage extends AbstractPageObject {
	Logger logger = Logger.getLogger(MantisStablePage.class);
	
	/**
	 * <p>
	 * This Method will 
	 * 1. First Get the Headers of Mantis-Stable table.
	 * 2. Findout the Column number(index) of 'Name' and 'Downloads/Week' Columns
	 * 3. Get the Name and Downloads/week Column Data of all the Rows and store these values into list. So that, we won't loose the order.
	 * 4. Calculate which one has highest downloads count and return that version
	 * </p>
	 * @return
	 */
	public String getMostDownloadedProjectFileVersion(){
		String tableidentifer = "files_list";
		List<String> lstableheaderstext = TableHelper.getTableHeadersText(tableidentifer);

		int namecolumnindex = TableHelper.getTableHeaderIndex("Name", lstableheaderstext);
		int downloadsperweekcolumnindex = TableHelper.getTableHeaderIndex("Downloads / Week", lstableheaderstext);
		
		By namecolumndata = By.xpath("//tbody/tr/th["+namecolumnindex+"]//a");
		List<String> lsnamecolumndata = TableHelper.getEntireColumnData(namecolumndata);
		
		By downloadsperweekcolumndata = By.xpath("//tbody//td["+(downloadsperweekcolumnindex-1)+"]");
		List<String> lsdownloadsperweekcolumndata = TableHelper.getEntireColumnData(downloadsperweekcolumndata);
		
		int previoushighestFileDownloadcount=0;
		int index = -1;
		for(String s:lsdownloadsperweekcolumndata){
			s= s.replaceAll(",", "");
			int currentFileDownloadcount = Integer.parseInt(s);
			if(currentFileDownloadcount > previoushighestFileDownloadcount){
				previoushighestFileDownloadcount = currentFileDownloadcount;
				index++;
				logger.debug("Current Most Downloaded Count: "+ previoushighestFileDownloadcount);
				logger.debug("Current Most Downloaded Count File Version: "+lsnamecolumndata.get(index));
			}
		}	
		logger.info("Most Downloaded File Name: "+ lsnamecolumndata.get(index));
		logger.info("Most Downloaded File Count: "+ previoushighestFileDownloadcount);
		System.out.println("Most Downloaded File Name: "+ lsnamecolumndata.get(index));
		System.out.println("Most Downloaded File Count: "+ previoushighestFileDownloadcount);
		return lsnamecolumndata.get(index);
	}

	@Override
	public void assertInPage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void navigateToPage() {
		// TODO Auto-generated method stub

	}

}
