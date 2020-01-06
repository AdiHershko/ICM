package Client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RequestsScreenController {
	public static Stage tmp_newWindow;
	public static RequestsScreenController _ins;
	public static boolean waitForNewRequest;
	private boolean unActive;
	private boolean isSearch;
	public static int newRequestID;
	public static Request r;
	private ArrayList<String> filesPaths = new ArrayList<String>();
	private Scene report;
	public static Report reportOfRequest;
	private Scene extensionScene;
	Stage newWindow = new Stage();
	public static int saveOrSub = 0;
	private int index;
	private int id = -1;
	public static int maxid=-1;
	public static boolean lock;
	private boolean isUploading;
	private ImageView loadinganim;
	Thread loading;
	@FXML
	private Button addFilesButton;
	@FXML
	private DatePicker datePickerAss;
	@FXML
	private DatePicker DatePickerExec;
	@FXML
	private Button executionSetTime;
	@FXML
	private Button logoutButton;
	@FXML
	private Button setDueDateBTN;
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
	private Label requestorLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private Label AppointTesterLabl;
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
	private Button openAssessmentReportB1;
	@FXML
	private Button FailureReportBtn;
	@FXML
	private Button FailureReportBtn1;
	@FXML
	private Button DeclineRequestBtn;
	@FXML
	private Button AskMoreDataBtn;
	@FXML
	private TextField TesteAppointField;
	@FXML
	private TextField extensionReason;
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
	private TextField setDueTime1;
	@FXML
	private Button extentionAskBtn;
	@FXML
	private ChoiceBox<SystemENUM> choiceBox = new ChoiceBox<SystemENUM>();
	@FXML
	private Label timeCreatedLabel;
	@FXML
	private Label stageDate1;
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
	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private CheckBox unActiveCheckBox;
	@FXML
	private Pane mainRequestPane;
	@FXML
	private Button viewFilesButton;

	public void initialize() {
		datePickerAss.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		_ins = this;
		isUploading=false;
		DatePickerExec.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		loadinganim = new ImageView("loading.gif");
		loadinganim.setX(mainRequestPane.getWidth() / 2 + 400);
		loadinganim.setY(mainRequestPane.getHeight() / 2);
		loadinganim.setScaleX(0.2);
		loadinganim.setScaleY(0.2);
		loadinganim.setVisible(false);
		Platform.runLater(() -> mainRequestPane.getChildren().add(loadinganim));
		if (Main.currentUser.getRole() == Enums.Role.College)
			CollegeUserUnderTablePane1.setVisible(true);

		TableSetup();
		RefreshTable();
		new Thread() {
			public void run() {
				Platform.runLater(new Runnable() // wont work without this shit
				{
					public void run() {
						userNameLabel.setText(Main.currentUser.getFirstName() + " " + Main.currentUser.getLastName());
						}
					});
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
	public void search(ActionEvent event) {
		String textFromUser = searchField.getText();
		if (textFromUser.equals("")) {
			isSearch = false;
		}
		else {
			try {
				id = Integer.parseUnsignedInt(textFromUser);
				isSearch = true;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Not a number");
				alert.setContentText("You didn't enter a number");
				alert.showAndWait();
				return;
			}
		}
		unVisibleRequestPane();
		RefreshTable();
	}

	@FXML
	public void showUnactive(ActionEvent event) {
		if (unActiveCheckBox.isSelected()) {
			unActive = true;
		}
		else {
			unActive = false;
		}
		RefreshTable();
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
			if (tableView.getSelectionModel().getSelectedItem()==null)
			{
				if (isUploading==false)
				{
					Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID));

				while (maxid==-1)
				{
					try{
						Thread.sleep(100);
					}catch(InterruptedException e) { }
				}
				isUploading=true;
				}
				Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer,
						maxid+1));
			}
			else {
				Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer,
					tableView.getSelectionModel().getSelectedItem().getId()));
			}
			filePathTextField.setText("");
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
		if (filePathTextField.getText().equals(""))
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
					new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer, r.getId()));
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
			col.setMinWidth(95);
	}

	public void RefreshTable() {
		lock = true;
		loading = new Thread() {
			public void run() {
				loadinganim.setVisible(true);
				while (lock) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						lock = false;
						loadinganim.setVisible(false);
						return;
					}
				}
			}
		};
		loading.start();
		if (Main.currentUser.getRole() == Enums.Role.College) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHCOLLEGE,
					Main.currentUser.getUsername(), id, isSearch, unActive));
		}
		else if (Main.currentUser.getRole() == Enums.Role.Manager
				|| Main.currentUser.getRole() == Enums.Role.Supervisor) {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHMAN,
					Main.currentUser.getUsername(), id, isSearch, unActive));
		}
		else {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHIS,
					Main.currentUser.getUsername(), id, isSearch, unActive));
		}
	}

	public void stopLoading() {
		loading.interrupt();
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
			String temp = r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage())]
					.getPlannedDueDate();
			if (Main.currentUser.getRole() == Enums.Role.College) {
				if (temp != null) {
					temp = new DateTime(temp).toString("dd/MM/yyyy");
					stageDate1.setText("Current stage due date: " + temp);
				}
				else {
					stageDate1.setText("Current stage due date: date not yet updated.");
				}
			}
			else {
				stageDate1.setVisible(false);
				if (temp != null) {
					temp = new DateTime(temp).toString("dd/MM/yyyy");
					setDueDateBTN.setVisible(false);
					executionSetTime.setVisible(false);
					if (r.getCurrentStage() == Enums.RequestStageENUM.Assesment) {
						datePickerAss.setVisible(false);
						setDueTime1.setVisible(true);
						setDueTime1.setEditable(false);
						setDueTime1.setText(temp);
					}
					else {
						DatePickerExec.setVisible(false);
						dueDate.setVisible(true);
						dueDate.setEditable(false);
						dueDate.setText(temp);
					}
				}
				else {
					setDueDateBTN.setVisible(true);
					if(r.getCurrentStage() == Enums.RequestStageENUM.Assesment) {
						setDueTime1.setVisible(false);
						datePickerAss.setVisible(true);
					}
					if(r.getCurrentStage() == Enums.RequestStageENUM.Execution) {
						dueDate.setVisible(false);
						DatePickerExec.setVisible(true);
					}

				}
			}
			timeCreatedLabel.setText("Creation time: " + r.getDate().toString("dd/MM/yyyy hh:mm a"));
			requestorLabel.setText("Requestor: " + r.getRequestorID());
			systemLabel.setText(r.getSystem().toString());
			stageLabel.setText(r.getCurrentStage().toString());
			statusLabel.setText(r.getStatus().toString());
			filePathTextField.setText("");
			uploadedFilesLabel.setText("Uploaded files: none");
			showUploadedFiles(r);
			if (Main.currentUser.getRole() == Enums.Role.Supervisor
					|| Main.currentUser.getRole() == Enums.Role.Manager) {
				SupervisorPane1.setVisible(true);
				if (r.getCurrentStageEnum() == Enums.RequestStageENUM.Closing
						&& (r.getStatus() == Enums.RequestStatus.Active
								|| r.getStatus() == Enums.RequestStatus.Rejected))
					changeStatus.setVisible(true);
				else
					changeStatus.setVisible(false);
				if (r.getStages()[4].getReportFailure() == null)
					FailureReportBtn1.setVisible(false);
				else
					FailureReportBtn1.setVisible(true);
			}
			else if (Main.currentUser.getRole() != Enums.Role.College) {
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
			if (Main.currentUser.getRole() == Enums.Role.CommitteMember) {
				DeclineRequestBtn.setVisible(false);
				AskMoreDataBtn.setVisible(false);
				AppointTesterLabl.setVisible(false);
				SaveTesterApointBtn.setVisible(false);
				testerCB.setVisible(false);
				ApproveStageBtn.setVisible(false);
			}
			else {
				Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetComitte));
				DeclineRequestBtn.setVisible(true);
				AskMoreDataBtn.setVisible(true);
				AppointTesterLabl.setVisible(true);
				SaveTesterApointBtn.setVisible(true);
				testerCB.setVisible(true);
				ApproveStageBtn.setVisible(true);
			}
			break;
		case Execution:
			StageManagersPane1.setVisible(true);
			ExecutionerFailure.setVisible(true);
			if (r.getStages()[4].getReportFailure() == null)
				FailureReportBtn.setVisible(false);
			else
				FailureReportBtn.setVisible(true);
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
		if (testerCB.getItems().isEmpty()) {
			for (String s : committe) {
				testerCB.getItems().add(s);
			}
		}
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					testerCB.getSelectionModel().select(r.getStages()[4].getStageMembers().get(1));
				} catch (Exception e) {
				}
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
		filePathTextField.setVisible(false);
		uploadFileButton.setVisible(false);
		addFilesButton.setVisible(false);
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
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Upload failed");
			alert.setContentText("Could not upload file to server");
			alert.showAndWait();
		}
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
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
	void openNewRequestPane(ActionEvent event) {
		disableAllRequestPans();
		choiceBox.getSelectionModel().clearSelection();
		tableView.getSelectionModel().clearSelection();
		GeneralViewRequest1.setVisible(true);
		CUserOpenRequest1.setVisible(true);
		descArea.setEditable(true);
		changeArea.setEditable(true);
		reasonArea.setEditable(true);
		commentsArea.setEditable(true);
		filePathTextField.setVisible(true);
		uploadFileButton.setVisible(true);
		addFilesButton.setVisible(true);
	}

	@FXML
	void submitNewRequest(ActionEvent event) {
		if (!isUploading)
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID)); //to delete folder of unfinished request
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
		alert.showAndWait();
		RefreshTable();
		disableAllRequestPans();
		isUploading=false;
		maxid=-1;
	}

	public void openAssessmentReportFunc(Report r) {
		reportOfRequest = r;
		Parent root = null;
		newWindow=new Stage();
		index = tableView.getSelectionModel().getSelectedIndex();
		try {
			root = FXMLLoader.load(getClass().getResource("2.2-ReportScreen.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		report = new Scene(root);
		newWindow.setTitle("Report");
		newWindow.setScene(report);
		newWindow.initOwner((Stage) (openAssessmentReportB.getScene().getWindow()));
		newWindow.initModality(Modality.WINDOW_MODAL);
		newWindow.setResizable(false);
		newWindow.setOnCloseRequest(e->newWindow.close());
		newWindow.show();

	}

	public void AssessmentReportPage() {
		if (Main.currentUser.getRole() != Enums.Role.Supervisor) {
			if (setDueTime1.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("must set time stage before starting stage!");
				alert.showAndWait();
				return;
			}
		}
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
	public void closeExtraWindowOnly() {
		newWindow.close();
	}
	public void closeExtraWindowSub() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Great!");
		alert.setContentText("Report submitted successfully!");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);

	}

	public void closeExtraWindowExt() {
		newWindow.close();
		RefreshTable();
		tableView.getSelectionModel().select(index);
	}

	public void showUploadedFiles(Request r) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				filesPaths = new ArrayList<String>();
			}
		}).start();
		//Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETUSERFILES, r));
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
		window.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
		window.initModality(Modality.WINDOW_MODAL);
		window.show();
	}

	@FXML
	void statusChange(ActionEvent event) {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStatus, r));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm!");
		alert.setHeaderText("Request closed!");
		alert.setContentText("Request number " + r.getId() + " closed");
		alert.show();
		RefreshTable();
		unVisibleRequestPane();
	}

	@FXML
	void FreezeUnfreeze(ActionEvent event) {
		boolean frozen = false, unfrozen = false;
		if (r.getStatus() == Enums.RequestStatus.Active || r.getStatus() == Enums.RequestStatus.Rejected) {
			frozen = true;
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Freeze, "" + r.getId()));
		}
		else if (r.getStatus() == Enums.RequestStatus.Frozen && r.getStages()[5].getReportFailure() == null) {
			unfrozen = true;
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Unfreeze, "" + r.getId()));
		}
		else if (r.getStatus() == Enums.RequestStatus.Frozen) {
			unfrozen = true;
			Main.client.handleMessageFromClientUI(
					new ClientServerMessage(Enums.MessageEnum.UnFreezeRejected, "" + r.getId()));
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
			alert.setContentText("Request number " + r.getId() + " changed to unfrozen");
			alert.show();
		}
		RefreshTable();
		unVisibleRequestPane();
		return;
	}

	@FXML
	void saveChanges(ActionEvent event) {
		descArea.setEditable(false);
		changeArea.setEditable(false);
		reasonArea.setEditable(false);
		commentsArea.setEditable(false);
		r.setDescription(descArea.getText());
		r.setChanges(changeArea.getText());
		r.setChangeReason(reasonArea.getText());
		r.setComments(commentsArea.getText());
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateRequestDetails, r));
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
		disableAllRequestPans();
	}

	@FXML
	void ApproveStageBtn(ActionEvent event) {
		if (r.getCurrentStage() == Enums.RequestStageENUM.Execution) {
			if (dueDate.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("must set time stage before ending stage!");
				alert.showAndWait();
				return;
			}
		}
		if (r.getCurrentStage() == Enums.RequestStageENUM.Examaning && r.getStages()[4].getStageMembers().size() < 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing tester");
			alert.setHeaderText("Missing tester");
			alert.setContentText("You need to appoint a tester before entering next stage");
			alert.show();
		}

		else {
			Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStage, r.getId()));
			unVisibleRequestPane();
			RefreshTable();
		}
	}

	@FXML
	void AskMoreData(ActionEvent event) {
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.downStage, r.getId()));
		RefreshTable();
		unVisibleRequestPane();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Request for more data:");
		alert.setContentText("Request for more data was sent");
		alert.show();
	}

	@FXML
	void DeclineRequest(ActionEvent event) {
		Main.client
				.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.declineRequest, "" + r.getId()));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Request declined!");
		alert.setHeaderText("Request declined!");
		alert.setContentText("Request number " + r.getId() + " declined, waiting for supervisor confirmation");
		alert.show();
		RefreshTable();
		unVisibleRequestPane();
	}

	@FXML
	void ReportFailure(ActionEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			public void run() {
				Pane root = null;
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
				newWindow.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
				newWindow.initModality(Modality.WINDOW_MODAL);
				tmp_newWindow = newWindow;
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
		r.getStages()[4].getStageMembers().add(tester);
		Main.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.AppointStageHandlers, r.getId(), 4, tester));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm tester");
		alert.setContentText("Request number " + r.getId() + " tester is " + tester);
		alert.show();
	}

	@FXML
	void ViewReport(ActionEvent event) {
		AssessmentReportPage();
	}

	@FXML
	void viewFailureReport(ActionEvent event) {
		Parent root = null;
		index = tableView.getSelectionModel().getSelectedIndex();
		newWindow = new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource("2.1-ExecutionFailures.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		report = new Scene(root);
		newWindow.setTitle("Execution Failures Report");
		newWindow.setScene(report);
		newWindow.setResizable(false);
		newWindow.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
		newWindow.initModality(Modality.WINDOW_MODAL);
		tmp_newWindow = newWindow;
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

	public void reportMsgAndRef(String[] result) {
		tmp_newWindow.close();
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.TesterRep, result));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Failure report sent");
		alert.setContentText("Failure report sent succesfully");
		alert.showAndWait();
		RefreshTable();
		unVisibleRequestPane();
	}

	@FXML
	public void setAssDueTime(ActionEvent event) {
		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		index = tableView.getSelectionModel().getSelectedIndex();
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (datePickerAss.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("must fill due time!");
			alert.showAndWait();
			return;
		}
		else {
			if (datePickerAss.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("due date too soon!");
				alert.showAndWait();
				return;
			}
			temp = (datePickerAss.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
			if (r.getCurrentStageEnum() == Enums.RequestStageENUM.Assesment) {
				Main.client.handleMessageFromClientUI(
						new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE, r.getId(), dt.toString()));
			}

		}
	}

	@FXML
	public void setExecDueTime(ActionEvent event) {
		org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt;
		String temp;
		index = tableView.getSelectionModel().getSelectedIndex();
		java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (DatePickerExec.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("must fill due time!");
			alert.showAndWait();
			return;
		}
		else {
			if (DatePickerExec.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("due date too soon!");
				alert.showAndWait();
				return;
			}
			temp = (DatePickerExec.getValue()).format(df);
			dt = dtf.parseDateTime(temp);
			if (r.getCurrentStageEnum() == Enums.RequestStageENUM.Execution) {
				Main.client.handleMessageFromClientUI(
						new ClientServerMessage(Enums.MessageEnum.SETEXECMDATE, r.getId(), dt.toString()));
			}

		}
	}

	@FXML
	void extentionAsk(ActionEvent event) {
		if (r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStageEnum())].getIsExtended()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("extension already given for this stage in that request!");
			alert.showAndWait();
			return;

		}
		if (r.getCurrentStage() == Enums.RequestStageENUM.Assesment) {
			if (setDueTime1.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("must set time stage before asking for extension!!");
				alert.showAndWait();
				return;
			}
		}
		else {
			if (dueDate.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("must set time stage before asking for extension!!");
				alert.showAndWait();
				return;
			}
		}
		DateTime dt = new DateTime(
				r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStageEnum())]
						.getPlannedDueDate());
		if (dt.minusDays(3).isAfterNow()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setContentText("can't ask for extension more than 3 days to due date!");
			alert.showAndWait();
			return;
		}
		else {

			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("ExtensionRequest.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			extensionScene = new Scene(root);
			newWindow = new Stage();
			newWindow.setTitle("Ask for extension");
			newWindow.setScene(extensionScene);
			newWindow.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.setResizable(false);
			newWindow.show();
		}
	}

	public void dateAlertRefresh() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Date updated");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);
		showRequest();
	}

	public void cannotUpdateStage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Can't update stage.\nNobody is appointed for next stage.\nMessage sent to supervisor.");
		alert.showAndWait();
		tableView.getSelectionModel().select(index);
		showRequest();
	}

	@FXML
	public void viewFiles(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("ShowFilesScreen.fxml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		Scene filesView = new Scene(root);
		newWindow.setTitle("User files");
		newWindow.setScene(filesView);
		newWindow.setResizable(false);
		newWindow.show();

	}
}
