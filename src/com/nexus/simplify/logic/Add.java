package com.nexus.simplify.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Add {
		
	public Add(){}
	
	String execute(String[] parameter) throws ParseException { 
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		int workload = Integer.parseInt(parameter[ParameterType.NEW_WORKLOAD_POS]);
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
}
