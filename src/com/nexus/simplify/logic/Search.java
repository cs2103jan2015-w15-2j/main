package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;

public class Search {
	
	private final int CURRENT_TIME_POS = 0;
	private final int DAY_OF_WEEK_POS = 1;
	private final int DAY_OF_MONTH_POS = 2;
	private final int MONTH_OF_YEAR_POS = 3;
	private final int CURRENT_YEAR_POS = 4;
	
	public Search(){}
	
	String execute(String[] parameter, boolean[] searchField){
		Database database = MainApp.getDatabase();
		String feedback;
		if(searchField[CURRENT_TIME_POS]==false && searchField[DAY_OF_WEEK_POS]==false&& 
		searchField[DAY_OF_MONTH_POS]==false && searchField[MONTH_OF_YEAR_POS]==false && 
		searchField[CURRENT_YEAR_POS]==false){
			feedback = "Parser failed to set truth value.";
			return feedback;
		} else {
			database.searchDatabase(parameter, searchField);
			feedback = "Search result displayed.";
			return feedback;
		}
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(boolean[] searchField){
		String feedback;
		
		if(searchField[CURRENT_TIME_POS]==false && searchField[DAY_OF_WEEK_POS]==false&& 
		searchField[DAY_OF_MONTH_POS]==false && searchField[MONTH_OF_YEAR_POS]==false && 
		searchField[CURRENT_YEAR_POS]==false){
			feedback = "Parser failed to set truth value.";
			return feedback;	// This feedback will not be displayed to the user.
		} else {
			feedback = "Search result displayed.";
			return feedback;
		}
	}
}
