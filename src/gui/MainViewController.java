package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartmentService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("Seller");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadView(String absolute) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute));
			VBox vbox = loader.load();
			Scene scene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vbox.getChildren());
		}catch(IOException e) {
			Alerts.showAlert("Erro ao carregar página", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void loadView2(String absolute) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute));
			VBox vbox = loader.load();
			Scene scene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vbox.getChildren());
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		}catch(IOException e) {
			Alerts.showAlert("Erro ao carregar página", null, e.getMessage(), AlertType.ERROR);
		}
	}

}
