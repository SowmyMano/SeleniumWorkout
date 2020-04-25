package testcases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC007_Honda {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
												
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
												
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='close']"))).click();

		//Click on scooters and click dio
		driver.findElementById("link_Scooter").click();
		driver.findElementByXPath("(//div[@id=\"scooter\"]//div[@class='item'])[1]").click();
		Thread.sleep(2000);
		
		//Click on Specifications and mouse over on ENGINE
		driver.findElementByLinkText("Specifications").click();
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//li[@class='specificationsLi wow bounceInLeft']/a[text()='ENGINE']")).perform();
		
		//Get Displacement value
		float dioDisplacement = Float.parseFloat(driver.findElementByXPath("//div[@class='engine part-2 axx']//span[text()='Displacement']/following-sibling::span").getText().replaceAll("c", ""));
		
		//Go to Scooters and click Activa 125
		driver.findElementById("link_Scooter").click();
		driver.findElementByXPath("(//div[@id=\"scooter\"]//div[@class='item'])[3]").click();
		Thread.sleep(2000);
		
		//Click on Specifications and mouse over on ENGINE
		driver.findElementByLinkText("Specifications").click();
		builder.moveToElement(driver.findElementByXPath("//div[@class='specifications-box col-md-4 col-sm-4 wow bounceInLeft']//a[text()='ENGINE']")).perform();
		
		//Get Displacement value
		float activaDisplacement = Float.parseFloat(driver.findElementByXPath("//div[@class='engine part-4 axx']//span[text()='Displacement']/following-sibling::span").getText().replaceAll("c", ""));
		
		//Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
		if(dioDisplacement > activaDisplacement) {
		System.out.println("Honda Dio has better displacement");	 
		} else
			System.out.println("Honda Activa 125 has better displacement");
		
		//Click FAQ from Menu 
		driver.findElementByXPath("//ul[@class='nav navbar-nav']//a[text()='FAQ']").click();
		
		//Click Activa 125 BS-VI under Browse By Product
		driver.findElementByXPath("//a[text()='Activa 125 BS-VI']").click();
		
		//Click  Vehicle Price
		driver.findElementByXPath("//a[text()=' Vehicle Price']").click();
		
		//Make sure Activa 125 BS-VI selected and click submit
		WebElement element = driver.findElementById("ModelID6");
		Select dropdown = new Select(element);
		if(dropdown.getFirstSelectedOption().getText().equalsIgnoreCase("Activa 125 BS-VI")) {
			System.out.println("Correct selection in dropdown");
			driver.findElementById("submit6").click();
		}	
		
		//Click the price link
		driver.findElementByXPath("//table[@id='tblPriceMasterFilters']//a").click();
		
		//Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		driver.switchTo().window(handles.get(1));
		
		WebElement stateElement = driver.findElementById("StateID");
		Select stateDropdown = new Select(stateElement);
		stateDropdown.selectByVisibleText("Tamil Nadu");
		
		WebElement cityElement = driver.findElementById("CityID");
		Select cityDropdown = new Select(cityElement);
		cityDropdown.selectByVisibleText("Chennai");
		
		//Click Search
		driver.findElementByXPath("//button[text()='Search']").click();
		
		//Print all the 3 models and their prices
		WebElement priceTable = driver.findElementById("gvshow");
		List<WebElement> rows = priceTable.findElements(By.tagName("tr"));
		List<WebElement> name = priceTable.findElements(By.xpath("//td[contains(text(),'ACTIVA')]"));
		List<WebElement> price = priceTable.findElements(By.xpath("//td[contains(text(),'Rs')]"));
		
		Map<String, String> priceMap = new HashMap<String, String>();
		for(int i=0; i<=rows.size();i++) {
			priceMap.put(name.get(i).getText(), price.get(i).getText());
		}
		
		for (Map.Entry<String, String> entry : priceMap.entrySet()) {
			System.out.println(entry.getKey()+" = "+entry.getValue());
		}

		//Close the Browser
		driver.quit();
	}
}
