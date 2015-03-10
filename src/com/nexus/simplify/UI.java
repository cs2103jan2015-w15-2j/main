/*
 * @author Toh Jian Feng
 * */

package com.nexus.simplify;

import java.util.Scanner;

public class UI implements IUI {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	Scanner scanner;
	
	private String userInput;
	
	private static final String MESSAGE_PROMPT = "command: ";
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!";
	private static final String USER_INPUT_EXIT = "exit";
	private static final String INPUT_FILE_NAME = "input.json";
	
	private static final int MAX_NUM_OF_TASKS_TO_DISPLAY = 5;
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
		Logic logic = new Logic();
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
	
	public void listenForCommandUntilExit() {
		Parser parser = new Parser();
		String userInput = getUserInput();
		while (!shouldExit(userInput)) {
			displayFeedback(parser.parseInput(userInput));
			userInput = getUserInput();
		}		
	}
	
	private boolean shouldExit(String userInput) {
		return userInput.equalsIgnoreCase(USER_INPUT_EXIT);
	}
	
	//-----------------//
	// Display Methods //
	//-----------------//
	
	public void promptUserForCommand() {
		displayMessage(MESSAGE_PROMPT);
	}
	
	public void displayWelcomeMessage() {
		displayMessage(MESSAGE_WELCOME);
	}
	
	@Override
	public void displayFeedback(CommandResult result) {
		displayMessage(buildShortTaskList(result.getModifiedTaskList()));
		if (result.getResultantFeedback() != null) {
			displayMessage(result.getResultantFeedback());
		}
	}
	
	public void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
	
	public void displayCurrentTaskList(TaskList taskList) {
		displayMessage(buildShortTaskList(taskList));
	}
	
	
	/*
	 * @return a displayable task list built from 
	 * @param taskList
	 * 
	 * */
	
	public String buildShortTaskList(TaskList taskList) {
		if (taskList == null) {
			return "";
		}
		StringBuilder shortTaskList = new StringBuilder();
		
		for (int i = 0; i < MAX_NUM_OF_TASKS_TO_DISPLAY; i++) {
			try {
				Task currentTask = taskList.get(i);
				
				int currentTaskIndex = i + LIST_NUMBER_OFFSET;
				shortTaskList.append(currentTaskIndex);
				shortTaskList.append(". ");
				shortTaskList.append(currentTask.getName());
				shortTaskList.append(" ");
				shortTaskList.append(currentTask.getDueDate());
				shortTaskList.append("\n");
			} catch (Exception e) {
				
			}
		}
		
		return shortTaskList.toString();
	}
}
