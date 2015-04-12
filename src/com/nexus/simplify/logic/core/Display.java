//@author generated
package com.nexus.simplify.logic.core;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

//@author A0094457U
/*
 * This class calls database to display stored tasks
 * in a format chosen by the user. Currently supporting:
 * a number, null(default), all, week, deadline, workload, 
 * done, file location.
 * */
public class Display {
	private final String BY_DEFAULT_SETTING = "Displayed tasks by default setting.";
	private final String ALL_TASKS = "Displayed all tasks.";
	private final String WITHIN_WEEK = "Displayed tasks due within a week.";
	private final String BY_DEADLINE = "Displayed tasks by deadline.";
	private final String BY_WORKLOAD = "Displayed tasks by workload.";
	private final String DISPLAY_DONE = "Displayed tasks that are done.";
	private final String INVALID_OPTION = "Invalid option for display, please try again.";
	public Display() {}
		
	public String execute(String[] parameter) throws Exception {
		final String FILE_LOCATION = "file";
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
				feedback = BY_DEFAULT_SETTING;
				return feedback;
			}
			
			switch (option) {
				case "all" :
					database.toggleDisplay(option);
					feedback = ALL_TASKS;
					return feedback;
					
				case "week" :
					database.toggleDisplay(option);
					feedback = WITHIN_WEEK;
					return feedback;
				
				case "deadline" :
					database.toggleDisplay(option);
					feedback = BY_DEADLINE;
					return feedback;
					
				case "workload" :
					database.toggleDisplay(option);
					feedback = BY_WORKLOAD;
					return feedback;
					
				case FILE_LOCATION :
					feedback = "File location: ";
					feedback += database.getDataFileLocation();
					return feedback;
					
				case "done" :
					database.toggleDisplay(option);
					feedback = DISPLAY_DONE;
					return feedback;
					
				default :
					throw new Exception(INVALID_OPTION);
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
			if(Integer.parseInt(option) == 1) {
				feedback = "Displayed 1 task.";
			} else {
				feedback = "Displayed " + option + " tasks.";
			}
			return feedback;
		} else {
			if (option == null || option.isEmpty()) {
				feedback = BY_DEFAULT_SETTING;
				return feedback;
			}
			
			switch (option) {
				case "all" :
					feedback = ALL_TASKS;
					return feedback;
					
				case "week" :
					feedback = WITHIN_WEEK;
					return feedback;
				
				case "deadline" :
					feedback = BY_DEADLINE;
					return feedback;
				
				case "workload" :
					feedback = BY_WORKLOAD;
					return feedback;
					
				case "done" :
					feedback = DISPLAY_DONE;
					return feedback;

				default :
					feedback = INVALID_OPTION;
					return feedback;
			}
		}
	}
}
