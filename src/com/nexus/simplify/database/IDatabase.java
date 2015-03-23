package Database;

import com.nexus.simplify.logic.TaskList;

public interface IDatabase {
	// method signatures
	GenericTaskList readFromFile();
	void writeToFile(GenericTaskList tasklist);
}
