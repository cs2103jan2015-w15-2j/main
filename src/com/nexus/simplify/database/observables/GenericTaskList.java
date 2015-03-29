package com.nexus.simplify.database.observables;

import java.util.*;

import com.nexus.simplify.database.tasktype.GenericTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an instance of a list consisting of GenericTask
 * @author Tan Qian Yi
 * */
public class GenericTaskList {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private final int TASKLIST_SIZE = 1000;
	private ArrayList<GenericTask> taskArray = new ArrayList<GenericTask>(TASKLIST_SIZE);
	private enum SORT_TYPE {
		NAME, WORKLOAD, ID
	}
	
	private int numOfDisplayItems = 5;
	private ObservableList<GenericTask> observableGeneric = FXCollections.observableArrayList();
	
	//-------------//
	// Constructor //
	//-------------//
	
	/**
	 * default constructor
	 * */
	public GenericTaskList() {
	}
	
	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	/**
	 * returns the GenericTask at given index of the list
	 * 
	 * @return GenericTask at given index of the list
	 * */
	public GenericTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//

	/**
	 * append given GenericTask at the end of the list
	 * 
	 * @param GenericTask to be appended to the end of the list
	 * */
	public void add(GenericTask task) {
		taskArray.add(task);
	}
	
	/**
	 * remove the GenericTask at given index of the list
	 * 
	 *  @param index of the GenericTask to be removed
	 * */
	public void delete(int index) {
		taskArray.remove(index);
	}
	
	/**
	 * remove the GenericTask with the given name from the list
	 * 
	 * @param name of GenericTask to be removed
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
	 * supported types include: <NAME>, <WORKLOAD>, <ID> 
	 *
	 * @param type of sorting to be done in String
	 * @return type of sorting to be done as SORT_TYPE
	 * */
	public SORT_TYPE getSortType(String keyword) {
		if (keyword.equals("name")) {
			return SORT_TYPE.NAME;
		} else if (keyword.equals("workload")){
			return SORT_TYPE.WORKLOAD;
		} else {
			return SORT_TYPE.ID;
		}
	}
	
	/**
	 * sorts the list via the given SORT_TYPE
	 * supported types include: <NAME>, <WORKLOAD>, <ID> 
	 *
	 * @param type of sorting to be done in the form of SORT_TYPE
	 * */
	public void sortBy(SORT_TYPE type) {
		switch(type) {
			case NAME:
				Collections.sort(taskArray, taskNameComparator);
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
	 * returns an ObservableList of GenericTask
	 * 
	 * @return ObservableList of GenericTask
	 * */	
	public ObservableList<GenericTask> getObservable() {
		int maxNumTasksToAdd;
		observableGeneric.removeAll(observableGeneric);
		if (!isEmpty()) {
			if (taskArray.size() > numOfDisplayItems) {
				maxNumTasksToAdd = numOfDisplayItems;
			} else {
				maxNumTasksToAdd = taskArray.size();
			}
			for (int i = 0; i < maxNumTasksToAdd; i++) {
				observableGeneric.add(get(i));
			}
		}
		return observableGeneric;
	}
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	/**
	 * compares the name of GenericTask t1 with the name of GenericTask t2
	 * 
	 * @return zero if the names of the GenericTasks are the same
	 * @return negative integer if the name of GenericTask t2 is lexicographically greater than the name of GenericTask t1
	 * @return positive integer if the name of GenericTask t2 is lexicographically less than the name of GenericTask t1
	 * */
	private Comparator<GenericTask> taskNameComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	/**
	 * compares the workload of GenericTask t1 with the workload of GenericTask t2
	 * 
	 * @return zero if the workloads of the GenericTasks are the same
	 * @return negative integer if the workload of GenericTask t2 is lexicographically greater than the workload of GenericTask t1
	 * @return positive integer if the workload of GenericTask t2 is lexicographically less than the workload of GenericTask t1
	 * */
	private Comparator<GenericTask> taskWorkloadComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	/**
	 * compares the id of DeadlineTask t1 with the id of DeadlineTask t2
	 * 
	 * @return zero if the ids of the DeadlineTasks are the same
	 * @return negative integer if the id of GenericTask t2 is lexicographically greater than the id of GenericTask t1
	 * @return positive integer if the id of GenericTask t2 is lexicographically less than the id of GenericTask t1
	 * */
	private Comparator<GenericTask> taskIdComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
