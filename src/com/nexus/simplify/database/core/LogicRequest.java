//@author A0111035A
package com.nexus.simplify.database.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;

/**
 * Helper class for testing purposes. Allows to track the method and parameters that
 * Logic calls on Database
 * 
 *  */
public class LogicRequest {
	OperationType _operation;
	Object[] _parameter;
	OperationType _displayOp = OperationType.DISPLAY;
	OperationType _addOp = OperationType.ADD;
	OperationType _modifyOp = OperationType.MODIFY;
	OperationType _deleteOp = OperationType.DELETE;
	OperationType _doneOp = OperationType.DONE;
	OperationType _clearOp = OperationType.CLEAR;
	OperationType _undoOp = OperationType.UNDO;
	OperationType _searchOp = OperationType.SEARCH;
	OperationType _invalidOp = OperationType.INVALID;
	Logger LOGGER = LoggerFactory.getLogger(LogicRequest.class.getName());
	
	public LogicRequest() {
		_operation = OperationType.INVALID;
		_parameter = new Object[ParameterType.MAX_SIZE];
	}
	
	public void reset() {
		_operation = OperationType.INVALID;
		_parameter = new Object[ParameterType.MAX_SIZE];
	}

	/**
	 * Track the parameter called onto addTimedTask in Database
	 * 
	 * @param name name of task
	 * @param startTime time and date when task starts
	 * @param endTime time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 */
	public void addTimedTask(String name, Date startTime, Date endTime, int workload) {
		LOGGER.info("addTimedTasked is called. Name: {}, StartTime: {}, EndTime: {}, Workload: {}",
				name, startTime, endTime, workload);
		setOperationType(_addOp);
		setName(name);
		setStartTime(startTime);
		setEndTime(endTime);
		setWorkload(workload);
	}
	
	/**
	 * Track the parameter called onto addDeadlineTask in Database.
	 * 
	 * @param name name of task
	 * @param deadline time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * 
	 */
	public void addDeadlineTask(String name, Date deadline, int workload) {
		LOGGER.info("addDeadlineTask is called. Name: {}, Deadline: {}, Workload: {}",
				name, deadline, workload);
		setOperationType(_addOp);
		setName(name);
		setStartTime(deadline);
		setEndTime(deadline);
		setWorkload(workload);
	}
	
	/**
	 * Track the parameter called onto addGenericTask in Database.
	 * 
	 * @param name name of task
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 *
	 */
	public void addGenericTask(String name, int workload) {
		LOGGER.info("addGenericTask is called. Name: {}, Workload: {}",
				name, workload);
		setOperationType(_addOp);
		setName(name);
		setWorkload(workload);
	}
	
	/**
	 * Track the parameter called onto deleteTaskByIndex() in Database.
	 * 
	 * @param index index of task with respect to the billboard
	 * @throws IndexOutOfBoundsException if index is not within range of 1 - 15 inclusive.
	 * 
	 */
	public void deleteTaskByIndex(int index) {
		LOGGER.info("deleteTaskByIndex is called. Index: {}", index);
		setOperationType(_deleteOp);
		setIndex(index);
	}
	
	/**
	 * Track the parameter called onto clearContent in Database.
	 */
	public void clearContent() {
		LOGGER.info("clearContent is called.");
		setOperationType(_clearOp);
	}
	
	// Not implemented yet
//	public void searchDatabase(String[] parameter, boolean[] searchField) {
//		setOperationType(_searchOp);
//	}
	
	// Not implemented yet
//	/**
//	 * Track the parameter called onto toggleDisplay in Database.
//	 * 
//	 * @param option toggles the sorting order of the task lists
//	 * */
//	public void toggleDisplay(String option) {
//		setOperationType(_displayOp);
//	}
	
