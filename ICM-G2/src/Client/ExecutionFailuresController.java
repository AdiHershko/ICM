package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;
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
	private Scene reportScene;
	static ExecutionFailuresController _ins;
	private int requestNum;
	@FXML
	private TextField exectuionReport;
	@FXML
	private Button submitBtn;
	@FXML
	private Label IdLabel;
	public void initialize() {
		requestNum=RequestsScreenController.idnum;
		_ins = this;
		IdLabel.setText(""+requestNum);
	}
	public void submitTextField() {
		String msgReport=exectuionReport.getText();
		String res=msgReport+"-"+RequestsScreenController.idnum;
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.TesterRep,res));
		RequestsScreenController.reportMsgAndRef();
	}
	
}
