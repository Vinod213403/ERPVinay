package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath ="./FileInput/DataEngine.xlsx";
String outputpath ="./FileOutput/HybridResults.xlsx";
ExtentReports report;
ExtentTest logger;
String TCSheet ="MasterTestCases";
public void startTest()throws Throwable
{
	String Module_status="";
	String Module_new ="";
	//create object for excel file util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all test cases in TCSheet
	for(int i=1;i<=xl.rowCount(TCSheet);i++)
	{
		if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
		{
			//store each sheet into one varibale
			String TCModule =xl.getCellData(TCSheet, i, 1);
			//iterate all rows in every sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read each cell from TCModule
				String Description =xl.getCellData(TCModule, j, 0);
				String ObjectType =xl.getCellData(TCModule, j, 1);
				String Ltype = xl.getCellData(TCModule, j, 2);
				String Lvalue = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("startBrowser"))
					{
						driver =FunctionLibrary.startBrowser();
					}
					if(ObjectType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
					}
					if(ObjectType.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(Ltype, Lvalue, TestData);
					}
					if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Ltype, Lvalue, TestData);
					}
					if(ObjectType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Ltype, Lvalue);
					}
					if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(TestData);
					}
					if(ObjectType.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
					}
					//write as pass into status cell in TCModule sheet
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					Module_status ="True";
				}catch(Throwable t)
				{
					System.out.println(t.getMessage());
					//write as Fail into status cell in TCModule sheet
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					Module_new="False";
				}
				if(Module_status.equalsIgnoreCase("True"))
				{
					xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
				}
				if(Module_new.equalsIgnoreCase("False"))
				{
					xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
				}
			}
		}
		else
		{
			//write as blocked for testcases flag to N
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
}

}













