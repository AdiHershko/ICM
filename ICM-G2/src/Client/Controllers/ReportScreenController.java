package Client.Controllers;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The Class ReportScreenController.
 * Controller for 2.2-ReportScreen.fxml
 */
public class ReportScreenController {

	/** The new window. */
	Stage newWindow = new Stage();
	
	/** The report. */
	private Report report;
	
	/** The location TXT field. */
	@FXML
	private TextField locatinTXT;
	
	/** The ID label. */
	@FXML
	private Label IDlabel;
	
	/** The description TXT. */
	@FXML
	private TextField descTXT;

	/** The result TXT. */
	@FXML
	private TextField resultTXT;

	/** The constrains TXT. */
	@FXML
	private TextField constrainsTXT;

	/** The risks TXT. */
	@FXML
	private TextField risksTXT;

	/** The time TXT. */
	@FXML
	private TextField timeTXT;

	/** The Submit report button. */
	@FXML
	private Button SubmitReportButton;
	
	/** The Save report button. */
	@FXML
	private Button SaveReportButton;
	
	/** The ins. */
	public static ReportScreenController _ins;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		IDlabel.setText(String.valueOf(RequestsScreenController.r.getId()));
		locatinTXT.setText(RequestsScreenController.reportOfRequest.getLocation());
		descTXT.setText(RequestsScreenController.reportOfRequest.getDescription());
		resultTXT.setText(RequestsScreenController.reportOfRequest.getResult());
		constrainsTXT.setText(RequestsScreenController.reportOfRequest.getConstrains());
		risksTXT.setText(RequestsScreenController.reportOfRequest.getRisks());
		if (RequestsScreenController.reportOfRequest.getDurationAssesment() == -1) {
			timeTXT.setText("");
		} else {
			timeTXT.setText(String.valueOf(RequestsScreenController.reportOfRequest.getDurationAssesment()));
		}
		if (RequestsScreenController.r.getCurrentStage() != Enums.RequestStageENUM.Assesment
				&& ClientMain.currentUser.getRole() != Enums.Role.Manager
				&& ClientMain.currentUser.getRole() != Enums.Role.Supervisor) {
			SubmitReportButton.setVisible(false);
			SaveReportButton.setVisible(false);
			descTXT.setEditable(false);
			resultTXT.setEditable(false);
			constrainsTXT.setEditable(false);
			risksTXT.setEditable(false);
			timeTXT.setEditable(false);
			locatinTXT.setEditable(false);
		}

	}

	/**
	 * Save report button function.
	 */
	public void saveReport() {

		report = new Report();
		if (!(timeTXT.getText().toString().equals(""))) {
			try {
				report.setDurationAssesment(Integer.parseInt(timeTXT.getText().toString()));
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("Not a number in duration assesment field.");
				alert.showAndWait();
				return;
			}
		} else {
			report.setDurationAssesment(-1);
		}

		report.setConstrains(constrainsTXT.getText());
		report.setResult(resultTXT.getText());
		report.setRisks(risksTXT.getText());
		report.setDescription(descTXT.getText());
		report.setLocation(locatinTXT.getText());
		report.setRequestId(RequestsScreenController.r.getId());

		ClientServerMessage msg = new ClientServerMessage(Enums.MessageEnum.CreateReport, report);

		ClientMain.client.handleMessageFromClientUI(msg);
		RequestsScreenController._ins.closeExtraWindow();
	}

	/**
	 * Submit report button function.
	 */
	public void submitReport() {
		if (risksTXT.getText().equals("") || timeTXT.getText().equals("") || resultTXT.getText().equals("")
				|| constrainsTXT.getText().equals("") || descTXT.getText().equals("")
				|| locatinTXT.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Required fields are missing.");
			alert.showAndWait();
		} else {
			report = new Report();
			if (!(timeTXT.getText().toString().equals(""))) {
				try {
					report.setDurationAssesment(Integer.parseInt(timeTXT.getText().toString()));
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR!");
					alert.setContentText("Not a number in duration assesment field!");
					alert.showAndWait();
					return;
				}
			} else {
				report.setDurationAssesment(-1);//-1 is the default value for missing duration
			}

			report.setConstrains(constrainsTXT.getText());
			report.setResult(resultTXT.getText());
			report.setRisks(risksTXT.getText());
			report.setDescription(descTXT.getText());
			report.setLocation(locatinTXT.getText());
			report.setRequestId(RequestsScreenController.r.getId());
			RequestsScreenController._ins.closeExtraWindowOnly();
			ClientServerMessage msg = new ClientServerMessage(Enums.MessageEnum.CreateReport, report);
			ClientMain.client.handleMessageFromClientUI(msg);
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.UpdateStage, report.getRequestId()));
			RequestsScreenController._ins.unVisibleRequestPane();
			RequestsScreenController._ins.closeExtraWindowSub();

		}

	}

}
