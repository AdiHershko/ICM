package Client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginScreenController {
	LoginScreenController _ins;
	
	public void initialize()
	{
		_ins=this;
	}

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
