package Client;

import java.io.IOException;

import Common.ClientServerMessage;
import Common.Enums.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginScreenController {
	static LoginScreenController _ins;

	public void initialize()
	{
		ChatClient client;
		_ins=this;
		try {
		 client = new ChatClient("localhost",ChatClient.DEFAULT_PORT);
		} catch (IOException e) {
			System.out.println("Cannot connect to the server");
			return;
		}
		Main.client=client;
		Main.client.handleMessageFromClientUI(new ClientServerMessage(MessageEnum.CONNECT));	}

	@FXML
	private Button loginButton;


	@FXML
	public void MoveScreen(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("RequestsScreen.fxml"));
		Scene requests = new Scene(root);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(requests);
		window.show();

	}

}
