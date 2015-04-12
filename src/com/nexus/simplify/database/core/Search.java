//@author A0114887U
package com.nexus.simplify.database.core;

import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

/**
 * Represents an instance of a search used to search for user-defined terms.
 * */
public class Search {

	private int HEAD = 0;
	//-------------//
	// Constructor //
	//-------------//
	
	public Search() {}
	
	//---------//
	// Methods //
	//---------//
	
	public ObservableList<GenericTask> searchGenericTlByName(String term, ObservableList<GenericTask> genericTl,
			ObservableList<GenericTask> resultantTl) {
		
		assert !genericTl.isEmpty();
	
		for(GenericTask genericTask: genericTl) {
			if (genericTask.getName().contains(term) && !resultantTl.contains(genericTask)) {
				resultantTl.add(genericTask);
			}
		}
		return resultantTl;
	
	}
	
	public ObservableList<GenericTask> searchGenericTlByWorkload(int workload, ObservableList<GenericTask> genericTl,
			ObservableList<GenericTask> resultantTl) {
		
		assert !genericTl.isEmpty();
		
		for(GenericTask genericTask: genericTl) {
			if (genericTask.getWorkload() == workload && !resultantTl.contains(genericTask)) {
				resultantTl.add(genericTask);
			}
		}
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByName(String term, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getName().contains(term) && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			}
		}
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByWorkload(int workload, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getWorkload() == workload && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByYear(int year, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getDeadline().getYear() == year && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			} else if (deadlineTask.getDeadline().getYear() == year && resultantTl.contains(deadlineTask)) {
				resultantTl.remove(deadlineTask);
				resultantTl.add(HEAD, deadlineTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByMonth(int month, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getDeadline().getMonthOfYear() == month && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			} else if (deadlineTask.getDeadline().getMonthOfYear() == month && resultantTl.contains(deadlineTask)) {
				resultantTl.remove(deadlineTask);
				resultantTl.add(HEAD, deadlineTask);
			}
		}
		
		return resultantTl;
	
	}

	public ObservableList<DeadlineTask> searchDeadlineTlByDay(int day, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getDeadline().getDayOfMonth() == day && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			} else if (deadlineTask.getDeadline().getDayOfMonth() == day && resultantTl.contains(deadlineTask)) {
				resultantTl.remove(deadlineTask);
				resultantTl.add(HEAD, deadlineTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByWeekday(int weekday, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getDeadline().getDayOfWeek() == weekday && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			} else if (deadlineTask.getDeadline().getDayOfWeek() == weekday && resultantTl.contains(deadlineTask)) {
				resultantTl.remove(deadlineTask);
				resultantTl.add(HEAD, deadlineTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<DeadlineTask> searchDeadlineTlByHour(int hour, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<DeadlineTask> resultantTl) {
		
		assert !deadlineTl.isEmpty();
		
		for(DeadlineTask deadlineTask: deadlineTl) {
			if (deadlineTask.getDeadline().getHourOfDay() == hour && !resultantTl.contains(deadlineTask)) {
				resultantTl.add(deadlineTask);
			} else if (deadlineTask.getDeadline().getHourOfDay() == hour && resultantTl.contains(deadlineTask)) {
				resultantTl.remove(deadlineTask);
				resultantTl.add(HEAD, deadlineTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByName(String term, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
		
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getName().contains(term) && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			}
		}
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByWorkload(int workload, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
	
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getWorkload() == workload && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByYear(int year, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
	
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getStartTimeAsDT().getYear() == year && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getStartTimeAsDT().getYear() == year && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} else if (timedTask.getEndTimeAsDT().getYear() == year && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getYear() == year && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			}
		}
		
		return resultantTl;
	}
	
	public ObservableList<TimedTask> searchTimedTlByMonth(int month, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
		
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getStartTimeAsDT().getMonthOfYear() == month && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getStartTimeAsDT().getMonthOfYear() == month && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} else if (timedTask.getEndTimeAsDT().getMonthOfYear() == month && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getMonthOfYear() == month && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByDay(int day, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
		
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getStartTimeAsDT().getDayOfMonth() == day && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			}  else if (timedTask.getStartTimeAsDT().getDayOfMonth() == day && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} else if (timedTask.getEndTimeAsDT().getDayOfMonth() == day && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			}  else if (timedTask.getEndTimeAsDT().getDayOfMonth() == day && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			}
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByWeekday(int weekday, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
		
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getStartTimeAsDT().getDayOfWeek() == weekday && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getStartTimeAsDT().getDayOfWeek() == weekday && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} else if (timedTask.getEndTimeAsDT().getDayOfWeek() == weekday && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getDayOfWeek() == weekday && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} 
		}
		
		return resultantTl;
	
	}
	
	public ObservableList<TimedTask> searchTimedTlByHour(int hour, ObservableList<TimedTask> timedTl,
			ObservableList<TimedTask> resultantTl) {
		
		assert !timedTl.isEmpty();
		
		for(TimedTask timedTask: timedTl) {
			if (timedTask.getStartTimeAsDT().getHourOfDay() == hour && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getStartTimeAsDT().getHourOfDay() == hour && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			} else if (timedTask.getEndTimeAsDT().getHourOfDay() == hour && !resultantTl.contains(timedTask)) {
				resultantTl.add(timedTask);
			} else if (timedTask.getEndTimeAsDT().getHourOfDay() == hour && resultantTl.contains(timedTask)) {
				resultantTl.remove(timedTask);
				resultantTl.add(HEAD, timedTask);
			}
		}
		
		return resultantTl;
	
	}
}