package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Client.LoginScreenController;
import Common.Request;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
//import server.DataBaseController;

public class EchoServer extends AbstractServer {
	final private static int DEFAULT_PORT = 5555;

	public static int getDefaultPort() {
		return DEFAULT_PORT;
	}

	public EchoServer(int port) {
		super(port);
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		if (msg == null)
			return;
		if (msg instanceof ClientServerMessage) {
			ClientServerMessage CSMsg = (ClientServerMessage) msg;
			switch (CSMsg.getType()) {
			case CONNECT:
				System.out.println("User "+client.toString()+" Connected");
				return;
			case DISCONNECT:
				System.out.println("User "+client.toString()+" Disconnected");
				return;
			case REFRESH:
				ObservableList<Request> ol = DataBaseController.getRequestsForIS(((ClientServerMessage) msg).getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHUSERID:
				ObservableList<Request> ol1 = DataBaseController.getRequestsForCollege(CSMsg.getMsg());
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol1.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case REFRESHMAN:
				ObservableList<Request> ol2 = DataBaseController.getRequestsForManager();
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.GETOBLIST, ol2.toArray()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case SearchUser:
				String[] temp = CSMsg.getMsg().split(" ");
				User us1 = DataBaseController.SearchUser(temp[0], temp[1]);

				if (us1==null) {

					try {
						client.sendToClient(new ClientServerMessage(Enums.MessageEnum.LoginFail));
					} catch (IOException e) {
						System.out.println("Cant send login fail to client!");
					}
				}
				else {
					try {
						client.sendToClient(us1);
					} catch (IOException e) {
						System.out.println("Cant send user to client!");
					}
				}
				return;
			case UPLOAD:
				new File("src\\Server\\"+CSMsg.getRequest().getId()).mkdir();
				String path = "src\\Server\\"+CSMsg.getRequest().getId();
				File f = new File(path+"\\"+CSMsg.getFileName());
			    OutputStream os = null;
			    boolean sucess=false;
			    try {
			        os = new FileOutputStream(f);
			        os.write(CSMsg.getBuffer());
			        sucess=true;
			    	}
			     catch (FileNotFoundException e) {
					System.out.println("cant find file");
				} catch (IOException e) {
					System.out.println("Cannot write to file");
				} finally {
					try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.UPLOADFINISH,sucess));
			        os.close();
					} catch (Exception e ) { }

			    }
			case CreateRequest:
				Request r = CSMsg.getRequest();
				int id = DataBaseController.CreateNewRequest(r);
				try {
					client.sendToClient(new ClientServerMessage(Enums.MessageEnum.NewRequestID, id));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case CreateReport:
				int i;
				Report report = CSMsg.getReport();
				i=DataBaseController.CreateReportForRequest(report);
				if(i==1) {
					try {
						client.sendToClient("good");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
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