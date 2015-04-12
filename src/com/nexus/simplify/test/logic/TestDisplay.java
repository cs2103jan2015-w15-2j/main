//@author generated
package com.nexus.simplify.test.logic;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.core.Display;

//@author A0094457U
public class TestDisplay {
	// This method tests boundary cases for unit testing
	@Test
	public void testExecute() throws Exception {
		Display stubDisplay = new Display();
		String[] testParameter;
		String feedback;
		
		// for displaying a specific number of tasks
		testParameter = new String[]{"9","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed 9 tasks.");
		
		// for display by default setting
		testParameter = new String[]{"","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed tasks by default setting.");
		
		// for display all
		testParameter = new String[]{"all","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed all tasks.");
		
		// for display week
		testParameter = new String[]{"week","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed tasks due within a week.");
		
		// for display deadline
		testParameter = new String[]{"deadline","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed tasks by deadline.");
		
		// for display workload
		testParameter = new String[]{"workload","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Displayed tasks by workload.");
		
		// for invalid command
		testParameter = new String[]{"dummy","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"Invalid option for display, please try again.");
	}
}
