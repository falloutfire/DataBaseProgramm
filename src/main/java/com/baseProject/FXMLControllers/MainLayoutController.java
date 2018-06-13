package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbidDAO;
import com.baseProject.Entities.Carbide;
import com.baseProject.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;


public class MainLayoutController {

    @FXML
    private TableView<Carbide> carbidesView;
    @FXML
    private TableColumn<Carbide,Integer> idColumn;
    @FXML
    private TableColumn<Carbide,Integer> markColumn;
    @FXML
    private TableColumn<Carbide,String> manufacturerColumn;
    @FXML
    private TableColumn<Carbide,String> colorColumn;
    @FXML
    private TableColumn<Carbide,Integer> fractionColumn;
    @FXML
    private TableColumn<Carbide,Float> percentSicColumn;
    @FXML
    private TableColumn<Carbide,Float> percentFeColumn;
    @FXML
    private TableColumn<Carbide,Float> percentcColumn;
    @FXML
    private TableColumn<Carbide,String> typeOfUSeColumn;
    @FXML
    private TableColumn<Carbide,String> classCutColumn;
    @FXML
    private TableColumn<Carbide,Float> valueCutColumn;
    @FXML
    private TableColumn<Carbide,String> classDestroyColumn;
    @FXML
    private TableColumn<Carbide,Float> valueDestroyColumn;
    private Main main;

    public MainLayoutController() {
    }

    public void initialize(){
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
        ObservableList<Carbide> carbides = CarbidDAO.searchAllCarbides();
        carbidesView.setItems(carbides);
        //carbidesView.setItems(carbides);
    }

    public void onClickAdd(ActionEvent actionEvent) {
        boolean okClicked = main.showMaterialAddDialog();
        if (okClicked) {
            updateTable();
        }
    }

    public void onClickDelete(ActionEvent actionEvent) {
        try {
            CarbidDAO.deleteMaterialBase(carbidesView.getSelectionModel().getSelectedItem().getId());
            updateTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClickShowAll(ActionEvent actionEvent) {
        updateTable();
    }

    public void onClickFilter(ActionEvent actionEvent) {
        boolean okClicked = main.showMaterialFilterDialog();
        if (okClicked) {
            //ObservableList<Carbide> materialBases = CarbidDAO.searchAllCarbides();
            //carbidesView.setItems(materialBases);
            //addItemsCombo();
        }
        updateTable();
    }

    @FXML
    public void onClickShowDetails(MouseEvent event){
        if(event.getClickCount() == 2){
            boolean okClicked = main.showMaterialDetailDialog(carbidesView.getSelectionModel().getSelectedItem());
            if (okClicked) {
                //ObservableList<Carbide> materialBases = CarbidDAO.searchAllCarbides();
                //carbidesView.setItems(materialBases);
                //addItemsCombo();
            }
            updateTable();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void updateTable(){
        ObservableList<Carbide> carbides = CarbidDAO.searchAllCarbides();
        carbidesView.setItems(carbides);
    }
}
