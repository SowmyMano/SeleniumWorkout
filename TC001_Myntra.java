package testcases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC001_Myntra {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
								
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
								
		//Launch URL
		driver.get("https://www.myntra.com/");
								
		//Maximize window
		driver.manage().window().maximize();
				
		//Declare wait time
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Mouse hover on WOMEN 
		WebElement womenElement = driver.findElementByXPath("(//a[text()='Women'])[1]");
		Actions builder = new Actions(driver);
		builder.moveToElement(womenElement).perform();
		
		//Click Jackets & Coats
		driver.findElementByXPath("//a[text()='Jackets & Coats']").click();
		
		//Find the total count of item (top) -> getText -> String
		String itemCount = driver.findElementByClassName("title-count").getText();
		int count = Integer.parseInt(itemCount.replaceAll("\\D", ""));
		System.out.println("Total no. of items: "+count);
		
		//Validate the sum of categories count matches
		String catCount1 = driver.findElementByXPath("(//span[@class='categories-num'])[1]").getText();
		int cat1 = Integer.parseInt(catCount1.replaceAll("\\D", ""));
		String catCount2 = driver.findElementByXPath("(//span[@class='categories-num'])[2]").getText();
		int cat2 = Integer.parseInt(catCount2.replaceAll("\\D", ""));
		int total = cat1 + cat2;
		System.out.println("Total sum of categories: "+total);
		
		if(total==count) {
			System.out.println("Count matches");
		}
		
		//Check Coats
		driver.findElementByXPath("(//ul[@class='categories-list']//div)[2]").click();
		Thread.sleep(3000);
		
		//Click + More option under BRAND
		driver.findElementByClassName("brand-more").click();
		
		//Type MANGO and click checkbox
		driver.findElementByClassName("FilterDirectory-searchInput").sendKeys("MANGO");
		driver.findElementByXPath("//ul[@class='FilterDirectory-list']//div").click();
		
		//Close the pop-up x
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();
		Thread.sleep(3000);
		
		//Confirm all the Coats are of brand MANGO
		List<WebElement> brandName = driver.findElementsByClassName("product-brand");
		int cnt = 0;
		for (WebElement webElement : brandName) {
			if(webElement.getText().equalsIgnoreCase("MANGO")) {
				cnt = cnt+1;
		}
		}
		if(cnt==brandName.size()) {
			System.out.println("All coats are of brand MANGO");
		}

		//Sort by Better Discount
		WebElement sort = driver.findElementByClassName("sort-sortBy");
		builder.moveToElement(sort).perform();
		driver.findElementByXPath("//label[text()='Better Discount']").click();
		Thread.sleep(2000);

		//Find the price of first displayed item
		List<WebElement> price = driver.findElementsByClassName("product-discountedPrice");
		System.out.println("Price of product: "+price.get(0).getText());
		
		//Mouse over on size of the first item
		WebElement size = driver.findElementByXPath("(//h4[@class='product-product'])[1]");
		builder.moveToElement(size).perform();
		Thread.sleep(2000);
		
		//Click on WishList Now
		driver.findElementByXPath("(//span[text()='wishlist now'])[1]").click();

		//Close Browser
		driver.close();
	}

}
