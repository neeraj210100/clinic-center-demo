package com.clinic.service;

import com.clinic.model.Lead;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public byte[] exportLeadsToExcel(List<Lead> leads) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Leads");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Email", "Phone Number", "Message", "Source", "Status", "Created At"};
            
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Create data rows
            int rowNum = 1;
            for (Lead lead : leads) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(lead.getId());
                row.createCell(1).setCellValue(lead.getName());
                row.createCell(2).setCellValue(lead.getEmail());
                row.createCell(3).setCellValue(lead.getPhoneNumber());
                row.createCell(4).setCellValue(lead.getMessage() != null ? lead.getMessage() : "");
                row.createCell(5).setCellValue(lead.getSource());
                row.createCell(6).setCellValue(lead.getStatus().toString());
                row.createCell(7).setCellValue(lead.getCreatedAt().format(DATE_FORMATTER));
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }
}
