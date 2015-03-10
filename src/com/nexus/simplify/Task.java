package com.nexus.simplify;

import org.joda.time.DateTime;

public class Task {	
	// instance variables
	private String name;
	private DateTime dueDate;
	int workload;
	private String id;
	
	// constructors
	public Task(String name) {
		this.name = name;
		this.workload = 1;
		// is id going to be generated at logic or task?
		this.dueDate = new DateTime(0, 0, 0, 0, 0);
	}
	
	public Task(String name, int day, int month, int year, int hour, int minute, String workload, String id) {
		this.name = name;
		this.workload = Integer.valueOf(workload);
		this.id = id;
		this.dueDate = new DateTime(year, month, day, hour, minute);
	}
	
	// methods
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public DateTime getDueDate() { return this.dueDate; }
	public void setDueDate(int day, int month, int year, int hour, int minute) { this.dueDate = new DateTime(year, month, day, hour, minute); }
	public int getWorkload() { return this.workload; }
	public void setWorkload(String workload) { this.workload = Integer.valueOf(workload); }
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	public boolean isFloatingTask() {
		if (dueDate == new DateTime(0, 0, 0, 0, 0)) {
			return true;
		} else {
			return false;
		}
	}
}