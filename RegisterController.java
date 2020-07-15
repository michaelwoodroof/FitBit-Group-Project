// Contributor -- Michael Woodroof

// ADD Code to Populate Fields if user goes back
// Add more disability checks // Mostly DONE

// Import Java
import java.awt.Desktop;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

// Import JavaFX
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

// Handles the Logic for the Login Page
public class RegisterController implements Initializable {

    // Used to Track current Process of making account
    public static AssignID asgID = new AssignID();
    public static Account currentRegister = new Account();
    public static UserAccount currentUserRegister = new UserAccount();
    public static CarerAccount currentCarerRegister = new CarerAccount();

    // Values used in Conjunction to Determine how to Create Accounts
    public static Boolean isUser = true;
    public static Boolean isFirstLoad = true;

    // Controls neeced for FXML
    @FXML
    // NEEDED for Switch
    public AnchorPane mainPane;
    // Stage One
    public Button btnSignIn;
    public Button btnNextStageOne;
    public ChoiceBox cbxIsUser;
    public Label lblFirstName;
    public Label lblSurname;
    public Label lblEmailAddress;
    public Label lblPassword;
    public Label lblPasswordConfirm;
    public TextField txtFirstName;
    public TextField txtSurname;
    public TextField txtEmail;
    public PasswordField psfPassword;
    public PasswordField psfPasswordConfirm;

    // Stage Two
    public Label lblPhoneNumber;
    public Label lblDay;
    public Label lblMonth;
    public Label lblYear;
    public Label lblGender;
    public TextField txtPhoneNumber;
    public TextField txtDay;
    public TextField txtYear;
    public ChoiceBox cbxMonth;
    public ChoiceBox cbxGender;

    // Stage Three
    public Button btnAuthcode;
    public Button btnCreateAccount;
    public Button btnHeightUnitToggle;
    public Button btnWeightUnitToggle;
    public ChoiceBox cbxHasDisabilities;
    public Label lblFeet;
    public Label lblInches;
    public Label lblStone;
    public Label lblPounds;
    public Label lblCM;
    public Label lblKG;
    public Label lblHasDisabilities;
    public TextField txtFeet;
    public TextField txtInches;
    public TextField txtStone;
    public TextField txtPounds;
    public TextField txtKG;
    public TextField txtCM;
    public TextField txtAuthcode;
    public VBox vbxDisabilities;
    public VBox vbxDisabilitiesDelete;

