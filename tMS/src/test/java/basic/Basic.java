package basic;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Basic {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeTest
	public void LaunchingBrowser() {
		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver ();
		wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(" http://172.10.1.159:9032");
		
	}
	
	@Test
	public void screenshot() throws IOException {
		
		TakesScreenshot sh=(TakesScreenshot) driver;
		File src=sh.getScreenshotAs(OutputType.FILE);
		File location=new File("SH/tMS/src.png");
		FileUtils.copyFile(src, location);
		
		
	}
	@Test
	public void logintest() {
		
		login ("username","password");
		
	}
	
	@Test
	public void login(String username, String password) {
		
		WebElement userid=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		userid.sendKeys("Admin");
		WebElement userpassword=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
		userpassword.sendKeys("admin@1234");
		
		WebElement termstick=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("terms")));
		termstick.click();
		
		WebElement loginbutton=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
		loginbutton.click();
		
		 WebElement togglebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='flex items-center space-x-4']/.//button/.//span[contains(text(),'menu')]")));
			togglebar.click();
			
								
			Actions tsk=new Actions(driver);
			
			WebElement task=driver.findElement(By.xpath("//span[contains(text(),'Task')]"));
			tsk.moveToElement(task).perform();
			WebElement task1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Task')]")));
			task1.click();
		
	}



	@AfterTest
	public void exitBrowser() {
		//driver.quit();
	}
	
}
