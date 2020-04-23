package testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC006_BigBasket {

	public static void main(String[] args) throws InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
										
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
										
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.bigbasket.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Mouse over on  Shop by Category 
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//a[@qa='categoryDD']")).perform();
		Thread.sleep(2000);
		
		//Go to FOODGRAINS, OIL & MASALA --> RICE & RICE PRODUCTS 
		builder.moveToElement(driver.findElementByLinkText("Foodgrains, Oil & Masala")).perform();
		builder.moveToElement(driver.findElementByLinkText("Rice & Rice Products")).perform();
		
		//Click on Boiled & Steam Rice
		driver.findElementByLinkText("Boiled & Steam Rice").click();
		Thread.sleep(3000);
		
		//Choose the Brand as bb Royal
		driver.findElementByXPath("(//span[text()='bb Royal']/preceding-sibling::span//i)[1]").click();
		Thread.sleep(3000);
		
		//Go to Ponni Boiled Rice - Super Premium and select 5kg bag from Dropdown
		driver.findElementByXPath("(//a[text()='Ponni Boiled Rice - Super Premium']/parent::div//following-sibling::div)[1]").click();
//		Thread.sleep(2000);
		driver.findElementByXPath("(//a[text()='Ponni Boiled Rice - Super Premium']/parent::div//following-sibling::div)[1]//ul[@class='dropdown-menu drop-select']//span[text()='5 kg']").click();
		Thread.sleep(2000);
		
		//print the price of Rice
		int riceCost = Integer.parseInt(driver.findElementByXPath("(//a[text()='Ponni Boiled Rice - Super Premium']/parent::div//following-sibling::div)[2]//span[@class='discnt-price']").getText().replaceAll("\\D", ""));
		System.out.println("Price of Rice: Rs."+riceCost);
		
		//Click Add button
		driver.findElementByXPath("(//a[text()='Ponni Boiled Rice - Super Premium']/parent::div//following-sibling::div)[2]//button[@qa='add']").click();
		Thread.sleep(2000);
		driver.findElementById("city-drop-overlay").click();
				
		//Type Dal in Search field and enter
		driver.findElementByXPath("(//input[@placeholder='Search for Products..'])[1]").sendKeys("Dal", Keys.ENTER);
		Thread.sleep(3000);
		
		//Go to Toor/Arhar Dal and select 2kg & set Qty 2 
		driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[1]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[1]//ul//span[text()='2 kg']").click();
		driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[2]//input[@ng-model='vm.startQuantity']").clear();
		driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[2]//input[@ng-model='vm.startQuantity']").sendKeys("2");
		
		//Print the price of Dal
		int dalCost = Integer.parseInt(driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[2]//span[@class='discnt-price']").getText().replaceAll("\\D", ""));
		System.out.println("Price of Dal: Rs."+dalCost);
		
		//Click Add button
		driver.findElementByXPath("(//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/parent::div//following-sibling::div)[2]//button[@qa='add']").click();
		
		//Verify the success message displayed 
		String successMessage = driver.findElementByXPath("//div[@class='toast-title']").getText();
		if(successMessage.equalsIgnoreCase("Successfully added Toor/Arhar Dal/Thuvaram Paruppu 2 kg to the basket")) {
			System.out.println("Success message displayed as expected");
			driver.findElementByXPath("//button[@class='toast-close-button']").click();
		} else
			System.out.println("Message mismatch");
		
		//Mouse hover on My Basket 
		builder.moveToElement(driver.findElementByXPath("//div[@class='dropdown checkout-section hvr-drop hidden-xs hidden-sm']")).perform();
		Thread.sleep(3000);
		int subTotal = (int) Float.parseFloat(driver.findElementByXPath("//span[@qa='subTotalMB']").getText());
		
		//Validate the Sub Total displayed for the selected items
		int total = riceCost + (dalCost*2);
		if(subTotal==total) {
			System.out.println("Subtotal validated");
		}
		
		//Reduce the Quantity of Dal as 1 
		driver.findElementByXPath("//a[contains(text(),'Toor/Arhar Dal')]//ancestor::div[@class='col-md-5 item-info']/following::button[@qa='decQtyMB']").click();
		Thread.sleep(2000);
		
		//Validate the Sub Total for the current items
		int newSubTotal = (int) Float.parseFloat(driver.findElementByXPath("//span[@qa='subTotalMB']").getText());
		int newTotal = riceCost+dalCost;
		if(newSubTotal==newTotal) {
			System.out.println("New subtotal validated");
		}
		
		//Close the Browser
		driver.close();
	}
}
