package com.nexus.simplify.logic.unittesting;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.Display;

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
		assertEquals(feedback,"displayed 9 tasks.");
		
		// for display by default setting
		testParameter = new String[]{"","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"displayed tasks by default setting.");
		
		// for display all
		testParameter = new String[]{"all","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"displayed all tasks.");
		
		// for display week
		testParameter = new String[]{"week","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"displayed tasks due within a week.");
		
		// for display deadline
		testParameter = new String[]{"deadline","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"displayed tasks by deadline.");
		
		// for display workload
		testParameter = new String[]{"workload","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"displayed tasks by workload.");
		
		// for invalid command
		testParameter = new String[]{"dummy","","","",""};
		feedback = stubDisplay.executeForTesting(testParameter);
		assertEquals(feedback,"invalid option for display, please try again.");
	}
}
