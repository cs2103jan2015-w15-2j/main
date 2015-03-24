package com.nexus.simplify.database;

import javafx.collections.ObservableList;

/**
 * Collates all three types of lists (Generic, DeadLine, Timed) into a package.
 * formatted for the 
 * @author Tan Qian Yi
 */
public class TaskListPackage {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private ObservableList<DeadlineTask> observableDeadlineTL;
	private ObservableList<TimedTask> observableTimedTL;
	private ObservableList<GenericTask> observableGenericTL;
	
	//-------------//
	// Constructor //
	//-------------//
	
	
	/**
	 * @param deadlineTL a list of deadline-based tasks.
	 * @param timedTL a list of timed tasks.
	 * @param genericTL a list of generic (floating) tasks.
	 * */
	public TaskListPackage(DeadlineTaskList deadlineTL, TimedTaskList timedTL, GenericTaskList genericTL) {
		this.observableDeadlineTL = deadlineTL.getObservable();
		this.observableGenericTL = genericTL.getObservable();
		this.observableTimedTL = timedTL.getObservable();
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	/**
	 * default getter for deadlineTL.
	 * 
	 * @return a list of deadline-based tasks.
	 * */
	public ObservableList<DeadlineTask> getDeadlineTL() {
		return observableDeadlineTL;
	}
	
	/**
	 * default getter for timedTL.
	 * 
	 * @return a list of timed tasks.
	 * */
	public ObservableList<TimedTask> getTimedTL() {
		return observableTimedTL;
	}
	
	/**
	 * default getter for genericTL.
	 * 
	 * @return a list of generic (floating) tasks.
	 * */
	public ObservableList<GenericTask> getGenericTL() {
		return observableGenericTL;
	}
}
