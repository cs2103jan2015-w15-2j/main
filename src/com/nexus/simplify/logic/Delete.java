package com.nexus.simplify.logic;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Delete {
	
	public Delete() {}
	
	String execute(String[] parameter){
		try{
			int indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "please enter a task index to delete.";
			return feedback;
		}
		int indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		Database database = MainApp.getDatabase();

		database.deleteTaskByIndex(indexToDelete);
		String feedback = "successfully deleted entry #" + parameter[ParameterType.INDEX_POS] + ".";

		return feedback; 
	}
}
