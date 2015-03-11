/*
 * @author David Zhao Han
 * */

package com.nexus.simplify;

import java.util.*;

public class TaskList {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private static final int APPROPRIATE_TASKLIST_SIZE = 1000;
	private ArrayList<Task> taskArray = new ArrayList<Task>(APPROPRIATE_TASKLIST_SIZE);
	private enum SORT_TYPE{
		NAME, DUE_DATE, WORKLOAD, ID
	};
	
	//-------------//
	// Constructor //
	//-------------//
	
	public TaskList() {
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public Task get(int i) { 
		return taskArray.get(i); 
	}
	
	public int size() { 
		return taskArray.size(); 
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void set(int index, Task newTask){ 
		arr.set(index, newTask); 
	}
	
	public void add(Task task) { 
		taskArray.add(task);
	}
	
	public void delete(int i){ 
		taskArray.remove(i);
	}
	
	public void delete(String name){
		int i = 0;
		for(; i<taskArray.size(); i++){
			if(name.equals(taskArray.get(i).getName())) { 
				break; 
			}
		}
		delete(i);
	}
	
	public void SortBy(SORT_TYPE sortType){
		switch(sortType){
			case NAME : 
				Collections.sort(taskArray, taskNameComparator);
				break;
			case DUE_DATE :
				Collections.sort(taskArray, taskDueDateComparator);
				break;
			case WORKLOAD :
				Collections.sort(taskArray, taskWorkloadComparator);
				break;
			case ID :
				Collections.sort(taskArray, taskIDComparator);
				break;
		}
	}
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	private static Comparator<Task> taskNameComparator = new Comparator<Task>() {
		public int compare(Task t1, Task t2){
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	private static Comparator<Task> taskDueDateComparator = new Comparator<Task>() {
		public int compare(Task t1, Task t2){
			String t1DueDate = t1.getDueDate().toUpperCase();
			String t2DueDate = t2.getDueDate().toUpperCase();
			return t1DueDate.compareTo(t2DueDate);
		}
	};
	
	private static Comparator<Task> taskWorkloadComparator = new Comparator<Task>() {
		public int compare(Task t1, Task t2){
			int t1Workload = t1.getWorkload();
			int t2Workload = t2.getWorkload();
			return t1Workload - t2Workload;
		}
	};
	
	private static Comparator<Task> taskIDComparator = new Comparator<Task>() {
		public int compare(Task t1, Task t2){
			int t1ID = Integer.parseInt(t1.getId());
			int t2ID = Integer.parseInt(t2.getId());
			return t1ID - t2ID;
		}
	};
	
	//--------------//
	// Misc Methods //
	//--------------//
	
	public boolean isEmpty() { 
		return taskArray.isEmpty(); 
	}

}
