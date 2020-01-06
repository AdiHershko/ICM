package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ManagerStatisticsController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ChoiceBox<?> reportChoiceBox;

    @FXML
    private Button managerBackBtn;

    @FXML
    private Pane periodReportDate;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private Button getReport1;

    @FXML
    private Pane periodRep;

    @FXML
    private Label openLabel;

    @FXML
    private Label freezeLabel;

    @FXML
    private Label closedLabel;

    @FXML
    private Label rejectedLabel;

    @FXML
    private Label daysLabel;

    @FXML
    private Pane performance;

    @FXML
    private Label extensionTotalLabel;

    @FXML
    private Label extensionMedianLabel;

    @FXML
    private Label extensionSDLabel;

    @FXML
    private Label extensionFDLabel;

    @FXML
    private Label addonsTotalLabel;

    @FXML
    private Label addonsMedianLabel;

    @FXML
    private Label addonsSDLabel;

    @FXML
    private Label addonsFDLabel;

    @FXML
    private Pane delaysPane;

    @FXML
    private ChoiceBox<?> systemCB;

    @FXML
    private Label delaysNum;

    @FXML
    private Label delaysMedian;

    @FXML
    private Label delaysSD;

    @FXML
    private Label delaysFD;

    @FXML
    void getReport1(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void managerBack(ActionEvent event) {

    }

}
