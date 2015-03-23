/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.collections.*;

public class ObservableTimedTL {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private int numOfDisplayItems = 5;
	private TimedTaskList tempTaskList;
	private ObservableList<TimedTask> observableTimed;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableTimedTL(TimedTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			tempTaskList.add(taskList.get(i));
		}
		observableTimed = FXCollections.observableList(tempTaskList);
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<TimedTask> get() {
		return observableTimed;
	}
}
