package com.nexus.simplify;
import com.nexus.simplify.ParameterType;

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
		else{
			taskToAdd = new Task(name,deadline,workload);
			feedback = "successfully added " + name + " " + deadline + " " + workload + ".";
		}
		TaskList tempList = Logic.getTempList();
		tempList.add(taskToAdd);
		Logic.getDatabase().writeToFile(tempList);
		
		CommandResult result = new CommandResult(tempList, feedback);
		return result;
	}
}
