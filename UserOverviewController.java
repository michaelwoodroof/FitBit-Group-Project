// Contributor -- Michael Woodroof, Janrey Mosuela, Akash Deoraj

// Imports
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UserOverviewController implements Initializable{

    // Properties
    public String currentTimeFrame; // Used to Show Graph Within Given Time Frame
    public String currentDataType; // Used to Determine what FitBitData to Draw
    public String currentDate; // Used to Determine which date to use for graph
    public int userID;
    public String authCode; // Used to store the current logged in user's auth code
    public String title;

    // ALl Controls for FXML
    @FXML
    public Button btnBack;
    public Button btnContact;
    public Button btnDropDownHeart;
    public Button btnDropDownSteps;
    public Button btnDropDownSleep;
    public Button btnLogout;
    public Button btnSetGoals;
    public Button btnSettings;
    public Label lblBMIValue;
    public Label lblBMRValue;
    public Label lblGraphTitle;
    public Label lblCurrentDate;
    public Label lblOverviewTitle;
    public Label lblVitalStep;
    public Label lblVitalHeart;
    public Label lblVitalSleep;
    public LineChart graMain;
    public GridPane grdUserInfo;
    public GridPane mainPane;

    // Graph Control Buttons
    public Button btnConfirm;
    public Button btnCustomDay;
    public Button btnNextDay;
    public Button btnPrevDay;
    public DatePicker dpCustom;
    public GridPane grdGraph;
    public Label lblNoData;
    public Label lblXAxis;
    public Label lblYAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {
            // Set Initial Time Instance
            currentTimeFrame = "week";

            // Set Initial Data Type
            currentDataType = "steps";

            // Sets to Today's Date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            currentDate = dtf.format(localDate);

            DateTimeFormatter dtfNormalised = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            lblCurrentDate.setText(dtfNormalised.format(localDate));

            // Obtain current logged in user's auth code
            try {
                if (LoggedInUser.GetUserType().equals("User")) {
                    UserAccounts userAccounts = new UserAccounts();
                    userAccounts.importFile();
                    UserAccount user = null;
                    try {
                        user = userAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
                    } catch (IOException e) {

                    }
                    authCode = user.getAuthorizationCode();

                    // Set BMI and BMR Values
                    String[] result = UserHealth.BMI();
                    result[0] = String.format("%.2f", Double.parseDouble(result[0]));
                    lblBMIValue.setText("" + result[0] + " " + result[1]);
                    lblBMRValue.setText("" + String.format("%.2f", UserHealth.BMR()));
                } else {
                    // Remove UserGoals Section of UI
                    grdGraph.getChildren().remove(grdUserInfo);
                    // Show Back Button
                    btnBack.setVisible(true);
                    lblOverviewTitle.setText(title);
                    btnContact.setText(title.substring(0, title.indexOf("Overview")));
                }

                // Create Instance of FitBit
                FitbitData fbData = new FitbitData();

                // Import Latest FitBit Data
                int[] stepsValue = fbData.getStepsDataDay(currentDate, authCode);
                int[] heartRateValue = fbData.getHRDataMin(currentDate, authCode);
                int[] sleepValue = FitbitData.getSleepDuration(currentDate, authCode);

                // Check for Null Values
                if (stepsValue[0] == 0) {
                    stepsValue = new int[]{0, 0};
					lblVitalStep.setText("Steps");
                }
				else{
					lblVitalStep.setText("" + stepsValue[stepsValue.length - 1] + " Steps");
				}

                if (heartRateValue.length == 0) {
                    heartRateValue = new int[]{0, 0};
					lblVitalHeart.setText("Heart Rate");
                }
				else{
					lblVitalHeart.setText("" + heartRateValue[heartRateValue.length - 1] + " BPM (Heart Rate)");
				}

                if (sleepValue.length == 0) {
                    sleepValue = new int[]{0};
					lblVitalSleep.setText("Sleep");
                }
				else{
					Double normalisedSleep = Double.valueOf(sleepValue[0]) / 3600000;
					lblVitalSleep.setText("" + String.format("%.2f", normalisedSleep) + " Hours of Sleep");
				}

            } catch (Exception e) {

            }

            // Set Image Buttons
            GenerateAssets ga = new GenerateAssets();
            btnLogout.setGraphic(ga.createIconButton("logout.png", 30, 30));
            btnSettings.setGraphic(ga.createIconButton("settings.png", 30, 30));
            btnBack.setGraphic(ga.createIconButton("back.png", 30, 30));
            btnDropDownHeart.setGraphic(ga.createIconButton("dropdownalt.png", 20, 20));
            btnDropDownSleep.setGraphic(ga.createIconButton("dropdownalt.png", 20, 20));
            btnDropDownSteps.setGraphic(ga.createIconButton("dropdownalt.png", 20, 20));

            drawLineGraph(currentDataType, currentTimeFrame, currentDate);
        });

    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @FXML
    public void loadCarerUI() throws IOException {
        // Change Current Dimensions of Stage
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "Carer Overview", "carerOverview.fxml", "carerOverview.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
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

    @FXML
    public void setDateNextPrev(ActionEvent event) {
        Button source = (Button) event.getSource();
        String type = source.getId();
        int offset = 0;

        if (type.equals("btnNextDay")) {
            // Check to see if it Exceeds Current Date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String todayDate = dtf.format(localDate);

            if (!(currentDate.equals(todayDate))) {
                offset = 1;
            }

        } else if (type.equals("btnPrevDay")) {
            offset = -1;
        }

        if (offset != 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(currentDate));
                c.add(Calendar.DATE, offset);
                currentDate = sdf.format(c.getTime());

                // Update Label and Graph
                lblCurrentDate.setText(currentDate);
                drawLineGraph(currentDataType, currentTimeFrame, currentDate);
            } catch (Exception e) {
                // Handle Error
            }
        }

    }

    @FXML
    public void loadCustom() {
        dpCustom.setVisible(true);
        btnConfirm.setVisible(true);
        btnCustomDay.setVisible(false);
    }

    @FXML
    public void updateDate() {
        dpCustom.setVisible(false);
        btnConfirm.setVisible(false);
        btnCustomDay.setVisible(true);
        // Checks Date is Valid
        if (dpCustom.getValue() != null && ( (dpCustom.getValue().isBefore(LocalDate.now())) || dpCustom.getValue().equals(LocalDate.now()) ) ) {

            LocalDate date = dpCustom.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Verify Date
            currentDate = dtf.format(date);
            lblCurrentDate.setText(currentDate);

            dpCustom.setVisible(false);
            btnConfirm.setVisible(false);
            btnCustomDay.setVisible(true);
            btnCustomDay.setText("Custom");
            btnCustomDay.setStyle(null);

            // Update Graph
            drawLineGraph(currentDataType, currentTimeFrame, currentDate);
        } else {
            btnCustomDay.setText("Invalid day");
            btnCustomDay.setStyle("-fx-text-fill: red;");
            dpCustom.setVisible(false);
            btnConfirm.setVisible(false);
            btnCustomDay.setVisible(true);
        }

    }

    // By Default shows the Graph over a Day Period
    @FXML
    public void loadGraph(ActionEvent e) {
        // Get Source and Type of Button Pressed
        Button source = (Button) e.getSource();
        String type = source.getId();

        if (type.equals("btnDropDownSleep")) {
            lblYAxis.setText("Sleep in Hours");
            source.setRotate(90);
            btnDropDownHeart.setRotate(-90);
            btnDropDownSteps.setRotate(-90);
            currentDataType = "sleep";
            drawLineGraph(currentDataType, currentTimeFrame, currentDate);
        } else if (type.equals("btnDropDownHeart")) {
        	// Default graph for heartrate
            lblYAxis.setText("Beats per Minute");
            btnDropDownSleep.setRotate(-90);
            source.setRotate(90);
            btnDropDownSteps.setRotate(-90);
            currentDataType = "heartrate";
            currentTimeFrame = "min";
            drawLineGraph(currentDataType, currentTimeFrame, currentDate);
        } else {
        	// Default graph for steps
            lblYAxis.setText("Number of Steps");
            btnDropDownSleep.setRotate(-90);
            btnDropDownHeart.setRotate(-90);
            source.setRotate(90);
            currentDataType = "steps";
            currentTimeFrame = "week";
            drawLineGraph(currentDataType, currentTimeFrame, currentDate);
        }

    }

    @FXML
    public void drawLineGraph(String typeOfData, String timeInstance, String date) {
        graMain.setAnimated(false);
        graMain.getData().clear(); //ensures that other series (graphs) don't overlap each other

        // Graph Initialisers
        PlotGraph plot = new PlotGraph();
        GraphValues graphValues = new GraphValues();
        int max = 0;

        if (typeOfData.equals("steps")) {
            graphValues = plot.steps(timeInstance, date, authCode);
            graMain.setCreateSymbols(true); //turns on line chart style dots
            lblGraphTitle.setText("Steps over a week Graph");
        } else if (typeOfData.equals("heartrate")) {
            graphValues = plot.heartRate(timeInstance, date, authCode);
            graMain.setCreateSymbols(false); //turns off line chart style dots
            lblGraphTitle.setText("Heart-Rate Graph");
        } else if (typeOfData.equals("sleep")) {
            graphValues = plot.sleep(timeInstance, date, authCode);
            graMain.setCreateSymbols(true); //turns off line chart style dots
            lblGraphTitle.setText("Sleep over a week Graph");
        }

        max = graphValues.getYAxis().size();
        if (max > 7 && !(typeOfData.equals("heartrate"))) {
            max = 7;
        }

        if (graphValues.getYAxis().isEmpty() || checkIfZero(graphValues.getYAxis())) {
            graMain.setVisible(false);
            lblXAxis.setVisible(false);
            lblYAxis.setVisible(false);
            //lblGraphTitle.setText("No data for this day");
            lblNoData.setVisible(true);
        } else {
            // Initiates Series where data to be plotted will be stored
            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            graMain.setVisible(true);
            lblXAxis.setVisible(true);
            lblYAxis.setVisible(true);
            lblNoData.setVisible(false);

            // Prevents Error if there is too many values


            // Add data from graphValues into series
            for (int i = 0; i < max; i++) {
                series.getData().add(new XYChart.Data<String, Number>(graphValues.getXAxis().get(i).toString(), graphValues.getYAxis().get(i)));
            }

            graMain.getData().addAll(series); // Append series into main graph
        }

    }

    @FXML
    public void contactCarer() {
        btnContact.setText("Upcoming Feature");
    }

    public Boolean checkIfZero(ArrayList<Number> yAxis) {
        for (int i = 0; i < yAxis.size(); i++) {
            if (yAxis.get(i).intValue() > 0) {
                return false;
            }
        }
        return true;
    }

    @FXML
    public void loadGoalUI(ActionEvent e) throws IOException {
        // Change Current Dimensions of Stage
        GenerateAssets ga = new GenerateAssets();
        Stage stg = (Stage) mainPane.getScene().getWindow();
        ga.loadUI(stg, "View Goals", "userGoalsUpdate.fxml", "userGoals.css", (int) stg.getHeight(), (int) stg.getWidth(), stg.isMaximized());
    }


}
