package com.nexus.simplify;
import com.nexus.simplify.ParameterType;
import com.nexus.simplify.database.Database;

public class Delete {
	
	private static final int TASK_INDEX_OFFSET = 1;
	
	public Delete() {
		
	}
	
	public CommandResult execute(String[] parameter){
		GenericTaskList tempList = Logic.getTempList();
		Database database = Logic.getDatabase();
		
		if(isNumeric(parameter[0])) {
			int index = Integer.parseInt(parameter[ParameterType.CURRENT_NAME_POS]) - TASK_INDEX_OFFSET;
			tempList.delete(index);
			database.writeToFile(tempList);
			String feedback = "successfully deleted entry #" + parameter[ParameterType.CURRENT_NAME_POS];
			CommandResult result = new CommandResult(tempList, feedback);
			return result;
		} else {
			String name = parameter[ParameterType.CURRENT_NAME_POS];
			tempList.delete(name);
			database.writeToFile(tempList);
			String feedback = "successfully deleted " + name;
			CommandResult result = new CommandResult(tempList, feedback);
			return result;
		}
	}
	
	private static boolean isNumeric(String str){
	    for (char c : str.toCharArray()) {
	        if (!Character.isDigit(c)) {
	        	return false;
	        }
	    }
	    return true;
	}
}
