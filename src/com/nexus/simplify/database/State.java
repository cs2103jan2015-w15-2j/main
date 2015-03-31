package com.nexus.simplify.database;

import javafx.collections.ObservableList;

import com.nexus.simplify.database.tasktype.*;

import java.io.*;

public class State {

	Database database;
	ObservableList<GenericTask> observableGeneric;
	ObservableList<DeadlineTask> observableDeadline;
	ObservableList<TimedTask> observableTimed;
	
	ByteArrayInputStream arrayIn;
	ByteArrayOutputStream arrayOut;
	byte[] genericByte;
	byte[] timedByte;
	byte[] deadlineByte;
	
	public State(Database database) {
		this.database = database;
		this.observableGeneric = database.getObservableGeneric();
		this.observableDeadline = database.getObservableDeadline();
		this.observableTimed = database.getObservableTimed();
	}
	
	public void saveGenericState() {
		try {
			arrayOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(arrayOut);
			objectOut.writeObject(observableGeneric);
			objectOut.close();
			genericByte = arrayOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTimedState() {
		try {
			arrayOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(arrayOut);
			objectOut.writeObject(observableTimed);
			objectOut.close();
			timedByte = arrayOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDeadlineState() {
		try {
			arrayOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(arrayOut);
			objectOut.writeObject(observableDeadline);
			objectOut.close();
			deadlineByte = arrayOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadGenericState() {
		try {
			arrayIn = new ByteArrayInputStream(genericByte);
			ObjectInputStream objectIn = new ObjectInputStream(arrayIn);
			observableGeneric = (ObservableList<GenericTask>) objectIn.readObject();
			objectIn.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadTimedState() {
		try {
			arrayIn = new ByteArrayInputStream(timedByte);
			ObjectInputStream objectIn = new ObjectInputStream(arrayIn);
			observableTimed = (ObservableList<TimedTask>) objectIn.readObject();
			objectIn.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadDeadlineState() {
		try {
			arrayIn = new ByteArrayInputStream(deadlineByte);
			ObjectInputStream objectIn = new ObjectInputStream(arrayIn);
			observableDeadline = (ObservableList<DeadlineTask>) objectIn.readObject();
			objectIn.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
}
