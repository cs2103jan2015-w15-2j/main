package com.nexus.simplify.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexus.simplify.database.observables.DeadlineTaskList;
import com.nexus.simplify.database.observables.GenericTaskList;
import com.nexus.simplify.database.observables.TimedTaskList;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

public class Writer {
	
	private static final String TASK_TYPE_TIMED = "Timed";
	private static final String TASK_TYPE_DEADLINE = "Deadline";
	private static final String TASK_TYPE_GENERIC = "Generic";
	
	private static final String JSON_KEY_DUEDATE = "DueDate";
	private static final String JSON_KEY_ID = "ID";
	private static final String JSON_KEY_WORKLOAD = "Workload";
	private static final String JSON_KEY_END_TIME = "End Time";
	private static final String JSON_KEY_START_TIME = "Start Time";
	private static final String JSON_KEY_NAME = "Name";
	private static final String JSON_KEY_TYPE = "Type";
	
	Database database;
	
	public Writer(Database database) {
		this.database = database;
	}
	//--------------//
	// File Writing //
	//--------------//
	
	public void writeToFile(GenericTaskList inputGenericTL, DeadlineTaskList inputDeadlineTL, TimedTaskList inputTimedTL) {
		try {
			String fileName = database.getDataFilePath();
			File outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile);
			JSONArray jsonArrayForStorage = new JSONArray();
			
			convertToStore(inputDeadlineTL, jsonArrayForStorage);
			convertToStore(inputTimedTL, jsonArrayForStorage);
			convertToStore(inputGenericTL, jsonArrayForStorage); 
			
			fileWriter.write(jsonArrayForStorage.toJSONString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//---------------------//
	// Variable Conversion //
	//---------------------//
		
	@SuppressWarnings("unchecked")
	private void convertToStore(GenericTaskList taskList, JSONArray jsonArrayForStorage) {
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				GenericTask currGenericTask = taskList.get(i);
				JSONObject jsonTask = new JSONObject();
				
				jsonTask.put(JSON_KEY_NAME, currGenericTask.getName());
				jsonTask.put(JSON_KEY_WORKLOAD, currGenericTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currGenericTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_GENERIC);
				
				jsonArrayForStorage.add(jsonTask);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void convertToStore(DeadlineTaskList deadlineTaskList, JSONArray jsonArrayForStorage) {
		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currDeadlineTask = deadlineTaskList.get(i);
				JSONObject jsonTask = new JSONObject();
				
				jsonTask.put(JSON_KEY_NAME, currDeadlineTask.getName());
				jsonTask.put(JSON_KEY_DUEDATE, currDeadlineTask.getReadableDeadline());
				jsonTask.put(JSON_KEY_WORKLOAD, currDeadlineTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currDeadlineTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_DEADLINE);
				
				jsonArrayForStorage.add(jsonTask);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void convertToStore(TimedTaskList timedTaskList, JSONArray jsonArrayForStorage) {
		
		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currTimedTask = timedTaskList.get(i);
				JSONObject jsonTask = new JSONObject();
				
				jsonTask.put(JSON_KEY_NAME, currTimedTask.getName());
				jsonTask.put(JSON_KEY_START_TIME, currTimedTask.getReadableStartTime());
				jsonTask.put(JSON_KEY_END_TIME, currTimedTask.getReadableEndTime());
				jsonTask.put(JSON_KEY_WORKLOAD, currTimedTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currTimedTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_TIMED);
				
				jsonArrayForStorage.add(jsonTask);
			}
		}
	}
}