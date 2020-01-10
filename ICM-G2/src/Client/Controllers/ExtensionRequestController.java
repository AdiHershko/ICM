package Client.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

/**
 * The Class ExtensionRequestController.
 * Controller for 1.1-ExtensionRequest.fxml
 */
public class ExtensionRequestController {

	/** The description text. */
	@FXML
	private TextArea descText;

	/** The submit button. */
	@FXML
	private Button submitButton;

	/** The date picker. */
	@FXML
	private DatePicker datePicker;

	/** The request ID label. */
	@FXML
	private Label reqIDLabel;

	/**
	 * Initialize the fxml
	 */
	public void initialize() {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		reqIDLabel.setText("Request ID:" + ClientMain.currentRequest.getId());

	}

	/**
	 * Submit extension request button function
	 */
	@FXML
	public void submitRequest() {
		String temp;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (descText.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("Must explain extension!");
			alert.showAndWait();
			return;
		}
		if (datePicker.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("Must choose date!");
			alert.showAndWait();
			return;
		}
		if (datePicker.getValue().isBefore(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("Can't ask for extension date before today!");
			alert.showAndWait();
			return;
		}
		try {
			temp = (datePicker.getValue()).format(df);
		} catch (IllegalArgumentException e) {
			return;
		}
		int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(ClientMain.currentRequest.getCurrentStage());
		ClientMain.currentRequest.getStages()[currstageNum].setExtensionAsk(descText.getText());
		ClientMain.currentRequest.getStages()[currstageNum].setExtendedDueDate(temp);
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.ASKFOREXTENSION, ClientMain.currentRequest, temp));
	}
}
