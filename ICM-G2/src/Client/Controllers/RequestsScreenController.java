package Client.Controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import Client.ClientMain;

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

// TODO: Auto-generated Javadoc
/**
 * The Class RequestsScreenController.
 */
public class RequestsScreenController {
	
	/** The tmp new window. */
	public static Stage tmp_newWindow;
	
	/** The ins. */
	public static RequestsScreenController _ins;
	
	/** The wait for new request. */
	public static boolean waitForNewRequest;
	
	/** The un active. */
	private boolean unActive;
	
	/** The is search. */
	private boolean isSearch;
	
	/** The new request ID. */
	public static int newRequestID;
	
	/** The r. */
	public static Request r;
	
	/** The files paths. */
	private ArrayList<String> filesPaths = new ArrayList<String>();
	
	/** The report. */
	private Scene report;
	
	/** The report of request. */
	public static Report reportOfRequest;
	
	/** The extension scene. */
	private Scene extensionScene;
	
	/** The new window. */
	Stage newWindow = new Stage();
	
	/** The save or sub. */
	public static int saveOrSub = 0;
	
	/** The index. */
	private int index;
	
	/** The id. */
	private int id = -1;
	
	/** The maxid. */
	public static int maxid = -1;
	
	/** The lock. */
	public static boolean lock;
	
	/** The is uploading. */
	private boolean isUploading;
	
	/** The loadinganim. */
	private ImageView loadinganim;
	
	/** The loading. */
	Thread loading;
	
	/** The add files button. */
	@FXML
	private Button addFilesButton;
	
	/** The date picker ass. */
	@FXML
	private DatePicker datePickerAss;
	
	/** The Date picker exec. */
	@FXML
	private DatePicker DatePickerExec;
	
	/** The execution set time. */
	@FXML
	private Button executionSetTime;
	
	/** The logout button. */
	@FXML
	private Button logoutButton;
	
	/** The set due date BTN. */
	@FXML
	private Button setDueDateBTN;
	
	/** The table view. */
	@FXML
	private TableView<Request> tableView;
	
	/** The General view request 1. */
	@FXML
	private Pane GeneralViewRequest1;
	
	/** The desc area. */
	@FXML
	private TextArea descArea;
	
	/** The change area. */
	@FXML
	private TextArea changeArea;
	
	/** The reason area. */
	@FXML
	private TextArea reasonArea;
	
	/** The comments area. */
	@FXML
	private TextArea commentsArea;
	
	/** The User views request 1. */
	@FXML
	private Pane UserViewsRequest1;
	
	/** The request ID label. */
	@FXML
	private Label requestIDLabel;
	
	/** The system label. */
	@FXML
	private Label systemLabel;
	
	/** The stage label. */
	@FXML
	private Label stageLabel;
	
	/** The status label. */
	@FXML
	private Label statusLabel;
	
	/** The date label. */
	@FXML
	private Label dateLabel;
	
	/** The requestor label. */
	@FXML
	private Label requestorLabel;
	
	/** The user name label. */
	@FXML
	private Label userNameLabel;
	
	/** The Appoint tester labl. */
	@FXML
	private Label AppointTesterLabl;
	
	/** The Assesment maker pane 1. */
	@FXML
	private Pane AssesmentMakerPane1;
	
	/** The College user under table pane 1. */
	@FXML
	private Pane CollegeUserUnderTablePane1;
	
	/** The Stage managers pane 1. */
	@FXML
	private Pane StageManagersPane1;
	
	/** The Stage managers 1. */
	@FXML
	private Button StageManagers1;
	
	/** The Executioner failure. */
	@FXML
	private Pane ExecutionerFailure;
	
	/** The Comitte pane 1. */
	@FXML
	private Pane ComittePane1;
	
	/** The Tester pane 1. */
	@FXML
	private Pane TesterPane1;
	
	/** The Supervisor pane 1. */
	@FXML
	private Pane SupervisorPane1;
	
	/** The C user open request 1. */
	@FXML
	private Pane CUserOpenRequest1;
	
	/** The file path text field. */
	@FXML
	private TextField filePathTextField;
	
