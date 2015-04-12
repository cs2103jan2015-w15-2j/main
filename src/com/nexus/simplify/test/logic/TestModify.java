//@author generated
package com.nexus.simplify.test.logic;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.core.Modify;

//@author A0094457U
public class TestModify {
	
	// This method tests boundary cases for unit testing
		@Test
		public void testExecute() throws Exception {
			Modify stubModify = new Modify();
			String[] testParameter;
			String feedback;
			
			// for invalid index
			testParameter = new String[]{"","dummy","dummy","dummy","5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Please enter a task index to modify.");
			
			// for invalid workload
			testParameter = new String[]{"5","dummy","dummy","dummy","invalid"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Please enter a valid workload.");
			
			// for nothing to modify
			testParameter = new String[]{"4","",null,"",null};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Please specify something to modify.");
			
			// for no name
			testParameter = new String[]{"5",null,"dummy","dummy","5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Task time, workload, modified.");
			
			// for no time
			testParameter = new String[]{"1","dummy","",null,"5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Task name, workload, modified.");
			
			// for no workload
			testParameter = new String[]{"1","dummy","dummy","dummy",""};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"Task name, time, modified.");
		}
}
