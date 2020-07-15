// Contributor -- Ianto Jones

// Imports
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Window;
import javafx.stage.Stage;

public class ViewGoalsController implements Initializable {

    // ALl Controls for FXML
    @FXML
    public Button btnBack;
    public Button btncreategoal;
    public Button btnLogout;
    public Button btnSettings;
    public GridPane mainPane;
    public VBox vboxx; //for dynamically creating the progress bars
    public VBox vboxgoaltype; //dynamic creation of goal type display
    public VBox vbxDaysTill;
    public VBox vbxSetOn;
    public VBox vbxCompletion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //dynamically create progress bars and labels for goals already stored in the system

        ArrayList<String> goals = new ArrayList<String>();
        try {
            UserAccounts userAccounts = new UserAccounts();
            userAccounts.importFile();
            UserAccount user = userAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
            goals = Goal_prediction.load(user.getID());
        } catch (Exception e) {

        }

        // Set Image Buttons
        GenerateAssets ga = new GenerateAssets();
        btnBack.setGraphic(ga.createIconButton("back.png", 30, 30));
        btnLogout.setGraphic(ga.createIconButton("logout.png", 30, 30));
        btnSettings.setGraphic(ga.createIconButton("settings.png", 30, 30));

        // Get number of goals that the user has from Goal_prediction.java
        int numgoals = 0;
        numgoals = goals.size();

        for (int i = 0; i < numgoals; i++) {

            //Set Up Infomation
            String[] str = goals.get(i).split(" ");

            // Set Goal type
            ImageView img = new ImageView();
            File file;

            if (str[2].equals("step")) {
                file = new File("assets/icons/steps.png");
            } else {
                file = new File("assets/icons/weight.png");
            }

            Image image = new Image(file.toURI().toString());
            img.setImage(image);
            img.setFitHeight(20);
            img.setFitWidth(20);

            // Assign Goal Info
            Label goalDaysTill = new Label(str[1]);
            goalDaysTill.setPrefHeight(25);

            Label goalSetOn = new Label(str[3]);
            goalSetOn.setPrefHeight(25);

            Label goalCompleted = new Label(str[4]);
            goalCompleted.setPrefHeight(25);

            // Add to VBoxs
            vboxgoaltype.getChildren().add(img);
            vbxDaysTill.getChildren().add(goalDaysTill);
            vbxSetOn.getChildren().add(goalSetOn);
            vbxCompletion.getChildren().add(goalCompleted);

            // Set Margins
            vboxgoaltype.setMargin(img, new Insets(5, 0, 0, 0));
        }
    }

    @FXML
    public void loadSettings() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "Settings", "settings.fxml", "settings.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML
    public void backbutton(ActionEvent e) throws IOException {
      //back to user overview
      GenerateAssets ga = new GenerateAssets();
      Stage stg = (Stage) mainPane.getScene().getWindow();
      ga.loadUI(stg, "My Overview", "userOverview.fxml", "overview.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML
    public void creategoal(ActionEvent e) throws IOException {
        //go to create goal Page
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "Create Goal", "createGoalupdate.fxml", "userGoals.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML
    public void logout(ActionEvent e) throws IOException {
        //Logs the user out of the system
        GenerateAssets ga = new GenerateAssets();
        ga.logout(btnLogout);
    }

}
