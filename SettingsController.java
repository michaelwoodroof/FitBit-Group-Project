// Contributor -- Michael Woodroof

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsController implements Initializable{

    // ALl Controls for FXML
    @FXML
    public Button btnAccPasswordChange;
    public Button btnBack;
    public Button btnChangePassword;
    public Button btnConfirmPassword;
    public Button btnDelete;
    public Button btnLogout;
    public GridPane mainPane;
    public Label lblFirstName;
    public Label lblEmail;
    public Label lblLastName;
    public PasswordField psfCheckPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get Account Type
        try {
            if (LoggedInUser.GetUserType().equals("User")) {
                UserAccounts uAccs = new UserAccounts();
                uAccs.importFile();

                UserAccount uAcc = uAccs.getAccountWithUsername(LoggedInUser.GetUsername());

                lblFirstName.setText(uAcc.getFirstName());
                lblLastName.setText(uAcc.getLastName());
                lblEmail.setText(uAcc.getEmailAddress());
            } else {
                CarerAccounts cAccs = new CarerAccounts();
                cAccs.importFile();

                CarerAccount cAcc = cAccs.getAccountWithUsername(LoggedInUser.GetUsername());

                lblFirstName.setText(cAcc.getFirstName());
                lblLastName.setText(cAcc.getLastName());
                lblEmail.setText(cAcc.getEmailAddress());

            }
        } catch (Exception e) {

        }

        GenerateAssets ga = new GenerateAssets();
        btnBack.setGraphic(ga.createIconButton("back.png", 30, 30));
        btnLogout.setGraphic(ga.createIconButton("logout.png", 30, 30));

    }

    public Boolean callAlert(String[] alerts) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(alerts[0]);
        alert.setHeaderText(alerts[1]);
        alert.setContentText(alerts[2]);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void loadMainUI() throws IOException {

        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();

        if (LoggedInUser.GetUserType().equals("Carer")) {
            ga.loadUI(stg, "Overview", "carerOverview.fxml", "carerOverview.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
        } else {
            ga.loadUI(stg, "My Overview", "userOverview.fxml", "overview.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
        }

    }

    @FXML
    public void logout() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        ga.logout(btnLogout);
    }

    @FXML
    public void confirmPassword() {
        try {
            UserAccounts uAccs = new UserAccounts();
            uAccs.importFile();

            UserAccount uAcc = uAccs.getAccountWithUsername(LoggedInUser.GetUsername());

            if (psfCheckPassword.getText().equals(uAcc.getPassword())) {
                // Show Password Change Code
                psfCheckPassword.setText("");
                btnConfirmPassword.setVisible(false);
                btnAccPasswordChange.setVisible(true);
            } else {
                // Throw Error

            }
        } catch (Exception e) {

        }

    }

    @FXML
    public void changePassword() {
        if (psfCheckPassword.getText().length() >= 8) {
            try {
                if (LoggedInUser.GetUserType().equals("Carer")) {
                    UserAccounts uAccs = new UserAccounts();
                    uAccs.importFile();

                    UserAccount uAcc = uAccs.getAccountWithUsername(LoggedInUser.GetUsername());

                    // Change Password
                    uAcc.setPassword(psfCheckPassword.getText());

                    // Save new Password
                    uAccs.exportFile();

                } else {
                    CarerAccounts cAccs = new CarerAccounts();
                    cAccs.importFile();

                    CarerAccount cAcc = cAccs.getAccountWithUsername(LoggedInUser.GetUsername());

                    // Change Password
                    cAcc.setPassword(psfCheckPassword.getText());

                    // Save new Password
                    cAccs.exportFile();
                }

                // Set Visibility States
                btnAccPasswordChange.setVisible(false);
                psfCheckPassword.setVisible(false);
                btnChangePassword.setVisible(true);

            } catch (Exception e) {

            }

        } else {
            // Throw Error

        }


    }

    @FXML
    public void loadPasCheck() {
        if (btnChangePassword.isVisible() == true) {
            psfCheckPassword.setVisible(true);
            btnConfirmPassword.setVisible(true);
            btnChangePassword.setVisible(false);
        } else {
            psfCheckPassword.setVisible(false);
            btnConfirmPassword.setVisible(false);
            btnChangePassword.setVisible(true);
        }
    }

    @FXML
    public void deleteAccount() throws IOException {
        // Check if they Want to
        String[] alertName = {"Delete Account", "This will delete your account",
        "Are you Sure?"};
        if (callAlert(alertName)) {
            if (LoggedInUser.GetUserType().equals("Carer")){
                CarerAccounts RegisteredAccounts = new CarerAccounts();
                RegisteredAccounts.importFile();
                CarerAccount CurrentAccount = RegisteredAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
                RegisteredAccounts.delete(CurrentAccount.getID());
            } else {
                UserAccounts RegisteredAccounts = new UserAccounts();
                RegisteredAccounts.importFile();
                UserAccount CurrentAccount = RegisteredAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
                RegisteredAccounts.delete(CurrentAccount.getID());
            }

            LoggedInUser.DeleteFile();

            GenerateAssets ga = new GenerateAssets();
            ga.logout(btnDelete);
        } else {

        }

    }

}
