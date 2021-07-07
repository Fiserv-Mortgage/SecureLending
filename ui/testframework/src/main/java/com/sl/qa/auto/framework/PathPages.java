package com.sl.qa.auto.framework;

public class PathPages {

	static String url = "https://loandocumentexchange.com/login";
	static String title = "Secure Document Exchange login";
	
	public void goTo() {
		Browser.goTo(url);
	}

	public PathPage getPathPage(int pageNo) {
		switch (pageNo) {
		case 1:
			return Pages.loginPathPage();
		}
		
		return null;
	}

	public boolean isAt() {
		return Browser.title().equals(title);
	}

	
	
}
