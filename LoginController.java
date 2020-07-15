// Contributor -- Michael Woodroof

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

// Handles the Logic for the Login Page
public class LoginController implements Initializable {

    // ALl Controls for FXML
    @FXML
    public Button btnLogin;
    public Button btnRegister;
    public ChoiceBox cbxTypeAcc;
    public GridPane mainPane;
    public ImageView imgView;
    public Label lblUsername;
    public Label lblPassword;
    public PasswordField pasfPassword;
    public TextField txtfUsername;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set Any Methods Here
        imgView.setFitHeight(400);
        imgView.setFitWidth(400);
    }

    @FXML
    public void registerLoad(ActionEvent event) throws IOException {
        // Change Current Dimensions of Stage
        Stage stage = (Stage) mainPane.getScene().getWindow();

        stage.setTitle("Register for an Account");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setResizable(false);

        // Load Register (Switch Scene)
        AnchorPane pane = FXMLLoader.load(getClass().getResource("xml/register_stage_one.fxml"));
        mainPane.getChildren().setAll(pane);

    }

    // Methods
    @FXML
    public void checkKey(KeyEvent key) throws IOException {
        if (key.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    public void callAlerts() {
        Alerts Item = new Alerts();
        Item.start();
    }

    public void authCodeCheck(){
        UserAccounts userAcc = new UserAccounts();
        userAcc.importFile();
        UserAccount user = null;
        user = userAcc.getAccountWithUsername(txtfUsername.getText());
        String authCode = user.getAuthorizationCode();

        //IF False send notification.
        boolean Code = FitbitAPI.CheckCode(authCode);
        if (Code == false) {
            Notifications.Display("You need to generate a new Fitbit Code.", "Fitbit code Oudated", "Fitbit");
        }
    }

    @FXML
    public void login() throws IOException {
        // Sets Whether it is a User or CareGiver login
    	Boolean logged = false;
        Boolean isUser = true;
        if (cbxTypeAcc.getValue().toString().equals(" CareGiver")) {
            isUser = false;
        }

        // Replace with Login Method
        // Below is Example REPLACE

        if (isUser == false) {
        	CarerAccounts carerAccounts = new CarerAccounts();
        	Vector<CarerAccount> users = new Vector<CarerAccount>();
        	users = carerAccounts.getUsers();
        	carerAccounts.importFile();
        	users = carerAccounts.getUsers();
        	String username = txtfUsername.getText();
        	String password = pasfPassword.getText();

        	for (int i = 0; i < users.size(); i++) {
        		if (users.get(i).getUsername().equals(username)) {
        			if (users.get(i).getPassword().equals(password)) {
        				LoggedInUser.ExportToFile(username, "Carer");
        				logged = true;
						callAlerts();
        				break;
        			}
        		}
        	}
        }

        if (isUser == true) {
        	UserAccounts userAccounts = new UserAccounts();
        	Vector<UserAccount> users = new Vector<UserAccount>();
        	userAccounts.importFile();
        	users = userAccounts.getUsers();
        	String username = txtfUsername.getText();
        	String password = pasfPassword.getText();

        	for (int i = 0; i < users.size(); i++) {
        		if (users.get(i).getUsername().equals(username)) {
        			if (users.get(i).getPassword().equals(password)) {
        				LoggedInUser.ExportToFile(username, "User");
        				logged = true;
						callAlerts();
        				break;
        			}
        		}
        	}
        }

    	if (logged == true) {

            // Load New Window
            String path = "";
            String cssPath = "";
            String title = "";
            if (isUser == true) {
            	path = "xml/userOverview.fxml";
            	cssPath = "css/overview.css";
            	title = "My Overview";
            }
            else {
                path = "xml/carerOverview.fxml";
                cssPath = "css/carerOverview.css";
                title = "Carer Overview";
            }

            try {
                // Close Scene
                Application.setUserAgentStylesheet(null);
                Stage loginStage = (Stage) btnLogin.getScene().getWindow();
                loginStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(path));
                Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
                scene.getStylesheets().clear();
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/assets/icons/loginIcon.png"));
                stage.setMinWidth(640);
                stage.setMinHeight(480);
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();


            } catch (IOException e) {}

    	} else {
            // When Login is invalid Basic Stlying needs Polish
            lblUsername.setStyle("-fx-text-fill: red;");
            lblUsername.setText(" Invalid Username");
            lblPassword.setStyle("-fx-text-fill: red;");
            lblPassword.setText(" Invalid Password");

            // Wipe Fields
            txtfUsername.setText("");
            pasfPassword.setText("");
        }
    }
}
