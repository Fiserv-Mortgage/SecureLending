package com.fiserv.b2bclient.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import sun.misc.BASE64Encoder;

public class B2BService {

	private static B2BService service = null;

	public B2BService() {
	
	}

	public String postHTTPRequest(byte[] xmlArray, String endPoint, String userName, String password)
			throws IOException {
		String method = "POST";
		String authentication = userName + ':' + password;
		BASE64Encoder encoder = new BASE64Encoder();
		String encoded = encoder.encode((authentication).getBytes("UTF-8"));

		URL url = new URL(endPoint);
		HttpURLConnection connection = null;
		connection = (HttpsURLConnection) url.openConnection();
		((HttpsURLConnection) connection).setHostnameVerifier(new MyHostnameVerifier());

		connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf8\"");
		connection.setRequestMethod(method);
		connection.setRequestProperty("Authorization", "Basic " + encoded);
		connection.setDoOutput(true);
		ByteArrayOutputStream out = (ByteArrayOutputStream) connection.getOutputStream();
		out.write(xmlArray, 0, xmlArray.length);
	
		
		out.close();

		// execute HTTPS request
		int returnCode = connection.getResponseCode();
		InputStream connectionIn = null;
		if (returnCode == 200)
			connectionIn = connection.getInputStream();
		else
			connectionIn = connection.getErrorStream();

		// print resulting stream
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connectionIn));
		String inputLine;
		String response = "";
		while ((inputLine = bufferReader.readLine()) != null) {
			response = response + inputLine;
		}

		bufferReader.close();
		return response;

	}

	public static B2BService getInstance() {
		if (service == null) {
			service = new B2BService();
		}
		return service;
	}

	public static class MyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			// verification of hostname is switched off
			return true;
		}
	}
}
