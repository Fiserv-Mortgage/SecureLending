package com.sl.qa.auto.framework;

import org.openqa.selenium.support.PageFactory;

public class Pages {

	public static HomePage homePage() {
		HomePage homePage = new HomePage();
		
		return homePage;
	}

	public static PathPages pathPages() {
		PathPages pathPages = new PathPages();
		return pathPages;
	}

	public static PathPage loginPathPage() {
		PathPage loginPathPage = new LoginPathPage();
		PageFactory.initElements(Browser.driver, loginPathPage);
		return loginPathPage;
	}
	
}
