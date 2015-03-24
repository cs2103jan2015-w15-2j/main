package com.nexus.simplify.UI.view;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.DeadlineTask;
import com.nexus.simplify.database.GenericTask;
import com.nexus.simplify.database.TaskListPackage;
import com.nexus.simplify.database.TimedTask;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 
 * @author Toh Jian Feng
 * */

public class BillboardOverviewController {
	//------------------//
	// Class Attributes //
	//------------------//
	
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!";

	// reference to MainApp
	MainApp mainApp;
	
	// deadline-based task attributes
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
	
	// timed task attributes
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
	
	// generic task attributes
	@FXML
	private TableView<GenericTask> genericTaskTable;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskIndexColumn;
	
	@FXML
	private TableColumn<GenericTask, String> genericTaskNameColumn;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskWorkloadColumn;
	
	// Feedback listener
	@FXML
	private TextArea feedbackDisplay;
	
	// 
	@FXML
	private TextField userInputField;
	
	//-------------//
	// Constructor //
	//-------------//
	
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public BillboardOverviewController() {
		
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
		
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
	 * 
	 * Initialises the 3 tables on the interface.
	 * 
	 * @param listPackage the package of observable lists obtained from database
	 * */
	public void initBillboard(Database database) {		
		fillTablesWithData(database.getTaskListPackage());
		displayWelcomeMessage();
		listenForCommand(mainApp);
		updateTables();
	}

	private void initGenericTaskTable() {
		genericTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		genericTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
		genericTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(genericTaskTable.getItems().indexOf(column.getValue())));
	}

	private void initTimedTaskTable() {
		timedTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		timedTaskStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeAsStringProperty());
		timedTaskEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeAsStringProperty());
		timedTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
		timedTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(timedTaskTable.getItems().indexOf(column.getValue())));

	}

	private void initDeadlineTaskTable() {
		deadlineTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameAsStringProperty());
		deadlineTaskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDTAsStringProperty());
		deadlineTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().getWorkloadAsIntegerProperty().asObject());
		deadlineTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(deadlineTaskTable.getItems().indexOf(column.getValue())));

	}
	
	/**
	 * 
	 * fetches updated lists from database once feedback is received from 
	 * the Logic component.
	 * 
	 * */
	private void updateTables() {
		feedbackDisplay.textProperty().addListener((observable, oldvalue, newvalue) -> {
			TaskListPackage listPackage = fetchDataFromDatabase(mainApp.getDatabase());
			fillTablesWithData(listPackage);
		});
	}
	
	//-----------------------//
	// Processing User Input //
	//-----------------------//
	
	private void listenForCommand(MainApp mainApp) {
		userInputField.setOnAction((event) -> {
			String feedback = processInputAndReceiveFeedback(mainApp.getLogic());
			feedbackDisplay.setText(feedback);
		});
	}
	
	private String processInputAndReceiveFeedback(Logic logic) {
		String resultantFeedback = logic.processInput();
		return resultantFeedback;
	}
	
	//-----------------//
	// Display Methods //
	//-----------------//
	
	private void displayWelcomeMessage() {
		feedbackDisplay.setText(MESSAGE_WELCOME);
	}
	
	//--------------------------------------//
	// Data Fetching and Table Manipulation //
	//--------------------------------------//
	
	private TaskListPackage fetchDataFromDatabase(Database database) {
		TaskListPackage listPackage = database.getTaskListPackage();
		return listPackage;
	}
	
	private void fillTablesWithData(TaskListPackage listPackage) {
		ObservableList<DeadlineTask> deadlineTaskList = listPackage.getDeadlineTL();
		ObservableList<TimedTask> timedTaskList = listPackage.getTimedTL();
		ObservableList<GenericTask> genericTaskList = listPackage.getGenericTL();
		
		deadlineTaskTable.setItems(deadlineTaskList);
		timedTaskTable.setItems(timedTaskList);
		genericTaskTable.setItems(genericTaskList);
	}	
}
