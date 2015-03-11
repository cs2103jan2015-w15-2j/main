/*
 * @author Tan Qian Yi
 * */

package com.nexus.simplify;

import java.util.*;
import java.io.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.*;

public class Database implements IDatabase {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private File file;
	private ArrayList<String> tempList = new ArrayList<String>();
	private TaskList taskList = new TaskList();
	
	//-------------//
	// Constructor //
	//-------------//
	
	public Database(String fileName) {
		setUpFile(fileName);
	}
	
	//----------------//
	// Initialization //
	//----------------//
	
	private void setUpFile(String fileName) {
		file = new File(fileName);
		if (file.exists()) {
			getDataFromFile();
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
	//--------------//
	// File Reading //
	//--------------//
	
	public TaskList readFromFile() {
		if (tempList.isEmpty()) {
			return null;
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
	
	public void writeToFile(TaskList inputTL) {
		try {
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
	
	private TaskList convertToTaskList(ArrayList<String> array) {
		TaskList resultantTaskList = new TaskList();
		Task task;
		JSONObject jsonTask;
		
		for (int i = 0; i < tempList.size(); i++) {
			jsonTask = new JSONObject(tempList.get(i));
			
			task = new Task(jsonTask.getString("name"));
			task.setDueDate(parseDueDate(jsonTask.getString("due date")));
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
	
	public ArrayList<String> convertToStore(TaskList tasklist) {
		JSONObject jsonTask;
		
		if (!tasklist.isEmpty()) {
			for (int i = 0; i < tasklist.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("name", tasklist.get(i).getName());
				jsonTask.put("due date", tasklist.get(i).getDueDate());
				jsonTask.put("workload", tasklist.get(i).getWorkload());
				// jsonTask.put("id", tasklist.get(i).getId());
				tempList.add(jsonTask.toString());
			}
		}
		
		return tempList;
	}
	
	public static void main(String[] args) {
		Database db = new Database("test_db");
		TaskList tl = new TaskList();
		tl.add(new Task("please write this task!"));
		db.writeToFile(tl);
	}
}
