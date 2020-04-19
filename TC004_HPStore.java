package testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC004_HPStore {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
								
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
										
		//Launch URL
		driver.get("https://store.hp.com/in-en/");
										
		//Maximize window
		driver.manage().window().maximize();
						
		//Declare wait time
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Mouse over on Laptops menu and click on Pavilion
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//li[@class='level0 nav-2 level-top ui-menu-item laptops-tablets-li']")).perform();
		driver.findElementByXPath("(//span[text()='Pavilion'])[1]").click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='optanon-alert-box-close banner-close-button']"))).click();
		
		//Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click();
		driver.findElementByXPath("//span[text()='Intel Core i7']/preceding-sibling::input").click();
		Thread.sleep(3000);
		
		//Hard Drive Capacity -->More than 1TB
		driver.findElementByXPath("//span[text()='More than 1 TB']/preceding-sibling::input").click();
		Thread.sleep(3000);
		
		//Select Sort By: Price: Low to High
		WebElement sorter = driver.findElementById("sorter");
		Select sortDropdown = new Select(sorter);
		sortDropdown.selectByValue("price_asc");
		Thread.sleep(3000);
		
		//Print the First resulting Product Name and Price
		System.out.println("Product Name: "+driver.findElementByXPath("(//a[@class='product-item-link'])[1]").getText());
		String price = driver.findElementByXPath("(//span[@data-price-type='finalPrice'])[1]").getText().replaceAll("\\D", "");
		System.out.println("Product Price: "+price);

		//Click on Add to Cart
		driver.findElementByXPath("(//span[text()='Add To Cart'])[1]").click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inside_closeButton fonticon icon-hclose']"))).click();
		
		//Click on Shopping Cart icon --> Click on View and Edit Cart
		driver.findElementByXPath("//a[@class='action showcart']").click();
		driver.findElementByXPath("//a[@class='action primary viewcart']").click();
		Thread.sleep(3000);
		
		//Check the Shipping Option --> Check availability at Pincode
		driver.findElementByName("pincode").sendKeys("641001");
		driver.findElementByXPath("//button[text()='check']").click();
		Thread.sleep(3000);
		if(driver.findElementByXPath("//span[@class='available']").getText().equalsIgnoreCase("In stock")){
			System.out.println("Product in stock");
		}
		
		//Verify the order Total against the product price. 
		if(driver.findElementByXPath("//tr[@class='grand totals']//span").getText().replaceAll("\\D", "").equals(price)) {
			System.out.println("Product price and grand total match. Proceed to checkout");
			//Proceed to Checkout if Order Total and Product Price matches
			driver.findElementByXPath("(//button[@title='Proceed to Checkout'])[1]").click();
		} else {
			System.out.println("Prices do not match");
		}
		Thread.sleep(3000);
		
		//Click on Place Order
		driver.findElementByXPath("//div[@class='col-mp mp-7 place-order-action']//span[text()='Place Order']").click();
		
		//Capture the Error message and Print
		System.out.println("Error message: "+driver.findElementByXPath("//div[@class='message notice']").getText());
		Thread.sleep(2000);
		
		//Close Browser
		driver.close();
	}
}
