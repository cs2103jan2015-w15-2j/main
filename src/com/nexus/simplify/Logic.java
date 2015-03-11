package com.nexus.simplify;

import com.nexus.simplify.OperationType;

public class Logic implements ILogic {
	
	private static TaskList tempList;
	private static Database database;
	
	public Logic(){
		tempList = new TaskList();
	}
	
	public CommandResult executeCommand(Command command){
		OperationType operation = command.getOperation();
		String[] parameter = command.getParameter();
		return command.executeSpecificCommand(operation, parameter);
	}
	
	public static TaskList getTempList() {
		return tempList;
	}
	
	public static Database getDatabase() {
		return database;
	}
	
	public CommandResult initialise(String fileName){
		database = new Database(fileName);
		tempList = database.readFromFile();
		if(tempList == null){
			return null;
		} else {
			String feedback = null;
			CommandResult result = new CommandResult(tempList, feedback);
			return result;
		}
	}
}
