package com.nexus.simplify;
import java.util.*;

public class TaskList {
	// instance variables
	int size;
	ArrayList<Task> arr;
	
	private static final int APPROPRIATE_TASKLIST_SIZE = 1000;
	private ArrayList<Task> taskArray = new ArrayList<Task>(APPROPRIATE_TASKLIST_SIZE);
	private enum SORT_TYPE{
		NAME, DUE_DATE, WORKLOAD, ID
	};
	
	
	public TaskList() {
		this.arr = taskArray;
		this.size = taskArray.size();
	}
	
	public int size() { return this.size; }
	public Task get(int i) { return this.arr.get(i); }
	public boolean isEmpty() { return this.arr.isEmpty(); }
	public void add(Task task) { this.arr.add(task); }
	public void delete(int i){ this.arr.remove(i); }
	public void delete(String name){
		int i = 0
		for(; i<this.size; i++){
			if(name.equals(arr.get(i).getName()){
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
	
	private static Comparator<Task> taskNameComparator = new Comparator<Task>(){
		private int compare(Task t1, Task t2){
			String t1Name = t1.getTaskName().toUpperCase();
			String t2Name = t2.getTaskName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	private static Comparator<Task> taskDueDateComparator = new Comparator<Task>(){
		public int compare(Task t1, Task t2){
			String t1DueDate = t1.getDueDate().toUpperCase();
			String t2DueDate = t2.getDueDate().toUpperCase();
			return t1DueDate.compareTo(t2DueDate);
		}
	}
	
	private static Comparator<Task> taskWorkloadComparator = new Comparator<Task>(){
		public int compare(Task t1, Task t2){
			int t1Workload = t1.getWorkload();
			int t2Workload = t2.getWorkload();
			return t1Workload - t2Workload;
		}
	};
	
	private static Comparator<Task> taskIDComparator = new Comparator<Task>(){
		public int compare(Task t1, Task t2){
			int t1ID = t1.getID();
			int t2ID = t2.getID();
			return t1ID - t2ID;
		}
	};

}
