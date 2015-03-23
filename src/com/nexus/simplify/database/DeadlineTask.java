/*
 * @author Tan Qian Yi 
 */

package com.nexus.simplify.database;

import org.joda.time.DateTime;
import org.joda.time.format.*;

public class DeadlineTask extends GenericTask {
	
	// reference to http://stackoverflow.com/questions/3307330/using-joda-date-time-api-to-parse-multiple-formats
	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/M/yyyy km").toFormatter();
		
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private DateTime dueDate;
	
	//--------------//
	// Constructors //
	//--------------//
	
	public DeadlineTask(String name, String deadline) {
		super(name);
		this.dueDate = formatter.parseDateTime(deadline);
	}
	
	public DeadlineTask(String name, String deadline, String workload) {
		super(name, workload);
		this.dueDate = formatter.parseDateTime(deadline);
	}
	
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	public void setDeadline(String deadline) {
		this.dueDate = formatter.parseDateTime(deadline);
	}
	
	//--------------------//
	// Attribute Accessor //
	//--------------------//
	
	public String getDeadline() {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
		return format.print(this.dueDate);
	}
}
