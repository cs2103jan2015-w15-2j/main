package com.nexus.simplify;

public class Add {
	private static final int NAME_POS = 0;
	private static final int DEADLINE_POS = 1;
	private static final int WORKLOAD_POS = 2;
	private Task taskToAdd;
	
	public Add(){}
	
	public CommandResult execute(String[] parameter){
		String name = parameter[NAME_POS];
		DueDate deadline = parameter[DEADLINE_POS];
		String workload = parameter[WORKLOAD_POS];
		String feedback;
		if(deadline == null && workload == null){
			taskToAdd = new Task(name);
			feedback = "successfully added " + name + "."; 
		}
		else{
			taskToAdd = new Task(name,deadline,workload)
		}
		TaskList tempList = Logic.getTempList.add(taskToAdd);
		Logic.getDataBase.writeToFile(tempList);
		String feedback = "successfully added " + name + " " + deadline + " " + workload + " "
		CommandResult result = new CommandResult(tempList, )
		return ;
		
	}
}
