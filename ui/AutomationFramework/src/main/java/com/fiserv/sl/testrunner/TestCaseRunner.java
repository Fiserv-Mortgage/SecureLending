package com.fiserv.sl.testrunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.fiserv.sl.configuration.ActionKeyword;
import com.fiserv.sl.configuration.Constant;
import com.fiserv.sl.configuration.SupportLib;
import com.fiserv.sl.testrunner.TestCaseRunner;
import com.fiserv.sl.util.ExcelUtils;

public class TestCaseRunner {
	public static int testSuitindex, i, browsercount, testScriptIndex;
	public static boolean testSuitResult, testScriptResult;

	public static File lDir = new File("");
	public static String absolutePath = lDir.getAbsolutePath();
	public static File folder1 = new File(Constant.testreport);
	public static String Endmessage, sActionKeyword, destpath;
	public static Method method[];
	public static ActionKeyword ActionKeywords;

	private static String infoMessage = "", warningMessage = "", errorMessage = "", fatalMessage = "",
			infoTestStartDetail = "";

	public TestCaseRunner() {
		ActionKeyword a = new ActionKeyword();
		method = a.getClass().getMethods();
	}

	public static void main(String[] args) {
	
		TestCaseRunner startEngine = new TestCaseRunner();
		System.out.println("Starting Test Suit Execution");
		if (Constant.rerun > 0)
			for (int i = 0; i < Constant.rerun; i++) {
				setuprerun();
				startEngine.testsuitrun();
			}
	}

private static void setuprerun() {
		
		Constant.testSuitWorkbook = Constant.ExcelReportLocation;
		testSuitResult = true;
		testScriptResult = true;
		Constant.ObjrepLoc.clear();
		Constant.ObjrepType.clear();
		
		System.out.println("Starting Test Suit Execution now");
		
	}

	// Test Suit Handle Function
	private static void testsuitrun() {
		System.out.println("Test suit entry");
		testSuitResult = true;
		testScriptResult = true;
		int DataCount;
		Constant.ObjrepLoc.clear();
		Constant.ObjrepType.clear();
		try {
			if (Constant.BuildObjrep) {
				BuildObjRep();
				infoMessage = "Object repository is created successfully";
			} else {
				warningMessage = "Object repository creation is skipped please set buildobjrep = true to create object repository";
			}
		} catch (Exception e) {
			fatalMessage = "Object repository creation failed";
		}
		// Loop for Test Suit Control till close/Blank in the run mode is found
		for (testSuitindex = 1; testSuitindex < 1048576; testSuitindex++) {
			// Setting File for test suit
			// Clearing the parameters for various controls
			Constant.datacolumnnumber.clear();
			Constant.TestSuit.clear();
			DataCount = 0;
			try {
				ExcelUtils.setExcelFile(Constant.testSuitWorkbook, Constant.TestSuitsheet);
				ExcelUtils.ReadRowTestSuit();
			} catch (Exception e) {
				fatalMessage = "After read Row Test Suit Not able to configure test suit Please verify constant.java values for test suit";

				return;
			}
			if (Constant.TestSuit.get("RunMode").contains("Blank")) {
				warningMessage = "RunMode is not Defined for the test scirpt moving to next test script"
						+ Constant.TestSuit.get("Testcaseid");
			}
			if (Constant.TestSuit.get("RunMode").contains("Close")) {
				break;
			}
			if (Constant.TestSuit.get("RunMode").equalsIgnoreCase("Yes")) {
				if (Constant.TestSuit.get("File location").contains("Blank")
						|| Constant.TestSuit.get("SheetName").contains("Blank")) {
					fatalMessage = "Test script Location/sheet name is not provided for "
							+ Constant.TestSuit.get("Testcaseid");
					testSuitResult = true;
					continue;
				} else if (Constant.TestSuit.get("Browser").equalsIgnoreCase("Blank")) {
					fatalMessage = "Browser is not defined for the test case, Please mention Browser value (Chrome/Firefox/IE/Safari/Android/HTMLUnit/androidAPP/NoBrowser)for test Script"
							+ Constant.TestSuit.get("Testcaseid");
					testSuitResult = true;
					continue;
				}
				// Setting up test case file parameters
				Constant.TestScriptSheet = Constant.TestSuit.get("SheetName");
				Constant.TestScriptWorkbook = Constant.Testdataloc + Constant.TestSuit.get("File location");
				// Reading number of data column present in the test script
				ExcelUtils.setExcelFile(Constant.TestScriptWorkbook, Constant.TestScriptSheet);
				ExcelUtils.getTestDataCount();
				if (testSuitResult == false) {
					fatalMessage = "No Data(Yes) column found in test script" + Constant.TestScriptWorkbook;
					testSuitResult = true;
					continue;
				}

				for (DataCount = 1; DataCount <= Constant.datacolumnnumber.size(); DataCount++) {
					// SettingupDataColumn
					Constant.Data = Constant.datacolumnnumber.get(DataCount - 1);
					// Starting Test case in the logs
					infoTestStartDetail = "#### Executing Test case: " + Constant.TestSuit.get("Testcaseid")
							+ "; Description: " + Constant.TestSuit.get("Description") + " For Data: " + DataCount;
					// Setting File for test suit
					ExcelUtils.setExcelFile(Constant.testSuitWorkbook, Constant.TestSuitsheet);
					// Reading Browser
					SupportLib.countbrowser();
					// running scripts for all browsers requested
					for (browsercount = 0; browsercount < Constant.Browser.length; browsercount++) {
						ActionKeyword.OpenBrowser();

					}
					// Starting Node Test Case for the browser.
					infoMessage = "#### Executing Test case: " + Constant.TestSuit.get("Testcaseid") + "; Description: "
							+ Constant.TestSuit.get("Description") + " In " + ActionKeyword.browser
							+ " Browser for TestData:" + DataCount;

					try {
						teststepsrun();
					} catch (Exception e) {
						fatalMessage = "#Exception# Following Error encountered moving to the next test case"
								+ e.toString();
						continue;
					} catch (Throwable e) {
						fatalMessage = "#Throwable# Following Error encountered moving to the next test case"
								+ e.toString();
						continue;
					}
					if (!testSuitResult) {
						testSuitResult = true;
						ActionKeyword.Quit();
						continue;

					} else if (testSuitResult) {
						ExcelUtils.setExcelFile(Constant.testSuitWorkbook, Constant.TestSuitsheet);
						infoMessage = "### Test case: " + Constant.TestSuit.get("Testcaseid") + "; Description: "
								+ Constant.TestSuit.get("Description") + "; status Executed";
						ActionKeyword.Quit();

					}

				}
				// data block
				if (testSuitResult == false) {
					testSuitResult = true;
					continue;
				} else if (testSuitResult == true) {

				}

			}

			// if run mode yes block
		}
		if (Constant.TestSuit.get("RunMode").equalsIgnoreCase("No")) {

			ExcelUtils.setExcelFile(Constant.testSuitWorkbook, Constant.TestSuitsheet);
			Endmessage = "### Test case no: " + Constant.TestSuit.get("Testcaseid") + "; Description: "
					+ Constant.TestSuit.get("Description") + "; Status: NotRun";
		} else if (Constant.TestSuit.get("RunMode").equalsIgnoreCase("close")) {

			ExcelUtils.setExcelFile(Constant.testSuitWorkbook, Constant.TestSuitsheet);
			infoMessage = "####Closing Test Suite ####";
			Endmessage = "Closing Test Suite";

		}

		// test suit block
	}
	// function block

