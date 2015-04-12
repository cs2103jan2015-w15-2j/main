package com.nexus.simplify.logic.core;

import java.text.ParseException;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;

public class Search {
	
	public Search() {}
	
	public String execute(String[] parameter, boolean[] searchField) throws ParseException {
		Database database = MainApp.getDatabase();
		String feedback;
		try {
			database.searchDatabase(parameter, searchField);
		} catch (ParseException e) {
			throw e;
		}
		feedback = "Search result displayed.";
		return feedback;
	}
}
