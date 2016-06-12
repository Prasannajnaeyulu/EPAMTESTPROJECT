package com.epam.mantisbt.pageobject;

import com.epam.mantisbt.common.AbstractPageObject;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 * This is the Page Class for Mantisbt HomePage. It contains all the functionalities that can be performed on this page.
 * As per test needs, right now added the navigation functionalities that takes us to the respective menu item page.
 * </p>
 * 
 */
public class MantisbtHomePage extends AbstractPageObject{
	
	//This enum contains all the menu items in Mantisbt home page
	public enum Menu{
		REVIEWS("Reviews"),
		FILES("Files"),
		TOOLS("Tools");
		
		String value;
		Menu(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	//This enum contains the submenu items of all the menus
	public enum SubMenu{
		SUPPORT("Support"),
		MAILING_LISTS("Mailing Lists"),
		DONATE("Donate");
		
		String value;
		SubMenu(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	/**
	 * <p>
	 *  This method navigates us from Mantisbthomepage to another page
	 *  Specify Null value to the parameter incase it is not available
	 * </p>
	 * @param menuitem
	 * @param submenuitem
	 * @param itemlinktext
	 */
	public void navigateToPage(Menu menuitem, SubMenu submenuitem, String itemlinktext){
		if(menuitem!=null)
			clickLink(menuitem.getValue());
		if(submenuitem!=null)
			clickLink(submenuitem.getValue());
		if(itemlinktext!=null && itemlinktext.isEmpty())
			clickLink(itemlinktext);
	}
	
	public void navigateToPage(Menu menuitem){
		navigateToPage(menuitem,null,null);
	}
	
	public void navigateToPage(Menu menuitem, SubMenu submenuitem){
		navigateToPage(menuitem,submenuitem,null);
	}

	public static MantisbtHomePage getInstance(){
		return new MantisbtHomePage();
	}
	
	@Override
	public void assertInPage() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void navigateToPage() {
	   		
	}

}
