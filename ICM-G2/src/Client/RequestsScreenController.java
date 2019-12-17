package Client;

import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestsScreenController {


	@FXML
	private Button addFilesButton;
	@FXML
	private TableView<Request> tableView;
	public static RequestsScreenController _ins;

	public void initialize()
	{
		_ins=this;
		TableSetup();
		RefreshTable();
	}



	@FXML
	public void addFiles()
	{
		System.out.println("button");
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
	}

	public void RefreshTable()
	{
		Main.client.handleMessageFromClientUI("REFRESH");
	}







}
