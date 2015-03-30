package com.nexus.simplify.database;

import static org.junit.Assert.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.nexus.simplify.database.observables.DeadlineTaskList;
import com.nexus.simplify.database.observables.GenericTaskList;
import com.nexus.simplify.database.observables.TimedTaskList;

/**
 * provides a test suite for the various methods found in the Database Class.
 * @author tohjianfeng
 * */
public class DatabaseTest {
	private static final String DEFAULT_DATA_FILE_PATH = "SavedData/input.json";
	private static final String DEFAULT_FILE_LOCATION = "SavedData/";

	//------------------//
	// Class Attributes //
	//------------------//
	
	private static Database database;
	
	private static GenericTaskList genericTL;
	private static DeadlineTaskList deadlineTL;
	private static TimedTaskList timedTL;
	
	
	//------------------------//
	// Initialization Testing //
	//------------------------//
	
	@Before
	public void initTestingEnvironment() throws Exception {
		database = new Database();
		genericTL = new GenericTaskList();
		deadlineTL = new DeadlineTaskList();
		timedTL = new TimedTaskList();
	}
	
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void retrieveDataFromFileTest() {
		
		// Test Case #2: Non-empty File
		// database.setDataFileLocation("TestSavedData/");
		
		JSONArray expectedJsonTaskArrayCaseTwo = new JSONArray();
		
		JSONObject jsonArrayItemOne = new JSONObject();
		jsonArrayItemOne.put("Name", "homework");
		jsonArrayItemOne.put("ID", "150330133007");
		jsonArrayItemOne.put("Type", "Generic");
		jsonArrayItemOne.put("Workload", 1);
		expectedJsonTaskArrayCaseTwo.add(jsonArrayItemOne);
		
		JSONObject jsonArrayItemTwo = new JSONObject();
		jsonArrayItemTwo.put("Name", "a lot of homework");
		jsonArrayItemTwo.put("ID", "150330133013");
		jsonArrayItemTwo.put("Type", "Generic");
		jsonArrayItemTwo.put("Workload", 1);
		expectedJsonTaskArrayCaseTwo.add(jsonArrayItemTwo);
		
		JSONObject jsonArrayItemThree = new JSONObject();
		jsonArrayItemThree.put("Name", "really a lot of homework");
		jsonArrayItemThree.put("ID", "150330133018");
		jsonArrayItemThree.put("Type", "Generic");
		jsonArrayItemThree.put("Workload", 1);
		expectedJsonTaskArrayCaseTwo.add(jsonArrayItemThree);
		
		/*
		JSONObject jsonArrayItemFour = new JSONObject();
		jsonArrayItemFour.put("Name", "bloody hell lot of homework");
		jsonArrayItemFour.put("DueDate","05 Apr 2015 16:00");
		jsonArrayItemFour.put("ID", "150330111918");
		jsonArrayItemFour.put("Type", "Generic");
		jsonArrayItemFour.put("Workload", 1);
		expectedJsonTaskArrayCaseTwo.add(jsonArrayItemFour);
		*/
		
		JSONArray jsonTaskArrayCaseTwo = database.retrieveDataFromDataFile();
		assertEquals(expectedJsonTaskArrayCaseTwo, jsonTaskArrayCaseTwo);
		
		
		
	}

	//------------//
	// IO Testing //
	//------------//


}
