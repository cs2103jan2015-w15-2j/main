package com.nexus.simplify.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This class determines task type and calls database
 * to add a task into the storage.
 * @author David Zhao Han
 */
public class Add {
		
	public Add(){}
	
	String execute(String[] parameter) throws ParseException { 
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workloadStr = parameter[ParameterType.NEW_WORKLOAD_POS];
		int workload;
		
		if (workloadStr == null || workloadStr.isEmpty()) {
			workload = 0;
		} else {
			workload = Integer.parseInt(workloadStr);
		}
		String feedback;
		Database database = MainApp.getDatabase();
		
		if(name == null || name.isEmpty()){
			feedback = "please enter a name for this task.";
			return feedback;
		}
		
		if((newStartTime == null || newStartTime.isEmpty()) && (newEndTime == null || newEndTime.isEmpty())){
			database.addGenericTask(name, workload);
			feedback = "successfully added floating task \"" + name + "\".";
		} else {
			if(newStartTime.equals(newEndTime)){
				Date deadline = df.parse(newStartTime);
				database.addDeadlineTask(name,deadline,workload);
				feedback = "successfully added deadline task \"" + name + "\".";
			} else {

				Date startTime = df.parse(newStartTime);
				Date endTime = df.parse(newEndTime);
				database.addTimedTask(name,startTime,endTime,workload);
				feedback = "successfully added timed task \"" + name + "\".";
			}
		}
		return feedback;
	}
	
	// this method is for unit testing, which assumes that parser and
	// database function correctly
	public String executeForTesting(String[] parameter){
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workloadStringForm = parameter[ParameterType.NEW_WORKLOAD_POS];
		String feedback;
		int workload;
		
		if(workloadStringForm == null || workloadStringForm.isEmpty()){
			workload = 0;
		} else {
			workload = Integer.parseInt(workloadStringForm);
		}
		
		if(name == null || name.isEmpty()){
			feedback = "please enter a name for this task.";
			return feedback;
		}
		
		if((newStartTime == null || newStartTime.isEmpty()) && (newEndTime == null || newEndTime.isEmpty())){
			feedback = "successfully added floating task \"" + name + 
					"\" with workload " + String.valueOf(workload) + ".";
		} else{
			if(newStartTime.equals(newEndTime)){
				feedback = "successfully added deadline task \"" + name + 
					"\" with deadline " + newStartTime + " and workload of " +
					String.valueOf(workload) + ".";
			} else{
				feedback = "successfully added timed task \"" + name + 
					"\" with starting time " + newStartTime + " and ending time " +
					newEndTime + " and workload of " + String.valueOf(workload) + ".";
			}
		}
		return feedback;
	}
}
