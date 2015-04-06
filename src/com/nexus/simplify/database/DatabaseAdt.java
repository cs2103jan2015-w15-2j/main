package com.nexus.simplify.database;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

/**
 * provides a series of tests for the various methods found in the Database Class.
 * @author tohjianfeng
 * */
public class DatabaseAdt {


	//------------------//
	// Class Attributes //
	//------------------//
	
	private static Database database;
		
	//----------------//
	// Initialization //
	//----------------//
	
	@BeforeClass
	public static void initTestingEnvironment() throws Exception {
		database = new Database();
		database.modifyFileLocation("TestSavedData/");
		database.initDatabase();
	}
	
	@Before
	public void resetEnvironmentForTest() {
		database.clearContent();
	}
	
	//----------//
	// Teardown //
	//----------//
	
	@AfterClass
	public static void resetDefaultDataPath() {
		database.modifyFileLocation("SavedData/");
	}
	//------------------------//
	// Data Path Manipulation //
	//------------------------//
	
	@Test
	public void testGetDataFileLocation() {
		assertEquals("TestSavedData/", database.getDataFileLocation());
	}
	
	@Test
	public void testGetDataFilePath() {
		assertEquals("TestSavedData/input.json", database.getDataFilePath());
	}
	
	@Test
	public void testModifyDataFilePath() {
		database.modifyFileLocation("TestSavedData/");
		assertEquals("TestSavedData/input.json", database.getDataFilePath());
	}
	
	//-------------------//
	// List Manipulation //
	//-------------------//
	
	@Test
	public void testAddGenericTaskToList() {
		// Test case 1a: adding a Generic task with workload
		GenericTask expectedGenericTaskCase1 = new GenericTask("Generic Task Test 1a", 5);
		database.addGenericTask("Generic Task Test 1a", 5);
		GenericTask resultantGenericTaskCase1 = database.getObservableGenericTL().get(0);
		assertEquals(expectedGenericTaskCase1.getName(), resultantGenericTaskCase1.getName());
		assertEquals(expectedGenericTaskCase1.getWorkload(), resultantGenericTaskCase1.getWorkload());
		
		database.clearContent();
		
		// Test case 1b: adding a Generic task with no workload
		// adding a task with 0 workload will trigger the setting of default workload value 1
		GenericTask expectedGenericTaskCase2 = new GenericTask("Generic Task Test 1b", 1);
		database.addGenericTask("Generic Task Test 1b", 0);
		GenericTask resultantGenericTaskCase2 = database.getObservableGenericTL().get(0);
		assertEquals(expectedGenericTaskCase2.getName(), resultantGenericTaskCase2.getName());
		assertEquals(expectedGenericTaskCase2.getWorkload(), resultantGenericTaskCase2.getWorkload());	
	}
	
	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testAddDeadlineTaskToList() {
		// Test case 1a: adding a Deadline task with workload
		DeadlineTask expectedDeadlineTaskCase1 = new DeadlineTask("Deadline Task Test 1a", new Date(), 5);
		database.addDeadlineTask("Deadline Task Test 1a", new Date(), 5);
		DeadlineTask resultantDeadlineTaskCase1 = database.getObservableDeadlineTL().get(0);
		assertEquals(expectedDeadlineTaskCase1.getName(), resultantDeadlineTaskCase1.getName());
		assertEquals(expectedDeadlineTaskCase1.getDeadline(), resultantDeadlineTaskCase1.getDeadline());
		assertEquals(expectedDeadlineTaskCase1.getWorkload(), resultantDeadlineTaskCase1.getWorkload());
		
		database.clearContent();
		
		// Test case 1b: adding a Deadline task with no workload
		DeadlineTask expectedDeadlineTaskCase2 = new DeadlineTask("Deadline Task Test 1b", new Date("29 Mar 2015 16:00"), 1);
		database.addDeadlineTask("Deadline Task Test 1b", new Date("29 Mar 2015 16:00"), 0);
		DeadlineTask resultantDeadlineTaskCase2 = database.getObservableDeadlineTL().get(0);
		assertEquals(expectedDeadlineTaskCase2.getName(), resultantDeadlineTaskCase2.getName());
		assertEquals(expectedDeadlineTaskCase2.getDeadline(), resultantDeadlineTaskCase2.getDeadline());
		assertEquals(expectedDeadlineTaskCase2.getWorkload(), resultantDeadlineTaskCase2.getWorkload());
	}
	
	@Ignore
	@Test
	public void testAddTimedTaskToList() {
		
	}

	@Test
	public void testDeleteTaskFromList() {
		// add dummy data
		database.addGenericTask("Watch movie", 2);
		database.addGenericTask("Walk the Dog", 5);
		database.addGenericTask("Read book", 3);
		database.addGenericTask("Do laundry", 4);
		database.addGenericTask("Sleep", 1);
		
		database.deleteTaskByIndex(3);
		
		// test for correct list size
		assertEquals(database.totalSizeOfAllLists(), 4);
		
		// test for current position task
		GenericTask expectedGenericTaskAtIndexThree = new GenericTask("Do laundry", 4);
		GenericTask currentGenericTaskAtIndexThree = database.getObservableGenericTL().get(2);
		assertEquals(expectedGenericTaskAtIndexThree.getName(), currentGenericTaskAtIndexThree.getName());
		assertEquals(expectedGenericTaskAtIndexThree.getWorkload(), currentGenericTaskAtIndexThree.getWorkload());
	}
	
	
	



}
