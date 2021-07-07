package com.sl.qa.auto.test.suite.testsuite;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.sl.qa.auto.framework.Browser;
import com.sl.qa.auto.framework.Pages;
import com.sl.qa.auto.framework.PathPage;

public class UnitTest {

	@Test
	public void canGoToHomePage() {
		Pages.homePage().goTo();
		Assert.assertTrue(Pages.homePage().isAt());
	}
	
	@Test
	public void canGoToJavaPathPage() {
		Pages.pathPages().goTo();
		PathPage pathPage = Pages.pathPages().getPathPage(1);
		pathPage.goTo();
		Assert.assertTrue(pathPage.isAtPathPage("1"));
	}
	
	@AfterClass
	public static void cleanUp() {
		Browser.close();
	}

}
