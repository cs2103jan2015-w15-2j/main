package com.nexus.simplify.logic;

import com.nexus.simplify.usercommand.ParameterType;

public class Add {
		
	public Add(){}
	
	String execute(String[] parameter){ 
		
		String name = parameter[ParameterType.NEW_NAME_POS];
		String deadline = parameter[ParameterType.NEW_DEADLINE_POS];
		String workload = parameter[ParameterType.NEW_WORKLOAD_POS];
		String feedback;
		Database database = new Database();
		
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
				feedback = "successfully added dealine task \"" + name + "\".";
			}
		}
		return feedback;
	}
}
