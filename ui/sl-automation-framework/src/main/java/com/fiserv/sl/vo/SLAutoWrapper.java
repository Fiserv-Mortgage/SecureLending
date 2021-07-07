package com.fiserv.sl.vo;

/**
 * 
 * @author atul.singh
 *
 */
public class SLAutoWrapper {
	private String resourcePath;
	private String testSuiteFileName;
	private String objReposFileName;

	public SLAutoWrapper(String resourcePath, String testSuiteFileName, String objReposFileName) {
		super();
		this.resourcePath = resourcePath;
		this.testSuiteFileName = testSuiteFileName;
		this.objReposFileName = objReposFileName;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public String getTestSuiteFileName() {
		return testSuiteFileName;
	}

	public String getObjReposFileName() {
		return objReposFileName;
	}

}
