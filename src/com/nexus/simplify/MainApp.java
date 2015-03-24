/**
 * @author Toh Jian Feng 
 *
 */

import java.io.IOException;

import com.nexus.simplify.UI.view.BillboardOverviewController;
import com.nexus.simplify.database.Database;
import com.nexus.simplify.logic.Logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private static final String NAME_INPUT_FILE = "input.json";

	//-----------------//
	// Class Variables //
	//-----------------//
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private Logic logic;
	private Database database;

	//-------------//
	// Constructor //
	//-------------//
	
	public MainApp() throws IOException {
		logic = Logic.getInstance();
		database = new Database(NAME_INPUT_FILE);
	}
	
	//--------------------------//
	// Interface Initialization //
	//--------------------------//
	
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
	
	public void showBillboardOverview() {
		try {
			// loads main interface from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("UI/view/InterfaceOverview.fxml"));
			AnchorPane billboardOverview = (AnchorPane) loader.load();
			
			// sets main interface onto the center of root layout.
			rootLayout.setCenter(billboardOverview);
			
			// gives the controller access to the main app.
			BillboardOverviewController bbController = loader.getController();
			bbController.setMainApp(this);
			bbController.initBillboard(database);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
 	//------------------//
	// Driver Functions //
	//------------------//
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Simplify");
		
		initRootLayout();
		
		showBillboardOverview();
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public Logic getLogic() {
		return logic;
	}
	
	public Database getDatabase() {
		return database;
	}
}
