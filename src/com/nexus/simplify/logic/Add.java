//@author generated
package com.nexus.simplify.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
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
	private final String FLOATING_TASK_MESSAGE = "Floating task added at index ";
	private final String DEADLINE_TASK_MESSAGE = "Deadline task added at index ";
	private final String TIMED_TASK_MESSAGE = "Timed task added at index ";
	
	public Add() {}
	
	String execute(String[] parameter) throws ParseException { 
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		String feedback;
		int workload;
		Database database = MainApp.getDatabase();
		
		// this if-else statement caters to workload
		if (workloadStr == null || workloadStr.isEmpty()) {
			workload = 0;
		} else {
			try {
				workload = Integer.parseInt(workloadStr);
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
			database.addGenericTask(name, workload);
			feedback = FLOATING_TASK_MESSAGE;
		} else {
			if(newStartTime.equals(newEndTime)) {
				Date deadline = df.parse(newStartTime);
				database.addDeadlineTask(name,deadline,workload);
				feedback = DEADLINE_TASK_MESSAGE;
			} else {
				Date startTime = df.parse(newStartTime);
				Date endTime = df.parse(newEndTime);
				database.addTimedTask(name,startTime,endTime,workload);
				feedback = TIMED_TASK_MESSAGE;
			}
		}
		return feedback;
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
				feedback = "Please enter a valid workload.";
				return feedback;
			}
		}
		
		if(name == null || name.isEmpty()) {
			feedback = "Please enter a name for this task.";
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
