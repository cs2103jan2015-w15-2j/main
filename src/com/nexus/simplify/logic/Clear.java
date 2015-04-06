package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;

public class Clear {
	public Clear(){}
	
	String execute(){
		Database database = MainApp.getDatabase();
		database.clearContent();
		String feedback = "All tasks cleared successfully.";
		return feedback;
	}
}
