package com.nexus.simplify.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.*;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class State {

	Database database;
	Deque<ObservableList<GenericTask>> genericTLState = new LinkedList<ObservableList<GenericTask>>();
	Deque<ObservableList<DeadlineTask>> deadlineTLState = new LinkedList<ObservableList<DeadlineTask>>();
	Deque<ObservableList<TimedTask>> timedTLState = new LinkedList<ObservableList<TimedTask>>();
	ObservableList<GenericTask> fixedGenericTL;
	ObservableList<DeadlineTask> fixedDeadlineTL;
	ObservableList<TimedTask> fixedTimedTL;
	ObservableList<GenericTask> fixedArchivedGenericTL;
	ObservableList<DeadlineTask> fixedArchivedDeadlineTL;
	ObservableList<TimedTask> fixedArchivedTimedTL;
	
	int deadlineLimit;
	
	Logger LOGGER = LoggerFactory.getLogger(State.class.getName());
	
	public State() {
	}
	
	public void saveState(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL, ObservableList<TimedTask> timedTL, ObservableList<GenericTask> archivedGenericTL, ObservableList<DeadlineTask> archivedDeadlineTL, ObservableList<TimedTask> archivedTimedTL) {
		fixedGenericTL = copyGenericTL(genericTL);
		/*deadlineLimit = deadlineTL.size();
		DeadlineTask deadlineT;
		assert !deadlineTL.isEmpty();
		for (DeadlineTask deadlineTask: deadlineTL) {
			deadlineT = new DeadlineTask(deadlineTask.getNameAsStringProperty(), deadlineTask.getDeadline(), deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty());
			fixedDeadlineTL.add(deadlineT);
		}*/
		fixedDeadlineTL= copyDeadlineTL(deadlineTL);
		fixedTimedTL = copyTimedTL(timedTL);
		fixedArchivedGenericTL  = copyGenericTL(archivedGenericTL);
		fixedArchivedDeadlineTL = copyDeadlineTL(archivedDeadlineTL);
		fixedArchivedTimedTL = copyTimedTL(archivedTimedTL);
		genericTLState.push(fixedGenericTL);
		deadlineTLState.push(fixedDeadlineTL);
		timedTLState.push(fixedTimedTL);
		genericTLState.push(fixedArchivedGenericTL);
		deadlineTLState.push(fixedArchivedDeadlineTL);
		timedTLState.push(fixedArchivedTimedTL);
		LOGGER.info("State saved");
	}
	
	public ObservableList<GenericTask> getGenericState() {
		LOGGER.info("Generic state retrieved");
		return genericTLState.pop();
	}
	
	public ObservableList<DeadlineTask> getDeadlineState() {
		/*ObservableList<DeadlineTask> deadlineTL = deadlineTLState.pop();
		while(deadlineLimit > 0) {
			deadlineTL.remove(deadlineTL.size() - 1);
			deadlineLimit--;
		}
		return deadlineTL;*/
		LOGGER.info("Deadline state retrieved");
		return deadlineTLState.pop();
	}
	
	public ObservableList<TimedTask> getTimedState() {
		LOGGER.info("Timed state retrieved");
		return timedTLState.pop();
	}
	
	public ObservableList<GenericTask> getArchivedGenericState() {
		LOGGER.info("Archived generic state retrieved");
		return genericTLState.pop();
	}
	
	public ObservableList<DeadlineTask> getArchivedDeadlineState() {
		LOGGER.info("Archived deadline state retrieved");
		return deadlineTLState.pop();
	}
	
	public ObservableList<TimedTask> getArchivedTimedState() {
		LOGGER.info("Archived timed state retrieved");
		return timedTLState.pop();
	}
	
	private ObservableList<GenericTask> copyGenericTL(ObservableList<GenericTask> genericTL) {
		ObservableList<GenericTask> copy = FXCollections.observableArrayList();
		for (GenericTask genericTask : genericTL) {
			GenericTask taskCopy = genericTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
	}
	
	private ObservableList<DeadlineTask> copyDeadlineTL(ObservableList<DeadlineTask> deadlineTL) {
		ObservableList<DeadlineTask> copy = FXCollections.observableArrayList();
		for (DeadlineTask deadlineTask : deadlineTL) {
			DeadlineTask taskCopy = deadlineTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
	}
	
	private ObservableList<TimedTask> copyTimedTL(ObservableList<TimedTask> timedTL) {
		ObservableList<TimedTask> copy = FXCollections.observableArrayList();
		for (TimedTask timedTask : timedTL) {
			TimedTask taskCopy = timedTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
	}

}
