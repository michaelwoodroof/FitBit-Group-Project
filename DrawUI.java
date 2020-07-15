// Contributor -- Michael Woodroof

// Imports
import java.io.*;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DrawUI extends Application {

    // Stage Used for all UI
    private Stage mainStage;

    @Override
    public void start(Stage mainStage) throws Exception {
        // Load XML Layout of UI
        Parent root = FXMLLoader.load(getClass().getResource("xml/login.fxml"));

        // Set Scene to Root
        Scene scene = new Scene(root);

        // Get Style Sheet
        scene.getStylesheets().add(getClass().getResource("css/login.css").toExternalForm());

        // Set Stage Properties
        mainStage.setResizable(false);
        mainStage.setHeight(400);
        mainStage.setWidth(600);
        mainStage.setTitle("Login Portal");
        mainStage.getIcons().add(new Image("/assets/icons/loginIcon.png"));
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