	/** The upload file button. */
	@FXML
	private Button uploadFileButton;
	
	/** The open assessment report B. */
	@FXML
	private Button openAssessmentReportB;
	
	/** The open assessment report B 1. */
	@FXML
	private Button openAssessmentReportB1;
	
	/** The Failure report btn. */
	@FXML
	private Button FailureReportBtn;
	
	/** The Failure report btn 1. */
	@FXML
	private Button FailureReportBtn1;
	
	/** The Decline request btn. */
	@FXML
	private Button DeclineRequestBtn;
	
	/** The Ask more data btn. */
	@FXML
	private Button AskMoreDataBtn;
	
	/** The Teste appoint field. */
	@FXML
	private TextField TesteAppointField;
	
	/** The extension reason. */
	@FXML
	private TextField extensionReason;
	
	/** The Save tester apoint btn. */
	@FXML
	private Button SaveTesterApointBtn;
	
	/** The View report btn. */
	@FXML
	private Button ViewReportBtn;
	
	/** The Report failure btn. */
	@FXML
	private Button ReportFailureBtn;
	
	/** The Approve stage btn. */
	@FXML
	private Button ApproveStageBtn;
	
	/** The submit btn. */
	@FXML
	private Button submitBtn;
	
	/** The due date. */
	@FXML
	private TextField dueDate;
	
	/** The set due time 1. */
	@FXML
	private TextField setDueTime1;
	
	/** The extention ask btn. */
	@FXML
	private Button extentionAskBtn;
	
	/** The choice box. */
	@FXML
	private ChoiceBox<SystemENUM> choiceBox = new ChoiceBox<SystemENUM>();
	
	/** The time created label. */
	@FXML
	private Label timeCreatedLabel;
	
	/** The stage date 1. */
	@FXML
	private Label stageDate1;
	
	/** The uploaded files label. */
	@FXML
	private Label uploadedFilesLabel;
	
	/** The stages settings button. */
	@FXML
	private Button stagesSettingsButton;
	
	/** The save btn. */
	@FXML
	private Button saveBtn;
	
	/** The edit btn. */
	@FXML
	private Button editBtn;
	
	/** The change status. */
	@FXML
	private Button changeStatus;
	
	/** The freeze unfreeze. */
	@FXML
	private Button freezeUnfreeze;
	
	/** The manager back btn. */
	@FXML
	private Button managerBackBtn;
	
	/** The tester CB. */
	@FXML
	private ChoiceBox<String> testerCB = new ChoiceBox<String>();
	
	/** The exectuion report. */
	@FXML
	private TextField exectuionReport;
	
	/** The search field. */
	@FXML
	private TextField searchField;
	
	/** The search button. */
	@FXML
	private Button searchButton;
	
	/** The un active check box. */
	@FXML
	private CheckBox unActiveCheckBox;
	
	/** The main request pane. */
	@FXML
	private Pane mainRequestPane;
	
	/** The view files button. */
	@FXML
	private Button viewFilesButton;

	/**
	 * Initialize.
	 */
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
		isUploading = false;
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
		if (ClientMain.currentUser.getRole() == Enums.Role.College)
			CollegeUserUnderTablePane1.setVisible(true);

