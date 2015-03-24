package com.nexus.simplify.database;

public interface IDatabase {
	// method signatures
	GenericTaskList readFromFile();
	void writeToFile(GenericTaskList tasklist);
}
