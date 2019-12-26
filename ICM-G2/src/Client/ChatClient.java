// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package Client;

import java.io.IOException;
import java.util.ArrayList;

import Common.ClientServerMessage;
import Common.Report;
import Common.Request;
import Common.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;

public class ChatClient extends AbstractClient {

	final public static int DEFAULT_PORT = 5555;
	Dialog<Boolean> noConnection = new Dialog<>();
	Node closeButton;
	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("Client connected");
		// -----------Checking if server is connected every 5 seconds.
		// TODO: why closing window after reconnect?
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
										noConnection.setContentText(
												"Server disconnected\nTrying to reconnect...");
										if (closeButton==null)
										{
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
				for (Object o : ((ClientServerMessage)msg).getList()) {
					l.add((Request) o);
				}
				RequestsScreenController._ins.getTableView().setItems(l);
				return;
			case NewRequestID:
				RequestsScreenController.waitForNewRequest = true;
				RequestsScreenController.newRequestID = ((ClientServerMessage) msg).getId();

				return;
			case UPLOADFINISH:
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						RequestsScreenController._ins.uploadFileMessage(((ClientServerMessage) msg).isUploadstatus());
					}

				});
			return;
			case GETUSERFILES:
				ArrayList<String> arr = (ArrayList<String>) ((ClientServerMessage)msg).getL();
				RequestsScreenController._ins.setFilePaths(arr);
				return;
			default:
				return;
			}
		}
		if (msg instanceof User)
		{
			Platform.runLater(new Runnable() {
				public void run() {
					LoginScreenController._ins.LoginGood((User)msg);
				}
			});
		}
		if(msg instanceof String) {
			Platform.runLater(new Runnable() {
				public void run() {
					RequestsScreenController._ins.closeExtraWindow();
				}
			});
		}
		if(msg instanceof Report) {
			Platform.runLater(new Runnable() {
				public void run() {
					RequestsScreenController._ins.openAssessmentReportFunc((Report)msg);
				}
			});
		}
	}

	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Action declined.\nNo connection to server.");
			alert.showAndWait();
		}
	}

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);

	}
}
