/*
 * @author Toh Jian Feng
 * */

package com.nexus.simplify;

import java.util.Scanner;

public class UI implements IUI {
	
	private static final String FORMATTING_WHITESPACE = " 	";
	private static final String FORMATTING_NEWLINE = "\n";
	private static final String FORMATTING_HEADERS = "No. 	Task					Due\n";
	private static final String FORMATTING_HEADER_BORDER = "===================================================================\n";
	private static final String FORMATTING_TABLE_BORDER = "-------------------------------------------------------------------\n";

	//-----------------//
	// Class Variables //
	//-----------------//
	
	Scanner scanner;
	
	Logic logic;
	Parser parser;
	
	private String userInput;
	
	private static final String MESSAGE_PROMPT = "command: ";
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!\n";
	private static final String USER_INPUT_EXIT = "exit";
	private static final String INPUT_FILE_NAME = "input.json";

	private static final int LIST_NUMBER_OFFSET = 1;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public UI() {
		scanner = new Scanner(System.in);
		run();
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
		displayCurrentTaskList(result.getModifiedTaskList());
		if (result.getResultantFeedback() != null) {
			displayMessage(result.getResultantFeedback());
		}
	}
	
	private void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
	
	private void displayCurrentTaskList(TaskList taskList) {
		displayMessage(buildShortTaskList(taskList));
	}
	
	//----------------------//
	// Task List Formatting //
	//----------------------//
	
	private String padSpaces(String strToBePadded) {
		StringBuilder resultString = new StringBuilder();
		resultString.append(strToBePadded);
		for (int i = 0; i < 40 - strToBePadded.length(); i++) {
			resultString.append(" ");
	    }
		return resultString.toString();
	}
	
	/*
	 * @return a displayable task list built from 
	 * @param taskList
	 * 
	 * */
	
	private String buildShortTaskList(TaskList taskList) {
		if (taskList == null) {
			return "";
		}
		StringBuilder shortTaskList = new StringBuilder();
		
		shortTaskList.append(FORMATTING_NEWLINE);
		shortTaskList.append(FORMATTING_HEADER_BORDER);
		shortTaskList.append(FORMATTING_HEADERS);
		shortTaskList.append(FORMATTING_HEADER_BORDER);
		
		for (int i = 0; i < taskList.size(); i++) {
			try {
				Task currentTask = taskList.get(i);
				
				int currentTaskIndex = i + LIST_NUMBER_OFFSET;
				shortTaskList.append(currentTaskIndex);
				shortTaskList.append(FORMATTING_WHITESPACE);
				shortTaskList.append(padSpaces(currentTask.getName()));
				if (!currentTask.isFloatingTask()) {
					shortTaskList.append(currentTask.getDueDate());
				}
				shortTaskList.append(FORMATTING_NEWLINE);
			} catch (Exception e) {
				
			}
			shortTaskList.append(FORMATTING_TABLE_BORDER);
		}
		

		
		return shortTaskList.toString();
	}
}
