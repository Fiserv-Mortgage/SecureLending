package com.fiserv.sl.configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


//import Util.ExcelUtils;


/**
 * Lib Class contains all the Action Keywords and Support Lib Function
 */
public class ActionKeyword {

	/**
	 * All the static element defined here
	 */
	public static WebDriver driver = null;
	public static WebElement ActionElement;
	public static List<Integer> activeframe = new ArrayList<Integer>();
	public static List<WebElement> ActiveFrameElement = new ArrayList<WebElement>();
	public static List<String> Valuestored = new ArrayList<String>();
	public static List<String> ValueCaptured = new ArrayList<String>();
	public static List<String> ValuesCaptured = new ArrayList<String>();
	public static List<Integer> SpamCaptured = new ArrayList<Integer>();
	public static List<WebElement> WebElementCollection = new ArrayList<WebElement>();
	public static List<String> WindowHandle = new ArrayList<String>();
	public static Map<String, String> DbValueCaptured = new HashMap<String, String>();
	public static int ValueCaptureindex = 0;
	public static int IEOffsetx = 0, IEOffsety = 0;
	public static String imageLocation, browser = null;

	private static String infoMessage = "", warningMessage = "", skipMessage = "", errorMessage = "", fatalMessage = "",
			passMessage = "", failMessage = "", eInfoMessage = "", ePassMessage = "", eFailMessage = "",
			eSkipMessage = "", eErrorMessage = "";

	/**
	 * Will sent text provided in the data column to the WebElement.
	 */
	public static void OpenBrowser() {
		try {

						if (browser == null || browser.contains("Blank") || browser == "") {
				fatalMessage = "Browser is not specify please Specify Browser in test suit";
				} else if (browser.equalsIgnoreCase("chrome")) {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				if (Constant.ChromeExtEnable == true) {
					SupportLib.browserconfig("chrome");
					Constant.highlighter = true;
					driver = new ChromeDriver(SupportLib.options);
				} else if (Constant.ChromeExtEnable == false) {
					System.setProperty("webdriver.chrome.driver", Constant.chromedriverlocation);
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-extensions");
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					driver = new ChromeDriver(capabilities);
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Constant.ImplicitWait, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(Constant.PageLoadWait, TimeUnit.SECONDS);
				infoMessage = "Opening Chrome for Test case execution";
			} else if (browser.equalsIgnoreCase("FireFox")) {
				Constant.highlighter = true;
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				if (Constant.FirefoxTabprefrence == true) {
					SupportLib.browserconfig("Firefox");
				}
				driver.manage().timeouts().implicitlyWait(Constant.ImplicitWait, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(Constant.PageLoadWait, TimeUnit.SECONDS);
				infoMessage = "Opening Firefox for Test case execution";

			}  else if (browser.equalsIgnoreCase("IE")) {
				Constant.highlighter = false;
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

				ieCapabilities.setCapability("nativeEvents", true);
				ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
				ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
				ieCapabilities.setCapability("disable-popup-blocking", true);
				ieCapabilities.setCapability("enablePersistentHover", true);
				ieCapabilities.setCapability("REQUIRE_WINDOW_FOCUS", true);
				System.setProperty("webdriver.ie.driver", Constant.IEDRIVER);
				driver = new InternetExplorerDriver(ieCapabilities);
				infoMessage = "Opening IE for Test case execution";

					}
		}
						 catch (Exception e) {
								errorMessage = Constant.TestSuit.get("Testcaseid") + "; Error in communicating with Browser" + e.toString();
								return;
							}

						}

						
						/**
						 * Return the WebElemets List according to the parameters provided.
						 */
						public static List<WebElement> FindElements() {

							String Type = Constant.TestCaseRow.get("ElementFinderType");
							String Loc = Constant.TestCaseRow.get("Elementlocation");
							List<WebElement> we = null;
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
							} else if (Type.equalsIgnoreCase("ObjectRep")) {
								we = SupportLib.SearchObjReps();
							} else {
								errorMessage = Constant.TestCaseRow.get("TestStepID") + "Element Location type is not defined";

							}

							return we;

						}


						/**
						 * Return the WebElemet according to the parameters provided.
						 */
						public static WebElement FindElement() {

							String Type = Constant.TestCaseRow.get("ElementFinderType");
							String Loc = Constant.TestCaseRow.get("Elementlocation");
							WebElement we = null;
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
							} else if (Type.equalsIgnoreCase("ObjectRep")) {
								we = SupportLib.SearchObjRep();

							} else {
								errorMessage = Constant.TestCaseRow.get("TestStepID") + "Element Location type is not defined";

						
							}

							return we;

						}						/**
	 * Click on the specific element
	 * 
	 * @throws NoSuchElementException
	 */
	public static void Click() {

		if (!(Constant.TestCaseRow.get("ActionSupportValue").contains("Blank"))
				|| !(Constant.TestCaseRow.get("Data").contains("Blank"))) {
			WebElementCollection = FindElements();
			if (WebElementCollection == null) {
				NoSuchElementException e = new NoSuchElementException();
				throw e;
			} else {
				ActionElement = SupportLib.SearhElement(WebElementCollection);
			}

		} else {
			ActionElement = FindElement();
		}
		if (ActionElement == null) {
			NoSuchElementException e = new NoSuchElementException();
			throw e;
		}
		ActionElement.click();
		passMessage = Constant.TestCaseRow.get("TestStepID") + ": " + Constant.TestCaseRow.get("TeststepDescription")
				+ ": Action click";
		ePassMessage = "Clicked is performed on Element: " + Constant.TestCaseRow.get("Elementlocation");
	}

	
	/**
	 * Close the driver
	 */
	public static void Close() {
		if (driver == null) {
			warningMessage = "Browser instance value is null";
		} else {
			driver.close();
			infoMessage = "Closing instance of Browser: " + browser;
		
		}

	}

