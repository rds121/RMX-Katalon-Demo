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
 * @title Tenant Creation Test
 * @author rsepesy
 * @info  A demo test that runs through some basic automation processes
 * @info  1. Prompt the user for information that will be used in the test
 * @info  2. Open the RMX specified by the user and create a Tenant with their given name
 * @info  3. Delete the Tenant then close the browser
 */

//Prompt the user for information: DBID, Username, Password, and Tenant to create
//This information will be stored in the data file attached to this project
WebUI.callTestCase(findTestCase('Prompt'), [:])

//Call the Login test case which will go to the provided DBID and login with the given credentials
WebUI.callTestCase(findTestCase('Login'), [:])

//Call test case to perform Create Tenant test
WebUI.callTestCase(findTestCase('Create Tenant'), [:], FailureHandling.STOP_ON_FAILURE)