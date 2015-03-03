package com.nexus.simplify;

import java.util.Scanner;

public class UI implements IUI {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	Scanner scanner;
	
	private String userInput;
	
	private static final String PROMPT = "command: ";
	
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
		displayMessage(PROMPT);
	}
	
	@Override
	public void displayFeedback() {
		
	}
	
	public void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
}
