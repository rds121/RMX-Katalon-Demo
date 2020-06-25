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
import javax.swing.*
import java.awt.Dimension
import java.awt.BorderLayout;
import java.awt.GridLayout;

//The output will be stored in a Map in order to be sent to Excel at the end
Map login = [:]

//The first node stored is the column headers, which will be labels in the text box pop-up
login[0] = findTestData('Demo - Testing Doc').getColumnNames()
	
//Loop over every row, then every column and store the current data in the login Map
for (def row = 1; row <= findTestData('Demo - Testing Doc').getRowNumbers(); row++){
	
	login[row] = []
	
	for (def col = 1; col <= findTestData('Demo - Testing Doc').getColumnNumbers(); col++){
	
		login[row] +=  findTestData('Demo - Testing Doc').getValue(col, row)
	}
}

//Open the pop-up and allow the user to change the data
login = runPanel(login)

//The file path for the Excel file that the output will be sent to
String filePath = System.getProperty('user.dir') + '\\Data Files\\Demo.xlsx'
String sheetName = 'Testing'

//First clear the sheet, then store the new data
CustomKeywords.'file.Excel.clearSheet'(filePath, sheetName)

CustomKeywords.'file.Excel.addToSheet'(filePath, sheetName, login)


/**
 * @title runPanel
 * @info  Given a Map of data, display it in a pop-up and allow the user to edit it
 * @param login - Map of information to display to user > First row is used for labels. Can handle multiple rows of data
 * @return Map - Return the Map with edited values
 */
Map runPanel(Map login){
	
	//Create the frame where the data will be displayed
	JFrame frame = new JFrame('Login Info')
	
	//Create a panel to put inside of the frame with a border of 5 pixels
	JPanel panel = new JPanel(new BorderLayout(5,5))
	
	//Number of columns in the Excel sheet - here this number will serve as the number of inputs the user can change
	def colCnt = login[0].size()
	
	//Create a column of labels in the pop-up
	JPanel labels = new JPanel(new GridLayout(colCnt,1,2,2))
	
	//Creat the label objects to add to the panel
	for (def title : login[0]){
		
		labels.add(new JLabel(title, SwingConstants.RIGHT))
	}
	
	//Create a column of text inputs for each row of data in the spreadsheet
	JPanel controls = new JPanel(new GridLayout(colCnt,login.size(),2,2))
	
	List<JTextField> data = []
	
	def datum
	
	def idx = 0
	
	/* For each row of data in the spreadsheet we will create a column in the pop-up to display the values to the user and allow them to be edited
	 * We start iterating on node 1 (j = 1) rather than 0 as the 0 node is used above for the labels
	 *
	 * The ordering here is strange, we add down columns rather than across rows which would be easier
	 * This is due to how text fields are added to the panel. They are added left to right, top to bottom
	 *
	 * Given:
	 * input[0] = [First, Last]
	 * input[1] = [Ryan, Sepesy]
	 * input[2] = [Crystal, Riep]
	 *
	 * Output:
	 * --------------------------
	 * |First: |Ryan  |Crystal| |
	 * |Last:  |Sepesy|Riep   | |
	 * --------------------------
	 */
	for (int i = 0; i < colCnt; i++){
		
		for (int j = 1; j < login.size(); j++){
			
			datum = login[j][i]
			
			//Check if this data contains a URL, and if it does remove all but the DB name
			if (datum.contains("https")){
				data[idx] = new JTextField(stripURL(datum))
			}
			
			else{
				data[idx] = new JTextField(datum)
			}
			
			controls.add(data[idx])
			
			idx++
		}
	}
	
	//Add the labels and text fields to the panel, then open the pop-up
	panel.add(labels, BorderLayout.WEST)
	
	panel.add(controls, BorderLayout.CENTER)
	
	JOptionPane.showMessageDialog(frame, panel, "Demo testing", JOptionPane.QUESTION_MESSAGE)
	
	//Replace the values in login with the values entered in the pop-up, which is stored in data
	idx = 0
	
	//Replace the data stored in the login Map with the data edited by the user
	for (int i = 0; i < colCnt; i++){
		
		for (int j = 1; j < login.size(); j++){
			
			//If the original data was a URL then replace the web address that was removed above
			if (login[j][i].contains("https")){
				
				login[j][i] = returnURL(data[idx].getText())
			}
			
			else{
				login[j][i] = data[idx].getText()
			}
			
			idx++
		}
	}
	
	return login
}

/**
 * @title stripURL
 * @info  Given a QA testing URL, remove all bu the database name
 * @param url - The url string to strip the extraneous info from
 * @return String - The database name only
 */
String stripURL(String url){
	
	//The two slashed (\\) must be used before the dot due to the way the split function operates
	return url.split("https://")[1].split("\\.")[0]
}

/**
 * @title Given a QA database name, return the rest of the URL
 * @param url - The database name to be appended to
 * @return String - The full URL of the QA testing environment
 */
String returnURL(String url){
	
	return ("https://" + url + ".rmx.rentmanager.qa/")
}