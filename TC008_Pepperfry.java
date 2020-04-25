package testcases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC008_Pepperfry {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Set chromedriver.exe file to the Java class
		System.setProperty("webdriver.chrome.driver", "./chromedriver81/chromedriver.exe");
														
		//Disable notifications and launch the browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
														
		//Launch URL. maximize window and declare wait time
		driver.get("https://www.pepperfry.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		//Mouseover on Furniture and click Office Chairs under Chairs
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElementByXPath("//div[@id='menu_wrapper']//a[text()='Furniture']")).perform();
		driver.findElementByLinkText("Office Chairs").click();
		
		//click Executive Chairs
		driver.findElementByXPath("//h5[text()='Executive Chairs']//ancestor::div[@class='clip-cat-wrap']").click();
		
		//Change the minimum Height as 50 in under Dimensions
		driver.findElementByXPath("(//div[@class='clip__filter-dimension-input-holder'])[1]/input[1]").clear();
		driver.findElementByXPath("(//div[@class='clip__filter-dimension-input-holder'])[1]/input[1]").sendKeys("50", Keys.ENTER);
		
		//Add "Poise Executive Chair in Black Colour" chair to Wishlist
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='regPopUp']/a"))).click();
		int addToWishlist = 0;
		driver.findElementByXPath("//a[@data-productname='Poise Executive Chair in Black Colour']").click();
		addToWishlist++;
		
		//Mouseover on Homeware and Click Pressure Cookers under Cookware
		builder.moveToElement(driver.findElementByXPath("//div[@id='menu_wrapper']//a[text()='Homeware']")).perform();
		driver.findElementByLinkText("Pressure Cookers").click();
		
		//Select Prestige as Brand
		WebElement brand = driver.findElementByXPath("//div//input[@id='brandsnamePrestige']");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", brand);
		Thread.sleep(2000);
		
		//Select Capacity as 1-3 Ltr
		WebElement capacity = driver.findElementByXPath("//li[@data-search='1 Ltr - 3 Ltr']/input");
		executor.executeScript("arguments[0].click();", capacity);
		Thread.sleep(2000);
		
		//Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
		driver.findElementByXPath("//a[@data-productname='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']").click();
		addToWishlist++;
		Thread.sleep(3000);
		
		//Verify the number of items in Wishlist
		int wishlistCount = Integer.parseInt(driver.findElementByXPath("//div[@class='wishlist_bar']/span").getText());
		if(wishlistCount==addToWishlist) {
			System.out.println("Wishlist count matches");
		} else
			System.out.println("Wishlist count does not match");
		
		//Navigate to Wishlist
		driver.findElementByXPath("//div[@class='wishlist_bar']").click();
		Thread.sleep(3000);
		
		//Move Pressure Cooker only to Cart from Wishlist
		WebElement toCart = driver.findElementByXPath("(//a[text()='Add to Cart'])[2]");
		executor.executeScript("arguments[0].click();", toCart);
		Thread.sleep(2000);
		
		//Check for the availability for Pincode 641001
		driver.findElementByXPath("//div[@class='pin_block']/input").sendKeys("641001");
		driver.findElementByXPath("//div[@class='pin_block']/a").click();
		Thread.sleep(2000);
		
		//Click Proceed to Pay Securely
		driver.findElementByXPath("//div[@class='minicart_footer']").click();
		
		//Capture the screenshot of the item under Order Item 
		/**Step-1: Locate the specific element that has to be captured*/
		WebElement imageElement = driver.findElementByXPath("//div[@class='ck-pro-img-wrap']//img");
		
		/**Step-2: Get location of image, its width and height*/
		Point imageLocation = imageElement.getLocation();
		int width = imageElement.getSize().getWidth();
		int height = imageElement.getSize().getHeight();
		
		/**Step-3: Take full screenshot*/
		File screenshot = driver.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImage = ImageIO.read(screenshot);
		
		/**Step-4: Crop the section starting at the fetched location coordinates(X,Y) for the specified width and height*/
		BufferedImage subimage = fullImage.getSubimage(imageLocation.getX(), imageLocation.getY(), width, height);
		
		/**Step-5: Write the cropped image to a file*/
		ImageIO.write(subimage, "png", screenshot);
		
		/**Step-6: Create a destination path and copy file to destination*/
		File dest = new File("./screenshots/cooker.png");
		FileUtils.copyFile(screenshot, dest);
		
		//Close the browser
		driver.close();
	}
}
