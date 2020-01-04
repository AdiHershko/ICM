package Client;

import java.io.IOException;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Enums.MessageEnum;
import Client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class clientChooseController {

	ChatClient client;
	@FXML
	private Pane pane;

	@FXML
	private Pane loginPane;

	@FXML
	private RadioButton localRB;

	@FXML
	private ToggleGroup yahav;

	@FXML
	private RadioButton remoteRB;

	@FXML
	private TextField IPTextBox;

	@FXML
	private Button ClientConnect;

	static clientChooseController _ins;
	public void initialize() {

		
		ClientConnect.setDefaultButton(true);
	}

	@FXML
	void connectToServer(ActionEvent event) {
		boolean isconnected = false;
		try {
			if (localRB.isSelected()) {
				client = new ChatClient("localhost", ChatClient.DEFAULT_PORT);
			}
			if (remoteRB.isSelected()) {
				String[] temp = IPTextBox.getText().split(":");
				client = new ChatClient(temp[0], Integer.parseInt(temp[1]));
			}
			isconnected = true;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Can't connect to server!");
			alert.setContentText("Can't connect to server!");
			alert.show();
		}
		if (isconnected) {
			Main.client = client;
			Main.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.CONNECT));
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scene requests = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(requests);
			window.setResizable(false);
			window.getIcons().add(new Image(getClass().getResourceAsStream("logo.jpg")));
			window.show();
		}

	}

}
