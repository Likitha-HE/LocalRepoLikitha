package invoices;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import generic_utilities.ExcelUtils;
import generic_utilities.FileUtility;
import generic_utilities.JavaUtils;
import generic_utilities.WebDriverUtils;
import object_repo.CreateInvoicePage;
import object_repo.HomePage;
import object_repo.InvoicePage;
import object_repo.LoginPage;

public class CreateInvoiceUsingSalesOrderTest {
	@Test
	public void createInvoiceUsingSalesOrderTest() throws IOException, InterruptedException {
		WebDriver driver=null;
		//Read data from property File
		FileUtility fu = new FileUtility();
		JavaUtils ju = new JavaUtils();
		ExcelUtils eu = new ExcelUtils();
		WebDriverUtils wdu = new WebDriverUtils();


		String BROWSER = fu.readDataFromPropertyFile("browser");
		String URL = fu.readDataFromPropertyFile("url");
		String USERNAME = fu.readDataFromPropertyFile("username");
		String PASSWORD = fu.readDataFromPropertyFile("password");

		String sName = eu.readDataFromExcel("Invoice", 0, 1)+ju.getRandomNo();
		String orgName = eu.readDataFromExcel("Invoice", 1, 1);
		String billingAdd = eu.readDataFromExcel("Invoice", 2, 1);
		String prodName = eu.readDataFromExcel("Invoice", 3, 1);
		String quantity = eu.readDataFromExcel("Invoice", 4, 1);
		String salesOrder = eu.readDataFromExcel("Invoice", 6, 1);

		if(BROWSER.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		}
		else if(BROWSER.equalsIgnoreCase("Firefox")) {
			driver=new FirefoxDriver();
		}
		else {
			driver=new EdgeDriver();
		}
		//maximize the window 
		wdu.maximizeWindow(driver);
		//Wait for loading web page
		wdu.waitForPageLoad(driver, 10);

		//Open/get the application
		driver.get(URL);

		//Step1:Login to the Application.
		LoginPage lp=new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);

		//check the quantity before creating invoice
		HomePage hp = new HomePage(driver);
		String actualQty = hp.clickOnProducts(driver, prodName);
		

		//Navigate to Home page and Click on 'More Link' present on top bar,Click on 'Vendors link' in the suggestion pop up.
		hp.clickOnInvoice(driver);

		//Click on 'Create Invoice' + icon
		InvoicePage ip = new InvoicePage(driver);
		ip.clickOnCreateInvoiceIcon();

		//Provide Mandatory field values,Click on 'select' + icon to select organization name
	    CreateInvoicePage cip = new CreateInvoicePage(driver);
	    cip.createInvoice(sName, driver, salesOrder, orgName, billingAdd);
		Thread.sleep(3000);
		
		//Validate invoice creation
		String actual1 = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(actual1.contains(sName)) {
			System.out.println("Ivoice cretaed sucessfully");
		}
		else {
			System.out.println("Invoice is not created");
		}
		//To Check the quantity available in Product list page Click on 'Product Link' present on top bar.
		String qtyAfterCI =  hp.clickOnProducts(driver, prodName);
		Double qtyAfterCI1=Double.parseDouble(qtyAfterCI);
		Double actualQty1=Double.parseDouble(actualQty);
		Double quantity12=Double.parseDouble(quantity);

        //Click the quantity data after creating invoice 
		if (qtyAfterCI1.equals(actualQty1 - quantity12)) {
			System.out.println("Quantity is getting reduced as expected");
		}
		else {
			System.out.println("Quantity is not reducing as expected");
		}

		//sign out
		hp.signOut(driver);
		
		Thread.sleep(5000);
		driver.quit();
	}

}
