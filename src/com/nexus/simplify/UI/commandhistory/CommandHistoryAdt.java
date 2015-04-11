package com.nexus.simplify.UI.commandhistory;

// @author A0108361M
import static org.junit.Assert.*;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommandHistoryAdt {

	private static final int EXPECTED_SIZE_OF_DOWNSTACK = 1;

	private static final String EMPTY_STRING = "";

	private static final int EXPECTED_SIZE_OF_UPSTACK = 4;

	private static final String ADD_TEST_STRING_FOUR = "add test string 4";

	private static final String ADD_TEST_STRING_THREE = "add test string 3";

	private static final String ADD_TEST_STRING_TWO = "add test string 2";

	private static final String ADD_TEST_STRING_ONE = "add test string 1";
	
	private static final String BROWSE_TEST_STRING_ONE = "browse test string 1";

	//------------------//
	// Class Attributes //
	//------------------//
	
	private static CommandHistory commandHistory;
	
	private static Deque<String> persistentUpStack;
	private static Deque<String> persistentDownStack;
	
	//----------------//
	// Initialization //
	//----------------//
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		commandHistory = new CommandHistory();
		
		persistentUpStack = new LinkedList<String>();
		persistentDownStack = new LinkedList<String>();
	}
	
	@Before
	public void setUp() throws Exception {
		persistentUpStack.push(ADD_TEST_STRING_ONE);
		persistentUpStack.push(ADD_TEST_STRING_TWO);
		persistentUpStack.push(ADD_TEST_STRING_THREE);
		persistentUpStack.push(ADD_TEST_STRING_FOUR);
	}
	
	//----------//
	// Teardown //
	//----------//

	@After
	public void tearDown() throws Exception {
		persistentUpStack.clear();
	}

	@Test
	public void testAddCommandToHistory() {
		commandHistory.addCommandToHistory(ADD_TEST_STRING_ONE);
		commandHistory.addCommandToHistory(ADD_TEST_STRING_TWO);
		commandHistory.addCommandToHistory(ADD_TEST_STRING_THREE);
		commandHistory.addCommandToHistory(ADD_TEST_STRING_FOUR);
		
		Deque<String> upStackFromCommandHistory = commandHistory.getUpStack();
		
		// test (a): check if all four strings were added to upStack 
		assertEquals(EXPECTED_SIZE_OF_UPSTACK, upStackFromCommandHistory.size());
		
		// test (b): check if all four strings were added in proper chronological order to upStack
		while (!persistentUpStack.isEmpty() && !upStackFromCommandHistory.isEmpty()) {
			String expectedStringValue = persistentUpStack.pop();
			String stringValueToBeTested = upStackFromCommandHistory.pop();
			assertEquals(expectedStringValue, stringValueToBeTested);
		}
	}
	
	@Test
	public void testBrowsePreviousCommand() {
		commandHistory.addCommandToHistory(BROWSE_TEST_STRING_ONE);
		
		// test (a): test if correct command is returned from function if upStack is not empty
		String stringValueToBeTestedForTestA = commandHistory.browsePreviousCommand();
		assertEquals(BROWSE_TEST_STRING_ONE, stringValueToBeTestedForTestA);
		
		// test (b): test if command is successfully pushed into downStack from upStack
		Deque<String> downStackFromCommandHistory = commandHistory.getDownStack();
		assertEquals(EXPECTED_SIZE_OF_DOWNSTACK, downStackFromCommandHistory.size());
		String stringValueToBeTestedForTestB = downStackFromCommandHistory.pop();
		assertEquals(BROWSE_TEST_STRING_ONE, stringValueToBeTestedForTestB);
		
		// test (c): test if browsePreviousCommand handles situations when upStack is empty.
		String stringValueToBeTestedForTestC = commandHistory.browsePreviousCommand();
		assertEquals(EMPTY_STRING, stringValueToBeTestedForTestC);
	}
	
	@Test
	public void testBrowseNextCommand() {
		// test (a): test if browseNextCommand handles situations when downStack is empty.
		String stringValueToBeTestedForTestA = commandHistory.browseNextCommand();
		assertEquals(EMPTY_STRING, stringValueToBeTestedForTestA);
		
		// test (b): test if correct command is returned from function if downStack is is not empty.
		commandHistory.addCommandToHistory(BROWSE_TEST_STRING_ONE);
		
	}

}
