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
		
		Database database = new Database();
		int indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "task ";
		String newName = parameter[ParameterType.NEW_NAME_POS];
		if(newName != null){
			database.modifyName(newName);
			feedback += "name ";
		}
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		if(newStartTime != null){
			Date startTime = df.parse(newStartTime);
			database.modifyStartTime(startTime);
			feedback += "and start time ";
		}
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		if(newEndTime != null){
			Date endTime = df.parse(newEndTime);
			database.modifyStartTime(endTime);
			feedback += "and end time ";
		}
		if(parameter[ParameterType.NEW_WORKLOAD_POS] != null){
			try{
				int newWorkload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
			}catch(NumberFormatException e){
				return "please enter a valid workload.";
			}
			int newWorkload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
			database.modifyWorkload(newWorkload);
			feedback += "and workload ";
		}
		feedback += "modified.";
		return feedback;
	}
}
