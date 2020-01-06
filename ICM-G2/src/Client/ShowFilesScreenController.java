package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ShowFilesScreenController {

	@FXML
	private Button openFileButton;

	@FXML
	private ListView<String> fileListView;
	static ShowFilesScreenController _ins;

	private Request r;

	public void initialize(){
		_ins=this;
		r=Main.currentRequest;
		showList();
	}



	public void showList(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETUSERFILES, r));

	}

	public void openFile(){
		if (fileListView.getSelectionModel().getSelectedItem()==null) return;
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETFILEFROMSERVER,r.getId(),fileListView.getSelectionModel().getSelectedItem()));
	}


	public ListView<String> getFileListView(){
		return fileListView;
	}

}
