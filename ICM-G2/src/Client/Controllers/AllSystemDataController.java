package Client.Controllers;

import java.io.IOException;
import org.joda.time.DateTime;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Message;
import Common.Report;
import Common.Request;
import Common.SupervisorLog;
import Common.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * The Class AllSystemDataController:
 * Controller for 3.3-AllSystemDataScreen.fxml.
 */
public class AllSystemDataController {
	
	/** The ins. */
	public static AllSystemDataController _ins;
	
	/** The back manager button. */
	@FXML
	private Button backManagerButton;
	
	/** The date and time label. */
	@FXML
	private Label dateAndTimeLabel;
	
	/** The user name label. */
	@FXML
	private Label userNameLabel;
	
	/** The reports radio. */
	@FXML
	private RadioButton reportsRadio;
	
	/** The reports table view. */
	@FXML
	private TableView<Report> reportsTableView;
	
	/** The requests table view. */
	@FXML
	private TableView<Request> requestsTableView;
	
	/** The stages table view. */
	@FXML
	private TableView<Common.Stage> stagesTableView;
	
	/** The users table view. */
	@FXML
	private TableView<User> usersTableView;
	
	/** The requests radio button. */
	@FXML
	private RadioButton requestsRadio;
	
	/** The stages radio button. */
	@FXML
	private RadioButton stagesRadio;
	
	/** The users radio button. */
	@FXML
	private RadioButton usersRadio;
	
	/** The messages radio button. */
	@FXML
	private RadioButton messagesRadioButton;
	
	/** The messages table view. */
	@FXML
	private TableView<Message> messagesTableView;
	
	/** The supervisor log radio button. */
	@FXML
	private RadioButton supervisorLogRadioButton;
	
	/** The supervisor log table view. */
	@FXML
	private TableView<SupervisorLog> supervisorLogTableView;

	/** The logout button. */
	@FXML
	private Button logoutButton;
	
	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		reportsTableSetup();
		requestsTableSetup();
		stagesTableSetup();
		usersTableSetup();
		messagesTableSetup();
		supervisorLogTableSetup();
		reportsTableView.setVisible(false);
		requestsTableView.setVisible(false);
		stagesTableView.setVisible(false);
		usersTableView.setVisible(false);
		messagesTableView.setVisible(false);
		supervisorLogTableView.setVisible(false);

