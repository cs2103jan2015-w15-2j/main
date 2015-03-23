/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.collections.*;
import org.joda.time.DateTime;

public class ObservableTimedTL {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private ObservableList<ObservableTimedTask> observableTimed = FXCollections.observableArrayList();
	
	private int numOfDisplayItems = 5;
	private String name;
	private int workload;
	private String id;
	private DateTime dueDate;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableTimedTL(TimedTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			name = taskList.get(i).getName();
			workload = taskList.get(i).getWorkload();
			id = taskList.get(i).getId();
			dueDate = taskList.get(i).getTimed();
			observableTimed.add(new ObservableTimedTask(name, dueDate, workload, id));
		}
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<ObservableTimedTask> get() {
		return observableTimed;
	}
}
