//@author A0111035A

package com.nexus.simplify.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.LogicRequest;
import com.nexus.simplify.logic.api.Logic;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.data.CommandData;

public abstract class AbstractTest {
	private static MainApp _main;
	protected static Logic _logic;
	protected static Database _db;
	protected static LogicRequest _logicRequest;
	protected static UserCommand parsedCommand;
	protected static Parser _natty;

	public static void initMainApp() {
		try {
			_main = new MainApp();
			_logic = _main.getLogic();
			_db = MainApp.getDatabase();
			_logicRequest = _db.getLogicRequest();
			_natty = new Parser();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the given value into a collection of dates
	 * 
	 * @param value
	 * @return
	 */
	protected List<Date> parseCollection(String value) {
		List<DateGroup> dateGroup = _natty.parse(value);
		List<Date> dates = dateGroup.get(0).getDates();
		return dates;
	}

	/**
	 * Parses the given value, asserting that one and only one date is produced.
	 * 
	 * @param value
	 * @return
	 */
	protected Date parseSingleDate(String value) {
		if (value == null) {
			return null;
		}
		List<Date> dates = parseCollection(value);
		Assert.assertEquals(1, dates.size());
		return dates.get(0);
	}

	/**
	 * Asserts that the given user input is what Parser receives.
	 * 
	 * @param expected
	 * @throws Exception 
	 */
	protected void validateParserInput(String userInput) throws Exception {
		_logic.getParser().parseInput(userInput);
		String actual = _logic.getParser().getGivenInput();
		assertEquals(userInput, actual);
	}

	/**
	 * validates the input of parser and then proceed with parsing a single user input 
	 * 
	 * @param userInput
	 * @return
	 */
	protected UserCommand parseSingleInput(String userInput) {
		try {
			validateParserInput(userInput);
			parsedCommand = _logic.getParsedCommand(userInput);			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedCommand;
	}

	/**
	 * Converts a natural datetime string into Java date string representation
	 * @param dateTime
	 * @return
	 */
	protected String naturalToJavaString(String dateTime) {
		Date java = null;
		java = parseSingleDate(dateTime); 
		if (java == null) {
			return null;
		} else {
			return java.toString();
		}
	}


	/**
	 * Asserts that the given user input parses into the given index, name, start time, end time
	 * 
	 * @param userInput
	 * @param index
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param workload
	 * @param fileLocation
	 */
	protected UserCommand validateParsedCommand(String userInput, String operation, String index, String name, 
			String startTime, String endTime, Object workload, String fileLocation) {
		startTime = naturalToJavaString(startTime);
		endTime = naturalToJavaString(endTime);
		parsedCommand = parseSingleInput(userInput);
		assertEquals(CommandData.getInstance().getOperationType(operation), parsedCommand.getOperationType());
		assertEquals(index, parsedCommand.getIndex());
		assertEquals(name, parsedCommand.getName());
		assertEquals(removeSeconds(startTime), removeSeconds(parsedCommand.getStartTime()));
		assertEquals(removeSeconds(endTime), removeSeconds(parsedCommand.getEndTime()));
		assertEquals(workloadToString(workload), parsedCommand.getWorkload());
		assertEquals(fileLocation, parsedCommand.getFileLocation());
		return parsedCommand;
	}


	protected Date javaDateStringToDate(String javaDate) {
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(javaDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	protected Object indexStringToInt(String string) {
		if (string == null) {
			return null;
		} else {
			int index = Integer.parseInt(string);
			return index;
		}
	}

	protected String workloadToString(Object workload) {
		if (workload == null) {
			return null;
		} else {
			return Integer.toString((int)workload);
		}
	}

	protected int normaliseWorkload(Object workload) {
		if (workload == null) {
			return 0;
		} else {
			return Integer.parseInt((String)workload);
		}
	}

	protected void validateCallOnDatabase(String userInput, UserCommand userCommand) {
		try {
			String feedback = _logic.executeCommand(userInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(userCommand.getOperationType(), _logicRequest.getOperationType());
		assertEquals(indexStringToInt(userCommand.getIndex()), _logicRequest.getIndex());
		assertEquals(userCommand.getName(), _logicRequest.getName());
		assertEquals(parseSingleDate(userCommand.getStartTime()), _logicRequest.getStartTime());
		assertEquals(parseSingleDate(userCommand.getEndTime()), _logicRequest.getEndTime());
		assertEquals(normaliseWorkload(userCommand.getWorkload()), _logicRequest.getWorkload());
		assertEquals(userCommand.getFileLocation(), _logicRequest.getFileLocation());
	}

	protected void testSingleInput(String userInput, String operation, String index, String name, String startTime, 
			String endTime, Object workload, String fileLocation) {
		UserCommand userCommand = validateParsedCommand(userInput, operation, index, name, startTime, endTime, workload, fileLocation);
		validateCallOnDatabase(userInput, userCommand);
	}
	
	// Mon Apr 11 12:54:27 SGT 2016
	private String removeSeconds(String DateTime) {
		if (DateTime == null) {
			return null;
		}
		assert(DateTime.length() == 23);
		String left = DateTime.substring(0, 17);
		String right = DateTime.substring(19);
		return left+right;
	}
}
