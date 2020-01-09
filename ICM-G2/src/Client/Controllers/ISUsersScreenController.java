package Client.Controllers;

import java.util.ArrayList;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class ISUsersScreenController.
 */
public class ISUsersScreenController {

	/** The ins. */
	public static ISUsersScreenController _ins;
	
	/** The is edit. */
	static boolean isEdit = false;

	/** The mail field. */
	@FXML
	private TextField mailField;

	/** The last name field. */
	@FXML
	private TextField lastNameField;

	/** The role choice box. */
	@FXML
	private ChoiceBox<Enums.Role> roleChoiceBox;

	/** The user ID field. */
	@FXML
	private TextField userIDField;

	/** The Find btn. */
	@FXML
	private Button FindBtn;

	/** The save btn. */
	@FXML
	private Button saveBtn;

	/** The first name field. */
	@FXML
	private TextField firstNameField;

	/** The password field. */
	@FXML
	private PasswordField passwordField;
	
	/** The semaphore. */
	@FXML
	private boolean semaphore;

	/** The canedit. */
	@FXML
	private boolean canedit;

	/** The info station box. */
	@FXML
	private CheckBox infoStationBox;

	/** The moodle box. */
	@FXML
	private CheckBox moodleBox;

	/** The library box. */
	@FXML
	private CheckBox libraryBox;

	/** The computers box. */
	@FXML
	private CheckBox computersBox;

	/** The lab box. */
	@FXML
	private CheckBox labBox;

	/** The site box. */
	@FXML
	private CheckBox siteBox;

	/** The remove user button. */
	@FXML
	private Button removeUserButton;

	/**
	 * Initialize.
	 */
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

	/**
	 * Enable find btn.
	 */
	public void enableFindBtn() {
		FindBtn.setVisible(true);
	}

