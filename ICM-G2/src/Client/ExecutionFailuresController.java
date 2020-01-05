package Client;

import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		IdLabel.setText("" + r.getId());
		if (Main.currentUser.getRole() == Enums.Role.CommitteChairman
				|| Main.currentUser.getRole() == Enums.Role.CommitteMember) {
			submitBtn.setVisible(true);
			exectuionReport.setEditable(true);
		} else {
			submitBtn.setVisible(false);
			exectuionReport.setEditable(false);
		}
		if (r.getStages()[4].getReportFailure() != null) {
			exectuionReport.setText(r.getStages()[4].getReportFailure());
		}
	}

	public void submitTextField() {
		String msgReport = exectuionReport.getText();
		String[] result= new String[2];
		result[0]=msgReport;
		result[1]=""+r.getId();
		RequestsScreenController._ins.reportMsgAndRef(result);
	}

}
