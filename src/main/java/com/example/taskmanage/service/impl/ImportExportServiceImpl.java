package com.example.taskmanage.service.impl;

import com.example.taskmanage.service.ImportExportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ImportExportServiceImpl implements ImportExportService {


    @Override
    public byte[] exportObject() {

        Workbook workbook = createWorkbook();

        Sheet sheet = workbook.createSheet("Danh sach cong viec");

//        create a header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Date"};
        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        Object[][] data = {
                {1, "John Doe", 25},
                {2, "Jane Smith", 30},
                {3, "Mike Johnson", 28},
        };



        return new byte[0];
    }

    private Workbook createWorkbook() {

        // Create a new workbook
        return new XSSFWorkbook();
    }
}
