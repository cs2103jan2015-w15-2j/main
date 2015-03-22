package com.nexus.simplify;

import com.nexus.simplify.logic.CommandResult;

public interface IUI {
	public void run();
	
	public String getUserInput();
	
	public void displayFeedback(CommandResult result);
	
	public void listenForCommandUntilExit();
}
