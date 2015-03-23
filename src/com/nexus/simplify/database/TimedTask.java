/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import org.joda.time.DateTime;
import org.joda.time.format.*;

public class TimedTask extends GenericTask {
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private DateTime dueDate;
	
	//--------------//
	// Constructors //
	//--------------//
	
	public TimedTask(String name, int year, int month, int day, int hour, int minute) {
		super(name);
		this.dueDate = new DateTime(year, month, day, hour, minute);
	}
	
	public TimedTask(String name, int year, int month, int day, int hour, int minute, String workload) {
		super(name, workload);
		this.dueDate = new DateTime(year, month, day, hour, minute);
	}
	
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	public void setTimed(int year, int month, int day, int hour, int minute) {
		this.dueDate = new DateTime(year, month, day, hour, minute);
	}
	
	//--------------------//
	// Attribute Accessor //
	//--------------------//
	
	public String getTimed() {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return format.print(this.dueDate);
	}
}
