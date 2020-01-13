import dbController.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static loadExcel.LoadExcelVolvo.openBookVolvo;
import static loadExcel.LoadExcelVolvoRemains.openBookVolvoRemains;
import static loadExcel.LoadExcelVolvoSaleGroup.openBookVolvoSaleGroup;


public class Controller {

    @FXML
    Button search;

    @FXML
    Button fileChooser_1;

    @FXML
    Button fileChooser_2;

    @FXML
    Button buttonLoadAllPrice;

    @FXML
    Button buttonLoadStock;

    @FXML
    TextField textFieldID;

    @FXML
    TextField textFieldChooserAllPriceID;

    @FXML
    TextField textFieldChooserStockID;

    @FXML
    Button buttonPriceStockID;

    @FXML
    TextField textFieldSalePriceStockID;

    @FXML
    TextField textFieldSalePriceZSOID;

    @FXML
    Button buttonPriceZSOID;

    @FXML
    TextField textFieldSaleStock12GroupID;

    @FXML
    TextField textFieldSaleStock8GroupID;

    @FXML
    TextField textFieldSaleStock4GroupID;

    @FXML
    TextField textFieldSaleStock2GroupID;

    @FXML
    Button buttonPriceStockGroupID;

    @FXML
    TextField textFieldSaleZSO12GroupID;

    @FXML
    TextField textFieldSaleZSO8GroupID;

    @FXML
    TextField textFieldSaleZSO4GroupID;

    @FXML
    TextField textFieldSaleZSO2GroupID;

    @FXML
    Button buttonPriceZSOGroupID;

    @FXML
    private TableView<DataBaseOutput> tableViewID;

    @FXML
    private TableColumn<DataBaseOutput, String> columnPartNumberID;

    @FXML
    private TableColumn<DataBaseOutput, String> columnNamePartID;

    @FXML
    private TableColumn<DataBaseOutput, Double> columnPriceID;

    @FXML
    private TableColumn<DataBaseOutput, Integer> columnRemainsID;

    Window primaryStage;

    @FXML
    Button fileChooserVolvoAll;

    @FXML
    TextField textFieldChooserAllPriceVolvoID;

    @FXML
    Button buttonLoadAllPriceVolvoID;

    @FXML
    Button fileChooserVolvoStock;

    @FXML
    TextField textFieldChooserVolvoStockID;

    @FXML
    Button buttonLoadPartsCSVolvoID;

    @FXML
    TextField textFieldChooserVolvoSaleGroupID;

    @FXML
    Button fileChooserVolvoSaleGroupID;

    @FXML
    Button buttonLoadSaleGroupID;

    // заполнение tableView +++++
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        columnPartNumberID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("partNumber"));
        columnNamePartID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("namePart"));
        columnPriceID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Double>("price"));
        columnRemainsID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Integer>("remains"));
        tableViewID.setItems(DataBaseAudi.initTableView());
    }

    // выбор файлов загрузки общего прайса и остатков ЦС +++++
    @FXML
    public void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open resource file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"),
                new FileChooser.ExtensionFilter("Text files", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(fileChooser_1.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooser_2.isFocused()) {
            if (selectedFile != null)
                textFieldChooserStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoAll.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceVolvoID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoStock.isFocused()) {
            if (selectedFile != null)
                textFieldChooserVolvoStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoSaleGroupID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserVolvoSaleGroupID.appendText(selectedFile.getAbsolutePath());
        }
    }

    // загрузка общего прайс-листа Audi+++++
    @FXML
    public void buttonLoadAllParts() throws Exception {
        DataBaseAudi.loadAllPrice(textFieldChooserAllPriceID.getText());
    }

    // загрузка остатков ЦС Audi+++++
    @FXML
    public void buttonLoadPartsCS() throws Exception {
        DataBaseAudi.loadPartsCS(textFieldChooserStockID.getText());
    }

    // выгрузка прайса Сток ЦС (0-й) Audi +наценка/-скидка +++++
    @FXML
    public void priceStockCS() throws SQLException, ClassNotFoundException {
        DataBaseAudi.formPriceStockCS(textFieldSalePriceStockID.getText());
    }

    // выгрузка прайса Срочный ЦС (0-й) Audi +наценка/-скидка +++++
    public void priceZSOCS() throws SQLException, ClassNotFoundException {
        DataBaseAudi.formPriceZSOCS(textFieldSalePriceZSOID.getText());
    }

    // выгрузка прайса Сток ЦС по группам Audi +наценка/-скидка +++++
    public void priceStockGroup() throws SQLException, ClassNotFoundException {
        DataBaseAudi.formPriceStockGroups(textFieldSaleStock12GroupID.getText(), textFieldSaleStock8GroupID.getText(),
                textFieldSaleStock4GroupID.getText(), textFieldSaleStock2GroupID.getText());
    }

    // выгрузка прайса Срочный ЦС по группам Audi +наценка/-скидка +++++
    public void priceZSOGroup() throws SQLException, ClassNotFoundException {
        DataBaseAudi.formPriceZSOGroups(textFieldSaleZSO12GroupID.getText(), textFieldSaleZSO8GroupID.getText(),
                textFieldSaleZSO4GroupID.getText(), textFieldSaleZSO2GroupID.getText());
    }

    public void buttonSearch() {
    }

    // загрузка общего прайс-листа Volvo+++++
    @FXML
    public void buttonLoadAllPriceVolvo() {
        try {
            DataBaseVolvo.loadAllPriceVolvo(openBookVolvo(textFieldChooserAllPriceVolvoID.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // загрузка остатков ЦС +++++
    @FXML
    public void buttonLoadPartsCSVolvo() {
        try {
            DataBaseVolvo.loadPartsCSVolvo(openBookVolvoRemains(textFieldChooserVolvoStockID.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // загрузка таблицы скидок
    @FXML
    public void buttonLoadSaleGroup() {
        try {
            DataBaseVolvo.loadSalesDiscountMatrix(openBookVolvoSaleGroup(textFieldChooserVolvoSaleGroupID.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
