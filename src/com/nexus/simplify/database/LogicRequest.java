package com.nexus.simplify.database;

import java.util.Date;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.logic.usercommand.ParameterType;
import com.nexus.simplify.logic.usercommand.UserCommand;

/**
 * Helper class for testing purposes. Allows to track the method and parameters that
 * Logic calls on Database
 *  
 * @author Davis
 *
 */
public class LogicRequest {
	OperationType _operation;
	Object[] _parameter;
	
	public LogicRequest() {
		_operation = OperationType.INVALID;
		_parameter = new Object[ParameterType.MAX_SIZE];
	}

	/**
	 * Adds a new timed task to the list of timed tasks.
	 * 
	 * @param name name of task
	 * @param startTime time and date when task starts
	 * @param endTime time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void setAddTimedTask(String name, Date startTime, Date endTime, int workload) {
		
	}
	
	/**
	 * Adds a new deadline-based task to the list of deadline-based tasks.
	 * 
	 * @param name name of task
	 * @param deadline time and date when task ends
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addDeadlineTask(String name, Date deadline, int workload) {
		if (workload == 0) {
			observableDeadline.add(new DeadlineTask(name, deadline));
		} else {
			observableDeadline.add(new DeadlineTask(name, deadline, workload));
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	/**
	 * Adds a new generic (floating) task to the list of generic tasks.
	 * 
	 * @param name name of task
	 * @param workload amount of effort to be put into the task from a range of 1 - 5
	 * */
	public void addGenericTask(String name, int workload) {
		if (workload == 0) {
			observableGeneric.add(new GenericTask(name));
		} else {
			observableGeneric.add(new GenericTask(name, workload));
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	
	/**
	 * Deletes a task from the table based on its index being displayed on the billboard.
	 * 
	 * @param index index of task with respect to the billboard
	 * @throws IndexOutOfBoundsException if index is not within range of 1 - 15 inclusive.
	 * */
	public void deleteTaskByIndex(int index) throws IndexOutOfBoundsException {
		if (index > this.totalSizeOfAllLists() || index < 1) {
			throw new IndexOutOfBoundsException(MSG_INDEX_OOR);
		} else {
			if (index <= observableDeadline.size()) {
				observableDeadline.remove(index - 1);
			} else if (index - observableDeadline.size() <= observableTimed.size()) {
				index = index - observableDeadline.size();
				observableTimed.remove(index - 1);
			} else {
				index = index - observableDeadline.size() - observableTimed.size();
				observableGeneric.remove(index - 1);
			}
		}
		writer.writeToFile(observableGeneric, observableDeadline, observableTimed);
	}
	
	public void clearContent() {
		setOperationType.
	}
	
	public void undoTask() {
		
	}
	
	public void searchDatabase(String[] parameter, boolean[] searchField) {
		
	}
	
	// Getters and Setters
	public void setOperationType(OperationType op) {
		_parameter[ParameterType.INDEX_POS] = op;
	}
	
	public void setIndex(int index) {
		_parameter[ParameterType.INDEX_POS] = index
	}
	
	public void setName(String name) {
		_parameter[ParameterType.INDEX_POS] = name
	}
	public void setStartTime(Date startTime) {
		_parameter[ParameterType.INDEX_POS] = startTime;
	}
	public void setEndTime(Date endTime) {
		_parameter[ParameterType.INDEX_POS] = endTime;
	}
	public void setWorkload(int workload) {
		_parameter[ParameterType.INDEX_POS] = workload;
	}
	public void setFileLocation(String fileLocation) {
		_parameter[ParameterType.INDEX_POS] = fileLocation;
	}
	
	public OperationType getOperationType(){
		return _operation;
	}

	public Object[] getParameter(){
		return _parameter;
	}

	public int getIndex() {
		int index = (int) _parameter[ParameterType.INDEX_POS];
		return index;
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
		int workload = (int) _parameter[ParameterType.NEW_WORKLOAD_POS];
		return workload;
	}
	
	public String getFileLocation() {
		String fileLocation = (String) _parameter[ParameterType.NEW_FILELOCATION_POS];
		return fileLocation;
	}	
}
