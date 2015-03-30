/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.util.*;
import java.io.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.nexus.simplify.database.observables.DeadlineTaskList;
import com.nexus.simplify.database.observables.GenericTaskList;
import com.nexus.simplify.database.observables.TaskListPackage;
import com.nexus.simplify.database.observables.TimedTaskList;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

@SuppressWarnings("unused")

public class Database {
	
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
	private static final String JSON_KEY_DATA_FILE_DIRECTORY = "data file directory";
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "dd MMM yyyy HH:mm";
	
	private static final String MSG_INDEX_OOR = "Index is out of range.";
	private static final String MSG_INVALID_WORKLOAD = "Invalid workload value entered. Supported workload values range from 1 to 5.";
	
	private static final String DEFAULT_FILE_NAME = "input.json";
	private static final String DEFAULT_DATA_FILE_LOCATION = "SavedData/";
	private static final String CONFIG_FILE_LOCATION = "config/";
	private static final String CONFIG_FILE_NAME = "simplify-config.json";
	
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private String dataFileLocation;
	
	private GenericTaskList genericTaskList = new GenericTaskList();
	private TimedTaskList timedTaskList = new TimedTaskList();
	private DeadlineTaskList deadlineTaskList = new DeadlineTaskList();
		
	//---------------//
	// API for Logic //
	//---------------//
	
	/**
	 * Adds a new timed task to the list of timed tasks.
	 * 
	 * @param name name of task
	 * @param startTime time and date when task starts
	 * @param endTime time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addTimedTask(String name, Date startTime, Date endTime, int workload) {
		if (workload == 0) {
			timedTaskList.add(new TimedTask(name, startTime, endTime));
		} else {
			timedTaskList.add(new TimedTask(name, startTime, endTime, workload));
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * Adds a new deadline-based task to the list of deadline-based tasks.
	 * 
	 * @param name name of task
	 * @param deadline time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addDeadlineTask(String name, Date deadline, int workload) {
		if (workload == 0) {
			deadlineTaskList.add(new DeadlineTask(name, deadline));
		} else {
			deadlineTaskList.add(new DeadlineTask(name, deadline, workload));
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * Adds a new generic (floating) task to the list of generic tasks.
	 * 
	 * @param name name of task
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addGenericTask(String name, int workload) {
		if (workload == 0) {
			genericTaskList.add(new GenericTask(name));
		} else {
			genericTaskList.add(new GenericTask(name, workload));
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	
	/**
	 * Deletes a task from the table based on its index being displayed on the billboard.
	 * 
	 * @param index index of task with respect to the billboard
	 * @throws IndexOutOfBoundsException if index is not within range of 1 - 15 inclusive.
	 * */
	public void deleteTaskByIndex(int index) throws IndexOutOfBoundsException {
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= deadlineTaskList.size()) {
				deadlineTaskList.delete(index - 1);
			} else if (index > deadlineTaskList.size() && index <= timedTaskList.size()) {
				timedTaskList.delete(index - 1);
			} else {
				genericTaskList.delete(index - 1);
			}
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * returns the total size of all three task lists.
	 * 
	 * @return total size of all three task lists
	 * */
	private int totalSizeOfAllLists() {
		return genericTaskList.size() + deadlineTaskList.size() + timedTaskList.size();
	}
	
	/**
	 * toggles the sorting order of the task lists by a keyword
	 * 
	 * @param option toggles the sorting order of the task lists
	 * */
	public void toggleDisplay(String option) {
		if (option.equals("deadline")) {
			deadlineTaskList.sortBy(deadlineTaskList.getSortType(option));
			timedTaskList.sortBy(timedTaskList.getSortType(option));
		} else {
			genericTaskList.sortBy(genericTaskList.getSortType(option));
			deadlineTaskList.sortBy(deadlineTaskList.getSortType(option));
			timedTaskList.sortBy(timedTaskList.getSortType(option));
		}
	}

