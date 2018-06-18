package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.Manufacturer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddManufacturerLayoutController {

    public TextField nameField;
    public TextField addressField;
    public TextField telephoneField;
    private Stage dialogStage;

    public AddManufacturerLayoutController() {
    }

    public void initialize(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void onClickAdd(ActionEvent actionEvent) {
        Manufacturer manufacturer = new Manufacturer();
        if(nameField.getText() != null && addressField.getText() != null && telephoneField.getText() != null){
            manufacturer.setName(nameField.getText());
            manufacturer.setTelephone(telephoneField.getText());
            manufacturer.setAddress(addressField.getText());
            try {
                CarbideDAO.addManufacturer(manufacturer);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Присутствует ошибка в одном из полей");
            alert.setContentText("Проверьте все поля на наличие ошибок!");
            alert.showAndWait();
        }
    }

    public void onClickClose(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