	/**
	 * Not a Lib Function called by Test control function from test case runner
	 * file
	 * 
	 * @throws Exception
	 * @throws Throwable
	 */
	public static void Quit() {

		try {
			if (driver == null) {
				warningMessage = "Browser instance value is null";
					} else {
				driver.quit();
				Process KillTask;
				int retCode = 0;
				KillTask = Runtime.getRuntime().exec("Taskkill /f /im chromedriver.exe");
				InputStream is = KillTask.getInputStream();
				while (retCode != -1) {
					retCode = is.read();
				}
				retCode = 0;
				KillTask = Runtime.getRuntime().exec("Taskkill /f /im IEDriverserver.exe");
				is = KillTask.getInputStream();
				while (retCode != -1) {
					retCode = is.read();
				}
				retCode = 0;
				KillTask = Runtime.getRuntime().exec("Taskkill /f /im  firefighter.exe");
				is = KillTask.getInputStream();
				while (retCode != -1) {
					retCode = is.read();
				}
				infoMessage = "Quiting all instance of Browser(Firefox)";
				retCode = 0;
				KillTask = Runtime.getRuntime().exec("Taskkill /f /im  chromeioum.exe");
				is = KillTask.getInputStream();
				while (retCode != -1) {
					retCode = is.read();
				}
				infoMessage = "Quiting all instance of Browser(Chrome)";
				retCode = 0;
				KillTask = Runtime.getRuntime().exec("Taskkill /f /im  iexploreworld.exe");
				is = KillTask.getInputStream();
				while (retCode != -1) {
					retCode = is.read();
				}
				infoMessage = "Quiting all instance of Browser(Internet explorer)";
				}

		} catch (Exception e) {
			}
	}


	
	/**
	 * SubmitValue action on the Web Element Fields required: Action, Element
	 * locator type, Element Location Test data: Value to enter in the
	 * Web-Element
	 * 
	 * @throws Exception
	 * @throws Throwable
	 */
	public static void SubmitValue() throws Exception, Throwable {

		ActionElement = FindElement();
		if (ActionElement == null) {
			NoSuchElementException e = new NoSuchElementException();
			throw e;
		}
		ActionElement.sendKeys(Constant.TestCaseRow.get("Data"));

		passMessage = Constant.TestCaseRow.get("TestStepID") + ": " + Constant.TestCaseRow.get("TeststepDescription")
				+ "; Action: Submited Value";
		
		ePassMessage = "Action: User provided value is submitted in Element"
				+ Constant.TestCaseRow.get("Elementlocation") + "Value: " + Constant.TestCaseRow.get("Data");

	}
}