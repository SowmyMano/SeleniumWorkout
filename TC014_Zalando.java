package testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC014_Zalando {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
																
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
																
		//Launch URL
		driver.get("https://www.zalando.com/");
		
		//Get the Alert text and print it
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Thread.sleep(2000);
		System.out.println("Alert message: "+alert.getText());
		alert.accept();
		
		//Maximize window and declare wait time
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Close the Alert box and click on Zalando.uk
		driver.findElementByXPath("//a[text()='Zalando.uk']").click();
		Thread.sleep(3000);
		
		//Click Women--> Clothing and click Coat
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='uc-btn-accept-banner']"))).click();
		driver.findElementByXPath("(//span[text()='Women'])[1]").click();
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//span[text()='Clothing']")).perform();
		driver.findElementByXPath("//span[text()='Coats']").click();
		Thread.sleep(4000);
		
		//Choose Material as cotton (100%) and Length as thigh-length
		driver.findElementByXPath("//span[text()='Material']").click();
		driver.findElementByXPath("//span[text()='cotton (100%)']").click();
		driver.findElementByXPath("//button[@aria-label='apply the Material filter']").click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Length']"))).click();
		driver.findElementByXPath("//span[text()='thigh-length']").click();
		driver.findElementByXPath("//button[@aria-label='apply the Length filter']").click();
		Thread.sleep(2000);
		
		//Click on Q/S designed by MANTEL - Parka coat
		driver.findElementByXPath("//div[text()='Q/S designed by']").click();
		Thread.sleep(2000);
		
		//Check the availability for Color as Olive and Size as 'M'
		driver.findElementByXPath("(//img[@alt='olive'])[2]").click();
		try {
			String message = driver.findElementByXPath("//h2[contains(text(),'Out')]").getText();
			if(message.equalsIgnoreCase("Out of stock")) {
				System.out.println("Olive out of stock");
				driver.findElementByXPath("(//img[@alt='navy'])[2]").click();
			} else
				System.out.println("Olive is in stock");
		}
		catch (Exception e) {
			System.out.println(e);
		}
				
		//If the previous preference is not available, check  availability for Color Navy and Size 'M'
		driver.findElementByXPath("//button[@aria-label='Choose your size']").click();
		driver.findElementByXPath("//span[text()='M']//ancestor::li").click();
		
		//Add to bag only if Standard Delivery is free
		String delivery = driver.findElementByXPath("(//span[text()='Standard delivery']/following::span)[1]").getText();
		if(delivery.equalsIgnoreCase("Free")) {
			System.out.println("Standard Delivery is free");
			driver.findElementByXPath("//button[@aria-label='Add to bag']").click();
		} else
			System.out.println("Standard Delivery is not free");
		
		//Mouse over on Your Bag and Click on "Go to Bag"
		builder.moveToElement(driver.findElementByXPath("//span[text()='Your bag']//ancestor::a")).perform();
		driver.findElementByXPath("//div[text()='Go to bag']").click();
		
		//Capture the Estimated Deliver Date and print
		String estimatedDelDate = driver.findElementByXPath("(//h2[text()='Estimated delivery']/following::span)[1]").getText();
		System.out.println("Estimated Delivery: "+estimatedDelDate);
		
		//Mouse over on FREE DELIVERY & RETURNS*, get the tool tip text and print
		String tooltip = driver.findElementByXPath("//a[text()='Free delivery & returns*']/parent::span").getAttribute("title");
		System.out.println("Free delivery & returns* Tool tip: "+tooltip);
		
		//Click on FREE DELIVERY & RETURNS
		driver.findElementByXPath("//a[text()='Free delivery & returns*']").click();
		Thread.sleep(6000);
		
		//Click on Start chat in the Start chat and go to the new window
		driver.findElementByXPath("//span[text()='Start chat']/parent::button").click();
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windows = new ArrayList<String>(windowHandles);
		driver.switchTo().window(windows.get(1));
		
		//Enter you first name and a dummy email and click Start Chat
		driver.findElementById("prechat_customer_name_id").sendKeys("Soundharya");
		driver.findElementById("prechat_customer_email_id").sendKeys("sou@vmail.com");
		driver.findElementByName("Start Chat").click();
		
		//Type Hi, click Send and print thr reply message and close the chat window
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("liveAgentChatTextArea"))).sendKeys("Hi");
		driver.findElementByXPath("//button[text()='Send']").click();
		String clientMessage = driver.findElementByXPath("//span[@class='client']/following-sibling::span//span[@class='messageText']").getText();
		System.out.println("Response from Zalando: "+clientMessage);
		Thread.sleep(3000);
		driver.close();
	}
}
