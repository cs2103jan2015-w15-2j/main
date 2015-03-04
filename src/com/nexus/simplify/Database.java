package com.nexus.simplify;

import java.util.*;
import java.io.*;

public class Database {
	private File file;
	private ArrayList<String> list = new ArrayList<String>();
	
	public void setUpFile(String fileName) {
		file = new File(fileName);
		if (file.exists()) {
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
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
	public TaskList readFromFile() {
		
	}
	
	public void writeToFile(TaskList tasklist) {
		
	}
	
	private TaskList convertToTask() {
		
	}
	
	private convertToStore(TaskList tasklist) {
		
	}
}
