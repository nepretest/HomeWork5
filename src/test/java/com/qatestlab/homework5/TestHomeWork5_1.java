package com.qatestlab.homework5;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestHomeWork5_1 extends TestSuiteBase {
	

	
	@Test
	public void verifyMobilePage() {
		wait.until(ExpectedConditions.titleContains("prestashop-automation"));
		if(getBrowserName.contains("mobile")) {
			Assert.assertTrue(mp.findMobilePanel().isDisplayed(), "Wrong website version is displayed");
			log.info("Website version is correct");
		} else {
			Assert.assertFalse(mp.findMobilePanel().isDisplayed(), "Wrong website version is displayed");
			log.info("Website version is correct");
		}
	}
}
