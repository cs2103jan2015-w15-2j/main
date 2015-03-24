package com.nexus.simplify.logic;
import com.nexus.simplify.Database;
import com.nexus.simplify.usercommand.ParameterType;

public class Delete {
	
	public Delete() {}
	
	String execute(String[] parameter){
		int indexToDelete = Integer.parseInt(parameter[0]);
		String feedback = "successfully deleted entry #" + parameter[0] + ".";
		Database database = new Database();
		database.deleteTaskByIndex(indexToDelete);
		return feedback; 
	}
}
