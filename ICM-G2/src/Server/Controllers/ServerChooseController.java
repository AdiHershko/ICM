package Server.Controllers;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerChooseController.
 */
public class ServerChooseController {

	/** The ins. */
	public static ServerChooseController _ins;
	
	/** The loading. */
	public static boolean loading = false;
	
	/** The connected. */
	public static boolean connected = false;
	
	/** The loadinganim. */
	private ImageView loadinganim;
	
	/** The hostfield. */
	// components
	@FXML
	private TextField hostfield;
	
	/** The server pane. */
	@FXML
	private Pane serverPane;

	/** The portfield. */
	@FXML
	private TextField portfield;

	/** The dbfield. */
	@FXML
	private TextField dbfield;

	/** The unfield. */
	@FXML
	private TextField unfield;

	/** The passfield. */
	@FXML
	private TextField passfield;
	
	/** The S port field. */
	@FXML
	private TextField S_portField;

	/** The connectbtn. */
	@FXML
	private Button connectbtn;
	
	/** The choice box. */
	@FXML
	private ChoiceBox<String> choiceBox = new ChoiceBox<String>();
	
	/** The sending messages label. */
	@FXML
	private Label sendingMessagesLabel;
	
	/** The loading anim. */
	@FXML
	private ImageView loadingAnim;

	/**
	 * Initialize.
	 */
	public void initialize() {
		_ins = this;
		loadinganim = new ImageView("loading.gif");
	}

	// getters and setters

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
	 * Gets the hostfield.
	 *
	 * @return the hostfield
	 */
	public TextField getHostfield() {
		return hostfield;
	}

	/**
	 * Sets the hostfield.
	 *
	 * @param hostfield the new hostfield
	 */
	public void setHostfield(TextField hostfield) {
		this.hostfield = hostfield;
	}

	/**
	 * Gets the portfield.
	 *
	 * @return the portfield
	 */
	public TextField getPortfield() {
		return portfield;
	}

	/**
	 * Sets the portfield.
	 *
	 * @param portfield the new portfield
	 */
	public void setPortfield(TextField portfield) {
		this.portfield = portfield;
	}

	/**
	 * Gets the dbfield.
	 *
	 * @return the dbfield
	 */
	public TextField getDbfield() {
		return dbfield;
	}

	/**
	 * Sets the dbfield.
	 *
	 * @param dbfield the new dbfield
	 */
	public void setDbfield(TextField dbfield) {
		this.dbfield = dbfield;
	}

	/**
	 * Gets the unfield.
	 *
	 * @return the unfield
	 */
	public TextField getUnfield() {
		return unfield;
	}

	/**
	 * Sets the unfield.
	 *
	 * @param unfield the new unfield
	 */
	public void setUnfield(TextField unfield) {
		this.unfield = unfield;
	}

	/**
	 * Gets the passfield.
	 *
	 * @return the passfield
	 */
	public TextField getPassfield() {
		return passfield;
	}

	/**
	 * Sets the passfield.
	 *
	 * @param passfield the new passfield
	 */
	public void setPassfield(TextField passfield) {
		this.passfield = passfield;
	}

	/**
	 * Gets the connectbtn.
	 *
	 * @return the connectbtn
	 */
	public Button getConnectbtn() {
		return connectbtn;
	}

	/**
	 * Sets the connectbtn.
	 *
	 * @param connectbtn the new connectbtn
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
	 * Connect.
	 *
	 * @param event the event
	 */
	@FXML
	void connect(ActionEvent event) {
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
	 * Disconnect.
	 */
	public void disconnect() {
		System.exit(1);
	}
	// tracking server choice

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
	 * Gets the loading anim.
	 *
	 * @return the loading anim
	 */
	public ImageView getLoadingAnim() {
		return loadingAnim;
	}

	/**
	 * Sets the loading anim.
	 *
	 * @param loadingAnim the new loading anim
	 */
	public void setLoadingAnim(ImageView loadingAnim) {
		this.loadingAnim = loadingAnim;
	}

}
