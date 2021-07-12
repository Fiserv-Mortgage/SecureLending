package com.fiserv.sl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.fiserv.sl.util.ExcelUtility;
import com.fiserv.sl.vo.TestScriptVO;
/**
 * 
 * @author Richa.arya
 *
 */
public class TestSuiteManager {

	public static void getTestSuit() {

	}

	public static List<TestScriptVO> getTestScripts(XSSFSheet testScriptSheet) {
		int testScriptCount = ExcelUtility.getRowCountOfSheet(testScriptSheet);
		List<TestScriptVO> testScriptList = new ArrayList<TestScriptVO>();
		TestScriptVO testScript = null;
		if (testScriptCount > 1) {
			for (int i = 1; i < testScriptCount; i++) {
				String testCaseId = testScriptSheet.getRow(i).getCell(0).getStringCellValue();
				String testStepId = testScriptSheet.getRow(i).getCell(1).getStringCellValue();
				String description = testScriptSheet.getRow(i).getCell(2).getStringCellValue();
				String pageName = testScriptSheet.getRow(i).getCell(3).getStringCellValue();
				//System.out.println(testCaseId+"_"+testStepId);
				String pageObject = testScriptSheet.getRow(i).getCell(4).getStringCellValue();
				String actionKeyWord = testScriptSheet.getRow(i).getCell(5).getStringCellValue();
				String dataSet = testScriptSheet.getRow(i).getCell(6).getStringCellValue();
				String result = testScriptSheet.getRow(i).getCell(7).getStringCellValue();
				testScript = new TestScriptVO(testCaseId, testStepId, description, pageName, pageObject, actionKeyWord,
						dataSet, result);
				testScriptList.add(testScript);
			}
		}
		return testScriptList;

	}

}
