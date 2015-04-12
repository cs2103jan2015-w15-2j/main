//@author A0111035A

package com.nexus.simplify.parser.api;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nexus.simplify.test.AbstractTest;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Unit Test class for Parser component. 
 * 
 * Actual input command string is given to Parser.parse method. 
 * DateTime input used are supported by natty at http://natty.joestelmach.com/try.jsp
 * Unsupported DateTime strings will be taken to be name of the task, thus there will be error during comparison 
 * if the intended DateTime string is not recognised as a DateTime
 * Results from parsing are stored in CommandData and the values are taken for comparison
 * 
 * Expected output are manually specified, individually.
 * Expected DateTime output is the DateTime that natty library parses from the given string 
 * that implies DateTime Element
 * 
 *
 */
public class ParserTest extends AbstractTest {
	final String DISPLAY = "display";
	final String ADD = "add";
	final String MODIFY = "modify";
	final String DELETE = "delete";
	final String CLEAR = "clear";
	final String DONE = "done";
	final String UNDO = "undo";
	final String SEARCH = "search";
	final String TEST_NAME = "Parser Unit Testing";

	@BeforeClass
	public static void oneTime() {
		initMainApp();
	}

	@Before
	public void resetLogicRequest(){
		_logicRequest.reset();
	}

	@Test
	public void testDisplay() {
		validateParsedCommand("display", DISPLAY, null, null, null, null, null, null);
		validateParsedCommand("display week", DISPLAY, "week", null, null, null, null, null);
		validateParsedCommand("display deadline", DISPLAY, "deadline", null, null, null, null, null);
		validateParsedCommand("display default", DISPLAY, "default", null, null, null, null, null);

		// invalid display parameters
		validateParsedCommand("display -1", DISPLAY, "-1", null, null, null, null, null);
		validateParsedCommand("display .", DISPLAY, ".", null, null, null, null, null);
		validateParsedCommand("display asdasd9808", DISPLAY, "asdasd9808", null, null, null, null, null);
	}

	@Test
	public void testAddName() {
		// Empty string boundary case for no name partition 
		validateParsedCommand("add", ADD, null, null, null, null, null, null);

		// " " string boundary case for whitespace parameter partition 
		validateParsedCommand("add  ", ADD, null, null, null, null, null, null);

		// "." string boundary case for valid name partition
		validateParsedCommand("add .", ADD, null, ".", null, null, null, null);

		// very long string boundary case for valid name partition
		char[] manyChars = new char[5000];
		Arrays.fill(manyChars, "a".charAt(0));
		String longString = new String(manyChars);
		validateParsedCommand("add " + longString, ADD, null, longString, null, null, null, null);

		// trailing whitespace
		validateParsedCommand("a trailing ", ADD, null, "trailing", null, null, null, null);
		validateParsedCommand("add trailing  ", ADD, null, "trailing", null, null, null, null);

		// leading whitespace
		validateParsedCommand("add  leading ", ADD, null, "leading", null, null, null, null);
		validateParsedCommand("add   leading  ", ADD, null, "leading", null, null, null, null);

		// cases to stress-test Parser  
		// words with a mix of digits
		validateParsedCommand("add CS2100", ADD, null, "CS2100", null, null, null, null);
		// name contains word that contains workload param
		validateParsedCommand("add w1w1", ADD, null, "w1w1", null, null, null, null);
	}

	@Test
	public void testAddDeadline() {
		// Natty supported common English phrases used to denote date
		validateAddSingleDateTime("tomorrow");
		validateAddSingleDateTime("day after tomorrow");
		validateAddSingleDateTime("week after tomorrow");

		validateAddSingleDateTime("next day");
		validateAddSingleDateTime("next Monday");
		validateAddSingleDateTime("next week");
		validateAddSingleDateTime("next month");
		validateAddSingleDateTime("next year");
		validateAddSingleDateTime("next spring");
		validateAddSingleDateTime("next summer");
		validateAddSingleDateTime("next autumn");
		validateAddSingleDateTime("next winter");

		validateAddSingleDateTime("upcoming day");
		validateAddSingleDateTime("upcoming Sunday");
		validateAddSingleDateTime("upcoming week");
		validateAddSingleDateTime("upcoming month");
		validateAddSingleDateTime("upcoming year");
		validateAddSingleDateTime("upcoming spring");
		validateAddSingleDateTime("upcoming summer");
		validateAddSingleDateTime("upcoming autumn");
		validateAddSingleDateTime("upcoming winter");

		// Specifying specific date
		validateAddSingleDateTime("15 April");
		validateAddSingleDateTime("15 Apr");
		validateAddSingleDateTime("15th Apr");
		validateAddSingleDateTime("15 Apr 2016");
		validateAddSingleDateTime("15 April 2016");
		validateAddSingleDateTime("15/4");
		validateAddSingleDateTime("15/4");
		validateAddSingleDateTime("15/4/2015");
		validateAddSingleDateTime("15 Apr");

		// Specifying Time
		validateAddSingleDateTime("4pm");
		validateAddSingleDateTime("4 pm");
		validateAddSingleDateTime("1600");
		validateAddSingleDateTime("1600h");
		validateAddSingleDateTime("1600 hour");

		//Specifying Date and Time
		validateAddSingleDateTime("tomorrow 4pm");
		validateAddSingleDateTime("next day 1600 pm");
		validateAddSingleDateTime("next Monday 1600");
		validateAddSingleDateTime("next year 1600h");
		validateAddSingleDateTime("next spring 1600 hour");
		validateAddSingleDateTime("week after tomorrow 4pm");
		validateAddSingleDateTime("week after tomorrow 8am");
		validateAddSingleDateTime("week after 17 April 8 am");
		validateAddSingleDateTime("month after 17 April 8");
		validateAddSingleDateTime("day after tomorrow 0800");		

		// boundary case for invalid deadline
		validateParsedCommand("add no deadline 20 apri", ADD, null, "no deadline apri", "20", "20", null, null);
	}

