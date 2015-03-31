package com.nexus.simplify.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.logic.Logic;
import com.nexus.simplify.logic.usercommand.UserCommand;

public abstract class AbstractTest {
	private static MainApp _main;
	protected static Logic _logic;
	protected static UserCommand parsedCommand;
	private Parser _natty;

	public static void initMainApp() {
		try {
			_main = new MainApp();
			_logic = _main.getLogic();
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
		return dateGroup.isEmpty() ? new ArrayList<Date>() : dateGroup.get(0).getDates();
	}

	/**
	 * Parses the given value, asserting that one and only one date is produced.
	 * 
	 * @param value
	 * @return
	 */
	protected String parseSingleDate(String value) {
		List<Date> dates = parseCollection(value);
		Assert.assertEquals(1, dates.size());
		return dates.get(0).toString();
	}

	/**
	 * Asserts that the given user input is what Parser receives.
	 * 
	 * @param expected
	 */
	protected void validateParserInput(String expected) {
		String actual = _logic.getParser().getGivenInput();
		assertEquals(expected, actual);
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
	protected String NaturalToJavaString(String dateTime) {
		String java = null;
		java = parseSingleDate(dateTime);
		return java;
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
	protected UserCommand validateParsedCommand(String userInput, String index, String name, String startTime, String endTime, 
			String workload, String fileLocation) {
		startTime = parseSingleDate(startTime);
		endTime = parseSingleDate(endTime);
		parsedCommand = parseSingleInput(userInput);
		assertEquals(index, parsedCommand.getIndex());
		assertEquals(name, parsedCommand.getName());
		assertEquals(startTime, parsedCommand.getStartTime());
		assertEquals(endTime, parsedCommand.getEndTime());
		assertEquals(fileLocation, parsedCommand.getFileLocation());
		return parsedCommand;
	}


	protected Date JavaDateStringToDate(String javaDate) {
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

	protected void validateCallOnDatabase(UserCommand userCommand) {
		//
	}

}
