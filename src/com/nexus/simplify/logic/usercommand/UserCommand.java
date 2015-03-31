package com.nexus.simplify.logic.usercommand;


public class UserCommand {
	private OperationType operation;
	private String[] parameter;

	public UserCommand(OperationType operation, String[] parameter){
		this.operation = operation;
		this.parameter = parameter;
	}

	public OperationType getOperationType(){
		return operation;
	}

	public String[] getParameter(){
		return parameter;
	}

	public String getIndex() {
		String index = parameter[ParameterType.INDEX_POS];
		return index;
	}
	
	public String getName() {
		String name = parameter[ParameterType.NEW_NAME_POS];
		return name;
	}
	
	public String getStartTime() {
		String startTime = parameter[ParameterType.NEW_STARTTIME_POS];
		return startTime;
	}
	
	public String getEndTime() {
		String endTime = parameter[ParameterType.NEW_ENDTIME_POS];
		return endTime;
	}
	
	public String getWorkload() {
		String workload = parameter[ParameterType.NEW_WORKLOAD_POS];
		return workload;
	}
	
	public String getFileLocation() {
		String fileLocation = parameter[ParameterType.NEW_FILELOCATION_POS];
		return fileLocation;
	}	
}
