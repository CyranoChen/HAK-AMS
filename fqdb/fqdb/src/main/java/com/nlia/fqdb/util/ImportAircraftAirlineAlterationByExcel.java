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

import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.service.impl.AircraftAirlineAlterationManager;

@Component
public class ImportAircraftAirlineAlterationByExcel {

    @Resource
    private ValidateAircraftAirlineAlterationFromExcel validateAircraftAirlineAlterationFromExcel;
    /**
     * 读取excel2003文件流，将每条记录读取并转换成对象列表
     * 
     * @param inputStream
     * @param flightBaseErrorList 存放校验不合格的航班数据
     * @return 含错误数据的HSSFWorkbook
     *      如果列名错误就返回全部数据
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> ImportAircraftAirlineAlterationDataByExcel2003(
            InputStream inputStream) throws IOException {
        String columnNameError = "";
        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();

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
                    .println("---------------import flightBase_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError += "获取列名失败\n";
        }
        if (!isColumnNameError) {
            // columnsCount获得字段名数
            columnsCount = sheet.getRow(0).getLastCellNum();
            if (columnsCount != 23) {// 列数不对
                isDataError = true;
                columnNameError += "列数应为22列，实际为" + columnsCount + "\n";
            }
            if (!isDataError) {
                totalData = sheet.getLastRowNum();

                // 　错误消息写入
                // sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
                // 处理excel第一行的字段名，i从1开始跳过第0列
                for (int i = 1; i < columnsCount; i++) {
                    if (sheet.getRow(0).getCell(i) == null) {
                        isColumnNameError = true;
                        columnNameError += "获取列名失败\n";
                        break;
                    }

                    sheet.getRow(0).getCell(i)
                            .setCellType(HSSFCell.CELL_TYPE_STRING);
                    String columnName = sheet.getRow(0).getCell(i)
                            .getStringCellValue().trim();
                    isColumnNameError = !ValidateAircraftAirlineAlterationFromExcel.verifyColumnName(i, columnName);
                    if (isColumnNameError) {// 列名不一致
                        columnNameError += "列名[" + columnName + "]存在错误\n";
                    }
                    columns.add(columnName);
                }
                if (columnNameError.equals("")) {
                    int i = 0, j = 0;
                    validateAircraftAirlineAlterationFromExcel.getairlineAllList();// 获取所有航空公司
//                    validateAircraftAirlineAlterationFromExcel.getaircraftTypeAllinList();
                    // 循环获取excel中的所有记录
                    for (i = 1; i <= totalData; i++) {
                        // rowValue的格式{字段名:value;}
                        Map<String, String> rowValue = new HashMap<>();

                        try {
                            for (j = 1; j <= sheet.getRow(i).getLastCellNum()
                                    && j < columnsCount; j++) {
                                // 判断单元格是否为空
                                if (sheet.getRow(i).getCell(j) != null) {
                                    sheet.getRow(i)
                                            .getCell(j)
                                            .setCellType(
                                                    HSSFCell.CELL_TYPE_STRING);
                                    if (sheet.getRow(i).getCell(j)
                                            .getStringCellValue() != null
                                            && !sheet.getRow(i).getCell(j)
                                                    .getStringCellValue()
                                                    .trim().equals("")) {
                                        String cellValue = sheet.getRow(i)
                                                .getCell(j)
                                                .getStringCellValue().trim();
                                        rowValue.put(columns.get(j - 1),
                                                cellValue);
                                    } else {
                                        rowValue.put(columns.get(j - 1), ""); //$NON-NLS-1$
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
                        AircraftAirlineAlteration aircraftAirlineAlteration = validateAircraftAirlineAlterationFromExcel
                                .validateSingleData(rowValue, i);

                        // 如果该excel数据行不是空行
                        if (aircraftAirlineAlteration.getAircraftRegistration() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftRegistration().equals("")) {
                            if (aircraftAirlineAlteration
                                    .getAircraftTypeICAOCode() != null
                                    && !aircraftAirlineAlteration
                                            .getAircraftTypeICAOCode().equals(
                                                    "")
                                    && aircraftAirlineAlteration
                                            .getAircraftTypeIATACode() != null
                                    && !aircraftAirlineAlteration
                                            .getAircraftTypeIATACode().equals(
                                                    "")
                                    && aircraftAirlineAlteration
                                            .getCurrentAirline() != null
                                    && !aircraftAirlineAlteration
                                            .getCurrentAirline().equals("")
                                    && aircraftAirlineAlteration
                                            .getCurrentSubairline() != null
                                    && !aircraftAirlineAlteration
                                            .getCurrentSubairline().equals("")
                                    // &&
                                    // aircraftAirlineAlteration.getAircraftTakeoffWeight()
                                    // != null &&
                                    // !aircraftAirlineAlteration.getAircraftTakeoffWeight().equals("")
                                    && aircraftAirlineAlteration.getStartDate() != null
                                    && aircraftAirlineAlteration.getEndDate() != null) {
                                // isEmptyRow = false;
                                aircraftAirlineAlterationAllList
                                        .add(aircraftAirlineAlteration);
                            }
                            isEmptyRow = false;
                        }
                        // 如果是非空行
                        if (!isEmptyRow) {
                            if (isDataError == false) {
                                if (aircraftAirlineAlteration
                                        .getVerifyDescription() != null
                                        && !aircraftAirlineAlteration
                                                .getVerifyDescription().equals(
                                                        "")) {
                                    // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                                    isDataError = true;
                                    aircraftAirlineAlterationErrorList.clear();
                                    aircraftAirlineAlterationErrorList
                                            .add(aircraftAirlineAlteration);
                                }
                            } else {
                                if (aircraftAirlineAlteration
                                        .getVerifyDescription() != null
                                        && !aircraftAirlineAlteration
                                                .getVerifyDescription().equals(
                                                        "")) {
                                    aircraftAirlineAlterationErrorList
                                            .add(aircraftAirlineAlteration);
                                    sheet.getRow(i).createCell(columnsCount);
                                    sheet.getRow(i)
                                            .getCell(columnsCount)
                                            .setCellValue(
                                                    aircraftAirlineAlteration
                                                            .getVerifyDescription());
                                }
                            }
                        }
                        isEmptyRow = true;
                    }// for 结束
                }
            }
        }
        List resultlist = new ArrayList();
        Map<String, Object> excelResult = new HashMap<>();
//        // 列名为空，返回空
//        if (isColumnNameError || isDataError) {
//            resultlist.add(aircraftAirlineAlterationAllList);
//            resultlist.add(aircraftAirlineAlterationErrorList);
//        } else {
            resultlist.add(aircraftAirlineAlterationAllList);
            resultlist.add(aircraftAirlineAlterationErrorList);
//        }
        excelResult.put("totalCount", totalData);    
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
    public Map<String, Object> ImportAircraftAirlineAlterationDataByExcel2007(InputStream inputStream) throws IOException {
        String columnNameError = "";
        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();

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
                    .println("---------------import flightBase_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        XSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError += "获取列名失败\n";
        }
        if (!isColumnNameError) {
            // columnsCount获得字段名数
            columnsCount = sheet.getRow(0).getLastCellNum();
            if (columnsCount != 23) {// 列数不对
                isDataError = true;
                columnNameError += "列数应为22列，实际为" + columnsCount + "\n";
            }
            if (!isDataError) {
                totalData = sheet.getLastRowNum();

                // 　错误消息写入
                // sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
                // 处理excel第一行的字段名，i从1开始跳过第0列
                for (int i = 1; i < columnsCount; i++) {
                    if (sheet.getRow(0).getCell(i) == null) {
                        isColumnNameError = true;
                        columnNameError += "获取列名失败\n";
                        break;
                    }

                    sheet.getRow(0).getCell(i)
                            .setCellType(HSSFCell.CELL_TYPE_STRING);
                    String columnName = sheet.getRow(0).getCell(i)
                            .getStringCellValue().trim();
                    isColumnNameError = !ValidateAircraftAirlineAlterationFromExcel.verifyColumnName(i, columnName);
                    if (isColumnNameError) {// 列名不一致
                        columnNameError += "列名[" + columnName + "]存在错误\n";
                    }
                    columns.add(columnName);
                }
                if (columnNameError.equals("")) {
                    int i = 0, j = 0;
                    validateAircraftAirlineAlterationFromExcel.getairlineAllList();// 获取所有航空公司
//                    validateAircraftAirlineAlterationFromExcel.getaircraftTypeAllinList();
                    // 循环获取excel中的所有记录
                    for (i = 1; i <= totalData; i++) {
                        // rowValue的格式{字段名:value;}
                        Map<String, String> rowValue = new HashMap<>();

                        try {
                            for (j = 1; j <= sheet.getRow(i).getLastCellNum()
                                    && j < columnsCount; j++) {
                                // 判断单元格是否为空
                                if (sheet.getRow(i).getCell(j) != null) {
                                    sheet.getRow(i)
                                            .getCell(j)
                                            .setCellType(
                                                    XSSFCell.CELL_TYPE_STRING);
                                    if (sheet.getRow(i).getCell(j)
                                            .getStringCellValue() != null
                                            && !sheet.getRow(i).getCell(j)
                                                    .getStringCellValue()
                                                    .trim().equals("")) {
                                        String cellValue = sheet.getRow(i)
                                                .getCell(j)
                                                .getStringCellValue().trim();
                                        rowValue.put(columns.get(j - 1),
                                                cellValue);
                                    } else {
                                        rowValue.put(columns.get(j - 1), ""); //$NON-NLS-1$
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
                        AircraftAirlineAlteration aircraftAirlineAlteration = validateAircraftAirlineAlterationFromExcel
                                .validateSingleData(rowValue, i);

                        // 如果该excel数据行不是空行
                        if (aircraftAirlineAlteration.getAircraftRegistration() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftRegistration().equals("")) {
                            if (aircraftAirlineAlteration
                                    .getAircraftTypeICAOCode() != null
                                    && !aircraftAirlineAlteration
                                            .getAircraftTypeICAOCode().equals(
                                                    "")
                                    && aircraftAirlineAlteration
                                            .getAircraftTypeIATACode() != null
                                    && !aircraftAirlineAlteration
                                            .getAircraftTypeIATACode().equals(
                                                    "")
                                    && aircraftAirlineAlteration
                                            .getCurrentAirline() != null
                                    && !aircraftAirlineAlteration
                                            .getCurrentAirline().equals("")
                                    && aircraftAirlineAlteration
                                            .getCurrentSubairline() != null
                                    && !aircraftAirlineAlteration
                                            .getCurrentSubairline().equals("")
                                    // &&
                                    // aircraftAirlineAlteration.getAircraftTakeoffWeight()
                                    // != null &&
                                    // !aircraftAirlineAlteration.getAircraftTakeoffWeight().equals("")
                                    && aircraftAirlineAlteration.getStartDate() != null
                                    && aircraftAirlineAlteration.getEndDate() != null) {
                                // isEmptyRow = false;
                                aircraftAirlineAlterationAllList
                                        .add(aircraftAirlineAlteration);
                            }
                            isEmptyRow = false;
                        }
                        // 如果是非空行
                        if (!isEmptyRow) {
                            if (isDataError == false) {
                                if (aircraftAirlineAlteration
                                        .getVerifyDescription() != null
                                        && !aircraftAirlineAlteration
                                                .getVerifyDescription().equals(
                                                        "")) {
                                    // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                                    isDataError = true;
                                    aircraftAirlineAlterationErrorList.clear();
                                    aircraftAirlineAlterationErrorList
                                            .add(aircraftAirlineAlteration);
                                }
                            } else {
                                if (aircraftAirlineAlteration
                                        .getVerifyDescription() != null
                                        && !aircraftAirlineAlteration
                                                .getVerifyDescription().equals(
                                                        "")) {
                                    aircraftAirlineAlterationErrorList
                                            .add(aircraftAirlineAlteration);
                                    sheet.getRow(i).createCell(columnsCount);
                                    sheet.getRow(i)
                                            .getCell(columnsCount)
                                            .setCellValue(
                                                    aircraftAirlineAlteration
                                                            .getVerifyDescription());
                                }
                            }
                        }
                        isEmptyRow = true;
                    }// for 结束
                }
            }
        }
        List resultlist = new ArrayList();
        Map<String, Object> excelResult = new HashMap<>();
//        // 列名为空，返回空
//        if (isColumnNameError || isDataError) {
//            resultlist.add(aircraftAirlineAlterationAllList);
//            resultlist.add(aircraftAirlineAlterationErrorList);
//        } else {
            resultlist.add(aircraftAirlineAlterationAllList);
            resultlist.add(aircraftAirlineAlterationErrorList);
//        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
    }

    public Map<String, Object> ImportAircraftAirlineAlterationDataFromCAACSCExcel2007(
            InputStream inputStream) {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
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
                    .println("---------------import flightLoadData_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

         XSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError = "无数据;";
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
            columnNameError += validateAircraftAirlineAlterationFromExcel
                    .columnsContansCAACSCExcleNeedColumnName(columns);
            if (columnNameError.equals("")) {
                int i = 0, j = 0;
                validateAircraftAirlineAlterationFromExcel.getairlineAllList();// 获取所有航空公司
                // 循环获取excel中的所有记录
                for (i = 1; i <= totalData; i++) {
                    // rowValue的格式{字段名:value;}
                    Map<String, String> rowValue = new HashMap<>();

                    try {
                    	if(sheet.getRow(i)==null){
                    		continue;
                    	}
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
                    boolean isEmptyRow = true;
                    AircraftAirlineAlteration aircraftAirlineAlteration = validateAircraftAirlineAlterationFromExcel
                            .validate_CAACSC_SingleData(rowValue,columnIndex, i,aircraftAirlineAlterationAllList);

                    // 如果该excel数据行不是空行
                    if (aircraftAirlineAlteration.getAircraftRegistration() != null
                            && !aircraftAirlineAlteration
                                    .getAircraftRegistration().equals("")) {
                    	
                        if (aircraftAirlineAlteration.getAircraftTypeICAOCode() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftTypeICAOCode().equals("")
                                && aircraftAirlineAlteration
                                        .getAircraftTypeIATACode() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftTypeIATACode().equals("")
                                && aircraftAirlineAlteration
                                        .getCurrentAirline() != null
                                && !aircraftAirlineAlteration
                                        .getCurrentAirline().equals("")
                                && aircraftAirlineAlteration
                                        .getCurrentSubairline() != null
                                && !aircraftAirlineAlteration
                                        .getCurrentSubairline().equals("")
                                // &&
                                // aircraftAirlineAlteration.getAircraftTakeoffWeight()
                                // != null &&
                                // !aircraftAirlineAlteration.getAircraftTakeoffWeight().equals("")
                                && aircraftAirlineAlteration.getStartDate() != null
                                && aircraftAirlineAlteration.getEndDate() != null) {
                            // isEmptyRow = false;
                            aircraftAirlineAlterationAllList
                                    .add(aircraftAirlineAlteration);
                        }
                        
                        isEmptyRow = false;
                    }
                    // 如果是非空行
                    if (!isEmptyRow) {
                        if (isDataError == false) {
                            if (aircraftAirlineAlteration
                                    .getVerifyDescription() != null
                                    && !aircraftAirlineAlteration
                                            .getVerifyDescription().equals("")) {
                            	//System.out.println("导入机号数据异常提示："+aircraftAirlineAlteration.getVerifyDescription());
                                // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                                isDataError = true;
                                aircraftAirlineAlterationErrorList.clear();
                                aircraftAirlineAlterationErrorList
                                        .add(aircraftAirlineAlteration);
                            }
                        } else {
                            if (aircraftAirlineAlteration
                                    .getVerifyDescription() != null
                                    && !aircraftAirlineAlteration
                                            .getVerifyDescription().equals("")) {
                            	//System.out.println("导入机号数据异常提示："+aircraftAirlineAlteration.getVerifyDescription());
                                aircraftAirlineAlterationErrorList
                                        .add(aircraftAirlineAlteration);
                                sheet.getRow(i).createCell(columnsCount);
                                sheet.getRow(i)
                                        .getCell(columnsCount)
                                        .setCellValue(
                                                aircraftAirlineAlteration
                                                        .getVerifyDescription());
                            }
                        }
                    }
                    isEmptyRow = true;
                }// for 结束
            }
        }
        List resultlist = new ArrayList();
        resultlist.add(aircraftAirlineAlterationAllList);
        resultlist.add(aircraftAirlineAlterationErrorList);
        excelResult.put("totalCount", totalData);        
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
    }

    public Map<String, Object> ImportAircraftAirlineAlterationDataFromCAACSCExcel2003(
            InputStream inputStream) {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
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
                    .println("---------------import flightLoadData_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError = "无数据;";
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
            columnNameError += validateAircraftAirlineAlterationFromExcel
                    .columnsContansCAACSCExcleNeedColumnName(columns);
            if (columnNameError.equals("")) {
                int i = 0, j = 0;
                validateAircraftAirlineAlterationFromExcel.getairlineAllList();// 获取所有航空公司
                // 循环获取excel中的所有记录
                for (i = 1; i <= totalData; i++) {
                    // rowValue的格式{字段名:value;}
                    Map<String, String> rowValue = new HashMap<>();

                    try {
                    	if(sheet.getRow(i)==null){
                    		continue;
                    	}
                    	
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
                    boolean isEmptyRow = true;
                    AircraftAirlineAlteration aircraftAirlineAlteration = validateAircraftAirlineAlterationFromExcel
                            .validate_CAACSC_SingleData(rowValue,columnIndex, i, aircraftAirlineAlterationAllList);
                    
                    // 如果该excel数据行不是空行
                    if (aircraftAirlineAlteration.getAircraftRegistration() != null
                            && !aircraftAirlineAlteration
                                    .getAircraftRegistration().equals("")) {
                        if (aircraftAirlineAlteration.getAircraftTypeICAOCode() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftTypeICAOCode().equals("")
                                && aircraftAirlineAlteration
                                        .getAircraftTypeIATACode() != null
                                && !aircraftAirlineAlteration
                                        .getAircraftTypeIATACode().equals("")
                                && aircraftAirlineAlteration
                                        .getCurrentAirline() != null
                                && !aircraftAirlineAlteration
                                        .getCurrentAirline().equals("")
                                && aircraftAirlineAlteration
                                        .getCurrentSubairline() != null
                                && !aircraftAirlineAlteration
                                        .getCurrentSubairline().equals("")
                                // &&
                                // aircraftAirlineAlteration.getAircraftTakeoffWeight()
                                // != null &&
                                // !aircraftAirlineAlteration.getAircraftTakeoffWeight().equals("")
                                && aircraftAirlineAlteration.getStartDate() != null
                                && aircraftAirlineAlteration.getEndDate() != null) {
                            // isEmptyRow = false;
                            aircraftAirlineAlterationAllList
                                    .add(aircraftAirlineAlteration);
                        }
                        isEmptyRow = false;
                    }
                    // 如果是非空行
                    if (!isEmptyRow) {
                        if (isDataError == false) {
                            if (aircraftAirlineAlteration
                                    .getVerifyDescription() != null
                                    && !aircraftAirlineAlteration
                                            .getVerifyDescription().equals("")) {
                            	//System.out.println("导入机号数据异常提示："+aircraftAirlineAlteration.getVerifyDescription());
                                // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                                isDataError = true;
                                aircraftAirlineAlterationErrorList.clear();
                                aircraftAirlineAlterationErrorList
                                        .add(aircraftAirlineAlteration);
                            }
                            	
                            
                        } else {
                            if (aircraftAirlineAlteration
                                    .getVerifyDescription() != null
                                    && !aircraftAirlineAlteration
                                            .getVerifyDescription().equals("")) {
                            	//System.out.println("导入机号数据异常提示："+aircraftAirlineAlteration.getVerifyDescription());
                                aircraftAirlineAlterationErrorList
                                        .add(aircraftAirlineAlteration);
                                sheet.getRow(i).createCell(columnsCount);
                                sheet.getRow(i)
                                        .getCell(columnsCount)
                                        .setCellValue(
                                                aircraftAirlineAlteration
                                                        .getVerifyDescription());
                            }
                        }
                    }
                    isEmptyRow = true;
                }// for 结束
            }
        }
        List resultlist = new ArrayList();
        resultlist.add(aircraftAirlineAlterationAllList);
        resultlist.add(aircraftAirlineAlterationErrorList);
        excelResult.put("totalCount", totalData);
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
    }
}


