package testcases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC013_StudyAbroad {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL. maximize window and declare wait time
		driver.get("https://studyabroad.shiksha.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
		
		//Mouse over on Colleges and click MS in Computer Science &Engg under MS Colleges
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='OK']"))).click();
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("(//label[text()='Colleges '])[1]")).perform();
		driver.findElementByLinkText("MS in Computer Science &Engg").click();
		Thread.sleep(3000);
		
		//Select GRE under Exam Accepted and Score 300 & Below
		driver.findElementByXPath("//p[text()='GRE']/parent::label").click();
		Thread.sleep(2000);
		WebElement element = driver.findElementByXPath("(//select[@class='score-select-field'])[1]");
		Select greScore = new Select(element);
		greScore.selectByVisibleText("300 & below");
		Thread.sleep(2000);
		
		//Max 10 Lakhs under 1st year Total fees, USA under countries
		driver.findElementByXPath("//p[text()='Max 10 Lakhs']/parent::label").click();
		Thread.sleep(2000);
		WebElement country = driver.findElementByXPath("//a[text()='USA']//ancestor::label/span");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", country);
		Thread.sleep(2000);
		
		//Select Sort By: Low to high 1st year total fees
		WebElement sortElement = driver.findElementById("categorySorter");
		Select sortDropdown = new Select(sortElement);
		sortDropdown.selectByValue("fees_ASC");
		Thread.sleep(2000);
		
		//Click Add to compare of the College having least fees with Public University, Scholarship and Accommodation facilities
		List<WebElement> univElements = driver.findElementsByXPath("//div[@class='course-touple clearwidth']");
		List<WebElement> publicList = driver.findElementsByXPath("//p[text()='Public university']/span");
		List<WebElement> scholarshipList = driver.findElementsByXPath("//p[text()='Scholarship']/span");
		List<WebElement> accommodationList = driver.findElementsByXPath("//p[text()='Accommodation']/span");
		List<WebElement> compareBtnList = driver.findElementsByXPath("//div[@class='compare-box flRt customInputs']");
		
		for (int i=0; i<univElements.size(); i++) {
			String university = publicList.get(i).getAttribute("class");
			String scholarship = scholarshipList.get(i).getAttribute("class");
			String accommodation = accommodationList.get(i).getAttribute("class");
			
			if(university.equalsIgnoreCase("tick-mark") && scholarship.equalsIgnoreCase("tick-mark") && accommodation.equalsIgnoreCase("tick-mark")) {
				compareBtnList.get(i).click();
				break;
			}
		}
		Thread.sleep(3000);
		
		//Select the first college under Compare with similar colleges
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[@class='sticky-suggestion-list']/li)[1]"))).click();
		Thread.sleep(3000);
		
		//Click on Compare College>
		driver.findElementByXPath("//a/strong[text()='Compare Colleges >']").click();
		Thread.sleep(2000);
		
		//Select When to Study as 2021
		driver.findElementByXPath("//label[@for='year1']").click();
		
		//Select Preferred Countries as USA
		WebElement prefCountry = driver.findElementByXPath("//div[text()='Preferred Countries']");
		executor.executeScript("arguments[0].click();", prefCountry);
		driver.findElementByXPath("//label[contains(@for,'USA')]").click();
		
		//Select Level of Study as Masters
		driver.findElementByXPath("//label[contains(@for,'Masters')]").click();
		
		//Select Preferred Course as MS
		WebElement prefCourse = driver.findElementByXPath("//div[text()='Preferred Course']");
		executor.executeScript("arguments[0].click();", prefCourse);
		driver.findElementByXPath("//li[text()='MS']").click();
		
		//Select Specialization as "Computer Science & Engineering"
		WebElement prefSpecialization = driver.findElementByXPath("//div[text()='Preferred Specialisations']");
		executor.executeScript("arguments[0].click();", prefSpecialization);
		driver.findElementByXPath("//li[text()='Computer Science & Engineering']").click();
		Thread.sleep(2000);
		
		//Click on Sign Up
		driver.findElementById("signup").click();
		Thread.sleep(5000);
		
		//Print all the warning messages displayed on the screen for missed mandatory fields
		List<WebElement> errorMessages = driver.findElementsByXPath("//div[contains(text(),'Please')]");
		for (WebElement webElement : errorMessages) {
			if(webElement.isDisplayed()) {
				System.out.println(webElement.getText());
			}			
		}
		
		driver.close();
	}
}