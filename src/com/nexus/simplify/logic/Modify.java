package com.nexus.simplify.logic;
import com.nexus.simplify.Database;
import com.nexus.simplify.logic.usercommand.ParameterType;

public class Modify {
	private static final int INVALID_OPTION = 0;
	private static final int NAME_ONLY = 1;
	private static final int DEADLINE_ONLY = 2;
	private static final int NAME_AND_DEADLINE = 3;
	private static final int WORKLOAD_ONLY = 4;
	private static final int NAME_AND_WORKLOAD = 5;
	private static final int DEADLINE_AND_WORKLOAD = 6;
	private static final int ALL_FIELDS = 7;
	 
	public Modify() {}
	
	public String execute(String[] parameter){
		String indexToModify = parameter[ParameterType.INDEX_POS];
		String newName = parameter[ParameterType.NEW_NAME_POS];
		String newDeadline = parameter[ParameterType.NEW_DEADLINE_POS];
		String newWorkload = parameter[ParameterType.NEW_WORKLOAD_POS];
		Database database = new Database();
		String feedback;
		
		int combinationNumber = decideCombination(newName,newDeadline,newWorkload);
	}
	
	private int decideCombination(String name, String deadline, String workload){
		if(name == null && deadline == null && workload == null){
			return INVALID_OPTION;
		}
		else if(name != null && deadline == null && workload)
	}
}
