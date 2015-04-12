package com.nexus.simplify.logic;

import java.text.ParseException;

import com.nexus.simplify.database.DatabaseConnector;

public class Search {
	
	public Search() {}
	
	public String execute(String[] parameter, boolean[] searchField) throws ParseException {
		DatabaseConnector database = new DatabaseConnector();
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
