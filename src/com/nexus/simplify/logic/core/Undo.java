//@author generated
package com.nexus.simplify.logic.core;

import com.nexus.simplify.database.api.Database;

//@author A0094457U
/*
 * This class calls database to undo task.
 */
public class Undo {
	public Undo(){}
	
	public String execute() throws Exception{
		Database database = new Database();
		database.undoTask();
		String feedback = "Undo operation is successful.";
		return feedback;
	}
}
