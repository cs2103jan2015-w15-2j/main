package com.nexus.simplify;

public class Command {
	enum OperationType {
		ADD, DELETE, MODIFY, ARCHIVE, INVALID
	};
	
	OperationType operation;
	
	final String[] parameter;

	/**
	 * Constructor. Takes in a string array containing all parameters
	 * 
	 * @param parameter
	 */
	public Command(OperationType operation, String[] parameter) {
		super();
		this.parameter = parameter;
	}

	// Getters
	public String[] getParamter() {
		return parameter;
	}
	
	
	
	
}
