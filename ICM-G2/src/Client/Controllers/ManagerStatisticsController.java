package Client.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.FrequencyDeviation;
import Common.Enums.SystemENUM;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class ManagerStatisticsController:
 * Controller for 3.2-ManagerStatistics.fxml.
 */
public class ManagerStatisticsController {
	
	/** The ins. */
	public static ManagerStatisticsController _ins;
	
	/** The date label. */
	@FXML
	private Label dateLabel;

	/** The user name label. */
	@FXML
	private Label userNameLabel;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The report choice box. */
	@FXML
	private ChoiceBox<String> reportChoiceBox = new ChoiceBox<String>();

	/** The manager back button. */
	@FXML
	private Button managerBackBtn;

	/** The period report date pane. */
	@FXML
	private Pane periodReportDate;

	/** The from date in periodic report. */
	@FXML
	private DatePicker fromDate;

	/** The to date in periodic report. */
	@FXML
	private DatePicker toDate;

	/** The get first report (periodic activity) button. */
	@FXML
	private Button getReport1;

	/** The period report pane. */
	@FXML
	private Pane periodRep;

	/** The open label. */
	@FXML
	private Label openLabel;

	/** The freeze label. */
	@FXML
	private Label freezeLabel;

	/** The closed label. */
	@FXML
	private Label closedLabel;

	/** The rejected label. */
	@FXML
	private Label rejectedLabel;

	/** The days label. */
	@FXML
	private Label daysLabel;

	/** The performance report pane. */
	@FXML
	private Pane performance;

	/** The extension total label. */
	@FXML
	private Label extensionTotalLabel;

	/** The extension median label. */
	@FXML
	private Label extensionMedianLabel;

	/** The extension SD label. */
	@FXML
	private Label extensionSDLabel;

	/** The extension FD label. */
	@FXML
	private Label extensionFDLabel;

	/** The addons total label. */
	@FXML
	private Label addonsTotalLabel;

	/** The addons median label. */
	@FXML
	private Label addonsMedianLabel;

	/** The addons SD label. */
	@FXML
	private Label addonsSDLabel;

	/** The addons FD label. */
	@FXML
	private Label addonsFDLabel;

	/** The delays pane. */
	@FXML
	private Pane delaysPane;
	
	/** The delays inner pane. */
	@FXML
	private Pane delaysInnerPane;
	
	/** The system ChoiceBox for delays report. */
	@FXML
	private ChoiceBox<SystemENUM> systemCB = new ChoiceBox<SystemENUM>();

	/** The delays number. */
	@FXML
	private Label delaysNum;

	/** The delays median. */
	@FXML
	private Label delaysMedian;

	/** The delays SD. */
	@FXML
	private Label delaysSD;

	/** The delays FD. */
	@FXML
	private Label delaysFD;

	/** The extensions graph. */
	@FXML
	private BarChart extensionsGraph;

	/** The x axis. */
	@FXML
	private CategoryAxis xAxis;

	/** The y axis. */
	@FXML
	private NumberAxis yAxis;

	/** The series for the graph. */
	static XYChart.Series series;
	
	/** The first series for the graph. */
	static XYChart.Series series1;
	
	/** The second series for the graph. */
	static XYChart.Series series2;

	/** The addons graph. */
	@FXML
	private BarChart addonsGraph;

	/** The delays graph. */
	@FXML
	private BarChart delaysGraph;

	/** The extensions table. */
	@FXML
	public TableView<FrequencyDeviation> extensionsTable;
	
	/** The addons table. */
	@FXML
	public TableView<FrequencyDeviation> addonsTable;
	
	/** The delays table. */
	@FXML
	public TableView<FrequencyDeviation> delaysTable;

	/**
	 * Gets the extensions table.
	 *
	 * @return the extensions table
	 */
	public TableView<FrequencyDeviation> getExtensionsTable() {
		return extensionsTable;
	}

	/**
	 * Sets the extensions table.
	 *
	 * @param extensionsTable the new extensions table
	 */
	public void setExtensionsTable(TableView<FrequencyDeviation> extensionsTable) {
		this.extensionsTable = extensionsTable;
	}

	/**
	 * Gets the delays table.
	 *
	 * @return the delays table
	 */
	public TableView<FrequencyDeviation> getDelaysTable() {
		return delaysTable;
	}

