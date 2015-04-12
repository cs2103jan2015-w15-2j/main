//@author generated
package com.nexus.simplify.logic.unittesting;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.Modify;

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
			assertEquals(feedback,"please enter a task index to modify.");
			
			// for invalid workload
			testParameter = new String[]{"5","dummy","dummy","dummy","invalid"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"please enter a valid workload.");
			
			// for nothing to modify
			testParameter = new String[]{"4","",null,"",null};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"please specify something to modify.");
			
			// for no name
			testParameter = new String[]{"5",null,"dummy","dummy","5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"task start time, end time, workload, modified.");
			
			// for no start time
			testParameter = new String[]{"1","dummy","","dummy","5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"task name, end time, workload, modified.");
			
			// for no end time
			testParameter = new String[]{"1","dummy","dummy",null,"5"};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"task name, start time, workload, modified.");
			
			// for no workload
			testParameter = new String[]{"1","dummy","dummy","dummy",""};
			feedback = stubModify.executeForTesting(testParameter);
			assertEquals(feedback,"task name, start time, end time, modified.");
		}
}
