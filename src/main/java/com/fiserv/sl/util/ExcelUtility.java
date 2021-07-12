package com.fiserv.sl.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fiserv.sl.execution.DriverScript;

/**
 * 
 * @author richa.arya
 *
 */
public class ExcelUtility {

	public static int getRowCountOfSheet(XSSFSheet excelWSheet) {
		int iNumber = 0;
		try {
			iNumber = excelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowCount | Exception desc : " + e.getMessage());
			DriverScript.bResult = false;
		}
		return iNumber;
	}

	public static XSSFWorkbook getWorkBook(String path) {

		XSSFWorkbook excelWBook = null;

		try {
			FileInputStream excelFile = new FileInputStream(path);
			excelWBook = new XSSFWorkbook(excelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return excelWBook;
	}

	public static XSSFSheet getWorkSheet(String path, String sheetName) {

		XSSFSheet excelWSheet = null;
		XSSFWorkbook excelWBook = null;
		try {
			FileInputStream excelFile = new FileInputStream(path);
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet("sheetName");
			excelWBook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelWSheet;

	}
	
	public static XSSFSheet getWorkSheet(XSSFWorkbook excelWBook, String sheetName) {

		XSSFSheet excelWSheet=excelWBook.getSheet(sheetName);
		return excelWSheet;

	}

}
