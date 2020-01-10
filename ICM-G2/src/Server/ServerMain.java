package Server;

import java.io.IOException;

import Server.Controllers.ServerChooseController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The Class ServerConsole.
 */
public class ServerMain extends Application {
	
	/** The ins. */
	public static ServerMain _init;
	
	/** The root. */
	public static Pane root;
	
	/** The stage. */
	static Stage stage;
	
	/** The c. */
	ServerChooseController c;


	/**
	 * Start, getting the fxml.
	 *
	 * @param stage the GUI stage
	 * @throws Exception
	 */
	@Override
	public void start(Stage stage) throws Exception {
		_init = this;
		ServerMain.stage = stage;
		try { // loading fxml file
			FXMLLoader load = new FXMLLoader();
			load.setLocation(getClass().getResource("Controllers/servergui.fxml"));
			root = load.load();
			c = load.getController(); // saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		c.setChoiceBox();
		c.getServerPane().setVisible(false);

		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("ICM Prototype - Server");
		stage.setResizable(false);
		stage.show();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Controllers/logo.jpg")));
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				System.exit(0);
			}
		});
	}

	/**
	 * The main method.
	 *
	 * @param args the main arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
