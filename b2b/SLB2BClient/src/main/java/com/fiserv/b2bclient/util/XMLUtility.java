package com.fiserv.b2bclient.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * @author atul.singh
 *
 */
public class XMLUtility {
	private static String provider = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";

	public static String readTagFromXML(String xmlInput, String pattern) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(provider, null);

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlInput)));
			NodeList nl = document.getElementsByTagName(pattern);
			return nl.item(0).getTextContent();

		} catch (Exception e) {
			throw new Exception();
		}
	}
}
