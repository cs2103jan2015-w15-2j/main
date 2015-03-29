package com.nexus.simplify.database;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nexus.simplify.database.observables.DeadlineTaskList;
import com.nexus.simplify.database.observables.GenericTaskList;
import com.nexus.simplify.database.observables.TimedTaskList;

/**
 * provides a test suite for the various methods found in the Database Class.
 * @author tohjianfeng
 * */
public class DatabaseTest {
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	Database database;
	
	private static String fileName;
	
	private GenericTaskList genericTL;
	private DeadlineTaskList deadlineTL;
	private TimedTaskList timedTL;
	
	
	//------------------------//
	// Initialization Testing //
	//------------------------//
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception {
		
	}
	
	//----------------//
	// Method Testing //
	//----------------//
	
	@Test
	public void addTaskTest() {
		
	}
	
	@Test
	public void deleteTaskTest() {
		
	}
	
	@Test
	public void modifyTaskTest() {
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
