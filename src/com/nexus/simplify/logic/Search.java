package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;

public class Search {
	
	private final int CURRENT_TIME_POS = 0;
	private final int DAY_OF_MONTH_POS = 1;
	private final int MONTH_OF_YEAR = 2;
	private final int CURRENT_YEAR_POS = 3;
	
	public Search(){}
	
	String execute(String[] parameter, boolean[] searchField){
		Database database = MainApp.getDatabase();
		String feedback;
		if(searchField[CURRENT_TIME_POS]==false && searchField[DAY_OF_MONTH_POS]==false
			&& searchField[CURRENT_TIME_POS]==false && searchField[CURRENT_TIME_POS]==false){
			feedback = "parser failed to set truth value.";
			return feedback;
		} else {
			database.searchDatabase(parameter, searchField);
			feedback = "search result displayed.";
			return feedback;
		}
	}
}
