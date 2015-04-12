package com.nexus.simplify.UI.controller;

// @author A0108361M

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Controller class of fxml BillBoardOverview.
 * BillboardOverview displays three tables for each type of task:
 * 	- deadline-based tasks
 * 	- timed tasks
 *  - generic tasks
 * */
public class BillboardOverviewController implements Initializable {
	private static final String LOGGER_ERROR_PROCESSING_FAILED = "Feedback from logic (input processing failed): {}";

	private static final String LOGGER_INFO_PROCESSING_SUCCESSFUL = "Feedback from logic (input processing successful): {}";

	private static final int INDEX_OF_FIRST_ROW_IN_TABLE = 0;
	
	private static final int GENERIC_TASK_TABLE_VISIBLE_ROW_SIZE = 15;
	private static final int DEADLINE_TASK_TABLE_VISIBLE_ROW_SIZE = 7;
	private static final int TIMED_TASK_TABLE_VISIBLE_ROW_SIZE = 7;
	
	private static final int LIST_INDEX_OFFSET = 1;
	private static final int DEADLINE_TASK_COL_INDEX_OFFSET = 1;
	
	private static final String WARNING_SUPPRESSION_RAWTYPES = "rawtypes";
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_WELCOME = "Welcome to Simplify!";

	
	//------------------//
	// Class Attributes //
	//------------------//
	
	// References to main application
	MainApp mainApp;
	Database database;
	
	// Container to store user command history
	CommandHistory commandHistory;
	
	// row index bookmarks for table traversal
	int previousDeadlineTaskTableRowIndex;
	int previousTimedTaskTableRowIndex;
	int previousGenericTaskTableRowIndex;
	
	// logging purposes
	Logger logger;
	
	
	// Attributes of the table displaying deadline-based tasks.
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
	
	// Attributes of the table displaying timed tasks.
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
	

	// Attributes of the table displaying generic tasks.
	@FXML
	private TableView<GenericTask> genericTaskTable;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskIndexColumn;
	
	@FXML
	private TableColumn<GenericTask, String> genericTaskNameColumn;
	
	@FXML
	private TableColumn<GenericTask, Integer> genericTaskWorkloadColumn;
	
	
	// Allows feedback to be displayed on the billboard.
	@FXML
	private Label feedbackDisplay;
	
	
	// Retrieves user input from the keyboard.
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
		previousDeadlineTaskTableRowIndex = 0;
		previousTimedTaskTableRowIndex = 0;
		previousGenericTaskTableRowIndex = 0;
		
		logger = LoggerFactory.getLogger(BillboardOverviewController.class.getName());
		
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
	 * */
	public void initBillboard() {
		fillTablesWithData();
		setTablesToListenForChanges();
		displayWelcomeMessage();
	}
	
	/**
	 * Adds listeners to all three tables so that
	 * any rows added or modified will be automatically
	 * scrolled to and highlighted.
	 * */
	private void setTablesToListenForChanges() {
		setDeadlineTaskTableToListenForChanges();
		setTimedTaskTableToListenForChanges();
		setGenericTaskTableToListenForChanges();
	}

