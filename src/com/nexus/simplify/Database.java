package com.nexus.simplify;

import java.util.*;
import java.io.*;
import org.json.*;

public class Database implements IDatabase {
	private File file;
	private ArrayList<String> list = new ArrayList<String>();
	private TaskList taskList = new TaskList();
	
	JSONObject jsonTask;
	
	public Database(String fileName) {
		setUpFile(fileName);
	}
	
	public void setUpFile(String fileName) {
		file = new File(fileName);
		if (file.exists()) {
			readFromFile();
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
	public TaskList readFromFile() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String text;
			while ((text = br.readLine()) != null) {
				list.add(text);
			}
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		taskList = convertToTask(list);
		return taskList;
	}
	
	public void writeToFile(TaskList tasklist) {
		try {
			StringWriter sw = new StringWriter();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
			while (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					bw.write(list.get(i));
				}
			}
			bw.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	private TaskList convertToTask(ArrayList<String> array) {
		TaskList tempList = new TaskList();
		Task task;
		for (int i = 0; i < list.size(); i++) {
			jsonTask = new JSONObject(list.get(i));
			task = new Task(jsonTask.getString("name"));
			// task.setDueDate(jsonTask.getString("due date"));
			task.setWorkload(jsonTask.getString("workload"));
			task.setId(jsonTask.getString("id"));
			tempList.add(task);
		}
		return tempList;
	}
	
	private ArrayList<String> convertToStore(TaskList tasklist) {
		while (!tasklist.isEmpty()) {
			for (int i = 0; i < tasklist.size(); i++) {
				jsonTask = new JSONObject();
				jsonTask.put("name", tasklist.get(i).getName());
				jsonTask.put("due date", tasklist.get(i).getDueDate());
				jsonTask.put("workload", tasklist.get(i).getWorkload());
				jsonTask.put("id", tasklist.get(i).getId());
				list.add(jsonTask.toString());
			}
		}
		return list;
	}
}
