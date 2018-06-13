package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbidDAO;
import com.baseProject.Entities.Carbide;
import com.baseProject.Util.DBUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DetailsLayoutController {

    public TextField idField;
    public TextField percentSiCField;
    public TextField percentCField;
    public TextField percentFeField;
    public TextField valueDestroyField;
    public TextField valueCutField;
    public ImageView imageMaterialView;
    public ComboBox markComboBox;
    public ComboBox typeOfUseComboBox;
    public ComboBox fractionNumberComboBox;
    public ComboBox classCutComboBox;
    public ComboBox classDestroyComboBox;
    public ComboBox manufacturerComboBox;
    public Label isUpdate;
    public TextField colorTextField;
    private Stage dialogStage;
    private boolean okClicked = false;
    private Image image;
    private Carbide carbide;
    private String mark;
    private String fractionNumber;
    private String typeOfUse;
    private String classCut;
    private String classDestroy;
    private String manufacturer;
    private ObservableList<String> markArray,fractionNumberArray, typeOfUseArray,
            classCutArray, classDestroyArray, manufacturerArray;
    private File file;

    public DetailsLayoutController() {
    }

    public void initialize() {
        manufacturerComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                manufacturer = String.valueOf(newValue);
            }
        });
        markComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mark = String.valueOf(newValue);
                try {
                    colorTextField.setText(CarbidDAO.getColor(mark));
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        fractionNumberComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                fractionNumber = (String.valueOf(newValue));
            }
        });
        typeOfUseComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                typeOfUse = String.valueOf(newValue);
                fractionNumberArray.clear();
                fractionNumberArray.addAll(CarbidDAO.setArrayListFractions(String.valueOf(newValue)));
                fractionNumberComboBox.getSelectionModel().select(0);
            }
        });
        classCutComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                classCut = String.valueOf(newValue);
            }
        });
        classDestroyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                classDestroy = String.valueOf(newValue);
            }
        });
        addComboItems();
    }

    private void addComboItems() {

        try {
            markArray = CarbidDAO.getMarkCarbide();
            classCutArray = CarbidDAO.getClassCut();
            classDestroyArray = CarbidDAO.getClassDestroy();
            fractionNumberArray = CarbidDAO.getFractionNumber();
            typeOfUseArray = CarbidDAO.getTypeOfUse();
            manufacturerArray = CarbidDAO.getManufacturer();

            classCutComboBox.setItems(classCutArray);
            classDestroyComboBox.setItems(classDestroyArray);
            markComboBox.setItems(markArray);
            typeOfUseComboBox.setItems(typeOfUseArray);
            fractionNumberComboBox.setItems(fractionNumberArray);
            manufacturerComboBox.setItems(manufacturerArray);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        Carbide carbide = new Carbide();
        carbide.setId(Integer.parseInt(idField.getText()));
        carbide.setMark(Integer.parseInt(mark));
        carbide.setFractionNumber(Integer.parseInt(fractionNumber));
        carbide.setPercentSiC(Float.parseFloat(percentSiCField.getText()));
        carbide.setPercentC(Float.parseFloat(percentCField.getText()));
        carbide.setPercentFe(Float.parseFloat(percentFeField.getText()));
        carbide.setTypeOfUse(typeOfUse);
        carbide.setClassCut(classCut);
        carbide.setClassDestroy(classDestroy);
        carbide.setValueCut(Float.parseFloat(valueCutField.getText()));
        carbide.setValueDestroy(Float.parseFloat(valueDestroyField.getText()));
        carbide.setManufacturer(manufacturer);

        CarbidDAO.setParameters(carbide, "Percent_SiC", carbide.getPercentSiC());
        CarbidDAO.setParameters(carbide, "Percent_C", carbide.getPercentC());
        CarbidDAO.setParameters(carbide, "Percent_Fe", carbide.getPercentFe());
        CarbidDAO.setParameters(carbide, "Value_Destroy", carbide.getValueDestroy());
        CarbidDAO.setParameters(carbide, "Value_Cut", carbide.getValueCut());
        try {
            CarbidDAO.setComboParameters(carbide, "class_destroy", "Name_class", carbide.getClassDestroy());
            CarbidDAO.setComboParameters(carbide, "class_of_cut", "Name_class", carbide.getClassCut());
            CarbidDAO.setComboParameters(carbide, "fractions", "F_number", String.valueOf(carbide.getFractionNumber()));
            CarbidDAO.setComboParameters(carbide, "manufacturer", "Name", carbide.getManufacturer());
            CarbidDAO.setComboParameters(carbide, "mark", "Mark", String.valueOf(carbide.getMark()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        isUpdate.setText("Материал обновлен");
    }

    public void onClickDelete(ActionEvent actionEvent) {
        try {
            CarbidDAO.deleteMaterialBase(carbide.getId());
            dialogStage.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setCarbide(Carbide carbide) {
        this.carbide = carbide;
        setCarbideToLayout();
    }

    private void setCarbideToLayout() {
        idField.setText(String.valueOf(carbide.getId()));
        markComboBox.getSelectionModel().select(String.valueOf(carbide.getMark()));
        colorTextField.setText(carbide.getColor());
        manufacturerComboBox.getSelectionModel().select(carbide.getManufacturer());
        percentSiCField.setText(String.valueOf(carbide.getPercentSiC()));
        percentCField.setText(String.valueOf(carbide.getPercentC()));
        percentFeField.setText(String.valueOf(carbide.getPercentFe()));
        typeOfUseComboBox.getSelectionModel().select(carbide.getTypeOfUse());
        fractionNumberComboBox.getSelectionModel().select(String.valueOf(carbide.getFractionNumber()));
        classCutComboBox.getSelectionModel().select(carbide.getClassCut());
        classDestroyComboBox.getSelectionModel().select(carbide.getClassDestroy());
        valueCutField.setText(String.valueOf(carbide.getValueCut()));
        valueDestroyField.setText(String.valueOf(carbide.getValueDestroy()));
        try {
            image = CarbidDAO.getImageMaterial(String.valueOf(carbide.getId()));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        imageMaterialView.setImage(image);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void onClickClose(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void onClickChooseImage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "JPEG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            if (!file.getPath().endsWith(".jpg")) {
                file = new File(file.getPath() + ".jpg");
                System.out.println(file);
            }
        }
    }

    public void onClickAdd(ActionEvent actionEvent) {
        Carbide carbide = new Carbide();
        try {
            if(file != null){
                carbide.setMark(Integer.parseInt(CarbidDAO.getComboParameters("mark", "Mark", mark)));
                carbide.setFractionNumber(Integer.parseInt(CarbidDAO.getComboParameters("fractions", "F_number", fractionNumber)));
                carbide.setManufacturer(CarbidDAO.getComboParameters("manufacturer", "Name", manufacturer));
                carbide.setTypeOfUse(CarbidDAO.getComboParameters("type_of_use", "Name_of_Type", typeOfUse));
                carbide.setClassDestroy(CarbidDAO.getComboParameters("class_destroy", "Name_Class", classDestroy));
                carbide.setClassCut(CarbidDAO.getComboParameters("class_of_cut", "Name_Class", classCut));
                carbide.setPercentSiC(Float.parseFloat(percentSiCField.getText()));
                carbide.setPercentC(Float.parseFloat(percentCField.getText()));
                carbide.setPercentFe(Float.parseFloat(percentFeField.getText()));
                carbide.setValueCut(Float.parseFloat(valueCutField.getText()));
                carbide.setValueDestroy(Float.parseFloat(valueDestroyField.getText()));

                CarbidDAO.addMaterialInBase(carbide, file);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
