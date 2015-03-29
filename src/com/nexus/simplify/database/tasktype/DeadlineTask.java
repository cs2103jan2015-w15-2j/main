package com.nexus.simplify.database.tasktype;

import java.util.Date;

import javafx.beans.property.*;

import org.joda.time.DateTime;
import org.joda.time.format.*;

/**
 * Represents an instance of a task tagged with a due date.
 * @author Tan Qian Yi 
 */
public class DeadlineTask extends GenericTask {
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "E MMM dd HH:mm";

	/** 
	 * reference to http://stackoverflow.com/questions/3307330/using-joda-date-time-api-to-parse-multiple-formats 
	 * */
	/*private DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(JAVA_DATE_FORMAT).toFormatter();*/
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private final ObjectProperty<DateTime> deadline;
	
	//--------------//
	// Constructors //
	//--------------//
	
	/**
	 * constructor without workload
	 * 
	 * @param name name of task
	 * @param deadline due date of task 
	 * */
	public DeadlineTask(String name, Date deadline) {
		super(name);
		this.deadline = new SimpleObjectProperty<DateTime>(new DateTime(deadline));
	}
	
	/**
	 * constructor without workload
	 * 
	 * @param name name of task
	 * @param deadline due date of task in DateTime 
	 * */
	public DeadlineTask(String name, DateTime deadline) {
		super(name);
		this.deadline = new SimpleObjectProperty<DateTime>(deadline);
	}
	
	/**
	 * constructor with workload
	 * 
	 * @param name name of task
	 * @param deadline due date of task
	 * @param workload amount of effort required to do the task ranging from 1 to 5. 
	 * */
	public DeadlineTask(String name, Date deadline, int workload) {
		super(name, workload);
		this.deadline = new SimpleObjectProperty<DateTime>(new DateTime(deadline));
	}
	
	/**
	 * constructor with workload and id
	 * 
	 * @param name name of task in StringProperty
	 * @param deadline due date of task
	 * @param workload amount of effort required to do the task ranging from 1 to 5 in StringProperty
	 * @param id identification number of task in StringProperty
	 * */
	public DeadlineTask(StringProperty name, Date deadline, IntegerProperty workload, StringProperty id) {
		super(name, workload, id);
		this.deadline = new SimpleObjectProperty<DateTime>(new DateTime(deadline));
	}
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	/**
	 * default setter for class attribute deadline
	 * 
	 * @param deadline the new deadline for the task
	 * */
	public void setDeadline(Date deadline) {
		this.deadline.set(new DateTime(deadline));
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	/**
	 * returns the due date of the task formatted into a readable String Object.
	 * 
	 * @return due date of task as String Object
	 * */
	public String getReadableDeadline() {
		DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
		return format.print(deadline.get());
	}
	
	/**
	 * default getter method for attribute deadline.
	 * 
	 * @return due date of task
	 * */
	public DateTime getDeadline() {
		return deadline.get();
	}

	/**
	 * returns the due date of the task formatted as a StringProperty Object.
	 * 
	 * @return due date of task as StringProperty Object
	 * */
	public StringProperty getDTAsStringProperty() {
		return new SimpleStringProperty(this.getReadableDeadline());
	}
	
	//--------//
	// Method //
	//--------//
	
	/**
	 * converts DeadlineTask to GenericTask
	 * 
	 * @param name name of DeadlineTask in StringProperty
	 * @param workload amount of effort required to do the DeadlineTask ranging from 1 to 5 in IntegerProperty
	 * @param id id of DeadlineTask in StringProperty
	 * @return GenericTask
	 * */
	public GenericTask convertToGeneric(StringProperty name, IntegerProperty workload, StringProperty id) {
		return new GenericTask(name, workload, id);
	}
}
