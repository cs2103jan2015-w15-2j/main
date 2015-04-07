package com.nexus.simplify.UI.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.nexus.simplify.MainApp;
import com.nexus.simplify.UI.commandhistory.CommandHistory;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.database.tasktype.DeadlineTask;
import com.nexus.simplify.database.tasktype.GenericTask;
import com.nexus.simplify.database.tasktype.TimedTask;
import com.nexus.simplify.logic.Logic;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
// import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Controller class of the Object BillBoardOverview.
 * BillboardOverview displays three tables for each type of task:
 * 1. deadline-based tasks
 * 2. timed tasks
 * 3. generic tasks
 * @author Toh Jian Feng
 * */
public class BillboardOverviewController implements Initializable {
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
	
	// List of Key Combinations
	
	
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
		// empty method
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
     * */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commandHistory = new CommandHistory();
		initDeadlineTaskTable();
		initTimedTaskTable();
		initGenericTaskTable();
		
		// forces the user input field to gain focus 
		// immediately after application starts.
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            userInputField.requestFocus();
	        }
	    });	
	}

	/**
	 * Initializes the 3 tables on the interface.
	 * @param listPackage the package of observable lists obtained from database
	 * */
	public void initBillboard() {
		fillTablesWithData();
		displayWelcomeMessage();
		
		deadlineTaskTable.getItems().addListener(
				new ListChangeListener<DeadlineTask>() {
				@Override
				public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends DeadlineTask> arg0) {
				    	deadlineTaskTable.scrollTo(deadlineTaskTable.getItems().size() - 1);
				    	deadlineTaskTable.getSelectionModel().selectLast();
				    } 				
				}
			);
			
			timedTaskTable.getItems().addListener(
					new ListChangeListener<TimedTask>() {
					@Override
					public void onChanged(
						javafx.collections.ListChangeListener.Change<? extends TimedTask> arg0) {
					    	timedTaskTable.scrollTo(timedTaskTable.getItems().size() - 1);
					    	timedTaskTable.getSelectionModel().selectLast();
					    } 				
					}
			);
			
			genericTaskTable.getItems().addListener(
					new ListChangeListener<GenericTask>() {
					@Override
					public void onChanged(
						javafx.collections.ListChangeListener.Change<? extends GenericTask> arg0) {
					    	genericTaskTable.scrollTo(genericTaskTable.getItems().size() - 1);
					    	genericTaskTable.getSelectionModel().selectLast();
					    } 					
					}
			);
			
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
		initDeadlineTaskIndexCol();
		initTimedTaskIndexCol();
		initGenericTaskIndexCol();
	}

	private void initGenericTaskIndexCol() {
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

	private void initTimedTaskIndexCol() {
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
	}

	private void initDeadlineTaskIndexCol() {
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
	}
	
	//--------------------//
	// Key Event Handling //
	//--------------------//
	
	@FXML
	private void processKeyCommandsFromUserInUserInputField(KeyEvent event) {
		switch (event.getCode()) {
			case ENTER:
				processInputOnEnterKeyPressed();
				break;
			case UP:
				browsePreviousCommand();
				break;
			case DOWN:
				browseNextCommand();
				break;
			case TAB:
				jumpToNonEmptyTable(null);
				break;
			default:
				break;
		}
	}
	
	@FXML
	private void processKeyCommandsFromDeadlineTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
				break;
			case DOWN:
				if (event.isShiftDown()) {
					jumpToTimedTaskTable(deadlineTaskTable);
					break;
				} else {
					jumpToNextTableRow(deadlineTaskTable);
					break;
				}
			case UP:
				jumpToPrevTableRow(deadlineTaskTable);
				break;
			case RIGHT:
				if (event.isShiftDown()) {
					jumpToGenericTaskTable(deadlineTaskTable);
					break;
				} 
			default:
				jumpToUserInputField(deadlineTaskTable);
				break;
		}
		event.consume();
	}
	
	@FXML
	private void processKeyCommandsFromTimedTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
			case BACK_SPACE:
			case DELETE:
				break;
			case UP:
				if (event.isShiftDown()) {
					jumpToDeadlineTaskTable(timedTaskTable);
					break;
				} else {
					jumpToPrevTableRow(timedTaskTable);
					break;
				}
			case DOWN:
				jumpToNextTableRow(timedTaskTable);
				break;
			case RIGHT:
				if (event.isShiftDown()) {
					jumpToGenericTaskTable(timedTaskTable);
					break;
				} 
			default:
				jumpToUserInputField(timedTaskTable);
				break;
		}
		event.consume();
	}
	
	@FXML
	private void processKeyCommandsFromGenericTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
				break;
			case LEFT:
				if (event.isShiftDown()) {
					jumpToDeadlineTaskTable(genericTaskTable);
					break;
				} 
			case UP:
				jumpToPrevTableRow(genericTaskTable);
				break;
			case DOWN:
				jumpToNextTableRow(genericTaskTable);
				break;
			default:
				jumpToUserInputField(genericTaskTable);
				break;
		}
		event.consume();
	}
	
	/**
	 * Switches focus to a non empty table when the tab
	 * key is pressed when the user input field has focus.
	 * 
	 * @param prevTable the previous table that had focus
	 * */
	private void jumpToNonEmptyTable(@SuppressWarnings("rawtypes") TableView prevTable) {
		if (!deadlineTaskTable.getItems().isEmpty()) {
			jumpToDeadlineTaskTable(prevTable);
		} else {
			if (!timedTaskTable.getItems().isEmpty()) {
				jumpToTimedTaskTable(prevTable);
			} else {
				jumpToGenericTaskTable(prevTable);
			}
		}
	}

	private void jumpToGenericTaskTable(@SuppressWarnings("rawtypes") TableView prevTable) {
		if (!genericTaskTable.getItems().isEmpty()) {
			genericTaskTable.requestFocus();
			genericTaskTable.scrollTo(genericTaskTable.getItems().size() - 1);
			genericTaskTable.getSelectionModel().selectLast();
			
			if (!(prevTable == null)) {
				prevTable.getSelectionModel().clearSelection();
			}
		}
	}

	private void jumpToTimedTaskTable(@SuppressWarnings("rawtypes") TableView prevTable) {
		timedTaskTable.requestFocus();
		timedTaskTable.scrollTo(timedTaskTable.getItems().size() - 1);
		timedTaskTable.getSelectionModel().selectLast();
		
		if (!(prevTable == null)) {
			prevTable.getSelectionModel().clearSelection();
		}
	}

	private void jumpToDeadlineTaskTable(@SuppressWarnings("rawtypes") TableView prevTable) {
		deadlineTaskTable.requestFocus();
		deadlineTaskTable.scrollTo(deadlineTaskTable.getItems().size() - 1);
		deadlineTaskTable.getSelectionModel().selectLast();
		
		if (!(prevTable == null)) {
			prevTable.getSelectionModel().clearSelection();
		}
	}
	
	private void jumpToNextTableRow(@SuppressWarnings("rawtypes") TableView table) {
		table.getSelectionModel().selectBelowCell();
	}
	
	private void jumpToPrevTableRow(@SuppressWarnings("rawtypes") TableView table) {
		table.getSelectionModel().selectAboveCell();
	}
	
	private void jumpToUserInputField(@SuppressWarnings("rawtypes") TableView prevTable) {
		if (!userInputField.isFocused()) {
			userInputField.requestFocus();
			prevTable.getSelectionModel().clearSelection();
		}
	}
	
	//-----------------------//
	// Processing User Input //
	//-----------------------//
	
	/**
	 * Sends input to the Logic component upon the action
	 * when the user presses Enter on the keyboard.
	 * */
	private void processInputOnEnterKeyPressed() {
		String userCommand = userInputField.getText().trim();
		commandHistory.addCommandToHistory(userCommand);
		
		String feedback = processInputAndReceiveFeedback(mainApp.getLogic(), userCommand);
		feedbackDisplay.setText(feedback);
		
		fillTableIndexes();
		userInputField.clear();
	}
	
	/**
	 * Navigates through user command history and displays the 
	 * previous command typed into the user input field.
	 * */
	private void browsePreviousCommand() {
		String previousCommandHistory = commandHistory.browsePreviousCommand();
		if (!previousCommandHistory.equals("")) {
			userInputField.setText(previousCommandHistory);
		}
	}
	
	/**
	 * Navigates through user command history and displays the 
	 * next command typed into the user input field.
	 * */
	private void browseNextCommand() {
		String nextCommandHistory = commandHistory.browseNextCommand();
		userInputField.setText(nextCommandHistory);
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
		ObservableList<DeadlineTask> deadlineTaskList = database.getObservableDeadlineTL();
		ObservableList<TimedTask> timedTaskList = database.getObservableTimedTL();
		ObservableList<GenericTask> genericTaskList = database.getObservableGenericTL();
		
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
		fillDeadlineTaskTableIndexes();
		fillTimedTaskTableIndexes(); 
		fillGenericTaskTableIndexes();
	}

	private void fillGenericTaskTableIndexes() {
		genericTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  genericTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
																	  + timedTaskTable.getItems().size()
																 )
												  );
	}

	private void fillTimedTaskTableIndexes() {
		timedTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  timedTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
															   )
												);
	}

	private void fillDeadlineTaskTableIndexes() {
		deadlineTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																      		deadlineTaskTable.getItems().indexOf(column.getValue())
																      		+ DEADLINE_TASK_COL_INDEX_OFFSET
																  )
												   );
	}
}
