package com.nexus.simplify;

public class Main {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	private UI ui;
	
	private static final String USER_INPUT_EXIT = "exit";
	
	private static final int SYSTEM_EXIT_CODE = 0;
	
	//---------------//
	// Main Function //
	//---------------//

	public static void main(String[] args) {
		int systemExitCode = new Main().launchUI();
		System.exit(systemExitCode);

	}
	
	
	private int launchUI() {
		ui = new UI();
		listenForCommandUntilExit(ui);
		return SYSTEM_EXIT_CODE;
		
	}
	
	//--------------//
	// Program Loop //
	//--------------//
	
	private void listenForCommandUntilExit(UI ui) {
		String userInput = ui.getUserInput();
		while (!shouldExit(userInput)) {
			
		}
		
	}
	
	private boolean shouldExit(String userInput) {
		return userInput.equalsIgnoreCase(USER_INPUT_EXIT);
	}

}
