package com.nexus.simplify.UI.commandhistory;

// @author A0108361M
import static org.junit.Assert.*;

import java.util.Deque;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Provides a series of tests for the methods found in the CommandHistory class.
 * */
public class CommandHistoryTest {

	private static final String EMPTY_STRING = "";
	
	private static final String TEST_STRING_FOUR = "test string 4";
	private static final String TEST_STRING_THREE = "test string 3";
	private static final String TEST_STRING_TWO = "test string 2";
	private static final String TEST_STRING_ONE = "test string 1";

	//------------------//
	// Class Attributes //
	//------------------//
	
	private static CommandHistory commandHistory;
	
	//----------------//
	// Initialization //
	//----------------//
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		commandHistory = new CommandHistory();
	}
	
	@Before
	public void setUp() throws Exception {
		commandHistory.addCommandToHistory(TEST_STRING_ONE);
		commandHistory.addCommandToHistory(TEST_STRING_TWO);
		commandHistory.addCommandToHistory(TEST_STRING_THREE);
		commandHistory.addCommandToHistory(TEST_STRING_FOUR);
	}
	
	//----------//
	// Teardown //
	//----------//

	@After
	public void tearDown() throws Exception {
		commandHistory.clearAllHistory();
	}

	@Test
	public void testAddCommandToHistory() {
		Deque<String> upStackFromCommandHistory = commandHistory.getUpStack();

		assertEquals(TEST_STRING_FOUR, upStackFromCommandHistory.pop());
		assertEquals(TEST_STRING_THREE, upStackFromCommandHistory.pop());
		assertEquals(TEST_STRING_TWO, upStackFromCommandHistory.pop());
		assertEquals(TEST_STRING_ONE, upStackFromCommandHistory.pop());
	}
	
	@Test
	public void testBrowsePreviousCommand() {
		// test (a): test if correct command is returned from function if upStack is not empty
		String expectedValueTestA = commandHistory.browsePreviousCommand();
		assertEquals(TEST_STRING_FOUR, expectedValueTestA);
		
		// test (b): test if command is successfully pushed into downStack from upStack
		Deque<String> downStackFromCommandHistory = commandHistory.getDownStack();
		String expectedValueTestB = downStackFromCommandHistory.peek();
		assertEquals(TEST_STRING_FOUR, expectedValueTestB);
		
		// test (c): test if browsePreviousCommand handles situations when upStack is empty.
		
		// emptying upStack
		commandHistory.browsePreviousCommand();
		commandHistory.browsePreviousCommand();
		commandHistory.browsePreviousCommand();
		String expectedValueTestC = commandHistory.browsePreviousCommand();
		assertEquals(EMPTY_STRING, expectedValueTestC);
	}
	
	@Test
	public void testBrowseNextCommand() {
		// test (a): test if correct command is returned from function if downStack is is not empty.
		commandHistory.browsePreviousCommand();
		commandHistory.browsePreviousCommand();
		commandHistory.browsePreviousCommand();
		commandHistory.browsePreviousCommand();
		String expectedValueTestB = commandHistory.browseNextCommand();		
		assertEquals(TEST_STRING_TWO, expectedValueTestB);
		
		// test (b): test if command is successfully pushed into upStack from downStack.
		// commandHistory.browseNextCommand();
		Deque<String> upStackFromCommandHistory = commandHistory.getUpStack();
		String expectedValueTestC = upStackFromCommandHistory.peek();
		assertEquals(TEST_STRING_ONE, expectedValueTestC);
		
		// test (c): test if browseNextCommand handles situations when downStack is empty.
		
		// emptying downStack
		commandHistory.browseNextCommand();	
		commandHistory.browseNextCommand();	
		commandHistory.browseNextCommand();	
		
		String expectedValueTestA = commandHistory.browseNextCommand();
		assertEquals(EMPTY_STRING, expectedValueTestA);
	}

	@Test
	public void testClearAllHistory() {
		// ensures that both upStack and downStack are non-empty.
		commandHistory.browsePreviousCommand(); 
		commandHistory.browsePreviousCommand();
	
		commandHistory.clearAllHistory();
		
		Deque<String> upStackFromCommandHistory = commandHistory.getUpStack();
		Deque<String> downStackFromCommandHistory = commandHistory.getDownStack();
		
		assertEquals(0, upStackFromCommandHistory.size());
		assertEquals(0, downStackFromCommandHistory.size());
	}
}
