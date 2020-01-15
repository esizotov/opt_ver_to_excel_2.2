package dbController;

import exportToExcel.ExportToExcel;
import javafx.collections.ObservableList;
import loadExcel.LoadExcelVolvoRow;
import loadExcel.LoadExcelVolvoRowRemains;
import loadExcel.LoadExcelVolvoRowSaleGroup;
import messageWindows.ControllerMessage;

import java.io.IOException;
import java.sql.*;

import static dbController.DataBaseAudi.dateAndTime;

public class DataBaseVolvo {

    private static Connection connection;
    private static Statement statement;
    private static String message;
    private static String nameFile;

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

// загрузка групп скидок Volvo+++++
    public static void loadSalesDiscountMatrix(ObservableList<LoadExcelVolvoRowSaleGroup> loadExcelVolvoRowSaleGroup) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE sales_discount_matrix;");
            try {
                for (int i = 0; i < loadExcelVolvoRowSaleGroup.size(); i++) {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales_discount_matrix VALUES (?, ?, ?, ?);");
                    preparedStatement.setString(1, loadExcelVolvoRowSaleGroup.get(i).getNameProductGroup());
                    preparedStatement.setString(2, loadExcelVolvoRowSaleGroup.get(i).getSaleGroup());
                    preparedStatement.setString(3, loadExcelVolvoRowSaleGroup.get(i).getStockDiscount());
                    preparedStatement.setString(4, loadExcelVolvoRowSaleGroup.get(i).getDailyDiscount());
                    preparedStatement.addBatch();
                    preparedStatement.executeBatch();
                }
            } catch (SQLException e) {
                ControllerMessage.messageWindowDone(String.valueOf(e));
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM sales_discount_matrix;");
            resultSet.next();
            message = "Загружено " + resultSet.getString(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }

    }

// выгрузка прайса Сток ЦС (0-й) +наценка/-скидка
    public static void formPriceStockCSVolvo(String discount) throws SQLException, ClassNotFoundException {
        connect();
        try {
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT INTO price_cs " +
                    "SELECT stock_cs.part_number, stock_cs.name_part, 'Volvo', " +
                    "round(stock_cs.retail_price * (1 - sales_discount_matrix.stock_discount / 100) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), stock_cs.stock_remains " +
                    "FROM price_cs_parts, stock_cs, sales_discount_matrix " +
                    "WHERE price_cs_parts.part_number = stock_cs.part_number " +
                    "AND stock_cs.sale_group = sales_discount_matrix.sale_group;");
        } catch (SQLException e) {
            try {
                ControllerMessage.messageWindowDone(String.valueOf(e));
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/STOCK_VOLVO_" + discount + "_" + dateAndTime() + ".xlsx";
        try {
            selectQuery(nameFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
    }

// выгрузка прайса Сток ЦС (0-й) +наценка/-скидка
    public static void formPriceZSOCSVolvo(String discount) throws SQLException, ClassNotFoundException {
        connect();
        try {
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT INTO price_cs " +
                    "SELECT stock_cs.part_number, stock_cs.name_part, 'Volvo', " +
                    "round(stock_cs.retail_price * (1 - sales_discount_matrix.daily_discount / 100) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), stock_cs.stock_remains " +
                    "FROM price_cs_parts, stock_cs, sales_discount_matrix " +
                    "WHERE price_cs_parts.part_number = stock_cs.part_number " +
                    "AND stock_cs.sale_group = sales_discount_matrix.sale_group;");
        } catch (SQLException e) {
            try {
                ControllerMessage.messageWindowDone(String.valueOf(e));
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/ZSO_VOLVO_" + discount + "_" + dateAndTime() + ".xlsx";
        try {
            selectQuery(nameFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
    }

// выгрузка в Excel+++++
    public static void selectQuery(String nameFile) throws IOException {
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM price_cs;");
            ExportToExcel.exportToExcel(resultSet, nameFile);
            ResultSet rs = statement.executeQuery("SELECT count(*) FROM price_cs;");
            rs.next();
            message = "В прайсе " + rs.getString(1) + " строк!";
            try {
                ControllerMessage.messageWindowDone(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        disconnect();
    }

}
