package com.nexus.simplify;

public class Main {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	private UI ui;
	
	private static final int SYSTEM_EXIT_CODE = 0;
	
	//---------------//
	// Main Function //
	//---------------//

	public static void main(String[] args) {
		int systemExitCode = new Main().launchUI();
		System.exit(systemExitCode);

	}
	
	private int launchUI() {
		ui = UI.getUiInstance();
		ui.listenForCommandUntilExit();
		return SYSTEM_EXIT_CODE;		
	}
}
