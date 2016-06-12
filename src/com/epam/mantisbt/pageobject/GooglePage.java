package com.epam.mantisbt.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.epam.mantisbt.common.AbstractPageObject;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 * This is the Page Class for Google Search Page. It contains all the functionalities that can be performed on google search page.
 * As per our requirement, I added only the necessary functions like doSearch and findAndClickLinkInSearchResults.
 * </p>
 *
 */
public class GooglePage extends AbstractPageObject {
	
	@FindBy(how=How.NAME, name="q")
	WebElement searchtextbox;
	
	@FindBy(how=How.NAME, name="btnG")
	WebElement searchbutton;
	
	Logger logger = Logger.getLogger(GooglePage.class);
	
	public void doSearch(String searchtext){
		typeEditBox("q", searchtext);
		clickButton("btnG");
	}
	
	public void findAndClickLinkInSearchResults(String linktext){
		WebElement linkinsearchresults = waitForElement(By.linkText(linktext));
		if(linkinsearchresults!=null){
			moveToElement(linkinsearchresults);
			clickLink(linktext);
		}
		else{
			logger.error("Unable to find a link with the text"+linktext);
			return;
		}
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
