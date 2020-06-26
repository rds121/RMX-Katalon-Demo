import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

/**
 * @title Create Object
 * @author rsepesy
 * @info  A demo test that runs through some basic automation processes
 * @info  1. Prompt the user for information that will be used in the test
 * @info  2. Open the RMX specified by the user and create a Tenant with their given name
 * @info  3. Delete the Tenant then close the browser
 */


//Wait for the Tenant favorite icon to load, then click on it
WebUI.waitForElementPresent(findTestObject('Object Repository/Tenant Creation Test/Tenant Link'), 30)

WebUI.click(findTestObject('Object Repository/Tenant Creation Test/Tenant Link'))

//Store the Tenant name provided in the prompt into a variable to be used when adding the tenant
def mainObj = findTestData('Demo - Testing Doc').getValue(4, 1)

//Click the add to begin adding a new tenant
WebUI.waitForElementPresent(findTestObject('Object Repository/Tenant Creation Test/a_Add New'), 30)

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Tenant Creation Test/a_Add New'))


//Input the first and last name of the Tenant provided by the prompt
WebUI.setText(findTestObject('Object Repository/Tenant Creation Test/input_First Name'), mainObj.split(' ')[0])

WebUI.setText(findTestObject('Object Repository/Tenant Creation Test/input_Last Name'), mainObj.split(' ')[1])

WebUI.delay(1)

//Enter a single letter to load properties, then select one at random
WebUI.setText(findTestObject('Object Repository/Tenant Creation Test/input_Property'), 'a')

CustomKeywords.'katalon.Actions.listSelect'()

//Save, then wait for the Back back to load to ensure the Tenant was created properly
WebUI.click(findTestObject('Object Repository/Tenant Creation Test/button_Save'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Tenant Creation Test/span_Back'), 30)

WebUI.delay(2)

//Once the Tenant is created, delete them to allow the test case to be run again without issue
WebUI.click(findTestObject('Object Repository/Tenant Creation Test/i_delete'))

WebUI.click(findTestObject('Object Repository/Tenant Creation Test/button_Yes'))

//Verify that the Add button loads, indicating that the delete was successful and you've been returned to the Tenant list
WebUI.waitForElementPresent(findTestObject('Object Repository/Tenant Creation Test/a_Add New'), 30)

WebUI.delay(2)

WebUI.closeBrowser()