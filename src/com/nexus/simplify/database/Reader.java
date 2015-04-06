package com.nexus.simplify.database;

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

public class Reader {

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
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "dd MMM yyyy HH:mm";
	
	Database database;
	ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();
	ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	
	public Reader(Database database) {
		this.database = database;
	}
	
	//--------------//
	// File Reading //
	//--------------//

	public JSONArray retrieveDataFromDataFile(String dataFileName) {
		JSONArray jsonTaskArray = new JSONArray();

		try {
			File dataFile = new File(dataFileName);
			if (!dataFile.exists()) {
				database.createNewFile(dataFileName);
			} else {
				JSONParser jsonParser = new JSONParser();
				Object object = jsonParser.parse(new FileReader(dataFileName));
				jsonTaskArray = (JSONArray) object;
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return jsonTaskArray;
	}

	/**
	 * Populates all three task lists (generic, deadline, timed)
	 * with their corresponding data obtained from the JSON file.
	 * 
	 * */
	public void populateTaskLists(JSONArray jsonTaskArray) {
		for (Object object : jsonTaskArray) {
			JSONObject jsonTask = (JSONObject) object;
			String taskType = (String) jsonTask.get(JSON_KEY_TYPE);
			switch (taskType) {
			case TASK_TYPE_GENERIC: 
				addGenericTaskToList(jsonTask);
				break;
			case TASK_TYPE_DEADLINE:
				addDeadlineTaskToList(jsonTask);
				break;
			case TASK_TYPE_TIMED:
				addTimedTaskToList(jsonTask);
				break;
			case TASK_TYPE_ARCHIVED_GENERIC:
				addArchivedGenericTaskToList(jsonTask);
				break;
			case TASK_TYPE_ARCHIVED_DEADLINE:
				addArchivedDeadlineTaskToList(jsonTask);
				break;
			case TASK_TYPE_ARCHIVED_TIMED:
				addArchivedTimedTaskToList(jsonTask);
				break;
			default:
				// invalid entry; ignore and continue to the next entry
				break;
			}
			database.setObservableDeadlineTL(observableDeadlineTL);
			database.setObservableGenericTL(observableGenericTL);
			database.setObservableTimedTL(observableTimedTL);
			database.setArchivedGenericTL(archivedGenericTL);
			database.setArchivedDeadlineTL(archivedDeadlineTL);
			database.setArchivedTimedTL(archivedTimedTL);
		}
	}

	//------------------//
	// Populating Lists //
	//------------------//

	private void addTimedTaskToList(JSONObject jsonTask) {
		TimedTask timedTask = new TimedTask (
				(String)jsonTask.get(JSON_KEY_NAME),
				parseDate((String)jsonTask.get(JSON_KEY_START_TIME)), 
				parseDate((String)jsonTask.get(JSON_KEY_END_TIME))
				);

		timedTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		timedTask.setId((String)jsonTask.get(JSON_KEY_ID));
		observableTimedTL.add(timedTask);
	}
	
	private void addArchivedTimedTaskToList(JSONObject jsonTask) {
		TimedTask timedTask = new TimedTask (
				(String)jsonTask.get(JSON_KEY_NAME),
				parseDate((String)jsonTask.get(JSON_KEY_START_TIME)), 
				parseDate((String)jsonTask.get(JSON_KEY_END_TIME))
				);

		timedTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		timedTask.setId((String)jsonTask.get(JSON_KEY_ID));
		archivedTimedTL.add(timedTask);
	}

	private void addDeadlineTaskToList(JSONObject jsonTask) {
		DeadlineTask deadlineTask = new DeadlineTask ( 
				(String)jsonTask.get(JSON_KEY_NAME), 
				parseDate((String)jsonTask.get(JSON_KEY_DUEDATE))
				);
		deadlineTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		deadlineTask.setId((String)jsonTask.get(JSON_KEY_ID));
		observableDeadlineTL.add(deadlineTask);
	}
	
	private void addArchivedDeadlineTaskToList(JSONObject jsonTask) {
		DeadlineTask deadlineTask = new DeadlineTask ( 
				(String)jsonTask.get(JSON_KEY_NAME), 
				parseDate((String)jsonTask.get(JSON_KEY_DUEDATE))
				);
		deadlineTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		deadlineTask.setId((String)jsonTask.get(JSON_KEY_ID));
		archivedDeadlineTL.add(deadlineTask);
	}

	private void addGenericTaskToList(JSONObject jsonTask) {
		GenericTask genericTask = new GenericTask((String)jsonTask.get(JSON_KEY_NAME));
		genericTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		genericTask.setId((String)jsonTask.get(JSON_KEY_ID));
		observableGenericTL.add(genericTask);
	}
	
	private void addArchivedGenericTaskToList(JSONObject jsonTask) {
		GenericTask genericTask = new GenericTask((String)jsonTask.get(JSON_KEY_NAME));
		genericTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		genericTask.setId((String)jsonTask.get(JSON_KEY_ID));
		observableGenericTL.add(genericTask);
	}

	//---------------------//
	// Variable Conversion //
	//---------------------//

	/**
	 * Re-formats a date represented as a String object
	 * into a DateTime object.
	 * 
	 * @param date date in String format
	 * @return date in DateTime format
	 * */
	private DateTime parseDate(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
		DateTime dueDate = format.parseDateTime(date);
		return dueDate;
	}
}
