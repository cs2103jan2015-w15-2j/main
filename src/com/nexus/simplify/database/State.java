package com.nexus.simplify.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.*;

import java.util.Deque;
import java.util.LinkedList;

public class State {

	Database database;
	Deque<ObservableList<GenericTask>> genericTLState = new LinkedList<ObservableList<GenericTask>>();
	Deque<ObservableList<DeadlineTask>> deadlineTLState = new LinkedList<ObservableList<DeadlineTask>>();
	Deque<ObservableList<TimedTask>> timedTLState = new LinkedList<ObservableList<TimedTask>>();
	ObservableList<GenericTask> fixedGenericTL = FXCollections.observableArrayList();
	ObservableList<DeadlineTask> fixedDeadlineTL = FXCollections.observableArrayList();
	ObservableList<TimedTask> fixedTimedTL = FXCollections.observableArrayList();
	ObservableList<GenericTask> fixedArchivedGenericTL = FXCollections.observableArrayList();
	ObservableList<DeadlineTask> fixedArchivedDeadlineTL = FXCollections.observableArrayList();
	ObservableList<TimedTask> fixedArchivedTimedTL = FXCollections.observableArrayList();
	
	int deadlineLimit;
	
	public State() {
	}
	
	public void saveState(ObservableList<GenericTask> genericTL, ObservableList<DeadlineTask> deadlineTL, ObservableList<TimedTask> timedTL, ObservableList<GenericTask> archivedGenericTL, ObservableList<DeadlineTask> archivedDeadlineTL, ObservableList<TimedTask> archivedTimedTL) {
		fixedGenericTL.setAll(genericTL);
		/*deadlineLimit = deadlineTL.size();
		DeadlineTask deadlineT;
		assert !deadlineTL.isEmpty();
		for (DeadlineTask deadlineTask: deadlineTL) {
			deadlineT = new DeadlineTask(deadlineTask.getNameAsStringProperty(), deadlineTask.getDeadline(), deadlineTask.getWorkloadAsIntegerProperty(), deadlineTask.getIDAsStringProperty());
			fixedDeadlineTL.add(deadlineT);
		}*/
		fixedDeadlineTL.setAll(deadlineTL);
		fixedTimedTL.setAll(timedTL);
		fixedArchivedGenericTL.setAll(archivedGenericTL);
		fixedArchivedDeadlineTL.setAll(archivedDeadlineTL);
		fixedArchivedTimedTL.setAll(archivedTimedTL);
		genericTLState.push(fixedGenericTL);
		deadlineTLState.push(fixedDeadlineTL);
		timedTLState.push(fixedTimedTL);
		genericTLState.push(fixedArchivedGenericTL);
		deadlineTLState.push(fixedArchivedDeadlineTL);
		timedTLState.push(fixedArchivedTimedTL);
	}
	
	public ObservableList<GenericTask> getGenericState() {
		return genericTLState.pop();
	}
	
	public ObservableList<DeadlineTask> getDeadlineState() {
		/*ObservableList<DeadlineTask> deadlineTL = deadlineTLState.pop();
		while(deadlineLimit > 0) {
			deadlineTL.remove(deadlineTL.size() - 1);
			deadlineLimit--;
		}
		return deadlineTL;*/
		return deadlineTLState.pop();
	}
	
	public ObservableList<TimedTask> getTimedState() {
		return timedTLState.pop();
	}
	
	public ObservableList<GenericTask> getArchivedGenericState() {
		return genericTLState.pop();
	}
	
	public ObservableList<DeadlineTask> getArchivedDeadlineState() {
		return deadlineTLState.pop();
	}
	
	public ObservableList<TimedTask> getArchivedTimedState() {
		return timedTLState.pop();
	}
}
