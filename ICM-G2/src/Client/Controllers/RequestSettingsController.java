package Client.Controllers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import Common.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestSettingsController.
 */
public class RequestSettingsController {
	
	/** The ins. */
	public static RequestSettingsController _ins;
	
	/** The can exit executor. */
	public boolean canExit_Executor = false;
	
	/** The can exit asses. */
	public boolean canExit_Asses = false;
	
	/** The current request. */
	Request currentRequest;
	
	/** The current user. */
	User currentUser;
	
	/** The assesment appointer text. */
	@FXML
	private TextField assesmentAppointerText;
	
	/** The examaning due date text. */
	@FXML
	private DatePicker examaningDueDateText;
	
	/** The execution appointer text. */
	@FXML
	private TextField executionAppointerText;
	
	/** The test due date text. */
	@FXML
	private DatePicker testDueDateText;
	
	/** The assesment due date text. */
	@FXML
	private DatePicker assesmentDueDateText;
	
	/** The assesment extension date text. */
	@FXML
	private TextField assesmentExtensionDateText;
	
	/** The examaning extension text. */
	@FXML
	private TextField examaningExtensionText;
	
	/** The execution due date text. */
	@FXML
	private DatePicker executionDueDateText;
	
	/** The execution extenstion date text. */
	@FXML
	private TextField executionExtenstionDateText;
	
	/** The tester extension date text. */
	@FXML
	private TextField testerExtensionDateText;
	
	/** The edit assesment button. */
	@FXML
	private Button editAssesmentButton;
	
	/** The request ID label. */
	@FXML
	private Label requestIDLabel;
	
	/** The done button. */
	@FXML
	private Button doneButton;
	
	/** The set assesment date button. */
	@FXML
	private Button setAssesmentDateButton;
	
	/** The approve assesment button. */
	@FXML
	private Button approveAssesmentButton;
	
	/** The decline assesment button. */
	@FXML
	private Button declineAssesmentButton;
	
	/** The save exam date button. */
	@FXML
	private Button saveExamDateButton;
	
	/** The set exec date button. */
	@FXML
	private Button setExecDateButton;
	
	/** The set test date button. */
	@FXML
	private Button setTestDateButton;
	
	/** The tester appointed text. */
	@FXML
	private TextField testerAppointedText;
	
	/** The edit tester button. */
	@FXML
	private Button editTesterButton;
	
	/** The edit assesment button 1. */
	@FXML
	private Button editAssesmentButton1;

	/**
	 * Initialize.
	 */
	public void initialize() {
		_ins = this;
		currentRequest = ClientMain.currentRequest;
		currentUser = ClientMain.currentUser;
		if (currentRequest.getCurrentStage() != Enums.RequestStageENUM.Initialization) {
			editAssesmentButton.setVisible(false);
		}
		setScreen();
	}

	/**
	 * Sets the screen.
	 */
	public void setScreen() {
		requestIDLabel.setText("" + currentRequest.getId());
		if (currentRequest.getStages()[1].getPlannedDueDate() != null)
			getAssesmentDueDateText().setPromptText(
					new DateTime(currentRequest.getStages()[1].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getAssesmentAppointerText()
				.setText(currentRequest.getStages()[1].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[1].getExtendedDueDate() != null)
			getAssesmentExtensionDateText()
					.setText(new DateTime(currentRequest.getStages()[1].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[2].getExtendedDueDate() != null)
			getExamaningExtensionText()
					.setText(new DateTime(currentRequest.getStages()[2].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[2].getPlannedDueDate() != null)
			getExamaningDueDateText().setPromptText(
					new DateTime(currentRequest.getStages()[2].getPlannedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[3].getPlannedDueDate() != null)
			getExecutionDueDateText().setPromptText(
					new DateTime(currentRequest.getStages()[3].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getExecutionAppointerText()
				.setText(currentRequest.getStages()[3].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[3].getExtendedDueDate() != null)
			getExecutionExtenstionDateText()
					.setText(new DateTime(currentRequest.getStages()[3].getExtendedDueDate()).toString("dd/MM/yyyy"));
		if (currentRequest.getStages()[4].getPlannedDueDate() != null)
			getTestDueDateText().setPromptText(
					new DateTime(currentRequest.getStages()[4].getPlannedDueDate()).toString("dd/MM/yyyy"));
		getTesterAppointedText()
				.setText(currentRequest.getStages()[4].getStageMembers().toString().replaceAll("[^A-Za-z0-9]+", ""));
		if (currentRequest.getStages()[4].getExtendedDueDate() != null)
			getTesterExtensionDateText()
					.setText(new DateTime(currentRequest.getStages()[4].getExtendedDueDate()).toString("dd/MM/yyyy"));
	}

	/**
	 * Edits the assesment.
	 */
	@FXML
	public void editAssesment() {
		if (currentRequest.getCurrentStage() == Enums.RequestStageENUM.Initialization)
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER,
					currentRequest, assesmentAppointerText.getText()));
		else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Request is already past the initialization stage!");
				alert.show();
			});

		}
		assesmentAppointerText.setEditable(false);
	}

	/**
	 * Edits the executioner.
	 */
	@FXML
	public void editExecutioner() {
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER, currentRequest,
				executionAppointerText.getText()));
	}

