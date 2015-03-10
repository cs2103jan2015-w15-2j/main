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
	
	public TaskList initialise(){
		Database database = new Database();
		return database.readFromFile();
	}
}
