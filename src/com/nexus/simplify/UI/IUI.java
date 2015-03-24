package com.nexus.simplify.UI;

import com.nexus.simplify.CommandResult;

import com.nexus.simplify.logic.CommandResult;

public interface IUI {
	public void run();
	
	public String getUserInput();
	
	public void displayFeedback(CommandResult result);
	
	public void listenForCommandUntilExit();
}
