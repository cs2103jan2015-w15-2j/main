/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.beans.property.*;

import org.joda.time.format.*;
import org.joda.time.DateTime;

public class ObservableTimedTask extends ObservableGenericTask {
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private final ObjectProperty<DateTime> dueDate;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableTimedTask(String name, DateTime dueDate, int workload, String id) {
		super(name, workload, id);
		this.dueDate = new SimpleObjectPropert<DateTime>(dueDate);
	}
	
	//--------------------//
	// Attribute Accessor //
	//--------------------//
	
	public DateTime getDueDate() {
		return dueDate.get();
	}
	
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	public void setDueDate(DateTime dueDate) {
		this.dueDate.set(dueDate);
	}
}
