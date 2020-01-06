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

import Common.Request;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	final private static int DEFAULT_PORT = 5555;
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
				ol = DataBaseController.getRequestsForIS(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(), CSMsg.isUnActive());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHCOLLEGE:
				ObservableList<Request> ol1 = FXCollections.observableArrayList();
				ol1 = DataBaseController.getRequestsForCollege(CSMsg.getMsg(), CSMsg.getId(), CSMsg.isSearch(), CSMsg.isUnActive());
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
			case SearchReport:
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
				int maxid= DataBaseController.getMaxRequestID();
				File check = new File("src\\"+(maxid+1));
				if (check.exists())
					deleteDirectory(check);
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETMAXREQID,maxid));
				}catch (Exception e)
				{
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
				File file = new File("src\\"+CSMsg.getId()+"\\"+CSMsg.getMsg());
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
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETFILEFROMSERVER, file.getName(), buffer,
							CSMsg.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case UpdateStage:
				boolean tmp = DataBaseController.ChangeRequestStage(CSMsg.getId(), true);
				if (tmp==false) {
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
				String good = DataBaseController.addISUser(((ClientServerMessage) msg).getUser());
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
				boolean result = DataBaseController.updateISUser(((ClientServerMessage) msg).getUser());
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
				if (DataBaseController.getISUser(id1)==null)
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
				if (DataBaseController.getISUser(id2)==null)
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
				DataBaseController.updateStageMember(id3,CSMsg.getRequest(), 4);
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
				boolean isDenied = DataBaseController.setRequestDeny(CSMsg.getRequest(),CSMsg.getStage());
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
				try{
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.ASKFOREXTENSION));
				} catch(Exception e) {
					e.printStackTrace();
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