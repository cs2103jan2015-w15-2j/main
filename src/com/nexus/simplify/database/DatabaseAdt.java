package com.nexus.simplify.database;

import static org.junit.Assert.*;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

/**
 * provides a test suite for the various methods found in the Database Class.
 * @author tohjianfeng
 * */
public class DatabaseAdt {
	private static final String DEFAULT_DATA_FILE_PATH = "SavedData/input.json";
	private static final String DEFAULT_FILE_LOCATION = "SavedData/";

	//------------------//
	// Class Attributes //
	//------------------//
	
	private static Database database;
		
	//----------------//
	// Initialization //
	//----------------//
	
	@Before
	public void initTestingEnvironment() throws Exception {
		database = new Database();
		database.setDataFileLocation("TestSavedData");
		database.initDatabase();
	}
	
	//------------------------//
	// Data Path Manipulation //
	//------------------------//
	
	@Test
	public void testGetDataFileLocation() {
		assertEquals(DEFAULT_FILE_LOCATION, database.getDataFileLocation());
	}
	
	@Test
	public void testGetDataFilePath() {
		assertEquals(DEFAULT_DATA_FILE_PATH, database.getDataFilePath());
	}
	
	@Test
	public void testSetDataFilePath() {
		database.setDataFileLocation("TestSavedData/");
		assertEquals(database.getDataFilePath(), "TestSavedData/input.json");
	}
	
	//-------------------//
	// List Manipulation //
	//-------------------//
	
	@Test
	public void testAddGenericTaskToList() {
		// Test case 1a: adding a Generic task with workload
		GenericTask expectedGenericTaskCase1 = new GenericTask("Generic Task Test 1a", 5);
		database.addGenericTask("Generic Task Test 1", 5);
		GenericTask resultantGenericTaskCase1 = database.getObservableGeneric().get(0);
		assertEquals(expectedGenericTaskCase1.getName(), resultantGenericTaskCase1.getName());
		assertEquals(expectedGenericTaskCase1.getWorkload(), resultantGenericTaskCase1.getWorkload());
		
		// Test case 1b: adding a Generic task with no workload
		database.clearContent();
		GenericTask expectedGenericTaskCase2 = new GenericTask("Generic Task Test 1b", 0);
		database.addGenericTask("Generic Task Test 1b", 0);
		GenericTask resultantGenericTaskCase2 = database.getObservableGeneric().get(0);
		assertEquals(expectedGenericTaskCase2.getName(), resultantGenericTaskCase2.getName());
		assertEquals(expectedGenericTaskCase2.getWorkload(), resultantGenericTaskCase2.getWorkload());	
	}
	
	@Test
	public void testAddDeadlineTaskToList() {
		// Test case 1a: adding a Deadline task with workload
		DeadlineTask expectedDeadlineTaskCase1 = new DeadlineTask("Deadline Task Test 1a", new Date(), 5);
		database.addDeadlineTask("Deadline Task Test 1a", new Date(), 5);
		DeadlineTask resultantDeadlineTaskCase1 = database.getObservableDeadline().get(0);
		assertEquals(expectedDeadlineTaskCase1.getName(), resultantDeadlineTaskCase1.getName());
		assertEquals(expectedDeadlineTaskCase1.getDeadline(), resultantDeadlineTaskCase1.getDeadline());
		assertEquals(expectedDeadlineTaskCase1.getWorkload(), resultantDeadlineTaskCase1.getWorkload());
		
		// Test case 1b: adding a Deadline task with no workload
		DeadlineTask expectedDeadlineTaskCase2 = new DeadlineTask("Deadline Task Test 1b", new Date(), 5);
		database.addDeadlineTask("Deadline Task Test 1b", new Date(), 5);
		DeadlineTask resultantDeadlineTaskCase2 = database.getObservableDeadline().get(0);
		assertEquals(expectedDeadlineTaskCase2.getName(), resultantDeadlineTaskCase2.getName());
		assertEquals(expectedDeadlineTaskCase2.getDeadline(), resultantDeadlineTaskCase2.getDeadline());
		assertEquals(expectedDeadlineTaskCase2.getWorkload(), resultantDeadlineTaskCase2.getWorkload());
	}
	
	@Test
	public void testAddTimedTaskToList() {
		
	}
	
	@Test
	public void testDeleteTaskFromList() {
		
	}
	
	
	



}
