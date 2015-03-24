/*
 * @author David Zhao Han
 * */

package com.nexus.simplify.logic;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Display {
	
	public Display() {}
		
	public String execute(String[] parameter){
		String option = parameter[ParameterType.INDEX_POS];
		String feedback;
		Database database = MainApp.getDatabase();
		if(isNumeric(option)){
			database.toggleDisplay(option);
			feedback = "displayed " + option + "tasks.";
			return feedback;
		}
		else{
			switch(option){
				case "all":
					database.toggleDisplay(option);
					feedback = "displayed all tasks.";
					return feedback;
				case "week":
					database.toggleDisplay(option);
					feedback = "displayed tasks due within a week.";
					return feedback;
				case "deadline":
					database.toggleDisplay(option);
					feedback = "displayed tasks by deadline";
					return feedback;
				case "workload":
					database.toggleDisplay(option);
					feedback = "displayed tasks by workload";
					return feedback;
				case null:
					database.toggleDisplay("default");
					feedback = "displayed tasks by default setting";
					return feedback;
				default:
					feedback = "invalid option for display, please try again.";
					return feedback;
			}
		}
	}
	
	private static boolean isNumeric(String str){
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
}
