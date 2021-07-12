package com.fiserv.sl.vo;

/**
 * 
 * @author richa.arya
 *
 */
public class TestScriptVO {
	private String testCaseId;
	private String testStepId;
	private String description;
	private String pageName;
	private String pageObject;
	private String actionKeyword;
	private String dataSet;
	private String result;

	public TestScriptVO(String testCaseId, String testStepId, String description, String pageName, String pageObject,
			String actionKeyword, String dataSet, String result) {
		super();
		this.testCaseId = testCaseId;
		this.testStepId = testStepId;
		this.description = description;
		this.pageName = pageName;
		this.pageObject = pageObject;
		this.actionKeyword = actionKeyword;
		this.dataSet = dataSet;
		this.result = result;
	}

	public String getTestCaseId() {
		return testCaseId;
	}

	public String getTestStepId() {
		return testStepId;
	}

	public String getPageName() {
		return pageName;
	}

	public String getPageObject() {
		return pageObject;
	}

	public String getActionKeyword() {
		return actionKeyword;
	}

	public String getDataSet() {
		return dataSet;
	}

	public String getResult() {
		return result;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "TestScriptVO [testCaseId=" + testCaseId + ", testStepId=" + testStepId + ", description=" + description
				+ ", pageName=" + pageName + ", pageObject=" + pageObject + ", actionKeyword=" + actionKeyword
				+ ", dataSet=" + dataSet + ", result=" + result + "]";
	}

}
