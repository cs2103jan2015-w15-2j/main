package com.nexus.simplify.UI.commandhistory;

//@author A0108361M
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
	
	private static final String LOGGER_INFO_ADD_COMMAND_TO_EMPTY_UPSTACK = "Adding command to empty upStack";
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
	
	// for storing the state of upStack after every addition of user command
	private Deque<String> cacheStack;
	
	private Logger logger;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public CommandHistory() {
		upStack = new LinkedList<String>();
		downStack = new LinkedList<String>();
		
		cacheStack = new LinkedList<String>();
		
		logger = LoggerFactory.getLogger(CommandHistory.class.getName());
	}
	
	//------------------//
	// Attribute Access //
	//------------------//
	
	public String getCommandOnTopOfUpStack() {
		return upStack.peek();
	}
	
	public String getCommandOnTopOfDownStack() {
		return downStack.peek();
	}
		
	public int getUpStackSize() {
		return upStack.size();
	}
	
	public int getDownStackSize() {
		return downStack.size();
	}
	
	public int getCacheStackSize() {
		return cacheStack.size();
	}
	
	public Deque<String> getUpStack() {
		return upStack;
	}
		
	//-----------------------//
	// Storing User Commands //
	//-----------------------//
	
	/**
	 * Adds recently entered user command to the stack.
	 * Consecutive identical commands are ignored.
	 * */
	public void addCommandToHistory(String userCommand) {
		resetUpStackToPreviousState();
		if (!upStack.isEmpty() && !isConsecutiveDuplicate(userCommand)) {
			upStack.push(userCommand);
			storeUpStackStateInCache();
			logger.info(LOGGER_INFO_USER_COMMAND_ADDED, userCommand);
		} else if (upStack.isEmpty()) {
			upStack.push(userCommand);
			storeUpStackStateInCache();
			logger.info(LOGGER_INFO_ADD_COMMAND_TO_EMPTY_UPSTACK);
			logger.info(LOGGER_INFO_USER_COMMAND_ADDED, userCommand);
		}
	}

	/**
	 * @return true if the specified user command is identical to 
	 *         the command entered previously, false otherwise
	 * @param userCommand the command to be added to history
	 * */
	private boolean isConsecutiveDuplicate(String userCommand) {
		return userCommand.equals(upStack.peek());
	}

	/**
	 * makes a copy of the current state of upStack.
	 * */
	private void storeUpStackStateInCache() {
		cacheStack.clear();
		cacheStack.addAll(upStack);
	}

	/**
	 * returns upStack to its original state so that
	 * user commands can be continued to be added to history
	 * in proper chronological order.
	 * */
	private void resetUpStackToPreviousState() {
		upStack.clear();
		upStack.addAll(cacheStack);
		downStack.clear();
	}
	
	/**
	 * Removes all stored user commands.
	 * */
	public void clearAllHistory() {
		upStack.clear();
		downStack.clear();
		cacheStack.clear();
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
