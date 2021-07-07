package com.fiserv.sl.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fiserv.sl.configuration.Constant;
import com.fiserv.sl.testrunner.TestCaseRunner;
    public class ExcelUtils {
                private static XSSFSheet ExcelWSheet;
                private static XSSFWorkbook ExcelWBook;
                static FormulaEvaluator evaluator;
                private static XSSFCell Cell;
                private static String errorMessage = "", fatalMessage = "";
                //private static XSSFRow Row;
 
             // This method is to set the File path and to open the Excel file
            	// Pass Excel Path and SheetName as Arguments to this method
            	public static void setExcelFile(String Path, String SheetName) {
            		try {
            			if (Path.contains("Blank") || SheetName.contains("Blank")) {
            				fatalMessage = "File Location/SheetName not found. Please provide File Location or Sheet Name";
            				IOException e = new IOException();
            				throw e;
            			}
            			FileInputStream ExcelFile;
            			ExcelFile = new FileInputStream(Path);
            			ExcelWBook = new XSSFWorkbook(ExcelFile);
            			ExcelWSheet = ExcelWBook.getSheet(SheetName);
            			evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
            		} catch (FileNotFoundException e) {
            			// TODO Auto-generated catch block
            			fatalMessage = "Not able to open File: " + Path + ": "
            					+ e.toString();
            			TestCaseRunner.testSuitResult = false;
            			TestCaseRunner.testScriptResult = false;
            			return;
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			fatalMessage = "Error in opening file: " + Path + e.toString();
            			TestCaseRunner.testSuitResult = false;
            			TestCaseRunner.testScriptResult = false;
            			return;
            		} catch (java.lang.NullPointerException e) {
            			// TODO Auto-generated catch block
            			fatalMessage = "Error in opening file: " + Path + e.toString();
            			TestCaseRunner.testSuitResult = false;
            			TestCaseRunner.testScriptResult = false;
            			return;
            		}
            	}
            	public static void ReadRowTestSuit() throws Exception {
            		Constant.TestSuit.put("Testcaseid",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 0));
            		Constant.TestSuit.put("Description",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 1));
            		Constant.TestSuit.put("RunMode",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 2));
            		Constant.TestSuit.put("File location",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 3));
            		Constant.TestSuit.put("SheetName",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 4));
            		Constant.TestSuit.put("Browser",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 5));
            		Constant.TestSuit.put("Category",
            				ExcelUtils.getCellData(TestCaseRunner.testSuitindex, 6));
            	}
            	
            	public static void getTestDataCount() {
            		try {
            			for (int i = 0; i < 256; i++) {
            				if (getCellData(0, i).equalsIgnoreCase("Data(Yes)")) {

            					Constant.datacolumnnumber.add(i);
            				} else if (getCellData(0, i).contains("Blank")) {
            					break;
            				} else {
            					continue;
            				}
            			}
            			if (Constant.datacolumnnumber.size() == 0) {
            				fatalMessage = "Not able to find Active Data column, Please make sure that atlease 1 column have 'Data(Yes)' header in respective test script";
            				TestCaseRunner.testSuitResult = false;
            				
            			}

            		} catch (Exception e) {
            			return;
            		}
            	}
            	public static void ReadRowTestScript() {
            		
            		if (TestCaseRunner.testSuitResult == false
            				|| TestCaseRunner.testScriptResult == false) {
            			return;
            		}

            		Constant.TestCaseRow.put("Testcaseid", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.Testcaseid));

            		Constant.TestCaseRow.put("TestStepID", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.TestStepID));
            		Constant.TestCaseRow.put("TeststepDescription", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.TeststepDescription));
            		Constant.TestCaseRow.put("ElementFinderType", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.ElementFinderType));
            		Constant.TestCaseRow.put("Elementlocation", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.Elementlocation));
            		Constant.TestCaseRow.put("Data",
            				ExcelUtils.getCellData(Constant.testcaserownum, Constant.Data));
            		Constant.TestCaseRow.put("Action", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.Action));
            		Constant.TestCaseRow.put("ActionSupportValue", ExcelUtils.getCellData(
            				Constant.testcaserownum, Constant.ActionSupportValue));
            		            	}

                
                //This method is to read the test data from the Excel cell
            //In this we are passing Arguments as Row Num, Col Num & Sheet Name
        	// This method is to read the test data from the Excel cell
        	// In this we are passing parameters/arguments as Row Num and Col Num
        	public static String getCellData(int RowNum, int ColNum) {
        		// CellValue CellValue = null;
        		String CellData = "Blank";
        		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

        		if (Cell == null) {
        			// java.lang.NullPointerException e = null;

        		} else {
        			if (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA) {
        				evaluator.evaluateFormulaCell(Cell);

        				if (Cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
        					if (DateUtil.isCellDateFormatted(Cell)) {
        						java.util.Date date = Cell.getDateCellValue();
        						SimpleDateFormat sdf = new SimpleDateFormat(
        								Constant.dataformate);
        						CellData = sdf.format(date);
        					} else {
        						CellData = String.valueOf(Cell.getNumericCellValue());
        					}
        				} else if (Cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
        					CellData = String.valueOf(Cell.getBooleanCellValue());
        					// System.out.println(CellData);
        					// System.out.println(Cell.getBooleanCellValue());
        				} else {
        					CellData = Cell.getStringCellValue();
        					}
        			} else if (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
        				CellData = String.valueOf(Cell.getNumericCellValue());

        			} else if (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
        				CellData = String.valueOf(Cell.getBooleanCellValue());
        			} else {
        				CellData = Cell.getStringCellValue();
        			}

        		}
        		if (CellData.isEmpty()) {
        			CellData = "Blank";

        		}
        		// System.out.println(CellData);
        		return CellData;
        	}
       	//This method is to get the row count used of the excel sheet
        	public static int getRowCount(String SheetName){
        			ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			int number=ExcelWSheet.getLastRowNum()+1;
        			return number;
        		}
 
			 
			
    	}