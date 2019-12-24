package Client;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Enums.MessageEnum;
import Common.Report;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ReportScreenController {
	
	
	
	private Report report;
	@FXML
	private TextField locatinTXT;
	@FXML
	private Label IDlabel;
	@FXML
	private TextField descTXT;

	@FXML
	private TextField resultTXT;

	@FXML
	private TextField constrainsTXT;

	@FXML
	private TextField risksTXT;

	@FXML
	private TextField timeTXT;

	@FXML
	private Button SubmitReportButton;
	static ReportScreenController _ins;
	public void initialize() {
		_ins = this;
		IDlabel.setText(String.valueOf(RequestsScreenController.r.getId()));
		
	}
	public void submitReport() {
		if (risksTXT.getText().equals("") || timeTXT.getText().equals("") || resultTXT.getText().equals("")
				|| constrainsTXT.getText().equals("") || descTXT.getText().equals("")
				|| locatinTXT.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("EMPTY REQUIERD FIELDS!");
			alert.showAndWait();
		}
		else {
			report=new Report();
			report.setDurationAssesment(Integer.parseInt(timeTXT.getText().toString()));
			
			report.setConstrains(constrainsTXT.getText());
			report.setResult(resultTXT.getText());
			report.setRisks(risksTXT.getText());
			report.setDescription(descTXT.getText());
			report.setLocation(locatinTXT.getText());
			report.setRequestId(RequestsScreenController.r.getId());
			
			ClientServerMessage msg = new ClientServerMessage(Enums.MessageEnum.CreateReport, report);
			
			Main.client.handleMessageFromClientUI(msg);
		}
	}
	
}
