package Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportController {

	@FXML
	private Label creationDateLabel;
	@FXML
	private Label periodLabel;
	@FXML
	private Label openLabel;
	@FXML
	private Label freezedLabel;
	@FXML
	private Label closedLabel;
	@FXML
	private Label rejectedLabel;
	@FXML
	private Label totalLabel;

	@FXML
	private Button closeButton;


	@FXML
	public void close(){
		((Stage)closeButton.getScene().getWindow()).close();
	}



}
