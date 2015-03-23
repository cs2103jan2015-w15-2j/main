/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.util.*;

public class GenericTaskList {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private final int TASKLIST_SIZE = 1000;
	private ArrayList<GenericTask> taskArray = new ArrayList<GenericTask>(TASKLIST_SIZE);
	private enum SORT_TYPE {
		NAME, WORKLOAD, ID
	}
	
	//-------------//
	// Constructor //
	//-------------//
	
	public GenericTaskList() {
	}
	
	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	public GenericTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	// is this necessary?
	public void set(int index, GenericTask newTask) {
		taskArray.set(index, newTask);
	}
	
	public void add(GenericTask task) {
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
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	private Comparator<GenericTask> taskNameComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	private Comparator<GenericTask> taskWorkloadComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	private Comparator<GenericTask> taskIdComparator = new Comparator<GenericTask>() {
		public int compare(GenericTask t1, GenericTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
