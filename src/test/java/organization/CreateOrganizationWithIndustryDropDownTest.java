package organization;

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
import object_repo.CreateOrganizationPage;
import object_repo.HomePage;
import object_repo.LoginPage;
import object_repo.OrganizationPage;

public class CreateOrganizationWithIndustryDropDownTest {
	@Test
	public void CreateOrganizationWithIndustryDropDownTest1() throws InterruptedException, IOException {
		WebDriver driver=null;

		//Create Random number
		//		Random ran = new Random();
		//		int random = ran.nextInt(900);
		//
		//		//Read data from property File
		//		FileInputStream access = new FileInputStream(".\\src\\test\\resources\\ResourceFile\\commondata.properties");
		//		Properties pObj = new Properties();
		//		pObj.load(access);
		//
		//		String BROWSER = pObj.getProperty("browser");
		//		String URL = pObj.getProperty("url");
		//		String USERNAME = pObj.getProperty("username");
		//		String PASSWORD = pObj.getProperty("password");
		//
		//		//Read Data from Excel
		//		FileInputStream testData = new FileInputStream(".\\src\\test\\resources\\ResourceFile\\TestData.xlsx");
		//		Workbook wb = WorkbookFactory.create(testData);
		//		Sheet sh = wb.getSheet("Organizations");
		//		String orgName = sh.getRow(0).getCell(1).getStringCellValue()+random;
		//		String industryDD=sh.getRow(1).getCell(1).getStringCellValue();
		
		//Create objects for the utils files
		FileUtility fu = new FileUtility();
		JavaUtils ju = new JavaUtils();
		ExcelUtils eu = new ExcelUtils();
		WebDriverUtils wdu = new WebDriverUtils();

		//Read data from property file using readDataFromPropertyFile(string)
		String BROWSER = fu.readDataFromPropertyFile("browser");
		String URL = fu.readDataFromPropertyFile("url");
		String USERNAME = fu.readDataFromPropertyFile("username");
		String PASSWORD = fu.readDataFromPropertyFile("password");

		//read data from Excel 

		String orgName = eu.readDataFromExcel("Organizations", 0, 1)+ju.getRandomNo();
		String industryDD=eu.readDataFromExcel("Organizations", 1, 1);


		if(BROWSER.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		}
		else if(BROWSER.equalsIgnoreCase("Firefox")) {
			driver=new FirefoxDriver();
		}
		else {
			driver=new EdgeDriver();
		}
		//Maximize the web page window
		//driver.manage().window().maximize();
		wdu.maximizeWindow(driver);
		//implicit wait
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wdu.waitForPageLoad(driver, 10);

		//Open/get the application
		driver.get(URL);

		//Step1:Login to the Application.
//		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
//		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
//		driver.findElement(By.id("submitButton")).click();
		
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);

		//Step2:Navigate to Home page and Click on Organization link present on top bar.
		//driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Organizations']")).click();
		
		HomePage hp = new HomePage(driver);
		hp.clickOnOrg();

		//Step3:Click on 'Create Organization' + icon.
		//driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		
		OrganizationPage orgp = new OrganizationPage(driver);
		orgp.clickOnCreateOrganizationIcon(); 
		
//		//Step4:Provide valid data for  all the mandatory fields.
//		driver.findElement(By.name("accountname")).sendKeys(orgName);
//
//		//Step5:click on industry dropdown and select 'Education' from the dropdown.
//		WebElement industry = driver.findElement(By.name("industry"));
//		Select sel = new Select(ele1);
//		sel.selectByContainsVisibleText(industryDD);
//		wdu.dropdownUsingContainsVisibleText(industry, industryDD);
//		
//
//		//Step5:Click on save button.
//		driver.findElement(RelativeLocator.with(By.xpath("//input[@title='Save [Alt+S]']")).below(By.name("description"))).click();
		
		CreateOrganizationPage cop = new CreateOrganizationPage(driver);
		cop.createOrganization(orgName, industryDD,driver);
		Thread.sleep(3000);

		//validate if data is created or not
		String actual = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(actual.contains(orgName)) {
			System.out.println("Organization cretaed sucessfully");
		}
		else {
			System.out.println("Organization is not created");
		}
	
		//sign out
//		WebElement signOutIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement signOut = driver.findElement(By.xpath("//a[contains(.,'Sign Out')]"));
//		Actions act = new Actions(driver);
//    	act.moveToElement(ele2).perform();
//		act.moveToElement(driver.findElement(By.xpath("//a[contains(.,'Sign Out')]"))).click().perform();
//		wdu.mouseHover(driver, signOutIcon);
//		wdu.mouseHoverAndClickOnEle(driver, signOut);
		
		hp.signOut(driver);

		//close the browser
		Thread.sleep(5000);
		driver.quit();
	}
	
	@Test
	public void test1() {
		System.out.println("Jekins scm test");
		System.out.println("Jekins scm test1");
	}

}
