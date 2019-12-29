package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ExecutionFailuresController {
	Stage newWindow = new Stage();
	static ExecutionFailuresController _ins;
	private Request r;
	@FXML
	private TextField exectuionReport;
	@FXML
	private Button submitBtn;
	@FXML
	private Label IdLabel;
	public void initialize() {
		_ins = this;
		r = RequestsScreenController.r;
		IdLabel.setText(""+r.getId());
		if (r.getStages()[4].getReportFailure() == null) {
			submitBtn.setVisible(true);
			exectuionReport.setEditable(true);
		}
		else {
			submitBtn.setVisible(false);
			exectuionReport.setEditable(false);
			exectuionReport.setText(r.getStages()[4].getReportFailure());
		}
	}
	public void submitTextField() {
		String msgReport=exectuionReport.getText();
		String res=msgReport+"-"+r.getId();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.TesterRep,res));
		RequestsScreenController.reportMsgAndRef();
	}
	
}