		new Thread() {
			public void run() {
				userNameLabel.setText(ClientMain.currentUser.getFirstName() + " " + ClientMain.currentUser.getLastName());
				while (true) // update time in 0.5s intervals
				{

					Platform.runLater(new Runnable()
					{
						public void run() {
							DateTime dt = new DateTime();
							dateAndTimeLabel.setText(dt.toString("dd/MM/yyyy hh:mm:ss a" + " |"));
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
	}

	/**
	 * Reports table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reportsTableSetup() {
		TableColumn<Report, Integer> idColumn = new TableColumn<>("Request ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("requestId"));
		TableColumn<Report, String> descriptionColumn = new TableColumn<>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
		TableColumn<Report, String> resultColumn = new TableColumn<>("Result");
		resultColumn.setCellValueFactory(new PropertyValueFactory("result"));
		TableColumn<Report, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory("location"));
		TableColumn<Report, String> constraintsColumn = new TableColumn<>("Constraints");
		constraintsColumn.setCellValueFactory(new PropertyValueFactory("constrains"));
		TableColumn<Report, String> risksColumn = new TableColumn<>("Risks");
		risksColumn.setCellValueFactory(new PropertyValueFactory("risks"));
		TableColumn<Report, Integer> durationColumn = new TableColumn<>("Duration");
		durationColumn.setCellValueFactory(new PropertyValueFactory("durationAssesment"));
		reportsTableView.getColumns().addAll(idColumn, descriptionColumn, resultColumn, locationColumn,
				constraintsColumn, risksColumn, durationColumn);
		reportsTableView.setMaxSize(1163, 417);
		for (TableColumn<Report, ?> col : reportsTableView.getColumns())
			col.setMinWidth(250);
	}

	/**
	 * Requests table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void requestsTableSetup() {
		TableColumn<Request, Integer> idColumn = new TableColumn<>("Request ID");
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Request, String> requestorColumn = new TableColumn<>("Requestor");
		requestorColumn.setCellValueFactory(new PropertyValueFactory("requestorID"));
		TableColumn<Request, Enums.SystemENUM> systemColumn = new TableColumn<>("System");
		systemColumn.setCellValueFactory(new PropertyValueFactory("system"));
		TableColumn<Request, String> descriptionColumn = new TableColumn<>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
		TableColumn<Request, String> changeColumn = new TableColumn<>("Change");
		changeColumn.setCellValueFactory(new PropertyValueFactory("changes"));
		TableColumn<Request, String> changeReasonColumn = new TableColumn<>("Change Reason");
		changeReasonColumn.setCellValueFactory(new PropertyValueFactory("changeReason"));
		TableColumn<Request, Enums.RequestStageENUM> stageColumn = new TableColumn<>("Stage");
		stageColumn.setCellValueFactory(new PropertyValueFactory("currentStage"));
		TableColumn<Request, Enums.RequestStatus> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
		TableColumn<Request, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		TableColumn<Request, String> commentsColumn = new TableColumn<>("Comments");
		commentsColumn.setCellValueFactory(new PropertyValueFactory("comments"));
		TableColumn<Request, String> currentHandlersColumn = new TableColumn<>("Current Handlers");
		currentHandlersColumn.setCellValueFactory(new PropertyValueFactory("currentHandlers"));
		TableColumn<Request, Boolean> isDeniedColumn = new TableColumn<>("Is Denied");
		isDeniedColumn.setCellValueFactory(new PropertyValueFactory("isDenied"));

		requestsTableView.getColumns().addAll(idColumn, requestorColumn, systemColumn, descriptionColumn, changeColumn,
				changeReasonColumn, stageColumn, statusColumn, dateColumn, commentsColumn, currentHandlersColumn,
				isDeniedColumn);
		requestsTableView.setMaxSize(1163, 417);
		for (TableColumn<Request, ?> col : requestsTableView.getColumns())
			col.setMinWidth(250);

	}

	/**
	 * Stages table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void stagesTableSetup() {
		TableColumn<Common.Stage, String> stageColumn = new TableColumn<>("Stage Name");
		stageColumn.setCellValueFactory(new PropertyValueFactory("StageName"));
		TableColumn<Common.Stage, String> plannedDueDateColumn = new TableColumn<>("Planned Due Date");
		plannedDueDateColumn.setCellValueFactory(new PropertyValueFactory("plannedDueDate"));
		TableColumn<Common.Stage, Boolean> isApprovedColumn = new TableColumn<>("Is Approved");
		isApprovedColumn.setCellValueFactory(new PropertyValueFactory("isApproved"));
		TableColumn<Common.Stage, Boolean> isExtendedColumn = new TableColumn<>("Is Extended");
		isExtendedColumn.setCellValueFactory(new PropertyValueFactory("isExtended"));
		TableColumn<Common.Stage, String> memberColumn = new TableColumn<>("Member");
		memberColumn.setCellValueFactory(new PropertyValueFactory("member"));
		TableColumn<Common.Stage, Integer> requestIDColumn = new TableColumn<>("Request ID");
		requestIDColumn.setCellValueFactory(new PropertyValueFactory("requestID"));
		TableColumn<Common.Stage, String> actualDateColumn = new TableColumn<>("Actual Date");
		actualDateColumn.setCellValueFactory(new PropertyValueFactory("actualDate"));
		TableColumn<Common.Stage, String> extendedDueDateColumn = new TableColumn<>("Extended Due Date");
		extendedDueDateColumn.setCellValueFactory(new PropertyValueFactory("extendedDueDate"));
		TableColumn<Common.Stage, String> reportFailureColumn = new TableColumn<>("Report Failure");
		reportFailureColumn.setCellValueFactory(new PropertyValueFactory("ReportFailure"));
		TableColumn<Common.Stage, Integer> daysOfExtensionColumn = new TableColumn<>("Days Of Extension");
		daysOfExtensionColumn.setCellValueFactory(new PropertyValueFactory("daysOfExtension"));
		stagesTableView.getColumns().addAll(stageColumn, plannedDueDateColumn, isApprovedColumn, isExtendedColumn,
				memberColumn, requestIDColumn, actualDateColumn, extendedDueDateColumn, reportFailureColumn,
				daysOfExtensionColumn);
		stagesTableView.setMaxSize(1163, 417);
		for (TableColumn<Common.Stage, ?> col : stagesTableView.getColumns())
			col.setMinWidth(250);

	}

	/**
	 * Users table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void usersTableSetup() {

		TableColumn<User, String> userNameColumn = new TableColumn<>("Username");
		userNameColumn.setCellValueFactory(new PropertyValueFactory("username"));
		TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
		passwordColumn.setCellValueFactory(new PropertyValueFactory("password"));
		TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
		TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
		lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
		TableColumn<User, String> emailColumn = new TableColumn<>("Mail");
		emailColumn.setCellValueFactory(new PropertyValueFactory("mail"));
		TableColumn<User, Enums.Role> roleColumn = new TableColumn<>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory("role"));
		TableColumn<User, Integer> collegeNumColumn = new TableColumn<>("College Num");
		collegeNumColumn.setCellValueFactory(new PropertyValueFactory("collegeNum"));
		TableColumn<User, String> departmentColumn = new TableColumn<>("Department");
		departmentColumn.setCellValueFactory(new PropertyValueFactory("department"));
		usersTableView.getColumns().addAll(userNameColumn, passwordColumn, firstNameColumn, lastNameColumn, emailColumn,
				roleColumn, collegeNumColumn, departmentColumn);
		usersTableView.setMaxSize(1163, 417);
		for (TableColumn<User, ?> col : usersTableView.getColumns())
			col.setMinWidth(250);
	}

	/**
	 * Messages table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void messagesTableSetup() {

		TableColumn<Message, Integer> requestIDColumn = new TableColumn<>("RequestID");
		requestIDColumn.setCellValueFactory(new PropertyValueFactory("requestID"));
		TableColumn<Message, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
		TableColumn<Message, String> detailsColumn = new TableColumn<>("Details");
		detailsColumn.setCellValueFactory(new PropertyValueFactory("details"));
		TableColumn<Message, String> recieverColumn = new TableColumn<>("Reciever");
		recieverColumn.setCellValueFactory(new PropertyValueFactory("reciever"));
		messagesTableView.getColumns().addAll(requestIDColumn, titleColumn, detailsColumn, recieverColumn);
		messagesTableView.setMaxSize(1163, 417);
		for (TableColumn<Message, ?> col : messagesTableView.getColumns())
			col.setMinWidth(250);
	}

	/**
	 * Supervisor log table setup.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void supervisorLogTableSetup() {

		TableColumn<SupervisorLog, Integer> requestIDColumn = new TableColumn<>("RequestID");
		requestIDColumn.setCellValueFactory(new PropertyValueFactory("requestID"));
		TableColumn<SupervisorLog, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		TableColumn<SupervisorLog, String> fieldColumn = new TableColumn<>("Field");
		fieldColumn.setCellValueFactory(new PropertyValueFactory("field"));
		TableColumn<SupervisorLog, String> whatChangeColumn = new TableColumn<>("What Change");
		whatChangeColumn.setCellValueFactory(new PropertyValueFactory("whatChange"));
		supervisorLogTableView.getColumns().addAll(requestIDColumn, dateColumn, fieldColumn, whatChangeColumn);
		supervisorLogTableView.setMaxSize(1163, 417);
		for (TableColumn<SupervisorLog, ?> col : supervisorLogTableView.getColumns())
			col.setMinWidth(250);

	}

	/**
	 * Show reports table.
	 *
	 * @param event the mouse click
	 */
	@FXML
	void showReports(ActionEvent event) {
		if (reportsRadio.isSelected()) {
			reportsTableView.setVisible(true);
			requestsTableView.setVisible(false);
			stagesTableView.setVisible(false);
			usersTableView.setVisible(false);
			messagesTableView.setVisible(false);
			supervisorLogTableView.setVisible(false);

		} else {
			reportsTableView.setVisible(false);
		}
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETALLREPORTS));

	}

	/**
	 * Show requests table.
	 *
	 * @param event the event
	 */
	@FXML
	void showRequests(ActionEvent event) {
		if (requestsRadio.isSelected()) {
			requestsTableView.setVisible(true);
			reportsTableView.setVisible(false);
			stagesTableView.setVisible(false);
			usersTableView.setVisible(false);
			messagesTableView.setVisible(false);
			supervisorLogTableView.setVisible(false);

		} else {
			requestsTableView.setVisible(false);
		}
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETALLREQUESTS));

	}

	/**
	 * Show stages table.
	 *
	 * @param event the event
	 */
	@FXML
	void showStages(ActionEvent event) {
		if (stagesRadio.isSelected()) {
			stagesTableView.setVisible(true);
			reportsTableView.setVisible(false);
			requestsTableView.setVisible(false);
			usersTableView.setVisible(false);
			messagesTableView.setVisible(false);
			supervisorLogTableView.setVisible(false);

		} else {
			stagesTableView.setVisible(false);
		}
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETALLSTAGES));
	}

	/**
	 * Show users table.
	 *
	 * @param event the event
	 */
	@FXML
	void showUsers(ActionEvent event) {
		if (usersRadio.isSelected()) {
			usersTableView.setVisible(true);
			reportsTableView.setVisible(false);
			requestsTableView.setVisible(false);
			stagesTableView.setVisible(false);
			messagesTableView.setVisible(false);
			supervisorLogTableView.setVisible(false);
		} else {
			usersTableView.setVisible(false);
		}
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETALLUSERS));

	}

	/**
	 * Show messages table.
	 *
	 * @param event the event
	 */
	@FXML
	void showMessages(ActionEvent event) {
		if (messagesRadioButton.isSelected()) {
			messagesTableView.setVisible(true);
			reportsTableView.setVisible(false);
			requestsTableView.setVisible(false);
			stagesTableView.setVisible(false);
			usersTableView.setVisible(false);
			supervisorLogTableView.setVisible(false);
		} else {
			messagesTableView.setVisible(false);
		}

		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETALLMESSAGES));

	}

	/**
	 * Show supervisor log table.
	 *
	 * @param event the event
	 */
	@FXML
	void showSupervisorLog(ActionEvent event) {
		if (supervisorLogRadioButton.isSelected()) {
			supervisorLogTableView.setVisible(true);
			reportsTableView.setVisible(false);
			requestsTableView.setVisible(false);
			stagesTableView.setVisible(false);
			usersTableView.setVisible(false);
			messagesTableView.setVisible(false);
		} else {
			supervisorLogTableView.setVisible(false);
		}
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETSUPERVISORLOG));
	}

	/**
	 * Manager back button function.
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
		Scene manager = new Scene(root);
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					javafx.stage.Stage window = (javafx.stage.Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(manager);
					window.setResizable(false);
					window.show();
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Gets the stages table view.
	 *
	 * @return the stages table view
	 */
	public TableView<Common.Stage> getStagesTableView() {
		return stagesTableView;
	}

	/**
	 * Sets the stages table view.
	 *
	 * @param stagesTableView the new stages table view
	 */
	public void setStagesTableView(TableView<Common.Stage> stagesTableView) {
		this.stagesTableView = stagesTableView;
	}

	/**
	 * Gets the reports table view.
	 *
	 * @return the reports table view
	 */
	public TableView<Report> getReportsTableView() {
		return reportsTableView;
	}

	/**
	 * Sets the reports table view.
	 *
	 * @param reportsTableView the new reports table view
	 */
	public void setReportsTableView(TableView<Report> reportsTableView) {
		this.reportsTableView = reportsTableView;
	}

	/**
	 * Gets the requests table view.
	 *
	 * @return the requests table view
	 */
	public TableView<Request> getRequestsTableView() {
		return requestsTableView;
	}

	/**
	 * Sets the requests table view.
	 *
	 * @param requestsTableView the new requests table view
	 */
	public void setRequestsTableView(TableView<Request> requestsTableView) {
		this.requestsTableView = requestsTableView;
	}

	/**
	 * Gets the users table view.
	 *
	 * @return the users table view
	 */
	public TableView<User> getUsersTableView() {
		return usersTableView;
	}

	/**
	 * Sets the users table view.
	 *
	 * @param usersTableView the new users table view
	 */
	public void setUsersTableView(TableView<User> usersTableView) {
		this.usersTableView = usersTableView;
	}

	/**
	 * Gets the messages table view.
	 *
	 * @return the messages table view
	 */
	public TableView<Message> getMessagesTableView() {
		return messagesTableView;
	}

	/**
	 * Sets the messages table view.
	 *
	 * @param messagesTableView the new messages table view
	 */
	public void setMessagesTableView(TableView<Message> messagesTableView) {
		this.messagesTableView = messagesTableView;
	}

	/**
	 * Gets the supervisor log table view.
	 *
	 * @return the supervisor log table view
	 */
	public TableView<SupervisorLog> getSupervisorLogTableView() {
		return supervisorLogTableView;
	}

	/**
	 * Sets the supervisor log table view.
	 *
	 * @param supervisorLogTableView the new supervisor log table view
	 */
	public void setSupervisorLogTableView(TableView<SupervisorLog> supervisorLogTableView) {
		this.supervisorLogTableView = supervisorLogTableView;
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
}
