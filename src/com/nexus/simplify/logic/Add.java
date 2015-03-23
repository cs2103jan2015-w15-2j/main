package com.nexus.simplify.logic;

import com.nexus.simplify.usercommand.ParameterType;

public class Add {
	private Task taskToAdd;
	
	public Add(){}
	
	public CommandResult execute(String[] parameter){
		String name = parameter[ParameterType.CURRENT_NAME_POS];
		String deadline = parameter[ParameterType.NEW_DEADLINE_POS];
		String workload = parameter[ParameterType.NEW_WORKLOAD_POS];
		
		String feedback;
		if(deadline == null && workload == null){
			taskToAdd = new Task(name);
			feedback = "successfully added " + name + "."; 
		}
		else if(deadline == null || workload == null){
			if(deadline == null){
			taskToAdd = new Task(name,deadline,workload);
			feedback = "successfully added " + name + " " + deadline + " " + workload + ".";
			}
			else{
				taskToAdd
			}
		}
		TaskList tempList = Logic.getTempList();
		tempList.add(taskToAdd);
		Logic.getDatabase().writeToFile(tempList);
		
		CommandResult result = new CommandResult(tempList, feedback);
		return result;
	}
}
