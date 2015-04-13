//@author generated
package com.nexus.simplify.logic.core;

import java.text.ParseException;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;

//@author A0094457U
/*
 * This class calls database to search for something.
 */
public class Search {
	private final String SUCCESS = "Search result displayed.";
	public Search() {}
	
	public String execute(String[] parameter, boolean[] searchField) throws ParseException {
		Database database = MainApp.getDatabase();
		String feedback;
		try {
			database.searchDatabase(parameter, searchField);
		} catch (ParseException e) {
			throw e;
		}
		feedback = SUCCESS;
		return feedback;
	}
}
