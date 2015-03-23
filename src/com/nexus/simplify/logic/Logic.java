package com.nexus.simplify.logic;
import com.nexus.simplify.Database;
import com.nexus.simplify.usercommand.OperationType;

public class Logic implements ILogic {
	
	private static Logic theOne;
	private static TaskList tempList;
	private static Database database;
	private Logic() {
		tempList = new TaskList();
	}
	
	public static Logic getInstance(){
		if(theOne == null){
			theOne = new Logic();
		}
		return theOne;
	}
	
	public CommandResult executeCommand(Command command) {
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
	
	public CommandResult initialise(String fileName) {
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
