import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Notifications {
	
	//References:
	//https://o7planning.org/en/11529/javafx-alert-dialogs-tutorial?fbclid=IwAR0_aRvoMJvN_xvjZoXOnwpjw7GWpDloOJT_SPoVaCLhTibkzE70IW12wM0
	//https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html
	//https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html

	public static void Display(String content, String header, String title) {
		Platform.runLater(() -> {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        alert.showAndWait();
        });
	}
}
