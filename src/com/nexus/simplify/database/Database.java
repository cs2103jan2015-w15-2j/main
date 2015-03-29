/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.util.*;
import java.io.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.*;

import com.nexus.simplify.database.*;

@SuppressWarnings("unused")

public class Database implements IDatabase {
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "MMM dd HH:mm";
	private static final String MSG_INDEX_OOR = "Index is out of range.";
	private static final String MSG_INVALID_WORKLOAD = "Invalid workload value entered. Supported workload values range from 1 to 5.";
	private static final String FILENAME = "input.json";
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private File file;
	private ArrayList<String> tempList = new ArrayList<String>();
	private GenericTaskList genericTaskList = new GenericTaskList();
	private TimedTaskList timedTaskList = new TimedTaskList();
	private DeadlineTaskList deadlineTaskList = new DeadlineTaskList();
	private GenericTaskList resultantGenericTL = new GenericTaskList();
	private DeadlineTaskList resultantDeadlineTL = new DeadlineTaskList();
	private TimedTaskList resultantTimedTL = new TimedTaskList();
		
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
		setUpFile(FILENAME);
		readFromFile();
	}
	
	//----------------//
	// Initialization //
	//----------------//
	
	/**
	 * initializes the data base by opening the input file 
	 * and fetching data from it.
	 * 
	 * @param fileName name of input file to be opened
	 * @throws IOException for an interrupted IO operation.
	 * 
	 * */
	private void setUpFile(String fileName) throws IOException {
		// String dirName = "somestring";
		// File dir = new File(dirName);
		// File actualFile = new File(dir, fileName);
		file = new File(fileName);
		if (file.exists()) {
			getDataFromFile();
			readFromFile();
		} else {
			file.createNewFile();
		}
	}
	
	//--------------//
	// File Reading //
	//--------------//
	
	public void readFromFile() {
		if (tempList.isEmpty()) {
			genericTaskList = new GenericTaskList();
			deadlineTaskList = new DeadlineTaskList();
			timedTaskList = new TimedTaskList();
		} else {
			genericTaskList = getGenericTLFromData(tempList);
			timedTaskList = getTimedTLFromData(tempList);
			deadlineTaskList = getDeadlineTLFromData(tempList);
		}
	}
	
	private void getDataFromFile() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String text;
			while ((text = br.readLine()) != null) {
				tempList.add(text);
			}
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	//--------------//
	// File Writing //
	//--------------//
	
	public void writeToFile(GenericTaskList inputGenericTL, DeadlineTaskList inputDeadlineTL, TimedTaskList inputTimedTL) {
		try {
			String fileName = file.getName();
			file.delete();
			file = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
			tempList.clear();
			convertToStore(inputDeadlineTL);
			convertToStore(inputTimedTL);
			convertToStore(inputGenericTL); 
			if (!tempList.isEmpty()) {
				for (int i = 0; i < tempList.size(); i++) {
					bw.write(tempList.get(i));
					bw.newLine();
				}
			}
			bw.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	//---------------------//
	// Variable Conversion //
	//---------------------//
	
	private GenericTaskList getGenericTLFromData(ArrayList<String> array) {
		GenericTask genericTask;
		GenericTaskList genericTL = new GenericTaskList();
		JSONObject jsonTask;
		
		for (int i = 0; i < array.size(); i++) {
			jsonTask = new JSONObject(array.get(i));
			if (jsonTask.getString("Type").equals("Generic")) {
				genericTask = new GenericTask(jsonTask.getString("Name"));
				genericTask.setWorkload(jsonTask.getInt("Workload"));
				genericTask.setId(jsonTask.getString("ID"));
				resultantGenericTL.add(genericTask);
			}
		}
		return genericTL;
	}
	
	private TimedTaskList getTimedTLFromData(ArrayList<String> array) {
		TimedTask timedTask;
		TimedTaskList timedTL = new TimedTaskList();
		JSONObject jsonTask;
		
		for (int i = 0; i < array.size(); i++) {
			jsonTask = new JSONObject(array.get(i));
			if (jsonTask.getString("Type").equals("Timed")) {
				timedTask = new TimedTask(jsonTask.getString("Name"), parseDate(jsonTask.getString("Start Time")), parseDate(jsonTask.getString("End Time")));
				timedTask.setWorkload(jsonTask.getInt("Workload"));
				timedTask.setId(jsonTask.getString("ID"));
				timedTL.add(timedTask);
			}
		}
		return timedTL;
	}
	
	private DeadlineTaskList getDeadlineTLFromData(ArrayList<String> array) {
		DeadlineTask deadlineTask;
		DeadlineTaskList deadlineTL = new DeadlineTaskList();
		JSONObject jsonTask;
		
		for (int i = 0; i < array.size(); i++) {
			jsonTask = new JSONObject(array.get(i));
			if (jsonTask.getString("Type").equals("Deadline")) {
				deadlineTask = new DeadlineTask(jsonTask.getString("Name"), parseDate(jsonTask.getString("Deadline")));
				deadlineTask.setWorkload(jsonTask.getInt("Workload"));
				deadlineTask.setId(jsonTask.getString("ID"));
				deadlineTL.add(deadlineTask);
			}
		}
		return deadlineTL;
	}
	
	private DateTime parseDate(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
		DateTime dueDate = format.parseDateTime(date);
		return dueDate;
	}
	
	private void convertToStore(GenericTaskList taskList) {
		JSONObject jsonTask;
		if (!taskList.isEmpty()) {
			for (int i = 0; i < taskList.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("Name", taskList.get(i).getName());
				// jsonTask.put("due date", tasklist.get(i).getDueDate());
				jsonTask.put("Workload", taskList.get(i).getWorkload());
				jsonTask.put("ID", taskList.get(i).getId());
				jsonTask.put("Type", "Generic");
				tempList.add(jsonTask.toString());
			}
		}
	}
	
	private void convertToStore(DeadlineTaskList deadlineTaskList) {
		JSONObject jsonTask;
		if (!deadlineTaskList.isEmpty()) {
			for (int i = 0; i < deadlineTaskList.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("Name", deadlineTaskList.get(i).getName());
				jsonTask.put("Deadline", deadlineTaskList.get(i).getReadableDeadline());
				jsonTask.put("Workload", deadlineTaskList.get(i).getWorkload());
				jsonTask.put("ID", deadlineTaskList.get(i).getId());
				jsonTask.put("Type", "Deadline");
				tempList.add(jsonTask.toString());
			}
		}
	}
	
	private void convertToStore(TimedTaskList timedTaskList) {
		JSONObject jsonTask;
		if (!timedTaskList.isEmpty()) {
			for (int i = 0; i < timedTaskList.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("Name", timedTaskList.get(i).getName());
				jsonTask.put("Start Time", timedTaskList.get(i).getReadableStartTime());
				jsonTask.put("End Time", timedTaskList.get(i).getReadableEndTime());
				jsonTask.put("Workload", timedTaskList.get(i).getWorkload());
				jsonTask.put("ID", timedTaskList.get(i).getId());
				jsonTask.put("Type", "Timed");
				tempList.add(jsonTask.toString());
			}
		}
	}
}
