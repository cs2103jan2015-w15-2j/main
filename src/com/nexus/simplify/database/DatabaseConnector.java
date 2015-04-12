package com.nexus.simplify.database;

import java.util.*;
import java.io.IOException;
import java.text.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.joda.time.DateTime;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.database.Writer;

public class DatabaseConnector implements IDatabaseConnector {

	//-----------------//
	// Class Variables //
	//-----------------//
	
	private Database database;
	private Search search = new Search();
	private State state;
	private Writer writer;	
	private LogicRequest logicRequest = new LogicRequest();

	private ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> resultantGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> resultantDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> resultantTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> temporaryGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> temporaryDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> temporaryTimedTL = FXCollections.observableArrayList();

	//-------------//
	// Constructor //
	//-------------//
	
	public DatabaseConnector() {
		
		this.database = MainApp.getDatabase();
		state = database.getState();
		writer = new Writer(database);
		archivedGenericTL = database.getArchivedGenericTL();
		archivedDeadlineTL = database.getArchivedDeadlineTL();
		archivedTimedTL = database.getArchivedTimedTL();
		observableGenericTL = database.getObservableGenericTL();
		observableDeadlineTL = database.getObservableDeadlineTL();
		observableTimedTL = database.getObservableTimedTL();
		
	}
	
	public DatabaseConnector(Database database) {
		
		this.database = database;
		state = database.getState();
		writer = new Writer(database);
		archivedGenericTL = database.getArchivedGenericTL();
		archivedDeadlineTL = database.getArchivedDeadlineTL();
		archivedTimedTL = database.getArchivedTimedTL();
		observableGenericTL = database.getObservableGenericTL();
		observableDeadlineTL = database.getObservableDeadlineTL();
		observableTimedTL = database.getObservableTimedTL();
	
	}
	
