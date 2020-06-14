package hr.java.vjezbe;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static Stage stage;
	private static BorderPane glavni;
	
	private void setMainPage(BorderPane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void setCenterPane(BorderPane root) {
		glavni.setCenter(root);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			glavni = (BorderPane)FXMLLoader.load(getClass().getResource("Pocetna.fxml"));
			setMainPage(glavni);
		} 
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
}
