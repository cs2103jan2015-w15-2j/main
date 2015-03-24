/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import org.joda.time.DateTime;

import javafx.collections.*;

public class ObservableDeadlineTL {

	//------------------//
	// Class Attributes //
	//------------------//

	private ObservableList<DeadlineTask> observableDeadline = FXCollections.observableArrayList();
	
	private int numOfDisplayItems = 5;
	private String name;
	private int workload;
	private String id;
	private DateTime deadline;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableDeadlineTL(DeadlineTaskList taskList) {
		for (int i = 0; i < numOfDiplayItems; i++) {
			name = taskList.get(i).getName();
			workload = taskList.get(i).getWorkload();
			id = taskList.get(i).getId();
			deadline = taskList.get(i).getDeadlineDT();
			observableDeadline.add(new ObservableDeadlineTask(name, deadline, workload, id));
		}
	}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<DeadlineTask> get() {
		return observableDeadline;
	}
}
