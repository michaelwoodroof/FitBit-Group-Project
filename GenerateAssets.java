// Contributor -- Michael Woodroof

// Imports
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GenerateAssets {

    public ImageView createIconButton(String imgName, int height, int width) {
        // Get Path
        ImageView output = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream("assets/icons/" + imgName));
            output = new ImageView(image);
            output.setFitHeight(height);
            output.setFitWidth(width);
        } catch (Exception e) {}

        return output;
    }

    @FXML
    public void logout(Button btn) throws IOException {
        LoggedInUser.DeleteFile();

        // After Deletion Return to Login UI
        loadLoginUI();

        // Close Scene
        Stage stagePrev = (Stage) btn.getScene().getWindow();
        stagePrev.close();
    }

    @FXML
    public void loadLoginUI() throws IOException {
        // load New Window
        Parent root = FXMLLoader.load(getClass().getResource("xml/login.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/login.css").toExternalForm());

        // Set Stage Properties
        Stage stageLog = new Stage();
        stageLog.setMinWidth(600);
        stageLog.setMinHeight(400);
        stageLog.setWidth(600);
        stageLog.setHeight(400);
        stageLog.setTitle("Login Portal");
        stageLog.getIcons().add(new Image("/assets/icons/loginIcon.png"));
        stageLog.setScene(scene);
        stageLog.show();
        stageLog.setResizable(false);
    }

    @FXML
    public void loadUI(Stage currentStage, String stageTitle, String xmlTitle, String cssPath) throws IOException {
        Stage stage = currentStage;

        stage.setTitle(stageTitle);
        stage.getIcons().add(new Image("/assets/icons/loginIcon.png"));

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("xml/" + xmlTitle)));
        scene.getStylesheets().add(getClass().getResource("css/" + cssPath).toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    @FXML
    public void loadUI(Stage currentStage, String stageTitle, String xmlTitle, String cssPath, int height, int width) throws IOException {
        Stage stage = currentStage;

        stage.setTitle(stageTitle);
        stage.getIcons().add(new Image("/assets/icons/loginIcon.png"));

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("xml/" + xmlTitle)));
        scene.getStylesheets().add(getClass().getResource("css/" + cssPath).toExternalForm());

        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    @FXML
    public void loadUI(Stage currentStage, String stageTitle, String xmlTitle, String cssPath, int height, int width, boolean isMax) throws IOException {
        Stage stage = currentStage;

        stage.setTitle(stageTitle);
        stage.getIcons().add(new Image("/assets/icons/loginIcon.png"));

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("xml/" + xmlTitle)));
        scene.getStylesheets().add(getClass().getResource("css/" + cssPath).toExternalForm());

        stage.setScene(scene);
        stage.setHeight(height);
        stage.setMaximized(isMax);
        stage.setWidth(width);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

}
