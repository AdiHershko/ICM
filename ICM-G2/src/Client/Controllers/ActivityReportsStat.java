package Client.Controllers;

import java.util.ArrayList;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.FrequencyDeviation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * The Class ActivityReportsStat.
 * 
 */
public class ActivityReportsStat {

	/** The instants. */
	public static ActivityReportsStat _ins;

	/** The open total duration. */
	@FXML
	private Label openTotalDuration;

	/** The open median label. */
	@FXML
	private Label openMedianLabel;

	/** The open SD label. */
	@FXML
	private Label openSDLabel;

	/** The open FD label. */
	@FXML
	private Label openFDLabel;

	/** The open table. */
	@FXML
	private TableView<FrequencyDeviation> openTable;

	/** The open graph. */
	@FXML
	private BarChart<String, Integer> openGraph;

	/** The x axis. */
	@FXML
	private CategoryAxis xAxis;

	/** The y axis. */
	@FXML
	private NumberAxis yAxis;

	/** The closed total label. */
	@FXML
	private Label closedTotalLabel;

	/** The closed median label. */
	@FXML
	private Label closedMedianLabel;

	/** The closed SD label. */
	@FXML
	private Label closedSDLabel;

	/** The closed FD label. */
	@FXML
	private Label closedFDLabel;

	/** The closed table. */
	@FXML
	private TableView<FrequencyDeviation> closedTable;

	/** The closed graph. */
	@FXML
	private BarChart<String, Integer> closedGraph;

	/** The x axis 1. */
	@FXML
	private CategoryAxis xAxis1;

	/** The y axis 1. */
	@FXML
	private NumberAxis yAxis1;

	/** The frozen total label. */
	@FXML
	private Label frozenTotalLabel;

	/** The frozen median label. */
	@FXML
	private Label frozenMedianLabel;

	/** The frozen SD label. */
	@FXML
	private Label frozenSDLabel;

	/** The frozen FD label. */
	@FXML
	private Label frozenFDLabel;

	/** The frozen table. */
	@FXML
	private TableView<FrequencyDeviation> frozenTable;

	/** The frozen graph. */
	@FXML
	private BarChart<String, Integer> frozenGraph;

	/** The x axis 2. */
	@FXML
	private CategoryAxis xAxis2;

	/** The y axis 2. */
	@FXML
	private NumberAxis yAxis2;

	/** The rejected total label. */
	@FXML
	private Label rejectedTotalLabel;

	/** The rejected median label. */
	@FXML
	private Label rejectedMedianLabel;

	/** The rejected SD label. */
	@FXML
	private Label rejectedSDLabel;

	/** The rejected FD label. */
	@FXML
	private Label rejectedFDLabel;

	/** The rejected table. */
	@FXML
	private TableView<FrequencyDeviation> rejectedTable;

	/** The rejected graph. */
	@FXML
	private BarChart<String, Integer> rejectedGraph;

	/** The x axis 21. */
	@FXML
	private CategoryAxis xAxis21;

	/** The y axis 21. */
	@FXML
	private NumberAxis yAxis21;

	/** The wd table. */
	@FXML
	private TableView<FrequencyDeviation> wdTable;

	/** The wd graph. */
	@FXML
	private BarChart<String, Integer> wdGraph;

	/** The x axis 22. */
	@FXML
	private CategoryAxis xAxis22;

	/** The y axis 22. */
	@FXML
	private NumberAxis yAxis22;

	/** The close button. */
	@FXML
	private Button closeButton;

	/** The close button 1. */
	@FXML
	private Button closeButton1;

	/** The wd total label. */
	@FXML
	private Label wdTotalLabel;

	/** The wd median label. */
	@FXML
	private Label wdMedianLabel;

	/** The wd SD label. */
	@FXML
	private Label wdSDLabel;

	/** The series 1. */
	static XYChart.Series<String, Integer> series1;
	
	/** The series 2. */
	static XYChart.Series<String, Integer> series2;
	
	/** The series 3. */
	static XYChart.Series<String, Integer> series3;
	
	/** The series 4. */
	static XYChart.Series<String, Integer> series4;
	
	/** The series 5. */
	static XYChart.Series<String, Integer> series5;

	/**
	 * Close.
	 *
	 * @param event the event
	 */
	@FXML
	void close(ActionEvent event) {
		((Stage) closeButton.getScene().getWindow()).close();
	}

