package com.fiserv.sl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fiserv.sl.vo.SLAutoWrapper;

/**
 * 
 * @author richa.arya
 *
 */
public class MemoryUtil {
	private static MemoryUtil instance = null;
	private static SLAutoWrapper slAutoWrapper = null;

	private MemoryUtil(SLAutoWrapper slAutoWrapp) {
		slAutoWrapper = slAutoWrapp;
		initalize();
	}

	private static XSSFWorkbook testSuiteWorkbook;
	private static XSSFSheet testSuiteSheet;
	// private static Map<String, XSSFWorkbook> testCaseWorkBook;
	private static Map<String, XSSFSheet> testCaseSheetMap = new HashMap<String, XSSFSheet>();
	private static Properties objectRepository;

	private static void initalize() {
		testSuiteWorkbook = ExcelUtility.getWorkBook(slAutoWrapper.getResourcePath() + "\\TestSuit.xlsx");
		testSuiteSheet = ExcelUtility.getWorkSheet(testSuiteWorkbook, "Testsuit");
		loadTestScript(testSuiteSheet);
		loadObjectRepo();
	}

	private static void loadTestScript(XSSFSheet testSuiteSheet) {
		int rowCount = ExcelUtility.getRowCountOfSheet(testSuiteSheet);
		for (int i = 1; i < rowCount; i++) {
			String testCaseFile = testSuiteSheet.getRow(i).getCell(3).getStringCellValue();
			if(testCaseFile.equals("")){
				break;
			}
			String testCaseSheetName = testSuiteSheet.getRow(i).getCell(4).getStringCellValue();
			XSSFWorkbook testCaseWorkbook = ExcelUtility
					.getWorkBook(slAutoWrapper.getResourcePath() + "\\"+testCaseFile);
			XSSFSheet testCaseSheet = ExcelUtility.getWorkSheet(testCaseWorkbook, testCaseSheetName);
			testCaseSheetMap.put(testCaseFile, testCaseSheet);
		}
	}
	private static void loadObjectRepo() {
		try {
		FileInputStream fs = new FileInputStream(slAutoWrapper.getResourcePath()+"\\"+slAutoWrapper.getObjReposFileName());
		objectRepository=new Properties(System.getProperties());
			objectRepository.load(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  XSSFWorkbook getTestSuiteWorkbook() {
		return testSuiteWorkbook;
	}

	public  XSSFSheet getTestSuiteSheet() {
		return testSuiteSheet;
	}

	public  Map<String, XSSFSheet> getTestCaseSheetMap() {
		return testCaseSheetMap;
	}

	public Properties getObjectRepository() {
		return objectRepository;
	}

	public static MemoryUtil getInstance(SLAutoWrapper slAutoWrapper) {
		if (instance == null) {
			instance = new MemoryUtil(slAutoWrapper);
		}
		return instance;
	}

	public static MemoryUtil getInstance() {
		if (instance == null) {
			System.out.println("Initailize Properly");
		}
		return instance;
	}
}
