package com.epam.mantisbt.common;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.epam.mantisbt.common.AbstractPageObject.*;

/**
 * 
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 *  This is the Helper class which parsed the webtable in a page. It has most general purpose methods. Like,
 *  Getting table headers, rowcount, colummcount and entire data of a specific cell by means of column locator etc..
 * </p>
 *
 */

public class TableHelper{
	static Logger logger = Logger.getLogger(TableHelper.class);

	public static WebElement getTable(String tableidentifier){
		String xpath = "//table[contains(@class,'"+tableidentifier+"')]";
		WebElement table = driver.findElement(By.xpath(xpath));
		if(table!=null){
			return table;
		}
		else
		{
			logger.error("Table not found");
			return null;
		}
	}

	public static List<String> getTableHeadersText(String tableidentifier){
		List<String> lsHeadersText = new ArrayList<String>();
		List<WebElement> lstableheaders = getTableHeaderElements(tableidentifier);
		for(WebElement header: lstableheaders){
			lsHeadersText.add(header.getText());
		}
		return lsHeadersText;
	}

	public static List<WebElement> getTableHeaderElements(String tableidentifier){
		String xpath = "//table[@id='"+tableidentifier+"' or contains(@class,'"+tableidentifier+"')]";
		List<WebElement> lstableheaders = new ArrayList<WebElement>();
		WebElement table = waitForElementVisibility(By.xpath(xpath));
		moveToElement(table);
		if(table!=null)
			lstableheaders = driver.findElements(By.xpath(xpath+"/thead//th"));
		else
			logger.error("Table not found");

		return lstableheaders;
	}

	public static int getTableHeaderIndex(String tableidentifier, String headername){
		List<String> lstableheaders = getTableHeadersText(tableidentifier);
		return getTableHeaderIndex(headername, lstableheaders);
	}

	public static int getTableHeaderIndex(String headername, List<String> lstableheaders){
		int i=1;
		for(String header:lstableheaders){
			if(header.equalsIgnoreCase(headername))
				return i;
			i++;
		}
		return -1;
	}

	public static int getTableRowsCount(String tableidentifier){
		WebElement table = getTable(tableidentifier);
		if(table!=null){
			List<WebElement> tablerows = table.findElements(By.xpath("//tbody/tr"));
			if(tablerows.isEmpty())
				return 0;
			else
				return tablerows.size();
		}
		else
			logger.error("Table not found");

		return 0;
	}

	public static int getTableColumnsCount(String tableidentifier){
		WebElement table = getTable(tableidentifier);
		if(table!=null){
			List<WebElement> tablecols = table.findElements(By.xpath("//thead/tr/th"));
			if(tablecols.isEmpty())
				return 0;
			else
				tablecols.size();
		}
		else
			logger.error("Table not found");

		return 0;
	}
	public static String getTableCellData(String tableidentifier,int row, int col){
		WebElement table = getTable(tableidentifier);
		if(table!=null){
			WebElement tablecell = table.findElement(By.xpath("//tbody/tr["+row+"]/td["+col+"]"));
			if(tablecell!=null)
				return tablecell.getText();
		}

		return "";
	}
	
	/**
	 * <p>
	 * Get the Entire Column data for all the rows identified by the By locator <columnudentifier>
	 * </p> 
	 * @param columnidentifier
	 * @return
	 */
	public static List<String> getEntireColumnData(By columnidentifier){
		List<String> lscolumndata =  new ArrayList<String>();
		List<WebElement> lscolumnelements = driver.findElements(columnidentifier);
		for(WebElement column: lscolumnelements){
			logger.debug("Adding column data to the list"+column.getText());
			lscolumndata.add(column.getText().trim());
		}
		return lscolumndata;
	}
}
