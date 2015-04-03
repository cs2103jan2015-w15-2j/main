package com.nexus.simplify.UI.view;

import java.util.Stack;

/**
 * @author Toh Jian Feng
 * */
public class CommandHistory {
	private static final String EMPTY_STRING = "";
	private Stack<String> inStack;
	private Stack<String> outStack;
	
	public CommandHistory() {
		inStack = new Stack<String>();
		outStack = new Stack<String>();
	}
	
	public void addCommandToHistory(String userCommand) {
		inStack.push(userCommand);
	}
	
	public String browsePreviousCommand() {
		if (!inStack.isEmpty()) {
			String commandToBeShown = inStack.peek();
			outStack.push(inStack.pop());
			return commandToBeShown;
			
		} else {
			return EMPTY_STRING;
		}
	}
	
	public String browseNextCommand() {
		if (!outStack.isEmpty()) {
			inStack.push(outStack.pop());
			if (!outStack.isEmpty()) {
				return outStack.peek();
			} else  {
				return EMPTY_STRING;
			}
		} else {
			return EMPTY_STRING;
		}
	}
}
