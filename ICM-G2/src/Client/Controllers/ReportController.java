package Client.Controllers;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The Class ReportController.
 * Controller for 3.4.1-Report.fxml.
 */
public class ReportController {

	/** The creation date label. */
	@FXML
	private Label creationDateLabel;

	/** The period label. */
	@FXML
	private Label periodLabel;

	/** The open label. */
	@FXML
	private Label openLabel;

	/** The freezed label. */
	@FXML
	private Label freezedLabel;

	/** The closed label. */
	@FXML
	private Label closedLabel;

	/** The rejected label. */
	@FXML
	private Label rejectedLabel;

	/** The total label. */
	@FXML
	private Label totalLabel;

	/** The close button. */
	@FXML
	private Button closeButton;

	/** The date. */
	private String date;

	/** The ins. */
	public static ReportController _ins;

	/**
	 * Initialize.
	 */
	public void initialize(){
		_ins=this;
		date=ReportsHistoryController._ins.getChosenDate();
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.LOADREPORTHISTORY,date));

	}


	/**
	 * Close the window.
	 */
	@FXML
	public void close(){
		((Stage)closeButton.getScene().getWindow()).close();
	}

	/**
	 * Load data of the report.
	 *
	 * @param data the data of the report
	 */
	public void loadData(Object[] data){
		String[] dataStr = (String[]) data;
		creationDateLabel.setText(dataStr[0]);
		periodLabel.setText(dataStr[1]+" to "+dataStr[2]);
		openLabel.setText(dataStr[3]);
		freezedLabel.setText(dataStr[4]);
		closedLabel.setText(dataStr[5]);
		rejectedLabel.setText(dataStr[6]);
		totalLabel.setText(dataStr[7]);

	}


}
