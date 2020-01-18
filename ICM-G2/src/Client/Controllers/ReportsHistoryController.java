package Client.Controllers;

import java.io.IOException;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsHistoryController.
 */
public class ReportsHistoryController {


	/** The open report button. */
	@FXML
	private Button openReportButton;


	/** The reports list view. */
	@FXML
	private ListView<String> reportsListView;

	/** The ins. */
	public static ReportsHistoryController _ins;

	/** The chosen date. */
	private String chosenDate;

	/**
	 * Initialize.
	 */
	public void initialize(){
		_ins=this;
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETREPORTHISTORY));
	}



	/**
	 * Open report from the list.
	 */
	@FXML
	public void openReport(){
		if (reportsListView.getSelectionModel().getSelectedItem()==null) return;
		chosenDate = reportsListView.getSelectionModel().getSelectedItem();
		Parent root = null;
		Stage newWindow = new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource("3.4.1-Report.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		Scene reportsHistory = new Scene(root);
		newWindow.setTitle("Reports History List");
		newWindow.setScene(reportsHistory);
		newWindow.setResizable(false);
		newWindow.initOwner((Stage) (openReportButton.getScene().getWindow()));
		newWindow.initModality(Modality.WINDOW_MODAL);
		newWindow.show();

	}



	/**
	 * Gets the reports list view.
	 *
	 * @return the reports list view
	 */
	public ListView<String> getReportsListView() {
		return reportsListView;
	}



	/**
	 * Gets the chosen date from the list.
	 *
	 * @return the chosen date
	 */
	public String getChosenDate() {
		return chosenDate;
	}




}
