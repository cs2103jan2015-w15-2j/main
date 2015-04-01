package com.nexus.simplify.database;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexus.simplify.database.tasktype.GenericTask;

public class ReaderTest {

	private static final String DEFAULT_DATA_FILE_PATH = "SavedData/input.json";
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	Database database;
	Reader reader;
	
	@Before
	public void initialise() {
		try {
			database = new Database();
			database.clearContent();
			reader = new Reader(database);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		database.clearContent();
	}
	
	@Test
	public void testRetrieveEmptyFile() {
		JSONArray expectedJsonArray = new JSONArray();
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH);
		assertEquals(expectedJsonArray, jsonArray);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testRetrieveDataFromDataFile() {
		JSONArray expectedJsonArray = new JSONArray();
		
		// database.addDeadlineTask("deadline", new Date(115, 6, 1), 1);
		database.addGenericTask("generic", 1);
		String id = database.getObservableGeneric().get(0).getId();
		// database.addTimedTask("timed", startTime, endTime, 1);
		
		JSONObject itemOne = new JSONObject();
		itemOne.put("Name", "generic");
		itemOne.put("Type", "Generic");
		itemOne.put("Workload", 1);
		itemOne.put("ID", id);
		expectedJsonArray.add(itemOne);
		
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH);
		assertEquals(expectedJsonArray, jsonArray);
	}
	
	@Test
	public void testPopulateTaskLists() {
		ObservableList<GenericTask> expectedGeneric = FXCollections.observableArrayList();
		
		database.addGenericTask("generic", 1);
		reader.populateTaskLists(reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH));
		
	}
}
