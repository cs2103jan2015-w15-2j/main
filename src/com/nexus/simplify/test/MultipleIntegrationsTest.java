//@author A0111035A

package com.nexus.simplify.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * System Testing for Add command
 */
public class MultipleIntegrationsTest extends AbstractIntegrationTest {

	@BeforeClass
	public static void oneTime() {
		initMainApp();
	}

	@Before
	public void resetLogicRequest(){
		_logicRequest.reset();
	}

	@Test
	public void testTimedTask() {
		testSingleInput("add drink coffee 8pm - 9pm", "add", null, "drink coffee", 
				"8pm", "9pm", null, null);
		testSingleInput("a meeting w5 tomorrow 2pm to 4pm", "add",  null, "meeting", 
				"tomorrow 2pm", "tomorrow 4pm", 5, null);
		testSingleInput("add dinner with tom tomorrow 1400 - 1600", "add",  null, "dinner with tom", 
				"tomorrow 1400", "tomorrow 1600", null, null);
		testSingleInput("add camping tomorrow 12pm to 8 pm 2 weeks from now w1", "add",  null, "camping",
				"tomorrow 12pm", "8pm 2 weeks from now", 1, null);

	}

	@Test
	public void testDeadlineTask() {
		testSingleInput("add V0.3 Demo tomorrow 3pm", "add",  null, "V0.3 Demo",
				"tomorrow 3pm", "tomorrow 3pm", null, null);
		testSingleInput("a V0.3 Demo 1 Apr 3pm w4", "add",  null, "V0.3 Demo",
				"1 Apr 3pm", "1 Apr 3pm", 4, null);
		testSingleInput("add V0.3 Demo w3 next week", "add",  null, "V0.3 Demo",
				"next week", "next week", 3, null);
		testSingleInput("add V0.3 Demo w5 5 days from now", "add",  null, "V0.3 Demo",
				"5 days from now", "5 days from now", 5, null);
	}

	@Test
	public void testGenericTask() {
		testSingleInput("add train for ippt w5", "add",  null, "train for ippt",
				null, null, 5, null);
		testSingleInput("a read", "add",  null, "read",
				null, null, null, null);
		testSingleInput("add learn web dev w5", "add",  null, "learn web dev",
				null, null, 5, null);
		testSingleInput("add catch up on lectures", "add",  null, "catch up on lectures",
				null, null, null, null);
	}

}
