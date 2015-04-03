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
		inStack.push(userCommand);
	}
	
	//----------------------------//
	// Command History Navigation //
	//----------------------------//
	
	/**
	 * 
	 * 
	 * */
	public String browsePreviousCommand() {
		if (!inStack.isEmpty()) {
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
