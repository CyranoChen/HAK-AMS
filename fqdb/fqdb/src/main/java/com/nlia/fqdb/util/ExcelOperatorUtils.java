package com.nlia.fqdb.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperatorUtils {

    /**
     * 根据校验结果，设置excel表单单元格的背景色为红色
     * @param fileName excel表单路径
     * @param errorMessage 校验结果，报错的行列号
     * @throws IOException
     */
    public static String changeExcel2003BackgroundColor(String fileName,Map<Integer, List<Integer>> errorMessage) throws IOException{
        HSSFWorkbook workBook = null;
        java.io.InputStream inputStream = new FileInputStream(fileName);
        try {
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = workBook.getSheetAt(0);
        int dataCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();
        
        //设置单元格样式（颜色设置为红色）
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setFillForegroundColor((short) 10);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        //遍历单元格，判断是否是报错的单元格，如果是，则设置新的样式
        for (int i = 1; i < dataCount + 1; i++) {
            if(errorMessage.containsKey(i)){
                for (int j = 0; j <= columnCount; j++) {
                    if(errorMessage.get(i).contains(j)){
                        if(sheet.getRow(i).getCell(j) == null)
                            sheet.getRow(i).createCell(j);
                    
                        sheet.getRow(i).getCell(j).setCellStyle(cellStyle);
                    }                                               
                }           
            }
        }
        //导出修改了颜色的excel
        String excelName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length()-4) + "_verify.xls";
        fileName = fileName.substring(0, fileName.lastIndexOf(File.separator)) +File.separator+ excelName;
        FileOutputStream fOut = new FileOutputStream(fileName);
        workBook.write(fOut);
        //关闭文件流
        fOut.close();
        inputStream.close();
        return fileName;
    }
    
    
    /**
     * 根据校验结果，设置excel表单单元格的背景色为红色
     * @param fileName excel表单路径
     * @param errorMessage 校验结果，报错的行列号
     * @throws IOException
     */
    public static String changeExcel2007BackgroundColor(String fileName,Map<Integer, List<Integer>> errorMessage) throws IOException{
        XSSFWorkbook workBook = null;
        java.io.InputStream inputStream = new FileInputStream(fileName);
        try {
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workBook.getSheetAt(0);
        int dataCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();
        
        //设置单元格样式（颜色设置为红色）
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setFillForegroundColor((short) 10);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
        //遍历单元格，判断是否是报错的单元格，如果是，则设置新的样式
        for (int i = 1; i < dataCount + 1; i++) {
            if(errorMessage.containsKey(i)){
                for (int j = 0; j <= columnCount; j++) {
                    if(errorMessage.get(i).contains(j)){
                        if(sheet.getRow(i).getCell(j) == null){
                            sheet.getRow(i).createCell(j);
                        }
                        sheet.getRow(i).getCell(j).setCellStyle(cellStyle);
                    }                                               
                }           
            }
        }
        //导出修改了颜色的excel
        String excelName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length()-5) + "_verify.xlsx";
        fileName = fileName.substring(0, fileName.lastIndexOf(File.separator)) +File.separator+ excelName;
        FileOutputStream fOut = new FileOutputStream(fileName);
        workBook.write(fOut);
        //关闭文件流
        fOut.close();
        inputStream.close();
        return fileName;
    }
}
