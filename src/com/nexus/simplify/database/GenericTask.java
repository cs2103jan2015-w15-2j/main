package com.nexus.simplify.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.*;

/**
 * Represents an instance of a task without due date or start and end time
 * @author Tan Qian Yi
 * */
public class GenericTask {
	
	/**
	 * Default value of workload when user does not specify is 1
	 * */
	private static final int DEFAULT_WORKLOAD_VALUE = 1;
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private final StringProperty id;
	private final StringProperty name;
	private final IntegerProperty workload;
	
	//--------------//
	// Constructors //
	//--------------//
	
	/**
	 * constructor without workload
	 * 
	 * @param name name of task
	 * @param  
	 * */
	public GenericTask(String name) {
		this.name = new SimpleStringProperty(name);
		this.workload = new SimpleIntegerProperty(DEFAULT_WORKLOAD_VALUE);
		this.id = new SimpleStringProperty(setId());
	}

	/**
	 * constructor with workload
	 * 
	 * @param name name of task
	 * @param workload workload of task
	 * */
	public GenericTask(String name, int workload) {
		this.name = new SimpleStringProperty(name);
		this.workload = new SimpleIntegerProperty(workload);
		this.id = new SimpleStringProperty(setId());
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	/**
	 * default setter for class attribute id
	 * Id is generated by task creation time and used to sort task lists by default.
	 * */
	private static String setId() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat dateTime = new SimpleDateFormat("yyMMddHHmmss");
		return dateTime.toString();
	}
	
	/**
	 * default setter for class attribute name
	 * 
	 * @param name the new name for the task
	 * */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * default setter for class attribute workload
	 * 
	 * @param workload the new workload for the task
	 * */
	public void setWorkload(int workload) {
		this.workload.set(workload);
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	/**
	 * default getter method for attribute id
	 * 
	 * @return id of task
	 * */
	public String getId() {
		return id.get();
	}
	
	/**
	 * default getter method for attribute name
	 * 
	 * @return name of task
	 * */
	public String getName() {
		return name.get();
	}
	
	/**
	 * default getter method for attribute workload
	 * 
	 * @return workload of task
	 * */
	public int getWorkload() {
		return workload.get();
	}
	
	/**
	 * returns the id of the task formatted as a StringProperty Object
	 * 
	 * @return id of task as StringProperty Object
	 * @author tohjianfeng
	 * */
	public StringProperty getIDAsStringProperty() {
		return id;
	}
	
	/**
	 * returns the name of the task formatted as a StringProperty Object
	 * 
	 * @return name of task as StringProperty Object
	 * */
	public StringProperty getNameAsStringProperty() {
		return name;
	}
	
	/**
	 * returns the workload of the task formatted as a IntegerProperty Object
	 * 
	 * @return workload of task as IntegerProperty Object
	 * */
	public IntegerProperty getWorkloadAsIntegerProperty() {
		return workload;
	}
}
