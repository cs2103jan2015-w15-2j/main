/*
 * @author Tan Qian Yi
 * */

package com.nexus.simplify;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Task {	
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private String name;
	private DateTime dueDate;
	int workload;
	private String id;
	
	//--------------//
	// constructors //
	//--------------//
	
	// default constructor
	public Task(String name) {
		this.name = name;
		this.workload = 1;
		this.dueDate = new DateTime(0, 1, 1, 0, 0);
	}
	
	public Task(String name, int day, int month, int year, int hour, int minute, String workload, String id) {
		this.name = name;
		this.workload = Integer.valueOf(workload);
		this.id = id;
		this.dueDate = new DateTime(year, month, day, hour, minute);
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public void setName(String name) { 
		this.name = name; 
	}
	
	public void setDueDate(int day, int month, int year, int hour, int minute) { 
		this.dueDate = new DateTime(year, month, day, hour, minute); 
	}
	
	public void setDueDate(DateTime dueDate) { 
		this.dueDate = dueDate;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public String getName() {
		return name; 
	}
	
	public int getWorkload() { 
		return this.workload; 
	}
	
	public void setWorkload(String workload) { 
		this.workload = Integer.valueOf(workload); 
	}
	
	public String getId() { 
		return this.id; 
	}
	
	public String getDueDate() {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return format.print(this.dueDate);
	}
	
	//--------------//
	// Misc Methods //
	//--------------//
	
	public boolean isFloatingTask() {
		if (dueDate.getYear() == 0) {
			return true;
		} else {
			return false;
		}
	}
}