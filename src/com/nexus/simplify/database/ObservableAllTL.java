/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

// import javafx.collections.*;

public class ObservableAllTL {
	
	//-----------------//
	// Class Atributes //
	//-----------------//
	
	private ObservableGenericTL genericTaskList;
	private ObservableTimedTL timedTaskList;
	private ObservableDeadlineTL deadlineTaskList;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableAllTL(ObservableGenericTL generic, ObservableTimedTL timed, ObservableDeadlineTL deadline) {
		this.genericTaskList = generic;
		this.timedTaskList = timed;
		this.deadlineTaskList = deadline;
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public ObservableGenericTL getGeneric() {
		return this.genericTaskList;
	}
	
	public ObservableTimedTL getTimed() {
		return this.timedTaskList;
	}
	
	public ObservableDeadlineTL getDeadline() {
		return this.deadlineTaskList;
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void setGeneric(ObservableGenericTL generic) {
		this.genericTaskList = generic;
	}
	
	public void setTimed(ObservableTimedTL timed) {
		this.timedTaskList = timed;
	}
	
	public void setDeadline(ObservableDeadlineTL deadline) {
		this.deadlineTaskList = deadline;
	}
}
