package com.nexus.simplify;

// This class is a backup in case we don't want to use enum.
// This class is accidentally created due to one guy's misconception that OperationType is a class...
public class OperationType {
	private String operation;
	
	public OperationType(String operation){
		this.operation = operation;
	}
	
	public String getOperation(){
		return operation;
	}
	
}
