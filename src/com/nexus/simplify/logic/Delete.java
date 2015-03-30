package com.nexus.simplify.logic;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This class calls database to delete a task in database
 * by passing a task index.
 * @author David Zhao Han
 */
public class Delete {
	
	public Delete() {}
	
	String execute(String[] parameter){
		int indexToDelete;
		try{
			indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "please enter a task index to delete.";
			return feedback;
		}
		indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		Database database = MainApp.getDatabase();

		database.deleteTaskByIndex(indexToDelete);
		String feedback = "successfully deleted entry #" + parameter[ParameterType.INDEX_POS] + ".";

		return feedback; 
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter){}
}
