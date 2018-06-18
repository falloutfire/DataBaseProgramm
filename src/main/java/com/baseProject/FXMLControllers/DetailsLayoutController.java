package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.Carbide;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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
import java.util.regex.Pattern;

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
    public TextField priceField;
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
        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        percentSiCField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) percentSiCField.setText(oldValue);
        });
        percentCField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) percentCField.setText(oldValue);
        });
        percentFeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) percentFeField.setText(oldValue);
        });
        valueCutField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) valueCutField.setText(oldValue);
        });
        valueDestroyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) valueDestroyField.setText(oldValue);
        });
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) priceField.setText(oldValue);
        });

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
                    colorTextField.setText(CarbideDAO.getColor(mark));
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
                fractionNumberArray.addAll(com.baseProject.DAO.CarbideDAO.setArrayListFractions(String.valueOf(newValue)));
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
            markArray = com.baseProject.DAO.CarbideDAO.getMarkCarbide();
            classCutArray = com.baseProject.DAO.CarbideDAO.getClassCut();
            classDestroyArray = com.baseProject.DAO.CarbideDAO.getClassDestroy();
            fractionNumberArray = com.baseProject.DAO.CarbideDAO.getFractionNumber();
            typeOfUseArray = com.baseProject.DAO.CarbideDAO.getTypeOfUse();
            manufacturerArray = com.baseProject.DAO.CarbideDAO.getManufacturer();

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
        carbide.setPrice(Float.parseFloat(priceField.getText()));

        CarbideDAO.setParameters(carbide, "Percent_SiC", carbide.getPercentSiC());
        CarbideDAO.setParameters(carbide, "Percent_C", carbide.getPercentC());
        CarbideDAO.setParameters(carbide, "Percent_Fe", carbide.getPercentFe());
        CarbideDAO.setParameters(carbide, "Value_Destroy", carbide.getValueDestroy());
        CarbideDAO.setParameters(carbide, "Value_Cut", carbide.getValueCut());
        CarbideDAO.setParameters(carbide, "Price", carbide.getPrice());
        try {
            CarbideDAO.setComboParameters(carbide, "class_destroy", "Name_class", carbide.getClassDestroy());
            CarbideDAO.setComboParameters(carbide, "class_of_cut", "Name_class", carbide.getClassCut());
            CarbideDAO.setComboParameters(carbide, "fractions", "F_number", String.valueOf(carbide.getFractionNumber()));
            CarbideDAO.setComboParameters(carbide, "manufacturer", "Name", carbide.getManufacturer());
            CarbideDAO.setComboParameters(carbide, "mark", "Mark", String.valueOf(carbide.getMark()));
            if(file != null){
                com.baseProject.DAO.CarbideDAO.setImage(carbide, file);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        isUpdate.setText("Материал обновлен");
    }

    public void onClickDelete(ActionEvent actionEvent) {
        try {
            com.baseProject.DAO.CarbideDAO.deleteMaterialBase(carbide.getId());
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
        priceField.setText(String.valueOf(carbide.getPrice()));
        try {
            image = com.baseProject.DAO.CarbideDAO.getImageMaterial(String.valueOf(carbide.getId()));
            imageMaterialView.setImage(image);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
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
            }
            Image img = new Image("file:" + file.getAbsolutePath(), 255, 212, true, true);
            imageMaterialView.setImage(img);
        }
    }

    public void onClickAdd(ActionEvent actionEvent) {
        Carbide carbide = new Carbide();
        try {
            float sic = Float.parseFloat(percentSiCField.getText());
            float c = Float.parseFloat(percentCField.getText());
            float fe = Float.parseFloat(percentFeField.getText());
            float cut = Float.parseFloat(valueCutField.getText());
            float dest = Float.parseFloat(valueDestroyField.getText());
            float pr = Float.parseFloat(priceField.getText());
            if (0 < sic && sic < 100 && 0 < c && c < 100 && 0 < fe && fe < 100 && 0 < cut && cut < 100 && 0 < dest && dest < 100 && pr < 1000) {
                if (file != null) {
                    carbide.setMark(Integer.parseInt(com.baseProject.DAO.CarbideDAO.getComboParameters("mark", "Mark", mark)));
                    carbide.setFractionNumber(Integer.parseInt(com.baseProject.DAO.CarbideDAO.getComboParameters("fractions", "F_number", fractionNumber)));
                    carbide.setManufacturer(com.baseProject.DAO.CarbideDAO.getComboParameters("manufacturer", "Name", manufacturer));
                    carbide.setTypeOfUse(com.baseProject.DAO.CarbideDAO.getComboParameters("type_of_use", "Name_of_Type", typeOfUse));
                    carbide.setClassDestroy(com.baseProject.DAO.CarbideDAO.getComboParameters("class_destroy", "Name_Class", classDestroy));
                    carbide.setClassCut(com.baseProject.DAO.CarbideDAO.getComboParameters("class_of_cut", "Name_Class", classCut));
                    carbide.setPercentSiC(Float.parseFloat(percentSiCField.getText()));
                    carbide.setPercentC(Float.parseFloat(percentCField.getText()));
                    carbide.setPercentFe(Float.parseFloat(percentFeField.getText()));
                    carbide.setValueCut(Float.parseFloat(valueCutField.getText()));
                    carbide.setValueDestroy(Float.parseFloat(valueDestroyField.getText()));
                    carbide.setPrice(Float.parseFloat(priceField.getText()));
                    CarbideDAO.addMaterialInBase(carbide, file);
                    dialogStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Не выбрано изображение материала");
                    alert.setContentText("Выберете изображение материала!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Присутствует ошибка в одном из полей");
                alert.setContentText("Проверьте все поля на наличие ошибок!");
                alert.showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
