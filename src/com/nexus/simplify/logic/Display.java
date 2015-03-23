/*
 * @author David Zhao
 * */

package com.nexus.simplify.logic;


public class Display {
	
	private static final String MESSAGE_DISPLAY_SUCCESS = "successfully displayed.";

	//---------------------//
	// Default Constructor //
	//---------------------//
	
	public Display() {
		
	}
	
	//---------------------------//
	// Executing Display Command //
	//---------------------------//
	
	public CommandResult execute(){
		TaskList updatedList = Logic.getTempList();
		CommandResult result = new CommandResult(updatedList, MESSAGE_DISPLAY_SUCCESS);
		return result;
	}
}
