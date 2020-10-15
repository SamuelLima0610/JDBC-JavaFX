package gui.util;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class Alerts {
	
	public static void showAlert(String title,String header,String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
}
