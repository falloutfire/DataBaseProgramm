package com.baseProject.FXMLControllers;

import com.baseProject.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LoginPassLayoutController {
    public TextField loginField;
    public PasswordField passwordField;
    private Main main;

    public void onClickEnter() throws IOException {
        String login = loginField.getText();
        String pass = passwordField.getText();
        /*final String resourceF = getClass().getResource("pass.txt").toExternalForm();
        List<String> lines = Files.readAllLines(Paths.get(resourceF.substring(8)), Charset.defaultCharset());
        String password = lines.get(0);
*/

        String result;
        final InputStream resourceF = this.getClass().getClassLoader().getResourceAsStream("com/baseProject/FXMLControllers/pass.txt");
        try(Scanner s = new Scanner(resourceF).useDelimiter("\\A")) {
            result = s.hasNext() ? s.next() : "";
        }
        System.out.println(result);
        if (Objects.equals(login, "Man") && Objects.equals(pass, "test")) {
            //main.openLayoutUser();
        } else if (Objects.equals(login, "Admin") && Objects.equals(pass, result)) {
            main.openLayoutAdmin();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка входа");
            alert.setHeaderText("Неверныое имя пользователя/пароль");
            alert.setContentText("Проверьте введеные вами данные.\n");
            alert.showAndWait();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
