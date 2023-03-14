package com.test.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Search_Product {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.com/ref=nav_logo");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("apple watch");
		driver.findElement(By.id("nav-search-submit-button")).click();
		
// printing names of products
		List<String> prodList= new ArrayList<>();
		List<WebElement> lstNames = driver.findElements(By.xpath(
				"//*[@data-component-type='s-search-results']/div[1]//div//div[contains(@class,'s-title-instructions-style')]//span[contains(@class,'a-size-medium a-color-base a-text-normal')]"));
		System.out.println("Total Products :" + lstNames.size());
	
		for (WebElement lsn : lstNames) {

			//System.out.println("::" + lsn.getAttribute("innerHTML").substring(0, 20) + "\n");
			String prodName_app =  lsn.getText().substring(0, 20); 
			prodList.add(prodName_app);
		//	System.out.println("this is product names from webpage " + prodList);

		}
	
// printing prices of products		
		List<String> priceList = new ArrayList<>();
		List<WebElement> lstPrice = driver.findElements(By.xpath(
				"//*[@data-component-type='s-search-results']/div[1]//div//div[contains(@class,'a-row a-size-base a-color-base')]//span[@class='a-price-whole']"));
		System.out.println("Total Available Prices :" + lstPrice.size());
	
		for (WebElement lsp : lstPrice) {

		//System.out.println("::" + lsp.getText());
		String prodPrice_app =  lsp.getText(); 
		priceList.add(prodPrice_app);
		//System.out.println("this is price from webpage" + priceList.toString());
	}
		
	    for (int i=0; i<lstNames.size(); i++) {
	    	String listprice_var = "";
	    	if (i < lstPrice.size()) {
		        listprice_var = lstPrice.get(i).getText();
		    } 
		    else  {
		    	listprice_var = "n/A";
		    }
		   System.out.println(lstNames.get(i).getAttribute("innerHTML").substring(0,20) + " : " + listprice_var + "\n");
		}

	// JDBC CONNECTION
		String URL= "jdbc:mysql://localhost:3306/alphatester";
		String strQuery = "Select * from Products";
		
			Connection conn;
			try {
				conn = DriverManager.getConnection(URL, "root", "Nasausa");
				Statement statement = conn.createStatement();	
				List<String> dbProdNameList = new ArrayList<>();
				List<String> dbProdPriceList = new ArrayList<>();
//				execute
				ResultSet rs = statement.executeQuery(strQuery);
				
				while (rs.next()) {
					dbProdNameList.add(rs.getString(1));
					dbProdPriceList.add(rs.getString(2));
					 System.out.println("this is dbProdNameList" + dbProdNameList.toString());
					 System.out.println("this is dbProdPriceList" + dbProdPriceList.toString());
				}
				
				
				
//				if((!dbProdNameList.isEmpty() ) || (!dbProdPriceList.isEmpty()) ){
//					
//					
//					if( ((dbProdNameList).toString()).equalsIgnoreCase(prodList.toString()))
//					{
//					    //System.out.println("The first column of the database table and the list of web elements match.");
//					    
//					    if((dbProdPriceList).toString().equalsIgnoreCase(priceList.toString())){
//					    	
//					    	System.out.println("The both the  column of the database table and the list of web elements match.");
//					    }
//					    else {
//						    System.out.println("The second column of the database table and the list of web elements do not match.");
//						}
//					    
//					}
//					
//					else {
//					    System.out.println("The first column of the database table and the list of web elements do not match.");
//					}
//					
//					
//					}
//					else {
//					    System.out.println("null values for either Product or Price column in Database");
//					}
// compare both form app and db
				
				if((lstPrice.size())==(lstNames.size())) {
				if((!dbProdNameList.isEmpty() ) && (!prodList.isEmpty())){
				for (int i=0; i<lstNames.size(); i++) {
					
				    if (dbProdNameList.get(i).equals(prodList.get(i)) && (dbProdPriceList.get(i).equals(priceList.get(i)) )) {
				    	System.out.println(dbProdNameList.get(i)+dbProdPriceList.get(i) + " and " + prodList.get(i)+ priceList.get(i)+ " are equal");
				    }
				    else {
				    	System.out.println(dbProdNameList.get(i)+dbProdPriceList.get(i) + " and " + prodList.get(i)+ priceList.get(i)+ " are not equal");
				    }
				}
				}
				
				else{
				    System.out.println("null values for either Product or Price column in Database");
				}}
				else {
				    System.out.println("Some of the products does not have prices ");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
}
	


