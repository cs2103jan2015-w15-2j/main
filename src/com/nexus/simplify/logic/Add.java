package com.nexus.simplify.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nexus.simplify.logic.usercommand.ParameterType;

public class Add {
		
	public Add(){}
	
	String execute(String[] parameter){ 
		String pattern = "E MMM dd hh:mm:ss zzz yyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String newStartTime = parameter[ParameterType.NEW_STARTTIME_POS];
		String newEndTime = parameter[ParameterType.NEW_ENDTIME_POS];
		String workload = parameter[ParameterType.NEW_WORKLOAD_POS];
		String feedback;
		Database database = new Database();
		
<<<<<<< HEAD
		if(deadline == null){
			database.addGenericTask(name,workload);
			feedback = "successfully added floating task \"" + name + "\".";
		}
		else{
			String[] splitResult = deadline.split(" ");
			if(splitResult.length == 2){
				String date = splitResult[0];
				String time = splitResult[1];
				String[] splitResult1 = date.split("/");
				String[] splitResult2 = time.split(":");
				int year = Integer.parseInt(splitResult1[0]);
				int month = Integer.parseInt(splitResult1[1]);
				int day = Integer.parseInt(splitResult1[2]);
				int hour = Integer.parseInt(splitResult2[0]);
				int minute = Integer.parseInt(splitResult2[1]);
				database.addTimed(name,year,month,day,hour,minute,workload);
				feedback = "successfully added timed task \"" + name + "\".";
			}
			else{
				database.addDeadlineTask(name,deadline,workload);
=======
		if(newStartTime == null && newEndTime == null){
			database.addFloating(name,workload);
			feedback = "successfully added floating task \"" + name + "\".";
		}
		else{
			if(newStartTime.equals(newEndTime)){
				Date deadline = df.parse(newStartTime);
				database.addDeadline(name,deadline,workload);
>>>>>>> 01e5f22a18081f28e0d4fe53426cc3f587ddb958
				feedback = "successfully added dealine task \"" + name + "\".";
			}
			else{
				Date startTime = df.parse(newStartTime);
				Date endTime = df.parse(newEndTime);
				database.addTimed(name,startTime,endTime,workload);
				feedback = "successfully added timed task \"" + name + "\".";
			}
		}
		return feedback;
	}
}
