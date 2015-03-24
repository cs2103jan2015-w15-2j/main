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
	
	private ObservableList<GenericTask> observableGeneric = FXCollections.observableArrayList();;
	
	private int numOfDisplayItems = 5;
	private String name;
	private int workload;
	private String id;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableGenericTL(GenericTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			name = taskList.get(i).getName();
			workload = taskList.get(i).getWorkload();
			id = taskList.get(i).getId();
			observableGeneric.add(new ObservableGenericTask(name, workload, id));
		}
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<GenericTask> get() {
		return observableGeneric;
	}
}
