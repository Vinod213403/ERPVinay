package commonFunctions;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
//method for launch browser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load property file here
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser value is not matching",true);
	}
	return driver;
}
//method for launching url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}
//method for to wait for any webelement in a page
public static void waitForElement(String LocatorType,String LocatorValue,String TestData)
{
	WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	if(LocatorType.equalsIgnoreCase("id"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
}
//method for any textbox
public static void typeAction(String LocatorType,String LocatorValue,String testData)
{
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(testData);
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(testData);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(testData);
	}
}
//method for any buttons,checkboxes,radiobuttons,links and images
public static void clickAction(String LocatorType,String LocatorValue)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	}
}
//method for validate title
public static void validateTitle(String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
	}catch(AssertionError a)
	{
		System.out.println(a.getMessage());
	}
}
//method for closing 
public static void closeBrowser()
{
	driver.quit();
}
//method for genereate data format
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
	return df.format(date);
}
}















