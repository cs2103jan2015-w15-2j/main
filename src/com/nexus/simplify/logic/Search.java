package com.nexus.simplify.logic;

import java.text.ParseException;

import com.nexus.simplify.database.DatabaseConnector;

public class Search {
	
	public Search() {}
	
	String execute(String[] parameter, boolean[] searchField) {
		DatabaseConnector databaseConnector = new DatabaseConnector();
		String feedback;
		try {
			databaseConnector.searchDatabase(parameter, searchField);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		feedback = "Search result displayed.";
		return feedback;
	}
}
