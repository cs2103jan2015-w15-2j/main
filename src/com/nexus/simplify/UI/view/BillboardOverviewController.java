package com.nexus.simplify.UI.view;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.logic.Logic;


import javafx.beans.property.ReadOnlyObjectWrapper;
// import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	private static final int DEADLINE_TASK_COL_INDEX_OFFSET = 1;
	//------------------//
	// Class Attributes //
	//------------------//
	
	// References to main application
	MainApp mainApp;
	Database database;
	
	// Container to store user command history
	CommandHistory commandHistory;
	
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
	private Label feedbackDisplay;
	
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
		commandHistory = new CommandHistory();
		initDeadlineTaskTable();
		initTimedTaskTable();
		initGenericTaskTable();
	}

	/**
	 * Initializes the 3 tables on the interface.
	 * @param listPackage the package of observable lists obtained from database
	 * */
	public void initBillboard() {
		fillTablesWithData();
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
	 * initializes the table columns displaying the indexes of the entries
	 * such that they are dynamically updated upon any modification to the 
	 * number of entries in the table.
	 * */
	private void initTableIndexes() {
		deadlineTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<DeadlineTask, Integer>() {
				@Override
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(String.valueOf(getIndex() + DEADLINE_TASK_COL_INDEX_OFFSET));
					}
				}
			};
		});
		
		timedTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<TimedTask, Integer>() {
				@Override
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(String.valueOf(getIndex() 
								+ DEADLINE_TASK_COL_INDEX_OFFSET 
								+ deadlineTaskTable.getItems().size()));
					}
				}
			};
		});
		
		genericTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<GenericTask, Integer>() {
				@Override
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(String.valueOf(getIndex() 
											   + DEADLINE_TASK_COL_INDEX_OFFSET 
											   + deadlineTaskTable.getItems().size()
											   + timedTaskTable.getItems().size()));
					}
				}
			};
		});
	}
	
	//-----------------------//
	// Processing User Input //
	//-----------------------//
	
	/**
	 * Enables focus on the user input field when user starts 
	 * to type on the keyboard.
	 * 
	 * */
	@FXML
	private void enableFocusToUserInputField() {
		userInputField.requestFocus();
	}
	
	/**
	 * Sends input to the Logic component upon the action
	 * when the user presses Enter on the keyboard.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
	
	
	private void processInputOnEnterKeyPressed() {
		String userCommand = userInputField.getText();
		commandHistory.addCommandToHistory(userCommand);
		
		String feedback = processInputAndReceiveFeedback(mainApp.getLogic(), userCommand);
		feedbackDisplay.setText(feedback);
		
		fillTableIndexes();
		userInputField.clear();
	}
	
	@FXML
	private void processKeyCommandsFromUserInUserInputField(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			processInputOnEnterKeyPressed();
		} else if (event.getCode() == KeyCode.UP) {
			browsePreviousCommand();
		} else if (event.getCode() == KeyCode.DOWN) {
			browseNextCommand();
		}
	}
	
	private void browsePreviousCommand() {
		String previousCommandHistory = commandHistory.browsePreviousCommand();
		userInputField.setText(previousCommandHistory);
	}
	
	private void browseNextCommand() {
		String nextCommandHistory = commandHistory.browseNextCommand();
		userInputField.setText(nextCommandHistory);
	}
	
		
	/**
	 * 
	 * 
	 * 
	 * */
	@FXML
	private void toggleFocusBetweenTableAndInputField() {
		
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
		} catch (Exception e) {
			String exceptionFeedback = e.getMessage();
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
	 * Populates all three tables with from the three Observable task lists
	 * found in the instance of Database.
	 * 
	 * This method will only be called once.
	 * */
	private void fillTablesWithData() {
		ObservableList<DeadlineTask> deadlineTaskList = database.getObservableDeadline();
		ObservableList<TimedTask> timedTaskList = database.getObservableTimed();
		ObservableList<GenericTask> genericTaskList = database.getObservableGeneric();
		
		deadlineTaskTable.setItems(deadlineTaskList);
		timedTaskTable.setItems(timedTaskList);
		genericTaskTable.setItems(genericTaskList);
		
		fillTableIndexes();
	}
	
	/**
	 * Populates the Index columns of each table, based on the updated entries.
	 * */
	private void fillTableIndexes() {		
		initTableIndexes();
		deadlineTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																      		deadlineTaskTable.getItems().indexOf(column.getValue())
																      		+ DEADLINE_TASK_COL_INDEX_OFFSET
																  )
												   );
		timedTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  timedTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
															   )
												); 
		genericTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  genericTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
																	  + timedTaskTable.getItems().size()
																 )
												  );
	}


}
