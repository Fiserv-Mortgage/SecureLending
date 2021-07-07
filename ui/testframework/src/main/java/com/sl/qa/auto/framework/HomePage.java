package com.sl.qa.auto.framework;


public class HomePage {

	static String url = "https://loandocumentexchange.com";
	static String title = "Secure Document Exchange";
	
	public void goTo() {
		Browser.goTo(url);
	}

	public boolean isAt() {
		return Browser.title().equals(title);
	}

}
