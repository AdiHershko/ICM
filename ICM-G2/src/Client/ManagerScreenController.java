package Client;

import java.io.IOException;

import org.joda.time.DateTime;

import Common.ClientServerMessage;
import Common.Enums;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerScreenController {
	static ManagerScreenController _ins;
	private Scene ISUsers;
	Stage newWindow = new Stage();
	ActionEvent event;
	@FXML
	private Button createUserButton;

	@FXML
	private Button editUsersButton;

	@FXML
	private Button ViewAllSystemDataButton;

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
	private Label dateLabel;
	@FXML
	private Label userNameLabel;



	public void initialize() {
		_ins = this;
		new Thread() {
			public void run() {
				Platform.runLater(()->userNameLabel.setText(Main.currentUser.getFirstName() + " " + Main.currentUser.getLastName()));
				while (true) // update time in 0.5s intervals
				{

					Platform.runLater(new Runnable() // wont work without this shit
					{
						public void run() {
							DateTime dt = new DateTime();
							dateLabel.setText(dt.toString("dd/MM/yyyy hh:mm:ss a" + " |"));
							dt = null; // for garbage collection
						}
					});
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}

			}
		}.start();
	}

	@FXML
    void openCreateUser(ActionEvent event) {
		Parent root = null;
		newWindow=new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource("3.1-AddISUsersScreen.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		ISUsers = new Scene(root);
		newWindow.setTitle("Create IS User");
		newWindow.setScene(ISUsers);
		newWindow.setResizable(false);
		newWindow.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
		newWindow.initModality(Modality.WINDOW_MODAL);
		newWindow.show();
    }

    @FXML
    void openEditUser(ActionEvent event) {
		Parent root = null;
		newWindow=new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource("3.1-AddISUsersScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ISUsers = new Scene(root);
		ISUsersScreenController._ins.enableFindBtn();
		newWindow.setTitle("Edit IS User");
		newWindow.setScene(ISUsers);
		newWindow.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
		newWindow.initModality(Modality.WINDOW_MODAL);
		newWindow.setResizable(false);
		newWindow.show();
    }

	@FXML
	public void logout(ActionEvent event) {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.logOut, Main.currentUser.getUsername()));
		Main.currentUser = null;
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
		window.show();
	}

	public void requestBtn(ActionEvent event) throws IOException {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("RequestsScreen.fxml"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.setResizable(false);
		window.show();
	}
	@FXML
	public void openStats(ActionEvent event) throws IOException{
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("3.2-ManagerStatistics.fxml"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		Scene stats = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(stats);
		window.setResizable(false);
		window.show();
	}

	@FXML
	public void viewAllSystemData(ActionEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("3.3-AllSystemDataScreen.fxml"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		Scene allSystemData = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(allSystemData);
		window.setResizable(false);
		window.show();

	}

}
