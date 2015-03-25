package com.nexus.simplify.logic;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This class determines what fields of a task to modify 
 * and calls APIs provided by database to modify storage.
 */
public class Modify {
	public Modify() {}
	
	public String execute(String[] parameter){
		// pattern follows Java.util.Date toString() method
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		int indexToModify;
		
		try{
			indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		}catch(NumberFormatException e){
			String feedback = "please enter a task index to modify.";
			return feedback;
		}
		
		Database database = MainApp.getDatabase();
		indexToModify = Integer.parseInt(parameter[ParameterType.INDEX_POS]);
		String feedback = "task ";
		String newName = parameter[ParameterType.NEW_NAME_POS];
		if(newName != null){
			database.modifyName(indexToModify, newName);
			feedback += "name ";
		}
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		if(newStartTime != null){
			Date startTime;
			try {
				startTime = df.parse(newStartTime);
				database.modifyStartTime(indexToModify, startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			feedback += "and start time ";
		}
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		if(newEndTime != null){
			Date endTime;
			try {
				endTime = df.parse(newEndTime);
				database.modifyStartTime(indexToModify, endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			feedback += "and end time ";
		}
		if(parameter[ParameterType.NEW_WORKLOAD_POS] != null){
			int newWorkload;
			try{
				newWorkload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
			}catch(NumberFormatException e){
				return "please enter a valid workload.";
			}
			
			newWorkload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
			
			try {
				database.modifyWorkload(indexToModify, newWorkload);
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			feedback += "and workload ";
		}
		feedback += "modified.";
		return feedback;
	}
}
