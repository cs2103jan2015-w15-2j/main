//@author generated
package com.nexus.simplify.logic.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.logic.usercommand.OperationType;

//@author A0094457U
/*
 * This class acts as a switch for different type of operations and creates
 * classes related to specific operations to operate on parameters passed.
 */
public class CoreLogic {
	private final String MESSAGE_INVALID = "Please enter a valid command.";
	private static Logger logger = Logger.getLogger("Logic");
	
	public CoreLogic() {}
	
	public String execute(OperationType operationType, OperationType savedCommandType,
			String[] parameter, boolean[] searchField) throws Exception {
		Database database = new Database();
		String feedback;
		
		switch (operationType) {
			case ADD :
				Add addOp = new Add();
				if(savedCommandType != null && (savedCommandType.equals(OperationType.SEARCH)
					|| savedCommandType.equals(OperationType.DISPLAY))) {
					database.retrieveActiveTasklists();
				}
				logger.log(Level.INFO, "Calling database to add");
				feedback = addOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DISPLAY :
				Display displayOp = new Display();
				logger.log(Level.INFO, "Calling database to display");
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
				logger.log(Level.INFO, "Calling database to modify");
				feedback = modifyOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DELETE :
				Delete deleteOp = new Delete();
				logger.log(Level.INFO, "Calling database to delete");
				feedback = deleteOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case DONE :
				Done doneOp = new Done();
				logger.log(Level.INFO, "Calling database to mark done");
				feedback = doneOp.execute(parameter);
				assert !feedback.isEmpty();
				savedCommandType = null;
				return feedback;
			case SEARCH :
				Search searchOp = new Search();
				logger.log(Level.INFO, "Calling database to search");
				feedback = searchOp.execute(parameter, searchField);
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
}
