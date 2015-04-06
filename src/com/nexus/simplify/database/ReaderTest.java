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
		
		database.addDeadlineTask("deadline", new Date(115, 6, 1), 1);
		String deadlineId = database.getObservableDeadline().get(0).getId();
		database.addGenericTask("generic", 1);
		String genericId = database.getObservableGeneric().get(0).getId();
		// database.addTimedTask("timed", new Date(115, 6, 1), new Date(115, 7, 1), 1);
		// String timedId = database.getObservableTimed().get(0).getId();
		
		JSONObject itemOne = new JSONObject();
		itemOne.put("Name", "deadline");
		itemOne.put("Type", "Deadline");
		itemOne.put("Workload", 1);
		itemOne.put("ID", deadlineId);
		itemOne.put("DueDate", "01 Jul 2015 00:00");
		expectedJsonArray.add(itemOne);
		
		JSONObject itemTwo = new JSONObject();
		itemTwo.put("Name", "generic");
		itemTwo.put("Type", "Generic");
		itemTwo.put("Workload", 1);
		itemTwo.put("ID", genericId);
		expectedJsonArray.add(itemTwo);
		
		
		/*JSONObject itemThree = new JSONObject();
		itemThree.put("Name", "timed");
		itemThree.put("Type", "timed");
		itemThree.put("Workload", 1);
		itemThree.put("ID", timedId);
		itemThree.put("Start Time", "01 Jul 2015 00:00");
		itemThree.put("End Time", "01 Aug 2015 00:00");
		expectedJsonArray.add(itemThree);*/
		
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH);
		assertEquals(expectedJsonArray.toString(), jsonArray.toString());
	}
	
	@Test
	public void testPopulateTaskLists() {
		ObservableList<GenericTask> expectedGeneric = FXCollections.observableArrayList();
		
		database.addGenericTask("generic", 1);
		reader.populateTaskLists(reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH));
		
	}
}
