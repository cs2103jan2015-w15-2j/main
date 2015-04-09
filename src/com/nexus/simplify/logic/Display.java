//@author generated
package com.nexus.simplify.logic;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class calls database to display stored tasks
 * in a format chosen by the user. Currently supporting:
 * a number, null(default), all, week, deadline, workload, 
 * done, file location.
 * */
public class Display {
	
	public Display() {}
		
	String execute(String[] parameter) {
		String option = parameter[ParameterType.INDEX_POS];
		String feedback;
		Database database = MainApp.getDatabase();
		
		if(isNumeric(option)) {
			database.toggleDisplay(option);
			if(Integer.parseInt(option) == 1) {
				feedback = "Displayed 1 task.";
			} else {
				feedback = "Displayed " + option + " tasks.";
			}
			return feedback;
		} else {
			if (option == null || option.isEmpty()) {
				database.toggleDisplay("default");
				feedback = "Displayed tasks by default setting.";
				return feedback;
			}
			
			switch (option) {
				case "all" :
					database.toggleDisplay(option);
					feedback = "Displayed all tasks.";
					return feedback;
					
				case "week" :
					database.toggleDisplay(option);
					feedback = "Displayed tasks due within a week.";
					return feedback;
				
				case "deadline" :
					database.toggleDisplay(option);
					feedback = "Displayed tasks by deadline.";
					return feedback;
					
				case "workload" :
					database.toggleDisplay(option);
					feedback = "Displayed tasks by workload.";
					return feedback;
					
				case "file location" :
					feedback = "File location: ";
					feedback += database.getDataFileLocation();
					return feedback;
					
				case "done" :
					database.toggleDisplay(option);
					feedback = "Displayed tasks that are done.";
					return feedback;
					
				default :
					feedback = "Invalid option for display, please try again.";
					return feedback;
			}
		}
	}
	
	private static boolean isNumeric(String str) {
		return str.matches("\\d+");	// digits: [0-9]
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter) {
		String option = parameter[ParameterType.INDEX_POS];
		String feedback;
		
		if(isNumeric(option)) {
			feedback = "Displayed " + option + " tasks.";
			return feedback;
		} else {
			if (option == null || option.isEmpty()) {
				feedback = "Displayed tasks by default setting.";
				return feedback;
			}
			
			switch (option) {
				case "all" :
					feedback = "Displayed all tasks.";
					return feedback;
					
				case "week" :
					feedback = "Displayed tasks due within a week.";
					return feedback;
				
				case "deadline" :
					feedback = "Displayed tasks by deadline.";
					return feedback;
				
				case "workload" :
					feedback = "Displayed tasks by workload.";
					return feedback;
					
				case "done" :
					feedback = "Displayed tasks that are done.";
					return feedback;

				default :
					feedback = "Invalid option for display, please try again.";
					return feedback;
			}
		}
	}
}
