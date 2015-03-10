package com.nexus.simplify;

import com.nexus.simplify.Parser.OperationType;

public class Command {
	
	final OperationType operation;
	
	final String[] parameter;

	/**
	 * Constructor. Takes in a string array containing all parameters
	 * 
	 * @param parameter
	 */
	public Command(OperationType operation, String[] parameter) {
		this.operation = operation;
		this.parameter = parameter;
	}

	public OperationType getOperation() {
		return operation;		
	}
	
	public String[] getParameter() {
		return parameter;
	}
	
	public Result executeSpecificCommand(OperationType operation, String[] parameter){
		switch(operation){
			case : ADD
		}
	}
		
}
