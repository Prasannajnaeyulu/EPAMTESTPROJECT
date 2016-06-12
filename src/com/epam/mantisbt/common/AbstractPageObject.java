package com.epam.mantisbt.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

/**
 * @author Prasannanjaneyulu padavala 
 * <p>
 * This page is the base page for all the page objects. It contains the code to create driver object,<br> 
 * To load application environment properties and all the synchronized methods to perform operations/actions<br>
 * on Application opened in a browser. Like click,type,waitforelement fluentwait and explicitwait etc..
 * </p>
 */
public abstract class AbstractPageObject {
	protected static WebDriver driver;
	public static Properties envprops;
	static Logger logger = Logger.getLogger(AbstractPageObject.class);
	public static boolean isBrowserOpen;
	public static Actions actions;
	private static WebDriverEventListener webdrivereventlistener = new com.epam.mantisbt.common.listeners.WebDriverListener();

	//These Properties should exist before the creation of driver object. Hence, Loading these as soon as class loads.
	//No need to wait till object creation
	static{
		envprops = new Properties();
		loadprops("environment.properties");
	}

	public static void loadprops(String filename){
		try {
			envprops.load(new FileReader("environment.properties"));
		} catch (FileNotFoundException e) {
			logger.error("environment.properties was not found hence failed to load");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AbstractPageObject(){
		if(!isBrowserOpen)
			openBrowser();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}


	private void registereventlisteners(WebDriver driver) {
		EventFiringWebDriver eventfiringdriver = new EventFiringWebDriver(driver);
		logger.info("registering webdriverListener class");
		eventfiringdriver.register(webdrivereventlistener);
		driver = eventfiringdriver;
	}

	private static void unregistereventlisteners(WebDriver driver) {
		EventFiringWebDriver eventfiringdriver = new EventFiringWebDriver(driver);
		logger.info("unregistering webdriverListener class");
		eventfiringdriver.unregister(webdrivereventlistener);
	}

	/**
	 * <p>
	 * Fluent wait with fixed/constant time 30 seconds <br>
	 * 
	 * Use this method if you want to wait for an element for a maximum of 30 seconds.
	 * Polling for every 5 seconds. Whenever it finds an element it will returns that. 
	 * If not, it will return null.
	 * If you want to customize the time use
	 * {@link #waitForElement(By bylocator, int timeinseconds) waitForElement(bylocator,timeinseconds)}
	 * </p>
	 */
	public static WebElement waitForElement(By bylocator){
		return waitForElement(bylocator, 30);
	}

	/**
	 * <p>
	 * Use this method if you want to wait for an element for a maximum of 30 seconds.
	 * Polling for every 5 seconds. Whenever it finds an element it will returns that. 
	 * If not, it will return null.
	 * If you want to customize the time use
	 * {@link #waitForElement(By bylocator, int timeinseconds) waitForElement(bylocator,timeinseconds)}
	 * </p>
	 */
	public static WebElement waitForElementVisibility(By bylocator){
		return waitForElementVisibility(bylocator, 30);
	}

	/**
	 * <p>
	 * Fluent Wait with Timebound in seconds specified by the caller<br>
	 * 
	 * Use this method if you want to wait for an element for a maximum of timeinseconds;<b>
	 * Polling for every 5 seconds. Whenever it finds an element it will returns the WebElement.<b>
	 * If not, it will return null.
	 * </p>
	 */
	public static WebElement waitForElement(final By bylocator, int timeinseconds){
		WebElement webelement=null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).
		withTimeout(timeinseconds, TimeUnit.SECONDS).
		pollingEvery(5, TimeUnit.SECONDS).
		ignoring(NoSuchElementException.class);

		webelement = wait.until(new Function<WebDriver,WebElement>(){

			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(bylocator);
			}

		});
		return webelement;
	}

	/**
	 * 
	 * @param bylocator
	 * @param timeunitinseconds
	 * @return WebElement
	 */
	public static WebElement waitForElementVisibility(By bylocator, int timeunitinseconds){
		WebElement we=null;
		WebDriverWait wait = new WebDriverWait(driver,timeunitinseconds);

		try{
			we = wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
		}
		catch(Exception e){
			logger.error("Element not visible in the given time"+timeunitinseconds);
		}
		return we;
	}
	//explicit wait
	public WebElement waitForElementToBeClickable(By bylocator, int timeunitinseconds){
		WebElement we=null;
		WebDriverWait wait = new WebDriverWait(driver,timeunitinseconds);

		try{
			we = wait.until(ExpectedConditions.elementToBeClickable(bylocator));
		}
		catch(Exception e){
			logger.error("Element not clickable in the given time"+timeunitinseconds);
		}
		return we;
	}

