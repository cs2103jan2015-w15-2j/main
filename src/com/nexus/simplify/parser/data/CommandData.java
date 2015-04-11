package com.nexus.simplify.parser.data;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;


public class CommandData {
	private static CommandData instance = null;
	private OperationType _userOp;
	private String[] _paramArray;
	private boolean[] _searchArray;
	private HashMap<String, OperationType> cmdHash = new HashMap<String, OperationType>();
	private Logger LOGGER = LoggerFactory.getLogger(CommandData.class.getName());
	

	public static CommandData getInstance() {
		if (instance == null) {
			instance = new CommandData();
		} 
		return instance;
	}
	
	public UserCommand createCommand() {
		OperationType tempOp = _userOp;
		String[] tempParam = _paramArray.clone();
		boolean[] tempSearch = _searchArray.clone();
		_userOp = null;
		_paramArray = new String[ParameterType.MAX_SIZE];
		LOGGER.info("User Operation: {}. User Parameters: {}. Search Boolean: {}", tempOp, Arrays.toString(tempParam), Arrays.toString(tempSearch));
		return new UserCommand(tempOp, tempParam, tempSearch);
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
		_searchArray[ParameterType.SEARCH_YEAR_POS] = true;
	}
	public void setMonthSearch() {
		_searchArray[ParameterType.SEARCH_MONTH_POS] = true;
	}
	public void setDayOfMonthSearch() {
		_searchArray[ParameterType.SEARCH_DAY_POS] = true;
	}
	public void setDayOfWeekSearch() {
		_searchArray[ParameterType.SEARCH_WEEKDAY_POS] = true;
	}
	public void setHourSearch() {
		_searchArray[ParameterType.SEARCH_HOUR_POS] = true;
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
		cmdHash.put("d", OperationType.DISPLAY);
		cmdHash.put("show", OperationType.DISPLAY);
		cmdHash.put("add", OperationType.ADD);
		cmdHash.put("a", OperationType.ADD);
		cmdHash.put("put", OperationType.ADD);
		cmdHash.put("p", OperationType.ADD);
		cmdHash.put("modify", OperationType.MODIFY);
		cmdHash.put("m", OperationType.MODIFY);
		cmdHash.put("change", OperationType.MODIFY);
		cmdHash.put("update", OperationType.MODIFY);
		cmdHash.put("delete", OperationType.DELETE);
		cmdHash.put("d", OperationType.DELETE);
		cmdHash.put("remove", OperationType.DELETE);
		cmdHash.put("done", OperationType.DONE);
		cmdHash.put("finish", OperationType.DONE);
		cmdHash.put("clear", OperationType.CLEAR);
		cmdHash.put("exit", OperationType.EXIT);
		cmdHash.put("quit", OperationType.EXIT);
		cmdHash.put("undo", OperationType.UNDO);
		cmdHash.put("u", OperationType.UNDO);
		cmdHash.put("search", OperationType.SEARCH);
		cmdHash.put("s", OperationType.SEARCH);
		cmdHash.put("find", OperationType.SEARCH);
		_paramArray = new String[ParameterType.MAX_SIZE];
		_searchArray = new boolean[ParameterType.SEARCH_MAX_SIZE];
		for (int i = 0; i < _searchArray.length; i++) {
			_searchArray[i] = false;
		}
		
	}

	public void setFileLocation(String filePathString) {
		_paramArray[ParameterType.NEW_FILELOCATION_POS] = filePathString;
		
	}
}
