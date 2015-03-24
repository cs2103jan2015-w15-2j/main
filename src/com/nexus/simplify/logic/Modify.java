package com.nexus.simplify.logic;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.nexus.simplify.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This method determines what fields of a task to modify 
 * and calls APIs provided by database to modify storage.
 */
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
		// pattern follows Java.util.Date toString() method
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		
		try{
			int indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "please enter a task index to modify.";
			return feedback;
		}
		
		int indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String newName = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		
		int newWorkload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
		Database database = new Database();
		String feedback;
		
		int combinationNumber = decideCombination(newName,newStartTime,newEndTime,newWorkload);
		switch(combinationNumber){
			case INVALID_OPTION:
				feedback = "please specify a(some) field(s) to modify.";
				return feedback;
			case NAME_ONLY:
				database.modifyName(indexToModify,newName);
				feedback = "task name modified";
				return feedback;
			case DEADLINE_ONLY:
				database.modifyDeadline(indexToModify,newStartTime,newEndTime);
				feedback = "task deadline modified.";
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
	
	private int decideCombination(String name, String start, String end, String workload){
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
