package com.nexus.simplify;
import com.nexus.simplify.ParameterType;

public class Modify {
	public Modify() {
		
	}
	
	public CommandResult execute(String[] parameter){
		TaskList tempList = Logic.getTempList();
		Database database = Logic.getDatabase();
		String nameOfItemToChange = parameter[ParameterType.CURRENT_NAME_POS];
		int indexInTaskList = 0;
		
		for(int i=0; i<tempList.size(); i++){
			if(nameOfItemToChange.equals(tempList.get(i).getName())) { 
				indexInTaskList = i;
				break; 
			}
		}
		
		Task newTask = new Task(parameter[ParameterType.NEW_NAME_POS], 
				parameter[ParameterType.NEW_DEADLINE_POS], parameter[ParameterType.NEW_WORKLOAD_POS]);
		
		tempList.set(indexInTaskList, newTask);
		database.writeToFile(tempList);
		
		String feedback = "successfully modified " + nameOfItemToChange;
		CommandResult result = new CommandResult(tempList, feedback);
		
		return result;
	}
}