	@Test
	public void testAddGenericWorkload() {
		// boundary case for valid workload
		validateAddNameWorkLoad("LOWEST WORKLOAD", 1);
		validateAddNameWorkLoad("HIGHEST WORKLOAD", 5);

		// boundary case for invalid workload
		validateParsedCommand("add boundary case w0", ADD, null, "boundary case w0", null, null, null, null);
		validateParsedCommand("add boundary case w6", ADD, null, "boundary case w6", null, null, null, null);

		// name contains a word which contains workload param as subsequence
		validateAddNameWorkLoad("w1a", 5);
	}

	@Test
	public void testAddDeadlineWorkload() {
		validateAddNameSingleDateTimeWorkload(TEST_NAME, "tomorrow", 1);

		// boundary case for invalid workload
		validateParsedCommand("add boundary case w0 20 oct", ADD, null, "boundary case w0", "20 oct", "20 oct", null, null);

		// boundary case for invalid name
		validateParsedCommand("add w5 20/04", ADD, null, null, "20/04", "20/04", 5, null);

		// boundary case for invalid deadline
		validateParsedCommand("add no deadline w5 apri", ADD, null, "no deadline apri", null, null, 5, null);

	}

	@Test
	public void testAddTimedWorkload() {
		validateAddNameTimedWorkload("oneword", "8 to 5 pm 19 apr", "8pm 19 apr", "5 pm 19 apr", 5);
		validateAddNameTimedWorkload("two words!", "8am to 5pm 19 apr", "8am 19 apr", "5pm 19 apr", 4);
		validateAddNameTimedWorkload("do parser unit testing", "8am to 2pm", "8am", "2pm", 3);
		validateAddNameTimedWorkload("prepare for CS2101 oral discussion", "1700 to 1900 14 apr", "1700 14 apr", "1900 14 apr", 2);
	}

	@Test
	public void testModify() {
		validateParsedCommand("modify 1 new name!", MODIFY, "1", "new name!", null, null, null, null);
		validateParsedCommand("modify 2 new name!"	, MODIFY, "2", "new name!", null, null, null, null);

		// negative index is invalid and is taken as part of the name
		validateParsedCommand("modify -1 new name!"	, MODIFY, null, "-1 new name!", null, null, null, null);
	}

	@Test
	public void testDelete() {
		validateParsedCommand("delete 1", DELETE, "1", null, null, null, null, null);
		validateParsedCommand("delete 100 workload", DELETE, "100", "workload", null, null, null, null);

		// negative index is invalid and is taken as part of the parameter
		validateParsedCommand("delete -1 deadline", DELETE, null, "-1 deadline", null, null, null, null);
	}

	@Test
	public void testClear() {
		validateParsedCommand("clear", CLEAR, null, null, null, null, null, null);
	}

	@Test
	public void testDone() {
		validateParsedCommand("done", DONE, null, null, null, null, null, null);
	}

	@Test
	public void testUndo() {
		validateParsedCommand("undo", UNDO, null, null, null, null, null, null);
		validateParsedCommand("u", UNDO, null, null, null, null, null, null);
	}

	@Test
	public void testSearch() {
		// search for name
		validateParsedCommand("search looking for a name", SEARCH, null, "looking for a name", null, null, null, null);

		// search directly specified HOURS, DAY OF WEEK, DAY OF MONTH, MONTH or YEAR
		// excludes "search tomorrow"
		validateParsedCommand("search fri", SEARCH, null, null, "fri", "fri", null, null);
		validateParsedCommand("search friday", SEARCH, null, null, "fri", "fri", null, null);
		validateParsedCommand("search apr", SEARCH, null, null, "apr", "apr", null, null);
		validateParsedCommand("search 0930", SEARCH, null, null, "0930", "0930", null, null);
		validateParsedCommand("search year 2020", SEARCH, null, null, "1 Jan 2020", "1 Jan 2020", null, null);
		validateParsedCommand("search 0000 year", SEARCH, null, null, "1 Jan 0000", "1 Jan 0000", null, null);
		validateParsedCommand("search -1 year", SEARCH, null, "-1 year", null, null, null, null);
		validateParsedCommand("search year -1", SEARCH, null, "year -1", null, null, null, null);

		// search for workload
		validateParsedCommand("search w1", SEARCH, null, null, null, null, 1, null);
		validateParsedCommand("search w5", SEARCH, null, null, null, null, 5, null);
		validateParsedCommand("search w0", SEARCH, null, "w0", null, null, null, null);
		validateParsedCommand("search w6", SEARCH, null, "w6", null, null, null, null);
	}

