package dbController;

import javafx.collections.ObservableList;
import loadExcel.LoadExcelVolvoRow;
import loadExcel.LoadExcelVolvoRowRemains;
import messageWindows.ControllerMessage;

import java.sql.*;

public class DataBaseVolvo {

    private static Connection connection;
    private static Statement statement;
    private static String message;

    //+++++
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/opt_volvo?useUnicode=true&serverTimezone=UTC&useSSL=false",
                "root", "root");
        statement = connection.createStatement();
    }

//+++++
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//загрузка полного прайс-листа Volvo +++++
    public static void loadAllPriceVolvo(ObservableList<LoadExcelVolvoRow> loadExcelVolvoRows) throws SQLException, ClassNotFoundException {
        connect();
        statement.execute("TRUNCATE TABLE price_cs_parts;");
        try {
            for (int i = 0; i < loadExcelVolvoRows.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO price_cs_parts VALUES (?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, loadExcelVolvoRows.get(i).getProductGroup());
                preparedStatement.setString(2, loadExcelVolvoRows.get(i).getFunctionalGroup());
                preparedStatement.setString(3, loadExcelVolvoRows.get(i).getPartNumber());
                preparedStatement.setString(4, loadExcelVolvoRows.get(i).getNamePart());
                preparedStatement.setString(5, loadExcelVolvoRows.get(i).getSaleGroup());
                preparedStatement.setString(6, loadExcelVolvoRows.get(i).getRetailPrice());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
        ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM price_cs_parts");
        resultSet.next();
        message = "Загружено " + resultSet.getInt(1) + " строк!";
        ControllerMessage.messageWindowDone(message);
        disconnect();
    }
// загрузка остатков ЦС Volvo+++++
    public static void loadPartsCSVolvo(ObservableList<LoadExcelVolvoRowRemains> loadExcelVolvoRowsRemains) throws Exception {
        connect();
        statement.execute("TRUNCATE TABLE stock_cs;");
        try {
            for (int i = 0; i < loadExcelVolvoRowsRemains.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stock_cs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, loadExcelVolvoRowsRemains.get(i).getPartNumber());
                preparedStatement.setString(2, loadExcelVolvoRowsRemains.get(i).getNamePart());
                preparedStatement.setString(3, loadExcelVolvoRowsRemains.get(i).getProductGroup());
                preparedStatement.setString(4, loadExcelVolvoRowsRemains.get(i).getFunctionalGroup());
                preparedStatement.setString(5, loadExcelVolvoRowsRemains.get(i).getSaleGroup());
                preparedStatement.setString(6, loadExcelVolvoRowsRemains.get(i).getRetailPrice());
                preparedStatement.setString(7, loadExcelVolvoRowsRemains.get(i).getStockRemains());
                preparedStatement.setString(8, loadExcelVolvoRowsRemains.get(i).getBackOrderQty());
                preparedStatement.setString(9, loadExcelVolvoRowsRemains.get(i).getQtyInTransit());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
        ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM stock_cs;");
        resultSet.next();
        message = "Загружено " + resultSet.getString(1) + " строк!";
        ControllerMessage.messageWindowDone(message);
        disconnect();
    }

}
