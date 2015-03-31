package com.nexus.simplify.logic;

public class Clear {
	public Clear(){}
	
	String execute(){
		Database database = MainApp.getDatabase();
		database.clearContent();
		String feedback = "successfully cleared storage.";
		return feedback;
	}
}
