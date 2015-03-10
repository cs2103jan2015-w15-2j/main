package com.nexus.simplify;

import com.nexus.simplify.Parser.OperationType;

public class Logic implements ILogic {
	
	private TaskList tempList;
	
	public Logic(){
		tempList = new TaskList();
	}
	
	public Result executeCommand(Command command){
		OperationType operation = command.getOperation();
		String[] parameter = command.getParameter();
		return command.executeSpecificCommand(operation, parameter);
	}
	
	public TaskList getTempList(){
		return tempList;
	}
	
	public TaskList initialise(String fileName){
		Database database = new Database(fileName);
		return database.readFromFile();
	}
}
