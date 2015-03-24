/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.collections.ObservableList;

public class TaskListPackage {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private ObservableList<DeadlineTask> observableDeadline;
	private ObservableList<TimedTask> observableTimed;
	private ObservableList<GenericTask> observableGeneric;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public TaskListPackage(DeadlineTaskList deadlineTL, TimedTaskList timedTL, GenericTaskList genericTL) {
		this.observableDeadline = deadlineTL.getObservable();
		this.observableGeneric = genericTL.getObservable();
		this.observableTimed = timedTL.getObservable();
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public ObservableList<DeadlineTask> getDeadline() {
		return observableDeadline;
	}
	
	public ObservableList<TimedTask> getTimed() {
		return observableTimed;
	}
	
	public ObservableList<GenericTask> getGeneric() {
		return observableGeneric;
	}
}
