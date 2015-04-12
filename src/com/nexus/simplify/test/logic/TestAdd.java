//@author generated
package com.nexus.simplify.logic.unittesting;
import static org.junit.Assert.*;
import org.junit.Test;
import com.nexus.simplify.logic.Add;

//@author A0094457U
public class TestAdd {
	
	// This method tests boundary cases for unit testing
	@Test
	public void testExecute() throws Exception {
		Add stubAdd = new Add();
		String[] testParameter;
		String feedback;
		
		// for no name partition
		testParameter = new String[]{"dummy", "", "dummy", "dummy", "1"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Please enter a name for this task.");
		
		// for invalid workload partition
		testParameter = new String[]{"dummy", "dummy", "dummy", "dummy", "dummy"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Please enter a valid workload.");
		
		// for floating tasks partition
		testParameter = new String[]{"", "play dota", null, null, "1"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Successfully added floating task \"play dota\" with workload 1.");
		
		// for floating tasks partition
		testParameter = new String[]{"dummy", "play dota", "", null, "1"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Successfully added floating task \"play dota\" with workload 1.");
		
		// for timed tasks partition
		testParameter = new String[]{"dummy", "anime seminar", "Jan 18 11:11", "Jan 18 12:22", "2"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Successfully added timed task \"anime seminar\" with starting time Jan 18 11:11 and ending time Jan 18 12:22 and workload of 2.");
		
		// for deadline tasks partition
		testParameter = new String[]{"dummy", "finish game XYZ", "Tue Feb 22 10:00", "Tue Feb 22 10:00", "3"};
		feedback = stubAdd.executeForTesting(testParameter);
		assertEquals(feedback,"Successfully added deadline task \"finish game XYZ\" with deadline Tue Feb 22 10:00 and workload of 3.");
	}
}