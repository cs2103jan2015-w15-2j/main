package com.nexus.simplify.logic;
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
		Database database = new Database();
<<<<<<< HEAD
		database.deleteTaskByIndex(indexToDelete);
=======
		database.delete(indexToDelete);
		String feedback = "successfully deleted entry #" + parameter[ParameterType.INDEX_POS] + ".";
>>>>>>> 01e5f22a18081f28e0d4fe53426cc3f587ddb958
		return feedback; 
	}
}
