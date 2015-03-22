package com.nexus.simplify.UI.view;

import com.nexus.simplify.MainApp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * @author Toh Jian Feng
 * */

public class BillboardOverviewController {
	//------------------//
	// Class Attributes //
	//------------------//
	
	// reference to MainApp
	MainApp mainApp;
	
	// deadline task attributes
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
	
	// floating task attributes
	@FXML
	private TableView floatingTaskTable;
	
	@FXML
	private TableColumn<FloatingTask, Integer> floatingTaskIndexColumn;
	
	@FXML
	private TableColumn<FloatingTask, String> floatingTaskNameColumn;
	
	@FXML
	private TableColumn<FloatingTask, Integer> floatingTaskWorkloadColumn;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public BillboardOverviewController() {
		
	}
	
	//----------------//
	// Initialization //
	//----------------//
	
	public void initBillboard(MainApp mainApp, TaskListPackage listPackage) {
		setMainApp(mainApp);
		
		initDeadlineTaskTable();
		initTimedTaskTable();
		initFloatingTaskTable();
		
		fillTablesWithData(listPackage);
	}

	private void initFloatingTaskTable() {
		floatingTaskIndexColumn.setCellValueFactory(cellData -> cellData.getValue().taskIndexProperty());
		floatingTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		floatingTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().taskWorkloadProperty());
	}

	private void initTimedTaskTable() {
		timedTaskIndexColumn.setCellValueFactory(cellData -> cellData.getValue().taskIndexProperty());
		timedTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		timedTaskStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskStartTimeProperty());
		timedTaskEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskEndTimeProperty());
		timedTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().taskWorkloadProperty());
	}

	private void initDeadlineTaskTable() {
		deadlineTaskIndexColumn.setCellValueFactory(cellData -> cellData.getValue().taskIndexProperty());
		deadlineTaskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		deadlineTaskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().taskDueDateProperty());
		deadlineTaskWorkloadColumn.setCellValueFactory(cellData -> cellData.getValue().taskWorkloadProperty());
	}
	
	private void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;		
	}

	private void fillTablesWithData(TaskListPackage listPackage) {
		ObservableList<DeadlineTask> deadlineTaskList = listPackage.getDeadlineTaskList();
		ObservableList<TimedTask> timedTaskList = listPackage.getTimedTaskList();
		ObservableList<FloatingTask> floatingTaskList = listPackage.getFloatingTaskList();
		
		deadlineTaskTable.setItems(deadlineTaskList);
		timedTaskTable.setItems(timedTaskList);
		floatingTaskTable.setItems(floatingTaskList);
	}
}
