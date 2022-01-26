package Entity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExporter {

        public static void main(String[] args) throws IOException {
                //headers for the table
                String[] columns = new String[]{
                        "Id", "Name", "Hourly Rate", "Part Time"
                };

                //actual data for the table in a 2d array
                Object[][] data = new Object[][]{
                        {1, "Việt", 40.0, false},
                        {2, "Hàn", 70.0, false},
                        {3, "Nhật", 60.0, true},};
                //create table with data
                JTable table = new JTable(data, columns);
                new ExcelExporter().exportTable(table, new File("test.xlsx"), "Danh sach nhan vien", 4);
        }

        public String getCurrentDateAsString() {
                SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy | hh:mm:ss");
                return dt.format(new Date());
        }

        public void exportTable(JTable table, File file, String headerTitle, int numOfColumns) throws IOException {
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("Data");
                createHeader(headerTitle, numOfColumns, wb, sheet);
                createTitle(wb, sheet, table);
                insertData(sheet, table);
                setColumnWidth(sheet);
                saveSheet(wb, file);
        }

        public void saveSheet(XSSFWorkbook wb, File file) {
                try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        wb.write(outputStream);
                        wb.close();
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void setColumnWidth(XSSFSheet sheet) {
                //set độ dài cho các cột
                sheet.setColumnWidth(0, 7000);
                sheet.setColumnWidth(1, 7000);
                sheet.setColumnWidth(2, 7000);
                sheet.setColumnWidth(3, 7000);
                sheet.setColumnWidth(4, 7000);
                sheet.setColumnWidth(5, 7000);
                sheet.setColumnWidth(6, 7000);
        }

        public void insertData(XSSFSheet sheet, JTable table) {
                int currentRow = 4;
                for (int i = 0; i < table.getRowCount(); i++) {
                        Row row = sheet.createRow(currentRow++);
                        for (int j = 0; j < table.getColumnCount(); j++) {
                                Cell cell = row.createCell(j);
                                Object value = table.getValueAt(i, j);

                                String content = "";

                                if (value != null) {
                                        content = value.toString();
                                }
                                cell.setCellValue(content);
                        }
                }
        }

        public void createTitle(XSSFWorkbook wb, XSSFSheet sheet, JTable table) {
                XSSFRow rowCol3 = sheet.createRow(3);

                //Format cho tiêu đề của bảng - hàng chứa tên cột
                XSSFCellStyle style1 = wb.createCellStyle();
                XSSFFont font1 = wb.createFont();
                font1.setFontName("Time New Roman");
                font1.setFontHeightInPoints((short) 15);
                font1.setBold(true);
                style1.setFont(font1);
                style1.setAlignment(HorizontalAlignment.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                        Cell cell = rowCol3.createCell(i);
                        cell.setCellValue(table.getColumnName(i));
                        rowCol3.getCell(i).setCellStyle(style1);
                }
        }

        public void createHeader(String headerValue, int numOfColumns, XSSFWorkbook wb, XSSFSheet sheet) {
                //Format cho header
                XSSFRow rowCol = sheet.createRow(0);
                XSSFCellStyle style = wb.createCellStyle();
                XSSFFont font = wb.createFont();
                font.setFontName("Time New Roman");
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                font.setItalic(true);
                style.setFont(font);
                style.setWrapText(true);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);

                Cell cell0 = rowCol.createCell(0);
                cell0.setCellValue(headerValue + "\n Ngày lập báo cáo: " + getCurrentDateAsString());
                cell0.setCellStyle(style);

                //Merge từ ô a1 đến a5, hàng 1 đến hàng 3
                sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, numOfColumns - 1));

        }

}
