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
