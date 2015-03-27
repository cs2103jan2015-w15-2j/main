package com.nexus.simplify.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

/*
 * This class determines task type and calls database
 * to add the task into the storage
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
		if (workloadStr == null) {
			workload = 0;
		} else {
			workload = Integer.parseInt(workloadStr);
		}
		String feedback;
		Database database = MainApp.getDatabase();
		
		if(newStartTime == null && newEndTime == null){
			database.addGenericTask(name, workload);
			feedback = "successfully added floating task \"" + name + "\".";
		}
		else{
			if(newStartTime.equals(newEndTime)){
				Date deadline = df.parse(newStartTime);
				database.addDeadlineTask(name,deadline,workload);
				feedback = "successfully added dealine task \"" + name + "\".";
			}
			else{
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
	public String executeForTesting(String[] parameter){}
}
