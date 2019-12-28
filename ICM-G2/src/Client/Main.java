package Client;

import java.io.IOException;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import Common.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stage;
	Pane root;
	LoginScreenController c;
	public static ChatClient client;
	static User currentUser;
	static Request currentRequest;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		try { // loading fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("loginScreen.fxml"));
			root = loader.load();
			c = loader.getController(); // saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("ICM Prototype - Client");
		stage.setResizable(false);
		stage.show();

		stage.setOnCloseRequest(e -> {
			try {
				if (currentUser != null) {
					client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.logOut, currentUser.getUsername()));
					client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.DISCONNECT));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

}
