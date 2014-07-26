package com.t3.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class URLUtilTest {
	
	@DataProvider(name="generateTestURLToLinkData")//, parallel=true)
	public Object[][] generateTestURLToLinkData() {
		return new Object[][] {
				{"hallo welt","hallo welt"},
				{"not a url htt://argus","not a url htt://argus"},
				{"simple url http://google.de is here", 
					"simple url <a href=\"http://google.de\">http://google.de</a> is here"},
				{"https://google.com/testus/whatever.php?param=4&param%5B2%5D=13",
						"<a href=\"https://google.com/testus/whatever.php?param=4&param%5B2%5D=13\">"+
								"https://google.com/testus/whatever.php?param=4&param%5B2%5D=13</a>"},
				{"asset://asseturl/","asset://asseturl/"},
				{"www.google.com","<a href=\"http://www.google.com\">www.google.com</a>"}
		};
	}
	
	
	@Test(dataProvider="generateTestURLToLinkData")
	public void testURLToLink(String text, String expectedText) {
		Assert.assertEquals(URLUtil.replaceUrlsWithLinks(text), expectedText); 
	}
}
