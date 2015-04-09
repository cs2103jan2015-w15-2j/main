package com.nexus.simplify.database;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.IOException;
import java.util.*;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.joda.time.DateTime;
import org.json.simple.*;

import com.nexus.simplify.database.tasktype.*;

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
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void testRetrieveDataFromDataFile() {
		JSONArray expectedJsonArray = new JSONArray();
		
		database.addDeadlineTask("deadline", new Date(115, 6, 1), 1);
		String deadlineId = database.getObservableDeadlineTL().get(0).getId();
		database.addGenericTask("generic", 1);
		String genericId = database.getObservableGenericTL().get(0).getId();
		database.addTimedTask("timed", new Date(115, 6, 1), new Date(115, 7, 1), 1);
		String timedId = database.getObservableTimedTL().get(0).getId();
		
		JSONObject itemOne = new JSONObject();
		itemOne.put("Name", "deadline");
		itemOne.put("DueDate", "01 Jul 2015 00:00");
		itemOne.put("Workload", 1);
		itemOne.put("ID", deadlineId);
		itemOne.put("Type", "Deadline");
		expectedJsonArray.add(itemOne);
		
		JSONObject itemThree = new JSONObject();
		itemThree.put("Name", "timed");
		itemThree.put("Start Time", "01 Jul 2015 00:00");
		itemThree.put("End Time", "01 Aug 2015 00:00");
		itemThree.put("Workload", 1);
		itemThree.put("ID", timedId);
		itemThree.put("Type", "Timed");
		expectedJsonArray.add(itemThree);
		
		JSONObject itemTwo = new JSONObject();
		itemTwo.put("Name", "generic");
		itemTwo.put("Workload", 1);
		itemTwo.put("ID", genericId);
		itemTwo.put("Type", "Generic");
		expectedJsonArray.add(itemTwo);
		
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH);
		assertEquals(expectedJsonArray.toString(), jsonArray.toString());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testPopulateTaskLists() {
		ObservableList<GenericTask> expectedGenericTL = FXCollections.observableArrayList(); 
		ObservableList<DeadlineTask> expectedDeadlineTL = FXCollections.observableArrayList();
		ObservableList<TimedTask> expectedTimedTL = FXCollections.observableArrayList();
		
		database.addGenericTask("generic", 1);
		database.addDeadlineTask("deadline", new Date(115, 6, 1), 1);
		database.addTimedTask("timed", new Date(115, 6, 1), new Date(115, 7, 1), 1);
		reader.populateTaskLists(reader.retrieveDataFromDataFile(DEFAULT_DATA_FILE_PATH));
		ObservableList<GenericTask> genericTL = FXCollections.observableArrayList(database.getObservableGenericTL());
		ObservableList<DeadlineTask> deadlineTL = FXCollections.observableArrayList(database.getObservableDeadlineTL());
		ObservableList<TimedTask> timedTL = FXCollections.observableArrayList();
		
		expectedGenericTL.add(new GenericTask(new SimpleStringProperty("generic"), new SimpleIntegerProperty(1), genericTL.get(0).getIDAsStringProperty()));
		expectedDeadlineTL.add(new DeadlineTask(new SimpleStringProperty("deadline"), new Date(115, 6, 1), new SimpleIntegerProperty(1), deadlineTL.get(0).getIDAsStringProperty()));
		expectedTimedTL.add(new TimedTask(new SimpleStringProperty("timed"), new DateTime(new Date(115, 6, 1)), new DateTime(new Date(115, 7, 1)), new SimpleIntegerProperty(1), deadlineTL.get(0).getIDAsStringProperty()));
		
		for (GenericTask generic: genericTL) {
			int index = genericTL.indexOf(generic);
			assertEquals(generic.getName(), expectedGenericTL.get(index).getName());
			assertEquals(generic.getWorkload(), expectedGenericTL.get(index).getWorkload());
			assertEquals(generic.getId(), expectedGenericTL.get(index).getId());
		}
		
		for (DeadlineTask deadline: deadlineTL) {
			int index = deadlineTL.indexOf(deadline);
			assertEquals(deadline.getName(), expectedDeadlineTL.get(index).getName());
			assertEquals(deadline.getReadableDeadline(), expectedDeadlineTL.get(index).getReadableDeadline());
			assertEquals(deadline.getWorkload(), expectedDeadlineTL.get(index).getWorkload());
			assertEquals(deadline.getId(), expectedDeadlineTL.get(index).getId());
		}
		
		for (TimedTask timed: timedTL) {
			int index = timedTL.indexOf(timed);
			assertEquals(timed.getName(), expectedTimedTL.get(index).getName());
			assertEquals(timed.getReadableStartTime(), expectedTimedTL.get(index).getReadableStartTime());
			assertEquals(timed.getReadableEndTime(), expectedTimedTL.get(index).getReadableEndTime());
			assertEquals(timed.getWorkload(), expectedTimedTL.get(index).getWorkload());
			assertEquals(timed.getId(), expectedTimedTL.get(index).getId());
		}
	}
}
