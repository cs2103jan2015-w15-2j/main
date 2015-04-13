//@author A0108361M
package com.nexus.simplify.database.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

/**
 * Represents an instance of a writer used to write to developer-specified file in JSON format.
 * */

public class Writer {

	private static final String NEW_LINE = "\n";
	private static final String JSON_ARRAY_CLOSE_BRACKET = "]";
	private static final String JSON_ARRAY_OPEN_BRACKET = "[";
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

	private CoreDatabase coreDatabase;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	//-------------//
	// Constructor //
	//-------------//
	
	public Writer() {
	}
	
	public Writer(CoreDatabase coreDatabase) {
		this.coreDatabase = coreDatabase;
	}
	
	//--------------//
	// File Writing //
	//--------------//
	
	/**
	 * Writes lists to file by getting file from Core Database with file creation in method.
	 * */
	public void writeToFile(ObservableList<GenericTask> inputObservableGeneric,
							ObservableList<DeadlineTask> inputObservableDeadline,
							ObservableList<TimedTask> inputObservableTimed,
							ObservableList<GenericTask> inputArchivedGeneric,
							ObservableList<DeadlineTask> inputArchivedDeadline,
							ObservableList<TimedTask> inputArchivedTimed) {
		
		try {
			String fileName = coreDatabase.getDataFilePath();
			File outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile);
			String stringOfTasks = JSON_ARRAY_OPEN_BRACKET;
			
			stringOfTasks = convertGenericTlToStore(inputObservableGeneric, stringOfTasks);
			stringOfTasks = convertDeadlineTlToStore(inputObservableDeadline, stringOfTasks);
			stringOfTasks = convertTimedTlToStore(inputObservableTimed, stringOfTasks);
			stringOfTasks = convertArchivedGenericTlToStore(inputArchivedGeneric, stringOfTasks);
			stringOfTasks = convertArchivedDeadlineTlToStore(inputArchivedDeadline, stringOfTasks);
			stringOfTasks = convertArchivedTimedTlToStore(inputArchivedTimed, stringOfTasks);
			stringOfTasks = stringOfTasks + JSON_ARRAY_CLOSE_BRACKET;
			
			fileWriter.write(stringOfTasks);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * Writes lists to file by getting file as parameter without file creation in method.
	 * */
	public void writeToFile(ObservableList<GenericTask> inputObservableGeneric,
							ObservableList<DeadlineTask> inputObservableDeadline,
							ObservableList<TimedTask> inputObservableTimed,
							ObservableList<GenericTask> inputArchivedGeneric,
							ObservableList<DeadlineTask> inputArchivedDeadline,
							ObservableList<TimedTask> inputArchivedTimed, File outputFile) {
		
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			String stringOfTasks = JSON_ARRAY_OPEN_BRACKET;
			
			stringOfTasks = convertGenericTlToStore(inputObservableGeneric, stringOfTasks);
			stringOfTasks = convertDeadlineTlToStore(inputObservableDeadline, stringOfTasks);
			stringOfTasks = convertTimedTlToStore(inputObservableTimed, stringOfTasks);
			stringOfTasks = convertArchivedGenericTlToStore(inputArchivedGeneric, stringOfTasks);
			stringOfTasks = convertArchivedDeadlineTlToStore(inputArchivedDeadline, stringOfTasks);
			stringOfTasks = convertArchivedTimedTlToStore(inputArchivedTimed, stringOfTasks);
			stringOfTasks = stringOfTasks + JSON_ARRAY_CLOSE_BRACKET;
			fileWriter.write(stringOfTasks);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	//---------------------//
	// Variable Conversion //
	//---------------------//

	private String convertGenericTlToStore(ObservableList<GenericTask> taskList,
											String stringOfTasks) {
		
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				GenericTask currentGenericTask = taskList.get(i);
				JSONObject jsonTask = createGenericTaskAsJsonObject(currentGenericTask);
				
				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				
				String taskInGsonFormat = gson.toJson(jsonElement);
				
				if (!isJsonArrayOpenBrace(stringOfTasks)) {
					stringOfTasks = stringOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringOfTasks = stringOfTasks + taskInGsonFormat;
				}
				
			}
		} 
		
		return stringOfTasks;
	}



