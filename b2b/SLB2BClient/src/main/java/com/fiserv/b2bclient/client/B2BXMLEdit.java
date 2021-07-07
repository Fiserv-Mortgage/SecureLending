package com.fiserv.b2bclient.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fiserv.b2bclient.util.DateTimeUtility;

public class B2BXMLEdit {



	public byte[] getXML() throws Exception {
		
		String adsUniqueId = "ADS" + DateTimeUtility.getDateandTime("YYYYddMMHHmmss");

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("C:\\temp\\NationWide\\abc.xml");

		// Update the Value for UUID
		Node service = doc.getElementsByTagName("UUID").item(0);
		service.setTextContent(adsUniqueId);

		Node Client = doc.getElementsByTagName("SessionID").item(0);
		Client.setTextContent(adsUniqueId);

		NodeList service2 = doc.getElementsByTagName("eclosereq:MORTGAGE_TERMS");
		service2.item(0).getAttributes().getNamedItem("LenderCaseIdentifier").setNodeValue(adsUniqueId);

		DOMSource source = new DOMSource(doc);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		try {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			org.w3c.dom.DocumentType docType = doc.getDoctype();
			if (docType != null) {
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getPublicId());
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getSystemId());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		StreamResult result = new StreamResult("D://def.xml");
		transformer.transform(source, result);

		File file = new File("D://def.xml");
		// init array with file length
		byte[] XMLArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(XMLArray); // read file into bytes[]
		fis.close();

		return XMLArray;

	}
	
	
	public byte[] getXML(Document rawInputFile, String sessionID, String lenderLoanNumner) throws Exception {
		
		String adsUniqueId =  java.util.UUID.randomUUID().toString();

		// Update the Value for UUID
		Node service = rawInputFile.getElementsByTagName("UUID").item(0);
		service.setTextContent(adsUniqueId);

		Node Client = rawInputFile.getElementsByTagName("SessionID").item(0);
		Client.setTextContent(sessionID);

		NodeList service2 = rawInputFile.getElementsByTagName("eclosereq:MORTGAGE_TERMS");
		service2.item(0).getAttributes().getNamedItem("LenderCaseIdentifier").setNodeValue(lenderLoanNumner);

		DOMSource source = new DOMSource(rawInputFile);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		try {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			org.w3c.dom.DocumentType docType = rawInputFile.getDoctype();
			if (docType != null) {
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getPublicId());
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getSystemId());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		StreamResult result = new StreamResult(bos);
		transformer.transform(source, result);		
		

		return bos.toByteArray();

	}

}
