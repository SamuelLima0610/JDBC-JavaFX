package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.service.DepartmentService;

public class DepartmentFormController implements Initializable{

	private DepartmentService service;
	
	private Department entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}catch(DbException e) {
			Alerts.showAlert("Error save database", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancel");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializedNodes();
	}
	
	private void initializedNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldLength(txtName, 30);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryPasserToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}
}
