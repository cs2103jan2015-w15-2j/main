//A0114887U
package com.nexus.simplify.database.tasktype;

import java.util.Date;

import javafx.beans.property.*;

import org.joda.time.DateTime;
import org.joda.time.format.*;

/**
 * Represents an instance of a task tagged with a due date.
 */
public class DeadlineTask extends GenericTask {
	
	/**
	 * All dates will be shaped according to this format. 
	 * <DAY> <MONTH> <YEAR> <HOUR>:<MINUTE>
	 * */
	private static final String JAVA_DATE_FORMAT = "dd MMM yyyy HH:mm";

	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private final ObjectProperty<DateTime> deadline;
	
	//--------------//
	// Constructors //
	//--------------//
	
	//@author A0108361M
	/**
	 * Constructor without workload.
	 * 
	 * @param name name of task
	 * @param deadline due date of task 
	 * */
	public DeadlineTask(String name, Date deadline) {
		super(name);
		this.deadline = new SimpleObjectProperty<DateTime>(new DateTime(deadline));
	}
	
	/**
	 * Constructor without workload.
	 * 
	 * @param name name of task
	 * @param deadline due date of task in DateTime 
	 * */
	public DeadlineTask(String name, DateTime deadline) {
		super(name);
		this.deadline = new SimpleObjectProperty<DateTime>(deadline);
	}
	
	/**
	 * Constructor with workload.
	 * 
	 * @param name name of task
	 * @param deadline due date of task
	 * @param workload amount of effort required to do the task ranging from 1 to 5. 
	 * */
	public DeadlineTask(String name, Date deadline, int workload) {
		super(name, workload);
		this.deadline = new SimpleObjectProperty<DateTime>(new DateTime(deadline));
	}
	
	//A0114887U
	/**
	 * Constructor with workload and id.
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
	
	/**
	 * Constructor with workload and id.
	 * 
	 * @param name name of task in StringProperty
	 * @param deadline due date of task in DateTime format
	 * @param workload amount of effort required to do the task ranging from 1 to 5 in StringProperty
	 * @param id identification number of task in StringProperty
	 * */
	public DeadlineTask(StringProperty name, DateTime deadline, IntegerProperty workload, StringProperty id) {
		super(name, workload, id);
		this.deadline = new SimpleObjectProperty<DateTime>(deadline);
	}
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	//@author A0108361M
	/**
	 * Default setter for class attribute deadline.
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
	 * Returns the due date of the task formatted into a readable String Object.
	 * 
	 * @return due date of task as String Object
	 * */
	public String getReadableDeadline() {
		DateTimeFormatter format = DateTimeFormat.forPattern(JAVA_DATE_FORMAT);
		return format.print(deadline.get());
	}
	
	/**
	 * Default getter method for attribute deadline.
	 * 
	 * @return due date of task
	 * */
	public DateTime getDeadline() {
		return deadline.get();
	}

	/**
	 * Returns the due date of the task formatted as a StringProperty Object.
	 * 
	 * @return due date of task as StringProperty Object
	 * */
	public StringProperty getDateTimeAsStringProperty() {
		return new SimpleStringProperty(this.getReadableDeadline());
	}
	
	//@author A0111035A
	/**
	 * Returns an exact copy of the task.
	 * */
	public DeadlineTask getCopy() {
		StringProperty cName = new SimpleStringProperty(getName());
		IntegerProperty cWorkload = new SimpleIntegerProperty(getWorkload());
		StringProperty cID = new SimpleStringProperty(getId());
		DateTime cDeadline = getDeadline();
		DeadlineTask copy = new DeadlineTask(cName, cDeadline, cWorkload, cID);
		return copy;
	}
}
