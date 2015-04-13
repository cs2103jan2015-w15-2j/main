//@author generated
package com.nexus.simplify.logic.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.OperationType;

//@author A0094457U
/*
 * This class acts as a switch for different type of operations and creates
 * classes related to specific operations to operate on parameters passed.
 */
public class CoreLogic {
	private final String MESSAGE_INVALID = "Please enter a valid command.";
	private final String LOGGER_ADD = "Calling database to add";
	private final String LOGGER_DISPLAY = "Calling database to display";
	private final String LOGGER_MODIFY = "Calling database to modify";
	private final String LOGGER_DELETE = "Calling database to delete";
	private final String LOGGER_DONE = "Calling database to mark done";
	private final String LOGGER_SEARCH = "Calling database to search";
	private final String LOGGER_UNDO = "Calling database to undo";
	private final String LOGGER_CLEAR = "Calling database to clear";
	private final String LOGGER_EXIT = "Calling system to exit";
	private static Logger logger = Logger.getLogger("Logic");
	
	public CoreLogic() {}
	
	public String execute(OperationType operationType, OperationType savedCommandType,
			String[] parameter, boolean[] searchField) throws Exception {
		Database database = MainApp.getDatabase();
		String feedback;
		
		switch (operationType) {
			case ADD :
				Add addOp = new Add();
				if(savedCommandType != null && (savedCommandType.equals(OperationType.SEARCH)
					|| savedCommandType.equals(OperationType.DISPLAY))) {
					database.retrieveActiveTasklists();
				}
				logger.log(Level.INFO, LOGGER_ADD);
				feedback = addOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DISPLAY :
				Display displayOp = new Display();
				logger.log(Level.INFO, LOGGER_DISPLAY);
				feedback = displayOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = OperationType.DISPLAY;
				return feedback;
			case MODIFY :
				Modify modifyOp = new Modify();
				if(savedCommandType != null && (savedCommandType.equals(OperationType.SEARCH)
						|| savedCommandType.equals(OperationType.DISPLAY))) {
					database.retrieveActiveTasklists();
				}
				logger.log(Level.INFO, LOGGER_MODIFY);
				feedback = modifyOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DELETE :
				Delete deleteOp = new Delete();
				logger.log(Level.INFO, LOGGER_DELETE);
				feedback = deleteOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DONE :
				Done doneOp = new Done();
				logger.log(Level.INFO, LOGGER_DONE);
				feedback = doneOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case SEARCH :
				Search searchOp = new Search();
				logger.log(Level.INFO, LOGGER_SEARCH);
				feedback = searchOp.execute(parameter, searchField);
				assert !feedback.isEmpty();
				savedCommandType = OperationType.SEARCH;
				return feedback;
			case UNDO :
				Undo undoOp = new Undo();
				logger.log(Level.INFO, LOGGER_UNDO);
				feedback = undoOp.execute();
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case CLEAR :
				Clear clearOp = new Clear();
				logger.log(Level.INFO, LOGGER_CLEAR);
				feedback = clearOp.execute();
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case EXIT :
				Exit exitOp = new Exit();
				logger.log(Level.INFO, LOGGER_EXIT);
				exitOp.execute();
				return null;
			default:
				throw new Exception(MESSAGE_INVALID);
		}
	}
}
