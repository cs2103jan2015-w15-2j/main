/*
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
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private File file;
	private ArrayList<String> tempList = new ArrayList<String>();
	private GenericTaskList taskList = new GenericTaskList();
	private TimedTaskList timedTaskList = new TimedTaskList();
	private DeadlineTaskList deadlineTaskList = new DeadlineTaskList();
	
	//---------------//
	// API for Logic //
	//---------------//
	
	public void addTimed(String name, int year, int month, int day, int hour, int minute, String workload) {
		timedTaskList.add(new TimedTask(name, year, month, day, hour, minute, workload));
	}
	
	public void addDeadline(String name, String deadline, String workload) {
		deadlineTaskList.add(new DeadlineTask(name, deadline, workload));
	}
	
	public void addFloating(String name, String workload) {
		taskList.add(new GenericTask(name, workload));
	}
	
	public void delete(int index) {
		// index arrangement: floating, timed, deadline
		if (index >= 11) {
			taskList.delete(index - 11);
		} else if (index >= 6) {
			timedTaskList.delete(index - 6);
		} else {
			deadlineTaskList.delete(index - 1);
		}
	}
	
	public void display(String option) {
		
	}
	
	// check if string is workload or name
	public void modify(int index, String string) {
		int integerValue = Integer.parseInt(string);
		if (integerValue >= 5 || integerValue <= 1) {
			taskList.get(index).setWorkload(integerValue);
		} else {
			taskList.get(index).setName(string);
		}
	}
	
	//------------//
	// API for UI //
	//------------//
	
	public TaskListPackage getTaskListPackage() {
		return new TaskListPackage(deadlineTaskList, timedTaskList, taskList);
	}

	//-------------//
	// Constructor //
	//-------------//
	
	public Database(String fileName) throws IOException {
		setUpFile(fileName);
	}
	
	//----------------//
	// Initialization //
	//----------------//
	
	private void setUpFile(String fileName) throws IOException {
		file = new File(fileName);
		if (file.exists()) {
			getDataFromFile();
		} else {
			file.createNewFile();
		}
	}
	
	//--------------//
	// File Reading //
	//--------------//
	
	public GenericTaskList readFromFile() {
		if (tempList.isEmpty()) {
			return new GenericTaskList();
		} else {
			taskList = convertToTaskList(tempList);
			return taskList;
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
	
	public void writeToFile(GenericTaskList inputTL) {
		try {
			String fileName = file.getName();
			file.delete();
			file = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
			convertToStore(inputTL);
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
	
	private GenericTaskList convertToTaskList(ArrayList<String> array) {
		GenericTaskList resultantTaskList = new GenericTaskList();
		GenericTask task;
		JSONObject jsonTask;
		
		for (int i = 0; i < tempList.size(); i++) {
			jsonTask = new JSONObject(tempList.get(i));
			
			task = new GenericTask(jsonTask.getString("name"));
			// task.setDueDate(parseDueDate(jsonTask.getString("due date")));
			task.setWorkload(jsonTask.getInt("workload"));
			// task.setId(jsonTask.getString("id"));
			
			resultantTaskList.add(task);
		}
		
		return resultantTaskList;
	}
	
	private DateTime parseDueDate(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		DateTime dueDate = format.parseDateTime(date);
		return dueDate;
	}
	
	private ArrayList<String> convertToStore(GenericTaskList tasklist) {
		JSONObject jsonTask;
		tempList.clear();
		if (!tasklist.isEmpty()) {
			for (int i = 0; i < tasklist.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("name", tasklist.get(i).getName());
				// jsonTask.put("due date", tasklist.get(i).getDueDate());
				jsonTask.put("workload", tasklist.get(i).getWorkload());
				// jsonTask.put("id", tasklist.get(i).getId());
				tempList.add(jsonTask.toString());
			}
		}
		
		return tempList;
	}
}
