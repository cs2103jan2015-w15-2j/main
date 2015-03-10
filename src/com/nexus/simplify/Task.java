package com.nexus.simplify;

public class Task {	
	// instance variables
	private String name;
	private DueDate dueDate;
	int workload;
	private String id;
	
	// constructors
	public Task(String name) {
		this.name = name;
	}
	
	public Task(String name, String day, String month, String year, String workload, String id) {
		this.name = name;
		this.workload = Integer.valueOf(workload);
		this.id = id;
	}
	
	// methods
	public boolean isFloatingTask() { return !dueDate.hasDueDate(); }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public DueDate getDueDate() { return this.dueDate; }
	public void setDueDate(String day, String month, String year) {	}
	public int getWorkload() { return this.workload; }
	public void setWorkload(String workload) { this.workload = Integer.valueOf(workload); }
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
}