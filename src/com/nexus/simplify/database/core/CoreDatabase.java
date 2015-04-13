/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database.core;

import java.io.*;
import java.net.URL;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.database.core.Reader;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;

public class CoreDatabase {


	private static final String DEFAULT_FILE_NAME = "Simplify-Data.json";
	private static final String KEY_FILE_LOCATION = "File location";
	
	private URL url = CoreDatabase.class.getProtectionDomain().getCodeSource().getLocation();
	private String defaultFileLocation = url.getPath();

	private Preferences preference;
	private State state;
	private String dataFileLocation;
	
	private ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();

	private Logger LOGGER = LoggerFactory.getLogger(CoreDatabase.class.getName());
	
	//-------------//
	// Constructor //
	//-------------//

	/**
	 * @param fileName name of input file to be opened
	 * @throws IOException for an interrupted IO operation.
	 * s
	 * */
	public CoreDatabase() throws IOException {
		
		initDatabase();
		Reader reader = new Reader(this);
		JSONArray jsonTaskArray = reader.retrieveDataFromDataFile(getDataFilePath());
		reader.populateTaskLists(jsonTaskArray);
		state = new State();
		
	}

	//----------------//
	// Initialization //
	//----------------//

	public void initDatabase() throws IOException {
		
		preference = Preferences.userRoot().node(this.getClass().getName());
		try {
			preference.clear();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String NO_SAVED_FILEPATH_FOUND = "";
		String fileLocationPath = preference.get(KEY_FILE_LOCATION, NO_SAVED_FILEPATH_FOUND);
		
		if (fileLocationPath.equals(NO_SAVED_FILEPATH_FOUND)) {
			defaultFileLocation = defaultFileLocation.replace("Simplify.jar", "");
			preference.put(KEY_FILE_LOCATION, defaultFileLocation);
			dataFileLocation = defaultFileLocation;
			LOGGER.info("Default settings for file location will be loaded.");
		} else {
			dataFileLocation = fileLocationPath;
			LOGGER.info("Saved settings for file location will be loaded.");
		}
		
	}

	//---------------------//
	// Attribute Accessors //
	//---------------------//

	public ObservableList<GenericTask> getObservableGenericTL() {
		return this.observableGenericTL;
	}

	public ObservableList<TimedTask> getObservableTimedTL() {
		return this.observableTimedTL;
	}

	public ObservableList<DeadlineTask> getObservableDeadlineTL() {
		return this.observableDeadlineTL;
	}

	public ObservableList<GenericTask> getArchivedGenericTL() {
		return this.archivedGenericTL;
	}

	public ObservableList<TimedTask> getArchivedTimedTL() {
		return this.archivedTimedTL;
	}

	public ObservableList<DeadlineTask> getArchivedDeadlineTL() {
		return this.archivedDeadlineTL;
	}
	
	public State getState() {
		return this.state;
	}

	/**
	 * @return the relative file path.
	 * */
	public String getDataFilePath() {
		return this.dataFileLocation + DEFAULT_FILE_NAME;
	}

	/**
	 * @return the location (directory) of the file.
	 * */
	public String getDataFileLocation() {
		return this.dataFileLocation;
	}

	//--------------------//
	// Attribute Mutators //
	//--------------------//

	public void setObservableGenericTL(ObservableList<GenericTask> generic) {
		this.observableGenericTL = generic;
	}

	public void setObservableTimedTL(ObservableList<TimedTask> timed) {
		this.observableTimedTL = timed;
	}

	public void setObservableDeadlineTL(ObservableList<DeadlineTask> deadline) {
		this.observableDeadlineTL = deadline;
	}

	public void setArchivedGenericTL(ObservableList<GenericTask> generic) {
		this.archivedGenericTL = generic;
	}

	public void setArchivedTimedTL(ObservableList<TimedTask> timed) {
		this.archivedTimedTL = timed;
	}

	public void setArchivedDeadlineTL(ObservableList<DeadlineTask> deadline) {
		this.archivedDeadlineTL = deadline;
	}

	public void setDataFileLocation(String newFileLocation) throws IOException {
		
		preference.put(KEY_FILE_LOCATION, newFileLocation);
		dataFileLocation = newFileLocation;
		File file;
		Writer writer = new Writer();
		
		if (isLastCharBackSlash(dataFileLocation)) {
			file = new File(dataFileLocation + DEFAULT_FILE_NAME);
		} else {
			file = new File(dataFileLocation + '\\' + DEFAULT_FILE_NAME);
		}
		
		file.getParentFile().mkdirs();
		file.createNewFile();
		writer.writeToFile(observableGenericTL, observableDeadlineTL, observableTimedTL,
				archivedGenericTL, archivedDeadlineTL, archivedTimedTL, file);
		
	}

	private boolean isLastCharBackSlash(String string) {
		
		int lastIndex = string.length() - 1;
		if (string.charAt(lastIndex) == '\'') {
			return true;
		} else {
			return false;
		}
		
	}
}