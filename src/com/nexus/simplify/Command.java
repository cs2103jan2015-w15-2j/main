package com.nexus.simplify;

import com.nexus.simplify.OperationType;

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
	
	public CommandResult executeSpecificCommand(OperationType operation, String[] parameter){
		switch(operation){
		
			case ADD: 
				Add addOperation = new Add();
				return addOperation.execute(parameter);
				
			case DISPLAY:
				Display displayOperation = new Display();
				return displayOperation.execute();
				
			case MODIFY:
				Modify modifyOperation = new Modify();
				return modifyOperation.execute(parameter);
				
			case DELETE:
				Delete deleteOperation = new Delete();
				return deleteOperation.execute(parameter);
				
		default:
			// need to return invalid operation here?
			return new CommandResult(Logic.getTempList(), "Invalid Command");
				
		}
	}
		
}
