package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.BaseWrapper;
import com.baseProject.Main;
import com.baseProject.Util.DBUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

public class RootLayoutController {

    private Main main;

    public void handleClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleAbout(ActionEvent actionEvent) {
        main.aboutWindow();
    }

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void onClickExport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
       /* FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "SQL files (*.sql)", "*.sql");*/
        FileChooser.ExtensionFilter extensionFilter1 = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        //fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.getExtensionFilters().add(extensionFilter1);
        //File file = fileChooser.showSaveDialog(main.getPrimaryStage());
        File file1 = fileChooser.showSaveDialog(main.getPrimaryStage());
        if (/*file != null ||*/ file1 != null) {
            /*if (!file.getPath().endsWith(".sql")) {
                file = new File(file.getPath() + ".sql");
            }*/
            if (!file1.getPath().endsWith(".xml")) {
                file1 = new File(file1.getPath() + ".xml");
            }
        }

        try {
            JAXBContext context = JAXBContext
                    .newInstance(BaseWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Обёртываем наши данные
            BaseWrapper wrapper = new BaseWrapper();
            wrapper.setMaterials(CarbideDAO.searchAllCarbides());
            wrapper.setManufacturers(CarbideDAO.searchAllManufacturers());
            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file1);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Экспорт");
            alert.setHeaderText("База данных экспортирована");
            alert.setContentText("База данных экспортирована в : " + file1.getAbsolutePath());
            alert.showAndWait();
        } catch (JAXBException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Экспорт");
            alert.setHeaderText("Невозможно экспортировать базу данных");
            alert.setContentText("Невозможно экспортировать базу данных в: " + file1.getAbsolutePath());
            alert.showAndWait();
        }
    }

    public void onClickRestore(ActionEvent actionEvent) {

        File pathFile = new File("backUp");
        pathFile.mkdir();
        try {
            DBUtil.dbRestore(pathFile + "\\backUp.sql");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void onClickCheck(ActionEvent actionEvent) {
        if (CarbideDAO.searchAllCarbidesCheck()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Проверка завершена");
            alert.setHeaderText("Проверка целостности базы данных завершена.");
            alert.setContentText("Ошибок целостности не обнаружено.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проверка завершена");
            alert.setHeaderText("Обнаружены ошибки целостности базы данных");
            alert.setContentText("Восстановите базу из резервной копии");
            alert.showAndWait();
        }
    }
}
