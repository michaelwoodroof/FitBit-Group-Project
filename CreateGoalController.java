// Contributor -- Michael Woodroof Ianto Jones

// Imports
import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CreateGoalController implements Initializable{

    // ALl Controls for FXML
    @FXML
    //back to useroverview/view goals
    public GridPane mainPane;
    public ChoiceBox cbxTypeGoal;
    public TextField txtGoalInt;
    public DatePicker datetTime;
    public Button btncreategoal;
    public Button btnLogout;
    public Button btnBack;
    public Button btnSettings;
    public Label lblGoalValue;
    public Label lblBTime;
    public Label lblWTime;

    public ChoiceBox cbxPresetGoal;
    public Button btnCreatePreset;

    public TextField txtBedTime;
    public TextField txtWakeUp;
    public Button btnSBT;
    public Button btnDeleteBT;

    // Static Instances
    public static BedTimes bTime = new BedTimes();
    public static UserAccount currentAcc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set Any Methods Here
        UserAccounts goalinput = new UserAccounts();
        goalinput.importFile();
        try {
            currentAcc = goalinput.getAccountWithUsername(LoggedInUser.GetUsername());
        } catch (IOException e) {

        }

        // Set Image Buttons
        GenerateAssets ga = new GenerateAssets();
        btnBack.setGraphic(ga.createIconButton("back.png", 30, 30));
        btnLogout.setGraphic(ga.createIconButton("logout.png", 30, 30));
        btnSettings.setGraphic(ga.createIconButton("settings.png", 30, 30));

    }

    @FXML
    public void loadSettings() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "Settings", "settings.fxml", "settings.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML
    public void createGoal() throws IOException {
        int goalIN = 0;
        try {
            goalIN = Integer.parseInt(txtGoalInt.getText());
            Goal_prediction goal = new Goal_prediction(currentAcc.getID(), currentAcc.getGender(), 29.0, Integer.parseInt(currentAcc.getWeight()),Integer.parseInt(currentAcc.getHeight()), cbxTypeGoal.getValue().toString(), goalIN);
            goal.compute();

            // Reset Style
            lblGoalValue.setStyle(null);
            lblGoalValue.setText("Goal Value");
            txtGoalInt.setStyle(null);

        } catch (NumberFormatException e) {
            // Style Change
            lblGoalValue.setStyle("-fx-text-fill: red;");
            lblGoalValue.setText("Invalid value");
            txtGoalInt.setStyle("-fx-border-width: 2px;");
            txtGoalInt.setStyle("-fx-border-color: red;");
        } catch (Exception e) {
            // Style Change
            lblGoalValue.setStyle("-fx-text-fill: red;");
            lblGoalValue.setText("Function error");
            txtGoalInt.setStyle("-fx-border-width: 2px;");
            txtGoalInt.setStyle("-fx-border-color: red;");
        }

    }

    @FXML
    public void createPreset() throws IOException {
        //create a goal from preset goal Values
        try {
            int goalVal = 0;
            String goalType = "";
            goalVal = Integer.parseInt(cbxPresetGoal.getValue().toString().split(" ")[0]);
            goalType = cbxPresetGoal.getValue().toString().split(" ")[1];
            Goal_prediction goalPreset = new Goal_prediction(currentAcc.getID(), currentAcc.getGender(), 29.0,Integer.parseInt(currentAcc.getWeight()),Integer.parseInt(currentAcc.getHeight()), goalType , goalVal);
            goalPreset.compute();
        } catch (Exception e) {

        }
    }

    @FXML
    public void back() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "View Goals", "userGoalsUpdate.fxml", "userGoals.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML
    public void cbedtime() {

        // Reset Style
        if (Regex.regexChecker(txtBedTime.getText(), Regex.regexPatterns(4))) {
            if (Regex.regexChecker(txtWakeUp.getText(), Regex.regexPatterns(4))) {
                lblWTime.setStyle(null);
                lblBTime.setStyle(null);
                txtBedTime.setStyle(null);
                txtBedTime.setText("Bedtime");
                txtWakeUp.setStyle(null);
                txtWakeUp.setText("Wakeup");
                bTime.add(currentAcc.getID(), txtBedTime.getText(), txtWakeUp.getText());
                bTime.exportFile();
            } else {
                lblWTime.setStyle("-fx-text-fill: red;");
                txtWakeUp.setText("Invalid Wakeup time (use format XXYY)");
                txtWakeUp.setStyle("-fx-border-width: 2px;");
                txtWakeUp.setStyle("-fx-border-color: red;");
            }
        } else {
            // Set Style
            lblBTime.setStyle("-fx-text-fill: red;");
            txtBedTime.setStyle("-fx-border-width: 2px;");
            txtBedTime.setStyle("-fx-border-color: red;");
            txtBedTime.setText("Invalid Bedtime (use format XXYY)");

            if (Regex.regexChecker(txtWakeUp.getText(), Regex.regexPatterns(4))) {
                lblWTime.setStyle("-fx-text-fill: red;");
                txtWakeUp.setText("Invalid Wakeup time (use format XXYY)");
                txtWakeUp.setStyle("-fx-border-width: 2px;");
                txtWakeUp.setStyle("-fx-border-color: red;");
            }
        }

    }

    @FXML
    public void dbedtime() {
      bTime.delete(currentAcc.getID());
      bTime.exportFile();
    }

    @FXML
    public void logout(ActionEvent e) throws IOException {
        GenerateAssets ga = new GenerateAssets();
        ga.logout(btnLogout);
    }

}
