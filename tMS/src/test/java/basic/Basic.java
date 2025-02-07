package basic;

import java.awt.AWTException;
import java.awt.Robot;
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
import org.openqa.selenium.support.ui.Select;
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
	public void logintest() throws IOException, AWTException {
		
		screenshot();
		login ("Admin","admin@1234");
		Task("Title TMS","Task Management System");
		
	}
	
	
	public void login(String username, String password) throws IOException {
		
		
		
		WebElement userid=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		userid.sendKeys(username);
		WebElement userpassword=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
		userpassword.sendKeys(password);
		
		
		WebElement termstick=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("terms")));
		termstick.click();
		
		WebElement loginbutton=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
		loginbutton.click();
		
		 WebElement togglebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='flex items-center space-x-4']/.//button/.//span[contains(text(),'menu')]")));
			togglebar.click();
			
			
								
			//Actions tsk=new Actions(driver);
			
			
			/*WebElement frames=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//aside[@id='sidebar']/.//ul[@class='space-y-2 p-4']/.//li[6]")));
			System.out.println(frames.getText());*/
			
			
//			WebElement task=driver.findElement(By.xpath("//aside[@id='sidebar']/.//ul[@class='space-y-2 p-4']/.//li[6]"));
//			tsk.moveToElement(task).click();
			WebElement task1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Task')]")));
			task1.click();
		
			
	}
	
	public void Task(String tasktitle,String ProjectTaskDescription) throws AWTException {
		
		WebElement addTask=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600']")));
		addTask.click();
		
		
		WebElement Client=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_ClientCode")));
		
		Select drop=new Select(Client);
		drop.selectByValue("KSP");
		
		Robot robot=new Robot();
		
		WebElement Milestone=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_ProjectMilestoneCode")));
		Select MS=new Select(Milestone);
		MS.selectByValue("M002");//No value
		
		WebElement ProjectName=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_ProjectCode")));
		Select PN=new Select(ProjectName);
		PN.selectByValue("P001");//No Value
		

		
		WebElement modulename=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_ProjectModuleCode")));
		Select MN=new Select(modulename);
		MN.selectByValue("M002");
		
		WebElement taskcode=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_TaskTypeCode")));
		Select TC=new Select (taskcode);
		TC.selectByValue("CL");
			
		WebElement title=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_Title")));
		title.sendKeys(tasktitle);
		
		WebElement description=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ProjectTask_Description")));
		description.sendKeys(ProjectTaskDescription);
		
		WebElement upload=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@for='ProjectTask_Files']")));
		upload.click();
		String path="E:\\KSP\\KSP Projects\\APC KK\\Couldn't save data.png";
		upload.sendKeys(path);
		
		
	}


public void screenshot() throws IOException {
		
		TakesScreenshot sh=(TakesScreenshot) driver;
		File src=sh.getScreenshotAs(OutputType.FILE);
		File location=new File("SH/tMS/src.png");
		FileUtils.copyFile(src, location);
		
	}



	@AfterTest
	public void exitBrowser() {
		//driver.quit();
	}
	
}
