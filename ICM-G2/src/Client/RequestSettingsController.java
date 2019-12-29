package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	public void initialize()
	{
		_ins=this;
		currentRequest=Main.currentRequest;
		setScreen();
	}


	/*
	 * TODO:
	 * CLEAR STAGES WITHOUT 5 STAGES (EXCEPTIONS)
	 * Save examining due date
	 * save testing due date
	 * decide what buttons to leave for dates
	 * make them useful
	 * change submit to exit
	 */



	public void setScreen(){
		requestIDLabel.setText(""+currentRequest.getId());
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.STAGESSCREEN,currentRequest));
	}


	@FXML
	public void editAssesment(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER,assesmentAppointerText.getText()));
	}

	@FXML
	public void editExecutioner(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER,executionAppointerText.getText()));
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
