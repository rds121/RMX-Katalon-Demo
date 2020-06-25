package file

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map as Map

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public class Excel {

	/**
	 * @title addToSheet
	 * @info  Write data from Map to specified Excel sheet
	 * @info  The format of the Map should be as follows - input = [row number : [column 1, column 2]]. See below for more examples
	 * 		  	
	 * 			Given: 
	 * 			input[0] = [Name, Date]
	 * 			input[1] = [Ryan, 1/1/2019]
	 * 			
	 * 			Outputs:
	 * 			---------------
	 * 			|Name|Date    |
	 * 			---------------
	 * 			|Ryan|1/1/2019|
	 * 			---------------
	 * 			
	 * 			NOTE: Every Map.value must be a List to be sent to Excel correctly 
	 * @param relPath - The path of the Excel sheet to save the data to
	 * @param sheetName - The sheet name of the Excel sheet where the data is stored
	 * @param input -  The Map containing data to send to the spreadsheet
	 */
	@Keyword
	def addToSheet(String relPath, String sheetName, Map input){
		FileInputStream fis = new FileInputStream(relPath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);

		int rowCount = 0
		int colNum = 0
		Row row
		Cell cell

		//The sheet is created by adding rows not columns, so we must add data for each column one row at a time
		for (int i = 0; i < input.size(); i++){

			row = sheet.createRow(rowCount);

			//Fill out the column data in each row by looping over the List in each node of the input Map
			for (def column : input[i]){
				cell = row.createCell(colNum)
				cell.setCellType(cell.CELL_TYPE_STRING);
				cell.setCellValue(column)
				colNum++
			}

			colNum = 0

			rowCount++
		}

		//Write sheet data to workbook
		FileOutputStream fos = new FileOutputStream(relPath);
		workbook.write(fos);
		fos.close();
	}

	/**
	 * @title clearSheet
	 * @info  Remove all data from a specified Excel sheet 
	 * @param relPath - The path of the Excel sheet to save the data to
	 * @param sheetName - The sheet name of the Excel sheet where the data is stored
	 */
	@Keyword
	def clearSheet(String relPath, String sheetName){
		FileInputStream fis = new FileInputStream(relPath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheet(sheetName);

		//If no sheet of the name exists, there will be an error when trying to clear it
		if(sheet == null){
			sheet = workbook.createSheet(sheetName)
		}

		Row row

		//Run over every row in the sheet and remove the data
		for (int index = sheet.getLastRowNum(); index >= sheet.getFirstRowNum(); index--) {

			row = sheet.getRow(index)

			if (row == null){
				break
			}

			sheet.removeRow(row);
		}

		//Write the now blank data to the workbook
		FileOutputStream fos = new FileOutputStream(relPath);
		workbook.write(fos);
		fos.close();
	}
}