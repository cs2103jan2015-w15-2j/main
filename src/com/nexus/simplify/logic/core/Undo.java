package com.nexus.simplify.logic;

import com.nexus.simplify.database.DatabaseConnector;

public class Undo {
	public Undo(){}
	
	public String execute(){
		DatabaseConnector database = new DatabaseConnector();
		database.undoTask();
		String feedback = "Undo operation is successful.";
		return feedback;
	}
}
