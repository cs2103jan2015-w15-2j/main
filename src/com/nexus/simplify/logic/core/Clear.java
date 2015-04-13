//@author generated
package com.nexus.simplify.logic.core;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;

//@author A0094457U
/*
 * this class calls database to clear stored contents
 */
public class Clear {
	private final String SUCCESS = "All tasks cleared successfully.";
	public Clear() {}
	
	public String execute() {
		Database database = MainApp.getDatabase();
		database.clearContent();
		String feedback = SUCCESS;
		return feedback;
	}
}
