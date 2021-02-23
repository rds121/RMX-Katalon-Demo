import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement

//Open a new browser
WebUI.openBrowser('')

//Navigate to the DBID stored in the testing doc
WebUI.navigateToUrl(findTestData('Demo - Testing Doc').getValue(1, 1))
	
//Verify whether the browser needs to login, and if it does input the correct information
if (WebUI.verifyElementPresent(findTestObject('Login/input_EXPRESS_Username'), 2, FailureHandling.OPTIONAL)){
	
	//Wait for the username and password inputs to appear, then enter in the data stored in the testing doc
	WebUI.waitForElementPresent(findTestObject('Login/input_EXPRESS_Username'), 30)
		
	WebUI.setText(findTestObject('Login/input_EXPRESS_Username'), findTestData('Demo - Testing Doc').getValue(
				2, 1))
		
	WebUI.setText(findTestObject('Login/input_EXPRESS_Password'), findTestData('Demo - Testing Doc').getValue(
				3, 1))
		
	
	//Click sign in
	WebUI.click(findTestObject('Login/span_Sign In'))
}

//Wait for the welcome message, verifying that login was successful
WebUI.waitForElementVisible(findTestObject('Login/h1_Welcome Message'), 60)

WebUI.waitForPageLoad(30)