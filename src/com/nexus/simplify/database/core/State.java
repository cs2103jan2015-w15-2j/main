//A0114887U
package com.nexus.simplify.database.core;

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

	private ObservableList<GenericTask> fixedGenericTl;
	private ObservableList<DeadlineTask> fixedDeadlineTl;
	private ObservableList<TimedTask> fixedTimedTl;
	
	private ObservableList<GenericTask> fixedArchivedGenericTl;
	private ObservableList<DeadlineTask> fixedArchivedDeadlineTl;
	private ObservableList<TimedTask> fixedArchivedTimedTl;
	
	private Logger LOGGER = LoggerFactory.getLogger(State.class.getName());
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private Deque<ObservableList<GenericTask>> genericTlState = new LinkedList<ObservableList<GenericTask>>();
	private Deque<ObservableList<DeadlineTask>> deadlineTlState = new LinkedList<ObservableList<DeadlineTask>>();
	private Deque<ObservableList<TimedTask>> timedTlState = new LinkedList<ObservableList<TimedTask>>();
	private Deque<ObservableList<GenericTask>> genericArchivedTlState = new LinkedList<ObservableList<GenericTask>>();
	private Deque<ObservableList<DeadlineTask>> deadlineArchivedTlState = new LinkedList<ObservableList<DeadlineTask>>();
	private Deque<ObservableList<TimedTask>> timedArchivedTlState = new LinkedList<ObservableList<TimedTask>>();
	
	//-------------//
	// Constructor //
	//-------------//
	
	public State() {
	}
	
	//---------//
	// Methods //
	//---------//
	
	public void saveState(ObservableList<GenericTask> genericTl,
							ObservableList<DeadlineTask> deadlineTl,
							ObservableList<TimedTask> timedTl,
							ObservableList<GenericTask> archivedGenericTl,
							ObservableList<DeadlineTask> archivedDeadlineTl,
							ObservableList<TimedTask> archivedTimedTl) {
		
		fixedGenericTl = copyGenericTl(genericTl);
		fixedDeadlineTl= copyDeadlineTl(deadlineTl);
		fixedTimedTl = copyTimedTl(timedTl);
		fixedArchivedGenericTl  = copyGenericTl(archivedGenericTl);
		fixedArchivedDeadlineTl = copyDeadlineTl(archivedDeadlineTl);
		fixedArchivedTimedTl = copyTimedTl(archivedTimedTl);
		
		pushFixedTlsToRespectiveDeques();
		LOGGER.info("State saved.");
		
	}
	
	public ObservableList<GenericTask> getFixedGenericState() {
		
		LOGGER.info("Generic state retrieved.");
		return genericTlState.pop();
		
	}
	
	public ObservableList<DeadlineTask> getFixedDeadlineState() {

		LOGGER.info("Deadline state retrieved.");
		return deadlineTlState.pop();

	}
	
	public ObservableList<TimedTask> getFixedTimedState() {

		LOGGER.info("Timed state retrieved.");
		return timedTlState.pop();
	
	}
	
	public ObservableList<GenericTask> getFixedArchivedGenericState() {
	
		LOGGER.info("Archived generic state retrieved.");
		return genericArchivedTlState.pop();
	
	}
	
	public ObservableList<DeadlineTask> getFixedArchivedDeadlineState() {
	
		LOGGER.info("Archived deadline state retrieved.");
		return deadlineArchivedTlState.pop();
	
	}
	
	public ObservableList<TimedTask> getFixedArchivedTimedState() {
	
		LOGGER.info("Archived timed state retrieved.");
		return timedArchivedTlState.pop();
	
	}
	
	public boolean isEmpty() {
		if (genericTlState.isEmpty() && deadlineTlState.isEmpty() && timedTlState.isEmpty() &&
				genericArchivedTlState.isEmpty() && deadlineArchivedTlState.isEmpty() && timedArchivedTlState.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void pushFixedTlsToRespectiveDeques() {
		
		genericTlState.push(fixedGenericTl);
		deadlineTlState.push(fixedDeadlineTl);
		timedTlState.push(fixedTimedTl);
		genericArchivedTlState.push(fixedArchivedGenericTl);
		deadlineArchivedTlState.push(fixedArchivedDeadlineTl);
		timedArchivedTlState.push(fixedArchivedTimedTl);
	
	}
	
	
	//A0111035A
	/**
	* Creating copies to store in double ended queue.
	* */
	private ObservableList<GenericTask> copyGenericTl(ObservableList<GenericTask> genericTl) {
	
		ObservableList<GenericTask> copy = FXCollections.observableArrayList();
		
		for (GenericTask genericTask : genericTl) {
			GenericTask taskCopy = genericTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
	
	private ObservableList<DeadlineTask> copyDeadlineTl(ObservableList<DeadlineTask> deadlineTl) {
		
		ObservableList<DeadlineTask> copy = FXCollections.observableArrayList();
		
		for (DeadlineTask deadlineTask : deadlineTl) {
			DeadlineTask taskCopy = deadlineTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
	
	private ObservableList<TimedTask> copyTimedTl(ObservableList<TimedTask> timedTl) {
		
		ObservableList<TimedTask> copy = FXCollections.observableArrayList();
		
		for (TimedTask timedTask : timedTl) {
			TimedTask taskCopy = timedTask.getCopy();
			copy.add(taskCopy);
		}
		return copy;
		
	}
}
