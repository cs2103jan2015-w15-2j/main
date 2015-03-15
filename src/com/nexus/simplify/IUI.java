package com.nexus.simplify;

public interface IUI {
	public void run();
	
	public String getUserInput();
	
	public void displayFeedback(CommandResult result);
	
	public void listenForCommandUntilExit();
}
