package com.nexus.simplify.logic.unittesting;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nexus.simplify.logic.Delete;

public class TestDelete {
	
	// This method tests boundary cases for unit testing
	@Test
	public void testExecute() throws Exception {
		Delete stubDelete = new Delete();
		String[] testParameter;
		String feedback;
		
		// for invalid index
		testParameter = new String[]{"","dummy","dummy","dummy","dummy"};
		feedback = stubDelete.executeForTesting(testParameter);
		assertEquals(feedback,"please enter a task index to delete.");
		
		// for valid command
		testParameter = new String[]{"5","dummy","dummy","dummy","dummy"};
		feedback = stubDelete.executeForTesting(testParameter);
		assertEquals(feedback,"successfully deleted entry #5.");
	}
}
