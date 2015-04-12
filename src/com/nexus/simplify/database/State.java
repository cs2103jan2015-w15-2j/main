package com.nexus.simplify.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.*;

import java.util.Deque;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an instance of a state used to store the lists in stacks to facilitate undo function.
 * */
public class State {

	private Deque<ObservableList<GenericTask>> genericTlState = new LinkedList<ObservableList<GenericTask>>();
	private Deque<ObservableList<DeadlineTask>> deadlineTlState = new LinkedList<ObservableList<DeadlineTask>>();
	private Deque<ObservableList<TimedTask>> timedTlState = new LinkedList<ObservableList<TimedTask>>();
	private ObservableList<GenericTask> fixedGenericTl;
	private ObservableList<DeadlineTask> fixedDeadlineTl;
	private ObservableList<TimedTask> fixedTimedTl;
	private ObservableList<GenericTask> fixedArchivedGenericTl;
	private ObservableList<DeadlineTask> fixedArchivedDeadlineTl;
	private ObservableList<TimedTask> fixedArchivedTimedTl;
	
	private Logger LOGGER = LoggerFactory.getLogger(State.class.getName());
	
	//-------------//
	// Constructor //
	//-------------//
	
	public State() {
	}
	
	//---------//
	// Methods //
	//---------//
	
	public void saveState(ObservableList<GenericTask> genericTl, ObservableList<DeadlineTask> deadlineTl,
			ObservableList<TimedTask> timedTl, ObservableList<GenericTask> archivedGenericTl,
			ObservableList<DeadlineTask> archivedDeadlineTl, ObservableList<TimedTask> archivedTimedTl) {
		
		fixedGenericTl = copyGenericTL(genericTl);
		fixedDeadlineTl= copyDeadlineTL(deadlineTl);
		fixedTimedTl = copyTimedTL(timedTl);
		fixedArchivedGenericTl  = copyGenericTL(archivedGenericTl);
		fixedArchivedDeadlineTl = copyDeadlineTL(archivedDeadlineTl);
		fixedArchivedTimedTl = copyTimedTL(archivedTimedTl);
		
		genericTlState.push(fixedGenericTl);
		deadlineTlState.push(fixedDeadlineTl);
		timedTlState.push(fixedTimedTl);
		genericTlState.push(fixedArchivedGenericTl);
		deadlineTlState.push(fixedArchivedDeadlineTl);
		timedTlState.push(fixedArchivedTimedTl);
		
		LOGGER.info("State saved.");
	}
	
	public ObservableList<GenericTask> getGenericState() {
		
		LOGGER.info("Generic state retrieved.");
		return genericTlState.pop();
		
	}
	
	public ObservableList<DeadlineTask> getDeadlineState() {

		LOGGER.info("Deadline state retrieved.");
		return deadlineTlState.pop();

	}
	
	public ObservableList<TimedTask> getTimedState() {

		LOGGER.info("Timed state retrieved.");
		return timedTlState.pop();
	
	}
	
	public ObservableList<GenericTask> getArchivedGenericState() {
	
		LOGGER.info("Archived generic state retrieved.");
		return genericTlState.pop();
	
	}
	
	public ObservableList<DeadlineTask> getArchivedDeadlineState() {
	
		LOGGER.info("Archived deadline state retrieved.");
		return deadlineTlState.pop();
	
	}
	
	public ObservableList<TimedTask> getArchivedTimedState() {
	
		LOGGER.info("Archived timed state retrieved.");
		return timedTlState.pop();
	
	}
	
	public boolean isEmpty() {
		if (genericTlState.isEmpty() && deadlineTlState.isEmpty() && timedTlState.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	* Creating copies to store in double ended queue.
	* */
	
	private ObservableList<GenericTask> copyGenericTL(ObservableList<GenericTask> genericTl) {
	
		ObservableList<GenericTask> copy = FXCollections.observableArrayList();
		
		for (GenericTask genericTask : genericTl) {
			GenericTask taskCopy = genericTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
	
	private ObservableList<DeadlineTask> copyDeadlineTL(ObservableList<DeadlineTask> deadlineTl) {
		
		ObservableList<DeadlineTask> copy = FXCollections.observableArrayList();
		
		for (DeadlineTask deadlineTask : deadlineTl) {
			DeadlineTask taskCopy = deadlineTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
	
	private ObservableList<TimedTask> copyTimedTL(ObservableList<TimedTask> timedTl) {
		
		ObservableList<TimedTask> copy = FXCollections.observableArrayList();
		
		for (TimedTask timedTask : timedTl) {
			TimedTask taskCopy = timedTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
}
