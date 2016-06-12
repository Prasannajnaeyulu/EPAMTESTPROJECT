package com.epam.mantisbt.tests;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.epam.mantisbt.common.Validate;
import com.epam.mantisbt.pageobject.GooglePage;
import com.epam.mantisbt.pageobject.MantisStablePage;
import com.epam.mantisbt.pageobject.MantisbtFilesPage;
import com.epam.mantisbt.pageobject.MantisbtHomePage;
import com.epam.mantisbt.pageobject.MantisbtHomePage.Menu;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * This is a Tests Class File which contains all the tests related to Mantis BT Files Page
 *
 */

public class MantisbtFilesTests extends BaseTest{
	
	WebDriver driver;
	
	public MantisbtFilesTests(){
		super("classes\\MantisbtTestData.xls");
	}

	@Test
	public void validateMantisbtMostDownloadedProjectFileVersion(){
		//Search and click link of mantisbt in google search page
		GooglePage googlepage = new GooglePage();
		googlepage.doSearch("mantisbt");
		googlepage.findAndClickLinkInSearchResults(testdatamap.get("mantisHomepagetitle"));
		Validate.validatePage(testdatamap.get("mantisHomepagetitle"));
				
		//Navigate to Files Page from MantisBT page
		MantisbtHomePage.getInstance().navigateToPage(Menu.FILES);
		
		Validate.validatePage(testdatamap.get("mantisFilesPagetitle"));
		
		//Navigate to mantis-stable page
		MantisbtFilesPage.getInstance().navigateTo("mantis-stable");
		
		Validate.validatePage(testdatamap.get("mantis-stablepagetitle"));
		
		MantisStablePage mantisstablepage = new MantisStablePage();
		String actualmostdownloadedfileversion = mantisstablepage.getMostDownloadedProjectFileVersion();
		
		Assert.assertEquals("Actual most downloaded files version not matched with expected", "1.2.19", actualmostdownloadedfileversion);
	}

}
