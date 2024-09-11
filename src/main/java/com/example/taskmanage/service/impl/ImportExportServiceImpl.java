package com.example.taskmanage.service.impl;

import com.example.taskmanage.service.ImportExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ImportExportServiceImpl implements ImportExportService {


    @Override
    public byte[] exportObject() {

        Workbook workbook = createWorkbook();

        Sheet sheet = workbook.createSheet("Danh sach cong viec");

        createHeaderRow(sheet);

        Object[][] data = {
                {1, "John Doe", 25},
                {2, "Jane Smith", 30},
                {3, "Mike Johnson", 28},
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
            rowNum++;
        }

        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {

            workbook.write(fileOut);

//            workbook.
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // Close the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return new byte[0];
    }

    private void createHeaderRow(Sheet sheet) {

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Date"};
        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private Workbook createWorkbook() {

        // Create a new workbook
        return new XSSFWorkbook();
    }
}
