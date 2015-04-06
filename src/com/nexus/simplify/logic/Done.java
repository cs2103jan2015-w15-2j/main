package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This class determines that user wants to mark
 * a task as done and calls database to modify the storage.
 * @author David Zhao Han
 */
public class Done {
	
	public Done(){}
	
	public String execute(String[] parameter){
		int indexToMarkDone;
		try{
			indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "Please enter a task index to mark as done.";
			return feedback;
		}
		indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		Database database = MainApp.getDatabase();

		database.markTaskDone(indexToMarkDone);
		String feedback = "Successfully marked entry #" + parameter[ParameterType.INDEX_POS] + " as done.";

		return feedback; 
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter){
		int indexToMarkDone;
		try{
			indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "Please enter a task index to mark as done.";
			return feedback;
		}
		indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "Successfully marked entry #" + parameter[ParameterType.INDEX_POS] + " as done.";

		return feedback; 
	}
}
