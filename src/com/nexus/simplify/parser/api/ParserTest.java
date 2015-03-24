package com.nexus.simplify.parser.api;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;

public class ParserTest {
	private static Parser _parser;
	
	
	@BeforeClass
	public static void init() {
		_parser = new Parser();
	}

	@Test
	public void testAddBoundaries() {
		String[] noParam = new String[ParameterType.MAX_SIZE];
		OperationType add = OperationType.ADD;

		// Empty string boundary case for no name partition 
		validateParse("add", add, noParam);
		
		// " " string boundary case for whitespace parameter partition 
		validateParse("add  ", add, noParam);
		
		// "." string boundary case for valid name partition
		String[] expectedParam = new String[ParameterType.MAX_SIZE];
		setParameterValue(expectedParam, ParameterType.NEW_NAME_POS, ".");
		validateParse("add .", add, expectedParam);
		
		// very long string boundary case for valid name partition
		String longName = "add asda asd asd add asda asdadd asda asd asd  "
				+ "asd add asda asd asd add asda asd asd add asda asd asd add asda asd asd "
				+ "add asda asd asd add asda asd asd add asda asd asd add asda asd asd ";
		String[] expectedLongParam = new String[ParameterType.MAX_SIZE];
		setParameterValue(expectedLongParam, ParameterType.NEW_NAME_POS, longName);
		validateParse(longName, add, expectedParam);
	}
	
	private void validateParse(String input, OperationType expectedOp, String[] expectedParam) {
		UserCommand resultCommand = _parser.parseInput(input);
		OperationType resultOp = resultCommand.getOperationType();
		String[] resultParam = resultCommand.getParameter();
		assertEquals(expectedOp, resultOp);
		assertArrayEquals(expectedParam, resultParam);
	}
	
	private void setParameterValue(String[] paramArr, int paramType, String param) {
		paramArr[paramType] = param;
	}
	
//	private void testOneDisplay(String userInput, String displayPref) {
//		UserCommand userCommand = _parser.parseInput(userInput);
//		assertEquals("display", userCommand.getOperationType());
//		assertEquals(displayPref, userCommand.getParameter()[ParameterType.INDEX_POS]);
//	}

}
