/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.collections.*;

@SuppressWarnings("unused")
public class ObservableGenericTL {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private int numOfDisplayItems = 5;
	private GenericTaskList tempTaskList;
	private ObservableList<GenericTask> observableGeneric;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableGenericTL(GenericTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			tempTaskList.add(taskList.get(i));
		}
		observableGeneric = FXCollections.observableList(tempTaskList);
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<GenericTask> get() {
		return observableGeneric;
	}
}
