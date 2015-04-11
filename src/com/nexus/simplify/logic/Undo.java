package com.nexus.simplify.logic;

import com.nexus.simplify.database.DatabaseConnector;

public class Undo {
	public Undo(){}
	
	String execute(){
		DatabaseConnector databaseConnector = new DatabaseConnector();
		databaseConnector.undoTask();
		String feedback = "Undo operation is successful.";
		return feedback;
	}
}
