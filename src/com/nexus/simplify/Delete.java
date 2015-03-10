package com.nexus.simplify;
import com.nexus.simplify.ParameterType;

public class Delete {
	public Delete(){}
	
	public CommandResult execute(String[] parameter){
		TaskList tempList = Logic.getTempList();
		Database database = Logic.getDatabase();
		
		if(isNumeric(parameter[0])){
			int index = Integer.parseInt(parameter[ParameterType.CURRENT_NAME_POS]);
			tempList.delete(index);
			database.writeToFile(tempList);
			String feedback = "successfully deleted entry #" + parameter[ParameterType.CURRENT_NAME_POS];
			CommandResult result = new CommandResult(tempList, feedback);
			return result;
		}
		else{
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
