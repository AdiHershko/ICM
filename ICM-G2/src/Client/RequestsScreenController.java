package Client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Enums.SystemENUM;
import Common.Report;
import Common.Request;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	public static Stage tmp_newWindow;
	public static RequestsScreenController _ins;
	public static boolean waitForNewRequest;
	public static int newRequestID;
	public static Request r;
	private ArrayList<String> filesPaths = new ArrayList<String>();
	private Scene report;
	public static Report reportOfRequest;
	Stage newWindow = new Stage();
	public static int saveOrSub = 0;
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
	@FXML
	private Button openAssessmentReportB;
	@FXML
	private Button FailureReportBtn;
	@FXML
	private Button DeclineRequestBtn;
	@FXML
	private Button AskMoreDataBtn;
	@FXML
	private TextField TesteAppointField;
	@FXML
	private Button SaveTesterApointBtn;
	@FXML
	private Button ViewReportBtn;
	@FXML
	private Button ReportFailureBtn;
	@FXML
	private Button ApproveStageBtn;
	@FXML
	private Button submitBtn;
	@FXML
	private TextField dueDate;
	@FXML
	private Button extentionAskBtn;
	@FXML
	private ChoiceBox<SystemENUM> choiceBox = new ChoiceBox<SystemENUM>();
	@FXML
	private Label timeCreatedLabel;
	@FXML
	private Label uploadedFilesLabel;
	@FXML
	private Button stagesSettingsButton;
	@FXML
	private Button saveBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button changeStatus;
	@FXML
	private Button freezeUnfreeze;
	@FXML
	private Button managerBackBtn;
	@FXML
	private ChoiceBox<String> testerCB = new ChoiceBox<String>();
	@FXML
	private TextField exectuionReport;
	private int index;

	public void initialize() {
		_ins = this;
		if (Main.currentUser.getRole() == Enums.Role.College)
			CollegeUserUnderTablePane1.setVisible(true);

		TableSetup();
		RefreshTable();
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

		choiceBox.getItems().add(SystemENUM.InfoStation);
		choiceBox.getItems().add(SystemENUM.Moodle);
		choiceBox.getItems().add(SystemENUM.Library);
		choiceBox.getItems().add(SystemENUM.Computers);
		choiceBox.getItems().add(SystemENUM.Labs);
		choiceBox.getItems().add(SystemENUM.Site);
		
		if (Main.currentUser.getRole() == Enums.Role.Manager) {
			managerBackBtn.setVisible(true);
		}
		else {
			managerBackBtn.setVisible(false);
		}
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

	public void uploadFileToServer_NewRequest(Request r) {
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
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer, r));
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
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHMAN));
		} else {
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.REFRESH, Main.currentUser.getUsername()));
		}
	}

	@FXML
	public void showRequest() {
		try {
			disableAllRequestPans();
			r = tableView.getSelectionModel().getSelectedItem();
			if (r == null)
				return;
			Main.currentRequest = r;
			GeneralViewRequest1.setVisible(true);
			UserViewsRequest1.setVisible(true);
			descArea.setText(r.getDescription());
			changeArea.setText(r.getChanges());
			reasonArea.setText(r.getChangeReason());
			commentsArea.setText(r.getComments());
			requestIDLabel.setText("" + r.getId());
			timeCreatedLabel.setText("Creation time: " + r.getDate().toString("dd/MM/yyyy hh:mm a"));
			systemLabel.setText(r.getSystem().toString());
			stageLabel.setText(r.getCurrentStage().toString());
			statusLabel.setText(r.getStatus());
			filePathTextField.setText("");
			uploadedFilesLabel.setText("Uploaded files: none");
			showUploadedFiles(r);
			if (Main.currentUser.getRole() == Enums.Role.Supervisor
					|| Main.currentUser.getRole() == Enums.Role.Manager) {
				SupervisorPane1.setVisible(true);
				if (r.getCurrentStageEnum() != Enums.RequestStageENUM.Closing)
					changeStatus.setVisible(false);
				else
					changeStatus.setVisible(true);
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
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetComitte));
			break;
		case Execution:
			StageManagersPane1.setVisible(true);
			if (r.getStages()[4].getReportFailure() == null)
				ExecutionerFailure.setVisible(false);
			else
				ExecutionerFailure.setVisible(true);
			break;
		case Testing:
			StageManagersPane1.setVisible(true);
			TesterPane1.setVisible(true);
			break;
		default:
			break;
		}
	}

	public void loadComitteeMembers(List<String> committe) {
		ArrayList<String> list = (ArrayList<String>) committe;
		if (testerCB.getItems().isEmpty()) {
			for (String s : committe) {
				testerCB.getItems().add(s);
			}
		}
		Platform.runLater(new Runnable()
				{
					public void run() {
						testerCB.getSelectionModel().select(r.getStages()[4].getStageMembers().get(1));//TODO maybe select if already in
					}
				});
		testerCB.setVisible(true);
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
		testerCB.getSelectionModel().clearSelection();
		testerCB.setVisible(false);
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
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.logOut, Main.currentUser.getUsername()));
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
	void openNewRequestPane(ActionEvent event) {
		disableAllRequestPans();
		choiceBox.getSelectionModel().clearSelection();
		GeneralViewRequest1.setVisible(true);
		CUserOpenRequest1.setVisible(true);
		descArea.setEditable(true);
		changeArea.setEditable(true);
		reasonArea.setEditable(true);
		commentsArea.setEditable(true);
	}

	@FXML
	void submitNewRequest(ActionEvent event) {

		if (descArea.getText().equals("") || changeArea.getText().equals("") || reasonArea.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Empty required fields!");
			alert.show();
			return;
		}
		if (choiceBox.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("No system selected!");
			alert.show();
			return;
		}
		String description = descArea.getText();
		String changes = changeArea.getText();
		String changeReason = reasonArea.getText();
		SystemENUM system = choiceBox.getValue();
		Request r = new Request(0, Main.currentUser.getUsername(), system, description, changes, changeReason,
				new DateTime());
		String comments = commentsArea.getText();
		r.setComments(comments);
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CreateRequest, r));
		waitForNewRequest = false;
		try {
			while (waitForNewRequest == false) {
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
		}
		r.setId(newRequestID);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("New request created! \n Request ID: " + r.getId());
		uploadFileToServer_NewRequest(r);
		alert.showAndWait();
		RefreshTable();
		disableAllRequestPans();
	}

	public void openAssessmentReportFunc(Report r) {
		reportOfRequest = r;
		Parent root = null;
		index = tableView.getSelectionModel().getSelectedIndex();
		try {
			root = FXMLLoader.load(getClass().getResource("2.2-ReportScreen.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		report = new Scene(root);
		newWindow.setTitle("Report");
		newWindow.setScene(report);
		newWindow.setResizable(false);
		newWindow.show();
		
	}

	public void AssessmentReportPage() {
		int temp = tableView.getSelectionModel().getSelectedItem().getId();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SearchReport, temp));
	}

	public void closeExtraWindow() {

		newWindow.close();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Great!");
		alert.setContentText("Report saved successfully!");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);

	}

	public void showUploadedFiles(Request r) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				filesPaths = new ArrayList<String>();
			}
		}).start();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETUSERFILES, r));
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {

		}
		if (filesPaths.size() == 0) {
			uploadedFilesLabel.setText("Uploaded files: none");
			return;
		}
		filesPaths.remove(0); // removing folder path
		uploadedFilesLabel.setText("Uploaded files: ");
		for (String s : filesPaths) // THE MOST ARABIC CODE I HAVE EVER WRITTEN
		{
			char[] ch = s.toCharArray();
			for (int i = 0; i < ch.length; i++)
				if (ch[i] == '\\')
					ch[i] = '/';
			String str = String.valueOf(ch);
			String[] str2 = str.split("/");

			uploadedFilesLabel.setText(uploadedFilesLabel.getText() + str2[str2.length - 1] + ",");
		}
	}

	public void setFilePaths(ArrayList<String> filePaths) {
		this.filesPaths = filePaths;
	}

	@FXML
	public void stageSettingsScreen(ActionEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("2.3-SupervisorRequestSettingsScreen.fxml"));
		} catch (IOException e) {
		}
		Scene requests = new Scene(root);
		Stage window = new Stage();
		window.setScene(requests);
		window.setResizable(false);
		window.show();
	}

	@FXML
	void statusChange(ActionEvent event) {
		String s = r.getId() + "-" + r.getStatus();
		System.out.println(s);
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStatus, s));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm!");
		alert.setHeaderText("Request closed!");
		alert.setContentText("Request number " + r.getId() + " closed");
		alert.show();
		RefreshTable();
	}

	@FXML
	void FreezeUnfreeze(ActionEvent event) {
		boolean frozen = false, unfrozen = false;
		if (r.getStatus().equals("Active")) {
			frozen = true;
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Freeze, "" + r.getId()));
		} else if (r.getStatus().equals("Frozen")) {
			unfrozen = true;
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Unfreeze, "" + r.getId()));
		}
		RefreshTable();
		if (frozen) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Confirm!");
			alert.setHeaderText("Changed to Frozen!");
			alert.setContentText("Request number " + r.getId() + " changed to frozen");
			alert.show();
		}
		if (unfrozen) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Confirm!");
			alert.setHeaderText("Changed to Active!");
			alert.setContentText("Request number " + r.getId() + " changed to active");
			alert.show();
		}
		return;
	}

	@FXML
	void saveChanges(ActionEvent event) {
		descArea.setEditable(false);
		changeArea.setEditable(false);
		reasonArea.setEditable(false);
		commentsArea.setEditable(false);
		String desc, change, reason, comment, res;
		desc = descArea.getText();
		change = changeArea.getText();
		reason = reasonArea.getText();
		comment = commentsArea.getText();
		res = "" + r.getId() + "-" + desc + "-" + change + "-" + reason + "-" + comment + "-" + "abc";
		if (desc.isEmpty())
			desc = " ";
		if (change.isEmpty())
			change = " ";
		if (reason.isEmpty())
			reason = " ";
		if (comment.isEmpty()) {
			comment = " ";
		}
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateRequestDetails, res));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edited!");
		alert.setHeaderText("Request edited!");
		alert.setContentText("Request number " + r.getId() + " edited");
		alert.show();
		RefreshTable();
	}

	@FXML
	void editChanges(ActionEvent event) {
		descArea.setEditable(true);
		changeArea.setEditable(true);
		reasonArea.setEditable(true);
		commentsArea.setEditable(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit!");
		alert.setHeaderText("You can edit!");
		alert.setContentText("Request number " + r.getId() + " can be edited");
		alert.show();

	}

	public void unVisibleRequestPane() {
		GeneralViewRequest1.setVisible(false);
	}
	@FXML
	void ApproveStageBtn(ActionEvent event) {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStage, r.getId()));
		RefreshTable();
		unVisibleRequestPane();
	}

	@FXML
	void AskMoreData(ActionEvent event) {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.downStage, r.getId()));
		RefreshTable();
		unVisibleRequestPane();
	}

	@FXML
	void DeclineRequest(ActionEvent event) {
		System.out.println("i want to " + r.getId());
		Main.client
				.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.declineRequest, "" + r.getId()));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Request declined!");
		alert.setHeaderText("Request declined!");
		alert.setContentText("Request number " + r.getId() + " declined, waiting for supervisor confirmation");
		alert.show();
		RefreshTable();
	}

	@FXML
	void ReportFailure(ActionEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			public void run() {
				Pane root=null;
				try { // loading fxml file
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("2.1-ExecutionFailures.fxml"));
					root = loader.load();
					loader.getController(); // saving controller class
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

				Scene s = new Scene(root);
				newWindow.setScene(s);
				newWindow.setTitle("Execution Failures Report");
				newWindow.setResizable(false);
				tmp_newWindow=newWindow;
				newWindow.show();
			}
		});
	}

    @FXML
    void SaveTesterApoint(ActionEvent event) {
    	if (testerCB.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("No tester selected!");
			alert.show();
			return;
		}
		String tester = testerCB.getValue();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.AppointStageHandlers, r.getId(),4,tester));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm tester");
		alert.setContentText("Request number " + r.getId() + " tester is "+tester);
		alert.show();
		RefreshTable();
    }

	@FXML
	void ViewReport(ActionEvent event) {
		AssessmentReportPage();
	}

	@FXML
	void extentionAsk(ActionEvent event) {

	}

	@FXML
	void viewFailureReport(ActionEvent event) {
		Parent root = null;
		index = tableView.getSelectionModel().getSelectedIndex();
		try {
			root = FXMLLoader.load(getClass().getResource("2.1-ExecutionFailures.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		report = new Scene(root);
		newWindow.setTitle("Execution Failures Report");
		newWindow.setScene(report);
		newWindow.setResizable(false);
		tmp_newWindow=newWindow;
		newWindow.show();
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
	
	public static void reportMsgAndRef() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Failure report sent");
		alert.setContentText("Failure report sent succesfully");
		alert.showAndWait();
		_ins.RefreshTable();
		tmp_newWindow.close();
		
	}
}
