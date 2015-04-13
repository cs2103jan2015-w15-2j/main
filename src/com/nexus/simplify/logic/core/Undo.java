//@author generated
package com.nexus.simplify.logic.core;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;

//@author A0094457U
/*
 * This class calls database to undo task.
 */
public class Undo {
	private final String SUCCESS = "Undo operation is successful.";
	public Undo(){}
	
	public String execute() throws Exception{
		Database database = MainApp.getDatabase();
		database.undoTask();
		String feedback = SUCCESS;
		return feedback;
	}
}
