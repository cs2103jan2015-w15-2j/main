//@author A0114887U
package com.nexus.simplify.database.tasktype;

import java.util.Date;

import javafx.beans.property.*;

import org.joda.time.DateTime;
import org.joda.time.format.*;

/**
 * Represents an instance of task tagged with start and end time.
 */
public class TimedTask extends GenericTask {
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "dd MMM yyyy HH:mm";
	private DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	//@author A0108361M

	private final ObjectProperty<DateTime> startTime;
	private final ObjectProperty<DateTime> endTime;
	
	//--------------//
	// Constructors //
	//--------------//
	
	/**
	 * Constructor for timed tasks with workload.
	 * 
	 * @param name name of task
	 * @param startTime start time of task
	 * @param endTime end time of task
	 * @param workload amount of effort required to do the task ranging from 1 to 5
	 * */
	public TimedTask(String name, Date startTime, Date endTime, int workload) {
		super(name, workload);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(new DateTime(startTime));
		this.endTime = new SimpleObjectProperty<DateTime>(new DateTime(endTime));
	}
	
	/**
	 * Constructor for timed tasks without workload.
	 * 
	 * @param name name of task
	 * @param startTime start time of task
	 * @param endTime end time of task
	 * */
	public TimedTask(String name, Date startTime, Date endTime) {
		super(name);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(new DateTime(startTime));
		this.endTime = new SimpleObjectProperty<DateTime>(new DateTime(endTime));
	}
	
	//A0114887U
	/**
	 * Constructor for timed tasks without workload.
	 * 
	 * @param name name of task
	 * @param startTime start time of task in DateTime
	 * @param endTime end time of task in DateTime
	 * */
	public TimedTask(String name, DateTime startTime, DateTime endTime) {
		super(name);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(startTime);
		this.endTime = new SimpleObjectProperty<DateTime>(endTime);
	}
	
	/**
	 * Constructor for timed tasks with workload, id and endTime in DateTime format.
	 * 
	 * @param name name of task in StringProperty format
	 * @param startTime start time of task as Date 
	 * @param endTime end time of task in DateTime format
	 * @param workload amount of effort required to do the task ranging from 1 to 5 in StringProperty
	 * @param id identification number of task in StringProperty
	 * */
	public TimedTask(StringProperty name, DateTime startTime, DateTime endTime, IntegerProperty workload, StringProperty id) {
		super(name, workload, id);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(new DateTime(startTime));
		this.endTime = new SimpleObjectProperty<DateTime>(endTime);
	}
	
	/**
	 * Constructor for timed tasks with workload, id and startTime in DateTime format.
	 * 
	 * @param name name of task in StringProperty format
	 * @param startTime start time of task in DateTime format 
	 * @param endTime end time of task as Date
	 * @param workload amount of effort required to do the task ranging from 1 to 5 in StringProperty
	 * @param id identification number of task in StringProperty
	 * */
	public TimedTask(StringProperty name, DateTime startTime, Date endTime, IntegerProperty workload, StringProperty id) {
		super(name, workload, id);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(startTime);
		this.endTime = new SimpleObjectProperty<DateTime>(new DateTime(endTime));
	}

	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	//@author A0108361M
	/**
	 * Default setter for class attribute startTime.
	 * 
	 * @param startTime the new start time for the task
	 * */
	public void setStartTime(Date startTime) {
		this.startTime.set(new DateTime(startTime));
	}
	
	/**
	 * Default setter for class attribute endTime.
	 * 
	 * @param endTime the new end time for the task
	 * */
	public void setEndTime(Date endTime) {
		this.endTime.set(new DateTime(endTime));
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	/**
	 * Returns the start time of the task formatted as a DateTimeProperty Object.
	 * 
	 * @return start time of task as a DateTimeProperty Object
	 * */
	public ObjectProperty<DateTime> getStartTime() {
		return startTime;
	}
	
	/**
	 * Returns the end time of the task formatted as a DateTimeProperty Object.
	 * 
	 * @return end time of task as a DateTimeProperty Object
	 * */
	public ObjectProperty<DateTime> getEndTime() {
		return endTime;
	}
	
	/**
	 * Default getter method for attribute startTime.
	 * 
	 * @return start time of task
	 * */
	public DateTime getStartTimeAsDateTime() {
		return startTime.get();
	}
	
	/**
	 * Default getter method for attribute endTime.
	 * 
	 * @return end time of task
	 * */
	public DateTime getEndTimeAsDateTime() {
		return endTime.get();
	}
	
	/**
	 * Returns the start time of the task formatted as a StringProperty Object.
	 * 
	 * @return start time of the task as StringProperty Object
	 * */
	public StringProperty getStartTimeAsStringProperty() {
		return new SimpleStringProperty(this.getReadableStartTime());
	}
	
	/**
	 * Returns the end time of the task formatted as a StringProperty Object.
	 * 
	 * @return end time of task as StringProperty Object
	 * */
	public StringProperty getEndTimeAsStringProperty() {
		return new SimpleStringProperty(this.getReadableEndTime());
	}
	
	/**
	 * Returns the start time of the task formatted into a readable String Object.
	 * 
	 * @return start time of task as String Object
	 * */
	public String getReadableStartTime() {
		return format.print(startTime.get());
	}
	
	/**
	 * Returns the end time of the task formatted into a readable String Object.
	 * 
	 * @return end time of task as String Object
	 * */
	public String getReadableEndTime() {
		return format.print(endTime.get());
	}
	
	//A0111035A
	/**
	 * Returns an exact copy of the task.
	 * */
	public TimedTask getCopy() {
		StringProperty cName = new SimpleStringProperty(getName());
		IntegerProperty cWorkload = new SimpleIntegerProperty(getWorkload());
		StringProperty cID = new SimpleStringProperty(getId());
		DateTime cStart = getStartTimeAsDateTime();
		DateTime cEnd = getEndTimeAsDateTime();
		TimedTask copy = new TimedTask(cName, cStart, cEnd, cWorkload, cID);
		return copy;
	}
}
