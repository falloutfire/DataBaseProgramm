package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbidDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FilterLayoutController {

    public ComboBox marksComboBox;
    public ComboBox destroyComboBox;
    public ComboBox cutComboBox;
    public ComboBox manufacturerComboBox;
    private Stage dialogStage;
    private boolean okClicked = false;
    private ObservableList<String> marks, cuts, destroys, manufacturers;

    public FilterLayoutController() {
    }

    public void initialize(){
        try {
            marks = CarbidDAO.getMarkCarbide();
            cuts = CarbidDAO.getClassCut();
            destroys = CarbidDAO.getClassDestroy();
            manufacturers = CarbidDAO.getManufacturer();
            marksComboBox.setItems(marks);
            cutComboBox.setItems(cuts);
            destroyComboBox.setItems(destroys);
            manufacturerComboBox.setItems(manufacturers);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void onClickCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
