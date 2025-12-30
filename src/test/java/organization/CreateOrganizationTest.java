package organization;

import java.io.IOException;
import static org.testng.Assert.*;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import generic_utilities.BaseClass;
import object_repo.CreateOrganizationPage;
import object_repo.HomePage;
import object_repo.OrganizationPage;

//@Listeners(generic_utilities.ListenerImplementaionClass.class)

public class CreateOrganizationTest extends BaseClass {
	@Test(groups = "smoke")
	public void createOrganizationWithMandatoryFTest() throws InterruptedException, EncryptedDocumentException, IOException {
		//read data from Excel 
		String orgName = eLib.readDataFromExcel("Organizations", 0, 1)+jLib.getRandomNo();
		
		//click on organization link in home page
		HomePage hp = new HomePage(driver);
		hp.clickOnOrg();
		
        //click on create organization icon
		OrganizationPage org = new OrganizationPage(driver);
		org.clickOnCreateOrganizationIcon();

		//provide all mandatory data and click on save
		CreateOrganizationPage co = new CreateOrganizationPage(driver);
		co.createOrganization(orgName);

		//validate if data is created or not
		String actual = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		assertTrue(actual.contains(orgName), "Organization is not created");
		System.out.println("Organization cretaed sucessfully");

	}

	@Test(groups = "regression")
	public void CreateOrganizationWithIndustryDropDownTest1() throws InterruptedException, IOException {
		//read data from Excel 
		String orgName = eLib.readDataFromExcel("Organizations", 0, 1)+jLib.getRandomNo();
		String industryDD=eLib.readDataFromExcel("Organizations", 1, 1);
		
		//click on organization link in home page
		HomePage hp = new HomePage(driver);
		hp.clickOnOrg();

        //click on create organization icon
		OrganizationPage orgp = new OrganizationPage(driver);
		orgp.clickOnCreateOrganizationIcon(); 

		//provide all mandatory data,industry Drop down and click on save
		CreateOrganizationPage cop = new CreateOrganizationPage(driver);
		cop.createOrganization(orgName, industryDD,driver);
		Thread.sleep(3000);

		//validate if data is created or not
		String actual = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		assertTrue(actual.contains(orgName), "Organization is not created");
		System.out.println("Organization cretaed sucessfully");
	}

}