	/**
	 * Edits the tester.
	 */
	@FXML
	public void editTester() {
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.EDITTESTER, currentRequest, testerAppointedText.getText()));
	}

	/**
	 * Done button action.
	 */
	@FXML
	public void doneButtonAction() {
		if ((!canExit_Asses || !canExit_Executor)
				&& currentRequest.getCurrentStage() == Enums.RequestStageENUM.Initialization) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Please appoint/approve executioner and assementor");
			alert.show();
			return;
		}
		RequestsScreenController._ins.RefreshTable();
		if (currentRequest.getCurrentStage() == Enums.RequestStageENUM.Initialization) {
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.UpdateStage, currentRequest.getId()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Initialization stage finished");
			alert.showAndWait();
			RequestsScreenController._ins.RefreshTable();
			RequestsScreenController._ins.unVisibleRequestPane();
		}
		Window window = doneButton.getScene().getWindow();
		if (window instanceof Stage)
			((Stage) window).close();
	}

	/**
	 * Sets the assesment date.
	 */
	@FXML
	public void setAssesmentDate() {

		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			temp = (assesmentDueDateText.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
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
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE, currentRequest.getId(), dt.toString()));
	}

	/**
	 * Approve assesment.
	 */
	@FXML
	public void approveAssesment() {
		if (!assesmentExtensionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(0, 1);
	}

	/**
	 * Decline assesment.
	 */
	@FXML
	public void declineAssesment() {
		if (!assesmentExtensionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(1, 1);
	}

	/**
	 * Approve examaning.
	 */
	@FXML
	public void approveExamaning() {
		if (!examaningExtensionText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(0, 2);
	}

	/**
	 * Decline examaning.
	 */
	@FXML
	public void declineExamaning() {
		if (!examaningExtensionText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(1, 2);
	}

	/**
	 * Approve execution.
	 */
	@FXML
	public void approveExecution() {
		if (!executionExtenstionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(0, 3);
	}

	/**
	 * Decline execution.
	 */
	@FXML
	public void declineExecution() {
		if (!executionExtenstionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(1, 3);
	}

	/**
	 * Approve test.
	 */
	@FXML
	public void approveTest() {
		if (!testerExtensionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(0, 4);
	}

	/**
	 * Decline test.
	 */
	@FXML
	public void declineTest() {
		if (!testerExtensionDateText.getText().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
			return;
		setDenied(1, 4);
	}

	/**
	 * Edits the assesment 1.
	 */
	@FXML
	public void editAssesment1() {
		assesmentAppointerText.setEditable(true);
	}

	/**
	 * Sets the denied.
	 *
	 * @param isDenied the is denied
	 * @param stage the stage
	 */
	private void setDenied(int isDenied, int stage) {
		currentRequest.setIsDenied(isDenied);
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.APPROVEASSEXTENSION, currentRequest, stage));
	}

	/**
	 * Save exam date.
	 */
	@FXML
	public void saveExamDate() {
		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			temp = (examaningDueDateText.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
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
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETEXAMDATE, currentRequest.getId(), dt.toString()));
	}

	/**
	 * Sets the exec date.
	 */
	@FXML
	public void setExecDate() {
		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			temp = (executionDueDateText.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
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
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETEXECMDATE, currentRequest.getId(), dt.toString()));
	}

	/**
	 * Sets the test date.
	 */
	@FXML
	public void setTestDate() {
		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			temp = (testDueDateText.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
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
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.SETTESTDATE, currentRequest.getId(), dt.toString()));

	}

	/**
	 * Gets the current request.
	 *
	 * @return the current request
	 */
	public Request getCurrentRequest() {
		return currentRequest;
	}

	/**
	 * Sets the current request.
	 *
	 * @param currentRequest the new current request
	 */
	public void setCurrentRequest(Request currentRequest) {
		this.currentRequest = currentRequest;
	}

	/**
	 * Gets the assesment appointer text.
	 *
	 * @return the assesment appointer text
	 */
	public TextField getAssesmentAppointerText() {
		return assesmentAppointerText;
	}

	/**
	 * Sets the assesment appointer text.
	 *
	 * @param assesmentAppointerText the new assesment appointer text
	 */
	public void setAssesmentAppointerText(TextField assesmentAppointerText) {
		this.assesmentAppointerText = assesmentAppointerText;
	}

	/**
	 * Gets the examaning due date text.
	 *
	 * @return the examaning due date text
	 */
	public DatePicker getExamaningDueDateText() {
		return examaningDueDateText;
	}

	/**
	 * Sets the examaning due date text.
	 *
	 * @param examaningDueDateText the new examaning due date text
	 */
	public void setExamaningDueDateText(DatePicker examaningDueDateText) {
		this.examaningDueDateText = examaningDueDateText;
	}

	/**
	 * Gets the execution appointer text.
	 *
	 * @return the execution appointer text
	 */
	public TextField getExecutionAppointerText() {
		return executionAppointerText;
	}

	/**
	 * Sets the execution appointer text.
	 *
	 * @param executionAppointerText the new execution appointer text
	 */
	public void setExecutionAppointerText(TextField executionAppointerText) {
		this.executionAppointerText = executionAppointerText;
	}

	/**
	 * Gets the test due date text.
	 *
	 * @return the test due date text
	 */
	public DatePicker getTestDueDateText() {
		return testDueDateText;
	}

	/**
	 * Sets the test due date text.
	 *
	 * @param testDueDateText the new test due date text
	 */
	public void setTestDueDateText(DatePicker testDueDateText) {
		this.testDueDateText = testDueDateText;
	}

	/**
	 * Gets the assesment due date text.
	 *
	 * @return the assesment due date text
	 */
	public DatePicker getAssesmentDueDateText() {
		return assesmentDueDateText;
	}

	/**
	 * Sets the assesment due date text.
	 *
	 * @param assesmentDueDateText the new assesment due date text
	 */
	public void setAssesmentDueDateText(DatePicker assesmentDueDateText) {
		this.assesmentDueDateText = assesmentDueDateText;
	}

	/**
	 * Gets the assesment extension date text.
	 *
	 * @return the assesment extension date text
	 */
	public TextField getAssesmentExtensionDateText() {
		return assesmentExtensionDateText;
	}

	/**
	 * Sets the assesment extension date text.
	 *
	 * @param assesmentExtensionDateText the new assesment extension date text
	 */
	public void setAssesmentExtensionDateText(TextField assesmentExtensionDateText) {
		this.assesmentExtensionDateText = assesmentExtensionDateText;
	}

	/**
	 * Gets the examaning extension text.
	 *
	 * @return the examaning extension text
	 */
	public TextField getExamaningExtensionText() {
		return examaningExtensionText;
	}

	/**
	 * Sets the examaning extension text.
	 *
	 * @param examaningExtensionText the new examaning extension text
	 */
	public void setexamaningExtensionText(TextField examaningExtensionText) {
		this.examaningExtensionText = examaningExtensionText;
	}

	/**
	 * Gets the execution due date text.
	 *
	 * @return the execution due date text
	 */
	public DatePicker getExecutionDueDateText() {
		return executionDueDateText;
	}

	/**
	 * Gets the tester appointed text.
	 *
	 * @return the tester appointed text
	 */
	public TextField getTesterAppointedText() {
		return testerAppointedText;
	}

	/**
	 * Sets the tester appointed text.
	 *
	 * @param testerAppointedText the new tester appointed text
	 */
	public void setTesterAppointedText(TextField testerAppointedText) {
		this.testerAppointedText = testerAppointedText;
	}

	/**
	 * Sets the execution due date text.
	 *
	 * @param executionDueDateText the new execution due date text
	 */
	public void setExecutionDueDateText(DatePicker executionDueDateText) {
		this.executionDueDateText = executionDueDateText;
	}

	/**
	 * Gets the execution extenstion date text.
	 *
	 * @return the execution extenstion date text
	 */
	public TextField getExecutionExtenstionDateText() {
		return executionExtenstionDateText;
	}

	/**
	 * Sets the execution extenstion date text.
	 *
	 * @param executionExtenstionDateText the new execution extenstion date text
	 */
	public void setExecutionExtenstionDateText(TextField executionExtenstionDateText) {
		this.executionExtenstionDateText = executionExtenstionDateText;
	}

	/**
	 * Gets the tester extension date text.
	 *
	 * @return the tester extension date text
	 */
	public TextField getTesterExtensionDateText() {
		return testerExtensionDateText;
	}

	/**
	 * Sets the tester extension date text.
	 *
	 * @param testerExtensionDateText the new tester extension date text
	 */
	public void setTesterExtensionDateText(TextField testerExtensionDateText) {
		this.testerExtensionDateText = testerExtensionDateText;
	}

}
