package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;

public class Undo {
	public Undo(){}
	
	String execute(){
		Database database = MainApp.getDatabase();
		database.undoTask();
		String feedback = "undo operation successful.";
		return feedback;
	}
}
