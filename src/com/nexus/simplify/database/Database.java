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
	private ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	
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
			observableTimedTL.add(new TimedTask(name, startTime, endTime));
		} else {
			observableTimedTL.add(new TimedTask(name, startTime, endTime, workload));
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
			observableDeadlineTL.add(new DeadlineTask(name, deadline));
		} else {
			observableDeadlineTL.add(new DeadlineTask(name, deadline, workload));
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
			observableGenericTL.add(new GenericTask(name));
		} else {
			observableGenericTL.add(new GenericTask(name, workload));
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
			if (index <= observableDeadlineTL.size()) {
				observableDeadlineTL.remove(index - 1);
			} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
				index = index - observableDeadlineTL.size();
				observableTimedTL.remove(index - 1);
			} else {
				index = index - observableDeadlineTL.size() - observableTimedTL.size();
				observableGenericTL.remove(index - 1);
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}
	
	public void clearContent() {
		logicRequest.clearContent();
		observableGenericTL.clear();
		observableTimedTL.clear();
		observableDeadlineTL.clear();
		archivedGenericTL.clear();
		archivedTimedTL.clear();
		archivedDeadlineTL.clear();
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
	public int totalSizeOfAllLists() {
		return observableGenericTL.size() + observableDeadlineTL.size() + observableTimedTL.size();
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
			if (index <= observableDeadlineTL.size()) {
				observableDeadlineTL.get(index - 1).setName(newName);
			} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
				index = index - observableDeadlineTL.size();
				observableTimedTL.get(index - 1).setName(newName);
			} else {
				index = index - observableDeadlineTL.size() - observableTimedTL.size();
				observableGenericTL.get(index - 1).setName(newName);
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
				if (index <= observableDeadlineTL.size()) {
					observableDeadlineTL.get(index - 1).setWorkload(newWorkloadValue);
				} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
					index = index - observableDeadlineTL.size();
					observableTimedTL.get(index - 1).setWorkload(newWorkloadValue);
				} else {
					index = index - observableDeadlineTL.size() - observableTimedTL.size();
					observableGenericTL.get(index - 1).setWorkload(newWorkloadValue);
				}
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
			if (index <= observableDeadlineTL.size()) {
				observableDeadlineTL.get(index - 1).setDeadline(newStartTime);
			} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
				index = index - observableDeadlineTL.size();
				observableTimedTL.get(index - 1).setStartTime(newStartTime);
			} else {
				index = index - observableDeadlineTL.size() - observableTimedTL.size();
				GenericTask task = observableGenericTL.get(index - 1);
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), newStartTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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
			if (index <= observableDeadlineTL.size()) {
				observableDeadlineTL.get(index - 1).setDeadline(newEndTime);
			} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
				index = index - observableDeadlineTL.size();
				observableTimedTL.get(index - 1).setEndTime(newEndTime);
			} else {
				index = index - observableDeadlineTL.size() - observableTimedTL.size();
				GenericTask task = observableGenericTL.get(index - 1);
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), newEndTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}
	
	public void markTaskDone(int indexToMarkDone) {
		logicRequest.markTaskDone(indexToMarkDone);
		// TODO Auto-generated method stub
		if (indexToMarkDone > this.totalSizeOfAllLists() || indexToMarkDone < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (indexToMarkDone <= observableDeadlineTL.size()) {
				archivedDeadlineTL.add(observableDeadlineTL.get(indexToMarkDone - 1));
				observableDeadlineTL.remove(indexToMarkDone - 1);
			} else if (indexToMarkDone - observableDeadlineTL.size() <= observableTimedTL.size()) {
				indexToMarkDone = indexToMarkDone - observableDeadlineTL.size();
				archivedTimedTL.add(observableTimedTL.get(indexToMarkDone - 1));
				observableTimedTL.remove(indexToMarkDone - 1);
			} else {
				indexToMarkDone = indexToMarkDone - observableDeadlineTL.size() - observableTimedTL.size();
				archivedGenericTL.add(observableGenericTL.get(indexToMarkDone - 1));
				observableGenericTL.remove(indexToMarkDone - 1);
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
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

	public void initDatabase() {
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
	
	public ObservableList<GenericTask> getObservableGenericTL() {
		return this.observableGenericTL;
	}
	
	public ObservableList<TimedTask> getObservableTimedTL() {
		return this.observableTimedTL;
	}
	
	public ObservableList<DeadlineTask> getObservableDeadlineTL() {
		return this.observableDeadlineTL;
	}
	
	public ObservableList<GenericTask> getArchivedGenericTL() {
		return this.archivedGenericTL;
	}
	
	public ObservableList<TimedTask> getArchivedTimedTL() {
		return this.archivedTimedTL;
	}
	
	public ObservableList<DeadlineTask> getArchivedDeadlineTL() {
		return this.archivedDeadlineTL;
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
	 * @return the LogicRequest object of this Database
	 */
	public LogicRequest getLogicRequest() {
		return logicRequest;
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void setObservableGenericTL(ObservableList<GenericTask> generic) {
		this.observableGenericTL = generic;
	}
	
	public void setObservableTimedTL(ObservableList<TimedTask> timed) {
		this.observableTimedTL = timed;
	}
	
	public void setObservableDeadlineTL(ObservableList<DeadlineTask> deadline) {
		this.observableDeadlineTL = deadline;
	}
	
	public void setArchivedGenericTL(ObservableList<GenericTask> generic) {
		this.archivedGenericTL = generic;
	}
	
	public void setArchivedTimedTL(ObservableList<TimedTask> timed) {
		this.archivedTimedTL = timed;
	}
	
	public void setArchivedDeadlineTL(ObservableList<DeadlineTask> deadline) {
		this.archivedDeadlineTL = deadline;
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