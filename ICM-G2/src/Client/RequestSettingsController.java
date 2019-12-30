package Client;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private TextField examiningDueDateText;
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


	public void initialize()
	{
		_ins=this;
		currentRequest=Main.currentRequest;
		setScreen();
	}


	/*
	 * TODO:
	 * compare due date with todays date
	 * add tester appointer text
	 */



	public void setScreen(){
		requestIDLabel.setText(""+currentRequest.getId());
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.STAGESSCREEN,currentRequest));
	}


	@FXML
	public void editAssesment(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER,currentRequest.getId(),assesmentAppointerText.getText()));
	}

	@FXML
	public void editExecutioner(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER,currentRequest.getId(),executionAppointerText.getText()));
	}


	@FXML
	public void doneButtonAction(){
		Window window = doneButton.getScene().getWindow();
		if (window instanceof Stage)
			((Stage) window).close();

	}

	@FXML
	public void setAssesmentDate(){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(assesmentDueDateText.getText());
		// add if not before today
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE,currentRequest.getId(),dt.toString()));
	}

	@FXML
	public void approveAssesment(){
		setDenied(0);
	}

	@FXML
	public void declineAssesment(){
		setDenied(1);
	}


	private void setDenied(int isDenied)
	{
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.APPROVEASSEXTENSION,currentRequest.getId(),isDenied));
	}

	@FXML
	public void saveExamDate(){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(examaningDueDateText.getText());
		// add if not before today
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SETEXAMDATE,currentRequest.getId(),dt.toString()));
	}


	@FXML
	public void setExecDate(){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(executionDueDateText.getText());
		// add if not before today
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SETEXECMDATE,currentRequest.getId(),dt.toString()));
	}

	@FXML
	public void setTestDate(){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(testDueDateText.getText());
		// add if not before today
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SETTESTDATE,currentRequest.getId(),dt.toString()));

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



	public TextField getExaminingDueDateText() {
		return examiningDueDateText;
	}



	public void setExaminingDueDateText(TextField examiningDueDateText) {
		this.examiningDueDateText = examiningDueDateText;
	}



	public TextField getExecutionDueDateText() {
		return executionDueDateText;
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
