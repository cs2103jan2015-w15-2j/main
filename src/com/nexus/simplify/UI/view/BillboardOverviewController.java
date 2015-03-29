package com.nexus.simplify.UI.view;

import java.text.ParseException;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.DeadlineTask;
import com.nexus.simplify.database.GenericTask;
import com.nexus.simplify.database.TaskListPackage;
import com.nexus.simplify.database.TimedTask;
import com.nexus.simplify.logic.Logic;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller class of the Object BillBoardOverview.
 * BillboardOverview displays three tables for each type of task:
 * 1. deadline-based tasks
 * 2. timed tasks
 * 3. generic tasks
 * @author Toh Jian Feng
 * */
public class BillboardOverviewController {
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!";
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	// References to main application
	MainApp mainApp;
	Database database;
	
	/**
	 * Attributes of the table displaying deadline-based tasks.
	 * */
	@FXML
	private TableView<DeadlineTask> deadlineTaskTable;
	
	@FXML
	private TableColumn<DeadlineTask, Integer> deadlineTaskIndexColumn;
	
	@FXML
	private TableColumn<DeadlineTask, String> deadlineTaskNameColumn;
	
	@FXML
	private TableColumn<DeadlineTask, String> deadlineTaskDueDateColumn;
	
	@FXML
	private TableColumn<DeadlineTask, Integer> deadlineTaskWorkloadColumn;
	
	/**
	 * Attributes of the table displaying timed tasks.
	 * */
	@FXML
	private TableView<TimedTask> timedTaskTable;
	
	@FXML
	private TableColumn<TimedTask, Integer> timedTaskIndexColumn;
	
	@FXML
	private TableColumn<TimedTask, String> timedTaskNameColumn;
	
	@FXML
	private TableColumn<TimedTask, String> timedTaskStartTimeColumn;
	
	@FXML
	private TableColumn<TimedTask, String> timedTaskEndTimeColumn;
	
	@FXML
	private TableColumn<TimedTask, Integer> timedTaskWorkloadColumn;
	
	/**
	 * Attributes of the table displaying generic tasks.
	 * 
	 * */
	@FXML
	private TableView<GenericTask> genericTaskTable;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskIndexColumn;
	
	@FXML
	private TableColumn<GenericTask, String> genericTaskNameColumn;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskWorkloadColumn;
	
	/**
	 * Allows feedback to be displayed on the billboard.
	 * */
	@FXML
	private TextArea feedbackDisplay;
	
	/**
	 * Retrieves user input from the keyboard.
	 * */
	@FXML
	private TextField userInputField;
	
	//-------------//
	// Constructor //
	//-------------//
	
    /**
     * The constructor is called before the initialize() method.
     */
	public BillboardOverviewController() {
		
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
		
	/**
	 * Called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp the main class for the program
	 * */	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;		
	}
	
	/**
	 * Called by the main application to reference its instance of database.
	 * 
	 * @param database the database instance from the main application
	 * */
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	//----------------//
	// Initialization //
	//----------------//
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		initDeadlineTaskTable();
		initTimedTaskTable();
		initGenericTaskTable();
	}

	/**
	 * Initializes the 3 tables on the interface.
	 * @param listPackage the package of observable lists obtained from database
	 * */
	public void initBillboard() {
		updateTables();
		displayWelcomeMessage();
	}

	/**
	 * Initializes the table displaying generic tasks with columns representing each attribute of the task class.
	 * */
	private void initGenericTaskTable() {
		genericTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		genericTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}

	/**
	 * Initializes the table displaying timed tasks with columns representing each attribute of the task class.
	 * */
	private void initTimedTaskTable() {
		timedTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		timedTaskStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeAsStringProperty());
		timedTaskEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeAsStringProperty());
		timedTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}

	/**
	 * Initializes the table displaying deadline-based tasks with columns representing each attribute of the task class.
	 * */
	private void initDeadlineTaskTable() {
		deadlineTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		deadlineTaskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDTAsStringProperty());
		deadlineTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}
	
	/**
	 * fetches updated lists from database once feedback is received from 
	 * the Logic component.
	 * */
	private void updateTables() {
		TaskListPackage listPackage = fetchDataFromDatabase(database);
		fillTablesWithData(listPackage);

	}
	
	//-----------------------//
	// Processing User Input //
	//-----------------------//
	
	/**
	 * Sends input to the Logic component upon the action
	 * when the user presses Enter on the keyboard.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
	@FXML
	private void processInputOnEnterKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String feedback = processInputAndReceiveFeedback(mainApp.getLogic(), userInputField.getText());
			feedbackDisplay.setText(feedback);
			updateTables();
			userInputField.clear();
		}
	}
	
	/**
	 * passes user input to the Logic component to process
	 * it as a command.
	 * 
	 * @param logic reference to the logic component
	 * @param userInput command entered by the user
	 * @return feedback from the logic component after processing the input.
	 * */
	private String processInputAndReceiveFeedback(Logic logic, String userInput) {
		String resultantFeedback;
		try {
			resultantFeedback = logic.executeCommand(userInput);
			return resultantFeedback;
		} catch (ParseException e) {
			String exceptionFeedback = e.toString();
			return exceptionFeedback;
		}
	}
	
	//-----------------//
	// Display Methods //
	//-----------------//
	
	/**
	 * displays a welcome message to the user during a new session.
	 * */
	private void displayWelcomeMessage() {
		feedbackDisplay.setText(MESSAGE_WELCOME);
	}
	
	//-----------------------------------------//
	// Data Observation and Table Manipulation //
	//-----------------------------------------//
	
	/**
	 * @param database the reference to the single instance of database in main app.
	 * @return a task list package that wraps the three observable task lists together.
	 * */
	private TaskListPackage fetchDataFromDatabase(Database database) {
		TaskListPackage listPackage = database.getTaskListPackage();
		return listPackage;
	}
	
	/**
	 * Populates all three tables with data fetched from database
	 * @param listPackage the wrapper class containing all three ObservableLists
	 * */
	private void fillTablesWithData(TaskListPackage listPackage) {
		ObservableList<DeadlineTask> deadlineTaskList = listPackage.getDeadlineTL();
		ObservableList<TimedTask> timedTaskList = listPackage.getTimedTL();
		ObservableList<GenericTask> genericTaskList = listPackage.getGenericTL();
		
		deadlineTaskTable.setItems(deadlineTaskList);
		timedTaskTable.setItems(timedTaskList);
		genericTaskTable.setItems(genericTaskList);
	}
}
