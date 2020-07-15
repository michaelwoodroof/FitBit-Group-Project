// Contributor -- Michael Woodroof

// Imports
import org.json.*;

import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarerOverviewController implements Initializable{

    // ALl Controls for FXML
    public Button btnLogout;
    public Button btnRefresh;
    public Button btnSettings;
    public FlowPane flwContainer;
    public GridPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get Account
        CarerAccounts cAccs = new CarerAccounts();
        cAccs.importFile();

        try {
            // Get Account
            if (cAccs.Search(LoggedInUser.GetUsername()) != null) {
                CarerAccount cAcc = cAccs.Search(LoggedInUser.GetUsername());

                // Import UserAccounts
                UserAccounts uAccs = new UserAccounts();
                uAccs.importFile();

                // Go through Each User
                for (int i = 0; i < cAcc.getUsers().length; i++) {

                    FitbitData fbData = new FitbitData();

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                	LocalDate localDate = LocalDate.now();
                	String todayDate = dtf.format(localDate);

                    // Use to Retrieve other Details
                    UserAccount currentAcc = uAccs.getAccountWithID(cAcc.getUsers()[i]);

                    // Import Latest FitBit Data
                    int[] stepsValue = fbData.getStepsDataDay(todayDate, currentAcc.getAuthorizationCode());
                    int[] heartRateValue = fbData.getHRDataMin(todayDate, currentAcc.getAuthorizationCode());
                    int[] sleepValue = FitbitData.getSleepDuration(todayDate, currentAcc.getAuthorizationCode());

                    // Check for Null Values
                    if (stepsValue.length == 0) {
                        stepsValue = new int[]{0, 0};
                    }

                    if (heartRateValue.length == 0) {
                        heartRateValue = new int[]{0, 0};
                    }

                    if (sleepValue.length == 0) {
                        sleepValue = new int[]{0};
                    }

                    // Create UI Controls
                    GenerateAssets ga = new GenerateAssets();

                    GridPane container = new GridPane();
                    container.getStyleClass().add("grdContainer");

                    for (int j = 0; j < 4; j++) {
                        RowConstraints row = new RowConstraints();
                        if (j == 1) {
                            row = new RowConstraints(30);
                        } else {
                            row = new RowConstraints(50);
                        }
                        container.getRowConstraints().add(row);
                    }

                    container.getColumnConstraints().add(new ColumnConstraints(400));

                    ImageView imgProfile = new ImageView();
                    imgProfile.getStyleClass().add("imgProfile");

                    File file = new File("assets/icons/placeholder.png");
                    Image image = new Image(file.toURI().toString());
                    imgProfile.setImage(image);
                    imgProfile.setFitHeight(100);
                    imgProfile.setFitWidth(100);

                    Label lblName = new Label(currentAcc.getFirstName() + " " + currentAcc.getLastName());
                    lblName.getStyleClass().add("lblName");

                    HBox hbxVitalsImage = new HBox();
                    hbxVitalsImage.setId("images");
                    hbxVitalsImage.getStyleClass().add("hbxVitals");

                    // Vital Images
                    int hImg = 22;
                    int wImg = 22;

                    // Set Margins // Top Right Bottom Left
                    ImageView imgVitalSleep = new ImageView();
                    file = new File("assets/icons/sleep.png");
                    image = new Image(file.toURI().toString());
                    imgVitalSleep.setImage(image);
                    imgVitalSleep.setFitHeight(hImg);
                    imgVitalSleep.setFitWidth(wImg);

                    ImageView imgVitalHeart = new ImageView();
                    file = new File("assets/icons/heart.png");
                    image = new Image(file.toURI().toString());
                    imgVitalHeart.setImage(image);
                    imgVitalHeart.setFitHeight(hImg);
                    imgVitalHeart.setFitWidth(wImg);

                    ImageView imgVitalSteps = new ImageView();
                    file = new File("assets/icons/steps.png");
                    image = new Image(file.toURI().toString());
                    imgVitalSteps.setImage(image);
                    imgVitalSteps.setFitHeight(hImg);
                    imgVitalSteps.setFitWidth(wImg);

                    hbxVitalsImage.setMargin(imgVitalSleep, new Insets(0, 0, 0, 39));
                    hbxVitalsImage.setMargin(imgVitalHeart, new Insets(0, 39, 0, 78));
                    hbxVitalsImage.setMargin(imgVitalSteps, new Insets(0, 0, 0, 39));

                    // Add to HBox
                    ObservableList listVitalsImage = hbxVitalsImage.getChildren();
                    listVitalsImage.addAll(imgVitalSleep, imgVitalHeart, imgVitalSteps);

                    HBox hbxVitals = new HBox();
                    hbxVitals.setId("" + cAcc.getUsers()[i]);
                    hbxVitals.getStyleClass().add("hbxVitals");

                    // Vital UI Controls
                    Label lblSteps = new Label("" + stepsValue[stepsValue.length - 1] + " Steps");
                    lblSteps.setId("stepsData");
                    lblSteps.getStyleClass().add("lblVital");

                    Double normalisedSleep = Double.valueOf(sleepValue[0]) / 3600000;

                    Label lblSleep = new Label("" + String.format("%.2f", normalisedSleep) + " Hours");
                    lblSleep.setId("sleepData");
                    lblSleep.getStyleClass().add("lblVital");

                    Label lblHeartrate = new Label("" + heartRateValue[heartRateValue.length - 1] + " BPM");
                    lblHeartrate.setId("heartData");
                    lblHeartrate.getStyleClass().add("lblVital");

                    // Add to HBox
                    ObservableList list = hbxVitals.getChildren();
                    list.addAll(lblSleep, lblHeartrate, lblSteps);

                    // UI Controls
                    Button btnPhone = new Button();
                    btnPhone.getStyleClass().add("btnPhone");
                    btnPhone.setGraphic(ga.createIconButton("phone.png", 35, 35));

                    Button btnMoreDetail = new Button();
                    btnMoreDetail.getStyleClass().add("btnMoreDetail");
                    btnMoreDetail.setId("" + cAcc.getUsers()[i]);
                    btnMoreDetail.setGraphic(ga.createIconButton("moreDetail.png", 35, 167));

                    // Set Method
                    btnMoreDetail.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // Load UserOverview.fxml
                            try {
                                Button source = (Button) event.getSource();
                                String id = source.getId();

                                // Get UserAccount from this ID
                                UserAccounts allAccs = new UserAccounts();
                                uAccs.importFile();
                                UserAccount clickedAcc = uAccs.getAccountWithID(Integer.parseInt(id));

                                Stage stage = (Stage) mainPane.getScene().getWindow();
                                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("xml/userOverview.fxml"));
                                Parent root = (Parent)fxmlloader.load();

                                // Load Methods
                                UserOverviewController controller = fxmlloader.<UserOverviewController>getController();
                                // Can Call Methods from other Controllers with this
                                controller.setAuthCode(clickedAcc.getAuthorizationCode());
                                controller.setTitle(clickedAcc.getFirstName() + " " + clickedAcc.getLastName() + " Overview");


                                Scene scene = new Scene(root);

                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {

                            }
                        }
                    });

                    // Set Margins // Top Right Bottom Left
                    container.setMargin(btnPhone, new Insets(0, 0, 0, 20));
                    container.setMargin(btnMoreDetail, new Insets(0, 20, 0, 0));
                    container.setMargin(imgProfile, new Insets(0, 0, 60, 0));

                    // Add UI Controls to Gridpane
                    container.add(imgProfile, 0, 0, 1, 1);
                    container.add(lblName, 0, 1, 1, 1);
                    container.add(hbxVitalsImage, 0, 2, 1, 1);
                    container.add(hbxVitals, 0, 3, 1, 1);
                    container.add(btnPhone, 0, 4, 1, 1);
                    container.add(btnMoreDetail, 0, 4, 1, 1);

                    // Set Constraints
                    container.setHalignment(lblName, HPos.CENTER);
                    container.setHalignment(hbxVitalsImage, HPos.CENTER);
                    container.setHalignment(hbxVitals, HPos.CENTER);
                    container.setHalignment(imgProfile, HPos.CENTER);
                    container.setHalignment(btnMoreDetail, HPos.RIGHT);

                    // Add Gridpane to Flowpane
                    flwContainer.getChildren().add(container);
                    flwContainer.setMargin(container, new Insets(80, 0, 0, 0));

                    // Set Image Buttons
                    btnLogout.setGraphic(ga.createIconButton("logout.png", 30, 30));
                    btnSettings.setGraphic(ga.createIconButton("settings.png", 30, 30));
                    btnRefresh.setGraphic(ga.createIconButton("reload.png", 30, 30));
                }

            } else {
                // Handle
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void logout() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        ga.logout(btnLogout);
    }

    @FXML
    public void loadSettings() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "Settings", "settings.fxml", "settings.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }

    @FXML // Used to Refresh Data
    public void refresh() {
        // Init Values
        FitbitData fbData = new FitbitData();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String todayDate = dtf.format(localDate);

        UserAccounts uAccs = new UserAccounts();
        uAccs.importFile();

        // Get Labels
        for (Node node : flwContainer.getChildren()) {
            if (node instanceof GridPane) {
                for (Node nodeInGrid : ((GridPane)node).getChildren()) {

                    if (nodeInGrid instanceof HBox && !(nodeInGrid.getId().equals("images"))) {
                        HBox hbx = (HBox) nodeInGrid;
                        UserAccount currentAcc = uAccs.getAccountWithID(Integer.parseInt(hbx.getId()));
                        for (Node nodeInHBox : ((HBox)nodeInGrid).getChildren()) {

                            if (nodeInHBox instanceof Label) {
                                // Perform Function

                                Label surrogate = (Label) nodeInHBox;

                                if (surrogate.getId().equals("stepsData")) {
                                    int[] stepsValue = fbData.getStepsDataDay(todayDate, currentAcc.getAuthorizationCode());
                                    if (stepsValue.length > 0) {
                                        ((Label)nodeInHBox).setText("" + stepsValue[stepsValue.length - 1] + " Steps");
                                    } else {
                                        ((Label)nodeInHBox).setText("0 Steps");
                                    }
                                } else if (surrogate.getId().equals("sleepData")) {
                                    int[] sleepValue = FitbitData.getSleepDuration(todayDate, currentAcc.getAuthorizationCode());
                                    Double normalisedSleep = 0.0;
                                    if (sleepValue.length > 0) {
                                        normalisedSleep = Double.valueOf(sleepValue[0]) / 3600000;
                                    }
                                    ((Label)nodeInHBox).setText("" + String.format("%.2f", normalisedSleep) + " Hours");
                                } else if (surrogate.getId().equals("heartData")) {
                                    int[] heartRateValue = fbData.getHRDataMin(todayDate, currentAcc.getAuthorizationCode());
                                    if (heartRateValue.length > 0) {
                                        ((Label)nodeInHBox).setText("" + heartRateValue[heartRateValue.length - 1] + " BPM");
                                    } else {
                                        ((Label)nodeInHBox).setText("0 BPM");
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