    // Stage Alt
    public Button btnAddAnother;
    public Label lblAddUser;
    public VBox vbxUserIDs;
    public VBox vbxDeleteUserID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set Any Methods Here
        // Init AssignID
        asgID.importFile();
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        if (isUser) {
            // Check fields
            if (checkFields(2)) {

                // Add StartDate
                currentUserRegister.setStartDate(String.valueOf(java.time.LocalDate.now()));

                // Add User to file
                UserAccounts newAcc = new UserAccounts();
                newAcc.importFile();
                newAcc.add(currentUserRegister);
                newAcc.exportFile();

                // Iterate ID
                asgID.iterate();
                asgID.exportFile();

                // Wipe Accounts
                currentRegister = new Account();
                currentUserRegister = new UserAccount();
                currentCarerRegister = new CarerAccount();

                // Load Login UI
                loadLoginUI();

            }


        } else {
            // Check fields
            if (checkFields(3)) {
                // Add Caregiver to file
                CarerAccounts newAcc = new CarerAccounts();
                newAcc.importFile();
                newAcc.add(currentCarerRegister);
                newAcc.exportFile();

                // Iterate ID
                asgID.iterate();
                asgID.exportFile();

                // Wipe Accounts
                currentRegister = new Account();
                currentUserRegister = new UserAccount();
                currentCarerRegister = new CarerAccount();

                // Load Login UI
                loadLoginUI();

            }
        }
    }

    @FXML
    public void checkNewGeneration() {
        String[] alertName = {"Delete Account", "By changing the Account type it will remove any current account progress !",
        "Are you Sure?"};
        if (cbxIsUser.getValue().equals(" User")) {
            if (isFirstLoad == false && isUser == true) {
                // Throw Alert
                if (callAlert(alertName)) {
                    isFirstLoad = true;
                    cbxIsUser.setValue(" Caregiver");
                }
            }
            isUser = true;
        } else {
            if (isFirstLoad == false && isUser == false) {
                // Throw Alert
                if (callAlert(alertName)) {
                    isFirstLoad = true;
                    cbxIsUser.setValue(" User");
                }
            }
            isUser = false;
        }
    }

    public void generateAccounts() {
        if (isFirstLoad == true) {
            if (isUser == true) {
                currentUserRegister = new UserAccount(currentRegister);
            } else {
                currentCarerRegister = new CarerAccount(currentRegister);
            }
        }
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
    public void convertHeight(ActionEvent event) {
        // Get Source
        Button unitHSource = (Button) event.getSource();
        String usho = unitHSource.getText();

        // For Weight
        if (usho.equals("Switch to cm")) {
            // Change Source Text
            unitHSource.setText("Switch to feet");

            // Hide Control
            txtFeet.setVisible(false);
            txtInches.setVisible(false);
            lblFeet.setVisible(false);
            lblInches.setVisible(false);

            // Show Control
            txtCM.setVisible(true);
            lblCM.setVisible(true);

            // If Text NULL then Set to 0 -- This for Useability
            if (txtFeet.getText().trim().equals("") && txtInches.getText().trim().equals("")) {
                txtFeet.setText("0");
                txtInches.setText("0");
            } else if (txtFeet.getText().trim().equals("")) {
                txtFeet.setText("0");
            } else if (txtInches.getText().trim().equals("")) {;
                txtInches.setText("0");
            }

            // Check if Invalid Value
            try {
                Integer.parseInt(txtFeet.getText());
            } catch (Exception e) {
                txtFeet.setText("0");
            }
            // Set to 0 if so
            try {
                Integer.parseInt(txtInches.getText());
            } catch (Exception e) {
                txtInches.setText("0");
            }

            // Reset Styles
            lblCM.setText("Centimetres");
            lblCM.setStyle(null);
            txtCM.setStyle(null);

            // Change Value
            String[] values = {txtFeet.getText(), txtInches.getText()};
            txtCM.setText(cHeight(values, 0)[0]);

        } else if (usho.equals("Switch to feet")) {
            // Change Source Text
            unitHSource.setText("Switch to cm");

            // Hide Control
            txtFeet.setVisible(true);
            txtInches.setVisible(true);
            lblFeet.setVisible(true);
            lblInches.setVisible(true);

            // Show Control
            txtCM.setVisible(false);
            lblCM.setVisible(false);

            // If Text NULL then Set to 0 -- This for Useability
            if (txtCM.getText().trim().equals("")) {
                txtCM.setText("0");
            }

            // Check if Invalid Value
            try {
                Double.parseDouble(txtCM.getText());
            } catch (Exception e) {
                txtCM.setText("0");
            }

            // Reset Styles
            lblFeet.setText("Feet");
            lblInches.setText("Inches");
            lblFeet.setStyle(null);
            lblInches.setStyle(null);
            txtFeet.setStyle(null);
            txtInches.setStyle(null);

            // Change Value
            String[] values = {txtCM.getText(), ""};
            String[] result = cHeight(values, 1);

            txtFeet.setText(result[0]);
            txtInches.setText(result[1]);
        }
    }

    public static String[] cHeight(String[] values, int state) {
        String[] result = new String[2];

        if (state == 0) {
            // Converts from Feet and Inches to CM
            result[0] = "" + String.format("%.2f", (Double.parseDouble(values[0]) + (Double.parseDouble(values[1]) / 12)) * 30.48 );
        } else {
            // Converts CM to Feet and Inches
            double value = Double.parseDouble(values[0]) * 0.0328084;
            long feetVal = (long) value;
            double inchVal = (value - feetVal) * 12;

            result[0] = Long.toString(feetVal);
            result[1] = String.format("%.0f", inchVal);
        }

        return result;
    }

    @FXML
    public void convertWeight(ActionEvent event) {
        // Get Source
        Button unitWSource = (Button) event.getSource();
        String uswo = unitWSource.getText();

        // For Height
        if (uswo.equals("Switch to kg")) {
            // Change Source Text
            unitWSource.setText("Switch to stone");

            // Hide Control
            txtStone.setVisible(false);
            txtPounds.setVisible(false);
            lblStone.setVisible(false);
            lblPounds.setVisible(false);

            // Show Control
            txtKG.setVisible(true);
            lblKG.setVisible(true);

            // If Text NULL then Set to 0
            if (txtStone.getText().trim().equals("") && txtPounds.getText().trim().equals("")) {
                txtStone.setText("0");
                txtPounds.setText("0");
            } else if (txtStone.getText().trim().equals("")) {
                txtStone.setText("0");
            } else if (txtPounds.getText().trim().equals("")) {;
                txtPounds.setText("0");
            }

            // Check if Invalid Value
            try {
                Integer.parseInt(txtStone.getText());
            } catch (Exception e) {
                txtStone.setText("0");
            }
            // Set to 0 if so
            try {
                Integer.parseInt(txtPounds.getText());
            } catch (Exception e) {
                txtPounds.setText("0");
            }

            // Reset Styles
            lblKG.setText("Kilograms");
            lblKG.setStyle(null);
            txtKG.setStyle(null);

            // Change Value
            String[] values = {txtStone.getText(), txtPounds.getText()};
            txtKG.setText(cWeight(values, 0)[0]);

        } else if (uswo.equals("Switch to stone")) {
            // Change Source Text
            unitWSource.setText("Switch to kg");

            // Hide Control
            txtStone.setVisible(true);
            txtPounds.setVisible(true);
            lblStone.setVisible(true);
            lblPounds.setVisible(true);

            // Show Control
            txtKG.setVisible(false);
            lblKG.setVisible(false);

            // If Text NULL then Set to 0
            if (txtKG.getText().trim().equals("")) {
                txtKG.setText("0");
            }

            // Check if Invalid Value
            try {
                Double.parseDouble(txtKG.getText());
            } catch (Exception e) {
                txtKG.setText("0");
            }

            // Reset Styles
            lblStone.setText("Stone");
            lblPounds.setText("Pounds");
            lblStone.setStyle(null);
            lblPounds.setStyle(null);
            txtStone.setStyle(null);
            txtPounds.setStyle(null);

            // Change Value
            String[] values = {txtKG.getText(), ""};
            String[] result = cWeight(values, 1);

            txtStone.setText(result[0]);
            txtPounds.setText(result[1]);

        }
    }

    public static String[] cWeight(String[] values, int state) {
        String[] result = new String[2];

        if (state == 0) {
            // Converts from Stone and Pounds to KG
            result[0] = "" + String.format("%.2f", (Double.parseDouble(values[0]) + (Double.parseDouble(values[1]) / 14)) * 6.35029);
        } else {
            // Converts KG to Stone and Pounds
            double valueInStone = Double.parseDouble(values[0]) * 0.15747;
            long stone = (long) valueInStone;
            double pounds = (valueInStone - stone) * 14;

            // Add Parameter
            String stoneN = Long.toString(stone);
            String poundsN = String.format("%.0f", pounds);

            // Fix Normalisation
            if (poundsN.equals("14")) {
                stone++;
                stoneN = Long.toString(stone);
                poundsN = "0";
            }

            result[0] = stoneN;
            result[1] = poundsN;
        }

        return result;
    }

    // Used to Adjust UI Screens
    @FXML
    public void loadRegisterState(ActionEvent event) throws IOException {

        Stage stage = (Stage) mainPane.getScene().getWindow();

        stage.setHeight(400);
        stage.setWidth(600);
        stage.setResizable(false);

        final Node source = (Node) event.getSource();
        String so = source.getId();

        Boolean isValid = false;
        int loadStage = -1;

        if (so.equals("btnNextStageOne") || so.equals("btnPrevStageThree")) {
            loadStage = 1;
            if (so.equals("btnPrevStageThree")) {
                isValid = true;
            } else {
                if (checkFields(0) && so.equals("btnNextStageOne") && cbxIsUser.getValue().equals(" User")) {
                    isValid = true;
                    generateAccounts();
                    isFirstLoad = false;
                } else if (checkFields(0) && so.equals("btnNextStageOne") && cbxIsUser.getValue().equals(" Caregiver")) {
                    isValid = true;
                    isUser = false;
                    loadStage = 3;
                    generateAccounts();
                    isFirstLoad = false;
                } else {
                    // Handle

                }
            }

        } else if (so.equals("btnNextStageTwo")) {
            loadStage = 2;
            if (checkFields(1)) {
                isValid = true;
            }
        } else if (so.equals("btnPrevStageTwo")) {
            isValid = true;
            loadStage = 0;
        }

        if (isValid) {
            if (loadStage == 0) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("xml/register_stage_one.fxml"));
                mainPane.getChildren().setAll(pane);
            } else if (loadStage == 1) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("xml/register_stage_two.fxml"));
                mainPane.getChildren().setAll(pane);
            } else if (loadStage == 2) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("xml/register_stage_three.fxml"));
                mainPane.getChildren().setAll(pane);
                stage.setResizable(true);
                stage.setHeight(800);
            } else if (loadStage == 3) {
                currentCarerRegister = new CarerAccount(currentRegister);
                AnchorPane pane = FXMLLoader.load(getClass().getResource("xml/register_stage_alt.fxml"));
                mainPane.getChildren().setAll(pane);
                stage.setResizable(true);
                stage.setHeight(800);
            }
        }

    }

    public static int maxIDU = 0;

    @FXML
    public void drawDisabilityMenu() throws IOException{

        // Set Any Methods Here
        if (cbxHasDisabilities.getValue().equals("Yes")) {
            HBox container = new HBox();
            // Create a ChoiceBox
            ChoiceBox cbxDisabilities = new ChoiceBox();
            ObservableList<String> list = FXCollections.observableArrayList(disabilitiesList());
            cbxDisabilities.setItems(list);
            // Create Add Button
            Button addButton = new Button("Add another");
            addButton.setId("btnAddButton");
            // Set Button Action
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Check Validity
                    if (checkField(12) && checkField(15)) {
                        // Get ID Max
                        int id = maxIDU;
                        for (Node node : vbxDisabilities.getChildren()) {
                            id++;
                        }
                        maxIDU = id;

                        // Add ChoiceBox
                        ChoiceBox cbxDisChoice = new ChoiceBox();
                        ObservableList<String> disList = FXCollections.observableArrayList(disabilitiesList());
                        cbxDisChoice.setItems(disList);
                        cbxDisChoice.setId("" + id);

                        vbxDisabilities.getChildren().add(cbxDisChoice);

                        // Add Button
                        Button btnDeleteID = new Button("x");
                        btnDeleteID.setPrefHeight(25);
                        btnDeleteID.setPrefWidth(25);
                        btnDeleteID.setId("" + id);

                        vbxDisabilitiesDelete.getChildren().add(btnDeleteID);

                        // Add Method
                        btnDeleteID.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Remove TextField
                                for (Node node : vbxDisabilities.getChildren()) {
                                    if (node instanceof ChoiceBox) {
                                        Button btn = (Button) event.getSource();
                                        String id = btn.getId();
                                        if (node.getId().equals(id)) {
                                            // Remove Nodes
                                            // This is Needed to prevent Thread Concurrency
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    vbxDisabilities.getChildren().remove(node);
                                                    vbxDisabilitiesDelete.getChildren().remove(btn);
                                                    // See if Removes an Issue
                                                    checkField(12);
                                                }
                                            });

                                        }
                                    }
                                }
                            }
                        });

                    }
                }
            });

            container.getChildren().add(cbxDisabilities);
            container.getChildren().add(addButton);

            vbxDisabilities.getChildren().add(container);
        } else {
            // Check if Values Exist
            if (vbxDisabilities.getChildren() != null) {
                // Throw Alert
                String[] alertName = {"Remove Disabilities", "By changing this you will remove your disabilities",
                "Are you Sure?"};
                if (callAlert(alertName)) {
                    // Remove Nodes
                    lblHasDisabilities.setText("Have Disabilities");
                    lblHasDisabilities.setStyle(null);
                    vbxDisabilities.getChildren().clear();
                } else {
                    cbxHasDisabilities.setValue("Yes");
                }
            }
        }
    }

    public static int maxIDC = 0;

    @FXML
    public void drawUserIDBox(ActionEvent event) throws IOException {
        // Get ID Max
        int id = maxIDC;
        for (Node node : vbxUserIDs.getChildren()) {
            id++;
        }
        maxIDC = id;

        if (checkField(14)) {
            TextField txtfIDBox = new TextField();
            Button btnDeleteID = new Button("x");
            btnDeleteID.setPrefHeight(25);
            btnDeleteID.setPrefWidth(25);

            txtfIDBox.setId("" + id);
            btnDeleteID.setId("" + id);
            // Add Method
            btnDeleteID.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Remove TextField
                    for (Node node : vbxUserIDs.getChildren()) {
                        if (node instanceof TextField) {
                            Button btn = (Button) event.getSource();
                            String id = btn.getId();
                            if (node.getId().equals(id)) {
                                // Remove Nodes
                                // This is Needed to prevent Thread Concurrency
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        vbxUserIDs.getChildren().remove(node);
                                        vbxDeleteUserID.getChildren().remove(btn);
                                        // See if Removes an Issue
                                        checkField(14);
                                    }
                                });

                            }
                        }
                    }
                }
            });
            vbxUserIDs.getChildren().add(txtfIDBox);
            vbxDeleteUserID.getChildren().add(btnDeleteID);
        }
    }

    @FXML
    public void getAuthcode(ActionEvent event){
        String url = "https://accounts.fitbit.com/login?targetUrl=https%3A%2F%2Fwww.fitbit.com%2Flogin%2Ftransferpage%3Fredirect%3Dhttps%25253A%25252F%25252Fwww.fitbit.com%25252Foauth2%25252Fauthorize%25253Fclient_id%25253D22D9J3%252526expires_in%25253D2592000%252526redirect_uri%25253Dhttps%2525253A%2525252F%2525252Fcuyear2group1.github.io%252526response_type%25253Dtoken%252526scope%25253Dactivity%25252Bheartrate%25252Bsleep%252526state&lcl=en_GB";
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {}

    }

    public ArrayList<String> disabilitiesList() {
        // Examples Add More
        ArrayList<String> disabilities = new ArrayList<String>();
        disabilities.add("Dyslexia");
        disabilities.add("Diabetes");
        disabilities.add("Cataract");
        return disabilities;
    }

    // Returning to Login Screen Method
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        loadLoginUI();
    }

    @FXML
    public void loadLoginUI() throws IOException {
        GenerateAssets ga = new GenerateAssets();
        ga.logout(btnCreateAccount);
    }

    // Handle Errors Here
    public Boolean checkField(int fieldNumber) {
        // Contains all Fields
        switch (fieldNumber){
            case 0: // First Name
                if (Regex.regexChecker(txtFirstName.getText(), Regex.regexPatterns(0)) && txtFirstName.getText().length() >= 1) {
                    lblFirstName.setText("First Name");
                    lblFirstName.setStyle(null);
                    txtFirstName.setStyle(null);
                    currentRegister.setFirstName(txtFirstName.getText());
                    return true;
                } else {
                    lblFirstName.setStyle("-fx-text-fill: red;");
                    txtFirstName.setStyle("-fx-border-width: 1px;");
                    txtFirstName.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 1: // Surname
                if (Regex.regexChecker(txtSurname.getText(), Regex.regexPatterns(0)) && txtSurname.getText().length() >= 1) {
                    lblSurname.setText("Surname");
                    lblSurname.setStyle(null);
                    txtSurname.setStyle(null);
                    currentRegister.setLastName(txtSurname.getText());
                    return true;
                } else {
                    lblSurname.setStyle("-fx-text-fill: red;");
                    txtSurname.setStyle("-fx-border-width: 1px;");
                    txtSurname.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 2: // Email
                if (Regex.regexChecker(txtEmail.getText(), Regex.regexPatterns(1))) {
                    File cFile = new File("accounts/carerAccounts.txt");
                    File uFile = new File("accounts/userAccounts.txt");
                    int checkCode = -1;
                    // Check if Unique in Both Accounts
                    if (cFile.exists() && uFile.exists()) {
                        // Both Files Exist
                        checkCode = 0;
                    } else if (cFile.exists()) {
                        // Only Carer File Exists
                        checkCode = 1;
                    } else if (uFile.exists()){
                        // Only User File Exists
                        checkCode = 2;
                    }

                    Boolean isUnique = true;
                    // Check Files
                    if (checkCode == 0 || checkCode == 2) {
                        // Check User File
                        try {
                            UserAccounts uAcc = new UserAccounts();
                            uAcc.importFile();
                            if(!uAcc.isUnique(txtEmail.getText())) {
                                isUnique = false;
                            }
                        } catch (Exception e) {
                            isUnique = false;
                        }
                    } else if (checkCode == 0 || checkCode == 1) {
                        // Check Carer File
                        // Check User File
                        try {
                            CarerAccounts cAcc = new CarerAccounts();
                            cAcc.importFile();
                            if(!cAcc.isUnique(txtEmail.getText())) {
                                isUnique = false;
                            }
                        } catch (Exception e) {
                            isUnique = false;
                        }
                    }
                    // Allow Email Address
                    if (isUnique) {
                        lblEmailAddress.setText("Enter Email Address");
                        lblEmailAddress.setStyle(null);
                        txtEmail.setStyle(null);
                        currentRegister.setEmailAddress(txtEmail.getText());
                        currentRegister.setUsername(txtEmail.getText());
                        return true;
                    } else {
                        // Handle
                        lblEmailAddress.setText("Enter a valid Email Address");
                        lblEmailAddress.setStyle("-fx-text-fill: red;");
                        txtEmail.setStyle("-fx-border-width: 1px;");
                        txtEmail.setStyle("-fx-border-color: red;");
                        return false;
                    }

                } else {
                    lblEmailAddress.setText("Enter a valid Email Address");
                    lblEmailAddress.setStyle("-fx-text-fill: red;");
                    txtEmail.setStyle("-fx-border-width: 1px;");
                    txtEmail.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 3: // Password
                if (psfPassword.getText().length() >= 8) {
                    lblPassword.setText("Enter Password");
                    lblPassword.setStyle(null);
                    psfPassword.setStyle(null);
                    return true;
                } else {
                    lblPassword.setText("Password not 8 characters long");
                    lblPassword.setStyle("-fx-text-fill: red;");
                    psfPassword.setStyle("-fx-border-width: 1px;");
                    psfPassword.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 4: // Password
                if (psfPasswordConfirm.getText().equals(psfPassword.getText())) {
                    if (checkField(3)) {
                        currentRegister.setPassword(psfPassword.getText());
                        lblPasswordConfirm.setText("Confirm Password");
                        lblPasswordConfirm.setStyle(null);
                        psfPasswordConfirm.setStyle(null);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    checkField(3);
                    lblPasswordConfirm.setText("Passwords do not match");
                    lblPasswordConfirm.setStyle("-fx-text-fill: red;");
                    psfPasswordConfirm.setStyle("-fx-border-width: 1px;");
                    psfPasswordConfirm.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 5: // Phonenumber
                if (Regex.regexChecker(txtPhoneNumber.getText(), Regex.regexPatterns(2)) && txtPhoneNumber.getText().length() >= 8) {
                    currentUserRegister.setPhoneNumber(txtPhoneNumber.getText());
                    lblPhoneNumber.setText("Phone Number (Optional)");
                    lblPhoneNumber.setStyle(null);
                    txtPhoneNumber.setStyle(null);
                    return true;
                } else {
                    lblPhoneNumber.setText("Please enter a valid phone number");
                    lblPhoneNumber.setStyle("-fx-text-fill: red;");
                    txtPhoneNumber.setStyle("-fx-border-width: 1px;");
                    txtPhoneNumber.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 6: // Day
                if (Regex.regexChecker(txtDay.getText(), Regex.regexPatterns(3)) && txtDay.getText().length() >= 1) {
                    lblDay.setText("Day");
                    lblDay.setStyle(null);
                    txtDay.setStyle(null);
                    return true;
                } else {
                    lblDay.setText("Please enter a valid day");
                    lblDay.setStyle("-fx-text-fill: red;");
                    txtDay.setStyle("-fx-border-width: 1px;");
                    txtDay.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 7: // Month
                if (cbxMonth.getValue() != null) {
                    lblMonth.setText("Month");
                    lblMonth.setStyle(null);
                    cbxMonth.setStyle(null);
                    return true;
                } else {
                    lblMonth.setText("Pick a Month");
                    lblMonth.setStyle("-fx-text-fill: red;");
                    cbxMonth.setStyle("-fx-border-width: 4px;");
                    cbxMonth.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 8: // Year
                // Check Year
                Boolean isValid = true;
                String yearValue = txtYear.getText();

                // Try to Convert to Int
                try {
                    int year = Year.now().getValue();
                    int actualYear = Integer.parseInt(yearValue);
                    if ( (year - actualYear) >= 0 && (year - actualYear) <= 200 ) {
                        lblYear.setText("Year");
                        lblYear.setStyle(null);
                        txtYear.setStyle(null);
                    } else {
                        isValid = false;
                    }
                } catch (Exception e) {
                    isValid = false;
                }

                if (isValid) {
                    if (checkField(7) && checkField(6)) {
                        // Convert Month to Numerical
                        String txtMonthData = "" + cbxMonth.getValue();
                        String month = "";

                        if (txtMonthData.equals("January")) {
                            month = "01";
                        } else if (txtMonthData.equals("February")) {
                            month = "02";
                        } else if (txtMonthData.equals("March")) {
                            month = "03";
                        } else if (txtMonthData.equals("April")) {
                            month = "04";
                        } else if (txtMonthData.equals("May")) {
                            month = "05";
                        } else if (txtMonthData.equals("June")) {
                            month = "06";
                        } else if (txtMonthData.equals("July")) {
                            month = "07";
                        } else if (txtMonthData.equals("August")) {
                            month = "08";
                        } else if (txtMonthData.equals("September")) {
                            month = "09";
                        } else if (txtMonthData.equals("October")) {
                            month = "10";
                        } else if (txtMonthData.equals("November")) {
                            month = "11";
                        } else if (txtMonthData.equals("December")) {
                            month = "12";
                        }
                        currentUserRegister.setAge(txtYear.getText() + "-" + month + "-" + txtDay.getText());
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    lblYear.setText("Please enter a valid year");
                    lblYear.setStyle("-fx-text-fill: red;");
                    txtYear.setStyle("-fx-border-width: 1px;");
                    txtYear.setStyle("-fx-border-color: red;");
                    return false;
                }

            case 9: // Gender
                if (cbxGender.getValue() != null) {
                    lblGender.setText("Gender");
                    lblGender.setStyle(null);
                    cbxGender.setStyle(null);
                    currentUserRegister.setGender((String) cbxGender.getValue());
                    return true;
                } else {
                    lblGender.setText("Pick a Gender");
                    lblGender.setStyle("-fx-text-fill: red;");
                    cbxGender.setStyle("-fx-border-width: 4px;");
                    cbxGender.setStyle("-fx-border-color: red;");
                    return false;
                }
            case 10: // Check Height
                // Check for Null Values
                if (txtCM.getText() == null || txtFeet.getText() == null) {
                    // See Visible
                    if (txtCM.isVisible() == true) {
                        lblCM.setStyle("-fx-text-fill: red;");
                        lblCM.setText("Please input a value in the highlighted field");
                        txtCM.setStyle("-fx-border-width: 1px;");
                        txtCM.setStyle("-fx-border-color: red;");
                        return false;
                    } else {
                        if (txtInches.getText() == null) {
                            // Set Error Here
                            lblInches.setStyle("-fx-text-fill: red;");
                            lblInches.setText("Please input a value in the highlighted field");
                            txtInches.setStyle("-fx-border-width: 1px;");
                            txtInches.setStyle("-fx-border-color: red;");
                        }
                        lblFeet.setStyle("-fx-text-fill: red;");
                        lblFeet.setText("Please input a value in the highlighted field");

                        txtFeet.setStyle("-fx-border-width: 1px;");
                        txtFeet.setStyle("-fx-border-color: red;");
                        return false;
                    }
                } else {
                    // See What is Visible
                    if (txtCM.isVisible() == true) {
                        // Perform Checks
                        try {
                            Double tryValue = Double.parseDouble(txtCM.getText());
                            // Set Value
                            currentUserRegister.setHeight(txtCM.getText());

                            // Reset Styles
                            lblCM.setText("Centimetres");
                            lblCM.setStyle(null);
                            txtCM.setStyle(null);

                            return true;
                        } catch (Exception e) {
                            lblCM.setStyle("-fx-text-fill: red;");
                            lblCM.setText("Please input a valid value in the highlighted field");
                            txtCM.setStyle("-fx-border-width: 1px;");
                            txtCM.setStyle("-fx-border-color: red;");
                            return false;
                        }
                    } else {
                        // Convert Measure
                        String[] values = {txtFeet.getText(), txtInches.getText()};

                        Boolean isHValid = true;
                        // See if Values are Valid
                        try {
                            int feet = Integer.parseInt(txtFeet.getText());
                            // Reset Style
                            lblFeet.setStyle(null);
                            lblFeet.setText("Inches");
                            txtFeet.setStyle(null);
                        } catch (Exception e) {
                            lblFeet.setStyle("-fx-text-fill: red;");
                            lblFeet.setText("Please input a valid value in the highlighted field");
                            txtFeet.setStyle("-fx-border-width: 1px;");
                            txtFeet.setStyle("-fx-border-color: red;");
                            isHValid = false;
                        }

                        try {
                            int inches = Integer.parseInt(txtInches.getText());
                            // Reset Style
                            lblInches.setStyle(null);
                            lblInches.setText("Inches");
                            txtInches.setStyle(null);
                        } catch (Exception e) {
                            lblInches.setStyle("-fx-text-fill: red;");
                            lblInches.setText("Please input a valid value in the highlighted field");
                            txtInches.setStyle("-fx-border-width: 1px;");
                            txtInches.setStyle("-fx-border-color: red;");
                            isHValid = false;
                        }

                        if (!isHValid) {
                            return false;
                        }
                        // Set Value
                        currentUserRegister.setHeight(cHeight(values, 0)[0]);

                        // Reset Styles
                        lblFeet.setText("Feet");
                        lblInches.setText("Inches");
                        lblFeet.setStyle(null);
                        lblInches.setStyle(null);
                        txtFeet.setStyle(null);
                        txtInches.setStyle(null);

                        return true;
                    }
                }

            case 11: // Check Weight
                // Check for Null Values
                if (txtKG.getText() == null || txtStone.getText() == null) {
                    // See Visible
                    if (txtPounds.isVisible() == true) {
                        lblKG.setStyle("-fx-text-fill: red;");
                        lblKG.setText("Please input a value in the highlighted field");
                        txtKG.setStyle("-fx-border-width: 1px;");
                        txtKG.setStyle("-fx-border-color: red;");
                        return false;
                    } else {
                        if (txtPounds.getText() == null) {
                            // Set Error Here
                            lblPounds.setStyle("-fx-text-fill: red;");
                            lblPounds.setText("Please input a value in the highlighted field");
                            txtPounds.setStyle("-fx-border-width: 1px;");
                            txtPounds.setStyle("-fx-border-color: red;");
                        }
                        lblStone.setStyle("-fx-text-fill: red;");
                        lblStone.setText("Please input a value in the highlighted field");

                        txtStone.setStyle("-fx-border-width: 1px;");
                        txtStone.setStyle("-fx-border-color: red;");
                        return false;
                    }
                } else {
                    // See What is Visible
                    if (txtKG.isVisible() == true) {
                        // Perform Checks
                        try {
                            Double tryValue = Double.parseDouble(txtKG.getText());
                            // Set Value
                            currentUserRegister.setWeight(txtKG.getText());

                            // Reset Styles
                            lblKG.setText("Kilograms");
                            lblKG.setStyle(null);
                            txtKG.setStyle(null);

                            return true;
                        } catch (Exception e) {
                            lblKG.setStyle("-fx-text-fill: red;");
                            lblKG.setText("Please input a valid value in the highlighted field");
                            txtKG.setStyle("-fx-border-width: 1px;");
                            txtKG.setStyle("-fx-border-color: red;");
                            return false;
                        }
                    } else {
                        // Convert Measure
                        String[] values = {txtStone.getText(), txtPounds.getText()};

                        Boolean isWValid = true;
                        // See if Values are Valid
                        try {
                            int stone = Integer.parseInt(txtStone.getText());
                            // Reset Style
                            lblStone.setStyle(null);
                            lblStone.setText("Stone");
                            txtStone.setStyle(null);
                        } catch (Exception e) {
                            lblStone.setStyle("-fx-text-fill: red;");
                            lblStone.setText("Please input a valid value in the highlighted field");
                            txtStone.setStyle("-fx-border-width: 1px;");
                            txtStone.setStyle("-fx-border-color: red;");
                            isWValid = false;
                        }

                        try {
                            int pounds = Integer.parseInt(txtPounds.getText());
                            // Reset Style
                            lblPounds.setStyle(null);
                            lblPounds.setText("Pounds");
                            txtPounds.setStyle(null);
                        } catch (Exception e) {
                            lblPounds.setStyle("-fx-text-fill: red;");
                            lblPounds.setText("Please input a valid value in the highlighted field");
                            txtPounds.setStyle("-fx-border-width: 1px;");
                            txtPounds.setStyle("-fx-border-color: red;");
                            isWValid = false;
                        }

                        if (!isWValid) {
                            return false;
                        }
                        // Set Value
                        currentUserRegister.setWeight(cWeight(values, 0)[0]);

                        // Reset Styles
                        lblStone.setText("Stone");
                        lblPounds.setText("Pounds");
                        lblStone.setStyle(null);
                        lblPounds.setStyle(null);
                        txtStone.setStyle(null);
                        txtPounds.setStyle(null);

                        return true;
                    }
                }

            case 12: // Check Disabilities
                if (cbxHasDisabilities.getValue().equals("Yes")) {
                    ArrayList<String> currentDisabilities = new ArrayList<String>();
                    for (Node node : vbxDisabilities.getChildren()) {
                        // Check Choicebox in HBox
                        if (node instanceof HBox) {
                            for (Node nodeIn:((HBox)node).getChildren()) {
                                if (nodeIn instanceof ChoiceBox) {
                                    String currentValue = String.valueOf(((ChoiceBox) nodeIn).getValue());
                                    if (currentValue.equals("null")) {
                                        return false;
                                    }
                                    currentDisabilities.add(currentValue);
                                }
                            }
                        // Check Rest of Choiceboxes
                        } else if (node instanceof ChoiceBox) {
                            // Check Prior Values
                            String currentValue = String.valueOf(((ChoiceBox) node).getValue());
                            if (currentValue.equals("null")) {
                                return false;
                            }
                            for (String value : currentDisabilities) {
                                // Check for Errors
                                if (value.equals(currentValue)) {
                                    return false;
                                }
                            }
                            // Add Value
                            currentDisabilities.add(currentValue);
                        }

                    }
                    // Assign Disabilities
                    currentUserRegister.setDisabilities(currentDisabilities.toArray(new String[currentDisabilities.size()]));
                    return true;
                } else {
                    lblHasDisabilities.setText("Have Disabilities");
                    lblHasDisabilities.setStyle(null);
                    String[] none = {"none"};
                    currentUserRegister.setDisabilities(none);
                    return true;
                }

            case 13: // FitBitAuthCode
                if (txtAuthcode.getText() != null && FitbitAPI.CheckCode(txtAuthcode.getText())) {
                    currentUserRegister.setAuthorizationCode(txtAuthcode.getText());
                    // Reset Style
                    return true;
                } else {
                    // Set Style
                    return false;
                }

            case 14: // Check User is Real ID
                ArrayList<Integer> currentUsers = new ArrayList<Integer>();
                for (Node node : vbxUserIDs.getChildren()) {
                    if (node instanceof TextField) {
                        // Check Previous Users
                        try {
                            Integer currentUserID = Integer.parseInt(((TextField) node).getText());
                            Boolean isFound = false;
                            // Check ID Actually Exists
                            try {
                                UserAccounts uAccs = new UserAccounts();
                                uAccs.importFile();
                                Vector<UserAccount> users = uAccs.getUsers();
                                // Go Through each User to See if ID Exists
                                for (int i = 0; i < users.size(); i++) {
                                    UserAccount uAcc = users.get(i);
                                    if (uAcc.getID() == currentUserID) {
                                        isFound = true;
                                        break;
                                    }
                                }

                                if (!isFound) {
                                    // Set Style
                                    lblAddUser.setStyle("-fx-text-fill: red;");
                                    lblAddUser.setText("Highlighted field is not a valid ID");
                                    node.setStyle("-fx-border-width: 1px;");
                                    node.setStyle("-fx-border-color: red;");
                                    return false;
                                }
                            } catch (Exception e) {
                                lblAddUser.setStyle("-fx-text-fill: red;");
                                lblAddUser.setText("User Accounts cannot be accessed");
                                return false;
                            }

                            // Check for Duplicates
                            for (Integer value : currentUsers) {
                                if (value.equals(currentUserID)) {
                                    lblAddUser.setStyle("-fx-text-fill: red;");
                                    lblAddUser.setText("Highlighted field has already been inputted");
                                    node.setStyle("-fx-border-width: 1px;");
                                    node.setStyle("-fx-border-color: red;");
                                    return false;
                                }
                            }
                            // Reset Style
                            node.setStyle(null);
                            // Add to List
                            currentUsers.add( Integer.parseInt(((TextField) node).getText()) );
                        } catch (Exception e) {
                            // Invalid Number Format
                            // Set Style
                            lblAddUser.setStyle("-fx-text-fill: red;");
                            lblAddUser.setText("Check input of the highlighted field(s)");
                            node.setStyle("-fx-border-width: 1px;");
                            node.setStyle("-fx-border-color: red;");
                            return false;
                        }

                    }
                }
                int[] users = currentUsers.stream().mapToInt(Integer::intValue).toArray();
                currentCarerRegister.setUsers(users);
                // Reset Style
                lblAddUser.setStyle(null);
                lblAddUser.setText("Add User ID");
                return true;
            case 15: // Used for Auxillary Uses
                int count = 0;
                int max = disabilitiesList().size();
                for (Node node : vbxDisabilities.getChildren()) {
                    count++;
                }
                if (count == max) {
                    // Handle
                    lblHasDisabilities.setText("Cannot add anymore disabilities");
                    lblHasDisabilities.setStyle("-fx-text-fill: red;");
                    return false;
                } else {
                    lblHasDisabilities.setText("Have Disabilities");
                    lblHasDisabilities.setStyle(null);
                    return true;
                }
            default:
                // Dump Error
                return false;
        }
    }

    public Boolean checkFields(int stageNumber) {
        // Check All Fields of a Given Stage
        // REASON for Checking all fields is so USER know what fields
        // are valid and which ones are not valid
        Boolean isValid = true;
        if (stageNumber == 0) {
            for (int i = 0; i <= 4; i++) {
                if (checkField(i) == false) {
                    isValid = false;
                }
                // Add ID Add Here
                if (isValid) {
                    // Assign ID Value
                    currentRegister.setID("" + asgID.getID());
                }
            }
        } else if (stageNumber == 1) {
            for (int i = 5; i <= 9; i++) {
                if (checkField(i) == false) {
                    isValid = false;
                }
            }
        } else if (stageNumber == 2){
            for (int i = 10; i <= 13; i++) {
                if (checkField(i) == false) {
                    isValid = false;
                }
            }
        } else if (stageNumber == 3) {
            if (checkField(14) == false) {
                isValid = false;
            }
        }
        return isValid;
    }

}
