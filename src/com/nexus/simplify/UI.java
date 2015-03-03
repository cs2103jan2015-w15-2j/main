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
	
	
	
	@Override
	public void displayFeedback() {
		
	}
}
