package Client;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RequestSettingsController {
	static RequestSettingsController _ins;
	Request currentRequest;
	@FXML
	private TextField assesmentAppointerText;
	@FXML
	private TextField examaningDueDateText;
	@FXML
	private TextField executionAppointerText;
	@FXML
	private TextField testDueDateText;
	@FXML
	private TextField assesmentDueDateText;
	@FXML
	private TextField assesmentExtensionDateText;
	@FXML
	private TextField examaningExtensionText;
	@FXML
	private TextField executionDueDateText;
	@FXML
	private TextField executionExtenstionDateText;
	@FXML
	private TextField testerExtensionDateText;
	@FXML
	private Button editAssesmentButton;
	@FXML
	private Label requestIDLabel;
	@FXML
	private Button doneButton;
	@FXML
	private Button setAssesmentDateButton;
	@FXML
	private Button approveAssesmentButton;
	@FXML
	private Button declineAssesmentButton;
	@FXML
	private Button saveExamDateButton;
	@FXML
	private Button setExecDateButton;
	@FXML
	private Button setTestDateButton;
	@FXML
	private TextField testerAppointedText;
	@FXML
	private Button editTesterButton;

	public void initialize() {
		_ins = this;
		currentRequest = Main.currentRequest;
		setScreen();
	}

	public void setScreen() {
		requestIDLabel.setText("" + currentRequest.getId());
		if (currentRequest.getStages()[1].getPlannedDueDate() != null)
			getAssesmentDueDateText()
					.setText(new DateTime(currentRequest.getStages()[1].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getAssesmentAppointerText()
				.setText(currentRequest.getStages()[1].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[1].getExtendedDueDate() != null)
			getAssesmentExtensionDateText()
					.setText(new DateTime(currentRequest.getStages()[1].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[2].getExtendedDueDate() != null)
			getExamaningExtensionText()
					.setText(new DateTime(currentRequest.getStages()[2].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[2].getPlannedDueDate() != null)
			getExamaningDueDateText()
					.setText(new DateTime(currentRequest.getStages()[2].getPlannedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[3].getPlannedDueDate() != null)
			getExecutionDueDateText()
					.setText(new DateTime(currentRequest.getStages()[3].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getExecutionAppointerText()
				.setText(currentRequest.getStages()[3].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[3].getExtendedDueDate() != null)
			getExecutionExtenstionDateText()
					.setText(new DateTime(currentRequest.getStages()[3].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[4].getPlannedDueDate() != null)
			getTestDueDateText()
					.setText(new DateTime(currentRequest.getStages()[4].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getTesterAppointedText()
				.setText(currentRequest.getStages()[4].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[4].getExtendedDueDate() != null)
			getTesterExtensionDateText()
					.setText(new DateTime(currentRequest.getStages()[4].getExtendedDueDate()).toString("dd/MM/yyyy"));
	}

	@FXML
	public void editAssesment() {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER,
				currentRequest.getId(), assesmentAppointerText.getText()));
	}

	@FXML
	public void editExecutioner() {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER,
				currentRequest.getId(), executionAppointerText.getText()));
	}

	@FXML
	public void editTester() {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITTESTER,
				currentRequest.getId(), testerAppointedText.getText()));
	}

	@FXML
	public void doneButtonAction() {
		Window window = doneButton.getScene().getWindow();
		if (window instanceof Stage)
			((Stage) window).close();

	}

	@FXML
	public void setAssesmentDate() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		try {
			dt = dtf.parseDateTime(assesmentDueDateText.getText());
		} catch (IllegalArgumentException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Invalid date. Enter a date with format dd/MM/yyyy");
				alert.show();
			});
			return;
		}
		if (dt.isBeforeNow()) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Please enter a date that is later than today.");
				alert.show();
			});
			return;
		}
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE, currentRequest.getId(), dt.toString()));
	}

	@FXML
	public void approveAssesment() {
		setDenied(0);
	}

	@FXML
	public void declineAssesment() {
		setDenied(1);
	}

	private void setDenied(int isDenied) {
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.APPROVEASSEXTENSION, currentRequest.getId(), isDenied));
	}

	@FXML
	public void saveExamDate() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		try {
			dt = dtf.parseDateTime(examaningDueDateText.getText());
		} catch (IllegalArgumentException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Invalid date. Enter a date with format dd/MM/yyyy");
				alert.show();
			});
			return;
		}
		if (dt.isBeforeNow()) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Please enter a date that is later than today.");
				alert.show();
			});
			return;
		}
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETEXAMDATE, currentRequest.getId(), dt.toString()));
	}

	@FXML
	public void setExecDate() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		try {
			dt = dtf.parseDateTime(executionDueDateText.getText());
		} catch (IllegalArgumentException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Invalid date. Enter a date with format dd/MM/yyyy");
				alert.show();
			});
			return;
		}
		if (dt.isBeforeNow()) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Please enter a date that is later than today.");
				alert.show();
			});
			return;
		}
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETEXECMDATE, currentRequest.getId(), dt.toString()));
	}

	@FXML
	public void setTestDate() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		try {
			dt = dtf.parseDateTime(testDueDateText.getText());
		} catch (IllegalArgumentException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Invalid date. Enter a date with format dd/MM/yyyy");
				alert.show();
			});
			return;
		}
		if (dt.isBeforeNow()) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Please enter a date that is later than today.");
				alert.show();
			});
			return;
		}
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETTESTDATE, currentRequest.getId(), dt.toString()));

	}

	public Request getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(Request currentRequest) {
		this.currentRequest = currentRequest;
	}

	public TextField getAssesmentAppointerText() {
		return assesmentAppointerText;
	}

	public void setAssesmentAppointerText(TextField assesmentAppointerText) {
		this.assesmentAppointerText = assesmentAppointerText;
	}

	public TextField getExamaningDueDateText() {
		return examaningDueDateText;
	}

	public void setExamaningDueDateText(TextField examaningDueDateText) {
		this.examaningDueDateText = examaningDueDateText;
	}

	public TextField getExecutionAppointerText() {
		return executionAppointerText;
	}

	public void setExecutionAppointerText(TextField executionAppointerText) {
		this.executionAppointerText = executionAppointerText;
	}

	public TextField getTestDueDateText() {
		return testDueDateText;
	}

	public void setTestDueDateText(TextField testDueDateText) {
		this.testDueDateText = testDueDateText;
	}

	public TextField getAssesmentDueDateText() {
		return assesmentDueDateText;
	}

	public void setAssesmentDueDateText(TextField assesmentDueDateText) {
		this.assesmentDueDateText = assesmentDueDateText;
	}

	public TextField getAssesmentExtensionDateText() {
		return assesmentExtensionDateText;
	}

	public void setAssesmentExtensionDateText(TextField assesmentExtensionDateText) {
		this.assesmentExtensionDateText = assesmentExtensionDateText;
	}

	public TextField getExamaningExtensionText() {
		return examaningExtensionText;
	}

	public void setexamaningExtensionText(TextField examaningExtensionText) {
		this.examaningExtensionText = examaningExtensionText;
	}

	public TextField getExecutionDueDateText() {
		return executionDueDateText;
	}

	public TextField getTesterAppointedText() {
		return testerAppointedText;
	}

	public void setTesterAppointedText(TextField testerAppointedText) {
		this.testerAppointedText = testerAppointedText;
	}

	public void setExecutionDueDateText(TextField executionDueDateText) {
		this.executionDueDateText = executionDueDateText;
	}

	public TextField getExecutionExtenstionDateText() {
		return executionExtenstionDateText;
	}

	public void setExecutionExtenstionDateText(TextField executionExtenstionDateText) {
		this.executionExtenstionDateText = executionExtenstionDateText;
	}

	public TextField getTesterExtensionDateText() {
		return testerExtensionDateText;
	}

	public void setTesterExtensionDateText(TextField testerExtensionDateText) {
		this.testerExtensionDateText = testerExtensionDateText;
	}

}
