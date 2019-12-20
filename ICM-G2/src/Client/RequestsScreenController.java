package Client;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RequestsScreenController {
	public static RequestsScreenController _ins;

	@FXML
	private Button addFilesButton;
	@FXML
	private TableView<Request> tableView;
	@FXML
	private Pane GeneralViewRequest1;
	@FXML
	private TextArea descArea;
	@FXML
	private TextArea changeArea;
	@FXML
	private TextArea reasonArea;
	@FXML
	private TextArea commentsArea;
	@FXML
	private Pane UserViewsRequest1;
	@FXML
	private Label requestIDLabel;
	@FXML
	private Label systemLabel;
	@FXML
	private Label stageLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private Pane AssesmentMakerPane1;
	@FXML
	private Pane CollegeUserUnderTablePane1;
	@FXML
	private Pane StageManagersPane1;
	@FXML
	private Button StageManagers1;
	@FXML
	private Pane ExecutionerFailure;
	@FXML
	private Pane ComittePane1;
	@FXML
	private Pane TesterPane1;
	@FXML
	private Pane SupervisorPane1;
	@FXML
	private Pane CUserOpenRequest1;
	@FXML
	private TextField filePathTextField;
	@FXML
	private Button uploadFileButton;

	public void initialize() {
		_ins = this;
		// TODO: add CollegeUserUnderTablePane1
		TableSetup();
		RefreshTable();
		new Thread() {
			public void run() {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDateTime now = LocalDateTime.now();
				dateLabel.setText(dtf.format(now));
				userNameLabel.setText(Main.currentUser.getFirstName() + " " + Main.currentUser.getLastName());
			}
		}.start();
	}

	@FXML
	public void addFiles(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File f;
		fileChooser.setTitle("Select a file to add");
		f = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
		if (f!=null)
			filePathTextField.setText(f.getPath());
	}

	@FXML
	public void uploadFileToServer()
	{
		if (filePathTextField.getText()=="")
			return;
		File f = new File(filePathTextField.getText());
		try {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPLOAD,f,tableView.getSelectionModel().getSelectedItem()));
		} catch(Exception e ) {return; }
	}

	public TableView<Request> getTableView() {
		return tableView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void TableSetup() {
		TableColumn<Request, Integer> idColumn = new TableColumn<>("Request ID");
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Request, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
		TableColumn<Request, String> stageColumn = new TableColumn<>("Stage");
		stageColumn.setCellValueFactory(new PropertyValueFactory("currentStage"));
		tableView.getColumns().addAll(idColumn, statusColumn, stageColumn);
		for (TableColumn<Request, ?> col : tableView.getColumns())
			col.setMinWidth(100);
	}

	public void RefreshTable() {
		if (Main.currentUser.getRole() == Enums.Role.College) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESH, ""));
			// TODO: create different message to server, to search College user
		} else if (Main.currentUser.getRole() == Enums.Role.Manager
				|| Main.currentUser.getRole() == Enums.Role.Supervisor) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESH, ""));
		} else {
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.REFRESH, Main.currentUser.getUsername()));
		}
	}

	@FXML
	public void showRequest() {
		try {
			disableAllRequestPans();
			Request r;
			GeneralViewRequest1.setVisible(true);
			UserViewsRequest1.setVisible(true);
			r = tableView.getSelectionModel().getSelectedItem();
			descArea.setText(r.getDescription());
			changeArea.setText(r.getChanges());
			reasonArea.setText(r.getChangeReason());
			commentsArea.setText(r.getComments());
			requestIDLabel.setText("" + r.getId());
			systemLabel.setText(r.getSystem().toString());
			stageLabel.setText(r.getCurrentStage().toString());
			statusLabel.setText(r.getStatus());
			if (Main.currentUser.getRole() == Enums.Role.Supervisor
					|| Main.currentUser.getRole() == Enums.Role.Manager) {
				SupervisorPane1.setVisible(true);
			} else if (Main.currentUser.getRole() != Enums.Role.College) {
				showRequestByStage(r);
			}
		} catch (Exception e) {
		}
	}

	public void showRequestByStage(Request r) {
		switch (r.getCurrentStage()) {
		case Assesment:
			AssesmentMakerPane1.setVisible(true);
			break;
		case Examaning:
			StageManagersPane1.setVisible(true);
			ComittePane1.setVisible(true);
			break;
		case Execution:
			StageManagersPane1.setVisible(true);
			// TODO: how to check if second time?
			break;
		case Testing:
			TesterPane1.setVisible(true);
			break;
		default:
			break;
		}
	}

	public void disableAllRequestPans() {
		GeneralViewRequest1.setVisible(false);
		UserViewsRequest1.setVisible(false);
		SupervisorPane1.setVisible(false);
		AssesmentMakerPane1.setVisible(false);
		StageManagersPane1.setVisible(false);
		ComittePane1.setVisible(false);
		StageManagersPane1.setVisible(false);
		TesterPane1.setVisible(false);
		CUserOpenRequest1.setVisible(false);
	}

}
