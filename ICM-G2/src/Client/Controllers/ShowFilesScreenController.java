package Client.Controllers;

import Client.ClientMain;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The Class ShowFilesScreenController.
 * Controller for 1.2-ShowFilesScreen.fxml
 */
public class ShowFilesScreenController {

	/** The open file button. */
	@FXML
	private Button openFileButton;

	/** The file list view. */
	@FXML
	private ListView<String> fileListView;
	
	/** The ins. */
	public static ShowFilesScreenController _ins;

	/** The request. */
	private Request r;

	/**
	 * Initialize the fxml.
	 */
	public void initialize() {
		_ins = this;
		r = ClientMain.currentRequest;
		showList();
	}

	/**
	 * Show list.
	 */
	public void showList() {
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETUSERFILES, r));

	}

	/**
	 * Open file.
	 */
	public void openFile() {
		if (fileListView.getSelectionModel().getSelectedItem() == null)
			return;
		ClientMain.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.GETFILEFROMSERVER, r.getId(),
				fileListView.getSelectionModel().getSelectedItem()));
	}

	/**
	 * Gets the file list view.
	 *
	 * @return the file list view
	 */
	public ListView<String> getFileListView() {
		return fileListView;
	}

}
