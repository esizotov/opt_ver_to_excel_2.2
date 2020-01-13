package loadExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadExcelVolvoSaleGroup {
    private static ObservableList<LoadExcelVolvoRowSaleGroup> loadExcelVolvoRowsSaleGroup = FXCollections.observableArrayList();

    public static ObservableList<LoadExcelVolvoRowSaleGroup> openBookVolvoSaleGroup(String fileAllPrice) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File(fileAllPrice));
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
            try {if (data.size() > 4) {
                 data.remove(0);
                 loadExcelVolvoRowsSaleGroup.add(new LoadExcelVolvoRowSaleGroup(data));
                 } else if (data.size() == 4) {
                 loadExcelVolvoRowsSaleGroup.add(new LoadExcelVolvoRowSaleGroup(data));
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileInputStream.close();
        return loadExcelVolvoRowsSaleGroup;
    }

}
