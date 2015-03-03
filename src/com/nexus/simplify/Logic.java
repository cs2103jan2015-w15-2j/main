package com.nexus.simplify;

public class Logic implements ILogic {
	
	public String executeCommand(Command comd){
		String operation = comd.getOperation();
		String parameter = "";
		int numWordsParameter = comd.getParameter().length;
		for(int i=0;i<numWordsParameter;i++){
			parameter += comd.getParameter()[i];
		}
		return operation + parameter;
	}
	
	public TaskList initialise(){
		return Database.readFromFile();
	}
}
