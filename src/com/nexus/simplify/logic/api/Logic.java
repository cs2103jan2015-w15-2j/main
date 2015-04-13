//@author generated
package com.nexus.simplify.logic.api;

import com.nexus.simplify.logic.core.CoreLogic;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.api.Parser;

//@author A0094457U
/*
 * This class acts as a facade for other components to interact
 * with the logic component.
 */
public class Logic implements ILogic {
	private static Logic theOne;
	private OperationType savedCommandType;
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
		OperationType operationType = command.getOperationType();
		String[] parameter = command.getParameter();
		boolean[] searchField = command.getSearchField();
		
		CoreLogic core = new CoreLogic();
		feedback = core.execute(operationType, savedCommandType, parameter, searchField);
		return feedback;
	}
	
	public Parser getParser() {
		return parser;
	}

	public UserCommand getParsedCommand(String userInput) throws Exception {
		UserCommand command = parser.parseInput(userInput);
		return command;
	}
}