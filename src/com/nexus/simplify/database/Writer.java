package com.nexus.simplify.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

/**
 * Represents an instance of a writer used to write to developer-specified file in JSON format.
 * */

public class Writer {

	private static final String TASK_TYPE_TIMED = "Timed";
	private static final String TASK_TYPE_DEADLINE = "Deadline";
	private static final String TASK_TYPE_GENERIC = "Generic";
	private static final String TASK_TYPE_ARCHIVED_GENERIC = "Archived Generic";
	private static final String TASK_TYPE_ARCHIVED_DEADLINE = "Archived Deadline";
	private static final String TASK_TYPE_ARCHIVED_TIMED = "Archived Timed";
	
	private static final String JSON_KEY_DUEDATE = "DueDate";
	private static final String JSON_KEY_ID = "ID";
	private static final String JSON_KEY_WORKLOAD = "Workload";
	private static final String JSON_KEY_END_TIME = "End Time";
	private static final String JSON_KEY_START_TIME = "Start Time";
	private static final String JSON_KEY_NAME = "Name";
	private static final String JSON_KEY_TYPE = "Type";

	private Database database;

	//-------------//
	// Constructor //
	//-------------//
	
	public Writer(Database database) {
		this.database = database;
	}
	
	//--------------//
	// File Writing //
	//--------------//

	public void writeToFile(ObservableList<GenericTask> inputObservableGeneric,
			ObservableList<DeadlineTask> inputObservableDeadline, ObservableList<TimedTask> inputObservableTimed,
			ObservableList<GenericTask> inputArchivedGeneric, ObservableList<DeadlineTask> inputArchivedDeadline,
			ObservableList<TimedTask> inputArchivedTimed) {
		
		try {
			String fileName = database.getDataFilePath();
			File outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile);
			JSONArray jsonArrayForStorage = new JSONArray();

			convertGenericTlToStore(inputObservableGeneric, jsonArrayForStorage);
			convertDeadlineTlToStore(inputObservableDeadline, jsonArrayForStorage);
			convertTimedTlToStore(inputObservableTimed, jsonArrayForStorage);
			convertArchivedGenericTlToStore(inputArchivedGeneric, jsonArrayForStorage);
			convertArchivedDeadlineTlToStore(inputArchivedDeadline, jsonArrayForStorage);
			convertArchivedTimedTlToStore(inputArchivedTimed, jsonArrayForStorage);

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
	private void convertGenericTlToStore(ObservableList<GenericTask> taskList, JSONArray jsonArrayForStorage) {
		
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				GenericTask currentGenericTask = taskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentGenericTask.getName());
				jsonTask.put(JSON_KEY_WORKLOAD, currentGenericTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentGenericTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_GENERIC);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void convertDeadlineTlToStore(ObservableList<DeadlineTask> deadlineTaskList, JSONArray jsonArrayForStorage) {

		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currentDeadlineTask = deadlineTaskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentDeadlineTask.getName());
				jsonTask.put(JSON_KEY_DUEDATE, currentDeadlineTask.getReadableDeadline());
				jsonTask.put(JSON_KEY_WORKLOAD, currentDeadlineTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentDeadlineTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_DEADLINE);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void convertTimedTlToStore(ObservableList<TimedTask> timedTaskList, JSONArray jsonArrayForStorage) {

		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currentTimedTask = timedTaskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentTimedTask.getName());
				jsonTask.put(JSON_KEY_START_TIME, currentTimedTask.getReadableStartTime());
				jsonTask.put(JSON_KEY_END_TIME, currentTimedTask.getReadableEndTime());
				jsonTask.put(JSON_KEY_WORKLOAD, currentTimedTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentTimedTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_TIMED);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void convertArchivedGenericTlToStore(ObservableList<GenericTask> taskList, JSONArray jsonArrayForStorage) {
		
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				GenericTask currentGenericTask = taskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentGenericTask.getName());
				jsonTask.put(JSON_KEY_WORKLOAD, currentGenericTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentGenericTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_GENERIC);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void convertArchivedDeadlineTlToStore(ObservableList<DeadlineTask> deadlineTaskList, JSONArray jsonArrayForStorage) {

		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currentDeadlineTask = deadlineTaskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentDeadlineTask.getName());
				jsonTask.put(JSON_KEY_DUEDATE, currentDeadlineTask.getReadableDeadline());
				jsonTask.put(JSON_KEY_WORKLOAD, currentDeadlineTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentDeadlineTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_DEADLINE);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void convertArchivedTimedTlToStore(ObservableList<TimedTask> timedTaskList, JSONArray jsonArrayForStorage) {

		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currentTimedTask = timedTaskList.get(i);
				JSONObject jsonTask = new JSONObject();

				jsonTask.put(JSON_KEY_NAME, currentTimedTask.getName());
				jsonTask.put(JSON_KEY_START_TIME, currentTimedTask.getReadableStartTime());
				jsonTask.put(JSON_KEY_END_TIME, currentTimedTask.getReadableEndTime());
				jsonTask.put(JSON_KEY_WORKLOAD, currentTimedTask.getWorkload());
				jsonTask.put(JSON_KEY_ID, currentTimedTask.getId());
				jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_TIMED);

				jsonArrayForStorage.add(jsonTask);
			}
		}
		
	}
	
}