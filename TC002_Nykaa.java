package testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC002_Nykaa {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
										
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
										
		//Launch URL
		driver.get("https://www.nykaa.com/");
										
		//Maximize window
		driver.manage().window().maximize();
						
		//Declare wait time
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Mouseover on Brands and Mouseover on Popular
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByClassName("menu-dropdown-icon")).perform();
		Thread.sleep(2000);
		
		builder.moveToElement(driver.findElementByXPath("(//div[@class='BrandsCategoryHeading']/a)[1]")).perform();
		
		//Click L'Oreal Paris
		driver.findElementByXPath("(//li[@class='brand-logo menu-links'])[5]").click();

		//Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		driver.switchTo().window(handles.get(1));
		
		if(driver.getTitle().contains("L'Oreal Paris")) {
			System.out.println("Title contains L'Oreal Paris");
		}

		//Click sort By and select customer top rated
		driver.findElementByXPath("//span[@class='pull-left']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()='customer top rated']").click();
		Thread.sleep(3000);

		//Click Category and click Shampoo
		driver.findElementByXPath("(//div[@class='filter-sidebar__filter-wrap'])[1]").click();
		driver.findElementByXPath("(//label[@for='chk_Shampoo_undefined']/div)[1]").click();
		
		//Check whether the Filter is applied with Shampoo
		if(driver.findElementByXPath("//ul[@class='pull-left applied-filter-lists']/li").getText().contains("Shampoo")){
			System.out.println("Filter applied with Shampoo");
		}
		
		//Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElementByXPath("//span[text()=\"L'Oreal Paris Colour Protect Shampoo\"]").click();
		
		//Go to the new window and select size as 175ml
		Set<String> windowHandles2 = driver.getWindowHandles();
		List<String> handles2 = new ArrayList<String>(windowHandles2);
		driver.switchTo().window(handles2.get(2));
		driver.findElementByXPath("//span[text()='175ml']").click();
		
		//Print the MRP of the product
		System.out.println("The MRP of the product: ₹"+driver.findElementByXPath("(//span[@class='post-card__content-price-offer'])[1]").getText().replaceAll("\\D", ""));

		//Click on ADD to BAG
		driver.findElementByXPath("(//button[@class='combo-add-to-btn prdt-des-btn js--toggle-sbag nk-btn nk-btn-rnd atc-simple m-content__product-list__cart-btn  '])[1]").click();
		
		//Go to Shopping Bag 
		driver.findElementByClassName("AddBagIcon").click();
		
		//Print the Grand Total amount
		System.out.println("Grand Total: ₹"+driver.findElementByXPath("//div[@class='first-col']").getText().replaceAll("\\D", ""));
		
		//Click Proceed
		driver.findElementByXPath("//button[@class='btn full fill no-radius proceed ']").click();
		
		//Click on Continue as Guest
		driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']").click();
		
		//Print the warning message (delay in shipment)
		System.out.println(driver.findElementByXPath("//div[@class='layout horizontal p10 communication-msg mtb10']").getText());
		Thread.sleep(3000);
		
		//Close all windows
		driver.quit();
	}
}
