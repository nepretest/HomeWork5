package com.qatestlab.homework5;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class MainPage {

	Random rand = new Random();
	WebDriver driver;
	WebElement element;
	Logger log;

	public MainPage(WebDriver driver, Logger log) {
		this.driver = driver;
		this.log = log;
	}

	public WebElement findMobilePanel() {
		Reporter.log("Verifying mobile panel");
		log.info("Verifying mobile panel");
		return driver.findElement(By.xpath("//div[@class='hidden-md-up text-xs-center mobile']"));
	}

	public void clickOnAllProducts() {
		Reporter.log("Clicking on All Products btn");
		log.info("Clicking on All Products btn");
		element = driver.findElement(By.xpath("//section/a[contains(@class,'all-product')]"));
		element.click();
		log.info("All Products btn is clicked");
	}

	public String clickOnRandomProductAndGetUrl() {
		Reporter.log("Clicking on random product");
		log.info("Clicking on random product");
		List<WebElement> elements = driver.findElements(By.xpath("//a[@class='thumbnail product-thumbnail']"));
		element = elements.get(rand.nextInt(elements.size()));
		element.click();
		log.info("Random product is clicked");
		return driver.getCurrentUrl();
	}
	
	 public void clickMoreInfo() {
		Reporter.log("Clicking on More Info");
		log.info("Clicking on More Info");
		WebElement element = driver.findElement(By.xpath("//a[@class='nav-link']"));
		element.click();
		log.info("More Info is clicked");
	 }
	 
	 public String getProductPrice() {
		 Reporter.log("Getting product price");
		 log.info("Getting product price");
		 return driver.findElement(By.xpath("//span[@itemprop='price']")).getText();
	 }

	 public String getProductName() {
		 Reporter.log("Getting product name");
		 log.info("Getting product name");
		 return driver.findElement(By.xpath("//h1[@class='h1']")).getText();
	 }
	 
	 
	 public int getProductQuantity(WebDriverWait wait) {
		 Reporter.log("Getting product quantity");
		 log.info("Getting product quantity");
		element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='product-quantities']/span"))));
		String quantity = element.getText();
		return Integer.parseInt(quantity.substring(0, (quantity.length()-7)));
	 }

	public void clickOnAddToCart() {
		Reporter.log("Clicking add to cart btn");
		log.info("Clicking add to cart btn");
		element = driver.findElement(By.xpath("//div[@class='add']/button"));
		element.click();
		log.info("Add to cart btn is clicked");
	}

	public void clickProceedBtn(WebDriverWait wait) {
		Reporter.log("Clicking on proceed btn");
		log.info("Clicking on proceed btn");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@class='btn btn-primary']")))).click();
		log.info("Proceed btn is clicked\n");
	}

	public String verifyOneProductInCart() {
		Reporter.log("Verifying one product in cart");
		log.info("Verifying one product in cart");
		return driver.findElement(By.xpath("//input[@class='js-cart-line-product-quantity form-control']")).getAttribute("value");
	}

	public String verifyProductNameInCart() {
		Reporter.log("Verifying product name in cart");
		log.info("Verifying product name in cart");
		return driver.findElement(By.xpath("//div[@class='product-line-info']/a[@class='label']")).getText();
	}
	
	public String verifyProductPriceInCart() {
		Reporter.log("Verifying product price in cart");
		log.info("Verifying product price in cart");
		return driver.findElement(By.xpath("//span[@class='product-price']/strong")).getText();
	}
	
	public void fillFirstNameField(String randomText, WebDriverWait wait) {
		Reporter.log("Entering first name: " + randomText);
		log.info("Entering first name: " + randomText);
		element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("firstname"))));
		element.sendKeys(randomText);
	}
	
	public void fillLastNameField(String randomText) {
		Reporter.log("Entering last name: " + randomText);
		log.info("Entering last name: " + randomText);
		element = driver.findElement(By.name("lastname"));
		element.sendKeys(randomText);
	}
	
	public void fillEmailField(String randomText) {
		Reporter.log("Entering email: " + randomText + "@test.test");
		log.info("Entering email: " + randomText + "@test.test");
		element = driver.findElement(By.name("email"));
		element.sendKeys(randomText+"@test.test");
	}
	
	public void clickConfirmPersonalData() {
		Reporter.log("Clicking on confirm personal data");
		log.info("Clicking on confirm personal data");
		element = driver.findElement(By.name("continue"));
		element.click();
		log.info("Confirm btn is clicked\n");
	}
	
	public void fillAddressField(String randomText, WebDriverWait wait) {
		Reporter.log("Entering address: " + randomText);
		log.info("Entering address: " + randomText);
		element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("address1"))));
		element.sendKeys(randomText);
	}
	
	public void fillCityField(String randomText) {
		Reporter.log("Entering city: " + randomText);
		log.info("Entering city: " + randomText);
		element = driver.findElement(By.name("city"));
		element.sendKeys(randomText);
	}
	
	public void fillPostCodeField() {
		Reporter.log("Entering postcode: 12345");
		log.info("Entering postcode: 12345");
		element = driver.findElement(By.name("postcode"));
		element.sendKeys("12345");
	}
	
	public void clickConfirmAdressBtn() {
		Reporter.log("Clicking on confirm address");
		log.info("Clicking on confirm address");
		element = driver.findElement(By.name("confirm-addresses"));
		element.click();
		log.info("Confirm btn is clicked");
	}
	
	public void clickConfirmDeliveryBtn() {
		Reporter.log("Clicking on confirm deliviry");
		log.info("Clicking on confirm delivery");
		element = driver.findElement(By.name("confirmDeliveryOption"));
		element.click();
		log.info("Confirm btn is clicked\n");
	}
	
	public void checkPaymentOptionRadio(WebDriverWait wait) {
		Reporter.log("Checking payment option radiobutton");
		log.info("Checking payment option radiobutton");
		element =  driver.findElement(By.id("payment-option-" + (1 + rand.nextInt(2))));
		element.click();
		log.info("Payment option radiobutton is clicked");
	}
	
	public void checkTermAndConditions() {
		Reporter.log("Checking terms and conditions checkbutton");
		log.info("Checking terms and conditions checkbutton");
		element = driver.findElement(By.id("conditions_to_approve[terms-and-conditions]"));
		element.click();
		log.info("Terms and conditions checkbutton is clicked");
	}
	
	public void clickPaymentConfirmation() {
		Reporter.log("Clicking on confirm payment btn");
		log.info("Clicking on confirm payment btn");
		element = driver.findElement(By.xpath("//div[@id='payment-confirmation']//button"));
		element.click();
		log.info("Confirm btn is clicked\n");
	}
	
	public WebElement verifyConfirmationMessage(WebDriverWait wait) {
		Reporter.log("Verifying confirmation message");
		log.info("Verifying confirmation message");
		return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[@class='h1 card-title']"))));
	}
	
	public String verifyProductPriceAfterConfirmation() {
		Reporter.log("Verifying product price after confirmation");
		log.info("Verifying product price after confirmation");
		return driver.findElement(By.xpath("//div[@class='col-xs-5 text-sm-right text-xs-left']")).getText();
	}
	
	public String verifyProductNameAfterConfirmation() {
		Reporter.log("Verifying product name after confirmation");
		log.info("Verifying product name after confirmation");
		return driver.findElement(By.xpath("//div[@class='col-sm-4 col-xs-9 details']//span")).getText();
	}
	
	
}
