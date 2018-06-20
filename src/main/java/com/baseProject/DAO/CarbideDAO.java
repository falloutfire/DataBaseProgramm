package com.baseProject.DAO;

import com.baseProject.Entities.Carbide;
import com.baseProject.Entities.Manufacturer;
import com.baseProject.Entities.User;
import com.baseProject.Util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarbideDAO {

    //метод который собирает фильтры
    private static String setStmtQuery(ArrayList<String> queries) {
        String stmtQuery;
        stmtQuery = "Select ID_material from material where ";
        for(int i = 0; i < queries.size(); i++)
            if(i != 0){
                stmtQuery = stmtQuery + " and " + queries.get(i);
            } else {
                stmtQuery = stmtQuery + queries.get(i);
            }
        System.out.println(stmtQuery);
        return stmtQuery;
    }

    public static User enterIn(String name) throws SQLException {
        User user = new User();
        String selectUser = "Select * from user where Name = '" + name + "';";
        ResultSet rs = DBUtil.dbExecuteQuery(selectUser);
        while (rs.next()) {
            user.setName(rs.getString("Name"));
            user.setPass(rs.getString("Pass"));
            user.setAdmin(rs.getBoolean("isAdmin"));
        }
        return user;
    }

    private static Carbide searchMaterialBase(String materialId) throws SQLException {
        //Declare a SELECT statement
        String selectStmtName = "Select * from material where ID_Material = " + materialId;

        Carbide carbide = new Carbide();
        try {
            ResultSet rsMatName = DBUtil.dbExecuteQuery(selectStmtName);
            int idMark;
            while (rsMatName.next()) {
                carbide.setId(rsMatName.getInt("ID_material"));
                idMark = rsMatName.getInt("ID_mark");

                String selectMark = "SELECT Mark, Color FROM mark WHERE ID_mark = '" + idMark + "'";
                ResultSet resultMark = DBUtil.dbExecuteQuery(selectMark);
                while (resultMark.next()) {
                    carbide.setMark(resultMark.getInt("Mark"));
                    carbide.setColor(resultMark.getString("Color"));
                }

                String selectCut = "SELECT Name_Class FROM class_of_cut WHERE ID_Class_Cut = '" + rsMatName.getString("ID_Class_Cut") + "'";
                ResultSet resultCut = DBUtil.dbExecuteQuery(selectCut);
                while (resultCut.next()) {
                    carbide.setClassCut(resultCut.getString("Name_Class"));
                }

                String selectFraction = "SELECT F_Number, ID_Type_of_Use FROM fractions WHERE ID_fraction = '" + rsMatName.getInt("ID_fraction") + "'";
                ResultSet resultFractions = DBUtil.dbExecuteQuery(selectFraction);
                while (resultFractions.next()) {
                    carbide.setFractionNumber(resultFractions.getInt("F_number"));
                    String selectTypeUse = "SELECT Name_of_Type FROM type_of_use WHERE ID_Type_of_Use = '" + resultFractions.getInt("ID_Type_of_Use") + "'";
                    ResultSet resultTypeUse = DBUtil.dbExecuteQuery(selectTypeUse);
                    while (resultTypeUse.next()) {
                        carbide.setTypeOfUse(resultTypeUse.getString("Name_of_Type"));
                    }
                }

                String selectDestroy = "SELECT Name_Class FROM class_destroy WHERE ID_Class_destroy = '" + rsMatName.getString("ID_Class_destroy") + "'";
                ResultSet resultDestroy = DBUtil.dbExecuteQuery(selectDestroy);
                while (resultDestroy.next()) {
                    carbide.setClassDestroy(resultDestroy.getString("Name_Class"));
                }

                String selectManufacturer = "Select Name from manufacturer where ID_manufacturer = '" + rsMatName.getString("ID_manufacturer") + "'";
                ResultSet resultManufacturer = DBUtil.dbExecuteQuery(selectManufacturer);
                while (resultManufacturer.next()) {
                    carbide.setManufacturer(resultManufacturer.getString("Name"));
                }

                carbide.setPercentSiC(rsMatName.getFloat("Percent_SiC"));
                carbide.setPercentFe(rsMatName.getFloat("Percent_Fe"));
                carbide.setPercentC(rsMatName.getFloat("Percent_C"));
                carbide.setValueCut(rsMatName.getFloat("Value_Cut"));
                carbide.setValueDestroy(rsMatName.getFloat("Value_Destroy"));
                carbide.setPrice(rsMatName.getFloat("Price"));
            }

            return carbide;
        } catch (SQLException e) {
            System.out.println("While searching an material with " + materialId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    public static ObservableList<Carbide> searchAllCarbides() {

        ObservableList<Carbide> carbides = FXCollections.observableArrayList();
        String selectStmt = "Select ID_Material from material";
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
            while (resultSet.next()) {
                String materialId = String.valueOf(resultSet.getInt("ID_Material"));
                carbides.add(searchMaterialBase(materialId));

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return carbides;
    }

    public static boolean searchAllCarbidesCheck() {

        ObservableList<Carbide> carbides = FXCollections.observableArrayList();
        String selectStmt = "Select ID_Material from material";
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
            while (resultSet.next()) {
                String materialId = String.valueOf(resultSet.getInt("ID_Material"));
                carbides.add(searchMaterialBase(materialId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ObservableList<Carbide> searchAllCarbides(ArrayList<String> queries) {

        ObservableList<Carbide> carbides = FXCollections.observableArrayList();
        String stringBuffer = setStmtQuery(queries);
        System.out.println(stringBuffer);
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(stringBuffer);
            while (resultSet.next()) {
                String materialId = String.valueOf(resultSet.getInt(1));
                carbides.add(searchMaterialBase(materialId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(carbides.size());
        return carbides;
    }

    public static ObservableList<String> getMarkCarbide() throws SQLException {
        ObservableList<String> marks = FXCollections.observableArrayList();
        String markStmt = "Select Mark from mark";
        ResultSet resultMark = DBUtil.dbExecuteQuery(markStmt);
        while (resultMark.next()) {
            marks.add(resultMark.getString("Mark"));
        }
        return marks;
    }

    public static ObservableList<String> getManufacturer() throws SQLException {
        ObservableList<String> manufacturers = FXCollections.observableArrayList();
        String manufStmt = "Select Name from manufacturer";
        ResultSet resultManuf = DBUtil.dbExecuteQuery(manufStmt);
        while (resultManuf.next()) {
            manufacturers.add(resultManuf.getString("Name"));
        }
        return manufacturers;
    }

    public static ObservableList<String> getClassDestroy() throws SQLException {
        ObservableList<String> destroys = FXCollections.observableArrayList();
        String destroyStmt = "Select Name_Class from class_destroy";
        ResultSet resultSet = DBUtil.dbExecuteQuery(destroyStmt);
        while (resultSet.next()) {
            destroys.add(resultSet.getString("Name_Class"));
        }
        return destroys;
    }

    public static ObservableList<String> getClassCut() throws SQLException {
        ObservableList<String> cuts = FXCollections.observableArrayList();
        String cutsStmt = "Select Name_Class from class_of_cut";
        ResultSet resultSet = DBUtil.dbExecuteQuery(cutsStmt);
        while (resultSet.next()) {
            cuts.add(resultSet.getString("Name_Class"));
        }
        return cuts;
    }

    public static ObservableList<String> getTypeOfUse() throws SQLException {
        ObservableList<String> cuts = FXCollections.observableArrayList();
        String cutsStmt = "Select Name_of_Type from type_of_use";
        ResultSet resultSet = DBUtil.dbExecuteQuery(cutsStmt);
        while (resultSet.next()) {
            cuts.add(resultSet.getString("Name_of_Type"));
        }
        return cuts;
    }

    public static ObservableList<String> getFractionNumber() throws SQLException {
        ObservableList<String> cuts = FXCollections.observableArrayList();
        String cutsStmt = "Select F_number from fractions";
        ResultSet resultSet = DBUtil.dbExecuteQuery(cutsStmt);
        while (resultSet.next()) {
            cuts.add(resultSet.getString("F_number"));
        }
        return cuts;
    }

    public static String getColor(String mark) throws SQLException {
        String markStmt = "Select Color from mark where mark = " + mark;
        String color = "_";
        ResultSet resultMark = DBUtil.dbExecuteQuery(markStmt);
        while (resultMark.next()) {
            color = resultMark.getString("Color");
        }
        return color;
    }

    public static Image getImageMaterial(String materialId) throws SQLException, IOException {
        Image image;
        String selectImage = "Select Image from material where ID_material = '" + materialId + "'";
        ResultSet resultSet = DBUtil.dbExecuteQuery(selectImage);
        byte[] content = new byte[1024];
        int size = 0;
        File folder = new File("res");
        if (!folder.exists()) {
            folder.mkdir();
        }
        while (resultSet.next()) {
            InputStream is = resultSet.getBinaryStream("Image");
            OutputStream os = new FileOutputStream(new File(folder + "/image.jpg"));
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();

        }
        image = new Image("file:res/image.jpg", 255, 212, true, true);
        return image;
    }

    public static void setParameters(Carbide carbide, String nameParameter, float value) {
        String updateParameters =
                "UPDATE material\n" +
                        "SET " + nameParameter + "=" + value + "\n" +
                        "WHERE ID_material=" + carbide.getId() + ";";
        try {
            DBUtil.dbExecuteUpdate(updateParameters);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
        }
    }

    public static void setComboParameters(Carbide carbide, String table, String nameParameter, String value) throws SQLException {
        String selectParameter = "Select * from " + table +
                " where " + nameParameter + " = '" + value + "'";
        String idParameter = "0";
        String nameColumn;
        ResultSet rs = DBUtil.dbExecuteQuery(selectParameter);
        ResultSetMetaData rmd = rs.getMetaData();
        nameColumn = rmd.getColumnName(1);
        while (rs.next()) {
            idParameter = rs.getString(1);
        }
        String updateParameter = "Update material\n" +
                "SET " + nameColumn + "=" + idParameter + "\n" +
                "WHERE ID_material=" + carbide.getId() + ";";
        DBUtil.dbExecuteUpdate(updateParameter);
    }

    public static ObservableList<String> setArrayListFractions(String typeOfUse) {
        String selectTypeID = "Select ID_Type_of_Use from type_of_use " +
                "where Name_of_type = '" + typeOfUse + "'";
        String id = "0";
        ObservableList<String> numbers = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectTypeID);
            while (resultSet.next()) {
                id = resultSet.getString(1);
            }
            String selectNumbers = "Select F_number from fractions where ID_Type_of_Use = " + id;
            ResultSet resultSetNumbers = DBUtil.dbExecuteQuery(selectNumbers);
            while (resultSetNumbers.next()) {
                numbers.add(resultSetNumbers.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public static void deleteMaterialBase(int materialId) throws SQLException {
        String deleteStmtMaterial =
                "DELETE FROM material\n" +
                        "WHERE ID_material = " + materialId + ";";
        DBUtil.dbExecuteUpdate(deleteStmtMaterial);
    }

    public static void addMaterialInBase(Carbide carbide, File file){
        String addStmt = "Insert into material (Image, ID_Class_Cut, ID_Class_destroy, ID_fraction, ID_mark, ID_manufacturer," +
                "Percent_SiC, Percent_Fe, Percent_C, Value_Destroy, Value_Cut, Price) value ( ?, " + carbide.getClassCut() + "," +
                carbide.getClassDestroy() + "," + carbide.getFractionNumber() + "," + carbide.getMark() + "," +
                carbide.getManufacturer() + "," + carbide.getPercentSiC() + "," + carbide.getPercentFe() + "," +
                carbide.getPercentC() + "," + carbide.getValueDestroy() + "," + carbide.getValueCut() + "," + carbide.getPrice() + ");";
        try {
            DBUtil.dbExecuteImage(addStmt, file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getComboParameters(String table, String nameParameter, String value) throws SQLException {
        String selectParameter = "Select * from " + table +
                " where " + nameParameter + " = '" + value + "'";
        String idParameter = "0";
        ResultSet rs = DBUtil.dbExecuteQuery(selectParameter);
        while (rs.next()) {
            idParameter = rs.getString(1);
        }
        return idParameter;
    }

    public static void setImage(Carbide carbide, File file){
        String insertStmt = "Update material set Image = ? where ID_Material = " + carbide.getId() + ";";
        try {
            DBUtil.dbExecuteImage(insertStmt, file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Manufacturer> searchAllManufacturers() {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        String selectStmt = "Select * from manufacturer";
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
            while (resultSet.next()) {
                Manufacturer m = new Manufacturer();
                m.setId(resultSet.getInt(1));
                m.setName(resultSet.getString(2));
                m.setAddress(resultSet.getString(3));
                m.setTelephone(resultSet.getString(4));
                manufacturers.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public static void deleteManufacturer(int id) throws SQLException {
        String selectMaterials = "Select * from material where ID_manufacturer = " + id;
        ResultSet resultSet = DBUtil.dbExecuteQuery(selectMaterials);
        if (resultSet.wasNull()){
            String deleteStmtMaterial =
                    "DELETE FROM manufacturer\n" +
                            "WHERE ID_manufacturer = " + id + ";";
            DBUtil.dbExecuteUpdate(deleteStmtMaterial);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Невозможно удалить поставщика");
            alert.setContentText("Убедитесь, что данный поставщик не используется в базе!");
            alert.showAndWait();
        }

    }

    public static void addManufacturer(Manufacturer manufacturer) throws SQLException {
        String insert = "Insert into manufacturer (Name, Address, telephone) value ('" + manufacturer.getName() + "', '" +
                manufacturer.getAddress() + "', '" + manufacturer.getTelephone() + "');";
        DBUtil.dbExecuteUpdate(insert);
    }


    public static void updateManufacturer(Manufacturer manufacturer) {
        String updateParameters =
                "UPDATE manufacturer\n" +
                        "SET Name = '" + manufacturer.getName() + "', Address = '" + manufacturer.getAddress() + "', telephone = '" +
                        manufacturer.getTelephone() + "' WHERE ID_manufacturer=" + manufacturer.getId() + ";";
        try {
            DBUtil.dbExecuteUpdate(updateParameters);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
        }
    }
}
