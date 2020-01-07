package Client;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import Common.ClientServerMessage;
import Common.Enums;
import Common.FrequencyDeviation;
import Common.Request;
import Common.Enums.SystemENUM;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private Pane delaysInnerPane;
	@FXML
	private ChoiceBox<SystemENUM> systemCB = new ChoiceBox<SystemENUM>();

	@FXML
	private Label delaysNum;

	@FXML
	private Label delaysMedian;

	@FXML
	private Label delaysSD;

	@FXML
	private Label delaysFD;

	@FXML
	public TableView<FrequencyDeviation> extensionsTable;
	@FXML
	public TableView<FrequencyDeviation> delaysTable;

	public TableView<FrequencyDeviation> getExtensionsTable() {
		return extensionsTable;
	}

	public void setExtensionsTable(TableView<FrequencyDeviation> extensionsTable) {
		this.extensionsTable = extensionsTable;
	}

	public TableView<FrequencyDeviation> getDelaysTable() {
		return delaysTable;
	}

	public void setDelaysTable(TableView<FrequencyDeviation> delaysTable) {
		this.delaysTable = delaysTable;
	}

	@FXML
	public TableView<FrequencyDeviation> addonsTable;

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
		systemCB.getItems().addAll(SystemENUM.All, SystemENUM.Computers, SystemENUM.InfoStation, SystemENUM.Labs);
		systemCB.getItems().addAll(SystemENUM.Library, SystemENUM.Moodle, SystemENUM.Site);
		systemCB.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> changeDelaySystem());
		reportChoiceBox.getItems().add("Period Report");
		reportChoiceBox.getItems().add("Performance Report");
		reportChoiceBox.getItems().add("Delays Report");
		reportChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> changePane());
		setTables();
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
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetExtensionFreq));
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

	public void changeDelaySystem() {
		delaysInnerPane.setVisible(true);
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.GetDelaysStat, systemCB.getValue()));
	}

	public void showDelaySystem(ArrayList<Double> l) {
		if (l.isEmpty()) {
			delaysNum.setText("Number of delays: 0");
			delaysMedian.setText("Median: 0");
			delaysSD.setText("Standard Deviation: 0");
			delaysTable.setVisible(false);
		}
		else {
			delaysNum.setText("Number of delays: " + l.get(0).toString());
			delaysMedian.setText("Median: " + l.get(1).toString());
			delaysSD.setText("Standard Deviation: " + l.get(2).toString());
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.GetDelaysFreq, systemCB.getValue()));
			delaysTable.setVisible(true);
		}
	}

	@FXML
	void getReport1(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) throws IOException {
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
		Platform.runLater(new Runnable() {
			public void run() {
				extensionTotalLabel.setText(String.format("Total duration: %.0f", l.get(0)));
				extensionMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
				extensionSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setTables() {
		TableColumn<FrequencyDeviation, Double> valueC = new TableColumn<>("Value");
		valueC.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC = new TableColumn<>("Frequency");
		freqC.setCellValueFactory(new PropertyValueFactory("freq"));
		extensionsTable.getColumns().addAll(valueC, freqC);
		valueC.setPrefWidth(50);
		freqC.setPrefWidth(100);

		TableColumn<FrequencyDeviation, Double> valueC2 = new TableColumn<>("Value");
		valueC.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC2 = new TableColumn<>("Frequency");
		freqC.setCellValueFactory(new PropertyValueFactory("freq"));
		addonsTable.getColumns().addAll(valueC2, freqC2);
		valueC2.setPrefWidth(50);
		freqC2.setPrefWidth(100);
		
		TableColumn<FrequencyDeviation, Double> valueC3 = new TableColumn<>("Value");
		valueC3.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC3 = new TableColumn<>("Frequency");
		freqC3.setCellValueFactory(new PropertyValueFactory("freq"));
		delaysTable.getColumns().addAll(valueC3, freqC3);
		valueC3.setPrefWidth(50);
		freqC3.setPrefWidth(100);
	}
}
