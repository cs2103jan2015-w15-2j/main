package com.nexus.simplify.database;

import java.util.Date;

public interface IDatabase {
	
	void addGenericTask(String name, int workload);
	void addDeadlineTask(String name, Date deadline, int workload);
	void addTimedTask(String name, Date startTime, Date endTime, int workload) throws Exception;
	void deleteTaskByIndex(int index);
	void clearContent();
	void undoTask();
	void retrieveActiveTasklists();
	void searchDatabase(String[] parameter, boolean[] searchForTimeUnit) throws java.text.ParseException;
	void toggleDisplay(String keyword);
	void modifyName(int index, String newName);
	void modifyWorkload(int index, int newWorkloadValue);
	void modifyStartEnd(int index, Date newStartTime, Date newEndTime) throws Exception;
	void markTaskDone(int indexToMarkDone);
	void modifyFileLocation(String newFileLocation);
	String getDataFileLocation();
	
}
