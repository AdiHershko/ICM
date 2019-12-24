package Client;

import java.io.IOException;

import Common.Enums;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManagerScreenController {
	static ManagerScreenController _ins;
	boolean isSup = false;
	boolean isMan = false;
	ActionEvent event;
	@FXML
	private Button createUserButton;

	@FXML
	private Button editUsersButton;

	@FXML
	private Button ViewAllDataUsersButton;

	@FXML
	private Button ViewAllDataRequestsButton;

	@FXML
	private Button statButton;

	@FXML
	private Button requestsButton;

	@FXML
	private Label timeDateTxt;

	@FXML
	private Label userTXT;

	@FXML
	private Button logoutButton;

	public void initialize() {
		_ins = this;
		if (Main.currentUser.getRole() == Enums.Role.Manager) {
			isMan = true;
			System.out.println("sdfdsfs im here\n");
		}
		if (Main.currentUser.getRole() == Enums.Role.Supervisor) {
			isSup = true;
		}
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Main.currentUser = null;
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();
	}

	public void requestBtn(ActionEvent event) throws IOException {
		this.event = event;
		if (isMan) {
			getReqScreen();
			
		} else if (isSup) {
			getReqScreen();
		}
	}

	public void getReqScreen() {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("RequestsScreen.fxml"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();
	}
}
