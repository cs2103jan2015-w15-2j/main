package com.nexus.simplify.database.observables;

import java.util.*;

import com.nexus.simplify.database.tasktype.DeadlineTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an instance of a list consisting of DeadlineTasks
 * @author Tan Qian Yi
 * */
public class DeadlineTaskList {

	//------------------//
	// Class Attributes //
	//------------------//
		
	private final int TASKLIST_SIZE = 1000;
	private ArrayList<DeadlineTask> taskArray = new ArrayList<DeadlineTask>(TASKLIST_SIZE);
	private enum SORT_TYPE {
		NAME, DEADLINE, WORKLOAD, ID
	}
	
	private int numOfDisplayItems = 5;
	private ObservableList<DeadlineTask> observableDeadline = FXCollections.observableArrayList();
		
	//-------------//
	// Constructor //
	//-------------//
	
	/**
	 * default constructor
	 * */
	public DeadlineTaskList() {
		
	}
	

	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	/**
	 * returns the DeadlineTask at given index of the list
	 * 
	 * @return DeadlineTask at given index of the list
	 * */
	public DeadlineTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//

	/**
	 * append given DeadlineTask at the end of the list
	 * 
	 * @param DeadlineTask to be appended to the end of the list
	 * */
	public void add(DeadlineTask task) {
		taskArray.add(task);
	}
	
	/**
	 * remove the DeadlineTask at given index of the list
	 * 
	 *  @param index of the DeadlineTask to be removed
	 * */
	public void delete(int index) {
		taskArray.remove(index - 1);
	}
	
	/**
	 * remove the DeadlineTask with the given name from the list
	 * 
	 * @param name of DeadlineTask to be removed
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
	
	public void clear() {
		taskArray.clear();
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
	 * supported types include: <NAME>, <DEADLINE>, <WORKLOAD>, <ID> 
	 *
	 * @param type of sorting to be done in String
	 * @return type of sorting to be done as SORT_TYPE
	 * */
	public SORT_TYPE getSortType(String keyword) {
		if (keyword.equals("name")) {
			return SORT_TYPE.NAME;
		} else if (keyword.equals("deadline")) {
			return SORT_TYPE.DEADLINE;
		} else if (keyword.equals("workload")){
			return SORT_TYPE.WORKLOAD;
		} else {
			return SORT_TYPE.ID;
		}
	}
	
	/**
	 * sorts the list via the given SORT_TYPE
	 * supported types include: <NAME>, <DEADLINE>, <WORKLOAD>, <ID> 
	 *
	 * @param type of sorting to be done in the form of SORT_TYPE
	 * */
	public void sortBy(SORT_TYPE type) {
		switch(type) {
			case NAME:
				Collections.sort(taskArray, taskNameComparator);
				break;
			case DEADLINE:
				Collections.sort(taskArray, taskDeadlineComparator);
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
	 * returns an ObservableList of DeadlineTask
	 * 
	 * @return ObservableList of DeadlineTask
	 * */
	public ObservableList<DeadlineTask> getObservable() {
		int maxNumTasksToAdd;
		observableDeadline.removeAll(observableDeadline);
		if (!isEmpty()) {
			if (taskArray.size() > numOfDisplayItems) {
				maxNumTasksToAdd = numOfDisplayItems;
			} else {
				maxNumTasksToAdd = taskArray.size();
			}
			for (int i = 0; i < maxNumTasksToAdd; i++) {
				observableDeadline.add(get(i));
			}
		}
		return observableDeadline;
	}
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	/**
	 * compares the name of DeadlineTask t1 with the name of DeadlineTask t2
	 * 
	 * @return zero if the names of the DeadlineTasks are the same
	 * @return negative integer if the name of DeadlineTask t2 is lexicographically greater than the name of DeadlineTask t1
	 * @return positive integer if the name of DeadlineTask t2 is lexicographically less than the name of DeadlineTask t1
	 * */
	private Comparator<DeadlineTask> taskNameComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	/**
	 * compares the deadline of DeadlineTask t1 with the deadline of DeadlineTask t2
	 * 
	 * @return zero if the deadlines of the DeadlineTasks are the same
	 * @return negative integer if the deadline of DeadlineTask t2 is lexicographically greater than the deadline of DeadlineTask t1
	 * @return positive integer if the deadline of DeadlineTask t2 is lexicographically less than the deadline of DeadlineTask t1
	 * */
	private Comparator<DeadlineTask> taskDeadlineComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Deadline = t1.getReadableDeadline();
			String t2Deadline = t2.getReadableDeadline();
			return t1Deadline.compareTo(t2Deadline);
		}
	};
	
	/**
	 * compares the workload of DeadlineTask t1 with the workload of DeadlineTask t2
	 * 
	 * @return zero if the workloads of the DeadlineTasks are the same
	 * @return negative integer if the workload of DeadlineTask t2 is lexicographically greater than the workload of DeadlineTask t1
	 * @return positive integer if the workload of DeadlineTask t2 is lexicographically less than the workload of DeadlineTask t1
	 * */
	private Comparator<DeadlineTask> taskWorkloadComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	/**
	 * compares the id of DeadlineTask t1 with the id of DeadlineTask t2
	 * 
	 * @return zero if the ids of the DeadlineTasks are the same
	 * @return negative integer if the id of DeadlineTask t2 is lexicographically greater than the id of DeadlineTask t1
	 * @return positive integer if the id of DeadlineTask t2 is lexicographically less than the id of DeadlineTask t1
	 * */
	private Comparator<DeadlineTask> taskIdComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
