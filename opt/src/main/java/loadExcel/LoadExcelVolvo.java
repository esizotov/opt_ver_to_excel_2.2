package loadExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadExcelVolvo {

    private static ObservableList<Object> loadExcelVolvoRows = FXCollections.observableArrayList();

    private void openBook() throws IOException, InvalidFormatException {

        FileInputStream fileInputStream = new FileInputStream(new File(
                "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Volvo\\Parts_Price_List_01-11-2019.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator iterator = sheet.rowIterator();

        while (iterator.hasNext()) {
            Row row = (Row) iterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List data = new ArrayList();
            while (cellIterator.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) {
                    case _NONE:
                        break;
                    case BOOLEAN:
                        data.add(cell.getBooleanCellValue());
                        break;
                    case BLANK:
                        break;
                    case FORMULA:
                        data.add(cell.getCellFormula());
                        break;
                    case NUMERIC:
                        data.add(cell.getNumericCellValue());
                        break;
                    case STRING:
                        data.add(cell.getStringCellValue());
                        break;
                    case ERROR:
                        break;
                }
            }
            loadExcelVolvoRows.add(data);
        }
        fileInputStream.close();
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        LoadExcelVolvo loadExcelVolvo = new LoadExcelVolvo();
        loadExcelVolvo.openBook();
    }
}