	/**
	 * Track the parameter called onto modifyName in Database.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newName new name to be written to the task
	 *  
	 * */
	public void modifyName(int index, String newName) {
		LOGGER.info("modifyName is called. Index: {}, Name: {}", index, newName);
		setOperationType(_modifyOp);
		setIndex(index);
		setName(newName);
	}
	
	/**
	 * Track the parameter called onto modifyWorkload in Database.
	 * 
	 * @param index index of task with respect to the billboard
	 * @param newWorkloadValue new value of workload to be written to the task
	 *
	 * */
	public void modifyWorkload(int index, int newWorkloadValue) {
		LOGGER.info("modifyWorkload is called. Index: {}, Workload: {}", index, newWorkloadValue);
		setOperationType(_modifyOp);
		setIndex(index);
		setWorkload(newWorkloadValue);
	}
	
	/**
	 * Track the parameter called onto modifyStartTime in Database.
	 *  
	 * @param index index of task with respect to the billboard
	 * @param newStartTime new value of start time to be written to the task
	 * @param newEndTime new value of end time to be written to the task
	 * @throws Exception if the start time is later than the end time
	 * */
	public void modifyStartEnd(int index, Date newStartTime, Date newEndTime) throws Exception {
		LOGGER.info("modifyStartTime is called. Index: {}, StartTime: {}, EndTime: {}", index, newStartTime, newEndTime);
		setOperationType(_modifyOp);
		setIndex(index);
		setStartTime(newStartTime);
		setEndTime(newEndTime);
	}
	
	/**
	 * Track the parameter called onto markTaskDone in Database.
	 * 
	 */
	public void markTaskDone(int indexToMarkDone) {
		LOGGER.info("markTaskDone is called. Index: {}", indexToMarkDone);
		setOperationType(_doneOp);
	}
	
	// check if there is missing backslash
	public void modifyFileLocation(String newFileLocation) {
		setOperationType(_modifyOp);
		setFileLocation(newFileLocation);
	}

	
	// Getters and Setters
	
	public void setOperationType(OperationType op) {
		_operation = op;
	}
	
	public void setIndex(int index) {
		_parameter[ParameterType.INDEX_POS] = index;
	}
	
	public void setName(String name) {
		_parameter[ParameterType.NEW_NAME_POS] = name;
	}
	public void setStartTime(Date startTime) {
		_parameter[ParameterType.NEW_STARTTIME_POS] = startTime;
	}
	public void setEndTime(Date endTime) {
		_parameter[ParameterType.NEW_ENDTIME_POS] = endTime;
	}
	public void setWorkload(int workload) {
		_parameter[ParameterType.NEW_WORKLOAD_POS] = workload;
	}
	public void setFileLocation(String fileLocation) {
		_parameter[ParameterType.NEW_FILELOCATION_POS] = fileLocation;
	}
	
	public OperationType getOperationType(){
		return _operation;
	}

	public Object[] getParameter(){
		return _parameter;
	}

	public Object getIndex() {
		if (_parameter[ParameterType.INDEX_POS] == null) {
			return null;
		} else {
			int index = (int) _parameter[ParameterType.INDEX_POS];
			return index;
		}
		
	}
	
	public String getName() {
		String index = (String) _parameter[ParameterType.NEW_NAME_POS];
		return index;
	}
	
	public Date getStartTime() {
		Date startTime = (Date) _parameter[ParameterType.NEW_STARTTIME_POS];
		return startTime;
	}
	
	public Date getEndTime() {
		Date endTime = (Date) _parameter[ParameterType.NEW_ENDTIME_POS];
		return endTime;
	}
	
	public int getWorkload() {
		if (_parameter[ParameterType.NEW_WORKLOAD_POS] == null) {
			return 0;
		} else {
			int workload = (int) _parameter[ParameterType.NEW_WORKLOAD_POS];
			return workload;
		}
		
	}
	
	public String getFileLocation() {
		String fileLocation = (String) _parameter[ParameterType.NEW_FILELOCATION_POS];
		return fileLocation;
	}	
}
