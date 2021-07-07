package com.fiserv.b2bclient.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import com.fiserv.b2bclient.service.B2BService;

public class Client {



	public static void main(String args[]) {

		List<List> sessionIdLoanIdList = null;
		Document doc = null;

		//B2BXMLEdit obj = new B2BXMLEdit();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse("C:\\temp\\NationWide\\input\\input.xml");

			sessionIdLoanIdList = com.fiserv.b2bclient.util.FileUtils
					.readCSVFile("C:\\temp\\NationWide\\input\\NationWide_LenderCaseNumber.csv");
		} catch (Exception ex) {
			System.out.println("Parsing error" + ex.getMessage());
			System.exit(0);
		}

		if (sessionIdLoanIdList != null) {
			
			ExecutorService executor = Executors.newFixedThreadPool(8);
			
			

			for (List sessionIdLoanId : sessionIdLoanIdList) {
				
				SimpleRunnable task = new SimpleRunnable((String)sessionIdLoanId.get(0), (String)sessionIdLoanId.get(1), doc);
				
				executor.execute(task);
			
			}
			
			executor.shutdown();
		}

	}
}

class SimpleRunnable implements Runnable {
	
	//public static String endPoint = "https://b2b-btat2.elending.fiservlendingsolutions.com/invoke/epc/receive";
	public static String endPoint = "https://b2b.elending.fiservlendingsolutions.com/invoke/epc/receive";
	public static String userName = "1000478086";
	public static String password = "test123!";
    
    private String sessionId;
    private String lenderLoanNumber;
    static B2BXMLEdit obj = new B2BXMLEdit();
    Document doc = null;
    
    

	public SimpleRunnable(String sessID, String lenderLoanNumber, Document doc) {
		super();
		this.sessionId = sessID;
		this.lenderLoanNumber = lenderLoanNumber;
		this.doc = doc;
	}



	public void run() {
		

		byte[] xmlArray;
		try {
			
		
		synchronized(obj) {
			xmlArray = obj.getXML(doc, sessionId.trim(), lenderLoanNumber.trim());		
		}
			

		String response = B2BService.getInstance().postHTTPRequest(xmlArray, endPoint, userName, password);
		

		File targetFile = new File("C:\\temp\\NationWide\\outPut\\", lenderLoanNumber.trim());
		// File file = new File("C:\\temp\\NationWide\\outPut\\" + sessionId);

		FileUtils.writeStringToFile(targetFile, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("B2B error-" + sessionId + "-" + e.getMessage());
		}

		
	}


     
    // standard logger, constructor
     
  
}
