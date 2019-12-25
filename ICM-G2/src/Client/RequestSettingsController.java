package Client;

import Common.ClientServerMessage;
import Common.Enums;
import Common.Request;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RequestSettingsController {
	String[] texts;
	Request currentRequest;
	@FXML
	private TextField assesmentAppointerText;
	@FXML
	private TextField examaningDueDateText;
	@FXML
	private TextField executionAppointerText;
	@FXML
	private TextField testDueDateText;
	@FXML
	private TextField assesmentDueDateText;
	@FXML
	private TextField assesmentExtensionDateText;
	@FXML
	private TextField examiningDueDateText;
	@FXML
	private TextField executionDueDateText;
	@FXML
	private TextField executionExtenstionDateText;
	@FXML
	private TextField testerExtensionDateText;

	public void initialize()
	{
		texts=new String[10];
		currentRequest=Main.currentRequest;
		setScreen();
	}



	public void setScreen(){
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.STAGESSCREEN,currentRequest));

	}

}
