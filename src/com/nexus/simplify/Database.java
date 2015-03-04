package com.nexus.simplify;

import java.util.*;
import java.io.*;
import org.json.simple.*;

public class Database {
	private File file;
	private ArrayList<String> list = new ArrayList<String>();
	private TaskList taskList = new TaskList();
	private JSONArray jsonArrayOfTasks;
	
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
		return taskList;
	}
	
	public void writeToFile(TaskList tasklist) {
		StringWriter sw = new StringWriter();
		// add constructor for TaskList
		/*
		while (!tasklist.isEmpty()) {
			for (int i = 0; i < tasklist.size(); i ++) {
				JSONObject task = new JSONObject();
				task.put("name", tasklist.get(i).getName());
				task.put("due date", tasklist.get(i).getDueDate());
				task.put("workload", tasklist.get(i).getWorkload());
				task.put("id", tasklist.get(i).getId());
				
				task.writeJSONString(sw);
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
				String jsonText = sw.toString();
				bw.write(jsonText);
				bw.close();
			}*/
		}
	}
	/*
	private TaskList convertToTask() {
		
	}
	
	private JSONArray convertToStore(TaskList tasklist) {
		
	}*/
}
