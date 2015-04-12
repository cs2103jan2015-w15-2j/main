package com.nexus.simplify;

//@author A0108361M
import java.io.IOException;

import com.nexus.simplify.UI.controller.BillboardOverviewController;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.api.Logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class for Simplify. 
 * */
public class MainApp extends Application {
	
	private static final String FILE_LOCATION_BILLBOARD_OVERVIEW_FXML = "UI/view/BillboardOverview.fxml";
	private static final String FILE_LOCATION_ROOT_LAYOUT_FXML = "UI/view/RootLayout.fxml";
	private static final String APP_TITLE = "Simplify";
	
	//-----------------//
	// Class Variables //
	//-----------------//
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private Logic logic;
	private static Database database;

	//-------------//
	// Constructor //
	//-------------//
	
	public MainApp() throws IOException {
		logic = Logic.getInstance();
		database = new Database();
	}
	
	//--------------------------//
	// Interface Initialization //
	//--------------------------//
	
	/**
	 * Initializes the root layout of the application (the canvas of the app)
	 * 
	 * */
	private void initRootLayout() {
		try {
			// load root layout from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(FILE_LOCATION_ROOT_LAYOUT_FXML));
			rootLayout = (BorderPane) loader.load();

			// Sets the scene containing the root layout.
			Scene sceneRootLayout = new Scene(rootLayout);
			primaryStage.setScene(sceneRootLayout);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * displays the scene of the billboard ('pasting' the display onto canvas)
	 * 
	 * */
	public void showBillboardOverview() {
		try {
			// loads main interface from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(FILE_LOCATION_BILLBOARD_OVERVIEW_FXML));
			AnchorPane billboardOverview = (AnchorPane) loader.load();

			// sets main interface onto the center of root layout.
			rootLayout.setCenter(billboardOverview);
			
			// gives the controller access to the main app.
			BillboardOverviewController bbController = loader.getController();
			bbController.setMainApp(this);
			bbController.setDatabase(database);
		    bbController.initBillboard();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
 	//------------------//
	// Driver Functions //
	//------------------//
	
	/**
	 * This main function serves as a fallback,
	 * should the start() method fail.
	 * */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Main driver method to start the app.
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {		
		this.primaryStage = primaryStage;
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.setTitle(APP_TITLE);
		
		initRootLayout();
		showBillboardOverview();
	}
	
	//------------------//
	// Attribute Access //
	//------------------//
	
	/**
	 * default getter for class attribute primaryStage.
	 * @return primary stage of application.
	 * */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * @return reference to the instance of logic instantiated in the main app.
	 * */
	public Logic getLogic() {
		return logic;
	}
	
	/**
	 * @return reference to the instance of database instantiated in the main app.
	 * */
	public static Database getDatabase() {
		return database;
	}
}
