package com.nlia.fqdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightMateInfo;

@Component
public class ImportFlightMateInfoByExcel {
    @Resource
    private ValidateFlightMateInfoFromExcel validateFlightMateInfoFromExcel;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> ImportFlightMateInfoByExcel2003(
            InputStream inputStream) throws IOException {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<FlightMateInfo> FlightMateInfoAllList = new ArrayList<>();
        List<FlightMateInfo> FlightMateInfoErrorList = new ArrayList<>();

        int totalData = 0;
        int columnsCount = 0;
        List<String> columns = new ArrayList<String>();
        boolean isColumnNameError = false;
        boolean isDataError = false;

        HSSFWorkbook workBook = null;
        try {
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out
                    .println("---------------import FlightMateInfo_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError=true;
            columnNameError= "无数据";
        }
        if(!isColumnNameError){
        // columnsCount获得字段名数
        columnsCount = sheet.getRow(0).getLastCellNum();
        totalData = sheet.getLastRowNum();

        // 　错误消息写入
        // 处理excel第一行的字段名
     // rowValue的格式{字段名:value;}
        Map<String, Integer> columnIndex = new HashMap<>();
        for (int i = 0; i < columnsCount; i++) {
            if (sheet.getRow(0).getCell(i) == null) {
                isColumnNameError = true;
                columnNameError+= "第"+ i +"列列名不能为空;";
            }
            sheet.getRow(0).getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
            String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();
            columns.add(columnName);
            columnIndex.put(columnName, i);
        }
        columnNameError+=ValidateFlightMateInfoFromExcel.columnsContansFlightMateInfoMastNeedColumnName(columns);
        if(columnNameError.equals("")){
        int i = 0, j = 0;
        validateFlightMateInfoFromExcel.getairlineAllList();//获取所有航空公司
        // 循环获取excel中的所有记录
        for (i = 1; i <= totalData; i++) {
            // rowValue的格式{字段名:value;}
            Map<String, String> rowValue = new HashMap<>();
            try {
                for (j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
                    // 判断单元格是否为空
                    if (sheet.getRow(i).getCell(j) != null) {
                        sheet.getRow(i).getCell(j)
                                .setCellType(HSSFCell.CELL_TYPE_STRING);
                        if (sheet.getRow(i).getCell(j).getStringCellValue() != null
                                && !sheet.getRow(i).getCell(j)
                                        .getStringCellValue().trim().equals("")) {
                            String cellValue = sheet.getRow(i).getCell(j)
                                    .getStringCellValue().trim();
                            rowValue.put(columns.get(j), cellValue);
                        } else {
                            rowValue.put(columns.get(j), ""); //$NON-NLS-1$
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excel data error,occur at the cell: i = "
                        + i + ", j = " + j);
            }
            // 对获取的数据进行校验，并转化成实体对象
            boolean isEmptyRow = true;
            FlightMateInfo flightMateInfo = validateFlightMateInfoFromExcel
                    .validateFlightMateInfoSingleData(rowValue,columnIndex, i);
            if (flightMateInfo.getId() != null) {
                isEmptyRow = false;
                FlightMateInfoAllList.add(flightMateInfo);
            }
            // 如果是非空行
//            if (!isEmptyRow) {
                if (isDataError == false) {
                    if (flightMateInfo.getVerifyDescription() != null
                            && !flightMateInfo.getVerifyDescription()
                                    .equals("")) {
                        // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                        isDataError = true;
                        FlightMateInfoErrorList.clear();
                        FlightMateInfoErrorList.add(flightMateInfo);
                    }
                } else {
                    if (flightMateInfo.getVerifyDescription() != null
                            && !flightMateInfo.getVerifyDescription()
                                    .equals("")) {
                        FlightMateInfoErrorList.add(flightMateInfo);
                        sheet.getRow(i).createCell(columnsCount);
                        sheet.getRow(i)
                                .getCell(columnsCount)
                                .setCellValue(
                                        flightMateInfo.getVerifyDescription());
                    }
                }
//            }
            isEmptyRow = true;

        }// for 结束
        }
        }
        List resultlist = new ArrayList();

        // 列名为空，返回空
        if (isColumnNameError || isDataError) {
            resultlist.add(FlightMateInfoAllList);
            resultlist.add(FlightMateInfoErrorList);
        } else {
            resultlist.add(FlightMateInfoAllList);
            resultlist.add(FlightMateInfoErrorList);
        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
        
    }

    /**
     * 读取excel2007文件流，将每条记录读取并转换成对象列表
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Object> ImportFlightMateInfoByExcel2007(
            InputStream inputStream) throws IOException {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<FlightMateInfo> FlightMateInfoAllList = new ArrayList<>();
        List<FlightMateInfo> FlightMateInfoErrorList = new ArrayList<>();

        int totalData = 0;
        int columnsCount = 0;
        List<String> columns = new ArrayList<String>();
        boolean isColumnNameError = false;
        boolean isDataError = false;

        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out
                    .println("---------------import FlightMateInfo_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        XSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError=true;
            columnNameError= "无数据";
        }
        if (!isColumnNameError) {
            // columnsCount获得字段名数
            columnsCount = sheet.getRow(0).getLastCellNum();
            totalData = sheet.getLastRowNum();

            // 　错误消息写入
            // 处理excel第一行的字段名
            // rowValue的格式{字段名:value;}
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < columnsCount; i++) {
                if (sheet.getRow(0).getCell(i) == null) {
                    isColumnNameError = true;
                    columnNameError += "第" + i + "列列名不能为空;";
                }
                sheet.getRow(0).getCell(i)
                        .setCellType(XSSFCell.CELL_TYPE_STRING);
                String columnName = sheet.getRow(0).getCell(i)
                        .getStringCellValue().trim();
                columns.add(columnName);
                columnIndex.put(columnName, i);
            }
            columnNameError += ValidateFlightMateInfoFromExcel
                    .columnsContansFlightMateInfoMastNeedColumnName(columns);
            if (columnNameError.equals("")) {
                int i = 0, j = 0;
                validateFlightMateInfoFromExcel.getairlineAllList();// 获取所有航空公司
                // 循环获取excel中的所有记录
                for (i = 1; i <= totalData; i++) {
                    // rowValue的格式{字段名:value;}
                    Map<String, String> rowValue = new HashMap<>();
                    try {
                        for (j = 0; j < sheet.getRow(i).getLastCellNum()
                                && j < columnsCount; j++) {
                            // 判断单元格是否为空
                            if (sheet.getRow(i).getCell(j) != null) {
                                sheet.getRow(i).getCell(j)
                                        .setCellType(XSSFCell.CELL_TYPE_STRING);
                                if (sheet.getRow(i).getCell(j)
                                        .getStringCellValue() != null
                                        && !sheet.getRow(i).getCell(j)
                                                .getStringCellValue().trim()
                                                .equals("")) {
                                    String cellValue = sheet.getRow(i)
                                            .getCell(j).getStringCellValue()
                                            .trim();
                                    rowValue.put(columns.get(j), cellValue);
                                } else {
                                    rowValue.put(columns.get(j), ""); //$NON-NLS-1$
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out
                                .println("Excel data error,occur at the cell: i = "
                                        + i + ", j = " + j);
                    }
                    // 对获取的数据进行校验，并转化成实体对象
                    boolean isEmptyRow = true;
                    FlightMateInfo flightMateInfo = validateFlightMateInfoFromExcel
                            .validateFlightMateInfoSingleData(rowValue,
                                    columnIndex, i);
                    if (flightMateInfo.getId() != null) {
                        isEmptyRow = false;
                        FlightMateInfoAllList.add(flightMateInfo);
                    }
                    // 如果是非空行
                    // if (!isEmptyRow) {
                    if (isDataError == false) {
                        if (flightMateInfo.getVerifyDescription() != null
                                && !flightMateInfo.getVerifyDescription()
                                        .equals("")) {
                            // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                            isDataError = true;
                            FlightMateInfoErrorList.clear();
                            FlightMateInfoErrorList.add(flightMateInfo);
                        }
                    } else {
                        if (flightMateInfo.getVerifyDescription() != null
                                && !flightMateInfo.getVerifyDescription()
                                        .equals("")) {
                            FlightMateInfoErrorList.add(flightMateInfo);
                            sheet.getRow(i).createCell(columnsCount);
                            sheet.getRow(i)
                                    .getCell(columnsCount)
                                    .setCellValue(
                                            flightMateInfo
                                                    .getVerifyDescription());
                        }
                    }
                    // }
                    isEmptyRow = true;

                }// for 结束
            }
        }
        List resultlist = new ArrayList();

        // 列名为空，返回空
        if (isColumnNameError || isDataError) {
            resultlist.add(FlightMateInfoAllList);
            resultlist.add(FlightMateInfoErrorList);
        } else {
            resultlist.add(FlightMateInfoAllList);
            resultlist.add(FlightMateInfoErrorList);
        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
    }
}