	/**
	 * Sets the delays table.
	 *
	 * @param delaysTable the new delays table
	 */
	public void setDelaysTable(TableView<FrequencyDeviation> delaysTable) {
		this.delaysTable = delaysTable;
	}

	/**
	 * Gets the addons table.
	 *
	 * @return the addons table
	 */
	public TableView<FrequencyDeviation> getAddonsTable() {
		return addonsTable;
	}

	/**
	 * Sets the addons table.
	 *
	 * @param addonsTable the new addons table
	 */
	public void setAddonsTable(TableView<FrequencyDeviation> addonsTable) {
		this.addonsTable = addonsTable;
	}

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		series = new XYChart.Series<>();
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		new Thread() {
			public void run() {
				Platform.runLater(() -> userNameLabel
						.setText(ClientMain.currentUser.getFirstName() + " " + ClientMain.currentUser.getLastName()));
				while (true) // update time in 0.5s intervals
				{

					Platform.runLater(new Runnable()
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
		ManagerStatisticsController._ins.getExtensionsGraph().getYAxis().setLabel("Frequency");
		ManagerStatisticsController._ins.getExtensionsGraph().getXAxis().setLabel("Value");
		systemCB.getItems().addAll(SystemENUM.All, SystemENUM.Computers, SystemENUM.InfoStation, SystemENUM.Labs);
		systemCB.getItems().addAll(SystemENUM.Library, SystemENUM.Moodle, SystemENUM.Site);
		systemCB.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> changeDelaySystem());
		reportChoiceBox.getItems().add("Period Report");
		reportChoiceBox.getItems().add("Performance Report");
		reportChoiceBox.getItems().add("Delays Report");
		reportChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> changePane());
		setTables();
	}

	/**
	 * Change pane from the report choice box.
	 */
	public void changePane() {
		switch (reportChoiceBox.getValue().toString()) {
		case "Period Report":
			delaysPane.setVisible(false);
			performance.setVisible(false);
			periodReportDate.setVisible(true);
			periodRep.setVisible(false);
			break;
		case "Performance Report":
			addonsGraph.getData().clear();
			delaysPane.setVisible(false);
			periodReportDate.setVisible(false);
			performance.setVisible(true);
			xAxis.setLabel("Value");
			yAxis.setLabel("Frequency");
			extensionsGraph.setTitle("Extensions");
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetExtensionStat));
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetExtensionFreq));
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetAddonsStat));
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetAddonsFreq));
			break;
		case "Delays Report":
			delaysGraph.getData().clear();
			periodReportDate.setVisible(false);
			performance.setVisible(false);
			delaysPane.setVisible(true);
			break;
		default:
			break;
		}
	}

	/**
	 * Change delay statistics.
	 */
	public void changeDelaySystem() {
		delaysInnerPane.setVisible(true);
		delaysGraph.getData().clear();
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.GetDelaysStat, systemCB.getValue()));
	}

	/**
	 * Show delay for a system.
	 *
	 * @param l the list of data
	 */
	public void showDelaySystem(ArrayList<Double> l) {
		if (l.isEmpty()) {
			delaysNum.setText("Number of delays: 0");
			delaysMedian.setText("Median: 0");
			delaysSD.setText("Standard Deviation: 0");
			delaysTable.setVisible(false);
		} else {
			delaysNum.setText(String.format("Number of delays: %.0f", l.get(0)));
			delaysMedian.setText(String.format("Median: %.1f", l.get(1)));
			delaysSD.setText(String.format("Standard Deviation: %g", l.get(2)));
			ClientMain.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.GetDelaysFreq, systemCB.getValue()));
			delaysTable.setVisible(true);
		}
	}

	/**
	 * Gets the activity periodic report.
	 *
	 * @param event the mouse click
	 */
	@FXML
	void getReport1(ActionEvent event) {
		String s1 = fromDate.getValue().toString();
		String s2 = toDate.getValue().toString();
		String[] s = new String[2];
		s[0] = s1;
		s[1] = s2;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = null, dt2 = null;
		try {
			dt1 = dtf.parseDateTime(s1);
			dt2 = dtf.parseDateTime(s2);
		} catch (IllegalArgumentException e) {
		}
		if (dt1.isBefore(dt2)) {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.getPeriodReport, s));
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong input");
			alert.setContentText("From date bigger or equal from the end date");
			alert.showAndWait();
			return;
		}
	}

	/**
	 * Logout button.
	 *
	 * @param event the mouse click
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void logout(ActionEvent event) throws IOException {
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.logOut, ClientMain.currentUser.getUsername()));
		ClientMain.currentUser = null;
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("0.1-loginScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setResizable(false);
		window.setScene(requests);
		window.show();
	}

	/**
	 * Manager back button.
	 *
	 * @param event the mouse click
	 */
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

	/**
	 * Update extensions labels.
	 *
	 * @param l the list
	 */
	public void updateExtensions(ArrayList<Double> l) {
		Platform.runLater(new Runnable() {
			public void run() {
				extensionTotalLabel.setText(String.format("Total duration: %.0f", l.get(0)));
				extensionMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
				extensionSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));
			}
		});
	}

	/**
	 * Update addons labels.
	 *
	 * @param l the list
	 */
	public void updateAddons(ArrayList<Double> l) {
		Platform.runLater(new Runnable() {
			public void run() {
				addonsTotalLabel.setText(String.format("Total duration: %.0f", l.get(0)));
				addonsMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
				addonsSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));
			}
		});
	}

	/**
	 * Sets the tables.
	 */
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
		valueC2.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC2 = new TableColumn<>("Frequency");
		freqC2.setCellValueFactory(new PropertyValueFactory("freq"));
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

	/**
	 * Update periodic report labels.
	 *
	 * @param res the res
	 */
	public void updatePeropd(ArrayList<Integer> res) {
		Platform.runLater(new Runnable() {
			public void run() {
				openLabel.setText("Open requests: " + res.get(0));
				freezeLabel.setText("Freezed requests: " + res.get(2));
				closedLabel.setText("Closed requests: " + res.get(1));
				rejectedLabel.setText("Rejected requests: " + res.get(3));
				daysLabel.setText("Number of work days: " + res.get(4));
				periodRep.setVisible(true);
			}
		});
	}

	/**
	 * Gets the extensions graph.
	 *
	 * @return the extensions graph
	 */
	public BarChart getExtensionsGraph() {
		return extensionsGraph;
	}

	/**
	 * Sets the extensions graph.
	 *
	 * @param extensionsGraph the new extensions graph
	 */
	public void setExtensionsGraph(BarChart extensionsGraph) {
		this.extensionsGraph = extensionsGraph;
	}

	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	public XYChart.Series getSeries() {
		return series;
	}

	/**
	 * Sets the series.
	 *
	 * @param series the new series
	 */
	public void setSeries(XYChart.Series series) {
		this.series = series;
	}

	/**
	 * Gets the addons graph.
	 *
	 * @return the addons graph
	 */
	public BarChart getAddonsGraph() {
		return addonsGraph;
	}

	/**
	 * Sets the addons graph.
	 *
	 * @param addonsGraph the new addons graph
	 */
	public void setAddonsGraph(BarChart addonsGraph) {
		this.addonsGraph = addonsGraph;
	}

	/**
	 * Gets the delays graph.
	 *
	 * @return the delays graph
	 */
	public BarChart getDelaysGraph() {
		return delaysGraph;
	}

	/**
	 * Sets the delays graph.
	 *
	 * @param delaysGraph the new delays graph
	 */
	public void setDelaysGraph(BarChart delaysGraph) {
		this.delaysGraph = delaysGraph;
	}

	/**
	 * Gets the series 1.
	 *
	 * @return the series 1
	 */
	public static XYChart.Series getSeries1() {
		return series1;
	}

	/**
	 * Sets the series 1.
	 *
	 * @param series1 the new series 1
	 */
	public static void setSeries1(XYChart.Series series1) {
		ManagerStatisticsController.series1 = series1;
	}

	/**
	 * Gets the series 2.
	 *
	 * @return the series 2
	 */
	public static XYChart.Series getSeries2() {
		return series2;
	}

	/**
	 * Sets the series 2.
	 *
	 * @param series2 the new series 2
	 */
	public static void setSeries2(XYChart.Series series2) {
		ManagerStatisticsController.series2 = series2;
	}
}
