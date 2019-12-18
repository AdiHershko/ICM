package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Client.LoginScreenController;
import Common.Request;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.ClientServerMessage;
import Common.Enums;
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
				DataBaseController.Connect();
				return;

			case REFRESH:
				ObservableList<Request> ol = DataBaseController.getTable(((ClientServerMessage) msg).getMsg());
				try {
					client.sendToClient(ol.toArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			case SearchUser:
				String[] temp = CSMsg.getMsg().split(" ");
				User us1 = DataBaseController.getTableWithUserName(temp[0], temp[1]);

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