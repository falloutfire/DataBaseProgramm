package com.baseProject;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.Carbide;
import com.baseProject.FXMLControllers.*;
import com.baseProject.Util.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainLayoutController mainLayoutController;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Carbides DataBase");
        primaryStage.sizeToScene();
        if (CarbideDAO.searchAllCarbidesCheck()) {
            File pathFile = new File("backUp");
            pathFile.mkdir();
            try {
                DBUtil.dbBackUp(pathFile + "\\backUp.sql");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showLoginPassLayout();
    }

    private void showLoginPassLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/LoginPassLayout.fxml"));
        AnchorPane loginPane = loader.load();
        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginPassLayoutController loginPassLayoutController = loader.getController();
        loginPassLayoutController.setMain(this);
    }

    private void showDataBaseLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/MainLayout.fxml"));
            AnchorPane mainPane = loader.load();

            rootLayout.setCenter(mainPane);

            mainLayoutController = loader.getController();
            mainLayoutController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRootDataBaseLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

            RootLayoutController rootLayoutUserController = loader.getController();
            rootLayoutUserController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDataBaseLayoutUser() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/UserMainLayout.fxml"));
            AnchorPane mainPane = loader.load();

            rootLayout.setCenter(mainPane);

            mainLayoutController = loader.getController();
            mainLayoutController.setMain(this);
            mainLayoutController.setUser(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRootDataBaseLayoutUser() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/RootUserLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

            RootLayoutController rootLayoutUserController = loader.getController();
            rootLayoutUserController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openLayoutAdmin() {
        showRootDataBaseLayout();
        showDataBaseLayout();
    }

    public void openLayoutUser() {
        showRootDataBaseLayoutUser();
        showDataBaseLayoutUser();
    }

    public void aboutWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Systems and Technology");
        alert.setHeaderText("О программе");
        alert.setContentText("Авторы: Илья Лихачев, Илья Родионов\nГруппа 455");

        alert.showAndWait();
    }

    public boolean showMaterialFilterDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/FilterLayout.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Фильтры");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            FilterLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showMaterialDetailDialog(Carbide carbide) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/DetailsLayout.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Информация");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            DetailsLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCarbide(carbide);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showMaterialDetailDialogUser(Carbide carbide) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/DetailsLayoutUser.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Информация");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            DetailsLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCarbide(carbide);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showMaterialAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/AddMaterialLayout.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            DetailsLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showManufacturerAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/AddManufacturerLayout.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            AddManufacturerLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainLayoutController getMainLayoutController() {
        return mainLayoutController;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
