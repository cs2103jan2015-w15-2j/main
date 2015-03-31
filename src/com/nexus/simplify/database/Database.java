/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.util.*;
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

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.database.Writer;
import com.nexus.simplify.database.Reader;
import com.nexus.simplify.database.State;

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
	
	Writer writer = new Writer(this);
	State state;
	LogicRequest logicRequest = new LogicRequest();
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private String dataFileLocation;
	private ObservableList<GenericTask> observableGeneric = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadline = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimed = FXCollections.observableArrayList();
	
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
		logicRequest.addTimedTask(name, startTime, endTime, workload);
		if (workload == 0) {
			observableTimed.add(new TimedTask(name, startTime, endTime));
		} else {
			observableTimed.add(new TimedTask(name, startTime, endTime, workload));
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	/**
	 * Adds a new deadline-based task to the list of deadline-based tasks.
	 * 
	 * @param name name of task
	 * @param deadline time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addDeadlineTask(String name, Date deadline, int workload) {
		logicRequest.addDeadlineTask(name, deadline, workload);
		if (workload == 0) {
			observableDeadline.add(new DeadlineTask(name, deadline));
		} else {
			observableDeadline.add(new DeadlineTask(name, deadline, workload));
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	/**
	 * Adds a new generic (floating) task to the list of generic tasks.
	 * 
	 * @param name name of task
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addGenericTask(String name, int workload) {
		logicRequest.addGenericTask(name, workload);
		if (workload == 0) {
			observableGeneric.add(new GenericTask(name));
		} else {
			observableGeneric.add(new GenericTask(name, workload));
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	
	/**
	 * Deletes a task from the table based on its index being displayed on the billboard.
	 * 
	 * @param index index of task with respect to the billboard
	 * @throws IndexOutOfBoundsException if index is not within range of 1 - 15 inclusive.
	 * */
	public void deleteTaskByIndex(int index) throws IndexOutOfBoundsException {
		logicRequest.deleteTaskByIndex(index);
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadline.size()) {
				observableDeadline.remove(index - 1);
			} else if (index - observableDeadline.size() <= observableTimed.size()) {
				index = index - observableDeadline.size();
				observableTimed.remove(index - 1);
			} else {
				index = index - observableDeadline.size() - observableTimed.size();
				observableGeneric.remove(index - 1);
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	public void clearContent() {
		logicRequest.clearContent();
		observableGeneric.clear();
		observableTimed.clear();
		observableDeadline.clear();
	}
	
	private void saveState() {
		state.saveGenericState();
		state.saveDeadlineState();
		state.saveTimedState();
	}
	
	public void undoTask() {
		state.loadDeadlineState();
		state.loadTimedState();
		state.loadGenericState();
	}
	
	public void searchDatabase(String[] parameter, boolean[] searchField) {
		
	}
	
	/**
	 * returns the total size of all three task lists.
	 * 
	 * @return total size of all three task lists
	 * */
	private int totalSizeOfAllLists() {
		return observableGeneric.size() + observableDeadline.size() + observableTimed.size();
	}
	
	/**
	 * toggles the sorting order of the task lists by a keyword
	 * 
	 * @param option toggles the sorting order of the task lists
	 * */
	public void toggleDisplay(String option) {
		if (option.equals("deadline")) {
			// deadlineTaskList.sortBy(observableDeadline.getSortType(option));
			// observableTimed.sortBy(observableTimed.getSortType(option));
		} else {
			// observableGeneric.sortBy(observableGeneric.getSortType(option));
			// deadlineTaskList.sortBy(observableDeadline.getSortType(option));
			// observableTimed.sortBy(observableTimed.getSortType(option));
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
		logicRequest.modifyName(index, newName);
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadline.size()) {
				observableDeadline.get(index - 1).setName(newName);
			} else if (index - observableDeadline.size() <= observableTimed.size()) {
				index = index - observableDeadline.size();
				observableTimed.get(index - 1).setName(newName);
			} else {
				index = index - observableDeadline.size() - observableTimed.size();
				observableGeneric.get(index - 1).setName(newName);
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
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
		logicRequest.modifyWorkload(index, newWorkloadValue);
		if (newWorkloadValue > 5 || newWorkloadValue < 1) {
			throw new Exception("Invalid workload value entered. Supported workload values range from 1 to 5.");
		} else {
			if (index > this.totalSizeOfAllLists() || index < 1) {
				throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
			} else {
				if (index <= observableDeadline.size()) {
					observableDeadline.get(index - 1).setWorkload(newWorkloadValue);
				} else if (index - observableDeadline.size() <= observableTimed.size()) {
					index = index - observableDeadline.size();
					observableTimed.get(index - 1).setWorkload(newWorkloadValue);
				} else {
					index = index - observableDeadline.size() - observableTimed.size();
					observableGeneric.get(index - 1).setWorkload(newWorkloadValue);
				}
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
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
		logicRequest.modifyStartTime(index, newStartTime);
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadline.size()) {
				observableDeadline.get(index - 1).setDeadline(newStartTime);
			} else if (index - observableDeadline.size() <= observableTimed.size()) {
				index = index - observableDeadline.size();
				observableTimed.get(index - 1).setStartTime(newStartTime);
			} else {
				index = index - observableDeadline.size() - observableTimed.size();
				GenericTask task = observableGeneric.get(index - 1);
				observableDeadline.add(new DeadlineTask(task.getNameAsStringProperty(), newStartTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
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
		logicRequest.modifyEndTime(index, newEndTime);
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadline.size()) {
				observableDeadline.get(index - 1).setDeadline(newEndTime);
			} else if (index - observableDeadline.size() <= observableTimed.size()) {
				index = index - observableDeadline.size();
				observableTimed.get(index - 1).setEndTime(newEndTime);
			} else {
				index = index - observableDeadline.size() - observableTimed.size();
				GenericTask task = observableGeneric.get(index - 1);
				observableDeadline.add(new DeadlineTask(task.getNameAsStringProperty(), newEndTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	public void markTaskDone(int indexToMarkDone) {
		logicRequest.markTaskDone(indexToMarkDone);
		// TODO Auto-generated method stub
		
	}
	
	// check if there is missing backslash
	public void modifyFileLocation(String newFileLocation) {
		this.setDataFileLocation(newFileLocation);
		storeSettingsIntoConfigFile(newFileLocation);
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
		Reader reader = new Reader(this);
		JSONArray jsonTaskArray = reader.retrieveDataFromDataFile(getDataFilePath());
		reader.populateTaskLists(jsonTaskArray);
		state = new State(this);
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
	
	public ObservableList<GenericTask> getObservableGeneric() {
		return this.observableGeneric;
	}
	
	public ObservableList<TimedTask> getObservableTimed() {
		return this.observableTimed;
	}
	
	public ObservableList<DeadlineTask> getObservableDeadline() {
		return this.observableDeadline;
	}
	
	/**
	 * @return the relative file path.
	 * 
	 * */
	public String getDataFilePath() {
		return this.dataFileLocation + DEFAULT_FILE_NAME;
	}
	
	/**
	 * @return the location (directory) of the file.
	 * 
	 * */
	public String getDataFileLocation() {
		return this.dataFileLocation;
	}
	
	/**
	 * Returns the LogicRequest object of this Database
	 @return 
	 */
	public LogicRequest getLogicRequest() {
		return logicRequest;
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void setObservableGeneric(ObservableList<GenericTask> generic) {
		this.observableGeneric = generic;
	}
	
	public void setObservableTimed(ObservableList<TimedTask> timed) {
		this.observableTimed = timed;
	}
	
	public void setObservableDeadline(ObservableList<DeadlineTask> deadline) {
		this.observableDeadline = deadline;
	}
	
	public void setDataFileLocation(String newFileLocation) {
		this.dataFileLocation = newFileLocation;
	}
	
	/**
	 * creates a new file for the program.
	 * 
	 * @param fileName name of the file
	 * */
	public void createNewFile(String fileName) {
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
	
	//-----------------//
	// File Processing //
	//-----------------//
	
	/**
	 * retrieves the data file location from the 
	 * configuration file.
	 * */
	public void retrieveSettingsFromConfigFile() {
		JSONObject configJson = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
			String configFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
			Object object = jsonParser.parse(new FileReader(configFilePath));
			configJson = (JSONObject) object;
			
			if (!configJson.containsKey(JSON_KEY_DATA_FILE_DIRECTORY)) {
				revertToDefaultSettings();
			} else {
				this.setDataFileLocation(String.valueOf(configJson.get(JSON_KEY_DATA_FILE_DIRECTORY)));
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
		this.setDataFileLocation(DEFAULT_DATA_FILE_LOCATION);
		storeSettingsIntoConfigFile(DEFAULT_DATA_FILE_LOCATION);
	}
	
	/**
	 * @param configFileName name of program configuration file
	 * @return true if config file exists, false otherwise.
	 * */
	public boolean configFileExists(String configFileName) {
		File configFile = new File(configFileName);
		return configFile.exists();
	}

	/**
	 * 
	 * 
	 * */
	@SuppressWarnings("unchecked") 
	private void storeSettingsIntoConfigFile(String fileLocation) {
		JSONObject configJson = new JSONObject();
		configJson.put("data file location", fileLocation);
		String outputConfigFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
		// File outputConfigFile = new File(outputConfigFilePath);
		try {
			FileWriter fileWriter = new FileWriter(outputConfigFilePath);
			fileWriter.write(configJson.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Database database = new Database();
		} catch(IOException e) {
		}
	}
}