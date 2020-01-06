package Client;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import Common.ClientServerMessage;
import Common.Enums;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManagerStatisticsController {
	static ManagerStatisticsController _ins;
	@FXML
	private Label dateLabel;

	@FXML
	private Label userNameLabel;

	@FXML
	private Button logoutButton;

	@FXML
	private ChoiceBox<String> reportChoiceBox = new ChoiceBox<String>();

	@FXML
	private Button managerBackBtn;

	@FXML
	private Pane periodReportDate;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private Button getReport1;

	@FXML
	private Pane periodRep;

	@FXML
	private Label openLabel;

	@FXML
	private Label freezeLabel;

	@FXML
	private Label closedLabel;

	@FXML
	private Label rejectedLabel;

	@FXML
	private Label daysLabel;

	@FXML
	private Pane performance;

	@FXML
	private Label extensionTotalLabel;

	@FXML
	private Label extensionMedianLabel;

	@FXML
	private Label extensionSDLabel;

	@FXML
	private Label extensionFDLabel;

	@FXML
	private Label addonsTotalLabel;

	@FXML
	private Label addonsMedianLabel;

	@FXML
	private Label addonsSDLabel;

	@FXML
	private Label addonsFDLabel;

	@FXML
	private Pane delaysPane;

	@FXML
	private ChoiceBox<?> systemCB;

	@FXML
	private Label delaysNum;

	@FXML
	private Label delaysMedian;

	@FXML
	private Label delaysSD;

	@FXML
	private Label delaysFD;

	public void initialize() {
		_ins = this;
		new Thread() {
			public void run() {
				userNameLabel.setText(Main.currentUser.getFirstName() + " " + Main.currentUser.getLastName());
				while (true) // update time in 0.5s intervals
				{

					Platform.runLater(new Runnable() // wont work without this shit
					{
						public void run() {
							DateTime dt = new DateTime();
							dateLabel.setText(dt.toString("dd/MM/yyyy hh:mm:ss a" + " |"));
							dt = null; // for garbage collection
						}
					});
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}

			}
		}.start();
		reportChoiceBox.getItems().add("Period Report");
		reportChoiceBox.getItems().add("Performance Report");
		reportChoiceBox.getItems().add("Delays Report");
		reportChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> changePane());
	}

	public void changePane() {
		switch (reportChoiceBox.getValue().toString()) {
		case "Period Report":
			delaysPane.setVisible(false);
			performance.setVisible(false);
			periodReportDate.setVisible(true);
			break;
		case "Performance Report":
			delaysPane.setVisible(false);
			periodReportDate.setVisible(false);
			performance.setVisible(true);
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetExtensionStat));
			break;
		case "Delays Report":
			periodReportDate.setVisible(false);
			performance.setVisible(false);
			delaysPane.setVisible(true);
			break;
		default:
			break;
		}
	}

	@FXML
	void getReport1(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) throws IOException{
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.logOut, Main.currentUser.getUsername()));
		Main.currentUser = null;
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setResizable(false);
		window.setScene(requests);
		window.show();
	}

	@FXML
	void managerBack(ActionEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("3.0-ManagerScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.setResizable(false);
		window.show();
	}

	public void updateExtensions(ArrayList<Double> l) {
		System.out.println(l);
		Platform.runLater(new Runnable() {
			public void run() {
				extensionTotalLabel.setText(String.format("Total duration: %.0f", l.get(0)));
				extensionMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
				extensionSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));
			}
		});
	}
	
}
