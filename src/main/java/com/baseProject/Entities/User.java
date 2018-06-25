package com.baseProject.Entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty name;
    private StringProperty pass;
    private BooleanProperty isAdmin;

    public User() {
        this.name = new SimpleStringProperty();
        this.pass = new SimpleStringProperty();
        this.isAdmin = new SimpleBooleanProperty();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public StringProperty passProperty() {
        return pass;
    }

    public boolean isIsAdmin() {
        return isAdmin.get();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public BooleanProperty isAdminProperty() {
        return isAdmin;
    }
}
