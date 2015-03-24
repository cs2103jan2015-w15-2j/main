package com.nexus.simplify.logic;
import com.nexus.simplify.Database;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.UserCommand;

public class Logic implements ILogic {
	
	private static Logic theOne;
	
	private Logic() {}
	
	public static Logic getInstance(){
		if(theOne == null){
			theOne = new Logic();
		}
		return theOne;
	}
	
	@Override
	public String executeCommand(String userInput){
		Parser parser = new Parser();
		UserCommand command = parser.parseInput(userInput);
		switch(command.getOperationType()){
			case ADD:
				Add addOp = new Add();
				return addOp.execute(command.getParameter());
			case DISPLAY:
				Display displayOp = new Display();
				return displayOp.execute(command.getParameter());
			case MODIFY:
				Modify modifyOp = new Modify();
				return modifyOp.execute(command.getParameter());
			case DELETE:
				Delete deleteOp = new Delete();
				return deleteOp.execute(command.getParameter());
			case DONE:
				Done doneOp = new Done();
				return doneOp.execute(command.getParameter());
			default:
				Invalid invalidOp = new Invalid();
				return invalidOp.execute();
		}
	}		
}