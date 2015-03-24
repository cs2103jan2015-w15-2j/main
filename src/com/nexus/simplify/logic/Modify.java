package com.nexus.simplify.logic;
import com.nexus.simplify.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Modify {
	private static final int INVALID_OPTION = 0;
	private static final int NAME_ONLY = 1;
	private static final int DEADLINE_ONLY = 2;
	private static final int NAME_AND_DEADLINE = 3;
	private static final int WORKLOAD_ONLY = 4;
	private static final int NAME_AND_WORKLOAD = 5;
	private static final int DEADLINE_AND_WORKLOAD = 6;
	private static final int ALL_FIELDS = 7;
	 
	public Modify() {}
	
	public String execute(String[] parameter){
		String indexToModify = parameter[ParameterType.INDEX_POS];
		String newName = parameter[ParameterType.NEW_NAME_POS];
		String newDeadline = parameter[ParameterType.NEW_DEADLINE_POS];
		String newWorkload = parameter[ParameterType.NEW_WORKLOAD_POS];
		Database database = new Database();
		String feedback;
		
		int combinationNumber = decideCombination(newName,newDeadline,newWorkload);
		switch(combinationNumber){
			case INVALID_OPTION:
				feedback = "please specify something to modify.";
				return feedback;
			case NAME_ONLY:
				database.modifyNameOnly(indexToModify,newName);
				feedback = "task name modified to " + newName + ".";
				return feedback;
			case DEADLINE_ONLY:
				database.modifyDeadlineOnly(indexToModify,newDeadline);
				feedback = "task deadline modified to " + newDeadline + ".";
				return feedback;
			case NAME_AND_DEADLINE:
				database.modifyNameAndDeadline(indexToModify,newName,newDeadline);
				feedback = "task name and deadline modified.";
				return feedback;
			case WORKLOAD_ONLY:
				database.modifyWorkloadOnly(indexToModify,newWorkload);
				feedback = "task workload modified to " + newWorkload + ".";
				return feedback;
			case NAME_AND_WORKLOAD:
				database.modifyNameAndWorkload(indexToModify,newName,newWorkload);
				feedback = "task name and workload modified.";
				return feedback;
			case DEADLINE_AND_WORKLOAD:
				database.modifyDeadlineAndWorkload(indexToModify,newDeadline,newWorkload);
				feedback = "task deadline and workload modified.";
				return feedback;
			case ALL_FIELDS:
				database.modifyAllFields(indexToModify,newName,newDeadline,newWorkload);
				feedback = "task modified.";
				return feedback;
			default:
				return "unexpected error.";
		}
	}
	
	private int decideCombination(String name, String deadline, String workload){
		if(name == null && deadline == null && workload == null){
			return INVALID_OPTION;
		}
		else if(name != null && deadline == null && workload == null){
			return NAME_ONLY;
		}
		else if(name == null && deadline != null && workload == null){
			return DEADLINE_ONLY;
		}
		else if(name != null && deadline != null && workload == null){
			return NAME_AND_DEADLINE;
		}
		else if(name == null && deadline == null && workload != null){
			return WORKLOAD_ONLY;
		}
		else if(name != null && deadline == null && workload != null){
			return NAME_AND_WORKLOAD;
		}
		else if(name == null && deadline != null && workload != null){
			return DEADLINE_AND_WORKLOAD;
		}
		else{
			return ALL_FIELDS;
		}
	}
}
