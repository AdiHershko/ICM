package Server.Controllers;

import java.io.File;

import Server.DataBaseController;
import Server.EchoServer;
import Server.EmailService;
import Server.ServerMain;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

/**
 * The Class ServerChooseController.
 * Controller for the server GUI (servergui.fxml)
 */
public class ServerChooseController {

	/** The ins. */
	public static ServerChooseController _ins;

	/** The loading boolean. */
	public static boolean loading = false;

	/** The connected boolean. */
	public static boolean connected = false;

	/** The loadingan image. */
	private ImageView loadinganim;

	/** The host text field. */
	@FXML
	private TextField hostfield;

	/** The server pane. */
	@FXML
	private Pane serverPane;

	/** The port text field. */
	@FXML
	private TextField portfield;

	/** The db text field. */
	@FXML
	private TextField dbfield;

	/** The user name field. */
	@FXML
	private TextField unfield;

	/** The password field. */
	@FXML
	private PasswordField passfield;

	/** The S port field. */
	@FXML
	private TextField S_portField;

	/** The connection button. */
	@FXML
	private Button connectbtn;

	/** The choice box. */
	@FXML
	private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

	/** The sending messages label. */
	@FXML
	private Label sendingMessagesLabel;

	/** The loading animation. */
	@FXML
	private ImageView loadingAnim;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		loadinganim = new ImageView("loading.gif");
	}


	/**
	 * Gets the server pane.
	 *
	 * @return the server pane
	 */
	public Pane getServerPane() {
		return serverPane;
	}

	/**
	 * Gets the s port field.
	 *
	 * @return the s port field
	 */
	public TextField getS_portField() {
		return S_portField;
	}

	/**
	 * Sets the s port field.
	 *
	 * @param s_portField the new s port field
	 */
	public void setS_portField(TextField s_portField) {
		S_portField = s_portField;
	}

	/**
	 * Gets the host field.
	 *
	 * @return the host field
	 */
	public TextField getHostfield() {
		return hostfield;
	}

	/**
	 * Sets the host field.
	 *
	 * @param hostfield the new host field
	 */
	public void setHostfield(TextField hostfield) {
		this.hostfield = hostfield;
	}

	/**
	 * Gets the port field.
	 *
	 * @return the port field
	 */
	public TextField getPortfield() {
		return portfield;
	}

	/**
	 * Sets the port field.
	 *
	 * @param portfield the new port field
	 */
	public void setPortfield(TextField portfield) {
		this.portfield = portfield;
	}

	/**
	 * Gets the db field.
	 *
	 * @return the db field
	 */
	public TextField getDbfield() {
		return dbfield;
	}

	/**
	 * Sets the db field.
	 *
	 * @param dbfield the new db field
	 */
	public void setDbfield(TextField dbfield) {
		this.dbfield = dbfield;
	}

	/**
	 * Gets the username field.
	 *
	 * @return the username field
	 */
	public TextField getUnfield() {
		return unfield;
	}

	/**
	 * Sets the username field.
	 *
	 * @param unfield the new username field
	 */
	public void setUnfield(TextField unfield) {
		this.unfield = unfield;
	}

	/**
	 * Gets the password field.
	 *
	 * @return the password field
	 */
	public PasswordField getPassfield() {
		return passfield;
	}

	/**
	 * Sets the password field.
	 *
	 * @param passfield the new password field
	 */
	public void setPassfield(PasswordField passfield) {
		this.passfield = passfield;
	}

	/**
	 * Gets the connect button.
	 *
	 * @return the connect button
	 */
	public Button getConnectbtn() {
		return connectbtn;
	}

	/**
	 * Sets the connect button.
	 *
	 * @param connectbtn the new connect button
	 */
	public void setConnectbtn(Button connectbtn) {
		this.connectbtn = connectbtn;
	}

	/**
	 * Gets the choice box.
	 *
	 * @return the choice box
	 */
	public ChoiceBox<String> getChoiceBox() {
		return choiceBox;
	}

	/**
	 * Sets the server pane.
	 *
	 * @param serverPane the new server pane
	 */
	public void setServerPane(Pane serverPane) {
		this.serverPane = serverPane;
	}

	// system connection

	/**
	 * Connect button function.
	 *
	 * @param event the mouse click
	 */
	@FXML
	void connect(ActionEvent event) {
		File srcFolder = new File("src\\");
		if (!srcFolder.exists())
		{
			if (!srcFolder.mkdir())
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Cannot create resource folder");
				alert.showAndWait();
				return;
			}
		}
		loading = true;
		loadinganim.setScaleX(0.2);
		loadinganim.setScaleY(0.2);
		loadinganim.setX(ServerMain.root.getPrefWidth() / 4);
		loadinganim.setY(ServerMain.root.getPrefHeight() / 8.0);
		loadinganim.setVisible(true);
		loadinganim.setVisible(true);
		new Thread(new Runnable() {
			public void run() {
				Platform.runLater(() -> ServerMain.root.getChildren().add(loadinganim));
				if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
					String temp = "jdbc:mysql://";
					temp += hostfield.getText();
					temp += ":";
					temp += portfield.getText();
					temp += "/";
					temp += dbfield.getText();
					temp += "?useLegacyDatetimeCode=false&serverTimezone=UTC";
					DataBaseController.setUrl(temp);
					DataBaseController.setPassword(passfield.getText());
					DataBaseController.setUsername(unfield.getText());
				}

				int temp = 1;
				try {
					temp = EchoServer.Start(Integer.parseInt((getS_portField().getText())));
				} catch (NumberFormatException e) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR!");
						alert.setContentText("No number in port field");
						alert.show();
					});
					return;
				}
				if (temp == 1) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR!");
						alert.setContentText("Connection to DB failed");
						alert.show();
					});

				} else if (temp == 2) {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR!");
						alert.setContentText("Can't listen to client");
						alert.show();
					});
				} else {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successful");
						alert.setContentText("System connected!");
						alert.show();
					});
					connected = true;
					EmailService.getInstannce();
				}
				while (loading) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				}
				Platform.runLater(new Runnable() {
					public void run() {
						ServerMain.root.getChildren().remove(loadinganim);
					}
				});
			}
		}).start();
	}

	/**
	 * Disconnect server (for button and window close).
	 */
	public void disconnect() {
		System.exit(1);
	}

	/**
	 * Sets the choice box.
	 */
	public void setChoiceBox() {
		choiceBox.getItems().add("Our Remote SQL Database");
		choiceBox.getItems().add("Local SQL Server, needs to have scheme and tables for ICM");
		choiceBox.setValue("Our Remote SQL Database");
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
					serverPane.setVisible(false);
				} else {
					serverPane.setVisible(true);
				}
			}

		});

	}

	/**
	 * Gets the sending messages label.
	 *
	 * @return the sending messages label
	 */
	public Label getSendingMessagesLabel() {
		return sendingMessagesLabel;
	}

	/**
	 * Sets the sending messages label.
	 *
	 * @param sendingMessagesLabel the new sending messages label
	 */
	public void setSendingMessagesLabel(Label sendingMessagesLabel) {
		this.sendingMessagesLabel = sendingMessagesLabel;
	}

	/**
	 * Gets the loading animation.
	 *
	 * @return the loading animation
	 */
	public ImageView getLoadingAnim() {
		return loadingAnim;
	}

	/**
	 * Sets the loading animation.
	 *
	 * @param loadingAnim the new loading animation
	 */
	public void setLoadingAnim(ImageView loadingAnim) {
		this.loadingAnim = loadingAnim;
	}

}
