package com.ust.proyect.users_api.Service;

import com.ust.proyect.users_api.Model.Dto.UserDto;
import com.ust.proyect.users_api.Model.Entity.User;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<User> listUsers;

    public ExcelExporter(List<User> listUsers){
        this.listUsers =listUsers;
        workbook=new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet =workbook.createSheet("Users");
        Row row = sheet.createRow(0);

        CellStyle style= workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row,0,"id",style);
        createCell(row,1,"userName",style);
        createCell(row,2,"phone",style);
        createCell(row,3,"rol",style);
        createCell(row,4,"password",style);

    }
    public void createCell(Row row,int columnCount,Object value,CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getUserName(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getRol(), style);
            createCell(row, columnCount++, user.getPassword(), style);

        }
    }
    public void guardar ()throws IOException{
        try (FileOutputStream fileOut = new FileOutputStream("mi_archivo.xlsx")) {
            writeHeaderLine();
            writeDataLines();
            workbook.write(fileOut);
        }
    }
    public ByteArrayInputStream export()throws IOException
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        writeHeaderLine();
        writeDataLines();

        workbook.write(stream);
        workbook.close();
        stream.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
