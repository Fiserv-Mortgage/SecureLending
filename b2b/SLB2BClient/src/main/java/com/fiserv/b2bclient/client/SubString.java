package com.fiserv.b2bclient.client;
import java.io.*;

public class SubString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 String str = "Dear ARINJAY AdsProd Jr.,Welcome to the GREENSTREAM BANK online document delivery system. This online process allows you to electronically access and review your loan documents from any computer with internet access.Your account information is:User ID: 	1001462324For security reasons, your temporary password and login instructions will be sent in a separate email. Upon receipt of both emails, you will have the information you need to access the system.";
		 
		    // Returns index of first occurrence of substring
		    int firstIndex = str.indexOf("100");
		    System.out.println("First occurrence of char 100" + " is found at : " + firstIndex);
		    System.out.println(str.subSequence(str.indexOf("100"),str.indexOf("100")+10));
	}

}
