package com.nexus.simplify.parser.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.joestelmach.natty.DateGroup;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.test.AbstractTest;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ParserTest extends AbstractTest {
	@BeforeClass
	public static void oneTime() {
		initMainApp();
	}

	@Before
	public void resetLogicRequest(){
		_logicRequest.reset();
	}

	public void testDisplay() {

	}

	@Test
	public void testAddName() {
		String[] noParam = new String[ParameterType.MAX_SIZE];
		OperationType add = OperationType.ADD;

		// Empty string boundary case for no name partition 
		validateParsedCommand("add", null, null, null, null, null, null);

		// " " string boundary case for whitespace parameter partition 
		validateParsedCommand("add  ", null, null, null, null, null, null);

		// "." string boundary case for valid name partition
		validateParsedCommand("add .", null, ".", null, null, null, null);

		// very long string boundary case for valid name partition
		char[] manyChars = new char[5000];
		Arrays.fill(manyChars, "a".charAt(0));
		String longString = new String(manyChars);
		validateParsedCommand("add " + longString, null, longString, null, null, null, null);
	}
	
	public void testAddDateTime() {

	}

	public void testAddGenericWorkload() {

	}
	
	public void testAddDeadlineWorkload() {

	}

	public void testAddTimedWorkload() {

	}

	public void testModify() {

	}

	public void testDelete() {

	}

	public void testClear() {

	}
	
	public void testDone() {

	}
	
	public void testUndo() {

	}
	
	public void testSearch() {

	}
}
