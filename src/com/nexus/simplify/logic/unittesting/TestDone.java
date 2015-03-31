package com.nexus.simplify.logic.unittesting;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.Done;

public class TestDone {
	// This method tests boundary cases for unit testing
	@Test
	public void testExecute() throws Exception {
		Done stubDone = new Done();
		String[] testParameter;
		String feedback;
		
		// for invalid index
		testParameter = new String[]{"dummy","dummy","dummy","dummy","dummy"};
		feedback = stubDone.executeForTesting(testParameter);
		assertEquals(feedback,"please enter a task index to mark as done.");
				
		// for valid command
		testParameter = new String[]{"5","dummy","dummy","dummy","dummy"};
		feedback = stubDone.executeForTesting(testParameter);
		assertEquals(feedback,"successfully marked entry #5 as done.");
	}
}
