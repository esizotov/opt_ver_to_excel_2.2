package loadExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loadExcelVolvoRow.LoadExcelVolvoRowRemains;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadExcelVolvoRemains {

    private static ObservableList<LoadExcelVolvoRowRemains> loadExcelVolvoRowsRemains = FXCollections.observableArrayList();

    public static ObservableList<LoadExcelVolvoRowRemains> openBookVolvoRemains(String fileRemains) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File(fileRemains));
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator iterator = sheet.rowIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Row row = (Row) iterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List data = new ArrayList();
            while (cellIterator.hasNext()) {
                HSSFCell cell = (HSSFCell) cellIterator.next();
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
                if (i > 0) {
                    if (data.size() == 9) {
                        loadExcelVolvoRowsRemains.add(new LoadExcelVolvoRowRemains(data));
                    }
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileInputStream.close();
        return loadExcelVolvoRowsRemains;
    }
}
