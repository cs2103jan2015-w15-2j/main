//@author generated
package com.nexus.simplify.logic;

import com.nexus.simplify.database.DatabaseConnector;

//@author A0094457U
/*
 * this class calls database to clear stored contents
 */
public class Clear {
	public Clear() {}
	
	String execute() {
		DatabaseConnector databaseConnector = new DatabaseConnector();
		databaseConnector.clearContent();
		String feedback = "All tasks cleared successfully.";
		return feedback;
	}
}
