//@author generated
package com.nexus.simplify.logic.core;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class determines what fields of a task to modify 
 * and calls APIs provided by database to modify storage.
 */
public class Modify {
	private final String FORMAT_PATTERN = "E MMM dd hh:mm:ss zzz yyy";
	private final String NO_INDEX = "Please enter a task index to modify.";
	private final String INVALID_WORKLOAD = "Please enter a valid workload.";
	private final String NOTHING_TO_MODIFY = "Please specify something to modify.";
	private final String LOCATION_MODIFIED = "file location modified ";
	
	public Modify() {}
	
	public String execute(String[] parameter) throws Exception {
		// pattern follows Java.util.Date toString() method
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_PATTERN);
		Database database = MainApp.getDatabase();
		
		String newFileLocation = parameter[ParameterType.NEW_FILELOCATION_POS];
		if(newFileLocation != null && !newFileLocation.isEmpty()) {
			exceptionHandling(database, newFileLocation);
			String feedback = LOCATION_MODIFIED;
			return feedback;
		}
		
		int indexToModify;
		exceptionHandling(parameter);
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
			exceptionHandling(df, indexToModify, database, newStartTime, newEndTime);
			feedback += "time, ";
		}
		
		String newWorkloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		if(newWorkloadStr != null && !newWorkloadStr.isEmpty()) {
			int newWorkload;
			exceptionHandling(newWorkloadStr);
			
			newWorkload = Integer.parseInt(newWorkloadStr);
			exceptionHandling(indexToModify, database, newWorkload);
			feedback += "workload, ";
		}
		
		if((newName==null || newName.isEmpty()) && (newStartTime==null || newStartTime.isEmpty()) &&
			(newEndTime==null || newEndTime.isEmpty()) && (newWorkloadStr==null || newWorkloadStr.isEmpty())
			&& (newFileLocation==null || newFileLocation.isEmpty())) { // if everything if null
			feedback = NOTHING_TO_MODIFY;
			return feedback;
		}
		feedback += "modified.";
		return feedback;
	}

	// exception handling for modifying file location
	private void exceptionHandling(Database database, String newFileLocation) throws Exception {
		try {
			database.modifyFileLocation(newFileLocation);
		} catch (Exception e) {
			throw e;
		}
	}

	// exception handling for modifying workload
	private void exceptionHandling(int indexToModify, Database database,
									int newWorkload) throws Exception {
		try {
			database.modifyWorkload(indexToModify, newWorkload);
		} catch (Exception e) {
			throw e;
		}
	}
	
	// exception handling for modifying time
	private void exceptionHandling(SimpleDateFormat df, int indexToModify, Database database, 
								String newStartTime, String newEndTime) throws Exception {
		Date startTime;
		Date endTime;
		try {
			startTime = df.parse(newStartTime);
			endTime = df.parse(newEndTime);
			database.modifyStartEnd(indexToModify, startTime, endTime);
		} catch (Exception e) {
			throw e;
		}
	}
	
	// exception handling for wrong workload format
	private void exceptionHandling(String newWorkloadStr) throws Exception {
		try{
			@SuppressWarnings("unused")
			int newWorkload = Integer.parseInt(newWorkloadStr);
		} catch (NumberFormatException e) {
			throw new Exception(INVALID_WORKLOAD);
		}
	}
	
	// exception handling for wrong index format
	private void exceptionHandling(String[] parameter) throws Exception {
		@SuppressWarnings("unused")
		int indexToModify;
		try{
			indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			throw new Exception(NO_INDEX);
		}
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		@SuppressWarnings("unused")
		int indexToModify;
		try{
			indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		} catch (NumberFormatException e) {
			String feedback = NO_INDEX;
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
				feedback = INVALID_WORKLOAD;
				return feedback;
			}
			newWorkload = Integer.parseInt(newWorkloadStr);
			feedback += "workload, ";
		}
		
		if((newName==null || newName.isEmpty()) && (newStartTime==null || newStartTime.isEmpty()) &&
				(newEndTime==null || newEndTime.isEmpty()) && 
				(newWorkloadStr==null || newWorkloadStr.isEmpty())) {
				feedback = NOTHING_TO_MODIFY;
				return feedback;
		}
		feedback += "modified.";
		return feedback;
	}
}
