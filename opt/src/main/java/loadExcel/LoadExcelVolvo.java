package loadExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loadExcelVolvoRow.LoadExcelVolvoRow;
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

    private static ObservableList<LoadExcelVolvoRow> loadExcelVolvoRows = FXCollections.observableArrayList();

    public static ObservableList<LoadExcelVolvoRow> openBookVolvo(String fileAllPrice) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File(fileAllPrice));
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator iterator = sheet.rowIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Row row = (Row) iterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List data = new ArrayList();
            while (cellIterator.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) {
                    case _NONE:
                        data.add("NONE");
                        break;
                    case BOOLEAN:
                        data.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case BLANK:
                        break;
                    case FORMULA:
                        data.add(cell.getCellFormula());
                        break;
                    case NUMERIC:
                        data.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case STRING:
                        data.add(cell.getStringCellValue());
                        break;
                    case ERROR:
                        break;
                }
            }
            try {
                if(i >= 4) {
                    if (data.size() == 6) {
                        loadExcelVolvoRows.add(new LoadExcelVolvoRow(data));
                    }
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileInputStream.close();
        return loadExcelVolvoRows;
    }
}

