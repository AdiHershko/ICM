package Client;

import java.io.IOException;

import Client.Controllers.ClientChooseController;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import Common.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class ClientMain.
 * The client main feature, opening first stage.
 */
public class ClientMain extends Application {
	
	/** The stage. */
	Stage stage;
	
	/** The root. */
	static Pane root;
	
	/** The c. */
	ClientChooseController c;
	
	/** The client. */
	public static ChatClient client;
	
	/** The current user. */
	public static User currentUser;
	
	/** The current request. */
	public static Request currentRequest;
	
	/** The main ins. */
	public static ClientMain _ins;
	
	/** The scene. */
	public static Scene s;

	/**
	 * Start.
	 *
	 * @param stage the stage to open
	 * @throws Exception
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		try { // loading fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Controllers/0-ClientEntrance.fxml"));
			root = loader.load();
			c = loader.getController(); // saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		_ins = this;
		s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("Client connection");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Controllers/logo.jpg")));
		stage.show();
		getHostServices();

		stage.setOnCloseRequest(e -> {
			try {
				if (currentUser != null) {
					client.handleMessageFromClientUI(
							new ClientServerMessage(Enums.MessageEnum.logOut, currentUser.getUsername()));
					client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.DISCONNECT));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);
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
