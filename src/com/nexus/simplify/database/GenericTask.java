/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.*;

public class GenericTask {
	
	private static final int default_workload_value = 1;
	
	//------------------//
	// Class Attributes //
	//------------------//
	private final StringProperty id;
	private final StringProperty name;
	private final IntegerProperty workload;
	
	//--------------//
	// Constructors //
	//--------------//
	
	// default constructor
	public GenericTask(String name) {
		this.name = new SimpleStringProperty(name);
		this.workload = new SimpleIntegerProperty(default_workload_value);
		this.id = new SimpleStringProperty(setId());
	}

	public GenericTask(String name, String workload) {
		this.name = new SimpleStringProperty(name);
		this.workload = new SimpleIntegerProperty(Integer.parseInt(workload));
		this.id = new SimpleStringProperty(setId());
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	private static String setId() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat dateTime = new SimpleDateFormat("yyMMddHHmmss");
		return dateTime.toString();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setWorkload(int workload) {
		this.workload.set(workload);
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public String getId() {
		return id.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public int getWorkload() {
		return workload.get();
	}
	
	/**
	 * @author tohjianfeng
	 * */
	public StringProperty getIDAsStringProperty() {
		return id;
	}
	
	public StringProperty getNameAsStringProperty() {
		return name;
	}
	
	public IntegerProperty getWorkloadAsIntegerProperty() {
		return workload;
	}
}
