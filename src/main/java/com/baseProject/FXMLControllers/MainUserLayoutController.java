package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.Carbide;
import com.baseProject.Entities.Manufacturer;
import com.baseProject.Entities.User;
import com.baseProject.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class MainUserLayoutController {
    public TableView<Manufacturer> manufacturerView;
    public TableColumn<Manufacturer, Integer> idManufColumn;
    public TableColumn<Manufacturer, String> nameManufColumn;
    public TableColumn<Manufacturer, String> adressColumn;
    public TableColumn<Manufacturer, String> telColumn;
    public TableView<Carbide> carbidesView;
    public TableColumn<Carbide, Integer> idColumn;
    public TableColumn<Carbide, Integer> markColumn;
    public TableColumn<Carbide, String> manufacturerColumn;
    public TableColumn<Carbide, String> colorColumn;
    public TableColumn<Carbide, Integer> fractionColumn;
    public TableColumn<Carbide, Float> percentSicColumn;
    public TableColumn<Carbide, Float> percentFeColumn;
    public TableColumn<Carbide, Float> percentcColumn;
    public TableColumn<Carbide, String> typeOfUSeColumn;
    public TableColumn<Carbide, String> classCutColumn;
    public TableColumn<Carbide, Float> valueCutColumn;
    public TableColumn<Carbide, String> classDestroyColumn;
    public TableColumn<Carbide, Float> valueDestroyColumn;
    public TableColumn<Carbide, Float> priceColumn;
    public TableView<User> userView;
    public TableColumn<User, String> nameUserColumn;
    public TableColumn<User, String> passColumn;
    public TableColumn<User, Boolean> isAdminColumn;
    private Main main;
    private ObservableList<Carbide> carbides;
    private boolean isUser = false;

    public MainUserLayoutController() {
    }

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        markColumn.setCellValueFactory(cellData -> cellData.getValue().markProperty().asObject());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        fractionColumn.setCellValueFactory(cellData -> cellData.getValue().fractionNumberProperty().asObject());
        percentSicColumn.setCellValueFactory(cellData -> cellData.getValue().percentSiCProperty().asObject());
        percentFeColumn.setCellValueFactory(cellData -> cellData.getValue().percentFeProperty().asObject());
        percentcColumn.setCellValueFactory(cellData -> cellData.getValue().percentCProperty().asObject());
        typeOfUSeColumn.setCellValueFactory(cellData -> cellData.getValue().typeOfUseProperty());
        classCutColumn.setCellValueFactory(cellData -> cellData.getValue().classCutProperty());
        valueCutColumn.setCellValueFactory(cellData -> cellData.getValue().valueCutProperty().asObject());
        classDestroyColumn.setCellValueFactory(cellData -> cellData.getValue().classDestroyProperty());
        valueDestroyColumn.setCellValueFactory(cellData -> cellData.getValue().valueDestroyProperty().asObject());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        idManufColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameManufColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        adressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        telColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        nameManufColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        adressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameManufColumn.setOnEditCommit(event -> {
            TablePosition<Manufacturer, String> pos = event.getTablePosition();
            String newNameMaterial = event.getNewValue();
            if (newNameMaterial != null) {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setName(newNameMaterial);
                CarbideDAO.updateManufacturer(materialBase);
                updateTable();
            } else {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setName(event.getOldValue());
                pos.getTableView().getItems().set(pos.getRow(), materialBase);
            }
        });
        adressColumn.setOnEditCommit(event -> {
            TablePosition<Manufacturer, String> pos = event.getTablePosition();
            String newNameMaterial = event.getNewValue();
            if (newNameMaterial != null) {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setAddress(newNameMaterial);
                CarbideDAO.updateManufacturer(materialBase);
                updateTable();
            } else {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setName(event.getOldValue());
                pos.getTableView().getItems().set(pos.getRow(), materialBase);
            }
        });
        telColumn.setOnEditCommit(event -> {
            TablePosition<Manufacturer, String> pos = event.getTablePosition();
            String newNameMaterial = event.getNewValue();
            if (newNameMaterial != null) {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setTelephone(newNameMaterial);
                CarbideDAO.updateManufacturer(materialBase);
                updateTable();
            } else {
                Manufacturer materialBase = pos.getTableView().getItems().get(pos.getRow());
                materialBase.setName(event.getOldValue());
                pos.getTableView().getItems().set(pos.getRow(), materialBase);
            }
        });

        ObservableList<Manufacturer> manufacturers = CarbideDAO.searchAllManufacturers();
        ObservableList<Carbide> carbides = CarbideDAO.searchAllCarbides();
        carbidesView.setItems(carbides);
        manufacturerView.setItems(manufacturers);
        manufacturerView.setEditable(true);
    }

    public void onClickShowAll(ActionEvent actionEvent) {
        updateTable();
    }

    public void onClickFilter(ActionEvent actionEvent) {
        main.showMaterialFilterDialog();
    }

    @FXML
    public void onClickShowDetails(MouseEvent event) {
        if (event.getClickCount() == 2 && isUser) {
            boolean okClicked = main.showMaterialDetailDialogUser(carbidesView.getSelectionModel().getSelectedItem());
            if (okClicked) {
                updateTable();
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void updateTable() {
        carbides = CarbideDAO.searchAllCarbides();
        ObservableList<Manufacturer> manufacturers = CarbideDAO.searchAllManufacturers();
        carbidesView.setItems(carbides);
        manufacturerView.setItems(manufacturers);
    }

    public void onClickShowAllM(ActionEvent actionEvent) {
        updateTable();
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
