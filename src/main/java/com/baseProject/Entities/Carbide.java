package com.baseProject.Entities;

import javafx.beans.property.*;

public class Carbide {
    private IntegerProperty id;
    private StringProperty classCut;
    private StringProperty classDestroy;
    private IntegerProperty fractionNumber;
    private StringProperty typeOfUse;
    private IntegerProperty mark;
    private StringProperty color;
    private StringProperty manufacturer;
    private FloatProperty percentSiC;
    private FloatProperty percentFe;
    private FloatProperty percentC;
    private FloatProperty valueDestroy;
    private FloatProperty valueCut;
    private FloatProperty price;

    public Carbide() {
        this.id = new SimpleIntegerProperty();
        this.classCut = new SimpleStringProperty();
        this.classDestroy = new SimpleStringProperty();
        this.fractionNumber = new SimpleIntegerProperty();
        this.typeOfUse = new SimpleStringProperty();
        this.mark = new SimpleIntegerProperty();
        this.color = new SimpleStringProperty();
        this.manufacturer = new SimpleStringProperty();
        this.percentSiC = new SimpleFloatProperty();
        this.percentFe = new SimpleFloatProperty();
        this.percentC = new SimpleFloatProperty();
        this.valueDestroy = new SimpleFloatProperty();
        this.valueCut = new SimpleFloatProperty();
        this.price = new SimpleFloatProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getClassCut() {
        return classCut.get();
    }

    public StringProperty classCutProperty() {
        return classCut;
    }

    public void setClassCut(String classCut) {
        this.classCut.set(classCut);
    }

    public String getClassDestroy() {
        return classDestroy.get();
    }

    public StringProperty classDestroyProperty() {
        return classDestroy;
    }

    public void setClassDestroy(String classDestroy) {
        this.classDestroy.set(classDestroy);
    }

    public int getFractionNumber() {
        return fractionNumber.get();
    }

    public IntegerProperty fractionNumberProperty() {
        return fractionNumber;
    }

    public void setFractionNumber(int fractionNumber) {
        this.fractionNumber.set(fractionNumber);
    }

    public String getTypeOfUse() {
        return typeOfUse.get();
    }

    public StringProperty typeOfUseProperty() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse.set(typeOfUse);
    }

    public int getMark() {
        return mark.get();
    }

    public IntegerProperty markProperty() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark.set(mark);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public float getPercentSiC() {
        return percentSiC.get();
    }

    public FloatProperty percentSiCProperty() {
        return percentSiC;
    }

    public void setPercentSiC(float percentSiC) {
        this.percentSiC.set(percentSiC);
    }

    public float getPercentFe() {
        return percentFe.get();
    }

    public FloatProperty percentFeProperty() {
        return percentFe;
    }

    public void setPercentFe(float percentFe) {
        this.percentFe.set(percentFe);
    }

    public float getPercentC() {
        return percentC.get();
    }

    public FloatProperty percentCProperty() {
        return percentC;
    }

    public void setPercentC(float percentC) {
        this.percentC.set(percentC);
    }

    public float getValueDestroy() {
        return valueDestroy.get();
    }

    public FloatProperty valueDestroyProperty() {
        return valueDestroy;
    }

    public void setValueDestroy(float valueDestroy) {
        this.valueDestroy.set(valueDestroy);
    }

    public float getValueCut() {
        return valueCut.get();
    }

    public FloatProperty valueCutProperty() {
        return valueCut;
    }

    public void setValueCut(float valueCut) {
        this.valueCut.set(valueCut);
    }

    public float getPrice() {
        return price.get();
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public FloatProperty priceProperty() {
        return price;
    }
}
