package com.sathiya.test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GUIScreen {
	public static void main(String arg[]){
		WebDriver driver;
		Initialization in = new Initialization();
		in.Login();
		
		driver = in.getDriver();
		try{
			
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
			Thread.sleep(2000);
			if(driver.findElements(By.xpath("//iframe[@id='ITSFRAME1']")).size() > 0){
				driver.switchTo().frame("ITSFRAME1");
				System.out.println("Frame changed");
			}
			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Exit']")));
			
			// First Page inputs - Different from normal
			List<WebElement> list = driver.findElements(By.xpath("//label"));
			List<WebElement> list1 = driver.findElements(By.xpath("//td[@role='heading']/following::input/preceding::label[1]"));
						
			System.out.println("Total input fields : "+list.size());
			System.out.println(list1.size());
			for(int i=0 ;i<list.size();i++){
				for(int j=0;j<list1.size();j++){
//					System.out.println(list.get(i).getAttribute("id") +" "+ list1.get(j).getAttribute("id"));
					if(list.get(i).getAttribute("id").toString().equals(list1.get(j).getAttribute("id").toString())){
						System.out.println(list.get(i).getAttribute("id"));
						list.remove(i);
//						break;
//						System.out.println(driver.findElement(By.xpath("//input[@id='"+list.get(i).getAttribute("id")+"']/following-sibling::span")).getText());
					}
				}
			}
			System.out.println(list.size());
			boolean flag = true;
			int count = 0;
			while(flag){
				flag = false;
				for(WebElement element : list){
					System.out.println();
					String n = element.getText();
					System.out.println(n);
					WebElement ne = driver.findElement(By.xpath("//label[@id='"+element.getAttribute("id")+"']//following::input[1]"));//.getAttribute("innerHTML");
					ReadExcel rx = new ReadExcel();
					String value = rx.Read(n);
					System.out.println(n);
					if( !value.equals("") ){
//						System.out.println(value);
						// needs to be fixed
						ReadData rd = new ReadData();
						if(ne.getAttribute("value").toString().equals("")){
							if(ne.getAttribute("class").equals(rd.readDetails("class"))){
								String a = "//label/span[text()='"+n +"']/following::span";
								driver.findElement(By.xpath(a)).click();
							    WebElement drop= driver.findElement(By.xpath("//ul//ancestor::div"));
							    List<WebElement> droplist=drop.findElements(By.tagName("li"));
							    
							    for (WebElement li : droplist) {
//							    	System.out.println(li.getText());
							    if (li.getText().equals(value)) {
							         li.click();
							       }
							    }
							}else{
								ne.sendKeys(value);
								Thread.sleep(500);
								String val = ne.getAttribute("aria-invalid");
								if(val != null){
									System.err.println("Invalid entry");
								}
							}
						}
						if(!ne.getAttribute("value").toString().equals(value)){
							flag = true;
							System.out.println(count++);
						}
					}
				}
				
			}

			Thread.sleep(2000);
			System.out.println();
			// Click on Continue.
			if(driver.findElements(By.xpath("//*[text()='Continue']")).size() > 0){
				System.out.println("Continue");
				
				boolean flg = true;
				while(flg){
					if(driver.findElements(By.xpath("//*[@role='tab']")).size() > 0){
						flg = true;
					}else{
						WebElement e = driver.findElement(By.xpath("//*[text()='Continue']/ancestor::div[@role='button']"));
						Actions ac = new Actions(driver);
						ac.moveToElement(e).click(e);
						ac.perform();
						flg = false;
					}
				}
				
			}else{
				driver.findElement(By.xpath("//body")).sendKeys(Keys.ENTER);
			}
			// Page loads //*[contains(text(),'Save')]
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Save']")));
			
			// inputs in second page
			List<WebElement> list3 = driver.findElements(By.xpath("//label/span"));
			for(WebElement ele : list3){
				ReadExcel rx = new ReadExcel();
				String value = rx.Read(ele.getText());
				System.out.println(value);
				
				if( !value.equals("") ){
					ReadData rd = new ReadData();
					String n = "";
					if(driver.findElements(By.xpath("//span[text()='"+ele.getText()+"']/preceding-sibling::input")).size() > 0){
						n="//span[text()='"+ele.getText()+"']/preceding-sibling::input";
					}else{
						n = "//*[text()='" +ele.getText()+ "']/following::input";
//						System.out.println("Fallback");
					}
					
					WebElement wb = driver.findElement(By.xpath(n));
					if(wb.isDisplayed()){
						if(wb.getAttribute("aria-readonly")== null){
							if(wb.getAttribute("class").equals(rd.readDetails("class"))){
								String a = "//label/bdi[text()='"+ ele.getText() +"']/following::span";
								driver.findElement(By.xpath(a)).click();
							    WebElement drop= driver.findElement(By.xpath("//ul//ancestor::div"));
							    List<WebElement> droplist=drop.findElements(By.tagName("li"));
							    
							    for (WebElement li : droplist) {
//							    	System.out.println(li.getText());
							    if (li.getText().equals(value)) {
							         li.click();
							       }
							    }
							}else{
								if(wb.getAttribute("value").toString().equals("")){
									wb.clear();
									wb.sendKeys(value);
									String val = wb.getAttribute("aria-invalid");
									if(val != null){
										System.err.println("Invalid entry");
									}
								}
								
							}
						}
					}
				}
				
			}
//			for(WebElement ele : list3){
//				
//				String n = ele.getText();
//				System.out.println(n);
//				ReadExcel rx = new ReadExcel();
////				System.out.println(driver.findElement(By.xpath("//input[@id='"+ ele.getAttribute("id")+"']/following-sibling::span")).getAttribute("innerHTML"));
//				WebElement e = driver.findElement(By.xpath("//span[@id='"+ele.getAttribute("id")+"']//following::input[1]"));
//
//				if(e.isDisplayed()){
//					if(e.getAttribute("aria-readonly")==null){
//						String value = rx.Read(n);
//						if( !value.equals("") ){
//							e.sendKeys(value);
////							e.sendKeys(Keys.ENTER);
//						}
//					}
//				}
//				
//			}
			
			// Tabs
			List<WebElement> tabs = driver.findElements(By.xpath("//*[(@role='tab') and (@action='TAB_ACCESS')]"));
			System.out.println("tabs and "+ tabs.size());
			List<String> tabString = new ArrayList<String>();
			
			for(WebElement tab : tabs){
				tabString.add(tab.getText());
			}


			// Tabs can be Stale so. again capturing.
			for(int i=0;i<tabString.size();i++){
				boolean result = true;
				while(driver.findElement(By.xpath("//*[(text()='"+tabString.get(i).trim()+"') and (@role='tab')]")).getAttribute("aria-selected").equals("false") || result) {
					try {
						WebElement e = driver.findElement(By.xpath("//*[(text()='"+tabString.get(i).trim()+"') and (@role='tab')]"));
				    	Actions ac = new Actions(driver);
			        	ac.click(e);
						ac.perform();
						result = false;
			            break;
			        } catch(StaleElementReferenceException e) {
			        	result = true;
			        }	
			    }
				
				Thread.sleep(2000);
//				driver.switchTo().parentFrame();
//
//				// Pop up handling
//				if(driver.findElements(By.xpath("//iframe[@id='URLSPW-0' and @inuse='true']")).size() > 0){
//					System.out.println("Popup");
//					
//					driver.switchTo().frame("URLSPW-0");
//					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Continue']")));
//					driver.findElement(By.xpath("//*[text()='Continue']/ancestor::div[@role='button']")).click();
//					driver.switchTo().parentFrame();
//					driver.switchTo().frame("ITSFRAME1");
//				}else{
//					if(driver.findElements(By.xpath("//iframe[@id='ITSFRAME1']")).size() > 0){
//						driver.switchTo().frame("ITSFRAME1");
//					}	
//				}

				// Input fields in tabs 
				List<WebElement> inputs = driver.findElements(By.xpath("//label"));
				List<String> inputname = new ArrayList<String>();
				for(WebElement ele : inputs){
					inputname.add(ele.getText());
				}
				for(String ele : inputname){
					ReadExcel rx = new ReadExcel();
					String value = rx.Read(ele.trim());
					if(ele.equals("Order Reason"))
						System.out.println(ele + " "+ value);
					
					if( !value.equals("") ){
						ReadData rd = new ReadData();
						String n = "";
						if(driver.findElements(By.xpath("//span[text()='"+ele.trim()+"']/preceding-sibling::input")).size() > 0){
							n="//span[text()='"+ele.trim()+"']/preceding-sibling::input";
						}else{
							n = "//*[text()='" +ele.trim()+ "']/following::input";
							System.out.println("Fallback");
						}
						
						WebElement wb = driver.findElement(By.xpath(n));
						if(wb.isDisplayed()){
							if(wb.getAttribute("aria-readonly")== null){
								System.out.println(wb.getAttribute("aria-describedby"));
								if(wb.getAttribute("aria-describedby").equals("ARIA_CB_DropDown_TUT")){
//									String a = "//label/bdi[text()='"+ ele.trim() +"']/following::span";
									System.out.println("Iam in ");
									driver.findElement(By.xpath(n)).click();
									driver.findElement(By.xpath(n)).sendKeys(value);
//									driver.findElement(By.xpath("//*[text()='"+ value+"']")).click();
//								    WebElement drop= driver.findElement(By.xpath("//ul//ancestor::div"));
//								    List<WebElement> droplist=drop.findElements(By.tagName("li"));
//								    
//								    for (WebElement li : droplist) {
////								    	System.out.println(li.getText());
//								    if (li.getText().equals(value)) {
//								         li.click();
//								       }
//								    }
								}else{
									if(wb.getAttribute("value").toString().equals("")){
										wb.clear();
										wb.sendKeys(value);
										String val = wb.getAttribute("aria-invalid");
										if(val != null){
											System.err.println("Invalid entry");
										}
									}
									
								}
							}
						}
					}
					
				}
			}
			driver.findElement(By.xpath("//*[text()='Save']/ancestor::div[@role='button']")).click();
			
		}catch(Exception e){
			System.out.println("Exception occured : " + e);
	        e.printStackTrace();
		}
	}

}
