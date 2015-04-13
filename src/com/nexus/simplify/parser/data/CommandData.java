//@author A0111035A

package com.nexus.simplify.parser.data;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;

/**
 * A singleton which holds data such as the user specified operation and
 * parameters from parsing of token lists generated from user command string
 * input.
 *
 */
public class CommandData {
	private static final String LOG_COMMAND_DATA = "User Operation: {}. User Parameters: {}. Search Boolean: {}";

	private static CommandData _instance = null;
	private static OperationType _userOp;
	private static String[] _paramArray;
	private static boolean[] _searchArray;
	private static HashMap<String, OperationType> _cmdHash = new HashMap<String, OperationType>();
	private static Logger LOGGER = LoggerFactory.getLogger(CommandData.class
			.getName());

	/**
	 * Returns {@code CommandData} singleton. Replaces a constructor as
	 * singletons are implemented as "static" classes. getInstance method will
	 * create a static instance of CommandData if CommandData was not
	 * instantiated.
	 * 
	 * @return The only instance of CommandData.
	 */
	public static CommandData getInstance() {
		if (_instance == null) {
			_instance = new CommandData();
		}
		return _instance;
	}

	/**
	 * Return a newly created {@link UserCommand} object which is populated with
	 * operation requested and various parameters in a string array. Parameter
	 * types that are not provided are left as {@code null}.
	 * 
	 * Stored data is reset after UserCommand is created.
	 * 
	 * @return UserCommand object with user specified operation and parameters.
	 */
	public UserCommand createCommand() {
		OperationType tempOp = _userOp;
		String[] tempParam = _paramArray.clone();
		boolean[] tempSearch = _searchArray.clone();
		_userOp = null;
		_paramArray = new String[ParameterType.MAX_SIZE];
		LOGGER.info(LOG_COMMAND_DATA, tempOp, Arrays.toString(tempParam),
				Arrays.toString(tempSearch));
		return new UserCommand(tempOp, tempParam, tempSearch);
	}

	/**
	 * Set specified operation type to class variable.
	 * 
	 * @see OperationType
	 */
	public void setOp(OperationType userOp) {
		_userOp = userOp;
	}

	/**
	 * Sets user operation type to OperationType.INVALID.
	 * 
	 * @see OperationType
	 */
	public void setInvalidOp() {
		_userOp = OperationType.INVALID;
	}

	/**
	 * Sets the specified display preference string in CommandData parameter
	 * array.
	 * 
	 * @param displayPref
	 *            The type of display action that the user wants.
	 */
	public void setDisplay(String displayPref) {
		// Logic will take display preference string from index paramater
		// position in the parameter array
		_paramArray[ParameterType.INDEX_POS] = displayPref;
	}

	/**
	 * Sets the specified task index in CommandData parameter array.
	 * 
	 * @param taskIndex
	 *            Index of a task as shown on Simplify's UI.
	 */
	public void setTaskIndex(String taskIndex) {
		_paramArray[ParameterType.INDEX_POS] = taskIndex;
	}

	/**
	 * Sets the specified task index in CommandData parameter array.
	 * 
	 * @param taskIndex
	 */
	public void setNewName(String name) {
		_paramArray[ParameterType.NEW_NAME_POS] = name;
	}

	/**
	 * Sets the specified single DateTime in CommandData parameter array.
	 * 
	 * @param time
	 *            String representation of java DateTime.
	 */
	public void setTime(String time) {
		_paramArray[ParameterType.NEW_STARTTIME_POS] = time;
		_paramArray[ParameterType.NEW_ENDTIME_POS] = time;
	}

	/**
	 * Sets the specified starting and ending DateTime in CommandData parameter
	 * array.
	 * 
	 * @param time1
	 *            String representation of starting DateTime specified by user.
	 * @param time2
	 *            String representation of ending DateTime specified by user.
	 */
	public void setTime(String time1, String time2) {
		_paramArray[ParameterType.NEW_STARTTIME_POS] = time1;
		_paramArray[ParameterType.NEW_ENDTIME_POS] = time2;
	}

	/**
	 * Sets the specified string representation of workload integer value in
	 * CommandData parameter array.
	 * 
	 * @param workload
	 *            String representation of workload integer value.
	 */
	public void setWorkload(String workload) {
		_paramArray[ParameterType.NEW_WORKLOAD_POS] = workload;
	}

