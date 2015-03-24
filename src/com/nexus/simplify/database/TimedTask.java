package com.nexus.simplify.database;

import java.util.Date;

import javafx.beans.property.*;

import org.joda.time.DateTime;
import org.joda.time.format.*;

/**
 * Represents an instance of task tagged with start and end times.
 * @author Tan Qian Yi
 */
public class TimedTask extends GenericTask {
	
	private static final String JAVA_DATE_FORMAT = "E MMM DD HH:mm";
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	/**
	 * @author tohjianfeng
	 * */
	private final ObjectProperty<DateTime> startTime;
	private final ObjectProperty<DateTime> endTime;
	
	//--------------//
	// Constructors //
	//--------------//
	
	/**
	 * constructor for timed tasks with workload
	 * 
	 * */
	public TimedTask(String name, Date startTime, Date endTime, String workload) {
		super(name, workload);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(new DateTime(startTime));
		this.endTime = new SimpleObjectProperty<DateTime>(new DateTime(endTime));
	}
	
	/**
	 * constructor for timed tasks without workload
	 * 
	 * */
	public TimedTask(String name, Date startTime, Date endTime) {
		super(name);
		assert(startTime != null || endTime != null);
		this.startTime = new SimpleObjectProperty<DateTime>(new DateTime(startTime));
		this.endTime = new SimpleObjectProperty<DateTime>(new DateTime(endTime));
	}
	
	/*
	public TimedTask(String name, int year, int month, int day, int hour, int minute) {
		super(name);
		this.dueDate = new SimpleObjectProperty<DateTime>(new DateTime(year, month, day, hour, minute));
	}
	
	public TimedTask(String name, int year, int month, int day, int hour, int minute, String workload) {
		super(name, workload);
		this.dueDate = new SimpleObjectProperty<DateTime>(new DateTime(year, month, day, hour, minute));
	}
	*/
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void setStartTime(String startTime) {
		this.startTime.set(new DateTime(startTime));
	}
	
	public void setEndTime(String endTime) {
		this.endTime.set(new DateTime(endTime));
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public ObjectProperty<DateTime> getStartTime() {
		return startTime;
	}
	
	public ObjectProperty<DateTime> getEndTime() {
		return endTime;
	}
	
	public DateTime getStartTimeAsDT() {
		return startTime.get();
	}
	
	public DateTime getEndTimeAsDT() {
		return endTime.get();
	}
	
	public StringProperty getStartTimeAsStringProperty() {
		return new SimpleStringProperty(this.getReadableStartTime());
	}
	
	public StringProperty getEndTimeAsStringProperty() {
		return new SimpleStringProperty(this.getReadableEndTime());
	}
	
	public String getReadableStartTime() {
		return startTime.get().toString();
	}
	
	public String getReadableEndTime() {
		return endTime.get().toString();
	}
	
	/*
	public String getTimed() {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return format.print(dueDate.get());
	}*/
}
