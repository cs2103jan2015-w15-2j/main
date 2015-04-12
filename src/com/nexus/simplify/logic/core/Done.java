//@author generated
package com.nexus.simplify.logic.core;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class determines that user wants to mark
 * a task as done and calls database to modify the storage.
 */
public class Done {
	private final String NO_INDEX = "Please enter a task index to mark as done.";
	public Done() {}
	
	public String execute(String[] parameter) throws Exception {
		int indexToMarkDone;
		
		try {
			indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			throw new Exception(NO_INDEX);
		}
		
		indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		Database database = MainApp.getDatabase();

		database.markTaskDone(indexToMarkDone);
		String feedback = "Successfully marked entry #" + parameter[ParameterType.INDEX_POS] + " as done.";
		return feedback; 
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		@SuppressWarnings("unused")
		int indexToMarkDone;
		
		try {
			indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			String feedback = NO_INDEX;
			return feedback;
		}
		
		indexToMarkDone = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "Successfully marked entry #" + parameter[ParameterType.INDEX_POS] + " as done.";
		return feedback; 
	}
}
