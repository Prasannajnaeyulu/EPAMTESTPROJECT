package com.epam.mantisbt.common.listeners;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import com.epam.mantisbt.common.AbstractPageObject;
/**
 * 
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 * This is the Listener Class of WebDriver Events. RightNow, I wanna capture the screenshot whenever test fails with webdriver specific exceptions.
 * Like, NoSuchElementException, StaleElementReferenceException, UnReachableBrowserException etc.. Hence, I overrided only OnException event method.
 * </p>
 */
public class WebDriverListener extends AbstractWebDriverEventListener {

	Logger logger = Logger.getLogger(WebDriverListener.class);
	
	@Override
	public void onException(Throwable exception, WebDriver driver) {
		logger.error("Got the exception: "+ exception.getMessage());
		logger.info("Taking the Screenshot of current page");
		try {
			AbstractPageObject.takescreenshot("Screenshot_"+System.currentTimeMillis()+".jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
