package testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC011_Snapdeal {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
		
		//Mouse over on Toys, Kids' Fashion & more and click on Toys
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//span[text()=\"Toys, Kids' Fashion & more\"]")).perform();
		
		//Click Educational Toys in Toys & Games
		driver.findElementByXPath("//span[text()='Educational Toys']").click();
		Thread.sleep(3000);
		
		//Click the Customer Rating 4 star and Up 
		driver.findElementByXPath("//label[@for='avgRating-4.0']").click();
		Thread.sleep(3000);
		
		//Click the offer as 40-50
		driver.findElementByXPath("//label[@for='discount-40%20-%2050']").click();
		Thread.sleep(3000);
		
		//Check the availability for the pincode
		driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("641001");
		driver.findElementByXPath("//button[text()='Check']").click();
		Thread.sleep(3000);
		
		//Click the Quick View of the first product 
		builder.moveToElement(driver.findElementByXPath("(//div[@class='product-tuple-image '])[1]")).perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='clearfix row-disc']/div)[1]"))).click();
		Thread.sleep(3000);
		
		//Click on View Details
		driver.findElementByXPath("//a[@class=' btn btn-theme-secondary prodDetailBtn']").click();
		
		//Capture the Price of the Product and Delivery Charge
		double pdtPrice1 = Double.parseDouble(driver.findElementByXPath("//span[@class='pdp-final-price']/span").getText().trim());
		double deliveryCharge1 = Double.parseDouble((driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText().replaceAll("\\D", "")));
		driver.findElementById("add-cart-button-id").click();
		Thread.sleep(2000);
		
		//Validate the You Pay amount matches the sum of (price+deliver charge)
		double calculatedPrice1 = pdtPrice1 + deliveryCharge1;
		System.out.println("You Pay price of product 1: Rs."+calculatedPrice1);
		double youPayPrice1 = Double.parseDouble(driver.findElementByXPath("//div[@class='you-pay']/span").getText().replaceAll("\\D", ""));
		if(calculatedPrice1==youPayPrice1) {
			System.out.println("You pay amount for product 1 matches the sum of product price and delivery charge");
		} else
			System.out.println("You pay amount for product 1 and sum of product price and delivery charge mismatch");
		
		//Search for Sanitizer
		driver.findElementById("inputValEnter").sendKeys("Sanitizer", Keys.ENTER);
		Thread.sleep(3000);
		
		//Click on Product "BioAyurveda Neem Power Hand Sanitizer"
		driver.findElementByXPath("//p[contains(@title,'BioAyurveda Neem Power  Hand Sanitizer')]").click();
		Thread.sleep(2000);
		
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		driver.switchTo().window(handles.get(1));
		
		//Capture the Price and Delivery Charge
		double pdtPrice2 = Double.parseDouble(driver.findElementByXPath("//span[@class='pdp-final-price']/span").getText().trim());
		double deliveryCharge2 = Double.parseDouble(driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText().replaceAll("\\D", ""));
		double calculatedPrice2 = pdtPrice2 + deliveryCharge2;
		System.out.println("You Pay price of product 2: Rs."+calculatedPrice2);
		
		//Click on Add to Cart
		driver.findElementById("add-cart-button-id").click();
		Thread.sleep(2000);
		
		//Click on Cart 
		driver.findElementByXPath("//div[@class='cartInner']").click();
		Thread.sleep(4000);
		
		//Validate the Proceed to Pay matches the total amount of both the products
		double total = calculatedPrice1 + calculatedPrice2;
		System.out.println("Total calculated price of 2 products: Rs."+total);
		double payPrice = Double.parseDouble(driver.findElementByXPath("//input[contains(@value,'PROCEED TO PAY')]").getAttribute("value").replaceAll("\\D", ""));
		System.out.println("Final price before proceeding to pay: Rs."+payPrice);
		if(total==payPrice) {
			System.out.println("Price match. Proceed to pay");
		} else
			System.out.println("Price mismatch. Do not proceed to pay");
		
		//Close all the windows
		driver.quit();
	}
}
