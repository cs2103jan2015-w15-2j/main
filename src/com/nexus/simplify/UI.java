package com.nexus.simplify;

import java.util.Scanner;

public class UI implements IUI {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	Scanner scanner;
	
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
	
	//-----------------//
	// Display Methods //
	//-----------------//
	
	public void promptForCommand() {
		displayMessage(PROMPT);
	}
	
	@Override
	public void displayFeedback() {
		
	}
	
	public void displayMessage(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
}
