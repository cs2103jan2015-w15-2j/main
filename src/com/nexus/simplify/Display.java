package com.nexus.simplify;

public class Display {
	public Display(){}
	
	public CommandResult execute(){
		TaskList updatedList = Logic.getDatabase().readFromFile();
		String feedback = "successfully displayed.";
		CommandResult result = new CommandResult(updatedList, feedback);
		return result;
	}
}