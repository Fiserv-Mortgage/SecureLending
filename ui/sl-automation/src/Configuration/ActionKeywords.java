package Configuration;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import Util.ExtentLogs;
import Util.Log;

public class ActionKeyword {

	/**
	 * All the static element defined here
	 */
	public static WebDriver driver = null;
	public static WebElement ActionElement;
	public static ArrayList<Integer> activeframe = new ArrayList<Integer>();
	public static ArrayList<WebElement> ActiveFrameElement = new ArrayList<WebElement>();
	public static ArrayList<String> WindowHandle = new ArrayList<String>();
	public static String imageLocation, browser = null;
	public static ArrayList<WebElement> WebElementCollection = new ArrayList<WebElement>();
	
	
	private static String infoMessage = "", skipMessage = "", errorMessage = "",
			passMessage = "", eInfoMessage = "", ePassMessage = "", 
			eSkipMessage = "";

	/**
	 * Click on the specific element
	 * 
	 * @throws NoSuchElementException
	 */
	public static void Click() {
		SupportLib.waitForAjax();

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
		Log.pass(passMessage);
		ePassMessage = "Clicked is performed on Element: " + Constant.TestCaseRow.get("Elementlocation");
		ExtentLogs.pass(ePassMessage);
	}
}
/**
 * Return the WebElemets List according to the parameters provided.
 */
public static ArrayList<WebElement> FindElements() {

	String Type = Constant.TestCaseRow.get("ElementFinderType");
	String Loc = Constant.TestCaseRow.get("Elementlocation");
	ArrayList<WebElement> we = null;
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

		Log.error(errorMessage);
	}

	return we;

}
public void OpenBrowser(String browserName) {
		try {
			if(browserName.equalsIgnoreCase("Firefox")) {
				driver = new FirefoxDriver();
			} else if(browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver","D:\\driver\\chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				System.out.println("Browser is not from Chrome or Firefox");
				return;
			}
		}
		catch (WebDriverException e) {
			System.out.println(e.getMessage());
			return;
		}

	}
	public void enter_URL(String URL) {
		driver.navigate().to(URL);
	}

}


/*else if(browserName.equalsIgnoreCase("IE")) {
	System.setProperty("webdriver.ie.driver","D:\\driver\\IEdriver.exe");
	driver = new InternetExploreDriver();
}*/ 

