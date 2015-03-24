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
}
