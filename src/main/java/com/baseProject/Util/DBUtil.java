package com.baseProject.Util;

import com.sun.rowset.CachedRowSetImpl;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.*;

public class DBUtil {
    private static final String url = "jdbc:mysql://localhost:3306/karbides?useSSL=false";
    private static final String user = "root";
    private static final String password = "polosin";
    // JDBC переменные для создания и управлением соединения
    private static Connection conn;
    private static PreparedStatement pre;
    private String ip = "192.168.40.253";

    public static void dbConnect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException cnfe) {
            System.err.println("Error: " + cnfe.getMessage());
        }
        conn = DriverManager.getConnection(url, user, password);

    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void dbBackUp(String path) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "cp1251"))) {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump --default-character-set=cp1251 -uroot -ppolosin --databases karbides >" + path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                writer.write(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            getAlert("Произошла ошибка при выполнении операцию резервного копирования!", "Невозможно создать резевную копию: " + path);
            throw e;
        }
    }

    public static void dbRestore(String path) throws IOException, InterruptedException {
        String[] restoreCMD = new String[]{"C:/Program Files/MySQL/MySQL Server 5.7/bin/mysql.exe", "--user=root", "--password=polosin", "-e", "source " + path};

        Process process;
        try {
            process = Runtime.getRuntime().exec(restoreCMD);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while ((reader.readLine()) != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            process.waitFor();
        } catch (Exception e) {
            getAlert("Произошла ошибка при выполении sql скрипта!", "Невозможно выполнить sql скрипт: " + path);
            throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {

        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            dbConnect();
            //Создание выражений для выполнения запросов
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    //For Update/Insert/Delete operations
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {

        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

    public static void dbExecuteImage(String sqlStmt, File file) throws SQLException {
        try {
            dbConnect();
            pre = conn.prepareStatement(sqlStmt);
            FileInputStream fin = new FileInputStream(file);
            System.out.println(file);
            pre.setBinaryStream(1, fin, (int) file.length());
            pre.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pre != null) {
                pre.close();
            }
            dbDisconnect();
        }
    }

    private static void getAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

