package Server;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

public class ServerChooseController {

	public static boolean loading=false;
	public static boolean connected = false;
	private ImageView loadinganim;
	// components
	@FXML
	private TextField hostfield;
	@FXML
	private Pane serverPane;

	@FXML
	private TextField portfield;

	@FXML
	private TextField dbfield;

	@FXML
	private TextField unfield;

	@FXML
	private TextField passfield;
	@FXML
	private TextField S_portField;

	@FXML
	private Button connectbtn;
	@FXML
	private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

	public void initialize(){
		loadinganim = new ImageView("Server\\loading.gif");
	}

	// getters and setters

	public Pane getServerPane() {
		return serverPane;
	}

	public TextField getS_portField() {
		return S_portField;
	}

	public void setS_portField(TextField s_portField) {
		S_portField = s_portField;
	}

	public TextField getHostfield() {
		return hostfield;
	}

	public void setHostfield(TextField hostfield) {
		this.hostfield = hostfield;
	}

	public TextField getPortfield() {
		return portfield;
	}

	public void setPortfield(TextField portfield) {
		this.portfield = portfield;
	}

	public TextField getDbfield() {
		return dbfield;
	}

	public void setDbfield(TextField dbfield) {
		this.dbfield = dbfield;
	}

	public TextField getUnfield() {
		return unfield;
	}

	public void setUnfield(TextField unfield) {
		this.unfield = unfield;
	}

	public TextField getPassfield() {
		return passfield;
	}

	public void setPassfield(TextField passfield) {
		this.passfield = passfield;
	}

	public Button getConnectbtn() {
		return connectbtn;
	}

	public void setConnectbtn(Button connectbtn) {
		this.connectbtn = connectbtn;
	}

	public ChoiceBox<String> getChoiceBox() {
		return choiceBox;
	}

	public void setServerPane(Pane serverPane) {
		this.serverPane = serverPane;
	}

	// system connection

	@FXML
	void connect(ActionEvent event) {
		loading=true;
		loadinganim.setScaleX(0.2);
		loadinganim.setScaleY(0.2);
		loadinganim.setX(ServerConsole.root.getPrefWidth()/4);
		loadinganim.setY(ServerConsole.root.getPrefHeight()/8.0);
		loadinganim.setVisible(true);
		loadinganim.setVisible(true);
		new Thread(new Runnable(){
			public void run()
			{
				Platform.runLater(()->ServerConsole.root.getChildren().add(loadinganim));
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
					Platform.runLater(()->{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR!");
					alert.setContentText("No number in port field");
					alert.show();
					});
					return;
				}
				if (temp == 1) {
					Platform.runLater(()->{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR!");
					alert.setContentText("Connection to DB failed");
					alert.show();
					});

				}
				else if (temp == 2) {
					Platform.runLater(()->{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR!");
					alert.setContentText("Can't listen to client");
					alert.show();
					});
				}
				else {
					Platform.runLater(()->{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Successful");
					alert.setContentText("System connected!");
					alert.show();
					});
					connected = true;
					DataBaseController.genAutoMessages();
				}
				while (loading)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				}
				Platform.runLater(new Runnable(){
					public void run(){
						ServerConsole.root.getChildren().remove(loadinganim);
					}
				});
			}
		}).start();
	}

	public void disconnect() {
		System.exit(1);
	}
	//tracking server choice

	public void setChoiceBox() {
		choiceBox.getItems().add("Our Remote SQL Database");
		choiceBox.getItems().add("Local SQL Server, needs to have scheme and tables for ICM");
		choiceBox.setValue("Our Remote SQL Database");
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
					serverPane.setVisible(false);
				}
				else {
					serverPane.setVisible(true);
				}
			}

		});

	}

}
