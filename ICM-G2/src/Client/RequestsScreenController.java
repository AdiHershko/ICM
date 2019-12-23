package Client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	public static boolean waitForNewRequest;
	public static int newRequestID;

	@FXML
	private Button addFilesButton;
	@FXML
	private Button logoutButton;
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
		if (Main.currentUser.getRole() == Enums.Role.College)
			CollegeUserUnderTablePane1.setVisible(true);

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
		if (f != null)
			filePathTextField.setText(f.getPath());
	}

	@FXML
	public void uploadFileToServer() {
		if (filePathTextField.getText() == "")
			return;
		File f = new File(filePathTextField.getText());
		InputStream is = null;
		BufferedInputStream bis = null;
		byte[] buffer = null;
		try {
			is = new FileInputStream(f.getPath());
			buffer = new byte[(int) f.length()];
			bis = new BufferedInputStream(is);
			bis.read(buffer, 0, buffer.length);
		} catch (IOException e) {
			System.out.println("Error reading file!");
		}
		try {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer,
					tableView.getSelectionModel().getSelectedItem()));
		} catch (Exception e) {
			return;
		}
		try {
			bis.close();
			is.close();
		} catch (Exception e) {
		}
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
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.REFRESHUSERID, Main.currentUser.getUsername()));
		} else if (Main.currentUser.getRole() == Enums.Role.Manager
				|| Main.currentUser.getRole() == Enums.Role.Supervisor) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESH, ""));
			// TODO change for manager and supervisor
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
			r = tableView.getSelectionModel().getSelectedItem();
			if (r == null)
				return;
			GeneralViewRequest1.setVisible(true);
			UserViewsRequest1.setVisible(true);
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
		descArea.clear();
		changeArea.clear();
		reasonArea.clear();
		commentsArea.clear();
	}

	public void uploadFileMessage(boolean status) {
		if (status) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Upload finished");
			alert.setContentText("Upload finished succesfully");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Upload failed");
			alert.setContentText("Could not upload file to server");
			alert.showAndWait();
		}
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Main.currentUser = null;
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();
	}

	@FXML
	void openNewRequestPane(ActionEvent event) {
		disableAllRequestPans();
		GeneralViewRequest1.setVisible(true);
		CUserOpenRequest1.setVisible(true);
		descArea.setEditable(true);
		changeArea.setEditable(true);
		reasonArea.setEditable(true);
		commentsArea.setEditable(true);
	}

	@FXML
	void submitNewRequest(ActionEvent event) {
		// TODO add verify for fields
		// TODO get system - check in AviTipus
		String description = descArea.getText();
		String changes = changeArea.getText();
		String changeReason = reasonArea.getText();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate now = LocalDate.now();
		Request r = new Request(0, Main.currentUser.getUsername(), Enums.SystemENUM.InfoStation, description, changes,
				changeReason, dtf.format(now).toString());
		r.setComments(commentsArea.getText());
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CreateRequest, r));
		waitForNewRequest = false;
		try {
			while (waitForNewRequest == false)
				Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		r.setId(newRequestID);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("New request created! \n Request ID: " + r.getId());
		alert.show();
	}

}
