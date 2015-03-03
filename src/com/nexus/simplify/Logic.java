package com.nexus.simplify;

import com.nexus.simplify.Parser.OperationType;

public class Logic implements ILogic {
	
	public String executeCommand(Command comd){
		OperationType operation = comd.getOperation();
		String parameter = "";
		int numWordsParameter = comd.getParameter().length;
		for(int i=0;i<numWordsParameter;i++){
			parameter += comd.getParameter()[i];
		}
		return operation + " " + parameter;
	}
	
	public TaskList initialise(){
		// Database not yet implemented
		return null;
		//		return Database.readFromFile();
	}
}
