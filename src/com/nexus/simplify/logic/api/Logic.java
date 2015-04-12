//@author generated
package com.nexus.simplify.logic.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.nexus.simplify.logic.Add;
import com.nexus.simplify.logic.Clear;
import com.nexus.simplify.logic.Delete;
import com.nexus.simplify.logic.Display;
import com.nexus.simplify.logic.Done;
import com.nexus.simplify.logic.Exit;
import com.nexus.simplify.logic.Modify;
import com.nexus.simplify.logic.Search;
import com.nexus.simplify.logic.Undo;
import com.nexus.simplify.database.DatabaseConnector;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.api.Parser;

//@author A0094457U
/*
 * This class acts as a facade for other components to interact
 * with the logic component.
 */
public class Logic implements ILogic {
	private final String MESSAGE_INVALID = "Please enter a valid command.";
	private static Logic theOne;
	private static Logger logger = Logger.getLogger("Logic");
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
		DatabaseConnector database = new DatabaseConnector();
		
		switch (operationType) {
			case ADD :
				Add addOp = new Add();
				if(savedCommandType != null && (savedCommandType.equals(OperationType.SEARCH)
					|| savedCommandType.equals(OperationType.DISPLAY))) {
					database.retrieveActiveTasklists();
				}
				logger.log(Level.INFO, "Calling database to add");
				feedback = addOp.execute(command.getParameter());
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DISPLAY :
				Display displayOp = new Display();
				logger.log(Level.INFO, "Calling database to display");
				feedback = displayOp.execute(command.getParameter());
				assert !feedback.isEmpty();
				savedCommandType = OperationType.DISPLAY;
				return feedback;
			case MODIFY :
				Modify modifyOp = new Modify();
				if(savedCommandType != null && (savedCommandType.equals(OperationType.SEARCH)
						|| savedCommandType.equals(OperationType.DISPLAY))) {
					database.retrieveActiveTasklists();
				}
				logger.log(Level.INFO, "Calling database to modify");
				feedback = modifyOp.execute(command.getParameter());
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DELETE :
				Delete deleteOp = new Delete();
				logger.log(Level.INFO, "Calling database to delete");
				feedback = deleteOp.execute(command.getParameter());
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DONE :
				Done doneOp = new Done();
				logger.log(Level.INFO, "Calling database to mark done");
				feedback = doneOp.execute(command.getParameter());
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case SEARCH :
				Search searchOp = new Search();
				logger.log(Level.INFO, "Calling database to search");
				feedback = searchOp.execute(command.getParameter(), command.getSearchField());
				assert !feedback.isEmpty();
				savedCommandType = OperationType.SEARCH;
				return feedback;
			case UNDO :
				Undo undoOp = new Undo();
				logger.log(Level.INFO, "Calling database to undo");
				feedback = undoOp.execute();
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case CLEAR :
				Clear clearOp = new Clear();
				logger.log(Level.INFO, "Calling database to clear");
				feedback = clearOp.execute();
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case EXIT :
				Exit exitOp = new Exit();
				logger.log(Level.INFO, "Calling system to exit");
				exitOp.execute();
				return null;
			default:
				throw new Exception(MESSAGE_INVALID);
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