	/**
	 * Initialize.
	 */
	public void initialize() {
		_ins = this;
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.ALLACTIVITY));
		setTables();
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series3 = new XYChart.Series<>();
		series4 = new XYChart.Series<>();
		series5 = new XYChart.Series<>();
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
		openTable.getColumns().addAll(valueC, freqC);
		valueC.setPrefWidth(50);
		freqC.setPrefWidth(100);

		TableColumn<FrequencyDeviation, Double> valueC2 = new TableColumn<>("Value");
		valueC2.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC2 = new TableColumn<>("Frequency");
		freqC2.setCellValueFactory(new PropertyValueFactory("freq"));
		closedTable.getColumns().addAll(valueC2, freqC2);
		valueC2.setPrefWidth(50);
		freqC2.setPrefWidth(100);

		TableColumn<FrequencyDeviation, Double> valueC3 = new TableColumn<>("Value");
		valueC3.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC3 = new TableColumn<>("Frequency");
		freqC3.setCellValueFactory(new PropertyValueFactory("freq"));
		frozenTable.getColumns().addAll(valueC3, freqC3);
		valueC3.setPrefWidth(50);
		freqC3.setPrefWidth(100);

		TableColumn<FrequencyDeviation, Double> valueC4 = new TableColumn<>("Value");
		valueC4.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC4 = new TableColumn<>("Frequency");
		freqC4.setCellValueFactory(new PropertyValueFactory("freq"));
		rejectedTable.getColumns().addAll(valueC4, freqC4);
		valueC4.setPrefWidth(50);
		freqC4.setPrefWidth(100);

		TableColumn<FrequencyDeviation, Double> valueC5 = new TableColumn<>("Value");
		valueC5.setCellValueFactory(new PropertyValueFactory("value"));
		TableColumn<FrequencyDeviation, Integer> freqC5 = new TableColumn<>("Frequency");
		freqC5.setCellValueFactory(new PropertyValueFactory("freq"));
		wdTable.getColumns().addAll(valueC5, freqC5);
		valueC5.setPrefWidth(50);
		freqC5.setPrefWidth(100);
	}

	/**
	 * Sets the labels.
	 *
	 * @param allActivity the new activity data
	 */
	public void setLabels(ArrayList<ArrayList<Double>> allActivity) {
		ArrayList<Double> l = allActivity.get(0);
		openTotalDuration.setText(String.format("Total: %.0f", l.get(0)));
		openMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
		openSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));

		l = allActivity.get(1);
		frozenTotalLabel.setText(String.format("Total: %.0f", l.get(0)));
		frozenMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
		frozenSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));

		l = allActivity.get(2);
		closedTotalLabel.setText(String.format("Total: %.0f", l.get(0)));
		closedMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
		closedSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));

		l = allActivity.get(3);
		rejectedTotalLabel.setText(String.format("Total: %.0f", l.get(0)));
		rejectedMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
		rejectedSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));

		l = allActivity.get(4);
		wdTotalLabel.setText(String.format("Total: %.0f", l.get(0)));
		wdMedianLabel.setText(String.format("Median: %.1f", l.get(1)));
		wdSDLabel.setText(String.format("Standard Deviation: %g", l.get(2)));
	}

	/**
	 * Sets the frequency.
	 *
	 * @param allActivityFreq the new frequency
	 */
	public void setFreq(ArrayList<Object[]> allActivityFreq) {
		try {
			Object[] arr;
			// openTable
			arr = allActivityFreq.get(0);
			ObservableList<FrequencyDeviation> openFreq = FXCollections.observableArrayList();
			for (Object fd : arr) {
				openFreq.add((FrequencyDeviation) fd);
			}
			openTable.setItems(openFreq);
			Platform.runLater(new Runnable() {
				public void run() {
					if (series1 != null)
						series1.getData().clear();
					for (FrequencyDeviation fd : openFreq)
						series1.getData().add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
					openGraph.getData().add(series1);
				}
			});

			// frozenTable
			arr = allActivityFreq.get(1);
			ObservableList<FrequencyDeviation> frozenFreq = FXCollections.observableArrayList();
			for (Object fd : arr) {
				frozenFreq.add((FrequencyDeviation) fd);
			}
			frozenTable.setItems(frozenFreq);
			Platform.runLater(new Runnable() {
				public void run() {
					if (series2 != null)
						series2.getData().clear();
					for (FrequencyDeviation fd : frozenFreq)
						series2.getData().add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
					frozenGraph.getData().add(series2);
				}
			});

			// closedTable
			arr = allActivityFreq.get(2);
			ObservableList<FrequencyDeviation> closedFreq = FXCollections.observableArrayList();
			for (Object fd : arr) {
				closedFreq.add((FrequencyDeviation) fd);
			}
			closedTable.setItems(closedFreq);
			Platform.runLater(new Runnable() {
				public void run() {
					if (series3 != null)
						series3.getData().clear();
					for (FrequencyDeviation fd : closedFreq)
						series3.getData().add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
					closedGraph.getData().add(series3);
				}
			});

			// rejectedTable
			arr = allActivityFreq.get(3);
			ObservableList<FrequencyDeviation> rejectedFreq = FXCollections.observableArrayList();
			for (Object fd : arr) {
				rejectedFreq.add((FrequencyDeviation) fd);
			}
			rejectedTable.setItems(rejectedFreq);
			Platform.runLater(new Runnable() {
				public void run() {
					if (series4 != null)
						series4.getData().clear();
					for (FrequencyDeviation fd : rejectedFreq)
						series4.getData().add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
					rejectedGraph.getData().add(series4);
				}
			});

			// wdTable
			arr = allActivityFreq.get(4);
			ObservableList<FrequencyDeviation> wdFreq = FXCollections.observableArrayList();
			for (Object fd : arr) {
				wdFreq.add((FrequencyDeviation) fd);
			}
			wdTable.setItems(wdFreq);
			Platform.runLater(new Runnable() {
				public void run() {
					if (series5 != null)
						series5.getData().clear();
					for (FrequencyDeviation fd : wdFreq)
						series5.getData().add(new XYChart.Data<>(String.format("%.2f", fd.getValue()), fd.getFreq()));
					wdGraph.getData().add(series5);
				}
			});
		} catch (Exception e) {
		}
	}
}
