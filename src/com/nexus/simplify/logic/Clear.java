//@author generated
package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;

//@author A0094457U
/*
 * this class calls database to clear stored contents
 */
public class Clear {
	public Clear() {}
	
	public String execute() {
		Database database = MainApp.getDatabase();
		database.clearContent();
		String feedback = "All tasks cleared successfully.";
		return feedback;
	}
}
