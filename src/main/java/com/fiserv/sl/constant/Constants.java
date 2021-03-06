package com.fiserv.sl.constant;

/**
 * 
 * @author richa.arya
 *
 */

public class Constants {
	public static final String URL = "https://test.secureshareportal.com/";
//	public static final String Path_TestData = "D://Tools QA Projects//trunk//Hybrid KeyWord Driven//src//dataEngine//DataEngine.xlsx";
//	public static final String Path_OR = "D://Tools QA Projects//trunk//Hybrid KeyWord Driven//src//config//OR.txt";
	public static final String File_TestData = "DataEngine.xlsx";
	public static final String Path_TestData = "src//main//java//dataEngine//DataEngine.xlsx";
	public static final String Path_OR = "src//main//java//dataEngine//OR.txt";
	
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
 
	//Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_PageObject =4 ;
	public static final int Col_ActionKeyword =5 ;
	public static final int Col_RunMode =2 ;
	public static final int Col_Result =3 ;
	public static final int Col_DataSet =6 ;
	public static final int Col_TestStepResult =7 ;
 
	// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
 
}
