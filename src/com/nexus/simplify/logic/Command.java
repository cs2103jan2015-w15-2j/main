package com.nexus.simplify.logic;

import com.nexus.simplify.usercommand.*;


public class Command {
	
	public Command() {}
	
	public String executeCommand(String userInput){
		Parser parser = new Parser();
		UserCommand command = parser.parseInput(userInput);
		switch(command.getOperationType()){
			case ADD:
				Add addOp = new Add();
				return addOp.execute(command);
			case DISPLAY:
				Display displayOp = new Display();
				return displayOp.execute(command);
			case MODIFY:
				Modify modifyOp = new Modify();
				return modifyOp.execute(command);
			case DELETE:
				Delete deleteOp = new Delete();
				return deleteOp.execute(command);
			case DONE:
				Done doneOp = new Done();
				return doneOp.execute(command);
			default INVALID:
				Invalid invalidOp = new Invalid();
				return invalidOp.execute();
		}
	}		
}
