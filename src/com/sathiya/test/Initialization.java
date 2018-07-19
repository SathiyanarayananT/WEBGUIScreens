package com.sathiya.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class Initialization {
	public WebDriver webdriver;
	public EventFiringWebDriver driver;
	public Initialization(){
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\i343453\\Desktop\\Selenium\\geckodriver.exe");
		webdriver = new FirefoxDriver();
		driver = new EventFiringWebDriver(webdriver);
		EventHandler handler = new EventHandler();
		driver.register(handler);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void Login(){
		ReadData rd = new ReadData();
		
		driver.get(rd.readDetails("URL"));
		driver.findElement(By.id("USERNAME_FIELD-inner")).sendKeys(rd.readDetails("Username"));
		driver.findElement(By.id("PASSWORD_FIELD-inner")).sendKeys(rd.readDetails("Password"));
		driver.findElement(By.id("LOGIN_LINK")).click();
		
	}
	public WebDriver getDriver(){
		return driver;	
	}
}
