/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
		
	public DeadlineTaskList() {
		
	}
	

	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	public DeadlineTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	// is this necessary?
	public void set(int index, DeadlineTask newTask) {
		taskArray.set(index, newTask);
	}
	
	public void add(DeadlineTask task) {
		taskArray.add(task);
	}
	
	public void delete(int index) {
		taskArray.remove(index);
	}
	
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
	
	public int size() {
		return taskArray.size();
	}
	
	public boolean isEmpty() {
		return taskArray.isEmpty();
	}
	
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
	
	public ObservableList<DeadlineTask> getObservable() {
		for (int i = 0; i < numOfDisplayItems; i++) {
			observableDeadline.add(get(i));
		}
		return observableDeadline;
	}
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	private Comparator<DeadlineTask> taskNameComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	private Comparator<DeadlineTask> taskDeadlineComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Deadline = t1.getReadableDeadline();
			String t2Deadline = t2.getReadableDeadline();
			return t1Deadline.compareTo(t2Deadline);
		}
	};
	
	private Comparator<DeadlineTask> taskWorkloadComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	private Comparator<DeadlineTask> taskIdComparator = new Comparator<DeadlineTask>() {
		public int compare(DeadlineTask t1, DeadlineTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