		TableSetup();
		RefreshTable();
		new Thread() {
			public void run() {
				Platform.runLater(new Runnable() // wont work without this shit
				{
					public void run() {
						userNameLabel.setText(ClientMain.currentUser.getFirstName() + " " + ClientMain.currentUser.getLastName());
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

		if (ClientMain.currentUser.getRole() == Enums.Role.Manager) {
			managerBackBtn.setVisible(true);
		} else {
			managerBackBtn.setVisible(false);
		}
	}

	/**
	 * Search.
	 *
	 * @param event the event
	 */
	@FXML
	public void search(ActionEvent event) {
		String textFromUser = searchField.getText();
		if (textFromUser.equals("")) {
			isSearch = false;
		} else {
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

	/**
	 * Show unactive.
	 *
	 * @param event the event
	 */
	@FXML
	public void showUnactive(ActionEvent event) {
		if (unActiveCheckBox.isSelected()) {
			unActive = true;
		} else {
			unActive = false;
		}
		RefreshTable();
	}

	/**
	 * Adds the files.
	 *
	 * @param event the event
	 */
	@FXML
	public void addFiles(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File f;
		fileChooser.setTitle("Select a file to add");
		f = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
		if (f != null)
			filePathTextField.setText(f.getPath());
	}

	/**
	 * Upload file to server.
	 */
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
			if (tableView.getSelectionModel().getSelectedItem() == null) {
				if (isUploading == false) {
					ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID));

					while (maxid == -1) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
					isUploading = true;
				}
				ClientMain.client.handleMessageFromClientUI(
						new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(), buffer, maxid + 1));
			} else {
				ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UPLOAD, f.getName(),
						buffer, tableView.getSelectionModel().getSelectedItem().getId()));
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

	/**
	 * Upload file to server new request.
	 *
	 * @param r the r
	 */
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
			ClientMain.client.handleMessageFromClientUI(
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

	/**
	 * Gets the table view.
	 *
	 * @return the table view
	 */
	public TableView<Request> getTableView() {
		return tableView;
	}

	/**
	 * Table setup.
	 */
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

	/**
	 * Refresh table.
	 */
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
		if (ClientMain.currentUser.getRole() == Enums.Role.College) {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHCOLLEGE,
					ClientMain.currentUser.getUsername(), id, isSearch, unActive));
		} else if (ClientMain.currentUser.getRole() == Enums.Role.Manager
				|| ClientMain.currentUser.getRole() == Enums.Role.Supervisor) {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHMAN,
					ClientMain.currentUser.getUsername(), id, isSearch, unActive));
		} else {
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESHIS,
					ClientMain.currentUser.getUsername(), id, isSearch, unActive));
		}
	}

	/**
	 * Stop loading.
	 */
	public void stopLoading() {
		loading.interrupt();
	}

	/**
	 * Show request.
	 */
	@FXML
	public void showRequest() {
		try {
			disableAllRequestPans();
			r = tableView.getSelectionModel().getSelectedItem();
			if (r == null)
				return;
			ClientMain.currentRequest = r;
			GeneralViewRequest1.setVisible(true);
			UserViewsRequest1.setVisible(true);
			descArea.setText(r.getDescription());
			changeArea.setText(r.getChanges());
			reasonArea.setText(r.getChangeReason());
			commentsArea.setText(r.getComments());
			requestIDLabel.setText("" + r.getId());
			String temp = r.getStages()[Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage())]
					.getPlannedDueDate();
			if (ClientMain.currentUser.getRole() == Enums.Role.College) {
				if (temp != null) {
					temp = new DateTime(temp).toString("dd/MM/yyyy");
					stageDate1.setText("Current stage due date: " + temp);
				} else {
					stageDate1.setText("Current stage due date: date not yet updated.");
				}
			} else {
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
					} else {
						DatePickerExec.setVisible(false);
						dueDate.setVisible(true);
						dueDate.setEditable(false);
						dueDate.setText(temp);
					}
				} else {
					setDueDateBTN.setVisible(true);
					dueDate.setText("");
					setDueTime1.setText("");
					if (r.getCurrentStage() == Enums.RequestStageENUM.Assesment) {
						setDueTime1.setVisible(false);
						datePickerAss.setVisible(true);
					}
					if (r.getCurrentStage() == Enums.RequestStageENUM.Execution) {
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
			if (ClientMain.currentUser.getRole() == Enums.Role.Supervisor
					|| ClientMain.currentUser.getRole() == Enums.Role.Manager) {
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
			} else if (ClientMain.currentUser.getRole() != Enums.Role.College) {
				showRequestByStage(r);
			}

		} catch (Exception e) {
		}
	}

	/**
	 * Show request by stage.
	 *
	 * @param r the r
	 */
	public void showRequestByStage(Request r) {
		switch (r.getCurrentStage()) {
		case Assesment:
			AssesmentMakerPane1.setVisible(true);
			break;
		case Examaning:
			StageManagersPane1.setVisible(true);
			ComittePane1.setVisible(true);
			if (ClientMain.currentUser.getRole() == Enums.Role.CommitteMember) {
				DeclineRequestBtn.setVisible(false);
				AskMoreDataBtn.setVisible(false);
				AppointTesterLabl.setVisible(false);
				SaveTesterApointBtn.setVisible(false);
				testerCB.setVisible(false);
				ApproveStageBtn.setVisible(false);
			} else {
				ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GetComitte));
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

	/**
	 * Load comittee members.
	 *
	 * @param committe the committe
	 */
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

	/**
	 * Disable all request pans.
	 */
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

	/**
	 * Upload file message.
	 *
	 * @param status the status
	 */
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

	/**
	 * Logout.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException {
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
	 * Open new request pane.
	 *
	 * @param event the event
	 */
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

	/**
	 * Submit new request.
	 *
	 * @param event the event
	 */
	@FXML
	void submitNewRequest(ActionEvent event) {
		if (!isUploading)
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID)); // to delete
																											// folder of
																											// unfinished
																											// request
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
		Request r = new Request(0, ClientMain.currentUser.getUsername(), system, description, changes, changeReason,
				new DateTime());
		String comments = commentsArea.getText();
		r.setComments(comments);
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.CreateRequest, r));
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
		isUploading = false;
		maxid = -1;
	}

	/**
	 * Open assessment report func.
	 *
	 * @param r the r
	 */
	public void openAssessmentReportFunc(Report r) {
		reportOfRequest = r;
		Parent root = null;
		newWindow = new Stage();
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
		newWindow.setOnCloseRequest(e -> newWindow.close());
		newWindow.show();

	}

	/**
	 * Assessment report page.
	 */
	public void AssessmentReportPage() {
		if (ClientMain.currentUser.getRole() != Enums.Role.Supervisor) {
			if (setDueTime1.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("must set time stage before starting stage!");
				alert.showAndWait();
				return;
			}
		}
		int temp = tableView.getSelectionModel().getSelectedItem().getId();
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.SearchReport, temp));
	}

	/**
	 * Close extra window.
	 */
	public void closeExtraWindow() {

		newWindow.close();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Great!");
		alert.setContentText("Report saved successfully!");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);

	}

	/**
	 * Close extra window only.
	 */
	public void closeExtraWindowOnly() {
		newWindow.close();
	}

	/**
	 * Close extra window sub.
	 */
	public void closeExtraWindowSub() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Great!");
		alert.setContentText("Report submitted successfully!");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);

	}

	/**
	 * Close extra window ext.
	 */
	public void closeExtraWindowExt() {
		newWindow.close();
		RefreshTable();
		tableView.getSelectionModel().select(index);
	}

	/**
	 * Show uploaded files.
	 *
	 * @param r the r
	 */
	public void showUploadedFiles(Request r) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				filesPaths = new ArrayList<String>();
			}
		}).start();
		// Main.client.handleMessageFromClientUI(new
		// ClientServerMessage(Enums.MessageEnum.GETUSERFILES, r));
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

	/**
	 * Sets the file paths.
	 *
	 * @param filePaths the new file paths
	 */
	public void setFilePaths(ArrayList<String> filePaths) {
		this.filesPaths = filePaths;
	}

	/**
	 * Stage settings screen.
	 *
	 * @param event the event
	 */
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

	/**
	 * Status change.
	 *
	 * @param event the event
	 */
	@FXML
	void statusChange(ActionEvent event) {
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStatus, r));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm!");
		alert.setHeaderText("Request closed!");
		alert.setContentText("Request number " + r.getId() + " closed");
		alert.show();
		RefreshTable();
		unVisibleRequestPane();
	}

	/**
	 * Freeze unfreeze.
	 *
	 * @param event the event
	 */
	@FXML
	void FreezeUnfreeze(ActionEvent event) {
		boolean frozen = false, unfrozen = false;
		if (r.getStatus() == Enums.RequestStatus.Active || r.getStatus() == Enums.RequestStatus.Rejected) {
			frozen = true;
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Freeze, "" + r.getId()));
		} else if (r.getStatus() == Enums.RequestStatus.Frozen && r.getStages()[5].getReportFailure() == null) {
			unfrozen = true;
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.Unfreeze, "" + r.getId()));
		} else if (r.getStatus() == Enums.RequestStatus.Frozen) {
			unfrozen = true;
			ClientMain.client.handleMessageFromClientUI(
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

	/**
	 * Save changes.
	 *
	 * @param event the event
	 */
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
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateRequestDetails, r));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edited!");
		alert.setHeaderText("Request edited!");
		alert.setContentText("Request number " + r.getId() + " edited");
		alert.show();
		RefreshTable();

	}

	/**
	 * Edits the changes.
	 *
	 * @param event the event
	 */
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

	/**
	 * Un visible request pane.
	 */
	public void unVisibleRequestPane() {
		GeneralViewRequest1.setVisible(false);
		disableAllRequestPans();
	}

	/**
	 * Approve stage btn.
	 *
	 * @param event the event
	 */
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
			ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.UpdateStage, r.getId()));
			unVisibleRequestPane();
			RefreshTable();
		}
	}

	/**
	 * Ask more data.
	 *
	 * @param event the event
	 */
	@FXML
	void AskMoreData(ActionEvent event) {
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.downStage, r.getId()));
		RefreshTable();
		unVisibleRequestPane();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Request for more data:");
		alert.setContentText("Request for more data was sent");
		alert.show();
	}

	/**
	 * Decline request.
	 *
	 * @param event the event
	 */
	@FXML
	void DeclineRequest(ActionEvent event) {
		ClientMain.client
				.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.declineRequest, "" + r.getId()));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Request declined!");
		alert.setHeaderText("Request declined!");
		alert.setContentText("Request number " + r.getId() + " declined, waiting for supervisor confirmation");
		alert.show();
		RefreshTable();
		unVisibleRequestPane();
	}

	/**
	 * Report failure.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Save tester apoint.
	 *
	 * @param event the event
	 */
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
		ClientMain.client.handleMessageFromClientUI(
				new ClientServerMessage(Enums.MessageEnum.AppointStageHandlers, r.getId(), 4, tester));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm tester");
		alert.setContentText("Request number " + r.getId() + " tester is " + tester);
		alert.show();
	}

	/**
	 * View report.
	 *
	 * @param event the event
	 */
	@FXML
	void ViewReport(ActionEvent event) {
		AssessmentReportPage();
	}

	/**
	 * View failure report.
	 *
	 * @param event the event
	 */
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

	/**
	 * Manager back.
	 *
	 * @param event the event
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
	 * Report msg and ref.
	 *
	 * @param result the result
	 */
	public void reportMsgAndRef(String[] result) {
		tmp_newWindow.close();
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.TesterRep, result));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Failure report sent");
		alert.setContentText("Failure report sent succesfully");
		alert.showAndWait();
		RefreshTable();
		unVisibleRequestPane();
	}

	/**
	 * Sets the ass due time.
	 *
	 * @param event the new ass due time
	 */
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
		} else {
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
				ClientMain.client.handleMessageFromClientUI(
						new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE, r.getId(), dt.toString()));
			}

		}
	}

	/**
	 * Sets the exec due time.
	 *
	 * @param event the new exec due time
	 */
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
		} else {
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
				ClientMain.client.handleMessageFromClientUI(
						new ClientServerMessage(Enums.MessageEnum.SETEXECMDATE, r.getId(), dt.toString()));
			}

		}
	}

	/**
	 * Extention ask.
	 *
	 * @param event the event
	 */
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
		} else {
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
		} else {

			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("1.1-ExtensionRequest.fxml"));
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

	/**
	 * Date alert refresh.
	 */
	public void dateAlertRefresh() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Date updated");
		RefreshTable();
		alert.showAndWait();
		tableView.getSelectionModel().select(index);
		showRequest();
	}

	/**
	 * Cannot update stage.
	 */
	public void cannotUpdateStage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Can't update stage.\nNobody is appointed for next stage.\nMessage sent to supervisor.");
		alert.showAndWait();
		tableView.getSelectionModel().select(index);
		showRequest();
	}

	/**
	 * View files.
	 */
	@FXML
	public void viewFiles() {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("1.2-ShowFilesScreen.fxml"));
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
