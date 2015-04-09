//@author generated
package com.nexus.simplify.logic;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.api.Parser;

//@author A0094457U
/*
 * This class acts as a facade for other components to interact
 * with the logic component.
 */
public class Logic implements ILogic {
	private OperationType savedCommandType;
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
		String feedback;
		switch (command.getOperationType()) {
			case ADD :
				Add addOp = new Add();
				feedback = addOp.execute(command.getParameter());
				savedCommandType = null;
				return feedback;
			case DISPLAY :
				Display displayOp = new Display();
				feedback = displayOp.execute(command.getParameter());
				savedCommandType = null;
				return feedback;
			case MODIFY :
				Modify modifyOp = new Modify();
				feedback = modifyOp.execute(command.getParameter());
				savedCommandType = null;
				return feedback;
			case DELETE :
				Delete deleteOp = new Delete();
				feedback = deleteOp.execute(command.getParameter());
				savedCommandType = null;
				return feedback;
			case DONE :
				Done doneOp = new Done();
				feedback = doneOp.execute(command.getParameter());
				savedCommandType = OperationType.DONE;
				return feedback;
			case SEARCH :
				Search searchOp = new Search();
				feedback = searchOp.execute(command.getParameter(), command.getSearchField());
				savedCommandType = OperationType.SEARCH;
				return feedback;
			case UNDO :
				Undo undoOp = new Undo();
				feedback = undoOp.execute();
				savedCommandType = null;
				return feedback;
			case CLEAR :
				Clear clearOp = new Clear();
				feedback = clearOp.execute();
				savedCommandType = null;
				return feedback;
			case EXIT :
				Exit exitOp = new Exit();
				exitOp.execute();
				return null;
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
	
	public OperationType getSavedCommandType(){
		return savedCommandType;
	}
}