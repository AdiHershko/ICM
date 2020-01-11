package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import Common.Request;
import Common.SupervisorLog;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.ClientServerMessage;
import Common.Enums;
import Common.FrequencyDeviation;
import Common.Message;
import Common.Report;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * The Class EchoServer. Handles the server connection from the server side, and
 * the client messages.
 */
public class EchoServer extends AbstractServer {

	/** The Constant DEFAULT_PORT. */
	final private static int DEFAULT_PORT = 5555;

	/** The calculator. */
	Calculator c = new Calculator();

	/**
	 * Gets the default port.
	 *
	 * @return the default port
	 */
	public static int getDefaultPort() {
		return DEFAULT_PORT;
	}

	/**
	 * Instantiates a new echo server.
	 *
	 * @param port the port
	 */
	public EchoServer(int port) {
		super(port);
	}

	/**
	 * Handle message from client.
	 *
	 * @param msg    the message
	 * @param client the client connection
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Request r;
		ArrayList<Double> addList = null;
		if (msg == null)
			return;
		if (msg instanceof ClientServerMessage) {
			ClientServerMessage CSMsg = (ClientServerMessage) msg;
			switch (CSMsg.getType()) {
			case CONNECT:// if connected
				System.out.println("User " + client.toString() + " Connected");
				return;
			case DISCONNECT:// if dis-connected
				System.out.println("User " + client.toString() + " Disconnected");
				return;
			case REFRESHIS:// if refresh table
				ObservableList<Request> ol = FXCollections.observableArrayList();
				ol = DataBaseController.getRequestsForIS(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(),
						CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHCOLLEGE:// if refresh table for college users
				ObservableList<Request> ol1 = FXCollections.observableArrayList();
				ol1 = DataBaseController.getRequestsForCollege(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(),
						CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol1.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHMAN:// if refresh table for manager
				ObservableList<Request> ol2 = FXCollections.observableArrayList();
				ol2 = DataBaseController.getRequestsForManager(CSMsg.getId(), CSMsg.isSearch(), CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol2.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLREPORTS:// if all reports ask
				ObservableList<Report> ol3 = FXCollections.observableArrayList();
				ol3 = DataBaseController.getAllReports();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETREPORTSLIST, ol3.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLREQUESTS:// if all requests ask
				ObservableList<Request> ol4 = FXCollections.observableArrayList();
				ol4 = DataBaseController.getAllRequests();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETREQUESTSLIST, ol4.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLSTAGES:// if all stages ask
				ObservableList<Common.Stage> ol5 = FXCollections.observableArrayList();
				ol5 = DataBaseController.getALLRequestStages();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETSTAGESLIST, ol5.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLUSERS:// if all users ask
				ObservableList<User> ol6 = FXCollections.observableArrayList();
				ol6 = DataBaseController.getAllUsers();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETUSERSLIST, ol6.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLMESSAGES:// if all messages ask
				ObservableList<Message> ol7 = FXCollections.observableArrayList();
				ol7 = DataBaseController.getAllMessages();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETMESSAGESLIST, ol7.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETSUPERVISORLOG:// if supervisor log ask
				ObservableList<SupervisorLog> ol8 = FXCollections.observableArrayList();
				ol8 = DataBaseController.getSupervisorLog();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETSUPERVISORLOGLIST, ol8.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case UpdateRequestDetails:// if update request details
				DataBaseController.updateRequestDetails(CSMsg.getRequest());
				DataBaseController.updateLogRequestDetails(CSMsg.getRequest());
				return;
			case UpdateStatus:// if status update
				r = CSMsg.getRequest();
				if (r.getStatus() == Enums.RequestStatus.Rejected) {
					DataBaseController.changeRequestStatus(r.getId(), 4);
				} else {
					DataBaseController.changeRequestStatus(r.getId(), 1);
				}
				DataBaseController.setClosingDate(r.getId());
				DataBaseController.userMessageOfClosing(r);
				return;
			case Freeze:// if freeze request
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 2);
				return;
			case UnFreezeRejected:// if un-freeze rejected request
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 3);
				return;
			case Unfreeze:// if un-freeze request
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 0);
				return;
			case TesterRep:// if tester report
				String[] arr = (String[]) CSMsg.getArray();
				DataBaseController.ChangeReportFailure(arr);
				DataBaseController.ChangeRequestStage(Integer.parseInt(arr[1]), false);
				return;
			case declineRequest:// if decline request
				DataBaseController.changeReqToClosed(Integer.parseInt(CSMsg.getMsg()));
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 3);
				DataBaseController.changeToRejected(Integer.parseInt(CSMsg.getMsg()));
				return;
			case SearchUser:// if search user
				String[] temp = (String[]) CSMsg.getArray();
				User us1 = DataBaseController.SearchUser(temp[0], temp[1]);
				if (us1 == null) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.LoginFail));
					} catch (IOException e) {
						System.out.println("Cant send login fail to client!");
					}
				} else {
					try {
						if (us1.getUsername().equals("")) {
							client.sendToClient(new ClientServerMessage(Enums.MessageEnum.tryingToLogSameTime));
						} else {
							client.sendToClient(us1);
						}
					} catch (IOException e) {
						System.out.println("Cant send user to client!");
					}
				}
				break;
			case SearchReport:// if search report
				Report report = DataBaseController.SearchReport(CSMsg.getId());
				if (report == null) {
					try {
						report = new Report(CSMsg.getId(), "", "", "", "", "", -1);
						client.sendToClient(report);
					} catch (IOException e) {
						System.out.println("Cant send no report to client!");
					}
				} else {
					try {

						client.sendToClient(report);
					} catch (IOException e) {
						System.out.println("Cant send user to client!");
					}
				}
				break;
			case UPLOAD:// if upload files
				new File("src\\" + CSMsg.getId()).mkdir();
				String path = "src\\" + CSMsg.getId();
				File f = new File(path + "\\" + CSMsg.getFileName());
				OutputStream os = null;
				boolean sucess = false;
				try {
					os = new FileOutputStream(f);
					os.write(CSMsg.getBuffer());
					sucess = true;
				} catch (FileNotFoundException e) {
					System.out.println("cant find file");
				} catch (IOException e) {
					System.out.println("Cannot write to file");
				} finally {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.UPLOADFINISH, sucess));
						os.close();
					} catch (Exception e) {
					}

				}
				break;
			case GETMAXREQID:// if get max request id
				int maxid = DataBaseController.getMaxRequestID();
				File check = new File("src\\" + (maxid + 1));
				if (check.exists())
					deleteDirectory(check);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID, maxid));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case CreateRequest:// if create request
				r = CSMsg.getRequest();
				int id = DataBaseController.CreateNewRequest(r);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.NewRequestID, id));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case CreateReport:// if create report
				Report report1 = CSMsg.getReport();
				DataBaseController.CreateReportForRequest(report1);
				break;
			case GETUSERFILES:// if get user files
				Request request = CSMsg.getRequest();
				try (Stream<Path> paths = Files.walk(Paths.get("src\\" + request.getId() + "\\"))) {
					ArrayList<Path> Paths = new ArrayList<>();
					paths.forEach(Paths::add);
					ArrayList<String> strings = new ArrayList<>();
					for (Path p : Paths)
						strings.add(p.toString());
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETUSERFILES, strings));
				} catch (IOException e) {
					return;
				}
				break;
			case GETFILEFROMSERVER:// if files from server
				File file = new File("src\\" + CSMsg.getId() + "\\" + CSMsg.getMsg());
				InputStream is = null;
				BufferedInputStream bis = null;
				byte[] buffer = null;
				try {
					is = new FileInputStream(file.getPath());
					buffer = new byte[(int) file.length()];
					bis = new BufferedInputStream(is);
					bis.read(buffer, 0, buffer.length);
				} catch (IOException e) {
					System.out.println("Error reading file!");
				}
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETFILEFROMSERVER, file.getName(),
							buffer, CSMsg.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case UpdateStage:// if update stage (up)
				boolean tmp = DataBaseController.ChangeRequestStage(CSMsg.getId(), true);
				if (tmp == false) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CannotUpdateStage));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			case downStage:// if update stage (down)
				DataBaseController.ChangeRequestStage(CSMsg.getId(), false);
				break;
			case GetComitte:
				ArrayList<String> committe = DataBaseController.GetCommitte();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ComitteList, committe));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case AppointStageHandlers:// if appoint stage handlers
				DataBaseController.AppointStageHandlers(CSMsg.getId(), CSMsg.getStage(), CSMsg.getMsg());
				break;
			case logOut:// if logout
				DataBaseController.DisconnectUser(CSMsg.getMsg());
				break;
			case ADDISUSER:// if add IS user
				String good = DataBaseController.addISUser(((ClientServerMessage) msg).getUser(), CSMsg.getBoolarr());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ADDISUSER, good));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GETISUSER:// if get IS user
				String[] res = DataBaseController.getISUser(((ClientServerMessage) msg).getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETISUSER, res));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case UPDATEISUSER:// if update IS user
				boolean result = DataBaseController.updateISUser(((ClientServerMessage) msg).getUser(),
						CSMsg.getBoolarr());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.UPDATEISUSER, result));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case CHECKSUPERVISOREXIST:// if check supervisor
				String supervisor = DataBaseController.getSupervisor();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CHECKSUPERVISOREXIST, supervisor));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case COUNTCOMMITEEMEMBERS:// if count committee members
				int count = DataBaseController.countCommitteMembers();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.COUNTCOMMITEEMEMBERS, count));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case CHECKCHAIRMANEXIST:// if check committee chairman
				String chairman = DataBaseController.getChairman();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CHECKCHAIRMANEXIST, chairman));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case EDITASSESMENTER:// if edit assessment appointee
				String id1 = ((ClientServerMessage) msg).getMsg();
				if (DataBaseController.getISUser(id1) == null)
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER, false));
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				DataBaseController.updateStageMember(id1, CSMsg.getRequest(), 1);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITASSESMENTER, true));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case EDITEXECUTIONER:// if edit execution appointee
				String id2 = ((ClientServerMessage) msg).getMsg();
				if (DataBaseController.getISUser(id2) == null)
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER, false));
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				DataBaseController.updateStageMember(id2, CSMsg.getRequest(), 3);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITEXECUTIONER, true));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case EDITTESTER:// if edit tester appointee
				String id3 = ((ClientServerMessage) msg).getMsg();
				if (DataBaseController.getISUser(id3) == null)
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITTESTER, false));
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				DataBaseController.updateStageMember(id3, CSMsg.getRequest(), 4);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.EDITTESTER, true));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case SETASSESMENTDATE:// if set assessment date
				DataBaseController.setDate(CSMsg.getId(), 1, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case APPROVEASSEXTENSION:// if set assessment approve extension
				boolean isDenied = DataBaseController.setRequestDeny(CSMsg.getRequest(), CSMsg.getStage());
				try {
					if (isDenied) {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.APPROVEASSEXTENSION, true));
						return;
					}
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.APPROVEASSEXTENSION, false));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case SETEXAMDATE:// if set examining date
				DataBaseController.setDate(CSMsg.getId(), 2, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case SETEXECMDATE:// if set execution date
				DataBaseController.setDate(CSMsg.getId(), 3, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case SETTESTDATE:// if set testing date
				DataBaseController.setDate(CSMsg.getId(), 4, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ASKFOREXTENSION:// if extension ask
				DataBaseController.changeExtendedDate(CSMsg.getRequest(), CSMsg.getMsg());
				DataBaseController.extensionMessage(CSMsg.getRequest());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ASKFOREXTENSION));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetExtensionStat:// if get extension statistic
				ArrayList<Double> ext = DataBaseController.getExtensionDurations();
				ArrayList<Double> resultList = c.calcAll(ext);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetExtensionStat, resultList));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetExtensionFreq:// if get extension frequency
				ArrayList<Double> extFreq = DataBaseController.getExtensionDurations();
				ObservableList<FrequencyDeviation> resultOL = c.freqDeviation(extFreq);
				try {
					client.sendToClient(
							new ClientServerMessage(Enums.MessageEnum.GetExtensionFreq, resultOL.toArray()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetDelaysStat:// if get delyas statistic
				ArrayList<Double> del = DataBaseController.getDelaysDurations(CSMsg.getEnm());
				if (del.isEmpty()) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetDelaysStat, del));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					ArrayList<Double> delResultList = c.calcAll(del);
					delResultList.add(0, (double) del.size());
					delResultList.remove(1);
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetDelaysStat, delResultList));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case GetDelaysFreq:// if get extension frequency
				ArrayList<Double> delfreq = DataBaseController.getDelaysDurations(CSMsg.getEnm());
				ObservableList<FrequencyDeviation> dekResultOL = c.freqDeviation(delfreq);
				try {
					client.sendToClient(
							new ClientServerMessage(Enums.MessageEnum.GetDelaysFreq, dekResultOL.toArray()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetAddonsStat:// if get addons statistic
				addList = DataBaseController.getAllAddons();
				ArrayList<Double> addonStatRes = c.calcAll(addList);
				ObservableList<FrequencyDeviation> addResultOL = c.freqDeviation(addList);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetAddonsStat, addonStatRes));
					client.sendToClient(
							new ClientServerMessage(Enums.MessageEnum.GetAddonsFreq, addResultOL.toArray()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case REMOVEUSER:// if remove user
				boolean removed = DataBaseController.removeUser(CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.REMOVEUSER, removed));
				} catch (Exception e) {
					e.printStackTrace();
				}
			case getPeriodReport:// if get periodic report
				String[] s = (String[]) CSMsg.getArray();
				ArrayList<Integer> peroid = DataBaseController.getActivityData(s);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.Statistics, peroid));
				} catch (IOException e1) {
					return;
				}
				break;
			case SHOWFILES:
				File exist = new File("src\\"+CSMsg.getRequest().getId());
				if (exist.exists())
				{
					try{
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SHOWFILES,true));
						return;
					} catch(Exception e){
						e.printStackTrace();
						return;
					}
				}
					try{
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SHOWFILES,false));
					}catch(Exception e) {
						e.printStackTrace();
					}
				break;
			default:
				break;
			}
		}

	}

	/**
	 * Server started.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * Server stopped.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * Delete directory.
	 *
	 * @param directoryToBeDeleted the directory to be deleted
	 */
	private void deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		directoryToBeDeleted.delete();
	}

	/**
	 * Start.
	 *
	 * @param port the port
	 * @return 0 if successful, 1 if failed, 2 if no clients
	 */
	public static int Start(int port) {
		if (DataBaseController.Connect() == false) {
			return 1;
		}
		EchoServer sv = new EchoServer(port);
		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
			return 2;
		}
		return 0;
	}

}