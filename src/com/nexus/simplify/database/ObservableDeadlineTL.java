/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.collections.*;

public class ObservableDeadlineTL {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private int numOfDisplayItems = 5;
	private DeadlineTaskList tempTaskList;
	private ObservableList<DeadlineTask> observableDeadline;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableDeadlineTL(DeadlineTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			tempTaskList.add(taskList.get(i));
		}
		observableDeadline = FXCollections.observableList(tempTaskList);
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<DeadlineTask> get() {
		return observableDeadline;
	}
}
