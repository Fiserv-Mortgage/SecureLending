package com.fiserv.sl.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Constant {

	// List of System Variables
	public static final String URL = "https://cert.loandocumentexchange.com/";
	public static final String Path_TestData = "D://Richa//Workspace//AutomationFramework//src//main//java//com//fiserv//slauto//dataengine";
	public static final String Path_OR = "D://Richa//Workspace//AutomationFramework//src//main//java//com//fiserv//slauto//dataengine";
	public static final String File_TestData = "dataengine.xlsx";

	public static final String Sourceloc = System.getProperty("user.dir");

	// List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;
	public static final int Col_TestScenarioID = 1;
	public static final int Col_PageObject = 3;
	public static final int Col_ActionKeyword = 4;
	
	public static int rerun = 0;
	
	public static String ExcelReportLocation = "";
	
	public static boolean BuildObjrep = true;
	public static final String ObjRepName = Sourceloc + "\\src\\DataEngine\\ObjRep.xlsx";
	public static final String ObjRepSheetname = "ObjRep";

	public static boolean commonsteps = false;
	public static int testcaserownum;
	public static int ActionSupportValue = 5;

	public static int TeststepDescription = 2;
	public static int ElementFinderType = 3;
	public static int Elementlocation = 4;

	public static String dataformate = "dd/M/yyyy";

	public static int Action = 6;
	public static int Data;
	public static int Testcaseid = 0;
	public static int TestStepID = 1;
	// New entry in Constant variable
	public static final int Col_RunMode = 2;

	public static String Browser[];

	public static String TestScriptSheet;
	public static String TestScriptWorkbook;

	public static int browsercount;

	public static List<Integer> datacolumnnumber = new ArrayList<Integer>();

	public static int ImplicitWait = 0;
	public static int PageLoadWait = 120;
	public static boolean FirefoxTabprefrence = true;
	public static String testreport = Sourceloc + "\\src\\reports\\";
	public static String TestSuitsheet = "Testsuit";
	public static String IEDRIVER = Sourceloc + "\\driver\\IEDriverServer.exe";
	public static boolean IECleanHistoryFlag = true;
	public static HashMap<String, String> TestCaseRow = new HashMap<String, String>();
	public static String Testdataloc = Sourceloc + "\\src\\DataEngine\\";
	public static String testSuitWorkbook = Testdataloc + "Testsuit.xlsx";
	public static HashMap<String, String> TestSuit = new HashMap<String, String>();

	public static boolean WaitforAjax = true;
	public static boolean AdsHelpControl = true;
	public static int AjaxWait = 40;
	public static int ExplicitWait = 50;

	// Object Repository Control
	public static ConcurrentHashMap<String, String> ObjrepLoc = new ConcurrentHashMap<String, String>();
	public static ConcurrentHashMap<String, String> ObjrepType = new ConcurrentHashMap<String, String>();

	public static boolean ChromeExtEnable = false;
	public static boolean highlighter = true;
	public static String chromedriverlocation = Sourceloc + "\\driver\\chromedriver.exe";

	// List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	// New entry in Constant variable
	public static final String Sheet_TestCases = "Test Cases";

	// List of Test Data
	public static final String UserName = "testuser_3";
	public static final String Password = "Test@123";

}