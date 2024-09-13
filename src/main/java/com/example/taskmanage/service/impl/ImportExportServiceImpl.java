package com.example.taskmanage.service.impl;

import com.example.taskmanage.common.export.ExportConstant;
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
import java.lang.reflect.Field;

@Slf4j
@Service
public class ImportExportServiceImpl implements ImportExportService {


    @Override
    public byte[] exportObject(Object[] data) {

        Workbook workbook = createWorkbook();

        Sheet sheet = workbook.createSheet("Danh sach cong viec");

        createHeaderRow(sheet, ExportConstant.EXPORT_HEADER);

        int rowNum = 1;
        for (Object rowData : data) {
//            TODO toi uu code
            Class<?> objClass = rowData.getClass(); // Get the class of the object

            // Get all fields (including private fields)
            Field[] fields = objClass.getDeclaredFields();

            Row row = sheet.createRow(rowNum);
            int colNum = 0;

            for (Field field : fields) {
                field.setAccessible(true);
                Cell cell = row.createCell(colNum++);
                try {
                    if (field.getType().equals(String.class)) {

                        cell.setCellValue((String) field.get(rowData));
                    } else if (field.getType().equals(Long.class)) {
                        cell.setCellValue((Long) field.get(rowData));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
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

    private void createHeaderRow(Sheet sheet, String[] headers) {

        Row headerRow = sheet.createRow(0);

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
