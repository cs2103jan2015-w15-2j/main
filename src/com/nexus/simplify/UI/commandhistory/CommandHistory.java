package com.nexus.simplify.UI.commandhistory;

// @author A0108361M
import java.util.Deque;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serves as a cache to store commands previously entered by the user
 * so that the user can access and modify them easily, rather
 * than typing out the whole command. 
 * 
 * Functionality is similar to the Unix Terminal and Windows Command Prompt,
 * the up and down arrow keys traverse the cache, with the up arrow key accessing up to the 
 * earliest command, while the down arrow key navigates to the latest command.
 * 
 * Commands are stored in chronological order.
 * */
public class CommandHistory {
	
	private static final String LOGGER_INFO_USER_COMMAND_ADDED = "User command added to command history: {}";

	private static final String LOGGER_INFO_NEXT_COMMAND_ACCESSED = "Next command in command history accessed: {}";

	private static final String LOGGER_INFO_PREVIOUS_COMMAND_ACCESSED = "Previous command in command history accessed: {}";

	private static final String LOGGER_WARNING_DOWNSTACK_EMPTY = "downStack is empty";

	private static final String LOGGER_WARNING_UPSTACK_EMPTY = "upStack is empty";

	private static final String EMPTY_STRING = "";
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	/**
	 * Note: double-ended queues (Deque) are used in place of 
	 *       the deprecated Stack class.
	 * */
	private Deque<String> upStack;
	private Deque<String> downStack;
	
	private Logger logger;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public CommandHistory() {
		upStack = new LinkedList<String>();
		downStack = new LinkedList<String>();
		
		logger = LoggerFactory.getLogger(CommandHistory.class.getName());
	}
	
	//------------------//
	// Attribute Access //
	//------------------//
	
	public Deque<String> getUpStack() {
		return upStack;
	}
	
	public Deque<String> getDownStack() {
		return downStack;
	}
	
	
	//-----------------------//
	// Storing User Commands //
	//-----------------------//
	
	/**
	 * Adds recently entered user command to the stack.
	 * Consecutive identical commands are ignored.
	 * */
	public void addCommandToHistory(String userCommand) {
		if (!upStack.isEmpty()) {
			if (!userCommand.equals(upStack.peek())) {
				upStack.push(userCommand);
				logger.info(LOGGER_INFO_USER_COMMAND_ADDED, userCommand);
			}
		} else {
			upStack.push(userCommand);
			logger.info(LOGGER_INFO_USER_COMMAND_ADDED, userCommand);
		}
	}
	
	/**
	 * Removes all stored user commands.
	 * */
	public void clearAllHistory() {
		upStack.clear();
		downStack.clear();
	}
	
	//----------------------------//
	// Command History Navigation //
	//----------------------------//
	
	/**
	 * Navigates to an earlier command in the archive with the up arrow key.
	 * @return the previous user command entered (i.e. the command at the very top of upStack)
	 *         if the stack is not empty, otherwise an empty string is returned.
	 * */
	public String browsePreviousCommand() {
		if (!upStack.isEmpty()) {
			String commandToBeShown = upStack.pop();
			logger.info(LOGGER_INFO_PREVIOUS_COMMAND_ACCESSED, commandToBeShown);
			downStack.push(commandToBeShown);
			return commandToBeShown;	
		} else {
			logger.warn(LOGGER_WARNING_UPSTACK_EMPTY);
			return EMPTY_STRING;
		}
	}
	
	/**
	 * Navigates to a later command in the archive with the down arrow key.
	 * @return the next user command entered in the cache if downStack is not empty.
	 * Note: the user command at the top of downStack is not returned to give a
	 *       'pyramid' feel when navigating the cache. Instead, the command directly below
	 *       will be returned.
	 * */
	public String browseNextCommand() {
		if (!downStack.isEmpty()) {
			upStack.push(downStack.pop());
			if (!downStack.isEmpty()) {
				String commandToBeShown = downStack.peek();
				logger.info(LOGGER_INFO_NEXT_COMMAND_ACCESSED, commandToBeShown);
				return downStack.peek();
			} else  {
				logger.warn(LOGGER_WARNING_DOWNSTACK_EMPTY);
				return EMPTY_STRING;
			}
		} else {
			logger.warn(LOGGER_WARNING_DOWNSTACK_EMPTY);
			return EMPTY_STRING;
		}
	}
}
