package testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC016_Ajio {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		  
		//Enter Bags in the Search field and Select Bags in Women Handbags
		driver.findElementByXPath("//input[@placeholder='Search AJIO']").click();
		driver.findElementByXPath("//input[@placeholder='Search AJIO']").sendKeys("Bags");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Bags in ']/following-sibling::span[text()='Women Handbags']//ancestor::li"))).click();
		Thread.sleep(4000);
		
		//Click on five grid and Select SORT BY as "What's New"
		driver.findElementByXPath("//div[@class='five-grid-container ']").click();
		Thread.sleep(2000);
		WebElement dropdown = driver.findElementByXPath("//option[text()='Relevance']/parent::select");
		Select sort = new Select(dropdown);
		sort.selectByVisibleText("What's New");
		Thread.sleep(3000);
		
		//Enter Price Range Min as 2000 and Max as 5000  
		driver.findElementByXPath("//span[text()='price']").click();
		driver.findElementById("minPrice").sendKeys("2000");
		driver.findElementById("maxPrice").sendKeys("5000");
		Thread.sleep(1000);
		driver.findElementByXPath("//div[@class='facet-min-price-filter']/button").click();
		Thread.sleep(5000);
		
		//Click on the product "Puma Ferrari LS Shoulder Bag" 
		driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click();
		Thread.sleep(6000);
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windows = new ArrayList<String>(windowHandles);
		driver.switchTo().window(windows.get(1));
		
		//Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
		double pdtPrice = Double.parseDouble(driver.findElementByXPath("//div[@class='prod-sp']").getText().replaceAll("\\D", ""));
		if(pdtPrice > 2690) {
			System.out.println("Product eligible for discount");
		} else
			System.out.println("Product not eligible for discount");
		String offerCode = driver.findElementByXPath("//div[@class='promo-title']").getText().replaceAll("Use Code", "");
		System.out.println(offerCode);
		Thread.sleep(3000);
		
		double discPrice = Double.parseDouble(driver.findElementByXPath("//div[@class='promo-discounted-price']/span").getText().replaceAll("\\D", ""));
		double discFinal = pdtPrice - discPrice;
		long discount = Math.round(discFinal);
		System.out.println("Discount: Rs."+discount);
		
		//Check the availability of the product for pincode 560043, print the expected delivery date if it is available
		driver.findElementByXPath("//div[@class='edd-pincode-msg-container']").click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("pincode"))).sendKeys("641002");
		driver.findElementByXPath("//button[text()='CONFIRM PINCODE']").click();
		Thread.sleep(2000);
		String date = driver.findElementByXPath("//li[text()='Expected Delivery: ']/span").getText();
		System.out.println("Expected Delivery: "+date);
		
		//Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByXPath("//div[@class='other-info-toggle']").click();
		Thread.sleep(3000);
		String custCare = driver.findElementByXPath("//span[text()='Customer Care Address']/following-sibling::span[@class='other-info']").getText();
		System.out.println("Customer Care Details");
		System.out.println("=====================");
		System.out.println(custCare);
		
		//Click on ADD TO BAG and then GO TO BAG  
		driver.findElementByXPath("//div[@class='pdp-addtocart-button']").click();
		Thread.sleep(3000);
		WebElement gotToBagEle = driver.findElementByXPath("//span[text()='GO TO BAG']");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", gotToBagEle);
		Thread.sleep(2000);
		
		//Check the Order Total before apply coupon 
		String totalBefCoupon = driver.findElementByXPath("(//section[@id='orderTotal']/span)[2]").getText();
		totalBefCoupon = totalBefCoupon.substring(4);
		double orderTotal = Double.parseDouble(totalBefCoupon.replaceAll(",", ""));
		System.out.println("Order total before coupon: Rs."+orderTotal);
		
		//Enter Coupon Code and Click Apply
		driver.findElementByXPath("//input[@placeholder='Enter coupon code']").sendKeys(offerCode);
		driver.findElementByXPath("//button[text()='Apply']").click();
		Thread.sleep(3000);
		
		//Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
		String savingsText = driver.findElementByXPath("//span[text()='Coupon savings']/following-sibling::span").getText();
		savingsText = savingsText.substring(4);
		double replacedText = Double.parseDouble(savingsText.replace(",", ""));
		long savings = Math.round(replacedText);
		System.out.println("Savings: Rs."+savings);
		if(discount==savings) {
			System.out.println("Savings matches calculated discount");
		} else
			System.out.println("Savings does not match calculated discount");
		
		//Click on Delete and Delete the item from Bag  
		driver.findElementByXPath("//div[text()='Delete']").click();
		driver.findElementByXPath("//div[text()='DELETE']").click();
		Thread.sleep(3000);
		
		//Close all the browsers
		driver.quit();
	}
}