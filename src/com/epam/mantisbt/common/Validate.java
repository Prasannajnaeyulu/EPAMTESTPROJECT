package com.epam.mantisbt.common;

import junit.framework.Assert;

import static com.epam.mantisbt.common.AbstractPageObject.*;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 *  This is the Helper class to validate the whether current page driver is on is an expected page or not.
 *  Also, We will grow this class by adding other methods like validating two String equality, two String arrays equality,
 *  two Lists ot sets equality, string matches etc.. 
 * </p>
 *
 */
 
public class Validate{
	
	/**
	 * This method will assert current page title equals to <expectedpagetitle>.
	 * It Waits for a maximum of 30 seconds to check whether current driver page title equals to expectedpagetitle or not.
	 * If not, it throws an Assertionerror after the 30 seconds time elapsed.
	 * @param expectedpagetitle
	 */
	public static void validatePage(String expectedpagetitle){
		String actualtitle="";
		int count=0;
		//This piece of code returning immediately though page is still loading
		/*boolean ispageloaded = waitForPageToLoad(driver);
		if(ispageloaded)
			logger.info("Page Successfully Loaded");*/
		do{
			actualtitle = driver.getTitle();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!actualtitle.equalsIgnoreCase(expectedpagetitle) && count++<6);
		//This piece of code returning empty text 
		/*WebElement titleelement = waitForElement(By.xpath("//head/title"));
		moveToElement(titleelement);
		highlightElement(titleelement);
		
		if(titleelement!=null)
			actualtitle = titleelement.getText();*/
			
		Assert.assertEquals("Failed to find the expected page", expectedpagetitle, actualtitle);
	}
}
