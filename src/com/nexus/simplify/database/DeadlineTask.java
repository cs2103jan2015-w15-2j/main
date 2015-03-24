/*
 * @author Tan Qian Yi 
 */

package com.nexus.simplify.database;

import javafx.beans.property.*;

import org.joda.time.DateTime;
import org.joda.time.format.*;

public class DeadlineTask extends GenericTask {
	
	// reference to http://stackoverflow.com/questions/3307330/using-joda-date-time-api-to-parse-multiple-formats
	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/M/yyyy km").toFormatter();
		
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private final ObjectProperty<DateTime> deadline;
	
	//--------------//
	// Constructors //
	//--------------//
	
	public DeadlineTask(String name, String deadline) {
		super(name);
		this.deadline = new SimpleObjectProperty<DateTime>(formatter.parseDateTime(deadline));
	}
	
	public DeadlineTask(String name, String deadline, String workload) {
		super(name, workload);
		this.deadline = new SimpleObjectProperty<DateTime>(formatter.parseDateTime(deadline));
	}
	
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	public void setDeadline(String deadline) {
		this.deadline.set(formatter.parseDateTime(deadline));
	}
	
	//--------------------//
	// Attribute Accessor //
	//--------------------//
	
	public String getDeadline() {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
		return format.print(deadline.get());
	}
	
	public DateTime getDeadlineDT() {
		return deadline.get();
	}
	
	/**
	 * @author tohjianfeng
	 * */
	
	public StringProperty getDTStringProperty() {
		return new SimpleStringProperty(this.getDeadline());
	}
}
