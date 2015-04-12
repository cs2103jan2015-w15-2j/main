package com.nexus.simplify.test.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nexus.simplify.database.api.Database;
import com.nexus.simplify.database.core.CoreDatabase;
import com.nexus.simplify.database.core.Reader;
import com.nexus.simplify.database.tasktype.*;

public class ReaderTest {

	private String DATA_FILE_PATH;
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	private CoreDatabase coreDatabase;
	private Reader reader;
	private Database database;
	
	@Before
	public void initialise() {
		
		try {
			coreDatabase = new CoreDatabase();
			database = new Database(coreDatabase);
			reader = new Reader(coreDatabase);
			DATA_FILE_PATH = coreDatabase.getDataFilePath();
			System.out.println(DATA_FILE_PATH);
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
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DATA_FILE_PATH);
		assertEquals(expectedJsonArray, jsonArray);
		
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void testRetrieveDataFromDataFile() {
		
		JSONArray expectedJsonArray = new JSONArray();
		
		database.addGenericTask("generic", 1);
		String genericId = coreDatabase.getObservableGenericTL().get(0).getId();
		database.addDeadlineTask("deadline", new Date(115, 6, 1), 1);
		String deadlineId = coreDatabase.getObservableDeadlineTL().get(0).getId();
		try {
			database.addTimedTask("timed", new Date(115, 6, 1), new Date(115, 7, 1), 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String timedId = coreDatabase.getObservableTimedTL().get(0).getId();
		
		JSONObject itemOne = new JSONObject();
		itemOne.put("Name", "generic");
		itemOne.put("Workload", 1);
		itemOne.put("ID", genericId);
		itemOne.put("Type", "Generic");
		expectedJsonArray.add(itemOne);
		
		JSONObject itemTwo = new JSONObject();
		itemTwo.put("Name", "deadline");
		itemTwo.put("DueDate", "01 Jul 2015 00:00");
		itemTwo.put("Workload", 1);
		itemTwo.put("ID", deadlineId);
		itemTwo.put("Type", "Deadline");
		expectedJsonArray.add(itemTwo);
		
		JSONObject itemThree = new JSONObject();
		itemThree.put("Name", "timed");
		itemThree.put("Start Time", "01 Jul 2015 00:00");
		itemThree.put("End Time", "01 Aug 2015 00:00");
		itemThree.put("Workload", 1);
		itemThree.put("ID", timedId);
		itemThree.put("Type", "Timed");
		expectedJsonArray.add(itemThree);
		
		JSONArray jsonArray = reader.retrieveDataFromDataFile(DATA_FILE_PATH);
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
		try {
			database.addTimedTask("timed", new Date(115, 6, 1), new Date(115, 7, 1), 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.populateTaskLists(reader.retrieveDataFromDataFile(DATA_FILE_PATH));
		ObservableList<GenericTask> genericTL = FXCollections.observableArrayList(coreDatabase.getObservableGenericTL());
		ObservableList<DeadlineTask> deadlineTL = FXCollections.observableArrayList(coreDatabase.getObservableDeadlineTL());
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
