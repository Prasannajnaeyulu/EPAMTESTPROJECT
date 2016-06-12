package com.epam.mantisbt.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.epam.mantisbt.common.AbstractPageObject;

/**
 *
 * @author Prasannanjaneyulu Padavala
 * <p>
 * This is the Page Class for MantisBTHomePages->Files Page. It contains all the functionalities that can be performed on google search page.
 * As per the test needs i added only the navigation functionality which takes us to the Mantis-stable page
 * </p>
 */
public class MantisbtFilesPage extends AbstractPageObject{
	
	public void navigateTo(String linktext){
		String xpath = "//a[contains(.,'"+linktext+"')]";
		WebElement we = waitForElementToBeClickable(By.xpath(xpath));
		if(we!=null)
			we.click();
	}
	
	public static MantisbtFilesPage getInstance(){
		return new MantisbtFilesPage();
	}
		

	@Override
	public void assertInPage() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void navigateToPage() {
		
	}

}
