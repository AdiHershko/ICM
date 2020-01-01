package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;


import Common.ClientServerMessage;
import Common.Enums;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

public class ExtensionRequestController {


	@FXML
	private TextArea descText;

	@FXML
	private Button submitButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Label reqIDLabel;

	public void initialize(){
		datePicker.setConverter(new StringConverter<LocalDate>()
		{
		    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

		    @Override
		    public String toString(LocalDate localDate)
		    {
		        if(localDate==null)
		            return "";
		        return dateTimeFormatter.format(localDate);
		    }

		    @Override
		    public LocalDate fromString(String dateString)
		    {
		        if(dateString==null || dateString.trim().isEmpty())
		        {
		            return null;
		        }
		        return LocalDate.parse(dateString,dateTimeFormatter);
		    }
		});
		reqIDLabel.setText("Request ID:"+Main.currentRequest.getId());

	}






	@FXML
	public void submitRequest(){ //add message to supervisor to show description
		DateTime dt;
		String temp;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			temp=(datePicker.getValue()).format(df);
		} catch (IllegalArgumentException e) {return; }
		Main.client.handleMessageFromClientUI(new ClientServerMessage(Enums.MessageEnum.ASKFOREXTENSION,Main.currentRequest,temp));
	}
}
