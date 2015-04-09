//@author generated
package com.nexus.simplify.logic;

import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.api.Parser;

//@author A0094457U
/*
 * This class acts as a facade for other components to interact
 * with the logic component.
 */
public class Logic implements ILogic {
	
	private static Logic theOne;
	private Parser parser = new Parser();
	
	private Logic() {}
	
	public static Logic getInstance() {
		if(theOne == null){
			theOne = new Logic();
		}
		return theOne;
	}
	
	@Override
	public String executeCommand(String userInput) throws Exception {
		UserCommand command = getParsedCommand(userInput);
		switch (command.getOperationType()) {
			case ADD :
				Add addOp = new Add();
				return addOp.execute(command.getParameter());
			case DISPLAY :
				Display displayOp = new Display();
				return displayOp.execute(command.getParameter());
			case MODIFY :
				Modify modifyOp = new Modify();
				return modifyOp.execute(command.getParameter());
			case DELETE :
				Delete deleteOp = new Delete();
				return deleteOp.execute(command.getParameter());
			case DONE :
				Done doneOp = new Done();
				return doneOp.execute(command.getParameter());
			case SEARCH :
				Search searchOp = new Search();
				return searchOp.execute(command.getParameter(), command.getSearchField());
			case UNDO :
				Undo undoOp = new Undo();
				return undoOp.execute();
			case CLEAR :
				Clear clearOp = new Clear();
				return clearOp.execute();
			case EXIT :
				Exit exitOp = new Exit();
				return exitOp.execute();
			default:
				return null;
		}
	}
	
	public Parser getParser() {
		return parser;
	}

	public UserCommand getParsedCommand(String userInput) throws Exception {
		UserCommand command = parser.parseInput(userInput);
		return command;
	}		
}