package Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import Client.Controllers.AllSystemDataController;
import Client.Controllers.ISUsersScreenController;
import Client.Controllers.LoginScreenController;
import Client.Controllers.ManagerStatisticsController;
import Client.Controllers.RequestSettingsController;
import Client.Controllers.RequestsScreenController;
import Client.Controllers.ShowFilesScreenController;
import Common.ClientServerMessage;
import Common.Enums;
import Common.FrequencyDeviation;
import Common.Message;
import Common.Report;
import Common.Request;
import Common.SupervisorLog;
import Common.User;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatClient.
 */
public class ChatClient extends AbstractClient {

	/** The Constant DEFAULT_PORT. */
	final public static int DEFAULT_PORT = 5555;
	
	/** The no connection. */
	Dialog<Boolean> noConnection = new Dialog<>();
	
	/** The close button. */
	Node closeButton;
	
	/** The no connection alert. */
	Alert noConnectionAlert;

	/**
	 * Instantiates a new chat client.
	 *
	 * @param host the host
	 * @param port the port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("Client connected");
		// -----------Checking if server is connected every 5 seconds.
		new Thread() {
			public void run() {
				while (true) {
					if (!isConnected()) {
						Platform.runLater(new Runnable() {

							public void run() {
								{
									if (!noConnection.isShowing()) {
										Button close = new Button("Exit program");
										close.setOnAction(e -> {
											System.exit(1);
										});
										noConnection.setTitle("ERROR!");
										noConnection.setContentText("Server disconnected\nTrying to reconnect...");
										if (closeButton == null) {
											noConnection.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
											closeButton = noConnection.getDialogPane().lookupButton(ButtonType.CLOSE);
											closeButton.managedProperty().bind(closeButton.visibleProperty());
											closeButton.setVisible(false);
										}
										noConnection.showAndWait();
									}
								}
							}
						});
						try {
							while (!openConnection()) {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e1) {
								}
							}
						} catch (IOException e) {
						}

						Platform.runLater(new Runnable() {
							public void run() {
								Alert connected = new Alert(AlertType.INFORMATION);
								connected.setTitle("Reconnected!");
								connected.setContentText("Reconnected to server!");
								noConnection.setResult(Boolean.TRUE);
								noConnection.close();
								noConnectionAlert.close();
								connected.show();
							}
						});

					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
			}
		}.start();
		// -------------------------------------------------
	}

	/**
	 * Handle message from server.
	 *
	 * @param msg the msg
	 */
	public void handleMessageFromServer(Object msg) {
		if (msg == null)
			return;
		if (msg instanceof Object[]) {
			ObservableList<Request> l = FXCollections.observableArrayList();
			for (Object o : (Object[]) msg) {
				l.add((Request) o);
			}
			RequestsScreenController._ins.getTableView().setItems(l);
		}
		if (msg instanceof ClientServerMessage) {
			switch (((ClientServerMessage) msg).getType()) {
			case LoginFail:
				Platform.runLater(new Runnable() {
					public void run() {
						LoginScreenController._ins.LoginFailError();
					}
				});
				return;
			case GETOBLIST:
				ObservableList<Request> l = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					l.add((Request) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						RequestsScreenController._ins.getTableView().setItems(l);
					}
				});
				RequestsScreenController.lock = false;
				RequestsScreenController._ins.stopLoading();
				return;

			case GETREPORTSLIST:
				ObservableList<Report> reportsList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					reportsList.add((Report) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getReportsTableView().setItems(reportsList);
					}
				});

				return;

			case GETSTAGESLIST:
				ObservableList<Common.Stage> stagesList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					stagesList.add((Common.Stage) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getStagesTableView().setItems(stagesList);
					}
				});
				return;

			case GETREQUESTSLIST:
				ObservableList<Request> requestsList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					requestsList.add((Request) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getRequestsTableView().setItems(requestsList);
					}
				});

				return;

			case GETUSERSLIST:
				ObservableList<User> usersList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					usersList.add((User) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getUsersTableView().setItems(usersList);
					}
				});
				return;

			case GETMESSAGESLIST:
				ObservableList<Message> messagesList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					messagesList.add((Message) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getMessagesTableView().setItems(messagesList);
					}
				});
				return;

			case GETSUPERVISORLOGLIST:
				ObservableList<SupervisorLog> supervisorLogList = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					supervisorLogList.add((SupervisorLog) o);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						AllSystemDataController._ins.getSupervisorLogTableView().setItems(supervisorLogList);
					}
				});
				return;

			case NewRequestID:
				RequestsScreenController.waitForNewRequest = true;
				RequestsScreenController.newRequestID = ((ClientServerMessage) msg).getId();

				return;
			case UPLOADFINISH:
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						RequestsScreenController._ins.uploadFileMessage(((ClientServerMessage) msg).isUploadstatus());
					}

				});
				return;
			case GETUSERFILES:
				ArrayList<String> arr = (ArrayList<String>) ((ClientServerMessage) msg).getL();
				RequestsScreenController._ins.setFilePaths(arr);
				arr.remove(0); // removing folder path
				for (String s : arr) // THE MOST ARABIC CODE I HAVE EVER WRITTEN
				{
					char[] ch = s.toCharArray();
					for (int i = 0; i < ch.length; i++)
						if (ch[i] == '\\')
							ch[i] = '/';
					String str = String.valueOf(ch);
					String[] str2 = str.split("/");
					ShowFilesScreenController._ins.getFileListView().getItems().add(str2[str2.length - 1]);
				}
				return;
			case ComitteList:
				RequestsScreenController._ins.loadComitteeMembers(((ClientServerMessage) msg).getL());
				break;
			case tryingToLogSameTime:
				Platform.runLater(new Runnable() {
					public void run() {
						LoginScreenController._ins.LoginSameTime();
					}
				});
				return;
			case ADDISUSER:
				if (((ClientServerMessage) msg).getMsg().equals("GOOD"))
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("User added");
						alert.show();
					});
				else if (((ClientServerMessage) msg).getMsg().equals("IDEXISTS"))
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("ID already exists");
						alert.show();
					});
				else
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("User was not added");
						alert.show();
					});
				break;
			case GETISUSER:
				String[] str = (String[]) (((ClientServerMessage) msg).getArray());
				if (str == null) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Could not find user");
						alert.show();
						ISUsersScreenController._ins.getRemoveUserButton().setVisible(false);
					});
					return;
				}
				Platform.runLater(() -> {
					ISUsersScreenController._ins.getPasswordField().setText(str[0]);
					ISUsersScreenController._ins.getFirstNameField().setText(str[1]);
					ISUsersScreenController._ins.getLastNameField().setText(str[2]);
					ISUsersScreenController._ins.getMailField().setText(str[3]);
					ISUsersScreenController._ins.getRoleChoiceBox().getSelectionModel()
							.select(Enums.Role.getRoleENUM(Integer.parseInt(str[4])));
					if (str[5] != null) {
						String[] perm = str[5].split(",");
						for (String s : perm) {
							switch (s) {
							case "InfoStation":
								ISUsersScreenController._ins.getInfoStationBox().setSelected(true);
								break;
							case "Moodle":
								ISUsersScreenController._ins.getMoodleBox().setSelected(true);
								break;
							case "Library":
								ISUsersScreenController._ins.getLibraryBox().setSelected(true);
								break;
							case "Computers":
								ISUsersScreenController._ins.getComputersBox().setSelected(true);
								break;
							case "Labs":
								ISUsersScreenController._ins.getLabBox().setSelected(true);
								break;
							case "Site":
								ISUsersScreenController._ins.getSiteBox().setSelected(true);
								break;
							}
						}
					}
				});
				return;
			case UPDATEISUSER:
				if (((ClientServerMessage) msg).isUploadstatus()) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("User updated");
						alert.show();
					});
					return;
				}
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("User was not updated");
					alert.show();
				});
				break;
			case CHECKSUPERVISOREXIST:
				String supervisor = (((ClientServerMessage) msg).getMsg());
				if (supervisor == null) { // supervisor does not exists
					ISUsersScreenController._ins.setCanEdit(true);
					ISUsersScreenController._ins.setSemaphore(false);
					return;
				}
				ISUsersScreenController._ins.setCanEdit(false);
				ISUsersScreenController._ins.setSemaphore(false);
				break;
			case COUNTCOMMITEEMEMBERS:
				int members = (((ClientServerMessage) msg).getId());
				if (members < Enums.numberOfCommitteeMember) {
					ISUsersScreenController._ins.setCanEdit(true);
					ISUsersScreenController._ins.setSemaphore(false);
					return;
				}
				ISUsersScreenController._ins.setCanEdit(false);
				ISUsersScreenController._ins.setSemaphore(false);
				break;
			case CHECKCHAIRMANEXIST:
				String chairman = (((ClientServerMessage) msg).getMsg());
				if (chairman == null) {
					ISUsersScreenController._ins.setCanEdit(true);
					ISUsersScreenController._ins.setSemaphore(false);
					return;
				}
				ISUsersScreenController._ins.setCanEdit(false);
				ISUsersScreenController._ins.setSemaphore(false);
				break;
			case EDITASSESMENTER:
				if (!((ClientServerMessage) msg).isUploadstatus()) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Could not find user");
						alert.show();
					});
					return;
				}
				RequestSettingsController._ins.canExit_Asses = true;
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Assesmentor updated");
					alert.show();
				});
				break;
			case EDITTESTER:
				if (!((ClientServerMessage) msg).isUploadstatus()) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Could not find user");
						alert.show();
					});
					return;
				}
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Tester updated");
					alert.show();
				});
				break;
			case EDITEXECUTIONER:
				if (!((ClientServerMessage) msg).isUploadstatus()) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Could not find user");
						alert.show();
					});
					return;
				}
				RequestSettingsController._ins.canExit_Executor = true;
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Executioner updated");
					alert.show();
				});
				break;
			case SETASSESMENTDATE:
				Platform.runLater(() -> {
					RequestsScreenController._ins.dateAlertRefresh();

				});
				break;
			case APPROVEASSEXTENSION:
				if ((((ClientServerMessage) msg).isUploadstatus())) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Extension declined");
						alert.show();
					});
					return;
				}
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Extension approved");
					alert.show();
				});
				break;
			case ASKFOREXTENSION:
				Platform.runLater(() -> {
					RequestsScreenController._ins.closeExtraWindowExt();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Asked for new date");
					alert.show();
				});
				break;
			case CannotUpdateStage:
				Platform.runLater(() -> {
					RequestsScreenController._ins.cannotUpdateStage();
				});
				break;
			case GETMAXREQID:
				if ((((ClientServerMessage) msg).getId()) == -1) {
					Platform.runLater(new Runnable() {
						public void run() {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Cannot fetch maxid");
							alert.show();
						}
					});
					return;
				}
				RequestsScreenController.maxid = ((ClientServerMessage) msg).getId();
				break;
			case GETFILEFROMSERVER:
				ClientServerMessage CSMsg = (ClientServerMessage) msg;
				File f = new File("src\\Client" + ((ClientServerMessage) msg).getFileName());
				OutputStream os = null;
				try {
					os = new FileOutputStream(f);
					os.write(CSMsg.getBuffer());
				} catch (FileNotFoundException e) {
					System.out.println("cant find file");
				} catch (IOException e) {
					System.out.println("Cannot write to file");
				} finally {
					try {
						HostServices hostServices = ClientMain._ins.getHostServices();
						hostServices.showDocument(f.getAbsolutePath());
						os.close();

					} catch (Exception e) {
					}
				}
				break;
			case GetExtensionStat:
				ManagerStatisticsController._ins
						.updateExtensions((ArrayList<Double>) ((ClientServerMessage) msg).getL());
				break;
			case GetExtensionFreq:
				ObservableList<FrequencyDeviation> res = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					res.add((FrequencyDeviation) o);
				}
				ManagerStatisticsController._ins.getExtensionsTable().setItems(res);

				Platform.runLater(new Runnable() {
					public void run() {
						if (ManagerStatisticsController._ins.getSeries() != null)
							ManagerStatisticsController._ins.getSeries().getData().clear();
						ManagerStatisticsController._ins.getExtensionsGraph().getData().clear();
						for (FrequencyDeviation fd : res)
							ManagerStatisticsController._ins.getSeries().getData()
									.add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
						ManagerStatisticsController._ins.getExtensionsGraph().getData()
								.add(ManagerStatisticsController._ins.getSeries());
					}
				});
				break;
			case GetDelaysStat:
				Platform.runLater(new Runnable() {
					public void run() {
						ManagerStatisticsController._ins
								.showDelaySystem((ArrayList<Double>) ((ClientServerMessage) msg).getL());
					}
				});
				break;
			case GetDelaysFreq:
				ObservableList<FrequencyDeviation> delRes = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					delRes.add((FrequencyDeviation) o);
				}
				ManagerStatisticsController._ins.getDelaysTable().setItems(delRes);
				Platform.runLater(new Runnable() {
					public void run() {
						if (ManagerStatisticsController._ins.getSeries1() != null)
							ManagerStatisticsController._ins.getSeries1().getData().clear();
						for (FrequencyDeviation fd : delRes)
							ManagerStatisticsController._ins.getSeries1().getData()
									.add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
						ManagerStatisticsController._ins.getDelaysGraph().getData()
								.add(ManagerStatisticsController._ins.getSeries1());
					}
				});
				break;
			case GetAddonsStat:
				ManagerStatisticsController._ins.updateAddons((ArrayList<Double>) ((ClientServerMessage) msg).getL());
				break;
			case GetAddonsFreq:
				ObservableList<FrequencyDeviation> dasfsadf = FXCollections.observableArrayList();
				for (Object o : ((ClientServerMessage) msg).getArray()) {
					dasfsadf.add((FrequencyDeviation) o);
				}
				ManagerStatisticsController._ins.getAddonsTable().setItems(dasfsadf);

				Platform.runLater(new Runnable() {
					public void run() {
						if (ManagerStatisticsController._ins.getSeries2() != null)
							ManagerStatisticsController._ins.getSeries2().getData().clear();
						for (FrequencyDeviation fd : dasfsadf)
							ManagerStatisticsController._ins.getSeries2().getData()
									.add(new XYChart.Data<>(String.format("%.0f", fd.getValue()), fd.getFreq()));
						ManagerStatisticsController._ins.getAddonsGraph().getData()
								.add(ManagerStatisticsController._ins.getSeries2());
					}
				});

				break;
			case REMOVEUSER:
				if (((ClientServerMessage) msg).isUploadstatus()) {
					Platform.runLater(new Runnable() {
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setContentText("User deleted succesfully");
							alert.show();
						}
					});
					return;
				}
				Platform.runLater(new Runnable() {
					public void run() {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("User was not deleted succesfully");
						alert.show();
					}
				});
				break;
			case Statistics:
				ArrayList<Integer> arr12 = (ArrayList<Integer>) ((ClientServerMessage) msg).getL();
				Platform.runLater(new Runnable() {
					public void run() {
						ManagerStatisticsController._ins.updatePeropd(arr12);
					}
				});
				return;
			default:
				break;
			}

		}
		if (msg instanceof User) {
			Platform.runLater(new Runnable() {
				public void run() {
					LoginScreenController._ins.LoginGood((User) msg);
				}
			});
		}
		if (msg instanceof Report) {
			Platform.runLater(new Runnable() {
				public void run() {
					RequestsScreenController._ins.openAssessmentReportFunc((Report) msg);
				}
			});
		}

	}

	/**
	 * Handle message from client UI.
	 *
	 * @param message the message
	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			noConnectionAlert = new Alert(AlertType.ERROR);
			noConnectionAlert.setTitle("ERROR!");
			noConnectionAlert.setContentText("Action declined.\nNo connection to server.");
			noConnectionAlert.showAndWait();
		}
	}

	/**
	 * Quit.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);

	}
}
