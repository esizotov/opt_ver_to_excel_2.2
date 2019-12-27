package loadExcel;

import com.sun.javafx.logging.JFRInputEvent;
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

import static java.lang.Double.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.*;

public class LoadExcelVolvo {

    private static ObservableList<LoadExcelVolvoRow> loadExcelVolvoRows = FXCollections.observableArrayList();

    private void openBook() throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File(
                "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Volvo\\Parts_Price_List_01-11-2019.xlsx"));
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
        for (int j = 0; j < loadExcelVolvoRows.size(); j++) {
            System.out.println(loadExcelVolvoRows.get(j).getProductGroup() + " " + loadExcelVolvoRows.get(j).getFunctionalGroup() + " " +
                    loadExcelVolvoRows.get(j).getPartNumber() + " " + loadExcelVolvoRows.get(j).getNamePart() + " " +
                    loadExcelVolvoRows.get(j).getSaleGroup() + " " + loadExcelVolvoRows.get(j).
                    getRetailPrice());
        }
        System.out.println(loadExcelVolvoRows.size());
        System.out.println(i - 1);
        fileInputStream.close();
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        LoadExcelVolvo loadExcelVolvo = new LoadExcelVolvo();
        loadExcelVolvo.openBook();
    }
}

