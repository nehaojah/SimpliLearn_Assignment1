package com.test.pages;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CopyData {
/// it copies product name and price from webpage to a already existed empty table in database when both columns have same length i.e. webpage have no null values for a product search
  public static void main(String[] args) throws SQLException {

    WebDriver driver = new ChromeDriver();
    driver.get("https://www.amazon.com/ref=nav_logo");
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.id("twotabsearchtextbox")).sendKeys("apple watch");
	driver.findElement(By.id("nav-search-submit-button")).click();

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alphatester", "root", "Nasausa");

    // Preparing the SQL statement
    PreparedStatement stmt = conn.prepareStatement("INSERT INTO Products (Expected_Prod_Name,Expected_Prod_prices) VALUES (?,?)");
    // Extracting data from the webpage using XPath
    List<WebElement> firstList = driver.findElements(By.xpath("//*[@data-component-type='s-search-results']/div[1]//div//div[contains(@class,'s-title-instructions-style')]//span[contains(@class,'a-size-medium a-color-base a-text-normal')]"));
    List<WebElement> secondList = driver.findElements(By.xpath("//*[@data-component-type='s-search-results']/div[1]//div//div[contains(@class,'a-row a-size-base a-color-base')]//span[@class='a-price-whole']"));
    System.out.println("Total Products :" + firstList.size());
 
    for (int i=0; i<firstList.size(); i++) {
    	 try {
	    String firstData = firstList.get(i).getAttribute("innerHTML").substring(0,20);
	      String secondData = secondList.get(i).getText();
	     
	    stmt.setString(1, firstData);
        stmt.setString(2, secondData);
        // Executing the insert statement
       
        stmt.executeUpdate();
       
	} catch (Exception e) {
       
        continue;
    }
    	
    }

	    
	}
    
    
    	
    



}
