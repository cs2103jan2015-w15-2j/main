package com.nexus.simplify;

import com.nexus.simplify.logic.TaskList;

public interface IDatabase {
	// method signatures
	TaskList readFromFile();
	void writeToFile(TaskList tasklist);
}
