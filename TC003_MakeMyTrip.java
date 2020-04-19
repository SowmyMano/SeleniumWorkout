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
import org.openqa.selenium.support.ui.Select;

public class TC003_MakeMyTrip {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
												
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
												
		//Launch URL
		driver.get("https://www.makemytrip.com/");
												
		//Maximize window
		driver.manage().window().maximize();
								
		//Declare wait time
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Click Hotels
		driver.findElementByXPath("//li[@class='menu_Hotels']").click();
		
		//Enter city as Goa, and choose Goa, India
		driver.findElementByXPath("//label[@for='city']").click();
		driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']").sendKeys("Goa");
		Thread.sleep(3000);
		driver.findElementByXPath("//p[text()='Goa, India']").click();
		
		//Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementById("checkin").click();
		driver.findElementByXPath("//div[text()='May']//ancestor::div[@class='DayPicker-Month']//div[text()='15']").click();
		driver.findElementByXPath("//div[text()='May']//ancestor::div[@class='DayPicker-Month']//div[text()='20']").click();
		
		//Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		driver.findElementById("guest").click();
		driver.findElementByXPath("//li[@data-cy='adults-2']").click();
		driver.findElementByXPath("//li[@data-cy='children-1']").click();
		WebElement ageElement = driver.findElementByXPath("//select[@class='ageSelectBox']");
		Select ageDropdown = new Select(ageElement);
		ageDropdown.selectByVisibleText("12");
		driver.findElementByXPath("//button[text()='APPLY']").click();
		
		//Click Search button
		driver.findElementById("hsw_search_button").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//body[@class='bodyFixed overlayWholeBlack']").click();
		
		//Select locality as Candolim
		WebElement localityElement = driver.findElement(By.xpath("//div[@id='hlistpg_fr_locality']//label[text()='Candolim']/preceding-sibling::input"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", localityElement);
		Thread.sleep(2000);
		
		//Select 5 star in Star Category under Select Filters
		WebElement starElement = driver.findElementByXPath("//div[@id='hlistpg_fr_star_category']//label[text()='5 Star']/preceding-sibling::input");
		executor.executeScript("arguments[0].click();", starElement);
		Thread.sleep(2000);
		
		//Click on the first resulting hotel and go to the new window
		driver.findElementByXPath("(//div[@id='Listing_hotel_0'])[1]").click();
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		driver.switchTo().window(handles.get(1));
		
		//Print the Hotel Name 
		System.out.println("Hotel Name: "+driver.findElementById("detpg_hotel_name").getText());
		
		//Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("(//span[text()='MORE OPTIONS'])[1]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("(//table[@class='tblEmiOption']//span)[1]").click();
		Thread.sleep(2000);
		driver.findElementByClassName("close").click();
		
		//Click on BOOK THIS NOW
		driver.findElementById("detpg_headerright_book_now").click();
		Thread.sleep(2000);
		
		//Print the Total Payable amount
		System.out.println("Total Payable Amount: "+driver.findElementById("revpg_total_payable_amt").getText());
		Thread.sleep(2000);
		
		//Close the browser
		driver.quit();
	}
}
