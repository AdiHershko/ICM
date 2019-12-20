package Client;

import java.io.IOException;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Enums.*;
import Common.User;
import Server.EchoServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginScreenController {
	static LoginScreenController _ins;
	public static boolean success = true;
	private ActionEvent event;

	public void initialize() {
		ChatClient client;
		_ins = this;
		try {
			client = new ChatClient("localhost", ChatClient.DEFAULT_PORT);
		} catch (IOException e) {
			System.out.println("Cannot connect to the server");
			return;
		}
		Main.client = client;
		Main.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.CONNECT));
	}

	@FXML
	private Button loginButton;
	@FXML
	private PasswordField passTXT;
	@FXML
	private TextField userTXT;

	@FXML
	public void MoveScreen(ActionEvent event) throws IOException {
		this.event = event;
		String temp = userTXT.getText() + " " + passTXT.getText();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.SearchUser, temp));

	}

	public void LoginFailError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setContentText("BAD USERNAME/PASSOWRD");
		alert.showAndWait();

	}

	public void LoginGood(User user) {
		Main.currentUser = user;
		Parent root = null;
		// TODO so are we using only user roles? no College/ISUser instance?
		if (user.getRole() == Enums.Role.Manager) {
			try {
				root = FXMLLoader.load(getClass().getResource("3.0-ManagerScreen.fxml"));
			} catch (IOException e) {
			}
		} else {
			try {
				root = FXMLLoader.load(getClass().getResource("RequestsScreen.fxml"));
			} catch (IOException e) {
			}
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();
	}

}
