package com.fiserv.sl.config;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class fileupload {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.setProperty("webdriver.chrome.driver", "C:\\Driver\\chromedriver.exe");
        String baseUrl = "http://demo.guru99.com/test/upload/";
        WebDriver driver = new ChromeDriver();

        driver.get(baseUrl);
        WebElement uploadElement = driver.findElement(By.id("uploadfile_0"));

        // enter the file path onto the file-selection input field
        uploadElement.sendKeys("C:\\Users\\richa.arya\\Desktop\\New folder- desktop stuff\\Secure Lending\\PDF Docs\\2850030817_Good Faith Estimate.pdf");

        // check the "I accept the terms of service" check box
        driver.findElement(By.id("terms")).click();

        // click the "UploadFile" button
        driver.findElement(By.name("send")).click();
        }
}