	//explicit wait with constant timeout 20 seconds
	public WebElement waitForElementToBeClickable(By bylocator){
		return waitForElementToBeClickable(bylocator, 20);	
	}


	public void typeEditBox(String editboxidentifier, String texttotype){
		String xpath = "//input[@id='"+editboxidentifier+"' or @name='"+editboxidentifier+"']";
		WebElement editbox = waitForElementVisibility(By.xpath(xpath));
		if(editbox!=null)
		{
			editbox.clear();
			editbox.sendKeys(texttotype);
		}
		else
		{
			logger.error("Failed to find edit box with the xpath: "+xpath);
			return;
		}
	}

	public void clickButton(String buttonidentifier){
		String xpath = "//button[@id='"+buttonidentifier+"' or text()='"+buttonidentifier+"' or @name='"+buttonidentifier+"' or " +
		"@value='"+buttonidentifier+"']";

		WebElement button = waitForElementVisibility(By.xpath(xpath));
		if(button!=null)
			button.click();
		else
		{
			logger.error("Failed to find button with the xpath: "+xpath);
			return;
		}
	}

	public void selectByNameorID(String identifier, String valuetoselect){
		String xpath = "//select[@name='"+identifier+"']";
		WebElement selectelement = waitForElementVisibility(By.xpath(xpath));
		if(selectelement!=null)
		{
			Select select = new Select(selectelement);
			select.selectByVisibleText(valuetoselect);
		}
	}

	public void clickLink(String linktext){
		WebElement link = waitForElementToBeClickable(By.linkText(linktext));
		if(link!=null)
			link.click();
		else
			logger.error("Unable to find the link with the linktext: "+linktext);
	}

	public void clickByPartialLinkText(String partiallinktext){
		WebElement link = waitForElementToBeClickable(By.partialLinkText(partiallinktext));
		if(link!=null)
			link.click();
		else
			logger.error("Unable to find the link with the linktext: "+partiallinktext);
	}

	public boolean isTextPresent(String texttofind){
		int count=0;
		while(!driver.getPageSource().contains(texttofind) && count++<3){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count>=3){
			return false;
		}

		return true;
	}

	public static void moveToElement(WebElement element){
		actions.moveToElement(element).build().perform();
	}

	public static  boolean waitForPageToLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoads = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Boolean)((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		return wait.until(pageLoads);
	}

	private void openBrowser(){
		String browsertype = envprops.getProperty("browser");
		if(browsertype!=null && !browsertype.isEmpty()){
			if(browsertype.equalsIgnoreCase("firefox"))
				driver = new FirefoxDriver();
			if(browsertype.equalsIgnoreCase("internetexplorer")){
				System.setProperty("webdriver.ie.driver", envprops.getProperty("iedriverserverpath"));
				driver = new InternetExplorerDriver();
			}
			if(browsertype.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", envprops.getProperty("chromedriverserverpath"));
				driver = new ChromeDriver();
			}
		}
		else{
			logger.error("Browser property must not be empty. Please specify it in environment.properties file");
			return;
		}
		String url = envprops.getProperty("url");
		if(url!=null && !url.isEmpty()){
			registereventlisteners(driver);
			driver.get(url);
			driver.manage().window().maximize();
			actions = new Actions(driver);
			isBrowserOpen=true;
		}
		else{
			logger.error("URL property must not be empty. Please specify it in environment.properties file");
			return;
		}
	}

	public static void quit() {
		// TODO Auto-generated method stub
		logger.info("inside quite method...");
		unregistereventlisteners(driver);
		driver.quit();
		isBrowserOpen = false;
	}

	public static void takescreenshot(String filename) throws IOException{
		String outputfile = System.getProperty("user.dir")+"/Screenshots/"+filename;
		logger.info("Taking the screenshot into a file: "+outputfile);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(outputfile));
	}

	public static void highlightElement(WebElement element){
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px groove green'", element);
	}

	public abstract void navigateToPage();
	public abstract void assertInPage();

}