	@Test
	public void testInterleavedParam() {
		// the DateTime and workload parameters can be blended into task names.
		// for DateTime phrases, the words cannot be spread out across the names. they must be present together as a subsetence
		validateParsedCommand("add do tomorrow CS3230 w3 tutorial", ADD, null, "do CS3230 tutorial", "tomorrow", "tomorrow", 3, null);
		validateParsedCommand("add attend w4 meeting tomorrow 4pm to 5pm at office", ADD, null, "attend meeting at office", "tomorrow 4pm", "tomorrow 5pm", 4, null);
		validateParsedCommand("add start 9am to 10pm next week revising for finals w5", ADD, null, "start revising for finals", "next week 9am", "next week 10pm", 5, null);
	}

	@Test
	public void testDoubleQuotesEscape() {
		// using double quotes as an escape for DateTime
		validateParsedCommand("add watch \"the day after tomorrow\" day after tomorrow", ADD, null, "watch the day after tomorrow", "day after tomorrow", "day after tomorrow", null, null);
		validateParsedCommand("add \"April Fools\" w2 12am 1 April", ADD, null, "April Fools", "12am 1 April", "12am 1 April", 2, null);
		validateParsedCommand("add w5 \"Chrismas\" Dinner  1800h to 1900h Christmas", ADD, null, "Chrismas Dinner", "1800h Christmas", "1900h Christmas", 5, null);

		// using two double quotes to record single double quotes
		validateParsedCommand("add Remember the date: \"\"13 April\"\" 13 April", ADD, null, "Remember the date: \"13 April\"", "13 April", "13 April", null, null);
		
		// using double quotes as an escape for workload
		validateParsedCommand("add \"w1\" w1", ADD, null, "w1", null, null, 1, null);
		validateParsedCommand("add \"w0\" w0", ADD, null, "w0 w0", null, null, null, null);
		
		// search
		validateParsedCommand("search looking for a \"day\"", SEARCH, null, "looking for a day", null, null, null, null);
		validateParsedCommand("search looking for \"\"a few quoted words\"\"", SEARCH, null, "looking for \"a few quoted words\"", null, null, null, null);
	}

	private void validateAddSingleDateTime(String dt) {
		validateParsedCommand(ADD + " " + TEST_NAME + " " + dt, ADD, null, TEST_NAME, dt, dt, null, null);
		validateParsedCommand(ADD  + " "+ dt + " " + TEST_NAME, ADD, null, TEST_NAME, dt, dt, null, null);
	}

	private void validateAddNameWorkLoad(String name, int workload) {
		assert(workload > 0 && workload < 6);
		String workloadString = "w"+workload;
		validateParsedCommand(ADD + " " + name + " " + workloadString, ADD, null, name, null, null, workload, null);
		validateParsedCommand(ADD + " " + workloadString + " " + name, ADD, null, name, null, null, workload, null);
	}

	private void validateAddNameSingleDateTimeWorkload(String name, String dateTime, int workload) {
		String workloadString = "w"+workload;
		validateParsedCommand(ADD + " " + name + " " + dateTime + " " + workloadString, ADD, null, name, dateTime, dateTime, workload, null);
		validateParsedCommand(ADD + " " + name + " " + workloadString + " " + dateTime, ADD, null, name, dateTime, dateTime, workload, null);
		validateParsedCommand(ADD + " " + workloadString + " " + name + " " + dateTime, ADD, null, name, dateTime, dateTime, workload, null);
		validateParsedCommand(ADD + " " + workloadString + " " + dateTime + " " + name, ADD, null, name, dateTime, dateTime, workload, null);
		validateParsedCommand(ADD + " " + dateTime + " " + name + " " + workloadString, ADD, null, name, dateTime, dateTime, workload, null);
		validateParsedCommand(ADD + " " + dateTime + " " + workloadString + " " + name, ADD, null, name, dateTime, dateTime, workload, null);
	}

	private void validateAddNameTimedWorkload(String name, String range, String startTime, String endTime, int workload) {
		String workloadString = "w"+workload;
		validateParsedCommand(ADD + " " + name + " " + range + " " + workloadString, ADD, null, name, startTime, endTime, workload, null);
		validateParsedCommand(ADD + " " + name + " " + workloadString + " " + range, ADD, null, name, startTime, endTime, workload, null);
		validateParsedCommand(ADD + " " + workloadString + " " + name + " " + range, ADD, null, name, startTime, endTime, workload, null);
		validateParsedCommand(ADD + " " + workloadString + " " + range + " " + name, ADD, null, name, startTime, endTime, workload, null);
		validateParsedCommand(ADD + " " + range + " " + name + " " + workloadString, ADD, null, name, startTime, endTime, workload, null);
		validateParsedCommand(ADD + " " + range + " " + workloadString + " " + name, ADD, null, name, startTime, endTime, workload, null);
	}
}
