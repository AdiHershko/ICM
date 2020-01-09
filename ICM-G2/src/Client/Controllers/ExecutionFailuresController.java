package Client.Controllers;

import Client.ClientMain;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecutionFailuresController.
 */
public class ExecutionFailuresController {
	
	/** The new window. */
	public Stage newWindow = new Stage();
	
	/** The ins. */
	public static ExecutionFailuresController _ins;
	
	/** The r. */
	private Request r;
	
	/** The exectuion report. */
	@FXML
	private TextField exectuionReport;
	
	/** The submit btn. */
	@FXML
	private Button submitBtn;
	
	/** The Id label. */
	@FXML
	private Label IdLabel;

	/**
	 * Initialize.
	 */
	public void initialize() {
		_ins = this;
		r = RequestsScreenController.r;
		IdLabel.setText("" + r.getId());
		if (ClientMain.currentUser.getRole() == Enums.Role.CommitteChairman
				|| ClientMain.currentUser.getRole() == Enums.Role.CommitteMember) {
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

	/**
	 * Submit text field.
	 */
	public void submitTextField() {
		String msgReport = exectuionReport.getText();
		String[] result = new String[2];
		result[0] = msgReport;
		result[1] = "" + r.getId();
		RequestsScreenController._ins.reportMsgAndRef(result);
	}

}
