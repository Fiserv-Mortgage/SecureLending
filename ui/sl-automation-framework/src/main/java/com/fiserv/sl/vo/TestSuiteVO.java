package com.fiserv.sl.vo;

import java.util.List;
/**
 * 
 * @author atul.singh
 *
 */
public class TestSuiteVO {
	private String testCaseId;
	private String description;
	private String runMode;
	private String testCaseFileName;
	private String testCaseSheetName;
	private List<String> browsers;
	private List<TestScriptVO> testScripts;

	public TestSuiteVO(String testCaseId, String description, String runMode, String testCaseFileName,
			String testCaseSheetName, List<String> browsers, List<TestScriptVO> testScripts) {
		super();
		this.testCaseId = testCaseId;
		this.description = description;
		this.runMode = runMode;
		this.testCaseFileName = testCaseFileName;
		this.testCaseSheetName = testCaseSheetName;
		this.browsers = browsers;
		this.testScripts = testScripts;
	}

	public String getTestCaseId() {
		return testCaseId;
	}

	public String getDescription() {
		return description;
	}

	/*
	 * public String getRunMode() { return runMode; }
	 */

	public boolean getRunMode() {
		if (runMode.equalsIgnoreCase("yes")) {
			return true;
		}
		return false;
	}

	public String getTestCaseFileName() {
		return testCaseFileName;
	}

	public String getTestCaseSheetName() {
		return testCaseSheetName;
	}

	public List<String> getBrowsers() {
		return browsers;
	}

	public List<TestScriptVO> getTestScripts() {
		return testScripts;
	}

	@Override
	public String toString() {
		return "TestSuiteVO [testCaseId=" + testCaseId + ", description=" + description + ", runMode=" + runMode
				+ ", testCaseFileName=" + testCaseFileName + ", testCaseSheetName=" + testCaseSheetName + ", browsers="
				+ browsers + ", testScripts=" + testScripts + "]";
	}

}
