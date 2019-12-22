package Client;

import java.io.IOException;

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

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Main.currentUser=null;
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();
	}
}
