package Client;

import Common.Enums;
import Common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ISUsersScreenConstroller {

	static ISUsersScreenConstroller _ins;
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

	public void initialize() {
		_ins = this;
		FindBtn.setVisible(false);
		roleChoiceBox.getItems().add(Enums.Role.GeneralIS);
		roleChoiceBox.getItems().add(Enums.Role.CommitteChairman);
		roleChoiceBox.getItems().add(Enums.Role.CommitteMember);
		roleChoiceBox.getItems().add(Enums.Role.Supervisor);
	}

	public void enableFindBtn() {
		FindBtn.setVisible(true);
	}

	@FXML
	void findUserFunction(ActionEvent event) {
		isEdit = true;
		userIDField.setEditable(false);
		//TODO search in DB, load data
	}

	@FXML
	void saveUser(ActionEvent event) {
		/* TODO
		check all values are filled
		check committed size
		check supervisor
		check not manager*/
		User current = new User(userIDField.getText(), passwordField.getText(), firstNameField.getText(),
				lastNameField.getText(), mailField.getText(), Enums.Role.GeneralIS);
		if (isEdit) {
			// TODO send update message
		} else {
			// TODO send creation message
		}
	}

}
