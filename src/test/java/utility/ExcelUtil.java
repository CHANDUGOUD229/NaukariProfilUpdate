package utility;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil
{
    /**
     * It will read the entire Data from the Excel
     * @param filePath
     * @param sheetIndex
     * @return
     */
    public static List<List<String>> readExcel(String filePath, int sheetIndex) {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    rowData.add(getCellValueAsString(cell));
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "";
        }
    }

//    public static void main(String[] args) {
//        String filePath = "./src/test/resources/testDataFiles/ExcelData.xlsx";
//        int sheetIndex = 1; // Index of the sheet to read (0-based)
//        List<List<String>> excelData = readExcel(filePath, sheetIndex);
//
//        for (List<String> row : excelData) {
//            for (String cell : row) {
//                System.out.print(cell + "\t");
//            }
//            System.out.println();
//        }
//    }

    public static String getCellValue(String filePath, int sheetIndex, int rowIndex, int columnIndex) {
        String cellValue = "";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    cellValue = getCellValueAsString(cell);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellValue;
    }


//    public static void main(String[] args) {
//        String filePath = "./src/test/resources/testDataFiles/ExcelData.xlsx";
//        int sheetIndex = 0; // Index of the sheet to read (0-based)
//        int rowIndex = 0; // Row index (0-based)
//        int columnIndex = 0; // Column index (0-based)
//        String cellValue = getCellValue(filePath, sheetIndex, rowIndex, columnIndex);
//        System.out.println("Cell Value: " + cellValue);
//    }

    public static void writeToExcel(List<List<Object>> data, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        int rowNum = 0;
        for (List<Object> rowData : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } // Add more cases for other data types as needed
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<List<Object>> data = List.of(
                List.of("Name", "Age", "City","chandu"),
                List.of("John", 25, "New York","chirra"),
                List.of("Alice", 30, "London","aadhya"),
                List.of("Bob", 35, "Paris","indian")
        );

        writeToExcel(data, "./src/test/resources/testDataFiles/ExcelData.xlsx");
    }

}



