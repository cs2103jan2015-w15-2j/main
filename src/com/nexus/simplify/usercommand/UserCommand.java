package com.nexus.simplify.usercommand;

public class UserCommand {
	private OperationType operation;
	private int parameterType;
	
	public UserCommand(OperationType operation, int parameterType){
		this.operation = operation;
		this.parameterType = parameterType;
	}
	
	public OperationType getOperationType(){
		return operation;
	}
	
	public int getParameterType(){
		return parameterType;
	}
}
