package com.fiserv.sl.execution;

import java.lang.reflect.Method;
import java.util.List;

import com.fiserv.sl.config.ActionKeywords;
import com.fiserv.sl.vo.TestScriptVO;
import com.fiserv.sl.vo.TestSuiteVO;

/**
 * 
 * @author atul.singh
 *
 */
public class TestExecutor {

	private static ActionKeywords actionKeywords = new ActionKeywords();
	private static Method method[] = actionKeywords.getClass().getMethods();;

	public static void executeScript(List<TestSuiteVO> testSuites) {
		for (TestSuiteVO testSuite : testSuites) {
			if (testSuite.getRunMode()) {
				List<TestScriptVO> testScripts = testSuite.getTestScripts();
				for (TestScriptVO script : testScripts) {
					String actionKeyWord = script.getActionKeyword();
					String pageObject = script.getPageObject();
					String data = script.getDataSet();
					try {
						execute_Actions(actionKeyWord, pageObject, data);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				System.out.println("TestCase not executable");
			}
		}
	}

	private static void execute_Actions(String actionKeyWord, String pageObject, String data) throws Exception {

		for (int i = 0; i < method.length; i++) {
			System.out.println(method[i].getName());
			if (method[i].getName().equals(actionKeyWord)) {
				method[i].invoke(actionKeywords, pageObject, data);
			}
		}
	}
}
