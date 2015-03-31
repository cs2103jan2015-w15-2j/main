package com.nexus.simplify.parser.data;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.parser.DateTimeParser;


public class CommandData {
	private static CommandData instance = null;
	OperationType _userOp;
	String[] _paramArray;
	HashMap<String, OperationType> cmdHash = new HashMap<String, OperationType>();
	Logger LOGGER = LoggerFactory.getLogger(DateTimeParser.class.getName());

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

	// display preference string takes up the position of index in the parameter array
	// this is according to logic compoent
	public void setDisplay(String displayPref) {
		_paramArray[ParameterType.INDEX_POS] = displayPref;
	}

	public void setTaskIndex(String taskIndex) {
		_paramArray[ParameterType.INDEX_POS] = taskIndex;
	}

	public void setNewName(String name) {
		_paramArray[ParameterType.NEW_NAME_POS] = name;
	}

	public void setTime(String time) {
		_paramArray[ParameterType.NEW_STARTTIME_POS] = time;
		_paramArray[ParameterType.NEW_ENDTIME_POS] = time;
	}

	public void setTime(String time1, String time2){
		_paramArray[ParameterType.NEW_STARTTIME_POS] = time1;
		_paramArray[ParameterType.NEW_ENDTIME_POS] = time2;
	}

	public void setWorkload(String workload) {
		_paramArray[ParameterType.NEW_WORKLOAD_POS] = workload;
	}

	public void setYearSearch() {

	}
	public void setMonthSearch() {

	}
	public void setDayOfMonthSearch() {

	}
	public void setDayOfWeekSearch() {

	}
	public void setTimeSearch() {

	}

	public UserCommand createCommand() {
		OperationType tempOp = _userOp;
		String[] tempArray = _paramArray.clone();
		_userOp = null;
		_paramArray = new String[ParameterType.MAX_SIZE];
		LOGGER.info("User Operation: {}. User Parameters: {}", tempOp, Arrays.toString(tempArray));
		return new UserCommand(tempOp, tempArray);
	}

	public OperationType getOperationType(String keyword) {
		if (cmdHash.containsKey(keyword)) {
			return cmdHash.get(keyword);
		} else {
			return OperationType.INVALID;
		}

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
		cmdHash.put("delete", OperationType.DELETE);
		cmdHash.put("done", OperationType.DONE);
		cmdHash.put("clear", OperationType.CLEAR);
		cmdHash.put("undo", OperationType.UNDO);
		cmdHash.put("search", OperationType.SEARCH);	
		_paramArray = new String[ParameterType.MAX_SIZE];
	}

	public void setFileLocation(String filePathString) {
		_paramArray[ParameterType.NEW_FILELOCATION_POS] = filePathString;
		
	}
}
