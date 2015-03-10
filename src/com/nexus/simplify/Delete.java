package com.nexus.simplify;

public class Delete {
	public Delete(){}
	
	public CommandResult execute(String[] parameter){
		TaskList tempList = Logic.getTempList();
		Database database = Logic.getDatabase();
		
		if(isNumeric(parameter[0])){
			int index = Integer.parseInt(parameter[0]);
			tempList.delete(index);
			database.writeToFile(tempList);
			String feedback = "successfully deleted entry #" + parameter[0];
			CommandResult result = new CommandResult(tempList, feedback);
			return result;
		}
		else{
			String name = parameter[0];
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
