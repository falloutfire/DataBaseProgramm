package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbidDAO;
import com.baseProject.Entities.Carbide;
import com.baseProject.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class FilterLayoutController {

    public ComboBox marksComboBox;
    public ComboBox destroyComboBox;
    public ComboBox cutComboBox;
    public ComboBox manufacturerComboBox;
    private Stage dialogStage;
    private boolean okClicked = false;
    private ArrayList<String> queries = new ArrayList<>();
    private String mark;
    private String destroy;
    private String cut;
    private String manufacturer;
    private ObservableList<String> marks, cuts, destroys, manufacturers;
    private Main main;

    public FilterLayoutController() {
    }

    public void initialize(){
        manufacturerComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                manufacturer = String.valueOf(newValue);
            }
        });
        cutComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                cut = String.valueOf(newValue);
            }
        });
        destroyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                destroy = String.valueOf(newValue);
            }
        });
        marksComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mark = String.valueOf(newValue);
            }
        });

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

    public void onClickSearch(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(mark != null){
            mark = CarbidDAO.getComboParameters("mark", "Mark", mark);
            queries.add("ID_mark = " + mark);
        }
        if(manufacturer != null){
            mark = CarbidDAO.getComboParameters("manufacturer", "Name", manufacturer);
            queries.add("ID_manufacturer = " + manufacturer);
        }
        if(cut != null){
            cut = CarbidDAO.getComboParameters("class_of_cut", "Name_Class", cut);
            queries.add("ID_Class_Cut = " + cut);
        }
        if(destroy != null){
            destroy = CarbidDAO.getComboParameters("class_destroy", "Name_Class", destroy);
            queries.add("ID_Class_destroy = " + destroy);
        }

        main.getMainLayoutController().setCarbides(CarbidDAO.searchAllCarbides(queries));
        dialogStage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
