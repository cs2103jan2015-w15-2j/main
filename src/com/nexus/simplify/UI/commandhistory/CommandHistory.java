package com.nexus.simplify.UI.commandhistory;

// @author A0108361M
import java.util.Stack;

public class CommandHistory {
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	private static final String EMPTY_STRING = "";
	private Stack<String> inStack;
	private Stack<String> outStack;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public CommandHistory() {
		inStack = new Stack<String>();
		outStack = new Stack<String>();
	}
	
	//-------------------//
	// 
	
	public void addCommandToHistory(String userCommand) {
		if (!inStack.empty()) {
			if (!userCommand.equals(inStack.peek())) {
				inStack.push(userCommand);
			}
		} else {
			inStack.push(userCommand);
		}
	}
	
	//----------------------------//
	// Command History Navigation //
	//----------------------------//
	
	/**
	 * 
	 * 
	 * */
	public String browsePreviousCommand() {
		if (!inStack.empty()) {
			String commandToBeShown = inStack.peek();
			outStack.push(inStack.pop());
			return commandToBeShown;
			
		} else {
			return EMPTY_STRING;
		}
	}
	
	/**
	 * 
	 * 
	 * */
	public String browseNextCommand() {
		if (!outStack.empty()) {
			inStack.push(outStack.pop());
			if (!outStack.empty()) {
				return outStack.peek();
			} else  {
				return EMPTY_STRING;
			}
		} else {
			return EMPTY_STRING;
		}
	}
}
