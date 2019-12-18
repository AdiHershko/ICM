package Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RequestsScreenController {
	public static RequestsScreenController _ins;


	@FXML
	private Button addFilesButton;
	@FXML
	private TableView<Request> tableView;
	@FXML
	private Pane GeneralViewRequest1;
	@FXML
	private TextArea descArea;
	@FXML
	private TextArea changeArea;
	@FXML
	private TextArea reasonArea;
	@FXML
	private TextArea commentsArea;
	@FXML
	private Pane UserViewsRequest1;
	@FXML
	private Label requestIDLabel;
	@FXML
	private Label systemLabel;
	@FXML
	private Label stageLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label userNameLabel;


	public void initialize()
	{
		_ins=this;
		TableSetup();
		RefreshTable();
		new Thread(){
			public void run()
			{
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				 LocalDateTime now = LocalDateTime.now();
				 dateLabel.setText(dtf.format(now));
				userNameLabel.setText(Main.currentUser.getFirstName()+" "+Main.currentUser.getLastName());
			}
		}.start();
	}



	@FXML
	public void addFiles(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a file to add");
		fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
	}

	public TableView<Request> getTableView()
	{
		return tableView;
	}



	public void TableSetup()
	{
		TableColumn<Request, Integer> idColumn = new TableColumn<>("Request ID");
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Request, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
		TableColumn<Request,String> stageColumn = new TableColumn<>("Stage");
		stageColumn.setCellValueFactory(new PropertyValueFactory("currentStage"));
		tableView.getColumns().addAll(idColumn,statusColumn,stageColumn);
		for (TableColumn<Request,?> col : tableView.getColumns())
			col.setMinWidth(100);
	}

	public void RefreshTable()
	{
		String UserID = "";//Itay - put here
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.REFRESH,UserID));
	}

	@FXML
	public void showRequest()
	{
		try {
		Request r;
		GeneralViewRequest1.setVisible(true);
		UserViewsRequest1.setVisible(true);
		r = tableView.getSelectionModel().getSelectedItem();
		descArea.setText(r.getDescription());
		changeArea.setText(r.getChanges());
		reasonArea.setText(r.getChangeReason());
		commentsArea.setText(r.getComments());
		requestIDLabel.setText(""+r.getId());
		systemLabel.setText(r.getSystem().toString());
		stageLabel.setText(r.getCurrentStage());
		statusLabel.setText(r.getStatus());
		} catch (Exception e) { }
	}

}
