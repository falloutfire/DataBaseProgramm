package com.baseProject.Entities;

import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lists")
public class BaseWrapper {

    private ObservableList<Carbide> materials;
    private ObservableList<Manufacturer> manufacturers;

    public ObservableList<Carbide> getMaterials() {
        return materials;
    }

    public void setMaterials(ObservableList<Carbide> materials) {
        this.materials = materials;
    }

    public ObservableList getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(ObservableList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}
