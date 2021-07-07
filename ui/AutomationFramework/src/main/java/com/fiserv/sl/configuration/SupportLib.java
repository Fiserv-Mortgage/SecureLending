package com.fiserv.sl.configuration;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fiserv.sl.testrunner.TestCaseRunner;


public class SupportLib {
	static String CHAR_LIST = "abcdefg123450hijklmn1567890opqrstuvw12390xyzABCDEFGHI1234567890JKLMNO12390PQRSTUV1234567890WXYZ67890";
	static String STRLIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static String NUM_LIST = "1234234567890234567890234567890789023456";
	static String SPL_LIST = "abcde!@#$@%fghijklmnopqrst!@#$!@uvyzAL&^%$EMNUV&*$%^WXYZ";
	static ChromeOptions options = new ChromeOptions();

	/**
	 * Function to extract double value from string page test Fields required:
	 */
	public static double ExtractDouble(String value) {
		Double f = Double.valueOf(value.replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
		return f;
	}

	/**
	 * Function to extract Int value from string page test Fields required:
	 */
	public static int ExtractInt(String frompage) {
		String fetchfrompage = frompage;
		fetchfrompage = fetchfrompage.replaceAll("[^\\d.]+|\\.(?!\\d)", "");
		int rangefromwebpage;
		if (fetchfrompage.isEmpty()) {
			rangefromwebpage = 0;
		} else {
			rangefromwebpage = Integer.valueOf(fetchfrompage);
		}
		return rangefromwebpage;
	}

	
	/**
	 * Function to Highlight the element on the page
	 * 
	 */
	public static void ElementHighlighter(WebElement we) {
		if (we == null) {
			return;
		}

		if (Constant.highlighter == true) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) ActionKeyword.driver;
				js.executeScript("arguments[0].style.backgroundColor = '#F2E898';", we);
				js.executeScript("arguments[0].style.border = '10px DarkOrange';", we);
			} catch (Exception e) {
				return;
			}
		}
	}

	
	public static void browserconfig(String Browser) {
		try {
			if (Browser.contains("FireFox")) {
				Robot robot = new Robot();
				robot.delay(40);
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_T);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_O);
				robot.keyRelease(KeyEvent.VK_O);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(1000);
			}
		} catch (AWTException e) {
			}

		options.addArguments("--always-authorize-plugins=true");
		options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
		System.setProperty("webdriver.chrome.driver", Constant.chromedriverlocation);
	}

	
	public static WebElement SearchObjRep() {

		WebElement we = null;
		String Key = Constant.TestCaseRow.get("Elementlocation");
		String Loc = "", Type = "";
		try {
			Loc = Constant.ObjrepLoc.get(Key);
			Type = Constant.ObjrepType.get(Key);
		} catch (Exception e) {

		}
		if (Loc == null || Type == null) {
			String fatalMessage = "Framework is not able to find Object defination in ObjRep for the Key" + Key;
			TestCaseRunner.testScriptResult = false;

		}
		if (Type.equalsIgnoreCase("Xpath")) {
			we = SupportLib.FindElement(1, Loc);
		} else if (Type.equalsIgnoreCase("ID")) {
			we = SupportLib.FindElement(2, Loc);
		} else if (Type.equalsIgnoreCase("CSS")) {
			we = SupportLib.FindElement(3, Loc);
		} else if (Type.equalsIgnoreCase("Link")) {
			we = SupportLib.FindElement(4, Loc);
		} else if (Type.equalsIgnoreCase("Name")) {
			we = SupportLib.FindElement(5, Loc);
		} else if (Type.equalsIgnoreCase("Class")) {
			we = SupportLib.FindElement(6, Loc);
		} else if (Type.equalsIgnoreCase("partialLink")) {
			we = SupportLib.FindElement(7, Loc);
		} else if (Type.equalsIgnoreCase("tagName")) {
			we = SupportLib.FindElement(8, Loc);
		}
		return we;

	}

	public static List<WebElement> SearchObjReps() {

		List<WebElement> we = null;
		String Key = Constant.TestCaseRow.get("Elementlocation");
		;
		String Loc = Constant.ObjrepLoc.get(Key);
		String Type = Constant.ObjrepType.get(Key);

		if (Type.equalsIgnoreCase("Xpath")) {
			we = SupportLib.FindElements(1, Loc);
		} else if (Type.equalsIgnoreCase("ID")) {
			we = SupportLib.FindElements(2, Loc);
		} else if (Type.equalsIgnoreCase("CSS")) {
			we = SupportLib.FindElements(3, Loc);
		} else if (Type.equalsIgnoreCase("Link")) {
			we = SupportLib.FindElements(4, Loc);
		} else if (Type.equalsIgnoreCase("Name")) {
			we = SupportLib.FindElements(5, Loc);
		} else if (Type.equalsIgnoreCase("Class")) {
			we = SupportLib.FindElements(6, Loc);
		} else if (Type.equalsIgnoreCase("partialLink")) {
			we = SupportLib.FindElements(7, Loc);
		} else if (Type.equalsIgnoreCase("tagName")) {
			we = SupportLib.FindElements(8, Loc);
		}
		return we;

	}

	public static WebElement FindElement(int i, String Loc) {
		WebDriverWait wait = null;

				wait = new WebDriverWait(ActionKeyword.driver, Constant.ExplicitWait);
		WebElement we = null;

		try {
			switch (i) {
			case 1:
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Loc))));

				break;
			case 2:
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Loc))));
				break;
			case 3:
				
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Loc))));
				break;
			case 4:
				
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(Loc))));
				break;
			case 5:
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.name(Loc))));
				break;
			case 6:
				
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.className(Loc))));
				break;
			case 7:
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(Loc))));
				break;
			case 8:
				we = (wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(Loc))));
				break;

			}
		} catch (Exception e) {
			return null;
		}
		return we;

	}

	
	public static List<WebElement> FindElements(int i, String loc) {

		WebDriverWait wait = new WebDriverWait(ActionKeyword.driver, Constant.ExplicitWait);

		List<WebElement> we = new ArrayList<WebElement>();
		we = null;
		try {
			switch (i) {
			case 1:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(loc))));
				break;
			case 2:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(loc))));
				break;
			case 3:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(loc))));
				break;
			case 4:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(loc))));
				break;
			case 5:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(loc))));
				break;
			case 6:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(loc))));
				break;
			case 7:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(loc))));
				break;
			case 8:
				we = (wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(loc))));
				break;
			}
		} catch (Exception e) {
			return we;
		}
		return we;

	}
	
	public static void countbrowser() {
		try {
			String browser = Constant.TestSuit.get("Browser");
			if (browser == "Blank") {
				Constant.Browser = null;
				return;
			}

			Constant.Browser = browser.split(",");
			Constant.browsercount = Constant.Browser.length;
		} catch (Exception e) {
		}
	}


	
	public static WebElement SearhElement(List<WebElement> webElementCollection) {
		for (WebElement sr : webElementCollection) {

			String actionSupportValue = Constant.TestCaseRow.get("ActionSupportValue");
			String data = Constant.TestCaseRow.get("Data").toLowerCase();
			if (!actionSupportValue.contains("Blank")) {
				if (sr.getAttribute(actionSupportValue).toLowerCase().contains(data)) {
					return sr;
				} else {
					continue;
				}
			} else if (actionSupportValue.contains("Blank")) {
				if (sr.getText().toLowerCase().equals(data)) {
					return sr;

				} else {
					continue;
				}
			} else {
				break;
			}
		}
		return null;
	}
}

	