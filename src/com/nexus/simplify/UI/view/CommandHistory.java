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
			outStack.push(inStack.pop());
			return inStack.peek();
		} else {
			return EMPTY_STRING;
		}
	}
	
	public String browseNextCommand() {
		if (!outStack.empty()) {
			inStack.push(outStack.pop()); 
			return outStack.peek();
		} else {
			return EMPTY_STRING;
		}		
	}
}
