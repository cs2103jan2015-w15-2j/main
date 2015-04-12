/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.database.Reader;

public class Database {

	private static final String DATA_FILE_LOCATION = "Data file location";
	private static final String JSON_KEY_DATA_FILE_DIRECTORY = "Data file directory";
	private static final String DEFAULT_FILE_NAME = "input.json";
	private static final String DEFAULT_DATA_FILE_LOCATION = "SavedData/";
	private static final String CONFIG_FILE_LOCATION = "Config/";
	private static final String CONFIG_FILE_NAME = "simplify-config.json";

	private LogicRequest logicRequest = new LogicRequest();
	
	//------------------//
	// Class Attributes //
	//------------------//

	private File file;
	private State state;
	private String dataFileLocation;
	private ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();
<<<<<<< HEAD
	
	private ObservableList<GenericTask> resultantGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> resultantDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> resultantTimedTL = FXCollections.observableArrayList();
	
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
		saveState();
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
		saveState();
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
		saveState();
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
		saveState();
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
		saveState();
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
		state.saveState(getObservableGenericTL(), getObservableDeadlineTL(), getObservableTimedTL(), getArchivedGenericTL(), getArchivedDeadlineTL(), getArchivedTimedTL());
	}
	
	public void undoTask() {
		setArchivedTL(state.getArchivedGenericState(), state.getArchivedDeadlineState(), state.getArchivedTimedState());
		setObservableTL(state.getGenericState(), state.getDeadlineState(), state.getTimedState());
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}
	
	public void retrieveActiveTasklists() {
		setObservableTL(activeGenericTL, activeDeadlineTL, activeTimedTL);
	}
	
	public void searchDatabase(String[] parameter, boolean[] searchField) throws java.text.ParseException {
		if (parameter[1] != null) {
			searchInName(parameter[1]);
		} else if (parameter[2] != null) {
			searchForTime(parameter[2], searchField);
		} else {
			searchForWorkload(Integer.valueOf(parameter[4]));
		}
	}
	
	private void searchForTime(String dateInString, boolean[] searchField) throws java.text.ParseException {
		String pattern = "E MMM dd HH:mm:ss zzz yyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		DateTime date = new DateTime(format.parse(dateInString));
		Search search = new Search();
		resultantGenericTL.clear();
		resultantDeadlineTL.clear();
		resultantTimedTL.clear();
		int count = 0;
		
		for(boolean boo: searchField) {
			if (boo == true) {
				if (count == 0) {
					search.searchDeadlineByHour(date.getHourOfDay(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByHour(date.getHourOfDay(), observableTimedTL, resultantTimedTL);
				} else if (count == 1) {
					search.searchDeadlineByWeekday(date.getDayOfWeek(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByWeekday(date.getDayOfWeek(), observableTimedTL, resultantTimedTL);
				} else if (count == 2) {
					search.searchDeadlineByDay(date.getDayOfMonth(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByDay(date.getDayOfMonth(), observableTimedTL, resultantTimedTL);
				} else if (count == 3) {
					search.searchDeadlineByMonth(date.getMonthOfYear(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByMonth(date.getMonthOfYear(), observableTimedTL, resultantTimedTL);
				} else {
					search.searchDeadlineByYear(date.getYear(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByYear(date.getYear(), observableTimedTL, resultantTimedTL);
				}
			}
			count++;
		}
		setActiveTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);
	}
	
	private void searchInName(String term){
		Search search = new Search();
		resultantGenericTL.clear();
		resultantDeadlineTL.clear();
		resultantTimedTL.clear();
		search.searchDeadlineByName(term, observableDeadlineTL, resultantDeadlineTL);
		search.searchGenericByName(term, observableGenericTL, resultantGenericTL);
		search.searchTimedByName(term, observableTimedTL, resultantTimedTL);
		setActiveTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);
	}
	
	private void searchForWorkload(int workload){
		Search search = new Search();
		resultantGenericTL.clear();
		resultantDeadlineTL.clear();
		resultantTimedTL.clear();
		search.searchDeadlineByWorkload(workload, observableDeadlineTL, resultantDeadlineTL);
		search.searchGenericByWorkload(workload, observableGenericTL, resultantGenericTL);
		search.searchTimedByWorkload(workload, observableTimedTL, resultantTimedTL);
		setActiveTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);
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
	 * @param keyword toggles the sorting order of the task lists
	 * */
	public void toggleDisplay(String keyword) {
		if (keyword.equals("done")) {
			setActiveTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
			setObservableTL(archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
		} else {
			setObservableTL(activeGenericTL, activeDeadlineTL, activeTimedTL);
			if (keyword.equals("deadline")) {
				Collections.sort(observableTimedTL, taskStartTimeComparator);
				Collections.sort(observableDeadlineTL, taskDeadlineComparator);
				// test search
				/*boolean[] testBoolean = {false, false, true, false, false};
				String[] testString = {"Wed Apr 08 12:04:00 SGT 2015"};
				try {
					searchDatabase(testString, testBoolean);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			} else if (keyword.equals("workload")) {
				Collections.sort(observableGenericTL, taskWorkloadComparator);
				Collections.sort(observableTimedTL, taskWorkloadComparator);
				Collections.sort(observableDeadlineTL, taskWorkloadComparator);
			} else if (keyword.equals("default")){
				Collections.sort(observableGenericTL, taskIdComparator);
				Collections.sort(observableTimedTL, taskIdComparator);
				Collections.sort(observableDeadlineTL, taskIdComparator);
			} else {
				// no sorting of list is performed
			}
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
		saveState();
		logicRequest.modifyName(index, newName);
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadlineTL.size()) {
				DeadlineTask modifiedTask = observableDeadlineTL.remove(index-1);
				modifiedTask.setName(newName);
				observableDeadlineTL.add(index-1, modifiedTask);
			} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
				index = index - observableDeadlineTL.size();
				TimedTask modifiedTask = observableTimedTL.remove(index-1);
				modifiedTask.setName(newName);
				observableTimedTL.add(index-1, modifiedTask);
			} else {
				index = index - observableDeadlineTL.size() - observableTimedTL.size();
				GenericTask modifiedTask = observableGenericTL.remove(index-1);
				modifiedTask.setName(newName);
				observableGenericTL.add(index-1, modifiedTask);
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
		saveState();
		logicRequest.modifyWorkload(index, newWorkloadValue);
		if (newWorkloadValue > 5 || newWorkloadValue < 1) {
			throw new Exception("Invalid workload value entered. Supported workload values range from 1 to 5.");
		} else {
			if (index > this.totalSizeOfAllLists() || index < 1) {
				throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
			} else {
				if (index <= observableDeadlineTL.size()) {
					DeadlineTask modifiedTask = observableDeadlineTL.remove(index-1);
					modifiedTask.setWorkload(newWorkloadValue);
					observableDeadlineTL.add(index-1, modifiedTask);
				} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
					index = index - observableDeadlineTL.size();
					TimedTask modifiedTask = observableTimedTL.remove(index-1);
					modifiedTask.setWorkload(newWorkloadValue);
					observableTimedTL.add(index-1, modifiedTask);
				} else {
					index = index - observableDeadlineTL.size() - observableTimedTL.size();
					GenericTask modifiedTask = observableGenericTL.remove(index-1);
					modifiedTask.setWorkload(newWorkloadValue);
					observableGenericTL.add(index-1, modifiedTask);
				}
			}
		}
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL, archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}
	
	/**
	 * Modifies the start and/or time of a task.
	 * Convert deadline tasks to timed tasks when start or end time is introduced
	 * Convert timed tasks to deadline tasks when start or end time is removed
	 * Convert generic task to deadline or timed task when start and/or end time is introduced
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newStartTime new value of start time to be written to the task
	 * @param newEndTime new value of end time to be written to the task
	 * @throws Exception when start time is later than end time
	 * */
	public void modifyStartEnd(int index, Date newStartTime, Date newEndTime) throws Exception {
		saveState();
		// logicRequest.modifyStartTime(index, newStartTime);
		assert index > 1;
		assert index < this.totalSizeOfAllLists();
		
		DateTime startTime = new DateTime(newStartTime);
		DateTime endTime = new DateTime(newEndTime);
		
		if (index <= observableDeadlineTL.size()) {
			if (newStartTime.equals(newEndTime)) {
				DeadlineTask modifiedTask = observableDeadlineTL.remove(index-1);
				modifiedTask.setDeadline(newStartTime);
				observableDeadlineTL.add(index-1, modifiedTask);
			} else if (newStartTime.before(newEndTime)){
				DeadlineTask task = observableDeadlineTL.get(index - 1);
				observableTimedTL.add(new TimedTask(task.getNameAsStringProperty(), startTime, endTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableDeadlineTL.remove(index - 1);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}
		} else if (index - observableDeadlineTL.size() <= observableTimedTL.size()) {
			index = index - observableDeadlineTL.size();
			TimedTask task = observableTimedTL.get(index - 1);
			if (newStartTime.equals(newEndTime)) {
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), startTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableTimedTL.remove(index - 1);
			} else if (newStartTime.before(newEndTime)) {
				TimedTask modifiedTask = observableTimedTL.remove(index-1);
				modifiedTask.setStartTime(newStartTime);
				modifiedTask.setEndTime(newEndTime);
				observableTimedTL.add(index-1, modifiedTask);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}
		} else {
			index = index - observableDeadlineTL.size() - observableTimedTL.size();
			GenericTask task = observableGenericTL.get(index - 1);
			if (newStartTime.equals(newEndTime)) {
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), newStartTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableGenericTL.remove(index - 1);
			} else if (newStartTime.before(newEndTime)) {
				observableTimedTL.add(new TimedTask(task.getNameAsStringProperty(), startTime, endTime, task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableGenericTL.remove(index - 1);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
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
	
	public void markTaskDone(int indexToMarkDone) {
		saveState();
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
	
	private void setObservableTL(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL, ObservableList<TimedTask> timedTL) {
		observableDeadlineTL.setAll(deadlineTL);
		observableTimedTL.setAll(timedTL);
		observableGenericTL.setAll(genericTL);
	}
	
	private void setActiveTL(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL, ObservableList<TimedTask> timedTL) {
		activeDeadlineTL.setAll(deadlineTL);
		activeTimedTL.setAll(timedTL);
		activeGenericTL.setAll(genericTL);
	}
	
	private void setArchivedTL(ObservableList<GenericTask>genericTL, ObservableList<DeadlineTask> deadlineTL, ObservableList<TimedTask> timedTL) {
		archivedDeadlineTL.setAll(deadlineTL);
		archivedTimedTL.setAll(timedTL);
		archivedGenericTL.setAll(genericTL);
	}
	
	//-------------//
	// Comparators //
	//-------------//
	
	private Comparator<GenericTask> taskWorkloadComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	private Comparator<GenericTask> taskIdComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			DateTime t1Id = t1.getIDAsDT();
			DateTime t2Id = t2.getIDAsDT();
			if(t1Id.isBefore(t2Id)) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	
	private Comparator<DeadlineTask> taskDeadlineComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			DateTime t1Deadline = t1.getDeadline();
			DateTime t2Deadline = t2.getDeadline();
			if(t1Deadline.isBefore(t2Deadline)) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	
	private Comparator<TimedTask> taskStartTimeComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			DateTime t1StartTime = t1.getStartTimeAsDT();
			DateTime t2StartTime = t2.getStartTimeAsDT();
			if(t1StartTime.isBefore(t2StartTime)) {
				return -1;
			} else {
				return 1;
			}
		}
	};
=======
>>>>>>> Qian-database

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
		state = new State();
	}

	//----------------//
	// Initialization //
	//----------------//

	public void initDatabase() throws IOException {
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
	public State getState() {
		return this.state;
	}

	/**
	 * @return the relative file path.
	 * */
	public String getDataFilePath() {
		return this.dataFileLocation + DEFAULT_FILE_NAME;
	}

	/**
	 * @return the location (directory) of the file.
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

	public void setDataFileLocation(String newFileLocation) throws IOException {
				
		file = new File(newFileLocation + DEFAULT_FILE_NAME);
		file.mkdirs();
		file.createNewFile();
		dataFileLocation = newFileLocation;
		
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
	 * @throws IOException 
	 * */
	private void revertToDefaultSettings() throws IOException {
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
		configJson.put(DATA_FILE_LOCATION, fileLocation);
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
}