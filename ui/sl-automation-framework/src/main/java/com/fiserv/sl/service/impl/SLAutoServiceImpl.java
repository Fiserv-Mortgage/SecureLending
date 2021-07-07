package com.fiserv.sl.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.fiserv.sl.config.ActionKeywords;
import com.fiserv.sl.execution.TestExecutor;
import com.fiserv.sl.service.SLAutoService;
import com.fiserv.sl.service.TestSuiteManager;
import com.fiserv.sl.util.ExcelUtility;
import com.fiserv.sl.util.MemoryUtil;
import com.fiserv.sl.vo.SLAutoWrapper;
import com.fiserv.sl.vo.TestScriptVO;
import com.fiserv.sl.vo.TestSuiteVO;

/**
 * 
 * @author richa.arya
 *
 */
class SLAutoServiceImpl implements SLAutoService {
	public static ActionKeywords actionKeywords;
	public static Method method[];

	private SLAutoServiceImpl() {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}

	private static SLAutoServiceImpl service = null;

	public void executeTestSuite(SLAutoWrapper slAutoWrapper) {
		MemoryUtil memory = MemoryUtil.getInstance(slAutoWrapper);

		try {

			TestSuiteVO testSuite = null;
			List<TestSuiteVO> testSuites = new ArrayList<TestSuiteVO>();
			XSSFSheet testSuitSheet = memory.getTestSuiteSheet();
			int testSuitCount = ExcelUtility.getRowCountOfSheet(testSuitSheet);
			if (testSuitCount > 1) {
				for (int i = 1; i < testSuitCount; i++) {
					if (testSuitSheet.getRow(i).getCell(0).getStringCellValue().equals("")) {
						break;
					}
					String testCaseId = testSuitSheet.getRow(i).getCell(0).getStringCellValue();
					String description = testSuitSheet.getRow(i).getCell(1).getStringCellValue();
					String runMode = testSuitSheet.getRow(i).getCell(2).getStringCellValue();
					String testScriptFileName = testSuitSheet.getRow(i).getCell(3).getStringCellValue();
					String testScriptSheetName = testSuitSheet.getRow(i).getCell(4).getStringCellValue();
					String browser = testSuitSheet.getRow(i).getCell(5).getStringCellValue();
					List<String> browsers = new ArrayList<String>();
					browsers.add(browser);
					List<TestScriptVO> testScripts = TestSuiteManager
							.getTestScripts(memory.getTestCaseSheetMap().get(testScriptFileName));
					testSuite = new TestSuiteVO(testCaseId, description, runMode, testScriptFileName,
							testScriptSheetName, browsers, testScripts);
					testSuites.add(testSuite);

				}
			}
			TestExecutor.executeScript(testSuites);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public static SLAutoServiceImpl getInstance() {
		if (service == null) {
			return new SLAutoServiceImpl();
		}
		return service;
	}
}