	private String convertDeadlineTlToStore(ObservableList<DeadlineTask> deadlineTaskList, 
											String stringsOfTasks) {

		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currentDeadlineTask = deadlineTaskList.get(i);
				JSONObject jsonTask = createDeadlineTaskAsJsonObject(currentDeadlineTask);

				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				String taskInGsonFormat = gson.toJson(jsonElement);
				if (!isJsonArrayOpenBrace(stringsOfTasks)) {
					stringsOfTasks = stringsOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringsOfTasks = stringsOfTasks + taskInGsonFormat;
				}
			}
		} 
		return stringsOfTasks;
		
	}


	private String convertTimedTlToStore(ObservableList<TimedTask> timedTaskList,
										String stringOfTasks) {

		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currentTimedTask = timedTaskList.get(i);
				JSONObject jsonTask = createTimedTaskAsJsonObject(currentTimedTask);

				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				String taskInGsonFormat = gson.toJson(jsonElement);
				if (!isJsonArrayOpenBrace(stringOfTasks)) {
					stringOfTasks = stringOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringOfTasks = stringOfTasks + taskInGsonFormat;
				}
			}
		}
		return stringOfTasks;
	}


	private String convertArchivedGenericTlToStore(ObservableList<GenericTask> taskList,
													String stringOfTasks) {
		
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				GenericTask currentGenericTask = taskList.get(i);
				JSONObject jsonTask = createArchivedGenericTaskAsJsonObject(currentGenericTask);

				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				String taskInGsonFormat = gson.toJson(jsonElement);
				if (!isJsonArrayOpenBrace(stringOfTasks)) {
					stringOfTasks = stringOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringOfTasks = stringOfTasks + taskInGsonFormat;
				}
			}
		}
		return stringOfTasks;
	}

	private String convertArchivedDeadlineTlToStore(ObservableList<DeadlineTask> deadlineTaskList,
													String stringOfTasks) {

		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currentDeadlineTask = deadlineTaskList.get(i);
				JSONObject jsonTask = createArchivedDeadlineTaskAsJsonObject(currentDeadlineTask);

				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				String taskInGsonFormat = gson.toJson(jsonElement);
				if (!isJsonArrayOpenBrace(stringOfTasks)) {
					stringOfTasks = stringOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringOfTasks = stringOfTasks + taskInGsonFormat;
				}
			}
		}
		return stringOfTasks;
	}


	private String convertArchivedTimedTlToStore(ObservableList<TimedTask> timedTaskList,
												String stringOfTasks) {

		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currentTimedTask = timedTaskList.get(i);
				JSONObject jsonTask = createArchivedTimedTaskAsJsonObject(currentTimedTask);

				JsonParser jp = new JsonParser();
				JsonElement jsonElement = jp.parse(jsonTask.toString());
				String taskInGsonFormat = gson.toJson(jsonElement);
				if (!isJsonArrayOpenBrace(stringOfTasks)) {
					stringOfTasks = stringOfTasks + NEW_LINE + taskInGsonFormat;
				} else {
					stringOfTasks = stringOfTasks + taskInGsonFormat;
				}
			}
		}
		return stringOfTasks;
	}
	

	@SuppressWarnings("unchecked")
	private JSONObject createDeadlineTaskAsJsonObject(DeadlineTask currentDeadlineTask) {
		JSONObject jsonTask = new JSONObject();
		jsonTask.put(JSON_KEY_NAME, currentDeadlineTask.getName());
		jsonTask.put(JSON_KEY_DUEDATE, currentDeadlineTask.getReadableDeadline());
		jsonTask.put(JSON_KEY_WORKLOAD, currentDeadlineTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentDeadlineTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_DEADLINE);
		return jsonTask;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject createTimedTaskAsJsonObject(TimedTask currentTimedTask) {
		JSONObject jsonTask = new JSONObject();
		
		jsonTask.put(JSON_KEY_NAME, currentTimedTask.getName());
		jsonTask.put(JSON_KEY_START_TIME, currentTimedTask.getReadableStartTime());
		jsonTask.put(JSON_KEY_END_TIME, currentTimedTask.getReadableEndTime());
		jsonTask.put(JSON_KEY_WORKLOAD, currentTimedTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentTimedTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_TIMED);
		
		return jsonTask;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createGenericTaskAsJsonObject(GenericTask currentGenericTask) {
		JSONObject jsonTask = new JSONObject();
		jsonTask.put(JSON_KEY_NAME, currentGenericTask.getName());
		jsonTask.put(JSON_KEY_WORKLOAD, currentGenericTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentGenericTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_GENERIC);
		return jsonTask;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject createArchivedDeadlineTaskAsJsonObject(DeadlineTask currentDeadlineTask) {
		JSONObject jsonTask = new JSONObject();
		jsonTask.put(JSON_KEY_NAME, currentDeadlineTask.getName());
		jsonTask.put(JSON_KEY_DUEDATE, currentDeadlineTask.getReadableDeadline());
		jsonTask.put(JSON_KEY_WORKLOAD, currentDeadlineTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentDeadlineTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_DEADLINE);
		return jsonTask;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createArchivedGenericTaskAsJsonObject(GenericTask currentGenericTask) {
		JSONObject jsonTask = new JSONObject();
		jsonTask.put(JSON_KEY_NAME, currentGenericTask.getName());
		jsonTask.put(JSON_KEY_WORKLOAD, currentGenericTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentGenericTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_GENERIC);
		return jsonTask;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject createArchivedTimedTaskAsJsonObject(TimedTask currentTimedTask) {
		JSONObject jsonTask = new JSONObject();
		jsonTask.put(JSON_KEY_NAME, currentTimedTask.getName());
		jsonTask.put(JSON_KEY_START_TIME, currentTimedTask.getReadableStartTime());
		jsonTask.put(JSON_KEY_END_TIME, currentTimedTask.getReadableEndTime());
		jsonTask.put(JSON_KEY_WORKLOAD, currentTimedTask.getWorkload());
		jsonTask.put(JSON_KEY_ID, currentTimedTask.getId());
		jsonTask.put(JSON_KEY_TYPE, TASK_TYPE_ARCHIVED_TIMED);
		return jsonTask;
	}
	
	private boolean isJsonArrayOpenBrace(String tasksInGson) {
		return tasksInGson.equals(JSON_ARRAY_OPEN_BRACKET);
	}
}