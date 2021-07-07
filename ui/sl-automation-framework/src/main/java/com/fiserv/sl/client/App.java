package com.fiserv.sl.client;

import org.apache.log4j.BasicConfigurator;

import com.fiserv.sl.constant.ServiceType;
import com.fiserv.sl.service.SLAutoService;
import com.fiserv.sl.service.impl.ServiceFactory;
import com.fiserv.sl.vo.SLAutoWrapper;

/**
 * 
 * 
 * @author atul.singh. Client for invoking the service
 */
public class App {
	public static void main(String args[]) {
		BasicConfigurator.configure();
		System.setProperty("webdriver.chrome.driver", "C:\\Driver\\chromedriver.exe");
		String resourcePath = "src//main//resources//";
		SLAutoService slAutoService = ServiceFactory.getFactory().getService(ServiceType.AUTOMATION_SERVICE);
		SLAutoWrapper slWrapper = new SLAutoWrapper(resourcePath, "TestSuit.xlsx", "OR.properties");
		slAutoService.executeTestSuite(slWrapper);
		
	}
}
