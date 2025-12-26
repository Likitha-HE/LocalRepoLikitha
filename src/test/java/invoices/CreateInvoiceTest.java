package invoices;

import java.io.IOException;

import org.openqa.selenium.By;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import generic_utilities.BaseClass;
import object_repo.CreateInvoicePage;
import object_repo.HomePage;
import object_repo.InvoicePage;

//@Listeners(generic_utilities.ListenerImplementaionClass.class)

public class CreateInvoiceTest extends BaseClass {
	@Test(groups = "regression",retryAnalyzer = generic_utilities.RetryImpClass.class)
	public void createInvoiceUsingSalesOrderTest() throws IOException, InterruptedException {

		String sName = eLib.readDataFromExcel("Invoice", 0, 1)+jLib.getRandomNo();
		String orgName = eLib.readDataFromExcel("Invoice", 1, 1);
		String billingAdd = eLib.readDataFromExcel("Invoice", 2, 1);
		String prodName = eLib.readDataFromExcel("Invoice", 3, 1);
		String quantity = eLib.readDataFromExcel("Invoice", 4, 1);
		String salesOrder = eLib.readDataFromExcel("Invoice", 6, 1);

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
//		if(actual1.contains(sName)) {
//			System.out.println("Ivoice cretaed sucessfully");
//		}
//		else {
//			System.out.println("Invoice is not created");
//		}
		
		assertTrue(actual1.contains(sName), "Invoice is not created");
		System.out.println("Ivoice cretaed sucessfully");
		
		
		//To Check the quantity available in Product list page Click on 'Product Link' present on top bar.
		String qtyAfterCI =  hp.clickOnProducts(driver, prodName);
		Double qtyAfterCI1=Double.parseDouble(qtyAfterCI);
		Double actualQty1=Double.parseDouble(actualQty);
		Double quantity12=Double.parseDouble(quantity);

        //Click the quantity data after creating invoice 
//		if (qtyAfterCI1.equals(actualQty1 - quantity12)) {
//			System.out.println("Quantity is getting reduced as expected");
//		}
//		else {
//			System.out.println("Quantity is not reducing as expected");
//		}
		double finalQty = actualQty1 - quantity12;
		assertEquals(qtyAfterCI1, finalQty,"Quantity is not getting reduced as expected");
		System.out.println("Quantity is getting reduced as expected");
	

	}
	
	@Test(groups = "smoke",retryAnalyzer = generic_utilities.RetryImpClass.class)
	public void createInvoiceWithoutMappingSalesOrderTest() throws IOException, InterruptedException {		
		
        //read data from Excel 
		String sName = eLib.readDataFromExcel("Invoice", 0, 1)+jLib.getRandomNo();
		String orgName = eLib.readDataFromExcel("Invoice", 1, 1);
		String billingAdd = eLib.readDataFromExcel("Invoice", 2, 1);
		String prodName = eLib.readDataFromExcel("Invoice", 3, 1);
		String quantity = eLib.readDataFromExcel("Invoice", 4, 1);
		String price = eLib.readDataFromExcel("Invoice", 5, 1);
		
		//check the quantity before creating invoice
		HomePage hp = new HomePage(driver);
		String actualQty = hp.clickOnProducts(driver, prodName);	
				
		//Navigate to Home page and Click on 'More Link' present on top bar,Click on 'Vendors link' in the suggestion pop up.
		hp.clickOnInvoice(driver);
		
		//Click on 'Create Invoice' + icon
		InvoicePage ip = new InvoicePage(driver);
		ip.clickOnCreateInvoiceIcon();
	
		//Provide Mandatory field values,Click on 'select' + icon to select organization name,Click on search Organization field and search for the required organization.
		CreateInvoicePage cip = new CreateInvoicePage(driver);
		cip.createInvoice(driver, sName, prodName, orgName, billingAdd, quantity, price);
		Thread.sleep(3000);
		
		//Validate invoice creation
		String actual1 = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
//		if(actual1.contains(sName)) {
//			System.out.println("Ivoice cretaed sucessfully");
//		}
//		else {
//			System.out.println("Invoice is not created");
//		}
		assertTrue(actual1.contains(sName), "Invoice is not created");
		System.out.println("Ivoice cretaed sucessfully");
		
		//To Check the quantity available in Product list page Click on 'Product Link' present on top bar.
		String qtyAfterCI = hp.clickOnProducts(driver, prodName);
		
		//converting String value to double
		Double qtyAfterCI1=Double.parseDouble(qtyAfterCI);
		Double actualQty1=Double.parseDouble(actualQty);
		Double quantity12=Double.parseDouble(quantity);	
		double finalQty = actualQty1 - quantity12;
		
		//checking quantity after creating invoice
//		if (qtyAfterCI1.equals(actualQty1 - quantity12)) {
//			System.out.println("Quantity is getting reduced as expected");
//		}
//		else {
//			System.out.println("Quantity is not reducing as expected");
//		}	
		
		assertEquals(qtyAfterCI1, finalQty,"Quantity is not getting reduced as expected");
		System.out.println("Quantity is getting reduced as expected");
		}

}