	/**
	 * Sets boolean value to {@code true} to signal that the user has searched
	 * for a year value.
	 */
	public void setYearSearch() {
		_searchArray[ParameterType.SEARCH_YEAR_POS] = true;
	}

	/**
	 * Sets boolean value to {@code true} to signal that the user has searched
	 * for a month value.
	 */
	public void setMonthSearch() {
		_searchArray[ParameterType.SEARCH_MONTH_POS] = true;
	}

	/**
	 * Sets boolean value to {@code true} to signal that the user has searched
	 * for a day of month value.
	 */
	public void setDayOfMonthSearch() {
		_searchArray[ParameterType.SEARCH_DAY_POS] = true;
	}

	/**
	 * Sets boolean value to {@code true} to signal that the user has searched
	 * for a weekday value.
	 */
	public void setDayOfWeekSearch() {
		_searchArray[ParameterType.SEARCH_WEEKDAY_POS] = true;
	}

	/**
	 * Sets boolean value to {@code true} to signal that the user has searched
	 * for an hour value.
	 */
	public void setHourSearch() {
		_searchArray[ParameterType.SEARCH_HOUR_POS] = true;
	}

	/**
	 * Returns the OperationType that corresponds to the specified keyword
	 * string.
	 * 
	 * @param keyword
	 *            String for user specified operation command keyword.
	 * @return Corresponding OperationType for the specified keyword. Return an
	 *         invalid OperationType if specified keyword does not map to any
	 *         operation.
	 */
	public OperationType getOperationType(String keyword) {
		if (_cmdHash.containsKey(keyword)) {
			return _cmdHash.get(keyword);
		} else {
			return OperationType.INVALID;
		}

	}

	/**
	 * Returns the OperationType that is stored in CommandData that may have be
	 * set previously
	 * 
	 * @return OperationType that is stored in Command Data. Returns
	 *         {@code null} if no OperationType was set.
	 */
	public OperationType getUserOp() {
		return _userOp;
	}

	/**
	 * Hidden constructor for CommandData so that no new instance of CommandData can be made externally. 
	 * 
	 * Initialise command string hashtable by adding all recognised command
	 * strings as keys to respective OperationType.
	 *
	 */
	private CommandData() {
		initOpHash();

		for (int i = 0; i < _searchArray.length; i++) {
			_searchArray[i] = false;
		}

	}

	/**
	 * Adds all supported operation keyword into HashMap with the corresponding
	 * OperationType
	 */
	private void initOpHash() {
		// Adding support for all supported commands
		_cmdHash.put("display", OperationType.DISPLAY);
		_cmdHash.put("show", OperationType.DISPLAY);
		_cmdHash.put("add", OperationType.ADD);
		_cmdHash.put("a", OperationType.ADD);
		_cmdHash.put("put", OperationType.ADD);
		_cmdHash.put("p", OperationType.ADD);
		_cmdHash.put("modify", OperationType.MODIFY);
		_cmdHash.put("m", OperationType.MODIFY);
		_cmdHash.put("change", OperationType.MODIFY);
		_cmdHash.put("update", OperationType.MODIFY);
		_cmdHash.put("delete", OperationType.DELETE);
		_cmdHash.put("remove", OperationType.DELETE);
		_cmdHash.put("done", OperationType.DONE);
		_cmdHash.put("finish", OperationType.DONE);
		_cmdHash.put("clear", OperationType.CLEAR);
		_cmdHash.put("exit", OperationType.EXIT);
		_cmdHash.put("quit", OperationType.EXIT);
		_cmdHash.put("undo", OperationType.UNDO);
		_cmdHash.put("u", OperationType.UNDO);
		_cmdHash.put("search", OperationType.SEARCH);
		_cmdHash.put("s", OperationType.SEARCH);
		_cmdHash.put("find", OperationType.SEARCH);
		_paramArray = new String[ParameterType.MAX_SIZE];
		_searchArray = new boolean[ParameterType.SEARCH_MAX_SIZE];
	}

	public void setFileLocation(String filePathString) {
		_paramArray[ParameterType.NEW_FILELOCATION_POS] = filePathString;

	}
}
