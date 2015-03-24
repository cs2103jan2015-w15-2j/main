/*
 * @author David Zhao
 * */

package com.nexus.simplify.logic;


public class Display {
	
	public Display() {}
		
	public String execute(){
		TaskList updatedList = Logic.getTempList();
		CommandResult result = new CommandResult(updatedList, MESSAGE_DISPLAY_SUCCESS);
		return result;
	}
}
