package com.nexus.simplify.logic;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Delete {
	
	public Delete() {}
	
	String execute(String[] parameter){
		int indexToDelete = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "successfully deleted entry #" + parameter[ParameterType.INDEX_POS] + ".";
		Database database = new Database();
		database.delete(indexToDelete);
		return feedback; 
	}
}
