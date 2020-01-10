package Client.Controllers;

import java.io.IOException;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Enums.*;
import Common.User;
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

/**
 * The Class LoginScreenController.
 * Controller for 0.1-loginScreen.fxml
 */
public class LoginScreenController {
	
	/** The ins. */
	public static LoginScreenController _ins;

	/** The mouse click event. */
	private ActionEvent event;
	
	/** The login button. */
	@FXML
	private Button loginButton;
	
	/** The password TXT. */
	@FXML
	private PasswordField passTXT;
	
	/** The user name TXT. */
	@FXML
	private TextField userTXT;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {

		_ins = this;
		loginButton.setDefaultButton(true);
	}

	/**
	 * Move to the next screen.
	 *
	 * @param event the clicking event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void MoveScreen(ActionEvent event) throws IOException {
		if (userTXT.getText().equals("") || passTXT.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("EMPTY USERNAME/PASSOWRD");
			alert.showAndWait();
		} else {
			this.event = event;
			String temp[] = { userTXT.getText(), passTXT.getText() };
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.SearchUser, temp));
		}
	}

	/**
	 * Login fail error.
	 */
	public void LoginFailError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setContentText("BAD USERNAME/PASSOWRD");
		alert.showAndWait();
	}

	/**
	 * Login same time.
	 */
	public void LoginSameTime() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setContentText("User already logged in");
		alert.showAndWait();

	}

	/**
	 * Login good.
	 *
	 * @param user the logged in user
	 */
	public void LoginGood(User user) {
		ClientMain.currentUser = user;
		Parent root = null;
		if (user.getRole() == Enums.Role.Manager) {
			try {
				root = FXMLLoader.load(getClass().getResource("3.0-ManagerScreen.fxml"));
			} catch (IOException e) {
			}
		} else {
			try {
				root = FXMLLoader.load(getClass().getResource("1-RequestsScreen.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.setResizable(false);
		window.show();
	}

}
