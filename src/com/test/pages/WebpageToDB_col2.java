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

public class WebpageToDB_col2 {

  public static void main(String[] args) throws SQLException {
    // Set up webdriver
   //System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.amazon.com/ref=nav_logo");
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.id("twotabsearchtextbox")).sendKeys("ipad");
	driver.findElement(By.id("nav-search-submit-button")).click();

    // Extract data from the webpage using XPath
    List<WebElement> dataElements = driver.findElements(By.xpath("//*[@data-component-type='s-search-results']/div[1]//div//div[contains(@class,'a-row a-size-base a-color-base')]//span[@class='a-price-whole']"));
    String[] dataArray = new String[dataElements.size()];
	int count = 0;
    for (int i = 0; i < dataElements.size(); i++) {
    	
    	
    	
      dataArray[i] = dataElements.get(i).getText();
     
      count++;
	    if (count == 6) {
	        break;
	    }
     
	        
	    }
    	
    
System.out.println( dataArray.length);
    // Connect to the database
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alphatester", "root", "Nasausa");

    // Prepare the SQL statement
    PreparedStatement stmt = conn.prepareStatement("INSERT INTO Products (Expected_Prod_prices) VALUES (?)");

    // Loop through the data and insert it into the database
    for (int i = 0; i < count; i ++) {
      stmt.setString(1, dataArray[i]);
      
      stmt.executeUpdate();
    }

    // Clean up
    stmt.close();
    conn.close();
    driver.quit();
  }
}
