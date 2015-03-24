package com.nexus.simplify.UI.view;

import java.text.ParseException;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.DeadlineTask;
import com.nexus.simplify.database.GenericTask;
import com.nexus.simplify.database.TaskListPackage;
import com.nexus.simplify.database.TimedTask;
import com.nexus.simplify.logic.Logic;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
 * @author Toh Jian Feng
 * */
public class BillboardOverviewController {
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!";
	
	//------------------//
	// Class Attributes //
	//------------------//
	
	/**
	 * Reference to MainApp.
	 * Required for controller to link with MainApp.
	 * */
	MainApp mainApp;
	
	/**
	 * Attributes of the table displaying deadline-based tasks.
	 * 
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
	 * 
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
	
	private int currentDeadlineTLSize = 0;
	private int currentTimedTLSize = 0;
	
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
	 * Initialises the 3 tables on the interface.
	 * 
	 * @param listPackage the package of observable lists obtained from database
	 * */
	public void initBillboard(Database database) {
		TaskListPackage tlPackage = fetchDataFromDatabase(database);
		fillTablesWithData(tlPackage);
		updateTableIndexValues();
		displayWelcomeMessage();
		updateTables();
	}

	/**
	 * Initializes the table displaying generic tasks with columns representing each attribute of the task class.
	 * Only genericTaskIndexColumn has no relation to the GenericTask class.
	 * */
	private void initGenericTaskTable() {
		genericTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		genericTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}

	/**
	 * Initializes the table displaying timed tasks with columns representing each attribute of the task class.
	 * Only timedTaskIndexColumn has no relation to the TimedTask class.
	 * */
	private void initTimedTaskTable() {
		timedTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		timedTaskStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeAsStringProperty());
		timedTaskEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeAsStringProperty());
		timedTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}

	/**
	 * Initializes the table displaying deadline-based tasks with columns representing each attribute of the task class.
	 * Only deadlineTaskIndexColumn has no relation to the DeadlineTask class.
	 * */
	private void initDeadlineTaskTable() {
		deadlineTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		deadlineTaskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDTAsStringProperty());
		deadlineTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
	}
	
	private void updateTableIndexValues() {
		genericTaskIndexColumn.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<Integer>(
						genericTaskTable.getItems().indexOf(column.getValue()) + currentDeadlineTLSize + currentTimedTLSize)
						);
		timedTaskIndexColumn.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<Integer>(
						timedTaskTable.getItems().indexOf(column.getValue()) + currentDeadlineTLSize)
						);
		deadlineTaskIndexColumn.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<Integer>(
						deadlineTaskTable.getItems().indexOf(column.getValue()))
						);
	}
	
	/**
	 * fetches updated lists from database once feedback is received from 
	 * the Logic component.
	 * */
	private void updateTables() {
		feedbackDisplay.textProperty().addListener((observable, oldvalue, newvalue) -> {
			TaskListPackage listPackage = fetchDataFromDatabase(MainApp.getDatabase());
			fillTablesWithData(listPackage);
		});
	}
	
	//-----------------------//
	// Processing User Input //
	//-----------------------//
	
	@FXML
	private void processInputOnEnterKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String feedback = processInputAndReceiveFeedback(MainApp.getLogic(), userInputField.getText());
			feedbackDisplay.setText(feedback);
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
	
	//--------------------------------------//
	// Data Fetching and Table Manipulation //
	//--------------------------------------//
	
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
		
		updateTableSizes(deadlineTaskList, timedTaskList);
		
		deadlineTaskTable.setItems(deadlineTaskList);
		timedTaskTable.setItems(timedTaskList);
		genericTaskTable.setItems(genericTaskList);
	}
	
	
	/**
	 * updates the refernce size values of the deadline-based task tables
	 * and the timed task tables.
	 * 
	 * */
	private void updateTableSizes(ObservableList<DeadlineTask> deadlineTaskList, ObservableList<TimedTask> timedTaskList) {
		currentTimedTLSize = timedTaskList.size();
		currentDeadlineTLSize = deadlineTaskList.size();
	}
}
