/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.*;

public class GenericTask {
	
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
		this.workload = new SimpleIntegerProperty(1);
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
}
