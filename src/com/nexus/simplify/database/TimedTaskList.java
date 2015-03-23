/*
 * @author Tan Qian Yi
 */
package Database;

import java.util.*;

public class TimedTaskList {

	//------------------//
	// Class Attributes //
	//------------------//
		
	private final int TASKLIST_SIZE = 1000;
	private ArrayList<TimedTask> taskArray = new ArrayList<TimedTask>(TASKLIST_SIZE);
	private enum SORT_TYPE {
		NAME, TIMED, WORKLOAD, ID
	}
		
	//-------------//
	// Constructor //
	//-------------//
		
	public TimedTaskList() {
	}
	

	//---------------------//
	// Attribute Accessor //
	//---------------------//
	
	public TimedTask get(int index) {
		return taskArray.get(index);
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	// is this necessary?
	public void set(int index, TimedTask newTask) {
		taskArray.set(index, newTask);
	}
	
	public void add(TimedTask task) {
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
	
	//--------------------//
	// Comparison Methods //
	//--------------------//
	
	private Comparator<TimedTask> taskNameComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Name = t1.getName().toUpperCase();
			String t2Name = t2.getName().toUpperCase();
			return t1Name.compareTo(t2Name);
		}
	};
	
	private Comparator<TimedTask> taskTimedComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Deadline = t1.getTimed();
			String t2Deadline = t2.getTimed();
			return t1Deadline.compareTo(t2Deadline);
		}
	};
	
	private Comparator<TimedTask> taskWorkloadComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Workload = Integer.toString(t1.getWorkload());
			String t2Workload = Integer.toString(t2.getWorkload());
			return t1Workload.compareTo(t2Workload);
		}
	};
	
	private Comparator<TimedTask> taskIdComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask t1, TimedTask t2) {
			String t1Id = t1.getId();
			String t2Id = t2.getId();
			return t1Id.compareTo(t2Id);
		} 
	};
}
