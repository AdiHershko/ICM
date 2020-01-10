package Client.Controllers;

import java.io.IOException;

import Common.ClientServerMessage;
import Common.Enums.MessageEnum;
import Client.ChatClient;
import Client.ClientMain;
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

/**
 * The Class ClientChooseController:
 * Controller for 0-ClientEntrance.fxml.
 */
public class ClientChooseController {

	/** The client. */
	ChatClient client;
	
	/** The pane. */
	@FXML
	private Pane pane;

	/** The login pane. */
	@FXML
	private Pane loginPane;

	/** The local RB. */
	@FXML
	private RadioButton localRB;

	/** The toggle group. */
	@FXML
	private ToggleGroup tg;

	/** The remote RB. */
	@FXML
	private RadioButton remoteRB;

	/** The IP text box. */
	@FXML
	private TextField IPTextBox;

	/** The Client connect. */
	@FXML
	private Button ClientConnect;

	/** The ins. */
	public static ClientChooseController _ins;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {

		ClientConnect.setDefaultButton(true);
	}

	/**
	 * Connect to server.
	 *
	 * @param event the mouse click
	 */
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
			ClientMain.client = client;
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.CONNECT));
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("0.1-loginScreen.fxml"));
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