	public static void BuildObjRep() throws Exception {
		ActionKeyword.Quit();
		ExcelUtils.setExcelFile(Constant.ObjRepName, Constant.ObjRepSheetname);
		int i = 0, j = 0;
		String key, Value, Type;
		if (Constant.ObjrepLoc.isEmpty()) {
			while (!ExcelUtils.getCellData(i + 1, j).equalsIgnoreCase("close")
					|| !(ExcelUtils.getCellData(i, j).equalsIgnoreCase("Blank")
							&& !ExcelUtils.getCellData(i + 1, j).equalsIgnoreCase("Blank")
							&& !ExcelUtils.getCellData(i + 2, j).equalsIgnoreCase("Blank"))) {
				key = ExcelUtils.getCellData(i, j);
				Value = ExcelUtils.getCellData(i, j + 1);
				Type = ExcelUtils.getCellData(i, j + 2);
				Constant.ObjrepLoc.put(key, Value);
				Constant.ObjrepType.put(key, Type);

				if (ExcelUtils.getCellData(i + 1, j).equalsIgnoreCase("close")
						|| (ExcelUtils.getCellData(i + 1, j).equalsIgnoreCase("Blank")
								&& ExcelUtils.getCellData(i + 2, j).equalsIgnoreCase("Blank")
								&& ExcelUtils.getCellData(i + 3, j).equalsIgnoreCase("Blank"))) {
					break;
				}
				i++;
			}

		}
	}

