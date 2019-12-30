package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ISUsersScreenController {

	static ISUsersScreenController _ins;
	static boolean isEdit = false;

	@FXML
	private TextField mailField;

	@FXML
	private TextField lastNameField;

	@FXML
	private ChoiceBox<Enums.Role> roleChoiceBox;

	@FXML
	private TextField userIDField;

	@FXML
	private Button FindBtn;

	@FXML
	private Button saveBtn;

	@FXML
	private TextField firstNameField;

	@FXML
	private PasswordField passwordField;
	@FXML
	private boolean semaphore;

	@FXML
	private boolean canedit;

	public void initialize() {
		_ins = this;
		FindBtn.setVisible(false);
		roleChoiceBox.getItems().add(Enums.Role.GeneralIS);
		roleChoiceBox.getItems().add(Enums.Role.CommitteChairman);
		roleChoiceBox.getItems().add(Enums.Role.CommitteMember);
		roleChoiceBox.getItems().add(Enums.Role.Supervisor);
		semaphore = false;
		canedit = false;
	}

	public void enableFindBtn() {
		FindBtn.setVisible(true);
	}

	@FXML
	void findUserFunction(ActionEvent event) {
		isEdit = true;
		Main.client
				.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETISUSER, userIDField.getText()));
	}

	@FXML
	void saveUser(ActionEvent event) {
		canedit = false;
		if (!allFieldsFilled()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Empty fields!");
			alert.showAndWait();
			return;
		}
		if (userIDField.getText().contains(",")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("UserID can't contain ,");
			alert.showAndWait();
			return;
		}
		User current = new User(userIDField.getText(), passwordField.getText(), firstNameField.getText(),
				lastNameField.getText(), mailField.getText(), roleChoiceBox.getSelectionModel().getSelectedItem());
		if (roleChoiceBox.getSelectionModel().getSelectedItem() == Enums.Role.Supervisor) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CHECKSUPERVISOREXIST));
			semaphore = true;
			while (semaphore) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			if (!canedit) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Supervisor already exist");
					alert.show();
				});
				return;
			}
		}

		if (roleChoiceBox.getSelectionModel().getSelectedItem() == Enums.Role.CommitteMember) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.COUNTCOMMITEEMEMBERS));
			semaphore = true;
			while (semaphore) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			if (!canedit) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Committe members number is at maximum");
					alert.show();
				});
				return;
			}
		}

		if (roleChoiceBox.getSelectionModel().getSelectedItem() == Enums.Role.CommitteChairman) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CHECKCHAIRMANEXIST));
			semaphore = true;
			while (semaphore) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			if (!canedit) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Committe Chairman already exists");
					alert.show();
				});
				return;
			}
		}

		if (isEdit) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPDATEISUSER, current));
		} else {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.ADDISUSER, current));
		}
	}

	private boolean allFieldsFilled() {
		return !mailField.getText().equals("") && !lastNameField.getText().equals("")
				&& roleChoiceBox.getSelectionModel().getSelectedItem() != null && !userIDField.getText().equals("")
				&& !firstNameField.getText().equals("") && !passwordField.getText().equals("");
	}

	public TextField getMailField() {
		return mailField;
	}

	public TextField getLastNameField() {
		return lastNameField;
	}

	public ChoiceBox<Enums.Role> getRoleChoiceBox() {
		return roleChoiceBox;
	}

	public TextField getUserIDField() {
		return userIDField;
	}

	public TextField getFirstNameField() {
		return firstNameField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public void setSemaphore(boolean con) {
		semaphore = con;
	}

	public void setCanEdit(boolean con) {
		canedit = con;
	}

}
