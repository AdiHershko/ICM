package Client.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportsHistoryController {


	@FXML
	private Button openReportButton;


	@FXML
	private ListView reportsListView;



	@FXML
	public void openReport(){
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

}
