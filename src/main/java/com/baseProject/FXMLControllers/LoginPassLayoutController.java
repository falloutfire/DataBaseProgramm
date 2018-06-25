package com.baseProject.FXMLControllers;

import com.baseProject.DAO.CarbideDAO;
import com.baseProject.Entities.User;
import com.baseProject.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Objects;

public class LoginPassLayoutController {
    public TextField loginField;
    public PasswordField passwordField;
    private Main main;

    public void onClickEnter() {
        String login = loginField.getText();
        String pass = passwordField.getText();
        User user;
        try {
            user = CarbideDAO.enterIn(login);
            if (Objects.equals(login, user.getName()) && Objects.equals(pass, user.getPass()) && !user.isIsAdmin()) {
                main.openLayoutUser();
            } else if (Objects.equals(login, user.getName()) && Objects.equals(pass, user.getPass()) && user.isIsAdmin()) {
                main.openLayoutAdmin();
            } else {
                getAlert();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (Objects.equals(login, "admin") && (Objects.equals(pass, "admin"))) {
                main.openLayoutAdmin();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка входа");
                alert.setHeaderText("Отсутствует подключение к базе данных");
                alert.setContentText("Проверьте подключение или же войдите в аккаунт администратора для восстановления базы данных.\n");
                alert.showAndWait();
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void getAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка входа");
        alert.setHeaderText("Неверныое имя пользователя/пароль");
        alert.setContentText("Проверьте введеные вами данные.\n");
        alert.showAndWait();
    }
}
