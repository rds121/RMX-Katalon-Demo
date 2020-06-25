package katalon

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver

public class Actions {
	
	/**
	 * @title listSelect
	 * @info  Find all elements in a list and choose one to click
	 * @param choice - Optional - Provide the numeric value of an option in the list to select. If blank the selection is random
	 * @return int - The number of the choice selected
	 */
	@Keyword
	int listSelect(def choice){

		WebDriver driver = DriverFactory.getWebDriver()

		Random rnd = new Random()

		String baseXpath = "//div[contains(@class, \'overlay\') and contains(@style, \'opacity: 1\')]/descendant::"

		List xpaths = []

		xpaths += baseXpath + "span[contains(@class, \'label\')]"

		xpaths += baseXpath + "div[@class = \'selector-overlay-item-display-wrap\']"

		List<WebElement> opt

		int selection

		for (def xpath : xpaths){
			opt = driver.findElements(By.xpath(xpath))

			if (opt.size() > 0){

				if (choice == null){
					//Use rnd.nextInt to select a random value within the scope of all options so that we don't always pick the same drop down option
					selection = rnd.nextInt(opt.size)
				}
				else{
					selection = choice
				}

				opt[selection].click()

				break
			}
		}

		return selection
	}
	
}
