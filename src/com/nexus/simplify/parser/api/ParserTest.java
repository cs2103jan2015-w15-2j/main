package com.nexus.simplify.parser.api;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;

public class ParserTest {
	Parser _parser;
	
	
	@BeforeClass
	public void init() {
		_parser = new Parser();
	}

	@Test
	public void testInputBoundary() {
		UserCommand noCommand = _parser.parseInput("");
		assertEquals(null, noCommand.getOperationType());
		String[] noParam = new String[ParameterType.MAX_SIZE];
		assertArrayEquals(noParam, noCommand.getParameter());
		
		
	}
	
	@Test
	public void testDisplay() {
		// Boundary case for 
	}
	
	private void testOneDisplay(String userInput, String displayPref) {
		UserCommand userCommand = _parser.parseInput(userInput);
		assertEquals("display", userCommand.getOperationType());
		assertEquals(displayPref, userCommand.getParameter()[ParameterType.INDEX_POS]);
	}

}
