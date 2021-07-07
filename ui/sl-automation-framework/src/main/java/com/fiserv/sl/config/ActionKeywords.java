package com.fiserv.sl.config;


import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.fiserv.sl.constant.Constants;
import com.fiserv.sl.execution.DriverScript;
import com.fiserv.sl.util.Log;
import com.fiserv.sl.util.MemoryUtil;
/**
 * 
 * @author richa.arya
 *
 */
public class ActionKeywords {
	public static WebDriver driver;
	public static String parentWindow;
	public static String childWindow;

	public static void openBrowser(String object, String data) {
		Log.info("Opening Browser");
		try {
			if (data.equals("Mozilla")) {
				driver = new FirefoxDriver();
				Log.info("Mozilla browser started");
			} else if (data.equals("IE")) {
				driver = new InternetExplorerDriver();
				Log.info("IE browser started");
			} else if (data.equals("Chrome")) {
				driver = new ChromeDriver();
				Log.info("Chrome browser started");
			}

			int implicitWaitTime = (10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void navigate(String object, String data) {
		try {
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
		} catch (Exception e) {
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void click(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			//driver.findElement(By.xpath(DriverScript.OR.getProperty(object))).click();
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).click();
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void input(String object, String data) {
		try {
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void waitFor(String object, String data) throws Exception {
		try {
			if(data!=null && !data.equals("")){
				Thread.sleep(Long.parseLong(data)*1000);
			}
			else{
				Log.info("Wait for 5 seconds");
				Thread.sleep(5000);
			}
			
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void closeBrowser(String object, String data) {
		try {
			Log.info("Closing the browser");
			driver.quit();
		} catch (Exception e) {
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	 /* This method is used to switch control from parent window to child window.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	public static void switchToChildWindow(String object, String data) throws InterruptedException {
		Set<String> windowId = driver.getWindowHandles();
		// get window id of current window
		Iterator<String> itererator = windowId.iterator();

		parentWindow = itererator.next();
		System.out.println(parentWindow);
		childWindow = itererator.next();
		System.out.println(childWindow);

		driver.switchTo().window(childWindow);
		Thread.sleep(3000);
	}
	
	/**
	 * This method is used to switch control between tabs in same window handle.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
		 
	/*
	public static void switchTab(String object, String data) {
		
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.close();
		driver.switchTo().window(tabs.get(0));
 
 }*/
	
	/**
	 * This method is used to switch control between tabs in same window handle.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
		 
	
	public static void switchTab(String object, String data) {
		
		 String parentHandle= driver.getWindowHandle();
		 System.out.println("Current opened tab is : " +parentHandle);
		 System.out.println("Clicking on Webelement " + object);
			//driver.findElement(By.xpath(DriverScript.OR.getProperty(object))).click();
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).click();
			//Thread.sleep(5000);
			String childHandle= driver.getWindowHandle();
			 System.out.println("Current opened child tab is : " +childHandle);
			 driver.switchTo().window(parentHandle);
			 //Thread.sleep(3000);
		/* String urlToClick=driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).getText();
		 System.out.println("URL to click is :-" +urlToClick);
		Set<String> handles=driver.getWindowHandles();
        for(String actual: handles)
        {
            
         if(!actual.equalsIgnoreCase(currentHandle))
         {
             //switching to the opened tab
             driver.switchTo().window(actual);             
             //opening the URL saved.
             driver.get(urlToClick);
         }
        }*/
 
 }
 

	/**
	 * This method is used to switch control from child window to parent window.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	public static void switchToParentWindow(String object, String data) throws InterruptedException {
		driver.switchTo().window(parentWindow);
		Thread.sleep(3000);
	}
	
	/**
	 * This method is used to handle any alert box with always a YES where ever any confirmation alert box appears.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	 
	public static void handleAlert(String object, String data) throws InterruptedException {
		Alert confirmationAlert = driver.switchTo().alert();
		String alertText = confirmationAlert.getText();
		
		System.out.println("Alert text is " + alertText); //display text of alert box on Console
		//always select to accept alert box.
		confirmationAlert.accept();
	}
	

	/**
	 * This method is used to verify text on page, will be used to verify is correct page is encountered and verify headings.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	
	public static void verifyText(String object, String data) throws Exception {
		
		try {
			Log.info("Verify text on Webelement " + object);
			if(data!=null && !data.equals(""))
			{
				String expectedMessage = data;
				System.out.println("Message expected is " +expectedMessage);
				String message = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).getText();
				System.out.println("Actual message is " +message);
				if(expectedMessage.equals(message))
				
					System.out.println("Text is same and present on screen.");
				
				//Assert.assertEquals(message,expectedMessage);
			else
				System.out.println("Either text is not same or not present on screen.");
			}
			else
			{
				System.out.println("Data is not provided");
			}
				
	}
		catch (Exception e) {
			Log.error("Not able to Verify text --- " + e.getMessage());
			DriverScript.bResult = false;
	}
	}
	
	/**
	 * This method is used to click only if element is encountered on page else it will move to next step.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	public static void clickIf(String object, String data) {
		try {
			Log.info("Clicking on Webelement if it is present on page " + object);
			//driver.findElement(By.xpath(DriverScript.OR.getProperty(object))).click();
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).click();
		} catch (Exception ignored) {
			Log.info("Element is not found on page, hence moving to next step.");
			//Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
	/**
	 * This method is used to clear a text field.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
		 
		public static void clearExistingText(String object, String data) {
				try {
				
				
				Log.info("Clicking on Webelement if it is present on page " + object);
				//driver.findElement(By.xpath(DriverScript.OR.getProperty(object))).click();
				driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).click();
				WebElement toClear = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
				toClear.sendKeys(Keys.CONTROL + "a");
				toClear.sendKeys(Keys.DELETE);
				
			} catch (Exception ignored) {
				Log.info("Element cannot be cleared, hence moving to next step.");
				//Log.error("Not able to click --- " + e.getMessage());
				DriverScript.bResult = false;
			}
			}

/**
 * This method is used to clear a text field and input new value to that field.
 * 
 * @param object
 * @param data
 * @throws InterruptedException
 * @author richa.arya
 */
	 
	public static void clearAndInput(String object, String data) {
			try {
			
			
			Log.info("Clicking on Webelement if it is present on page " + object);
			//driver.findElement(By.xpath(DriverScript.OR.getProperty(object))).click();
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).click();
			WebElement toClear = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).sendKeys(data);
			
		} 
			
			catch (Exception e) {
				Log.error("Not able to Enter UserName --- " + e.getMessage());
				DriverScript.bResult = false;
				
				//catch (Exception ignored) {
			//Log.info("Element cannot be cleared, hence moving to next step.");
			//Log.error("Not able to click --- " + e.getMessage());
			//DriverScript.bResult = false;
		}
		}
	
	/**
	 * This method is used to input text only if element is present on page else it will move to next step.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
		 
	
	public static void inputIf(String object, String data) {
		try {
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).sendKeys(data);
			
		} 
		catch (Exception ignored) {
			Log.info("Element is not found on page, hence moving to next step.");
			//Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}	
	
	/**
	 * This method is used to click enter key from keyboard.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
	
	
	public static void pressEnter(String object, String data) {
		try {
			Log.info("Pressing enter on " + object);
			WebElement textbox = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			textbox.sendKeys(Keys.ENTER);
			//driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).sendKeys(data);
			
		} 
		catch (Exception ignored) {
			Log.info("Element is not found on page, hence moving to next step.");
			//Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}	
	

	/**
	 * This method is used to verify if certain text is present on page else it will move to next step.
	 * 
	 * @param object
	 * @param data
	 * @throws InterruptedException
	 * @author richa.arya
	 */
		 
	
public static void verifyTextPresent(String object, String data) throws Exception {
		
		try {
			Log.info("Verify text on Webelement " + object);
			
			{
				
				String message = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))).getText();
				if(message!=null && !message.equals(""))

				System.out.println("Text is present at specified location with value :- " +message);
				
				else
				System.out.println("Text is not present. Step FAIL.");
			}
			
	}
		catch (Exception e) {
			Log.error("Not able to Verify text --- " + e.getMessage());
			DriverScript.bResult = false;
	}
	}
	

/**
 * This method is used to verify if certain text (only data and not the headings) is present in a table (which displays a list) else it will move to next step.
 * 
 * @param object
 * @param data
 * @throws InterruptedException
 * @author richa.arya
 */

public static void verifyTextList(String object, String data) throws Exception {
	
	try {
		Log.info("Verify text on Webelement " + object);
		if(data!=null && !data.equals(""))
		{
			
			WebElement htmltable=driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			String expectedMessage = data;
			System.out.println("Text to be verified from list is " +expectedMessage);
		
			List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
			for(int rnum=0;rnum<rows.size();rnum++)
			{
			List<WebElement> values=rows.get(rnum).findElements(By.tagName("td"));
			//System.out.println("Number of columns:"+values.size());
			
			for(int cnum=0;cnum<values.size();cnum++)
			{
					    String st=values.get(cnum).getText();
					    System.out.println(st);
			if(expectedMessage.equals(st))
			{
				System.out.println("Text is present in list");
			
			}
			

					else
		{
			System.out.println("Text is not present in list.");
		}
			}
			}}
		
		else
		{
			System.out.println("Data is not provided");
		}
			
}
	catch (Exception e) {
		Log.error("Not able to Verify text --- " + e.getMessage());
		DriverScript.bResult = false;
}
}

/**
 * This method is used to upload any pdf sample where ever upload of document is required.
 * 
 * @param object
 * @param data
 * @throws InterruptedException
 * @author richa.arya
 */



public static void FileUpload(String object, String data) {
try {
Log.info("Upload file at location : " + object);
WebElement uploadElement = driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
// enter the file path onto the file-selection input field
        uploadElement.sendKeys("C:\\Users\\richa.arya\\Desktop\\New folder- desktop stuff\\Secure Lending\\PDF Docs\\2850030817_Good Faith Estimate.pdf");
	System.out.println("File successfully uploaded.");
	}
	catch (Exception e) {
		Log.error("Not able to upload file --- " + e.getMessage());
		DriverScript.bResult = false;
}
}




/**
 * This method is used to verify if certain text (only headings) is not present in a table (which displays a list) else it will move to next step.
 * 
 * @param object
 * @param data
 * @throws InterruptedException
 * @author richa.arya
 */

public static void verifyHeadings(String object, String data) throws Exception {
	
	try {
		Log.info("Verify text on Webelement " + object + "is removed");
		if(data!=null && !data.equals(""))
		{
		
			List<WebElement> htmltable=driver.findElements(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			String expectedMessage = data;
			System.out.println("Text to be verified that is removed is " +expectedMessage);
			System.out.println("Total headers found are :- "+htmltable.size());
			for(WebElement header:htmltable)
				//Error that appears in above statement :- Type mismatch : cannot convert from element type object to webelement.
				
			{
				System.out.println(header.getText());
				if(expectedMessage.equals(header.getText()))
				{
					System.out.println("Text is present in list");
				
				}
				

						else
			{
				System.out.println("Text is removed from list.");
			}
			}}
	}
			/*List<WebElement> col=htmltable.findElements(By.className("dataField sortable"));
			System.out.println(col.size());
			 for (WebElement webElement : col) {
		            String name = webElement.getText();
		            System.out.println(name);
			for(int rnum=0;rnum<1;rnum++)
			//for(int rnum=0;rnum<rows.size();rnum++)n
			{
				//System.out.println(+rnum);
			//List<WebElement> values=rows.get(rnum).findElements(By.tagName("th"));
			//System.out.println("Number of columns:"+values.size());
			 //System.out.println("Header is");  
			for(int cnum=1;cnum<9;cnum++)
			{
					    //String st=values.get(cnum).getText();
				//List<WebElement> st=driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
					    System.out.println(driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object))));
			if(expectedMessage.equals(driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)))))
			{
				System.out.println("Text is present in list");
			
			}
			

					else
		{
			System.out.println("Text is removed from list.");
		}
			}
			}}}
		'
		else
		{
			System.out.println("Data is not provided");
		} 
			
}*/
	catch (Exception e) {
		Log.error("Not able to Verify text --- " + e.getMessage());
		DriverScript.bResult = false;
}
}
}


	
/*public static void verifyTextList(String object, String data) throws Exception {
	
	try {
		Log.info("Verify text on Webelement " + object);
		if(data!=null && !data.equals(""))
		{
			
			WebElement htmltable=driver.findElement(By.xpath(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
			for(int rnum=0;rnum<rows.size();rnum++)
			{
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			System.out.println("Number of columns:"+columns.size());
			for(int cnum=0;cnum<columns.size();cnum++)
			{
			System.out.println(columns.get(cnum).getText());
			String expectedMessage = data;
			System.out.println("Text to be verified from list is " +expectedMessage);
			if(expectedMessage.equals(columns.get(cnum).getText()))
			{
				System.out.println("Text is present in list");
			
			}
			

			/*int index = 0;
			WebElement baseTable = driver.findElement(By.className(MemoryUtil.getInstance().getObjectRepository().getProperty(object)));
			List<WebElement> tableRows = baseTable.findElements(By.tagName("td"));
			{
			String st = tableRows.get(index).getText();
			String expectedMessage = data;
			System.out.println("Text to be verified from list is " +expectedMessage);
			if(expectedMessage.equals(st))
			
				System.out.println("Text is present in list");
			
			//Assert.assertEquals(message,expectedMessage);*/
		/*else
		{
			System.out.println("Either text is not same or not present on screen.");
		}
			}
			}}
		
		else
		{
			System.out.println("Data is not provided");
		}
			
}
	catch (Exception e) {
		Log.error("Not able to Verify text --- " + e.getMessage());
		DriverScript.bResult = false;
}
}*/






	
			
	/*public static void waitForAjax() {
	if (Constants.WaitforAjax) {
		try {
			if (ActionKeywords.driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) ActionKeywords.driver;
				for (int i = 0; i < Constants.AjaxWait; i++) {
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		} catch (Exception e) {
			return;
		}
	} else {

	}
}*/

		// Perform the actions on new window

	/*	// Close the new window, if that window no more required
		driver.close();

		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);
	}*/
	
	
	       /* // Store and Print the name of the First window on the console

	        String handle= driver.getWindowHandle();


	        // Store and Print the name of all the windows open	              

	        Set handles = driver.getWindowHandles();

	        System.out.println(handles);

	        // Pass a window handle to the other window

	        for (String handle1 : driver.getWindowHandles()) {

	        	System.out.println(handle1);

	        	driver.switchTo().window(handle1);

	        	}

	        
	}*/
	

