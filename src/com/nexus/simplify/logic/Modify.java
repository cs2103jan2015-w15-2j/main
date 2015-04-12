//@author generated
package com.nexus.simplify.logic;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class determines what fields of a task to modify 
 * and calls APIs provided by database to modify storage.
 */
public class Modify {
	public Modify() {}
	
	String execute(String[] parameter) {
		// pattern follows Java.util.Date toString() method
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		int indexToModify;
		
		try{
			indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			String feedback = "Please enter a task index to modify.";
			return feedback;
		}
		
		Database database = MainApp.getDatabase();
		indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "Task ";
		String newName = parameter[ParameterType.NEW_NAME_POS];
		if(newName != null && !newName.isEmpty()) {
			database.modifyName(indexToModify, newName);
			feedback += "name, ";
		}
		
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		if(newStartTime != null && !newStartTime.isEmpty() && 
			newEndTime != null && !newEndTime.isEmpty()) {
			Date startTime;
			Date endTime;
			try {
				startTime = df.parse(newStartTime);
				endTime = df.parse(newEndTime);
				database.modifyStartEnd(indexToModify, startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			feedback += "time, ";
		}
		
		String newWorkloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		if(newWorkloadStr != null && !newWorkloadStr.isEmpty()) {
			int newWorkload;
			try{
				newWorkload = Integer.parseInt(newWorkloadStr);
			} catch (NumberFormatException e) {
				return "Please enter a valid workload.";
			}
			
			newWorkload = Integer.parseInt(newWorkloadStr);
			try {
				database.modifyWorkload(indexToModify, newWorkload);
			} catch (Exception e) {
				e.printStackTrace();
			}
			feedback += "workload, ";
		}
		
		String newFileLocation = parameter[ParameterType.NEW_FILELOCATION_POS];
		if(newFileLocation != null && !newFileLocation.isEmpty()) {
			try {
				database.modifyFileLocation(newFileLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			feedback += "file location, ";
		}
		
		if((newName==null || newName.isEmpty()) && (newStartTime==null || newStartTime.isEmpty()) &&
			(newEndTime==null || newEndTime.isEmpty()) && (newWorkloadStr==null || newWorkloadStr.isEmpty())
			&& (newFileLocation==null || newFileLocation.isEmpty())) { // if everything if null
			feedback = "Please specify something to modify.";
			return feedback;
		}
		feedback += "modified.";
		return feedback;
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		@SuppressWarnings("unused")
		int indexToModify;
		try{
			indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			String feedback = "Please enter a task index to modify.";
			return feedback;
		}
		
		indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "Task ";
		String newName = parameter[ParameterType.NEW_NAME_POS];
		if(newName != null && !newName.isEmpty()) {
			feedback += "name, ";
		}
		
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		if(newStartTime != null && !newStartTime.isEmpty() && 
			newEndTime != null && !newEndTime.isEmpty()) {
			feedback += "time, ";
		}
		
		String newWorkloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		if(newWorkloadStr != null && !newWorkloadStr.isEmpty()) {
			@SuppressWarnings("unused")
			int newWorkload;
			try{
				newWorkload = Integer.parseInt(newWorkloadStr);
			}catch(NumberFormatException e){
				feedback = "Please enter a valid workload.";
				return feedback;
			}
			newWorkload = Integer.parseInt(newWorkloadStr);
			feedback += "workload, ";
		}
		
		if((newName==null || newName.isEmpty()) && (newStartTime==null || newStartTime.isEmpty()) &&
				(newEndTime==null || newEndTime.isEmpty()) && 
				(newWorkloadStr==null || newWorkloadStr.isEmpty())) {
				feedback = "Please specify something to modify.";
				return feedback;
		}
		feedback += "modified.";
		return feedback;
	}
}
