package com.nexus.simplify.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.nexus.simplify.database.core.Reader;
import com.nexus.simplify.test.database.DatabaseTest;
import com.nexus.simplify.test.logic.TestAdd;
import com.nexus.simplify.test.logic.TestDelete;
import com.nexus.simplify.test.logic.TestDisplay;
import com.nexus.simplify.test.logic.TestDone;
import com.nexus.simplify.test.logic.TestModify;
import com.nexus.simplify.test.parser.ParserTest;

@RunWith(Suite.class)
@SuiteClasses({
    
	MultipleIntegrationsTest.class,
    
	// Parser Unit Tests
	ParserTest.class,
	
	// Logic Unit Tests
	TestAdd.class,
	TestDelete.class,
	TestDisplay.class,
	TestDone.class,
	TestModify.class,
    
	// Database Unit Tests
	DatabaseTest.class,
	Reader.class
	
	//TODO Add ui tests
})

public class AllTests {}