	public static void teststepsrun() throws Throwable {

		sActionKeyword = null;
		testScriptIndex = 0;
		testScriptResult = true;
		testSuitResult = true;

		ActionKeyword.ValueCaptured.clear();
		ActionKeyword.Valuestored.clear();
		ActionKeyword.SpamCaptured.clear();
		ActionKeyword.ValuesCaptured.clear();
		ActionKeyword.ActiveFrameElement.clear();
		ActionKeyword.activeframe.clear();
		ActionKeyword.WindowHandle.clear();

		ExcelUtils.setExcelFile(Constant.TestScriptWorkbook, Constant.TestScriptSheet);
		try {

			Class cls = Class.forName("Configuration.ActionKeyword");
			Method method = null;
			testScriptResult = true;
			for (testScriptIndex = 1; testScriptIndex <= 1048576; testScriptIndex++) {
				if (testScriptResult == false || testSuitResult == false) {
					break;
				}
				Constant.TestCaseRow.clear();
				Constant.testcaserownum = testScriptIndex;
				ExcelUtils.ReadRowTestScript();

				if (Constant.TestCaseRow.get("Data").equalsIgnoreCase("#Skip")) {
					String skipMessage = Constant.TestCaseRow.get("TestStepID") + ": "
							+ Constant.TestCaseRow.get("TeststepDescription")
							+ ":Step is skipped based on data selections";
					String eSkipMessage = " Action Element" + Constant.TestCaseRow.get("Elementlocation")
							+ " Moving to next step";
					continue;
				}

				if (testScriptResult == false || testSuitResult == false) {
					break;
				}
				sActionKeyword = Constant.TestCaseRow.get("Action");
				if (sActionKeyword.contains("Blank")) {
					fatalMessage = Constant.TestCaseRow.get("Testcaseid") + sActionKeyword
							+ ":Action Keyword is not defined. Please defined Action Keyword";
					testScriptResult = false;
					testSuitResult = false;
					continue;
				}
				method = ((Class) cls).getDeclaredMethod(sActionKeyword, null);

				if (!(sActionKeyword.equalsIgnoreCase("Close"))) {
					method.invoke(sActionKeyword, null);

				} else if (sActionKeyword.contains("Close") && Constant.commonsteps == true) {
					return;
				} else if (sActionKeyword.contains("Close") && Constant.commonsteps == false) {
					ActionKeyword.Close();
					break;
				}
			}
		}

		// Error handling code
		catch (NoSuchMethodException e) {
			String logmessage = Constant.TestCaseRow.get("Testcaseid") + sActionKeyword + ":Invalid Action Kewyord";
			errorMessage = logmessage + e.toString();

			testSuitResult = false;

		} catch (NoSuchElementException e) {
			String logmessage = Constant.TestCaseRow.get("Testcaseid") + sActionKeyword
					+ ":Not able to search the Element on Active window";
			errorMessage = logmessage + e.toString();

			testSuitResult = false;
		} catch (UnreachableBrowserException e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Browser not available" + e.toString();

			testSuitResult = false;
		} catch (NullPointerException e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Action/ActionElement not found " + e.toString();

			testSuitResult = false;
		} catch (IllegalArgumentException e) {
			errorMessage = Constant.TestCaseRow.get("TestStepID") + "; Element not found " + e.toString();

			testSuitResult = false;
		} catch (NoSuchWindowException e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Browser Window is Closed" + e.toString();

			testSuitResult = false;

		} catch (WebDriverException e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Browser Window is Closed" + e.toString();

			testSuitResult = false;
		} catch (InvocationTargetException e) {

			Throwable cause = e.getCause();
			if (cause == null) {

				errorMessage = "Got InvocationTargetException, but the cause is null ";

			} else if (cause instanceof NoSuchElementException) {
				String logmessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + TestCaseRunner.sActionKeyword
						+ ": Not able to search the Element on Active window ";
				errorMessage = logmessage + cause.toString();

			} else if (cause instanceof java.io.IOException) {

				errorMessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + "; Error in communicating with browser: "
						+ cause.toString();
			}

			else if (cause instanceof org.openqa.selenium.NoSuchWindowException) {
				errorMessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + "; Browser Window is Closed: "
						+ cause.toString();
			}

			else if (cause instanceof org.openqa.selenium.remote.UnreachableBrowserException) {
				errorMessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + "; Browser not available: "
						+ cause.toString();
			}

			else if (cause instanceof NoSuchElementException) {
				errorMessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + sActionKeyword
						+ ":Not able to search the Element on Active window " + cause.toString();

			} else {
				errorMessage = Constant.TestCaseRow.get("Testcaseid") + Constant.TestCaseRow.get("TestStepID")
						+ Constant.TestCaseRow.get("ElementFinderType") + sActionKeyword + "  " + e.getCause()
						+ e.getMessage() + ":Exception is not handled";
			}

			testSuitResult = false;
		}

		catch (Exception e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Exception Not handled: " + e.toString();

			testSuitResult = false;
		}

		catch (Throwable e) {
			errorMessage = Constant.TestCaseRow.get("Testcaseid") + "; Throwable Exception Not handled: "
					+ e.toString();

			testSuitResult = false;

		}

	}
	}