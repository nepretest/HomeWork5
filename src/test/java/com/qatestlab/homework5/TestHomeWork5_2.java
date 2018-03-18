package com.qatestlab.homework5;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestHomeWork5_2 extends TestSuiteBase {

	SoftAssert sa = new SoftAssert();
	String randomProductUrl;
	String productPrice;
	String productName;
	int productQuantity;

	@Test
	public void openRandomProduct() {
		wait.until(ExpectedConditions.titleContains("prestashop-automation"));
		mp.clickOnAllProducts();
		randomProductUrl = mp.clickOnRandomProductAndGetUrl();
		productPrice = mp.getProductPrice();
		productName = mp.getProductName();
		try {
			mp.clickMoreInfo();
		} catch (Exception e) {
			log.info("More info btn is disabled");
		}
		productQuantity = mp.getProductQuantity(wait);
		log.info("Random product properties are: Name - " + productName + ", Price - " + productPrice + ", Quantity - "
				+ productQuantity +"\n");
	}

	@Test(dependsOnMethods = { "openRandomProduct" })
	public void addProductToCart() {
		mp.clickOnAddToCart();
		mp.clickProceedBtn(wait);
	}

	@Test(dependsOnMethods = { "addProductToCart" })
	public void verifyProductParameters() {
		sa.assertEquals("1", mp.verifyOneProductInCart(), "Quantity of the product in the cart is not equals to one");
		sa.assertTrue(productName.equalsIgnoreCase(mp.verifyProductNameInCart()),
				"Actual name - " + mp.verifyProductNameInCart() + ", Expected name - " + productName);
		sa.assertEquals(productPrice, mp.verifyProductPriceInCart(),
				"Actual price - " + mp.verifyProductPriceInCart() + ", Expected price - " + productPrice);
		sa.assertAll();
		mp.clickProceedBtn(wait);
		log.info("All product parameters in the cart are correct\n");
	}

	@Test(dependsOnMethods = { "verifyProductParameters" })
	public void fillPersonalDataFileds() {
		mp.fillFirstNameField(randomText(), wait);
		mp.fillLastNameField(randomText());
		mp.fillEmailField(randomText());
		mp.clickConfirmPersonalData();
	}

	@Test(dependsOnMethods = { "fillPersonalDataFileds" })
	public void fillAdressFileds() {
		mp.fillAddressField(randomText(), wait);
		mp.fillPostCodeField();
		mp.fillCityField(randomText());
		mp.clickConfirmAdressBtn();
		mp.clickConfirmDeliveryBtn();
	}

	@Test(dependsOnMethods = { "fillAdressFileds" })
	public void checkPaymentOptions() {
		mp.checkPaymentOptionRadio(wait);
		mp.checkTermAndConditions();
		mp.clickPaymentConfirmation();
	}

	@Test(dependsOnMethods = { "checkPaymentOptions" })
	public void verifyDataAfterPaymentConfirmation() {
		sa.assertTrue(mp.verifyConfirmationMessage(wait).isDisplayed(), "Confirmation message is not displayed");
		sa.assertEquals(productPrice, mp.verifyProductPriceAfterConfirmation(),
				"Actual price - " + mp.verifyProductPriceAfterConfirmation() + ", Expected price - " + productPrice);
		sa.assertTrue(mp.verifyProductNameAfterConfirmation().toLowerCase().startsWith(productName.toLowerCase()),
				"Actual name - " + mp.verifyProductNameAfterConfirmation() + ", Expected name - " + productName);
		sa.assertAll();
		log.info("All product parameters after payment confirmation are correct\n");
	}

	@Test(dependsOnMethods = { "verifyDataAfterPaymentConfirmation" })
	public void verifyProductQuantityReduced() {
		driver.get(randomProductUrl);
		try {
			mp.clickMoreInfo();
		} catch (Exception e) {
			log.info("More info btn is disabled");
		}
		Assert.assertTrue(productQuantity - mp.getProductQuantity(wait) == 1,
				"Actual quantity - " + mp.getProductQuantity(wait) + ", Expected quantity - " + (productQuantity - 1) + "\n");
		log.info("Product quantity is reduced by \n");
	}

}
