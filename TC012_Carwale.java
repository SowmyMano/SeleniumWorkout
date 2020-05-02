package testcases;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC012_Carwale {

	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.carwale.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
		
		//Click on Used
		driver.findElementByXPath("(//ul[@id='newUsedSearchOption']/li)[2]").click();
		
		//Select the City as Chennai
		driver.findElementById("usedCarsList").click();
		driver.findElementById("usedCarsList").sendKeys("Chennai", Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content']//a[contains(@cityname,'chennai')]"))).click();
		
		//Select budget min (8L) and max(12L) and Click Search
		driver.findElementByXPath("//ul[@id='minPriceList']/li[text()='8 Lakh']").click();
		driver.findElementByXPath("//ul[@id='maxPriceList']/li[text()='12 Lakh']").click();
		Thread.sleep(2000);
		driver.findElementById("btnFindCar").click();
		Thread.sleep(2000);
		
		//Select Cars with Photos under Only Show Cars With
		driver.findElementByName("CarsWithPhotos").click();
		Thread.sleep(2000);
		
		//Select Manufacturer as "Hyundai" --> Creta
		WebElement element = driver.findElementByXPath("//li[@data-manufacture-en='Hyundai']");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		Thread.sleep(2000);
		WebElement subElement = driver.findElementByXPath("//span[text()='Creta']/parent::li");
		executor.executeScript("arguments[0].click();", subElement);
		Thread.sleep(2000);
		
		//Select Fuel Type as Petrol
		WebElement fuelElement = driver.findElementByXPath("//div[@name='fuel']");
		executor.executeScript("arguments[0].click();", fuelElement);
		WebElement fuelType = driver.findElementByXPath("//li[@name='Petrol']");
		executor.executeScript("arguments[0].click();", fuelType);
		Thread.sleep(2000);
	
		//Select Best Match as "KM: Low to High"
		WebElement sortElement = driver.findElementById("sort");
		Select sortDropdown = new Select(sortElement);
		sortDropdown.selectByVisibleText("KM: Low to High");
		Thread.sleep(2000);
		
		//Validate the Cars are listed with KMs Low to High
		List<WebElement> kmList = driver.findElementsByXPath("//td[contains(@title,'km')]");
		List<Integer> kmListAsDisplayed = new ArrayList<Integer>();
		for (WebElement kmElement : kmList) {
			kmListAsDisplayed.add(Integer.parseInt(kmElement.getAttribute("title").replaceAll("\\D", "")));
		}
		System.out.println(kmListAsDisplayed);
		
		List<Integer> kmListSorted = new ArrayList<Integer>(kmListAsDisplayed);
		Collections.sort(kmListSorted);
		System.out.println(kmListSorted);
		
		boolean sorted = false;
		for(int i=0; i<kmListAsDisplayed.size(); i++) {
			if(kmListAsDisplayed.get(i)==kmListSorted.get(i)) {
				sorted = true;
			} else {
				sorted = false;
				break;
			}
		}
		
		if(sorted==true) {
			System.out.println("Cars are listed in order KM:Low to High");
		} else
			System.out.println("Cars are not listed in order KM:Low to High");
		Thread.sleep(3000);
		
		//Add the least KM ran car to Wishlist
		int leastKm = kmListSorted.get(0);
		NumberFormat nFormat = NumberFormat.getInstance();
		nFormat.setGroupingUsed(true);
		String formattedKm = nFormat.format(leastKm);
		WebElement wishElement = driver.findElementByXPath("//span[contains(text(),\""+formattedKm+"\")]//ancestor::div[@class='card-detail-block']/preceding-sibling::div//span[contains(@class,'shortlist-icon')]");
		executor.executeScript("arguments[0].click()", wishElement);
		
		//Go to Wishlist and Click on More Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class,'action-box shortlistBtn')]"))).click();
		Thread.sleep(2000);
		driver.findElementByXPath("//a[contains(text(),'More details')]").click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		driver.switchTo().window(handles.get(1));
		
		//Print all the details under Overview 
		List<WebElement> headingElements = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width text-light-grey']");
		List<WebElement> valueElements = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width dark-text']");
		Map<String, String> overview = new LinkedHashMap<String, String>();
		
		for(int i=0; i<headingElements.size(); i++) {
			overview.put(headingElements.get(i).getText(), valueElements.get(i).getText());
		}
		
		System.out.println("Car Overview");
		System.out.println("=============");
		for (Map.Entry<String, String> entry : overview.entrySet()) {
			System.out.println(entry.getKey()+" = "+entry.getValue());
		}
		Thread.sleep(3000);
		
		//Close the browser
		driver.quit();
	}

}
