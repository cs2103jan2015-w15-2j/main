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
	public void displayFeedback(String resultantFeedback) {
		System.out.println(resultantFeedback);
	}
	
	public void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
}
