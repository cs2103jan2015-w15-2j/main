package com.nexus.simplify.database;

import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;


public class Search {
	
	Database database;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public Search(Database database) {
		this.database = database;
	}
	
	public ObservableList<TimedTask> searchTimedByYear(int year, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getStartTimeAsDT().getYear() == year && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getYear() == year && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<TimedTask> searchTimedByMonth(int month, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getStartTimeAsDT().getMonthOfYear() == month && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getMonthOfYear() == month && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<TimedTask> searchTimedByDay(int day, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getStartTimeAsDT().getDayOfMonth() == day && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getDayOfMonth() == day && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	// weekday needs to be in integer of 1 to 7 instead of String
	public ObservableList<TimedTask> searchTimedByWeekday(int weekday, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getStartTimeAsDT().getDayOfWeek() == weekday && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getDayOfWeek() == weekday && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<TimedTask> searchTimedByHour(int hour, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getStartTimeAsDT().getHourOfDay() == hour && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getHourOfDay() == hour && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByYear(int year, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getDeadline().getYear() == year && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByMonth(int month, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getDeadline().getMonthOfYear() == month && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByDay(int day, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getDeadline().getDayOfMonth() == day && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	// weekday needs to be in integer 1 to 7 instead of String
	public ObservableList<DeadlineTask> searchDeadlineByWeekday(int weekday, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getDeadline().getDayOfWeek() == weekday && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByHour(int hour, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getDeadline().getHourOfDay() == hour && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<TimedTask> searchTimedByWorkload(int workload, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getWorkload() == workload && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByWorkload(int workload, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getWorkload() == workload && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<GenericTask> searchGenericByWorkload(int workload, ObservableList<GenericTask> genericTL, ObservableList<GenericTask> resultantTL) {
		assert !genericTL.isEmpty();
		for(GenericTask genericTask: genericTL) {
			if (genericTask.getWorkload() == workload && !resultantTL.contains(genericTask)) {
				resultantTL.add(genericTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<TimedTask> searchTimedByName(String term, ObservableList<TimedTask> timedTL, ObservableList<TimedTask> resultantTL) {
		assert !timedTL.isEmpty();
		for(TimedTask timedTask: timedTL) {
			if (timedTask.getName().contains(term) && !resultantTL.contains(timedTask)) {
				resultantTL.add(timedTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<DeadlineTask> searchDeadlineByName(String term, ObservableList<DeadlineTask> deadlineTL, ObservableList<DeadlineTask> resultantTL) {
		assert !deadlineTL.isEmpty();
		for(DeadlineTask deadlineTask: deadlineTL) {
			if (deadlineTask.getName().contains(term) && !resultantTL.contains(deadlineTask)) {
				resultantTL.add(deadlineTask);
			}
		}
		return resultantTL;
	}
	
	public ObservableList<GenericTask> searchGenericByName(String term, ObservableList<GenericTask> genericTL, ObservableList<GenericTask> resultantTL) {
		assert !genericTL.isEmpty();
		for(GenericTask genericTask: genericTL) {
			if (genericTask.getName().contains(term) && !resultantTL.contains(genericTask)) {
				resultantTL.add(genericTask);
			}
		}
		return resultantTL;
	}
}