	/**
	 * Adds a listener to the table displaying generic tasks
	 * so that any rows added or modified will be automatically
	 * scrolled to and highlighted.
	 * */
	private void setGenericTaskTableToListenForChanges() {
		genericTaskTable.getItems().addListener(
			new ListChangeListener<GenericTask>() {
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends GenericTask> change) {
					change.next();		
					
					if (change.wasAdded() || change.wasUpdated() || change.wasRemoved()) {
						int locationIndexOfChange = change.getTo() - LIST_INDEX_OFFSET;
						previousGenericTaskTableRowIndex = locationIndexOfChange;
						genericTaskTable.scrollTo(locationIndexOfChange);
						genericTaskTable.getSelectionModel().select(locationIndexOfChange);
					} 
			    } 						
			}
		);
	}

	/**
	 * Adds a listener to the table displaying timed tasks
	 * so that any rows added or modified will be automatically
	 * scrolled to and highlighted.
	 * */
	private void setTimedTaskTableToListenForChanges() {
		timedTaskTable.getItems().addListener(
			new ListChangeListener<TimedTask>() {
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends TimedTask> change) {
					change.next();
						
					if (change.wasAdded() || change.wasUpdated() || change.wasRemoved()) {
						int locationIndexOfChange = change.getTo() - LIST_INDEX_OFFSET;
						previousTimedTaskTableRowIndex = locationIndexOfChange;
						timedTaskTable.scrollTo(locationIndexOfChange);
						timedTaskTable.getSelectionModel().select(locationIndexOfChange);
					} 
					
			    } 				
			}
		);
	}

	/**
	 * Adds a listener to the table displaying deadline-based tasks
	 * so that any rows added or modified will be automatically
	 * scrolled to and highlighted.
	 * */
	private void setDeadlineTaskTableToListenForChanges() {
		deadlineTaskTable.getItems().addListener(
			new ListChangeListener<DeadlineTask>() {
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends DeadlineTask> change) {
					change.next(); // transit to the next instance of change
					
					if (change.wasAdded() || change.wasUpdated() || change.wasRemoved()) {
						int locationIndexOfChange = change.getTo() - LIST_INDEX_OFFSET;
						previousDeadlineTaskTableRowIndex = locationIndexOfChange;
						deadlineTaskTable.scrollTo(locationIndexOfChange);
						deadlineTaskTable.getSelectionModel().select(locationIndexOfChange);
					} 
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
	 * such that they are updated based on the list sizes.
	 * */
	private void initTableIndexes() {
		initDeadlineTaskIndexCol();
		initTimedTaskIndexCol();
		initGenericTaskIndexCol();
	}

	/**
	 * initializes the index columns of the table displaying generic tasks.
	 * the start index of the column is 1 after the last index of the table displaying 
	 * timed tasks.
	 * */
	private void initGenericTaskIndexCol() {
		genericTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<GenericTask, Integer>() {
				@Override 
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle(EMPTY_STRING);
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

	/**
	 * initializes the index columns of the table displaying timed tasks.
	 * the start index of the column is 1 after the last index of the table 
	 * displaying deadline-based tasks.
	 * */
	private void initTimedTaskIndexCol() {
		timedTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<TimedTask, Integer>() {
				@Override
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle(EMPTY_STRING);
					} else {
						setText(String.valueOf(getIndex() 
												+ DEADLINE_TASK_COL_INDEX_OFFSET 
												+ deadlineTaskTable.getItems().size()));
					}
				}
			};
		});
	}

	/**
	 * initializes the index columns of the table displaying timed tasks.
	 * the start index of the column is 1.
	 * */
	private void initDeadlineTaskIndexCol() {
		deadlineTaskIndexColumn.setCellFactory(column -> {
			return new TableCell<DeadlineTask, Integer>() {
				@Override
				protected void updateItem(Integer index, boolean empty) {
					if (index == null || empty) {
						setText(null);
						setStyle(EMPTY_STRING);
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
	
	/**
	 * Listens for any keys pressed by the user when
	 * current focus is on user input field.
	 * Valid key commands are as follows:
	 * 
	 * Enter key: sends input to logic component.
	 * up/down arrow keys: navigate through user command history.
	 * tab key: switches focus to a non-empty table.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
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
				clearSelectionsInAllTables();
				jumpToNonEmptyTable(null);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Listens for any keys pressed by the user when
	 * current focus is on the table displaying deadline-based tasks.
	 * 
	 * Valid key commands are as follows:
	 * 	Shift + tab arrow keys: toggles focus to the table displaying timed tasks.
	 *  tab arrow key: toggles focus to the table displaying generic tasks. 
	 * 	up/down arrow keys: navigate through table rows.
	 *  left/right arrow keys: jump up/down multiple rows.
	 * 
	 * Any other key will return focus to the user input field.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
	@FXML
	private void processKeyCommandsFromDeadlineTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
				break;
			case TAB:
				previousDeadlineTaskTableRowIndex = deadlineTaskTable.getSelectionModel().getSelectedIndex();
				if (event.isShiftDown()) {
					jumpToTimedTaskTable(deadlineTaskTable, previousTimedTaskTableRowIndex);
				} else {
					jumpToGenericTaskTable(deadlineTaskTable, previousGenericTaskTableRowIndex);
				}
				break;
			case DOWN:
				jumpToNextTableRow(deadlineTaskTable);
				break;
			case UP:
				jumpToPrevTableRow(deadlineTaskTable);
				break;
			case LEFT:
				jumpMultipleTableRows(deadlineTaskTable, Direction.UPWARDS, DEADLINE_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			case RIGHT:
				jumpMultipleTableRows(deadlineTaskTable, Direction.DOWNWARDS, DEADLINE_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			default:
				previousDeadlineTaskTableRowIndex = deadlineTaskTable.getSelectionModel().getSelectedIndex();
				jumpToUserInputField(deadlineTaskTable);
				break;
		}
		event.consume();
	}
	
	/**
	 * Listens for any keys pressed by the user when
	 * current focus is on the table displaying timed tasks.
	 * 
	 * Valid key commands are as follows:
	 * Shift + tab arrow keys: toggles focus to the table displaying generic tasks.
	 * Tab arrow key: toggles focus to the table displaying deadline-based tasks. 
	 * up/down arrow keys: navigate through table rows.
	 * left/right arrow keys: jump up/down multiple rows.
	 * 
	 * Any other key will return focus to the user input field.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
	@FXML
	private void processKeyCommandsFromTimedTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
				break;
			case TAB:
				previousTimedTaskTableRowIndex = timedTaskTable.getSelectionModel().getSelectedIndex();
				if (event.isShiftDown()) {
					jumpToGenericTaskTable(timedTaskTable, previousGenericTaskTableRowIndex);
				} else {
					jumpToDeadlineTaskTable(timedTaskTable, previousDeadlineTaskTableRowIndex);
				}
				break;
			case UP:
				jumpToPrevTableRow(timedTaskTable);
				break;
			case DOWN:
				jumpToNextTableRow(timedTaskTable);
				break;
			case LEFT:
				jumpMultipleTableRows(timedTaskTable, Direction.UPWARDS, TIMED_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			case RIGHT:
				jumpMultipleTableRows(timedTaskTable, Direction.DOWNWARDS, TIMED_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			default:
				previousTimedTaskTableRowIndex = timedTaskTable.getSelectionModel().getSelectedIndex();
				jumpToUserInputField(timedTaskTable);
				break;
		}
		event.consume();
	}
	
	/**
	 * Listens for any keys pressed by the user when
	 * current focus is on the table displaying deadline-based tasks.
	 * 
	 * Valid key commands are as follows:
	 * Shift + tab arrow keys: toggles focus to the table displaying deadline-based tasks.
	 * tab arrow key: toggles focus to the table displaying timed tasks.	
	 * up/down arrow keys: navigate through table rows.
	 * left/right arrow keys: jump up/down multiple rows.
	 * 
	 * Any other key will return focus to the user input field.
	 * 
	 * @param event the event in which a key is pressed.
	 * */
	@FXML
	private void processKeyCommandsFromGenericTaskTable(KeyEvent event) {
		switch (event.getCode()) {
			case SHIFT:
				break;
			case TAB:
				previousGenericTaskTableRowIndex = genericTaskTable.getSelectionModel().getSelectedIndex();
				if (event.isShiftDown()) {
					jumpToDeadlineTaskTable(genericTaskTable, previousDeadlineTaskTableRowIndex);
					break;
				} else {
					jumpToTimedTaskTable(genericTaskTable, previousTimedTaskTableRowIndex);
				}
				break;
			case UP:
				jumpToPrevTableRow(genericTaskTable);
				break;
			case DOWN:
				jumpToNextTableRow(genericTaskTable);
				break;
			case LEFT:
				jumpMultipleTableRows(genericTaskTable, Direction.UPWARDS, GENERIC_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			case RIGHT:
				jumpMultipleTableRows(genericTaskTable, Direction.DOWNWARDS, GENERIC_TASK_TABLE_VISIBLE_ROW_SIZE);
				break;
			default:
				previousGenericTaskTableRowIndex = genericTaskTable.getSelectionModel().getSelectedIndex();
				jumpToUserInputField(genericTaskTable);
				break;
		}
		event.consume();
	}
	
	/**
	 * Switches focus to a table that has at least one entry
	 * when the tab key is pressed.
	 * 
	 * Pre-condition: Current focus is on the user input field. 
	 * 
	 * @param prevTable the previous table that had focus
	 * */
	private void jumpToNonEmptyTable(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView prevTable) {
		if (!deadlineTaskTable.getItems().isEmpty()) {
			jumpToDeadlineTaskTable(prevTable, previousDeadlineTaskTableRowIndex);
		} else {
			if (!timedTaskTable.getItems().isEmpty()) {
				jumpToTimedTaskTable(prevTable, previousTimedTaskTableRowIndex);
			} else {
				jumpToGenericTaskTable(prevTable, previousGenericTaskTableRowIndex);
			}
		}
	}

	/**
	 * Switches focus to the table displaying generic tasks. 
	 * The last row of the table will be highlighted.
	 * 
	 * Focus will not be shifted to the table if it is empty.
	 * 
	 * If the focus was switched from any other table, any highlighted row
	 * of that particular table will be de-selected.
	 * 
	 * @param prevTable the previous table that had focus
	 * @param previouslyHighlightedIndex the index of the row previously highlighted
	 * */
	private void jumpToGenericTaskTable(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView prevTable, 
			                            int previouslyHighlightedIndex) {
		ObservableList<GenericTask> genericTaskList = genericTaskTable.getItems();
		
		if (!genericTaskList.isEmpty()) {
			genericTaskTable.requestFocus();
			
			genericTaskTable.scrollTo(previouslyHighlightedIndex);
			genericTaskTable.getSelectionModel().select(previouslyHighlightedIndex);
			
			if (!(prevTable == null)) {
				prevTable.getSelectionModel().clearSelection();
			}
		}
	}

	/**
	 * Switches focus to the table displaying timed tasks. 
	 * The last row of the table will be highlighted.
	 * 
	 * Focus will not be shifted to the table if it is empty.
	 * 
	 * If the focus was switched from any other table, any highlighted row
	 * of that particular table will be de-selected.
	 * 
	 * @param prevTable the previous table that had focus
	 * @param previouslyHighlightedIndex the index of the row previously highlighted
	 * */
	private void jumpToTimedTaskTable(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView prevTable, 
			                          int previouslyHighlightedIndex) {
		ObservableList<TimedTask> timedTaskList = timedTaskTable.getItems();
		
		if (!timedTaskList.isEmpty()) {
			timedTaskTable.requestFocus();
			
			timedTaskTable.scrollTo(previouslyHighlightedIndex);
			timedTaskTable.getSelectionModel().select(previouslyHighlightedIndex);
			
			if (!(prevTable == null)) {
				prevTable.getSelectionModel().clearSelection();
			}
		}
	}

	/**
	 * Switches focus to the table displaying deadline-based tasks. 
	 * The last row of the table will be highlighted.
	 * 
	 * Focus will not be shifted to the table if it is empty.
	 * 
	 * If the focus was switched from any other table, any highlighted row
	 * of that particular table will be de-selected.
	 * 
	 * @param prevTable the previous table that had focus
	 * @param previouslyHighlightedIndex the index of the row previously highlighted
	 * */
	private void jumpToDeadlineTaskTable(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView prevTable, 
			                             int previouslyHighlightedIndex) {
		ObservableList<DeadlineTask> deadlineTaskList = deadlineTaskTable.getItems();
		
		if (!deadlineTaskList.isEmpty()) {
			deadlineTaskTable.requestFocus();
			
			deadlineTaskTable.scrollTo(previouslyHighlightedIndex);
			deadlineTaskTable.getSelectionModel().select(previouslyHighlightedIndex);
			
			if (!(prevTable == null)) {
				prevTable.getSelectionModel().clearSelection();
			}
		}

	}
	
	/**
	 * Highlights the next table row.
	 * */
	private void jumpToNextTableRow(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView table) {
		table.getSelectionModel().selectBelowCell();
		table.scrollTo(table.getSelectionModel().getSelectedIndex());
	}
	
	/**
	 * Highlights the previous table row.
	 * */
	private void jumpToPrevTableRow(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView table) {
		table.getSelectionModel().selectAboveCell();
		table.scrollTo(table.getSelectionModel().getSelectedIndex());
	}
	
	/**
	 * Highlights the next/previous row after/before an interval of rows.
	 * @param table the table that currently has focus
	 * @param direction determines if navigation is upwards or downwards
	 * @param numRows the row interval
	 * */
	private void jumpMultipleTableRows(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView table, 
			                           Direction direction, int numRows) {
		int currentRowIndex = table.getSelectionModel().getSelectedIndex();
		int destinationRowIndex;
		switch (direction) {
			case UPWARDS:
				destinationRowIndex = currentRowIndex - numRows;
				if (destinationRowIndex < INDEX_OF_FIRST_ROW_IN_TABLE) {
					destinationRowIndex = INDEX_OF_FIRST_ROW_IN_TABLE;
				}
				table.getSelectionModel().select(destinationRowIndex);
				table.scrollTo(destinationRowIndex);
				break;
			case DOWNWARDS:
				destinationRowIndex = currentRowIndex + numRows;
				int indexOfLastRowInTable = table.getItems().size() - LIST_INDEX_OFFSET;
				if (destinationRowIndex > indexOfLastRowInTable) {
					destinationRowIndex = indexOfLastRowInTable;
				}
				table.getSelectionModel().select(destinationRowIndex);
				table.scrollTo(destinationRowIndex);
				break;
		}
	}
	
	/**
	 * Switches focus to the user input field.
	 * All highlighted rows in any table will be de-selected.
	 * */
	private void jumpToUserInputField(@SuppressWarnings(WARNING_SUPPRESSION_RAWTYPES) TableView prevTable) {
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
		clearSelectionsInAllTables();
		
		String userCommand = userInputField.getText().trim();
		commandHistory.addCommandToHistory(userCommand);
		
		String feedback = processInputAndReceiveFeedback(mainApp.getLogic(), userCommand);
		feedbackDisplay.setText(feedback);
		
		fillTableIndexes();
		userInputField.clear();
	}

	/**
	 * De-selects all highlighted rows for all three tables.
	 * */
	private void clearSelectionsInAllTables() {
		deadlineTaskTable.getSelectionModel().clearSelection();
		timedTaskTable.getSelectionModel().clearSelection();
		genericTaskTable.getSelectionModel().clearSelection();
	}
	
	/**
	 * Navigates through user command history and displays the 
	 * previous command typed into the user input field.
	 * */
	private void browsePreviousCommand() {
		String previousCommandHistory = commandHistory.browsePreviousCommand();
		if (!previousCommandHistory.equals(EMPTY_STRING)) {
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
			logger.info(LOGGER_INFO_PROCESSING_SUCCESSFUL, resultantFeedback);
			return resultantFeedback;
		} catch (Exception e) {
			String exceptionFeedback = e.getMessage();
			logger.error(LOGGER_ERROR_PROCESSING_FAILED, exceptionFeedback);
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
	 * Populates all three tables with data from the three Observable task lists
	 * found in the instance of Database.
	 * 
	 * This method will only be called once as the tables will continue to listen to
	 * any changes to the three task lists.
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

	/**
	 * Populates the index columns of the task table displaying generic tasks.
	 * The starting index of this task table is 1 after the last index
	 * of the task table displaying timed tasks.
	 * */
	private void fillGenericTaskTableIndexes() {
		genericTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  genericTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
																	  + timedTaskTable.getItems().size()
																 )
												  );
	}

	/**
	 * Populates the index columns of the task table displaying generic tasks.
	 * The starting index of this task table is 1 after the last index
	 * of the task table displaying deadline-based tasks.
	 * */
	private void fillTimedTaskTableIndexes() {
		timedTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																	  timedTaskTable.getItems().indexOf(column.getValue()) 
																	  + DEADLINE_TASK_COL_INDEX_OFFSET 
																	  + deadlineTaskTable.getItems().size()
															   )
												);
	}

	/**
	 * Populates the index columns of the task table displaying generic tasks.
	 * The starting index of this task table is 1.
	 * */
	private void fillDeadlineTaskTableIndexes() {
		deadlineTaskIndexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer> (
																      		deadlineTaskTable.getItems().indexOf(column.getValue())
																      		+ DEADLINE_TASK_COL_INDEX_OFFSET
																  )
												   );
	}
}