	//---------------//
	// API for Logic //
	//---------------//
	
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

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Adds a new timed task to the list of timed tasks.
	 * 
	 * @param name name of task
	 * @param startTime time and date when task starts
	 * @param endTime time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addTimedTask(String name, Date startTime, Date endTime, int workload) throws Exception {

		saveState();
		logicRequest.addTimedTask(name, startTime, endTime, workload);
		
		boolean isStartEqualEnd = startTime.equals(endTime);
		boolean isStartBeforeEnd = startTime.before(endTime);

		if (workload == 0) {
			if (isStartEqualEnd) {
				observableDeadlineTL.add(new DeadlineTask(name, startTime));
			} else if (isStartBeforeEnd) {
				observableTimedTL.add(new TimedTask(name, startTime, endTime));
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}
		} else {
			if (isStartEqualEnd) {
				observableDeadlineTL.add(new DeadlineTask(name, startTime, workload));
			} else if (isStartBeforeEnd) {
				observableTimedTL.add(new TimedTask(name, startTime, endTime, workload));
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}
		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

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

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}

	/**
	 * Deletes a task from the table based on its index being displayed on the billboard.
	 * 
	 * @param index index of task with respect to the billboard
	 * */
	public void deleteTaskByIndex(int index) {

		saveState();
		logicRequest.deleteTaskByIndex(index);

		assert index > 0;
		assert index < this.totalSizeOfAllLists();

		int deadlineTLSize = observableDeadlineTL.size();
		int timedTLSize = observableTimedTL.size();

		if (index <= deadlineTLSize) {
			observableDeadlineTL.remove(index - 1);
		} else if (index - deadlineTLSize <= timedTLSize) {
			index = index - deadlineTLSize;
			observableTimedTL.remove(index - 1);
		} else {
			index = index - deadlineTLSize - timedTLSize;
			observableGenericTL.remove(index - 1);
		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Clears all content from all observable and archived lists.
	 */
	public void clearContent() {

		saveState();
		logicRequest.clearContent();

		observableGenericTL.clear();
		observableTimedTL.clear();
		observableDeadlineTL.clear();
		archivedGenericTL.clear();
		archivedTimedTL.clear();
		archivedDeadlineTL.clear();

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Undo the previous command that the user has called.
	 * */
	public void undoTask() {

		setArchivedTL(state.getArchivedGenericState(), state.getArchivedDeadlineState(),
				state.getArchivedTimedState());
		setObservableTL(state.getGenericState(), state.getDeadlineState(), state.getTimedState());

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Sets the observable task lists back to the active/default after performing
	 * search or display done method.
	 * 
	 * */
	public void retrieveActiveTasklists() {
		setObservableTL(temporaryGenericTL, temporaryDeadlineTL, temporaryTimedTL);
	}

	/**
	 * Searchs using user-defined terms for relevant tasks in task lists.
	 * 
	 * @param parameter string array that contain terms to search for
	 * @param searchForTimeUnit boolean array that indicates time unit to search for 
	 * */
	public void searchDatabase(String[] parameter, boolean[] searchForTimeUnit) throws java.text.ParseException {

		resultantGenericTL.clear();
		resultantDeadlineTL.clear();
		resultantTimedTL.clear();

		if (parameter[1] != null) {
			searchInName(parameter[1]);
		} else if (parameter[2] != null) {
			searchForTime(parameter[2], searchForTimeUnit);
		} else {
			searchForWorkload(Integer.valueOf(parameter[4]));
		}

	}

	/**
	 * Toggles the sorting order of the task lists by a keyword.
	 * 
	 * @param keyword toggles the sorting order of the task lists
	 * */
	public void toggleDisplay(String keyword) {

		if (keyword.equals("done")) {
			setTemporaryTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
			setObservableTL(archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
		} else {
			setObservableTL(temporaryGenericTL, temporaryDeadlineTL, temporaryTimedTL);
			if (keyword.equals("deadline")) {
				Collections.sort(observableTimedTL, taskStartTimeComparator);
				Collections.sort(observableDeadlineTL, taskDeadlineComparator);
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
	 * */
	public void modifyName(int index, String newName) {

		saveState();
		logicRequest.modifyName(index, newName);

		assert index > 0;
		assert index < this.totalSizeOfAllLists();

		int deadlineTLSize = observableDeadlineTL.size();
		int timedTLSize = observableTimedTL.size();

		if (index <= deadlineTLSize) {
			observableDeadlineTL.get(index - 1).setName(newName);
		} else if (index - deadlineTLSize <= timedTLSize) {
			index = index - deadlineTLSize;
			observableTimedTL.get(index - 1).setName(newName);
		} else {
			index = index - deadlineTLSize - timedTLSize;
			observableGenericTL.get(index - 1).setName(newName);
		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Modifies the workload value of a task.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newWorkloadValue new value of workload to be written to the task
	 * @throws Exception if workload is not in range of 1 - 5 inclusive.
	 * */
	public void modifyWorkload(int index, int newWorkloadValue) {

		saveState();
		logicRequest.modifyWorkload(index, newWorkloadValue);

		assert index > 0;
		assert index < this.totalSizeOfAllLists();

		int deadlineTLSize = observableDeadlineTL.size();
		int timedTLSize = observableTimedTL.size();

		if (index <= deadlineTLSize) {
			observableDeadlineTL.get(index - 1).setWorkload(newWorkloadValue);
		} else if (index - deadlineTLSize <= timedTLSize) {
			index = index - deadlineTLSize;
			observableTimedTL.get(index - 1).setWorkload(newWorkloadValue);
		} else {
			index = index - deadlineTLSize - timedTLSize;
			observableGenericTL.get(index - 1).setWorkload(newWorkloadValue);
		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}

	/**
	 * Modifies the start and/or time of a task.
	 * Convert deadline tasks to timed tasks when start or end time is introduced.
	 * Convert timed tasks to deadline tasks when start or end time is removed.
	 * Convert generic task to deadline or timed task when start and/or end time is introduced.
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
		int deadlineTLSize = observableDeadlineTL.size();
		int timedTLSize = observableTimedTL.size();
		boolean isStartEqualEnd = newStartTime.equals(newEndTime);
		boolean isStartBeforeEnd = newStartTime.before(newEndTime);

		if (index <= deadlineTLSize) {

			if (isStartEqualEnd) {
				observableDeadlineTL.get(index - 1).setDeadline(newStartTime);
			} else if (isStartBeforeEnd){
				DeadlineTask task = observableDeadlineTL.get(index - 1);
				observableTimedTL.add(new TimedTask(task.getNameAsStringProperty(), startTime, endTime,
						task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableDeadlineTL.remove(index - 1);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}

		} else if (index - deadlineTLSize <= timedTLSize) {

			index = index - deadlineTLSize;
			TimedTask task = observableTimedTL.get(index - 1);
			if (isStartEqualEnd) {
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), startTime,
						task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableTimedTL.remove(index - 1);
			} else if (isStartBeforeEnd) {
				task.setStartTime(newStartTime);
				task.setEndTime(newEndTime);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}

		} else {

			index = index - deadlineTLSize - timedTLSize;
			GenericTask task = observableGenericTL.get(index - 1);
			if (isStartEqualEnd) {
				observableDeadlineTL.add(new DeadlineTask(task.getNameAsStringProperty(), newStartTime,
						task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableGenericTL.remove(index - 1);
			} else if (isStartBeforeEnd) {
				observableTimedTL.add(new TimedTask(task.getNameAsStringProperty(), startTime, endTime,
						task.getWorkloadAsIntegerProperty(), task.getIDAsStringProperty()));
				observableGenericTL.remove(index - 1);
			} else {
				throw new Exception("Please provide a start time that is earlier than the end time.");
			}

		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);

	}

	/**
	 * Marks the task at index as done, remove from observable task lists
	 * and add to the archived task lists.
	 * 
	 * @param indexToMarkDone index of task to be marked as done
	 * */
	public void markTaskDone(int indexToMarkDone) {

		saveState();
		logicRequest.markTaskDone(indexToMarkDone);

		assert indexToMarkDone > 0;
		assert indexToMarkDone < this.totalSizeOfAllLists();

		int deadlineTLSize = observableDeadlineTL.size();
		int timedTLSize = observableTimedTL.size();

		if (indexToMarkDone <= deadlineTLSize) {
			archivedDeadlineTL.add(observableDeadlineTL.get(indexToMarkDone - 1));
			observableDeadlineTL.remove(indexToMarkDone - 1);
		} else if (indexToMarkDone - deadlineTLSize <= timedTLSize) {
			indexToMarkDone = indexToMarkDone - deadlineTLSize;
			archivedTimedTL.add(observableTimedTL.get(indexToMarkDone - 1));
			observableTimedTL.remove(indexToMarkDone - 1);
		} else {
			indexToMarkDone = indexToMarkDone - deadlineTLSize - timedTLSize;
			archivedGenericTL.add(observableGenericTL.get(indexToMarkDone - 1));
			observableGenericTL.remove(indexToMarkDone - 1);
		}

		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}

	// check if there is missing backslash
	public void modifyFileLocation(String newFileLocation) throws IOException {
		database.setDataFileLocation(newFileLocation);
	}
	
	public String getDataFileLocation() {
		return database.getDataFileLocation();
	}

	/**
	 * Searches for the given time unit in dates of tasks and display all tasks that contain
	 * the time unit (except for Generic Task).
	 * 
	 * @param search search instance
	 * @param dateInString date with time unit to search for included
	 * @param searchForTimeUnit boolean array with time unit to search for indicated with true
	 * */
	private void searchForTime(String dateInString, boolean[] searchForTimeUnit)
			throws java.text.ParseException {

		String pattern = "E MMM dd HH:mm:ss zzz yyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		DateTime date = new DateTime(format.parse(dateInString));
		int count = 0;

		for(boolean boo: searchForTimeUnit) {

			if (boo == true) {

				if (count == 0) {
					search.searchDeadlineByHour(date.getHourOfDay(), observableDeadlineTL,
							resultantDeadlineTL);
					search.searchTimedByHour(date.getHourOfDay(), observableTimedTL, resultantTimedTL);
				} else if (count == 1) {
					search.searchDeadlineByWeekday(date.getDayOfWeek(), observableDeadlineTL,
							resultantDeadlineTL);
					search.searchTimedByWeekday(date.getDayOfWeek(), observableTimedTL, resultantTimedTL);
				} else if (count == 2) {
					search.searchDeadlineByDay(date.getDayOfMonth(), observableDeadlineTL,
							resultantDeadlineTL);
					search.searchTimedByDay(date.getDayOfMonth(), observableTimedTL, resultantTimedTL);
				} else if (count == 3) {
					search.searchDeadlineByMonth(date.getMonthOfYear(), observableDeadlineTL,
							resultantDeadlineTL);
					search.searchTimedByMonth(date.getMonthOfYear(), observableTimedTL, resultantTimedTL);
				} else {
					search.searchDeadlineByYear(date.getYear(), observableDeadlineTL, resultantDeadlineTL);
					search.searchTimedByYear(date.getYear(), observableTimedTL, resultantTimedTL);
				}

			}

			count++;

		}

		setTemporaryTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);

	}

	/**
	 * Searches for the given term in the names of tasks and display all tasks that contains
	 * the term.
	 * 
	 * @param search search instance
	 * @param term term to search for in names of tasks
	 * */
	private void searchInName(String term){

		search.searchDeadlineByName(term, observableDeadlineTL, resultantDeadlineTL);
		search.searchGenericByName(term, observableGenericTL, resultantGenericTL);
		search.searchTimedByName(term, observableTimedTL, resultantTimedTL);

		setTemporaryTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);

	}

	/**
	 * Searches for the given workload of tasks and display all tasks that contains the
	 * workload.
	 * 
	 * @param search search instance
	 * @param workload workload to search for
	 * */
	private void searchForWorkload(int workload){

		search.searchDeadlineByWorkload(workload, observableDeadlineTL, resultantDeadlineTL);
		search.searchGenericByWorkload(workload, observableGenericTL, resultantGenericTL);
		search.searchTimedByWorkload(workload, observableTimedTL, resultantTimedTL);

		setTemporaryTL(observableGenericTL, observableDeadlineTL, observableTimedTL);
		setObservableTL(resultantGenericTL, resultantDeadlineTL, resultantTimedTL);

	}

	/**
	 * Returns the total size of all three task lists.
	 * 
	 * @return total size of all three task lists
	 * */
	private int totalSizeOfAllLists() {
		return observableGenericTL.size() + observableDeadlineTL.size() + observableTimedTL.size();
	}

	/**
	 * Saves the state of all observable and archived lists to facilitate undo method.
	 * */
	private void saveState() {
		state.saveState(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL);
	}
	
	//------------------//
	// Variable Setters //
	//------------------//

	private void setObservableTL(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL,
			ObservableList<TimedTask> timedTL) {
		
		observableDeadlineTL.setAll(deadlineTL);
		observableTimedTL.setAll(timedTL);
		observableGenericTL.setAll(genericTL);
	}

	private void setTemporaryTL(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL,
			ObservableList<TimedTask> timedTL) {
		
		temporaryDeadlineTL.setAll(deadlineTL);
		temporaryTimedTL.setAll(timedTL);
		temporaryGenericTL.setAll(genericTL);
	}

	private void setArchivedTL(ObservableList<GenericTask>genericTL, ObservableList<DeadlineTask> deadlineTL,
			ObservableList<TimedTask> timedTL) {
		
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
}
