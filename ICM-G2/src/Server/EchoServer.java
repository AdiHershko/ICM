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
import java.util.ArrayList;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

public class EchoServer extends AbstractServer {
	final private static int DEFAULT_PORT = 5555;
	Calculator c = new Calculator();

	public static int getDefaultPort() {
		return DEFAULT_PORT;
	}

	public EchoServer(int port) {
		super(port);
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Request r;
		if (msg == null)
			return;
		if (msg instanceof ClientServerMessage) {
			ClientServerMessage CSMsg = (ClientServerMessage) msg;
			switch (CSMsg.getType()) {
			case CONNECT:
				System.out.println("User " + client.toString() + " Connected");
				return;
			case DISCONNECT:
				System.out.println("User " + client.toString() + " Disconnected");
				return;
			case REFRESHIS:
				ObservableList<Request> ol = FXCollections.observableArrayList();
				ol = DataBaseController.getRequestsForIS(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(),
						CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHCOLLEGE:
				ObservableList<Request> ol1 = FXCollections.observableArrayList();
				ol1 = DataBaseController.getRequestsForCollege(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(),
						CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol1.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHMAN:
				ObservableList<Request> ol2 = FXCollections.observableArrayList();
				ol2 = DataBaseController.getRequestsForManager(CSMsg.getId(), CSMsg.isSearch(), CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol2.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;


			case GETALLREPORTS:
				ObservableList<Report> ol3 = FXCollections.observableArrayList();
				ol3 = DataBaseController.getAllReports();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETREPORTSLIST, ol3.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			case GETALLREQUESTS:
				ObservableList<Request> ol4 = FXCollections.observableArrayList();
				ol4 = DataBaseController.getAllRequests();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETREQUESTSLIST, ol4.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			case GETALLSTAGES:
				ObservableList<Common.Stage> ol5 = FXCollections.observableArrayList();
				ol5 = DataBaseController.getALLRequestStages();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETSTAGESLIST, ol5.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case GETALLUSERS:
				ObservableList<User> ol6 = FXCollections.observableArrayList();
				ol6 = DataBaseController.getAllUsers();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETUSERSLIST, ol6.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;


			case GETALLMESSAGES:
				ObservableList<Message> ol7 = FXCollections.observableArrayList();
				ol7 = DataBaseController.getAllMessages();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETMESSAGESLIST, ol7.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			case GETSUPERVISORLOG:
				ObservableList<SupervisorLog> ol8 = FXCollections.observableArrayList();
				ol8 = DataBaseController.getSupervisorLog();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETSUPERVISORLOGLIST, ol8.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;


			case UpdateRequestDetails:
				DataBaseController.updateRequestDetails(CSMsg.getRequest());
				DataBaseController.updateLogRequestDetails(CSMsg.getRequest());
				return;
			case UpdateStatus:
				r = CSMsg.getRequest();
				if (r.getStatus() == Enums.RequestStatus.Rejected) {
					DataBaseController.changeRequestStatus(r.getId(), 4);
				}
				else {
					DataBaseController.changeRequestStatus(r.getId(), 1);
				}
				DataBaseController.setClosingDate(r.getId());
				DataBaseController.userMessageOfClosing(r);
				return;
			case Freeze:
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 2);
				return;
			case UnFreezeRejected:
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 3);
				return;
			case Unfreeze:
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 0);
				return;
			case TesterRep:
				String[] arr = (String[]) CSMsg.getArray();
				DataBaseController.ChangeReportFailure(arr);
				DataBaseController.ChangeRequestStage(Integer.parseInt(arr[1]), false);
				return;
			case declineRequest:
				DataBaseController.changeReqToClosed(Integer.parseInt(CSMsg.getMsg()));
				DataBaseController.changeRequestStatus(Integer.parseInt(CSMsg.getMsg()), 3);
				DataBaseController.changeToRejected(Integer.parseInt(CSMsg.getMsg()));
				return;
			case SearchUser:
				String[] temp = (String[]) CSMsg.getArray();
				User us1 = DataBaseController.SearchUser(temp[0], temp[1]);
				if (us1 == null) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.LoginFail));
					} catch (IOException e) {
						System.out.println("Cant send login fail to client!");
					}
				}
				else {
					try {
						if (us1.getUsername().equals("")) {
							client.sendToClient(new ClientServerMessage(Enums.MessageEnum.tryingToLogSameTime));
						}
						else {
							client.sendToClient(us1);
						}
					} catch (IOException e) {
						System.out.println("Cant send user to client!");
					}
				}
				break;
			case SearchReport:
				Report report = DataBaseController.SearchReport(CSMsg.getId());
				if (report == null) {
					try {
						report = new Report(CSMsg.getId(), "", "", "", "", "", -1);
						client.sendToClient(report);
					} catch (IOException e) {
						System.out.println("Cant send no report to client!");
					}
				}
				else {
					try {

						client.sendToClient(report);
					} catch (IOException e) {
						System.out.println("Cant send user to client!");
					}
				}
				break;
			case UPLOAD:
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
			case GETMAXREQID:
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
			case CreateRequest:
				r = CSMsg.getRequest();
				int id = DataBaseController.CreateNewRequest(r);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.NewRequestID, id));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case CreateReport:
				Report report1 = CSMsg.getReport();
				DataBaseController.CreateReportForRequest(report1);
				break;
			case GETUSERFILES:
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
			case GETFILEFROMSERVER:
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
			case UpdateStage:
				boolean tmp = DataBaseController.ChangeRequestStage(CSMsg.getId(), true);
				if (tmp == false) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CannotUpdateStage));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			case downStage:
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
			case AppointStageHandlers:
				DataBaseController.AppointStageHandlers(CSMsg.getId(), CSMsg.getStage(), CSMsg.getMsg());
				break;
			case logOut:
				DataBaseController.DisconnectUser(CSMsg.getMsg());
				break;
			case ADDISUSER:
				String good = DataBaseController.addISUser(((ClientServerMessage) msg).getUser(),CSMsg.getBoolarr());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ADDISUSER, good));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GETISUSER:
				String[] res = DataBaseController.getISUser(((ClientServerMessage) msg).getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETISUSER, res));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case UPDATEISUSER:
				boolean result = DataBaseController.updateISUser(((ClientServerMessage) msg).getUser(),CSMsg.getBoolarr());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.UPDATEISUSER, result));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case CHECKSUPERVISOREXIST:
				String supervisor = DataBaseController.getSupervisor();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CHECKSUPERVISOREXIST, supervisor));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case COUNTCOMMITEEMEMBERS:
				int count = DataBaseController.countCommitteMembers();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.COUNTCOMMITEEMEMBERS, count));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case CHECKCHAIRMANEXIST:
				String chairman = DataBaseController.getChairman();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.CHECKCHAIRMANEXIST, chairman));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case EDITASSESMENTER:
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
			case EDITEXECUTIONER:
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
			case EDITTESTER:
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
			case SETASSESMENTDATE:
				DataBaseController.setDate(CSMsg.getId(), 1, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case APPROVEASSEXTENSION:
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
			case SETEXAMDATE:
				DataBaseController.setDate(CSMsg.getId(), 2, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case SETEXECMDATE:
				DataBaseController.setDate(CSMsg.getId(), 3, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case SETTESTDATE:
				DataBaseController.setDate(CSMsg.getId(), 4, CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.SETASSESMENTDATE));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ASKFOREXTENSION:
				DataBaseController.changeExtendedDate(CSMsg.getRequest(), CSMsg.getMsg());
				DataBaseController.extensionMessage(CSMsg.getRequest());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ASKFOREXTENSION));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetExtensionStat:
				ArrayList<Double> ext = DataBaseController.getExtensionDurations();
				ArrayList<Double> resultList = c.calcAll(ext);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetExtensionStat, resultList));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetExtensionFreq:
				ArrayList<Double> extFreq = DataBaseController.getExtensionDurations();
				ObservableList<FrequencyDeviation> resultOL = c.freqDeviation(extFreq);
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetExtensionFreq, resultOL.toArray()));
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case GetDelaysStat:
				ArrayList<Double> del = DataBaseController.getDelaysDurations(CSMsg.getEnm());
				if (del.isEmpty()) {
					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetDelaysStat, del));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
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
			case GetDelaysFreq:
				ArrayList<Double> delFreq = DataBaseController.getDelaysDurations(CSMsg.getEnm());
				ObservableList<FrequencyDeviation> dekResultOL = c.freqDeviation(delFreq);
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetDelaysFreq, dekResultOL.toArray()));
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case GetAddonsStat:
				ArrayList<Double> addList = DataBaseController.getAllAddons();
				ArrayList<Double> addonStatRes = c.calcAll(addList);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetAddonsStat, addonStatRes));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetAddonsFreq:
				ArrayList<Double> addFreq = DataBaseController.getAllAddons();
				ObservableList<FrequencyDeviation> addResultOL = c.freqDeviation(addFreq);
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GetAddonsFreq, addResultOL.toArray()));
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case REMOVEUSER:
				boolean removed = DataBaseController.removeUser(CSMsg.getMsg());
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.REMOVEUSER,removed));
				}catch(Exception e){
					e.printStackTrace();
				}
			case getPeriodReport:
				int active = 0, closed = 0, frozen = 0, rejected = 0, rejectedclose = 0;
				String[] s = (String[]) CSMsg.getArray();
				DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
				DateTime dt1 = null, dt2 = null;
				try {

					dt1 = dtf.parseDateTime(s[0]);
					dt2 = dtf.parseDateTime(s[1]);
				} catch (IllegalArgumentException e) {
					return;
				}
				ObservableList<Request> olist1 = FXCollections.observableArrayList();
				olist1 = DataBaseController.getAllTheRequests();
				int results=DataBaseController.getDurations();
				for (int i = 0; i < olist1.size(); i++) {
					if (olist1.get(i).getDate().isAfter(dt1) && olist1.get(i).getDate().isBefore(dt2)) {
						if (olist1.get(i).getStatus().equals(Enums.RequestStatus.Active))
							active++;
						if (olist1.get(i).getStatus().equals(Enums.RequestStatus.Closed))
							closed++;
						if (olist1.get(i).getStatus().equals(Enums.RequestStatus.Frozen))
							frozen++;
						if (olist1.get(i).getStatus().equals(Enums.RequestStatus.Rejected))
							rejected++;
						if (olist1.get(i).getStatus().equals(Enums.RequestStatus.RejectedClosed))
							rejectedclose++;
					}

				}
				ArrayList<Integer> l= new ArrayList<>();
				l.add(active);
				l.add(closed);
				l.add(frozen);
				int temprej=rejected+rejectedclose;
				l.add(temprej);
				l.add(results);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.Statistics, l));
				} catch (IOException e1) {
					return;
				}
				break;
			default:
				break;
			}
		}

	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	private void deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		directoryToBeDeleted.delete();
	}

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