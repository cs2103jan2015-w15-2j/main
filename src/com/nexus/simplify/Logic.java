package com.nexus.simplify;

import com.nexus.simplify.OperationType;
import com.nexus.simplify.database.Database;

import java.io.*;

public class Logic implements ILogic {
	
	private static GenericTaskList tempList;
	private static Database database;
	
	public Logic() {
		tempList = new GenericTaskList();
	}
	
	public CommandResult executeCommand(Command command) {
		OperationType operation = command.getOperation();
		String[] parameter = command.getParameter();
		return command.executeSpecificCommand(operation, parameter);
	}
	
	public static GenericTaskList getTempList() {
		return tempList;
	}
	
	public static Database getDatabase() {
		return database;
	}
	
	public CommandResult initialise(String fileName) {
		try {
			database = new Database(fileName);
			tempList = database.readFromFile();
			if(tempList == null){
				return null;
			} else {
				String feedback = null;
				CommandResult result = new CommandResult(tempList, feedback);
				return result;
			}
		} catch (IOException ioe){
			// return error message
			System.err.println("Error when accessing file");
			return null;
		}
	}
}
