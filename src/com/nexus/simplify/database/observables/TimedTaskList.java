package com.nexus.simplify.database.observables;

import java.util.*;

import com.nexus.simplify.database.tasktype.TimedTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an instance of a list consisting of TimedTasks
 * @author Tan Qian Yi
 * */
public class TimedTaskList {

	//------------------//
	// Class Attributes //
	//------------------//
		
	private final int TASKLIST_SIZE = 1000;
	private ArrayList<TimedTask> taskArray = new ArrayList<TimedTask>(TASKLIST_SIZE);
	private enum SORT_TYPE {
		NAME, TIMED, WORKLOAD, ID
	}

	private int numOfDisplayItems = 5;
	private ObservableList<TimedTask> observableTimed = FXCollections.observableArrayList();
	
		
	//-------------//
	// Constructor //
	//-------------//
	
	/**
	 * default constructor
	 * */
	public TimedTaskList() {
	}
	

	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	/**
	 * returns the TimedTask at given index of the list
	 * 
	 * @return TimedTask at given index of the list
	 * */
	public TimedTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	/**
	 * append given TimedTask at the end of the list
	 * 
	 * @param TimedTask to be appended to the end of the list
	 * */
	public void add(TimedTask task) {
		taskArray.add(task);
	}
	
	/**
	 * remove the TimedTask at given index of the list
	 * 
	 *  @param index of the TimedlineTask to be removed
	 * */
	public void delete(int index) {
		taskArray.remove(index);
	}
	
	/**
	 * remove the TimedTask with the given name from the list
	 * 
	 * @param name of TimedTask to be removed
	 * */
	public void delete(String name) {
		int i;
		for (i = 0; i < taskArray.size(); i++) {
			if (name.equals(taskArray.get(i).getName())) {
				break;
			}
		}
		delete(i);
	}
	
	//---------//
	// Methods //
	//---------//
	
	/**
	 * returns the size of the list
	 * 
	 * @return size of the list as an integer
	 * */
	public int size() {
		return taskArray.size();
	}
	
	/**
	 * checks if the list is empty
	 * 
	 * @return true if the list is empty
	 * */
	public boolean isEmpty() {
		return taskArray.isEmpty();
	}
	
	/**
	 * returns the type of sorting to be done formatted as SORT_TYPE
	 * supported types include: <NAME>, <TIMED>, <WORKLOAD>, <ID>
	 * 
	 * @param type of sorting to be done in String
	 * @return type of sorting to be done as SORT_TYPE
	 * */
	public SORT_TYPE getSortType(String keyword) {
		if (keyword.equals("name")) {
			return SORT_TYPE.NAME;
		} else if (keyword.equals("deadline")) {
			return SORT_TYPE.TIMED;
		} else if (keyword.equals("workload")){
			return SORT_TYPE.WORKLOAD;
		} else {
			return SORT_TYPE.ID;
		}
	}
	
	/**
	 * sorts the list via the given SORT_TYPE
	 * supported types include: <NAME>, <TIMED>, <WORKLOAD>, <ID>
	 * 
	 * @param type of sorting to be done in the form of SORT_TYPE
	 * */
	public void sortBy(SORT_TYPE type) {
		switch(type) {
			case NAME:
				Collections.sort(taskArray, taskNameComparator);
				break;
			case TIMED:
				Collections.sort(taskArray, taskTimedComparator);
				break;
			case WORKLOAD:
				Collections.sort(taskArray, taskWorkloadComparator);
				break;
			case ID:
				Collections.sort(taskArray, taskIdComparator);
				break;
		}
	}
	
	/**
	 * returns an ObservableList of TimedTask
	 * 
	 * @return ObservableList of TimedTask
	 * */
	public ObservableList<TimedTask> getObservable() {
		int maxNumTasksToAdd;
		observableTimed.removeAll(observableTimed);
		if (!isEmpty()) {
			if (taskArray.size() > numOfDisplayItems) {
				maxNumTasksToAdd = numOfDisplayItems;
			} else {
				maxNumTasksToAdd = taskArray.size();
			}
			for (int i = 0; i < maxNumTasksToAdd; i++) {
				observableTimed.add(get(i));
			}
		}
		return observableTimed;
	}
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	/**
	 * compares the name of TimedTask t1 with the name of TimedTask t2
	 * 
	 * @return zero if the names of the TimedTasks are the same
	 * @return negative integer if the name of TimedTask t2 is lexicographically greater than the name of TimedTask t1
	 * @return positive integer if the name of TimedTask t2 is lexicographically less than the name of TimedTask t1
	 * */
	private Comparator<TimedTask> taskNameComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	/**
	 * compares the start time of TimedTask t1 with the start time of TimedTask t2
	 * 
	 * @return zero if the start time of the TimedTasks are the same
	 * @return negative integer if the start time of TimedTask t2 is lexicographically greater than the start time of TimedTask t1
	 * @return positive integer if the start time of TimedTask t2 is lexicographically less than the start time of TimedTask t1
	 * */
	
	private Comparator<TimedTask> taskTimedComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1StartTime = t1.getReadableStartTime();
			String t2StartTime = t2.getReadableStartTime();
			return t1StartTime.compareTo(t2StartTime);
		}
	};
	
	/**
	 * compares the workload of TimedTask t1 with the workload of TimedTask t2
	 * 
	 * @return zero if the workloads of the TimedTasks are the same
	 * @return negative integer if the workload of TimedTask t2 is lexicographically greater than the workload of TimedTask t1
	 * @return positive integer if the workload of TimedTask t2 is lexicographically less than the workload of TimedTask t1
	 * */
	private Comparator<TimedTask> taskWorkloadComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	/**
	 * compares the id of TimedTask t1 with the id of TimedTask t2
	 * 
	 * @return zero if the ids of the TimedTasks are the same
	 * @return negative integer if the id of TimedTask t2 is lexicographically greater than the id of TimedTask t1
	 * @return positive integer if the id of TimedTask t2 is lexicographically less than the id of TimedTask t1
	 * */
	private Comparator<TimedTask> taskIdComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
