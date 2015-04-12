/**
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import java.io.*;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.database.Reader;

public class Database {

	private static final String DATA_FILE_LOCATION = "Data file location";
	private static final String JSON_KEY_DATA_FILE_DIRECTORY = "Data file directory";
	private static final String DEFAULT_FILE_NAME = "input.json";
	private static final String DEFAULT_DATA_FILE_LOCATION = "SavedData/";
	private static final String CONFIG_FILE_LOCATION = "Config/";
	private static final String CONFIG_FILE_NAME = "simplify-config.json";
	private static final String KEY_FILE_LOCATION = "File location";
	
	//------------------//
	// Class Attributes //
	//------------------//

	private Preferences preference;
	private State state;
	private String dataFileLocation;
	private ObservableList<GenericTask> archivedGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> archivedDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> archivedTimedTL = FXCollections.observableArrayList();
	private ObservableList<GenericTask> observableGenericTL = FXCollections.observableArrayList();
	private ObservableList<DeadlineTask> observableDeadlineTL = FXCollections.observableArrayList();
	private ObservableList<TimedTask> observableTimedTL = FXCollections.observableArrayList();

	private Logger LOGGER = LoggerFactory.getLogger(Database.class.getName());
	
	//-------------//
	// Constructor //
	//-------------//

	/**
	 * @param fileName name of input file to be opened
	 * @throws IOException for an interrupted IO operation.
	 * 
	 * */
	public Database() throws IOException {
		
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
		final String NO_SAVED_FILEPATH_FOUND = "";
		String fileLocationPath = preference.get(KEY_FILE_LOCATION, NO_SAVED_FILEPATH_FOUND);
		
		if (fileLocationPath.equals(NO_SAVED_FILEPATH_FOUND)) {
			preference.put(KEY_FILE_LOCATION, DEFAULT_DATA_FILE_LOCATION);
			dataFileLocation = DEFAULT_DATA_FILE_LOCATION;
			LOGGER.info("Default settings for file location will be loaded.");
		} else {
			dataFileLocation = fileLocationPath;
			LOGGER.info("Saved settings for file location will be loaded.");
		}
		
		/*String configFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
		if (!configFileExists(configFilePath)) {
			createNewFile(configFilePath);
			revertToDefaultSettings();
			
		} else {
			retrieveSettingsFromConfigFile();
			
		}*/
		
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
				
		File file = new File(newFileLocation);
		file.mkdirs();
		file.createNewFile();
		dataFileLocation = newFileLocation;
		preference.put(KEY_FILE_LOCATION, newFileLocation);
		
	}

	/**
	 * Creates a new file for the program.
	 * 
	 * @param fileName name of the file
	 * */
	public void createNewFile(String fileName) {
		try {
			File newConfigFile = new File(fileName);

			// we make a new instance of directory for the file.
			if (newConfigFile.getParentFile() != null) {
				newConfigFile.getParentFile().mkdirs();
			}

			newConfigFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//-----------------//
	// File Processing //
	//-----------------//

	/**
	 * Retrieves the data file location from the 
	 * configuration file.
	 * */
	public void retrieveSettingsFromConfigFile() {
		JSONObject configJson = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
			String configFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
			Object object = jsonParser.parse(new FileReader(configFilePath));
			configJson = (JSONObject) object;

			if (!configJson.containsKey(JSON_KEY_DATA_FILE_DIRECTORY)) {
				revertToDefaultSettings();
			} else {
				this.setDataFileLocation(String.valueOf(configJson.get(JSON_KEY_DATA_FILE_DIRECTORY)));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the location of the data file to its default location
	 * and stores the default settings into the configuration file.
	 * 
	 * @throws IOException 
	 * */
	private void revertToDefaultSettings() throws IOException {
		this.setDataFileLocation(DEFAULT_DATA_FILE_LOCATION);
		storeSettingsIntoConfigFile(DEFAULT_DATA_FILE_LOCATION);
	}

	/**
	 * @param configFileName name of program configuration file
	 * @return true if config file exists, false otherwise.
	 * */
	public boolean configFileExists(String configFileName) {
		File configFile = new File(configFileName);
		return configFile.exists();
	}

	/**
	 * 
	 * 
	 * */
	@SuppressWarnings("unchecked") 
	private void storeSettingsIntoConfigFile(String fileLocation) {
		JSONObject configJson = new JSONObject();
		configJson.put(DATA_FILE_LOCATION, fileLocation);
		String outputConfigFilePath = CONFIG_FILE_LOCATION + CONFIG_FILE_NAME;
		// File outputConfigFile = new File(outputConfigFilePath);
		try {
			FileWriter fileWriter = new FileWriter(outputConfigFilePath);
			fileWriter.write(configJson.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}