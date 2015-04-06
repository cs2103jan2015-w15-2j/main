package com.nexus.simplify;
import java.io.IOException;

import com.nexus.simplify.UI.controller.BillboardOverviewController;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.Logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Main class for Simplify.
 * @author tohjianfeng
 * 
 * */
public class MainApp extends Application {
	
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
			loader.setLocation(MainApp.class.getResource("UI/view/RootLayout.fxml"));
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
			loader.setLocation(MainApp.class.getResource("UI/view/BillboardOverview.fxml"));
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
	 * default main function.
	 * */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Main driver method to start the app.
	 * 
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {		
		this.primaryStage = primaryStage;
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.setTitle("Simplify");
		
		initRootLayout();
		showBillboardOverview();
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
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