	/**
	 * Find user function.
	 *
	 * @param event the event
	 */
	@FXML
	void findUserFunction(ActionEvent event) {
		removeUserButton.setVisible(true);
		isEdit = true;
		ClientMain.client
				.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETISUSER, userIDField.getText()));
	}

	/**
	 * Save user.
	 *
	 * @param event the event
	 */
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
		if (userIDField.getText().contains(",") || userIDField.getText().contains("'")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("UserID can contain only letters");
			alert.showAndWait();
			return;
		}
		User current = new User(userIDField.getText(), passwordField.getText(), firstNameField.getText(),
				lastNameField.getText(), mailField.getText(), roleChoiceBox.getSelectionModel().getSelectedItem());
		if (roleChoiceBox.getSelectionModel().getSelectedItem() == Enums.Role.Supervisor) {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CHECKSUPERVISOREXIST));
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
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.COUNTCOMMITEEMEMBERS));
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
			removeUserButton.setVisible(false);
		}

		if (roleChoiceBox.getSelectionModel().getSelectedItem() == Enums.Role.CommitteChairman) {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CHECKCHAIRMANEXIST));
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
		boolean[] permissions = new boolean[6];
		ArrayList<CheckBox> boxes = new ArrayList<>();
		boxes.add(infoStationBox);
		boxes.add(computersBox);
		boxes.add(moodleBox);
		boxes.add(siteBox);
		boxes.add(labBox);
		boxes.add(libraryBox);
		for (int i = 0; i < 6; i++)
			if (boxes.get(i).isSelected())
				permissions[i] = true;
		if (isEdit) {
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.UPDATEISUSER, current, permissions));
		} else {
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.ADDISUSER, current, permissions));
		}
	}

	/**
	 * All fields filled.
	 *
	 * @return true, if successful
	 */
	private boolean allFieldsFilled() {
		return !mailField.getText().equals("") && !lastNameField.getText().equals("")
				&& roleChoiceBox.getSelectionModel().getSelectedItem() != null && !userIDField.getText().equals("")
				&& !firstNameField.getText().equals("") && !passwordField.getText().equals("");
	}

	/**
	 * Removes the user.
	 */
	@FXML
	public void removeUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to remove user " + userIDField.getText() + " from the system?", ButtonType.YES,
				ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.REMOVEUSER, userIDField.getText()));
			ISUsersScreenController._ins.getRemoveUserButton().setVisible(false);
		}

	}

	/**
	 * Gets the mail field.
	 *
	 * @return the mail field
	 */
	public TextField getMailField() {
		return mailField;
	}

	/**
	 * Gets the last name field.
	 *
	 * @return the last name field
	 */
	public TextField getLastNameField() {
		return lastNameField;
	}

	/**
	 * Gets the role choice box.
	 *
	 * @return the role choice box
	 */
	public ChoiceBox<Enums.Role> getRoleChoiceBox() {
		return roleChoiceBox;
	}

	/**
	 * Gets the site box.
	 *
	 * @return the site box
	 */
	public CheckBox getSiteBox() {
		return siteBox;
	}

	/**
	 * Sets the site box.
	 *
	 * @param siteBox the new site box
	 */
	public void setSiteBox(CheckBox siteBox) {
		this.siteBox = siteBox;
	}

	/**
	 * Gets the user ID field.
	 *
	 * @return the user ID field
	 */
	public TextField getUserIDField() {
		return userIDField;
	}

	/**
	 * Gets the first name field.
	 *
	 * @return the first name field
	 */
	public TextField getFirstNameField() {
		return firstNameField;
	}

	/**
	 * Gets the password field.
	 *
	 * @return the password field
	 */
	public PasswordField getPasswordField() {
		return passwordField;
	}

	/**
	 * Sets the semaphore.
	 *
	 * @param con the new semaphore
	 */
	public void setSemaphore(boolean con) {
		semaphore = con;
	}

	/**
	 * Sets the can edit.
	 *
	 * @param con the new can edit
	 */
	public void setCanEdit(boolean con) {
		canedit = con;
	}

	/**
	 * Gets the info station box.
	 *
	 * @return the info station box
	 */
	public CheckBox getInfoStationBox() {
		return infoStationBox;
	}

	/**
	 * Sets the info station box.
	 *
	 * @param infoStationBox the new info station box
	 */
	public void setInfoStationBox(CheckBox infoStationBox) {
		this.infoStationBox = infoStationBox;
	}

	/**
	 * Gets the moodle box.
	 *
	 * @return the moodle box
	 */
	public CheckBox getMoodleBox() {
		return moodleBox;
	}

	/**
	 * Sets the moodle box.
	 *
	 * @param moodleBox the new moodle box
	 */
	public void setMoodleBox(CheckBox moodleBox) {
		this.moodleBox = moodleBox;
	}

	/**
	 * Gets the library box.
	 *
	 * @return the library box
	 */
	public CheckBox getLibraryBox() {
		return libraryBox;
	}

	/**
	 * Sets the library box.
	 *
	 * @param libraryBox the new library box
	 */
	public void setLibraryBox(CheckBox libraryBox) {
		this.libraryBox = libraryBox;
	}

	/**
	 * Gets the computers box.
	 *
	 * @return the computers box
	 */
	public CheckBox getComputersBox() {
		return computersBox;
	}

	/**
	 * Sets the computers box.
	 *
	 * @param computersBox the new computers box
	 */
	public void setComputersBox(CheckBox computersBox) {
		this.computersBox = computersBox;
	}

	/**
	 * Gets the lab box.
	 *
	 * @return the lab box
	 */
	public CheckBox getLabBox() {
		return labBox;
	}

	/**
	 * Sets the lab box.
	 *
	 * @param labBox the new lab box
	 */
	public void setLabBox(CheckBox labBox) {
		this.labBox = labBox;
	}

	/**
	 * Sets the role choice box.
	 *
	 * @param roleChoiceBox the new role choice box
	 */
	public void setRoleChoiceBox(ChoiceBox<Enums.Role> roleChoiceBox) {
		this.roleChoiceBox = roleChoiceBox;
	}

	/**
	 * Gets the removes the user button.
	 *
	 * @return the removes the user button
	 */
	public Button getRemoveUserButton() {
		return removeUserButton;
	}

	/**
	 * Sets the removes the user button.
	 *
	 * @param removeUserButton the new removes the user button
	 */
	public void setRemoveUserButton(Button removeUserButton) {
		this.removeUserButton = removeUserButton;
	}

}
