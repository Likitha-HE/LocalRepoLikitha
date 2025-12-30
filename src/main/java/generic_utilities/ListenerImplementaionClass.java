package generic_utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementaionClass implements ITestListener {
	ExtentReports report;
	ExtentTest test;

	public static ThreadLocal<ExtentTest> extent=new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		test = report.createTest(methodName);
		extent.set(test);
		extent.get().log(Status.INFO, methodName+" execution starts");

	}

	@Override
	public void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		extent.get().log(Status.PASS, methodName+" ---> passed");


	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			String methodName = result.getMethod().getMethodName();
			String fileName = methodName+new JavaUtils().sysDate();

			String filepath = ".\\screenshot\\"+fileName+".png";
			TakesScreenshot ts=(TakesScreenshot)BaseClass.sdriver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File des = new File(filepath);

			FileUtils.copyFile(src, des);
			String path = des.getAbsolutePath();

			extent.get().addScreenCaptureFromPath(path);

			extent.get().log(Status.FAIL, result.getThrowable());
			extent.get().log(Status.FAIL,methodName+"--->failed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		extent.get().log(Status.SKIP, methodName+"---->Skipped");

	}

	@Override
	public void onStart(ITestContext context) {
		ExtentSparkReporter htmlreport = new ExtentSparkReporter(".\\ExtentReport\\report"+new JavaUtils().sysDate()+".html");
		htmlreport.config().setDocumentTitle("TP-30");
		htmlreport.config().setTheme(Theme.DARK);
		htmlreport.config().setReportName("Vtiger");

		report=new ExtentReports();
		report.attachReporter(htmlreport);
		report.setSystemInfo("base_browser", "chrome");
		report.setSystemInfo("base_platform", "windows");
		report.setSystemInfo("base_url", "http://localhost:8888");
		report.setSystemInfo("reporter_Name", "Likitha");
	}

	@Override
	public void onFinish(ITestContext context) {

		report.flush();
	}
}
