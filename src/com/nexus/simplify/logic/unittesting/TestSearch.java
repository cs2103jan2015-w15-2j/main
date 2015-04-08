package com.nexus.simplify.logic.unittesting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nexus.simplify.logic.Search;

public class TestSearch {
	// This method tests boundary cases for unit testing
	@Test
	public void testExecute() throws Exception {
		Search stubSearch = new Search();
		boolean[] testParameter;
		String feedback;
			
		// for parser error partition
		testParameter = new boolean[]{false,false,false,false,false};
		feedback = stubSearch.executeForTesting(testParameter);
		assertEquals(feedback,"Parser failed to set truth value.");
			
		// for successful search partition
		testParameter = new boolean[]{true,false,false,false,false};
		feedback = stubSearch.executeForTesting(testParameter);
		assertEquals(feedback,"Search result displayed.");
	}
}
