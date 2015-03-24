package com.nexus.simplify.database;

public interface IDatabase {
	// method signatures
	void readFromFile();
	void writeToFile(GenericTaskList inputGenericTL, DeadlineTaskList inputDeadlineTL, TimedTaskList inputTimedTL);
}
