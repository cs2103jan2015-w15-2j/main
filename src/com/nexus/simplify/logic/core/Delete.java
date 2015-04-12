//@author generated
package com.nexus.simplify.logic.core;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class calls database to delete a task in database
 * by passing a task index.
 */
public class Delete {
	private final String NO_INDEX = "Please enter a task index to delete.";
	public Delete() {}
	
	public String execute(String[] parameter) throws Exception {
		int indexToDelete;
		
		try {
			indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			throw new Exception(NO_INDEX);
		}
		indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		
		Database database = MainApp.getDatabase();
		database.deleteTaskByIndex(indexToDelete);

		String feedback = "Successfully deleted entry #" + parameter[ParameterType.INDEX_POS] + ".";
		return feedback; 
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		int indexToDelete;
		
		try {
			indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			String feedback = NO_INDEX;
			return feedback;
		}
		indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		
		String feedback = "Successfully deleted entry #" + String.valueOf(indexToDelete) + ".";
		return feedback;
	}
}
