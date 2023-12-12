package com.example.filehandling.excelUtility;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelWriter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void generateExcelFile(File file, List<CricketerExcel> cricketerExcelList) throws IOException, InvalidFormatException {

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("cricketer_data");
        writeHeaderLine();
        writeBodyData(cricketerExcelList);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        workbook.close();
    }

    private void writeHeaderLine(){

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
//        font.setFontHeight((short) 16);
        font.setItalic(true);

        style.setFont(font);

        createCell(row,0,"ID",style);
        createCell(row,1,"NAME",style);
        createCell(row,2,"DESCRIPTION",style);

    }

    private void writeBodyData(List<CricketerExcel> cricketerExcelList){
        int rowCount = 1;
        Font font = workbook.createFont();
//        font.setFontHeight((short)10);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);

        for(CricketerExcel cricketerExcel : cricketerExcelList){
            int columnCount = 0;
            Row row = sheet.createRow(rowCount++);


            createCell(row,columnCount++, cricketerExcel.getId(),cellStyle);
            createCell(row,columnCount++, cricketerExcel.getName(),cellStyle);
            createCell(row,columnCount++, cricketerExcel.getDescription(),cellStyle);

        }
    }

    private void createCell(Row row, int column, Object value, CellStyle style){

        sheet.autoSizeColumn(column);
        Cell cell = row.createCell(column);

        if(value instanceof Integer){
           cell.setCellValue((int) value);
        }else if(value instanceof String){
            cell.setCellValue((String) value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if(value instanceof Long){
            cell.setCellValue((Long) value);
        }else if(value instanceof Short){
            cell.setCellValue((Short) value);
        }else if(value instanceof Date){
            cell.setCellValue((Date) value);
        }else if(value instanceof Float){
            cell.setCellValue((Float) value);
        }else if(value instanceof Double){
            cell.setCellValue((Double) value);
        }
        cell.setCellStyle(style);

    }
}
