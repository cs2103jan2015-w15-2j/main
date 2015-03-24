/*
 * @author Toh Jian Feng
 * */

package com.nexus.simplify.UI;

import java.util.Scanner;

<<<<<<< HEAD:src/com/nexus/simplify/UI/UI.java
<<<<<<< HEAD:src/com/nexus/simplify/UI/UI.java
import com.nexus.simplify.CommandResult;
import com.nexus.simplify.Logic;
import com.nexus.simplify.Parser;
import com.nexus.simplify.Task;
import com.nexus.simplify.TaskList;
=======
<<<<<<< HEAD
import com.nexus.simplify.parser.api.Parser;
>>>>>>> 01e5f22a18081f28e0d4fe53426cc3f587ddb958:src/com/nexus/simplify/UI.java
=======
import com.nexus.simplify.logic.CommandResult;
import com.nexus.simplify.logic.Logic;
import com.nexus.simplify.logic.Task;
import com.nexus.simplify.logic.TaskList;
<<<<<<< HEAD:src/com/nexus/simplify/UI/UI.java
>>>>>>> 6f2e3832147ef855d6261e6402dc33b96517a985:src/com/nexus/simplify/UI.java
=======
>>>>>>> a48afdbc8be48586e72b0c7fb156529aa761713f
>>>>>>> 01e5f22a18081f28e0d4fe53426cc3f587ddb958:src/com/nexus/simplify/UI.java

public class UI implements IUI {
	

	private static final String FORMATTING_SINGLE_WHITESPACE = " ";
	private static final String FORMATTING_THREE_WHITESPACES = " 	";
	private static final String FORMATTING_NEWLINE = "\n";
	private static final String FORMATTING_HEADERS = "No.  	Task					Due                 WorkLoad    \n";
	private static final String FORMATTING_HEADER_BORDER = "==============================================================================\n";
	private static final String FORMATTING_TABLE_DIVIDER = "------------------------------------------------------------------------------\n";
	private static final String FORMATTING_DUE_DATE_BUFFER = "                 ";
	
	private static final String MESSAGE_PROMPT = "command: ";
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!\n";
	private static final String USER_INPUT_EXIT = "exit";
	private static final String INPUT_FILE_NAME = "input.json";
	
	private static final int LIST_NUMBER_OFFSET = 1;
	private static final int HEADER_TASKNAME_TO_DUEDATE_OFFSET = 40;

	//-----------------//
	// Class Variables //
	//-----------------//
	
	Scanner scanner;
	
	private static UI uiInstance;
	
	Logic logic;
	Parser parser;
	
	private String userInput;
	
	//-------------//
	// Constructor //
	//-------------//
	
	private UI() {
		scanner = new Scanner(System.in);
		run();
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public static UI getUiInstance() {
		if (uiInstance == null) {
			uiInstance = new UI();
		}
		return uiInstance;
	}
	
	//------------------------//
	// Program Initialization //
	//------------------------//
	
	@Override
	public void run() {
		displayWelcomeMessage();
		logic = new Logic();
		CommandResult initialFeedback = logic.initialise(INPUT_FILE_NAME);
		if (initialFeedback != null) {
			displayFeedback(initialFeedback);
		}
	}
	
	//-------------------------//
	// Accepting User Commands //
	//-------------------------//
	
	@Override
	public String getUserInput() {
		promptUserForCommand();
		userInput = scanner.nextLine();
		return userInput;
	}
	
	//--------------//
	// Program Loop //
	//--------------//
	
	@Override
	public void listenForCommandUntilExit() {
		parser = new Parser();
		String userInput = getUserInput();
		while (!shouldExit(userInput)) {
			displayFeedback(parser.parseInput(userInput, logic));
			displayMessage(FORMATTING_NEWLINE);
			userInput = getUserInput();
		}		
	}
	
	private boolean shouldExit(String userInput) {
		return userInput.equalsIgnoreCase(USER_INPUT_EXIT);
	}
	
	//-----------------//
	// Display Methods //
	//-----------------//
	
	private void promptUserForCommand() {
		System.out.print(MESSAGE_PROMPT);
	}
	
	private void displayWelcomeMessage() {
		displayMessage(MESSAGE_WELCOME);
	}
	
	@Override
	public void displayFeedback(CommandResult result) {
		GenericTaskList modifiedTaskList = result.getModifiedTaskList();
		displayCurrentTaskList(modifiedTaskList);
		
		String resultantFeedback = result.getResultantFeedback();
		if (resultantFeedback != null) {
			displayMessage(resultantFeedback);
		}
	}
	
	private void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
	
	private void displayCurrentTaskList(GenericTaskList taskList) {
		displayMessage(buildShortTaskList(taskList));
	}
	
	//----------------------//
	// Task List Formatting //
	//----------------------//
	
	private String padSpaces(String strToBePadded) {
		StringBuilder resultString = new StringBuilder();
		resultString.append(strToBePadded);
		assert HEADER_TASKNAME_TO_DUEDATE_OFFSET - strToBePadded.length() >= 0;
		for (int i = 0; i < HEADER_TASKNAME_TO_DUEDATE_OFFSET - strToBePadded.length(); i++) {
			resultString.append(FORMATTING_SINGLE_WHITESPACE);
	    }
		return resultString.toString();
	}
	
	private void buildDisplayTLHeader(StringBuilder taskList) {
		taskList.append(FORMATTING_NEWLINE);
		taskList.append(FORMATTING_HEADER_BORDER);
		taskList.append(FORMATTING_HEADERS);
		taskList.append(FORMATTING_HEADER_BORDER);
	}
	
	private void addTaskToDisplayTL(StringBuilder taskList, int currentTaskIndex, GenericTask currentTask) {
		taskList.append(currentTaskIndex);
		taskList.append(FORMATTING_THREE_WHITESPACES);
		
		String taskName = currentTask.getName();
		assert taskName instanceof String;
		
		String taskDueDate = currentTask.getDueDate();
		assert taskDueDate instanceof String;
		
		Integer taskWorkload = currentTask.getWorkload();
		assert taskWorkload instanceof Integer;
		
		taskList.append(padSpaces(taskName));
		
		if (!currentTask.isFloatingTask()) {
			taskList.append(taskDueDate);
		} else {
			taskList.append(FORMATTING_DUE_DATE_BUFFER);
		}
		
		taskList.append(FORMATTING_THREE_WHITESPACES);
		taskList.append(taskWorkload.intValue());
		taskList.append(FORMATTING_NEWLINE);
	}
	
	/*
	 * @return a pretty-formatted task list built from 
	 * @param taskList
	 * 
	 * */
	private String buildShortTaskList(GenericTaskList taskList) {
		if (taskList == null) {
			return "";
		} else {
			StringBuilder shortTaskList = new StringBuilder();
			
			buildDisplayTLHeader(shortTaskList);
			
			for (int i = 0; i < taskList.size(); i++) {
				try {
					GenericTask currentTask = taskList.get(i);
					assert currentTask instanceof GenericTask;
					int currentTaskIndex = i + LIST_NUMBER_OFFSET;
					
					addTaskToDisplayTL(shortTaskList, currentTaskIndex, currentTask);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("Index out of bounds: " + e.getMessage());
				}
				shortTaskList.append(FORMATTING_TABLE_DIVIDER);
			}
		
			return shortTaskList.toString();
		}

	}
}
