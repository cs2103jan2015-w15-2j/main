/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GenericTask {
	
	//------------------//
	// Class Attributes //
	//------------------//
	private String id;
	private String name;
	private int workload;
	
	//--------------//
	// Constructors //
	//--------------//
	
	// default constructor
	public GenericTask(String name) {
		this.name = name;
		this.workload = 1;
		this.id = setId();
	}

	public GenericTask(String name, String workload) {
		this.name = name;
		this.workload = Integer.parseInt(workload);
		this.id = setId();
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
		this.name = name;
	}
	
	public void setWorkload(int workload) {
		this.workload = workload;
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getWorkload() {
		return this.workload;
	}
}
