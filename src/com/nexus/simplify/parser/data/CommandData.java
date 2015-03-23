package com.nexus.simplify.parser.data;

import java.util.Date;
import java.util.HashMap;

import com.nexus.simplify.OperationType;
import com.sun.prism.Texture.Usage;

public class CommandData {
	private static CommandData instance = null;
	OperationType _userOp;
	String[] _paramArray;
	HashMap<String, OperationType> cmdHash = new HashMap<String, OperationType>();

	public static CommandData getInstance() {
		if (instance == null) {
			instance = new CommandData();
		} 
		return instance;
	}
	
	public void setOp(OperationType userOp) {
		_userOp = userOp;
	}

	
	public void setInvalidOp() {
		_userOp = OperationType.INVALID;
	}

	public void setDisplay(String displayPref) {
		
	}
	
	public void setTaskIndex(String taskIndex) {

	}

	public void setNewName(String name) {

	}

	public void setTime(String string) {

	}

	public void setTime(String time1, String time2){

	}
	
	public void setWorkload(String workload) {
		
	}
	
	public void setFileLoc(String fileloc) {
		
	}
	
	public UserCommand createCommand() {
		return null;
	}
	
	public OperationType getOperationType(String keyword) {
		return cmdHash.get(keyword);
	}
	
	public OperationType getUserOp() {
		return _userOp;
	}
	
	/**
	 * Initialise command string hashtable by adding all recognised command strings 
	 * as keys to respective OperationType.
	 *
	 */
	private CommandData() {
		// adding support for all supported commands 
		cmdHash.put("display", OperationType.DISPLAY);
		cmdHash.put("add", OperationType.ADD);
		cmdHash.put("modify", OperationType.MODIFY);
		cmdHash.put("update", OperationType.MODIFY);
		cmdHash.put("clear", OperationType.CLEAR);
		cmdHash.put("done", OperationType.ARCHIVE);
		cmdHash.put("delete", OperationType.DELETE);
		cmdHash.put("filelocation", OperationType.FILELOCATION);
	}
}
