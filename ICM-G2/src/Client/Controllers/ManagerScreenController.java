package Client.Controllers;

import java.io.IOException;

import org.joda.time.DateTime;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import javafx.application.Platform;
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

/**
 * The Class ManagerScreenController:
 * Controller for 3.0-ManagerScreen.fxml
 */
public class ManagerScreenController {
	
	/** The ins. */
	public static ManagerScreenController _ins;
	
	/** The IS users. */
	private Scene ISUsers;
	
	/** The new window. */
	Stage newWindow = new Stage();
	
	/** The event. */
	ActionEvent event;
	
	/** The create user button. */
	@FXML
	private Button createUserButton;

	/** The edit users button. */
	@FXML
	private Button editUsersButton;

	/** The View all system data button. */
	@FXML
	private Button ViewAllSystemDataButton;

	/** The statistics button. */
	@FXML
	private Button statButton;

	/** The requests button. */
	@FXML
	private Button requestsButton;

	/** The time date text label. */
	@FXML
	private Label timeDateTxt;

	/** The user TXT. */
	@FXML
	private Label userTXT;

	/** The logout button. */
	@FXML
	private Button logoutButton;
	
	/** The date label. */
	@FXML
	private Label dateLabel;
	
	/** The user name label. */
	@FXML
	private Label userNameLabel;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		new Thread() {
			public void run() {
				Platform.runLater(() -> userNameLabel
						.setText(ClientMain.currentUser.getFirstName() + " " + ClientMain.currentUser.getLastName()));
				while (true) // update time in 0.5s intervals
				{

					Platform.runLater(new Runnable()
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

	/**
	 * Open create user screen.
	 *
	 * @param event the event
	 */
	@FXML
	void openCreateUser(ActionEvent event) {
		Parent root = null;
		newWindow = new Stage();
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

	/**
	 * Open edit user screen.
	 *
	 * @param event the event
	 */
	@FXML
	void openEditUser(ActionEvent event) {
		Parent root = null;
		newWindow = new Stage();
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

	/**
	 * Logout button function.
	 *
	 * @param event the event
	 */
	@FXML
	public void logout(ActionEvent event) {
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.logOut, ClientMain.currentUser.getUsername()));
		ClientMain.currentUser = null;
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
		window.show();
	}

	/**
	 * Request button function.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void requestBtn(ActionEvent event) throws IOException {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("1-RequestsScreen.fxml"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.setResizable(false);
		window.show();
	}

	/**
	 * Open statistics window.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void openStats(ActionEvent event) throws IOException {
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

	/**
	 * Opens view all system data window.
	 *
	 * @param event the event
	 */
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
