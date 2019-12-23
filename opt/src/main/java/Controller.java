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
import java.sql.SQLException;


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

    // заполнение tableView +++++
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        columnPartNumberID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("partNumber"));
        columnNamePartID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("namePart"));
        columnPriceID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Double>("price"));
        columnRemainsID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Integer>("remains"));
        tableViewID.setItems(DataBase.initTableView());
    }

    // выбор файлов загрузки общего прайса и остатков ЦС +++++
    @FXML
    public void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open resource file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*,*")
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
    }

    // загрузка общего прайс-листа +++++
    @FXML
    public void buttonLoadAllParts() throws Exception {
        DataBase.loadAllPrice(textFieldChooserAllPriceID.getText());
    }

    // загрузка остатков ЦС +++++
    @FXML
    public void buttonLoadPartsCS() throws Exception {
        DataBase.loadPartsCS(textFieldChooserStockID.getText());

    }

    // выгрузка прайса Сток ЦС (0-й) +наценка/-скидка +++++
    @FXML
    public void priceStockCS() throws SQLException, ClassNotFoundException {
        DataBase.formPriceStockCS(textFieldSalePriceStockID.getText());
    }

    // выгрузка прайса Срочный ЦС (0-й) +наценка/-скидка +++++
    public void priceZSOCS() throws SQLException, ClassNotFoundException {
        DataBase.formPriceZSOCS(textFieldSalePriceZSOID.getText());
    }

    // выгрузка прайса Сток ЦС по группам +наценка/-скидка +++++
    public void priceStockGroup() throws SQLException, ClassNotFoundException {
        DataBase.formPriceStockGroups(textFieldSaleStock12GroupID.getText(), textFieldSaleStock8GroupID.getText(),
                textFieldSaleStock4GroupID.getText(), textFieldSaleStock2GroupID.getText());
    }

    // выгрузка прайса Срочный ЦС по группам +наценка/-скидка +++++
    public void priceZSOGroup() throws SQLException, ClassNotFoundException {
        DataBase.formPriceZSOGroups(textFieldSaleZSO12GroupID.getText(), textFieldSaleZSO8GroupID.getText(),
                textFieldSaleZSO4GroupID.getText(), textFieldSaleZSO2GroupID.getText());
    }

    public void buttonSearch() {

    }
}
