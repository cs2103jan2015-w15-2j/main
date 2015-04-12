package com.nexus.simplify.logic;

import java.text.ParseException;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;

public class Search {
	
	public Search() {}
	
	public String execute(String[] parameter, boolean[] searchField) {
		Database database = MainApp.getDatabase();
		String feedback;
		try {
			database.searchDatabase(parameter, searchField);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		feedback = "Search result displayed.";
		return feedback;
	}
}
