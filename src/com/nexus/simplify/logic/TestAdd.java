package com.nexus.simplify.logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAdd {

	@Test
	public void testExecute() throws Exception {
		Add stubAdd = new Add();
		String[] testParameter;
		String feedback;
		
		// boundary test case for floating tasks partition
		testParameter = new String[]{null, "play dota", null, null, "1"};
		feedback = stubAdd.execute(testParameter);
		assertEquals(feedback,"successfully added floating task \"play dota\".");
		
		// boundary test case for timed tasks partition
		testParameter = new String[]{null, "anime seminar", "Jan 18 11:11", "Jan 18 12:22", "2"};
		feedback = stubAdd.execute(testParameter);
		assertEquals(feedback,"successfully added timed task \"anime seminar\".");
		
		// boundary test case for deadline tasks partition
		testParameter = new String[]{null, "finish game XYZ", "Tue Feb 22 10:00", "Tue Feb 22 10:00", "3"};
		feedback = stubAdd.execute(testParameter);
		assertEquals(feedback,"successfully added deadline task \"finish game XYZ\".");
	}
}