	/**
	 * Modifies the name value of a task.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newName new name to be written to the task
	 * @throws IndexOutofBoundsException if index is not within range of 1 - 15 inclusive 
	 * */
	public void modifyName(int index, String newName) throws IndexOutOfBoundsException {
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= deadlineTaskList.size()) {
				deadlineTaskList.get(index - 1).setName(newName);
			} else if (index > deadlineTaskList.size() && index <= timedTaskList.size()) {
				timedTaskList.get(index - 1).setName(newName);
			} else {
				genericTaskList.get(index - 1).setName(newName);
			}
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * Modifies the workload value of a task.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newWorkloadValue new value of workload to be written to the task
	 * @throws IndexOutofBoundsException if index is not within range of 1 - 15 inclusive 
	 * @throws Exception if workload is not in range of 1 - 5 inclusive.
	 * */
	public void modifyWorkload(int index, int newWorkloadValue) throws IndexOutOfBoundsException, Exception {
		if (newWorkloadValue > 5 || newWorkloadValue < 1) {
			throw new Exception("Invalid workload value entered. Supported workload values range from 1 to 5.");
		} else {
			if (index > this.totalSizeOfAllLists() || index < 1) {
				throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
			} else {
				if (index <= deadlineTaskList.size()) {
					deadlineTaskList.get(index - 1).setWorkload(newWorkloadValue);
				} else if (index > deadlineTaskList.size() && index <= timedTaskList.size()) {
					timedTaskList.get(index - 1).setWorkload(newWorkloadValue);
				} else {
					genericTaskList.get(index - 1).setWorkload(newWorkloadValue);
				}
			}
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * Modifies the start time of a task.
	 * Changes GenericTask to DeadlineTask when start time is added.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newStartTime new value of start time to be written to the task
	 * @throws IndexOutofBoundsException if index is not within range of 1 - 15 inclusive
	 * */
	public void modifyStartTime(int index, Date newStartTime) throws IndexOutOfBoundsException {
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= deadlineTaskList.size()) {
				DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
				DeadlineTask deadlineTask = deadlineTaskList.get(index - 1);
				String deadline = deadlineTask.getReadableDeadline();
				int comparisonValue = format.print(new DateTime(newStartTime)).compareTo(deadline);
				if (comparisonValue <= 0) {
					timedTaskList.add(new TimedTask(deadlineTask.getNameAsStringProperty(), newStartTime, deadlineTask.getDeadline(), deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty()));
				} else {
					timedTaskList.add(new TimedTask(deadlineTask.getNameAsStringProperty(), deadlineTask.getDeadline(), newStartTime, deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty()));
				}
			} else if (index > deadlineTaskList.size() && index <= timedTaskList.size()) {
				timedTaskList.get(index - 1).setStartTime(newStartTime);
			} else {
				GenericTask task = genericTaskList.get(index - 1);
				deadlineTaskList.add(new DeadlineTask(task.getNameAsStringProperty(), newStartTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	/**
	 * Modifies the end time of a task.
	 * Changes GenericTask to DeadlineTask when end time is added
	 * Changes DeadlineTask to TimedTask when end time is added
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newEndTime new value of end time to be written to the task
	 * @throws IndexOutofBoundsException if index is not within range of 1 - 15 inclusive
	 * */
	public void modifyEndTime(int index, Date newEndTime) throws IndexOutOfBoundsException {
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= deadlineTaskList.size()) {
				DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
				DeadlineTask deadlineTask = deadlineTaskList.get(index - 1);
				String deadline = deadlineTask.getReadableDeadline();
				int comparisonValue = format.print(new DateTime(newEndTime)).compareTo(deadline);  
				if (comparisonValue <= 0) {
					timedTaskList.add(new TimedTask(deadlineTask.getNameAsStringProperty(), newEndTime, deadlineTask.getDeadline(), deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty()));
				} else {
					timedTaskList.add(new TimedTask(deadlineTask.getNameAsStringProperty(), deadlineTask.getDeadline(), newEndTime, deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty()));
				}
			} else if (index > deadlineTaskList.size() && index <= timedTaskList.size()) {
				timedTaskList.get(index - 1).setEndTime(newEndTime);
			} else {
				GenericTask task = genericTaskList.get(index - 1);
				deadlineTaskList.add(new DeadlineTask(task.getNameAsStringProperty(), newEndTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writeToFile(genericTaskList, deadlineTaskList, timedTaskList);
	}
	
	//------------//
	// API for UI //
	//------------//
	
	/**
	 * packages the three lists.
	 * 
	 * @return a package containing all three lists.
	 * 
	 * */
	public TaskListPackage getTaskListPackage() {
		return new TaskListPackage(deadlineTaskList, timedTaskList, genericTaskList);
	}

	//-------------//
	// Constructor //
	//-------------//
	
	/**
	 * @param fileName name of input file to be opened
	 * @throws IOException for an interrupted IO operation.
	 * 
	 * */
	public Database() throws IOException {
		initDatabase();
		JSONArray jsonTaskArray = retrieveDataFromDataFile();
		populateTaskLists(jsonTaskArray);
	}
	
	//----------------//
	// Initialization //
	//----------------//

	private void initDatabase() {
		String configFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
		if (!configFileExists(configFilePath)) {
			createNewFile(configFilePath);
			revertToDefaultSettings();
		} else {
			retrieveSettingsFromConfigFile();
		}
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public GenericTaskList getGenericTL() {
		return genericTaskList;
	}
	
	public TimedTaskList getTimedTL() {
		return timedTaskList;
	}
	
	public DeadlineTaskList getDeadlineTL() {
		return deadlineTaskList;
	}
	
	//-----------------//
	// File Processing //
	//-----------------//
	
	/**
	 * retrieves the data file location from the 
	 * configuration file.
	 * */
	private void retrieveSettingsFromConfigFile() {
		JSONObject configJson = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
			String configFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
			Object object = jsonParser.parse(new FileReader(CONFIG_FILE_LOCATION + CONFIG_FILE_NAME));
			configJson = (JSONObject) object;
			
			if (!configJson.containsKey(JSON_KEY_DATA_FILE_DIRECTORY)) {
				revertToDefaultSettings();
			} else {
				this.dataFileLocation = String.valueOf(configJson.get(JSON_KEY_DATA_FILE_DIRECTORY));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sets the location of the data file to its default location
	 * and stores the default settings into the configuration file.
	 * */
	private void revertToDefaultSettings() {
		this.dataFileLocation = DEFAULT_DATA_FILE_LOCATION;
		storeSettingsIntoConfigFile();
	}
	
	/**
	 * @param configFileName name of program configuration file
	 * @return true if config file exists, false otherwise.
	 * */
	private boolean configFileExists(String configFileName) {
		File configFile = new File(configFileName);
		return configFile.exists();
	}
	
	/**
	 * creates a new file for the program.
	 * 
	 * @param fileName name of the file
	 * */
	private void createNewFile(String fileName) {
		try {
			File newConfigFile = new File(fileName);
			
			// we make a new instance of directory for the file.
			if (newConfigFile.getParentFile() != null) {
				newConfigFile.getParentFile().mkdirs();
			}
			
			newConfigFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * 
	 * */
	@SuppressWarnings("unchecked")
	private void storeSettingsIntoConfigFile() {
		JSONObject configJson = new JSONObject();
		configJson.put("data file location", DEFAULT_DATA_FILE_LOCATION);
		String outputConfigFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
		File outputConfigFile = new File(outputConfigFilePath);
		try {
			FileWriter fileWriter = new FileWriter(outputConfigFilePath);
			fileWriter.write(configJson.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	//--------------//
	// File Reading //
	//--------------//
	
	/**
	 * @return the relative file path.
	 * 
	 * */
	public String getDataFilePath() {
		return this.dataFileLocation + DEFAULT_FILE_NAME;
	}
	
	
	private JSONArray retrieveDataFromDataFile() {
		String dataFileName = getDataFilePath();
		JSONArray jsonTaskArray = new JSONArray();
	
		try {
			File dataFile = new File(dataFileName);
			if (!dataFile.exists()) {
				createNewFile(dataFileName);
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
				default:
					// invalid entry; ignore and continue to the next entry
					break;
			}
		}
	}
	
	//--------------//
	// File Writing //
	//--------------//
	
	public void writeToFile(GenericTaskList inputGenericTL, DeadlineTaskList inputDeadlineTL, TimedTaskList inputTimedTL) {
		try {
			String fileName = getDataFilePath();
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
		timedTaskList.add(timedTask);
	}

	private void addDeadlineTaskToList(JSONObject jsonTask) {
		DeadlineTask deadlineTask = new DeadlineTask ( 
											(String)jsonTask.get(JSON_KEY_NAME), 
											parseDate((String)jsonTask.get(JSON_KEY_DUEDATE))
										);
		deadlineTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		deadlineTask.setId((String)jsonTask.get(JSON_KEY_ID));
		deadlineTaskList.add(deadlineTask);
	}

	private void addGenericTaskToList(JSONObject jsonTask) {
		GenericTask genericTask = new GenericTask((String)jsonTask.get(JSON_KEY_NAME));
		genericTask.setWorkload(((Long)jsonTask.get(JSON_KEY_WORKLOAD)).intValue());
		genericTask.setId((String)jsonTask.get(JSON_KEY_ID));
		genericTaskList.add(genericTask);
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
		JSONObject jsonTask;
		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				DeadlineTask currDeadlineTask = deadlineTaskList.get(i);
				jsonTask = new JSONObject();
				
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
		JSONObject jsonTask;
		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				TimedTask currTimedTask = timedTaskList.get(i);
				jsonTask = new JSONObject();
				
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
