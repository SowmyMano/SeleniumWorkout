package testcases;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC017_Azure {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL. maximize window and declare wait time
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		//Click on Pricing
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Pricing']"))).click();
		
		//Click on Pricing Calculator
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Pricing calculator')]"))).click();
		
		//Click on Containers 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Containers']"))).click();

		//Select Container Instances
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@title='Container Instances'])[2]"))).click();
		
		//Click on Container Instance Added View
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-module-loc"))).click();
		
		//Select Region as "South India"
		WebElement region = driver.findElementByName("region");
		Select regionDropdown = new Select(region);
		regionDropdown.selectByVisibleText("South India");
		Thread.sleep(1000);
		
		//Set the Duration as 180000 seconds
		driver.findElementByXPath("(//input[@name='seconds'])[1]").sendKeys(Keys.BACK_SPACE, "80000");
		Thread.sleep(1000);
		
		//Select the Memory as 4GB 
		WebElement memory = driver.findElementByName("memory");
		Select memDropdown = new Select(memory);
		memDropdown.selectByVisibleText("4 GB");
		Thread.sleep(1000);
		
		//Enable SHOW DEV/TEST PRICING
		driver.findElementByXPath("//button[@id='devtest-toggler']").click();
		Thread.sleep(3000);
		
		//Select Indian Rupee as currency  
		WebElement currency = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currencyDropdown = new Select(currency);
		currencyDropdown.selectByValue("INR");
		Thread.sleep(2000);
		
		//Print the Estimated monthly price
		String amount = driver.findElementByXPath("(//div[contains(@class,'estimate-total')]//span[@class='numeric'])[2]/span").getText();
		amount = amount.substring(1);
		float estimate = Float.parseFloat(amount);
		System.out.println("Estimated monthly cost: Rs."+estimate);
		
		//Click on Export to download the estimate as excel
		driver.findElementByXPath("//button[text()='Export']").click();
		Thread.sleep(4000);
		
		//Verify the downloaded file in the local folder
		File download = new File("C:\\Users\\Soundharya_M\\Downloads\\ExportedEstimate.xlsx");
		if(download.exists()) {
			System.out.println("Downloaded file available in local folder");
		} else
			System.out.println("Downloaded file not available in local folder");
		
		//Navigate to Example Scenarios and Select CI/CD for Containers
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//li[@id='solution-architectures-picker']")).click().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='CI/CD for Containers']"))).click();
		Thread.sleep(2000);
		
		//Click Add to Estimate  
		WebElement addToEstimate = driver.findElementByXPath("//button[text()='Add to estimate']");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", addToEstimate);
		Thread.sleep(2000);
		
		//Change the Currency as Indian Rupee
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='estimate-tabs']//a[text()='CI/CD for containers']")));
		WebElement exampleCurr = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select exampleCurrDropdown = new Select(exampleCurr);
		exampleCurrDropdown.selectByValue("INR");
		Thread.sleep(2000);
		
		//Enable SHOW DEV/TEST PRICING  
		driver.findElementByXPath("//button[@id='devtest-toggler']").click();
		Thread.sleep(3000);
		
		//Export the Estimate
		driver.findElementByXPath("//button[text()='Export']").click();
		Thread.sleep(4000);
		
		//Verify the downloded file in the local folder
		File download1 = new File("C:\\Users\\Soundharya_M\\Downloads\\ExportedEstimate (1).xlsx");
		if(download1.exists()) {
			System.out.println("Downloaded file available in local folder");
		} else
			System.out.println("Downloaded file not available in local folder");
		
		//Close browser
		driver.close();
	}
}