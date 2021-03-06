//@author generated
package com.nexus.simplify.logic.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class determines task type and calls database
 * to add a task into the storage.
 */
public class Add {
	private final String DATE_FORMAT_PATTERN = "E MMM dd HH:mm:ss zzz yyy";
	private final String INVALID_WORKLOAD = "Please enter a valid workload.";
	private final String NO_NAME = "Please enter a name for this task.";
	private final String LOGGER_FLOATING = "Floating task is added.";
	private final String LOGGER_DEADLINE = "Deadline task is added.";
	private final String LOGGER_TIMED = "Timed task is added.";
	private static Logger logger = Logger.getLogger("AddOp"); 
	
	public Add() {}
	
	public String execute(String[] parameter) throws Exception { 
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		final String MESSAGE_TASK_ADDED = "Task \"" + name + "\" added successfully.";
		String feedback;
		int workload;
		Database database = MainApp.getDatabase();
		
		if (workloadStr == null || workloadStr.isEmpty()) {
			workload = 0;
		} else {
			workload = exceptionHandling(workloadStr);
		}
		
		if(name == null || name.isEmpty()) {
			throw new Exception(NO_NAME);
		}
		
		if((newStartTime == null || newStartTime.isEmpty()) &&
		   (newEndTime == null || newEndTime.isEmpty())) {
			database.addGenericTask(name, workload);
			logger.log(Level.INFO, LOGGER_FLOATING);
			feedback =  MESSAGE_TASK_ADDED;
		} else {
			if(newStartTime.equals(newEndTime)) {
				Date deadline = df.parse(newStartTime);
				database.addDeadlineTask(name,deadline,workload);
				logger.log(Level.INFO, LOGGER_DEADLINE);
				feedback = MESSAGE_TASK_ADDED;
			} else {
				Date startTime = df.parse(newStartTime);
				Date endTime = df.parse(newEndTime);
				database.addTimedTask(name,startTime,endTime,workload);
				logger.log(Level.INFO, LOGGER_TIMED);
				feedback = MESSAGE_TASK_ADDED;
			}
		}
		return feedback;
	}

	// exception handling for wrong workload format
	private int exceptionHandling(String workloadStr) throws Exception {
		int workload;
		try {
			workload = Integer.parseInt(workloadStr);
		} catch (NumberFormatException e) {
			throw new Exception(INVALID_WORKLOAD);
		}
		return workload;
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workloadStringForm = parameter[ParameterType.NEW_WORKLOAD_POS];
		String feedback;
		int workload;
		
		if(workloadStringForm == null || workloadStringForm.isEmpty()) {
			workload = 0;
		} else {
			try {
				workload = Integer.parseInt(workloadStringForm);
			} catch (NumberFormatException e) {
				feedback = INVALID_WORKLOAD;
				return feedback;
			}
		}
		
		if(name == null || name.isEmpty()) {
			feedback = NO_NAME;
			return feedback;
		}
		
		if((newStartTime == null || newStartTime.isEmpty()) &&
			(newEndTime == null || newEndTime.isEmpty())) {
			feedback = "Successfully added floating task \"" + name + 
					"\" with workload " + String.valueOf(workload) + ".";
		} else {
			if(newStartTime.equals(newEndTime)) {
				feedback = "Successfully added deadline task \"" + name + 
					"\" with deadline " + newStartTime + " and workload of " +
					String.valueOf(workload) + ".";
			} else {
				feedback = "Successfully added timed task \"" + name + 
					"\" with starting time " + newStartTime + " and ending time " +
					newEndTime + " and workload of " + String.valueOf(workload) + ".";
			}
		}
		return feedback;
	}
}
