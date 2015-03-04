package com.nexus.simplify;

public interface IDatabase {
	// method signatures
	TaskList readFromFile();
	void writeToFile(TaskList tasklist);
}
