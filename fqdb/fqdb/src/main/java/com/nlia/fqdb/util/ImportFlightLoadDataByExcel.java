package com.nlia.fqdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.nlia.fqdb.service.impl.FlightLoadDataManager;

@Component
public class ImportFlightLoadDataByExcel {
    // add by march 20140826 start 导入配载信息
    @Resource
    private ValidateFlightLoadDataFromExcel validateFlightLoadDataFromExcel;
    @Resource
    private FlightLoadDataManager flightLoadDataManager;
    /**
     * 读取excel2003文件流，将每条记录读取并转换成对象列表
     * 
     * @param inputStream
     * @param flightLoadDataeErrorList
     *            存放校验不合格的航班数据
     * @return 含错误数据的HSSFWorkbook 如果列名错误就返回全部数据
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> ImportFlightLoadDataByExcel2003(
            InputStream inputStream) throws IOException {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        Date maxDate = null,minDate=null;
        int dataIndex=0;//EXCLE中data字段的位置
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
            excelResult.put("resultMessage", "文件格式不正确！");
            List resultlist = new ArrayList();
            resultlist.add(flightLoadDataAllList);
            resultlist.add(flightLoadDataErrorList);
            excelResult.put("result", resultlist);
            return excelResult;
        }

        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError = "无数据";
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
                columns.add(columnName.toLowerCase());//转小写
                columnIndex.put(columnName.toLowerCase(), i);//转小写
                if(columnName.equals("date")){
                    dataIndex=i;
                }
            }
            columnNameError += validateFlightLoadDataFromExcel.columnsContansFlightLoadDataExcleNeedColumnName(columns);
            if (columnNameError.equals("")) {
//                getFlightLoadDateByDate(sheet,dataIndex);
                int i = 0, j = 0;
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
                    FlightLoadData flightLoadData = validateFlightLoadDataFromExcel
                            .validateFlightLoadDataSingleData(rowValue,
                                    columnIndex, i,flightLoadDataAllList);
                    if (flightLoadData.getJhrq() != null
                     && flightLoadData.getCyr() != null
                     && flightLoadData.getHbh() != null
                     && flightLoadData.getJh() != null
                     && flightLoadData.getIo() != null
                     && flightLoadData.getHd() != null) {
                        isEmptyRow = false;
                        flightLoadDataAllList.add(flightLoadData);
                       if( flightLoadData.getVerifyDescription() == null
                        || flightLoadData.getVerifyDescription().isEmpty()) {
                            Date temp = flightLoadData.getJhrq();
                            if (maxDate==null) {
                                maxDate = temp;
                                minDate = temp;
                            }
                            if (maxDate.before(temp)) {
                                maxDate = temp;
                            }
                            if (minDate.after(temp)) {
                                minDate = temp;
                            }
                        }
                    }
                    // 如果是非空行
                    // if (!isEmptyRow) {
                    if (isDataError == false) {
                        if (flightLoadData.getVerifyDescription() != null
                                && !flightLoadData.getVerifyDescription()
                                        .equals("")) {
                            // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                            isDataError = true;
                            flightLoadDataErrorList.clear();
                            flightLoadDataErrorList.add(flightLoadData);
                        }
                    } else {
                        if (flightLoadData.getVerifyDescription() != null
                                && !flightLoadData.getVerifyDescription()
                                        .equals("")) {
                            flightLoadDataErrorList.add(flightLoadData);
                            sheet.getRow(i).createCell(columnsCount);
                            sheet.getRow(i)
                                    .getCell(columnsCount)
                                    .setCellValue(
                                            flightLoadData
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
//        if (isColumnNameError || isDataError) {
//            resultlist.add(flightLoadDataAllList);
//            resultlist.add(flightLoadDataErrorList);
//        } else {
            resultlist.add(flightLoadDataAllList);
            resultlist.add(flightLoadDataErrorList);
//        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        excelResult.put("maxDate",maxDate);
        excelResult.put("minDate",minDate);
        excelResult.put("columnsCount",columnsCount);
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
    public Map<String, Object> ImportFlightLoadDataByExcel2007(
            InputStream inputStream) throws IOException {
        String columnNameError = "";
        Map<String, Object> excelResult = new HashMap<>();
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        Date maxDate = null,minDate=null;
        int dataIndex=0;//EXCLE中data字段的位置
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
            excelResult.put("resultMessage", "文件格式不正确！");
            List resultlist = new ArrayList();
            resultlist.add(flightLoadDataAllList);
            resultlist.add(flightLoadDataErrorList);
            excelResult.put("result", resultlist);
            return excelResult;
        }

        XSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            columnNameError = "无数据";
        }
        if (!isColumnNameError) {
            // columnsCount获得字段名数
            columnsCount = sheet.getRow(0).getLastCellNum();
            totalData = sheet.getLastRowNum();

            // 　错误消息写入
            // 处理excel第一行的字段名
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < columnsCount; i++) {
                if (sheet.getRow(0).getCell(i) == null) {
                    isColumnNameError = true;
                    columnNameError += "第" + i + "列列名不能为空;";
                    return excelResult;
                }

                sheet.getRow(0).getCell(i)
                        .setCellType(XSSFCell.CELL_TYPE_STRING);
                String columnName = sheet.getRow(0).getCell(i)
                        .getStringCellValue().trim();
                columns.add(columnName.toLowerCase());//转小写
                columnIndex.put(columnName.toLowerCase(), i);//转小写
                if(columnName.equals("date")){
                    dataIndex=i;
                }
            }
            columnNameError += ValidateFlightLoadDataFromExcel
                    .columnsContansFlightLoadDataExcleNeedColumnName(columns);
            if (columnNameError.equals("")) {
//                getFlightLoadDateByDate(sheet,dataIndex);
                int i = 0, j = 0;
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
                        System.out.println("Excel data error,occur at the cell: i = "
                                        + i + ", j = " + j);
                    }
                    // 对获取的数据进行校验，并转化成实体对象
                    boolean isEmptyRow = true;
                    FlightLoadData flightLoadData = validateFlightLoadDataFromExcel
                            .validateFlightLoadDataSingleData(rowValue,
                                    columnIndex, i,flightLoadDataAllList);
                    if (flightLoadData.getJhrq() != null
                            && flightLoadData.getCyr() != null
                            && flightLoadData.getHbh() != null
                            && flightLoadData.getJh() != null
                            && flightLoadData.getIo() != null
                            && flightLoadData.getHd() != null) {
                        isEmptyRow = false;
                        flightLoadDataAllList.add(flightLoadData);
                        if( flightLoadData.getVerifyDescription() == null
                                || flightLoadData.getVerifyDescription().isEmpty()) {
                                    Date temp = flightLoadData.getJhrq();
                                    if (maxDate == null) {
                                        maxDate = temp;
                                        minDate = temp;
                                    }
                                    if (maxDate.before(temp)) {
                                        maxDate = temp;
                                    }
                                    if (minDate.after(temp)) {
                                        minDate = temp;
                                    }
                                }
                    }
                    // 如果是非空行
                    // if (!isEmptyRow) {
                    if (isDataError == false) {
                        if (flightLoadData.getVerifyDescription() != null
                                && !flightLoadData.getVerifyDescription()
                                        .equals("")) {
                            // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                            isDataError = true;
                            flightLoadDataErrorList.clear();
                            flightLoadDataErrorList.add(flightLoadData);
                        }
                    } else {
                        if (flightLoadData.getVerifyDescription() != null
                                && !flightLoadData.getVerifyDescription()
                                        .equals("")) {
                            flightLoadDataErrorList.add(flightLoadData);
                            sheet.getRow(i).createCell(columnsCount);
                            sheet.getRow(i)
                                    .getCell(columnsCount)
                                    .setCellValue(
                                            flightLoadData
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
            resultlist.add(flightLoadDataAllList);
            resultlist.add(flightLoadDataErrorList);
        } else {
            resultlist.add(flightLoadDataAllList);
            resultlist.add(flightLoadDataErrorList);
        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        excelResult.put("maxDate",maxDate);
        excelResult.put("minDate",minDate);
        excelResult.put("columnsCount",columnsCount);
        return excelResult;
    }
    // add by march 20140826 end
    
    //获取EXCLE中时间段内的所有FlightLoadData
    private void getFlightLoadDateByDate(XSSFSheet sheet,int dataIndex){
        int totalData = sheet.getLastRowNum();
        int columnsCount = sheet.getRow(0).getLastCellNum();
        int i = 0, j = 0;
        String maxDate,minDate;
        // 循环获取excel中的所有记录
        maxDate=minDate=sheet.getRow(1).getCell(dataIndex).getStringCellValue().trim();
        for (i = 1; i <= totalData; i++) {
            try {
                    if (sheet.getRow(i).getCell(dataIndex) != null) {
                        sheet.getRow(i).getCell(dataIndex).setCellType(XSSFCell.CELL_TYPE_STRING);
                        if (sheet.getRow(i).getCell(dataIndex).getStringCellValue() != null&& !sheet.getRow(i).getCell(dataIndex).getStringCellValue().trim().equals("")) {
                            String cellValue = sheet.getRow(i).getCell(dataIndex).getStringCellValue().trim();
                            if(maxDate.compareTo(cellValue)<0){
                                maxDate=cellValue;
                            }
                            if(minDate.compareTo(cellValue)>0){
                                minDate=cellValue;
                            }
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excel data error,occur at the cell: i = "
                                + i + ", j = " + j);
            }
        }
        
        Date start,end;
        start=DateUtils.getFirstMinuteOfOneday(DateUtils.String2Date(minDate)) ;
        end =DateUtils.getLastMinuteOfOneday(DateUtils.String2Date(maxDate));
        Map<String, Object> filters = new HashMap<>();
        filters.clear();
        filters.put("jhrq_greaterThanOrEqualTo",start);
        filters.put("jhrq_lessThanOrEqualTo",end);
        List<FlightLoadData> list2=flightLoadDataManager.findBy(filters);
        validateFlightLoadDataFromExcel.setFlightLoadDataList(list2);
        
    }
    
    private void getFlightLoadDateByDate(HSSFSheet sheet, int dataIndex) {
        int totalData = sheet.getLastRowNum();
        int columnsCount = sheet.getRow(0).getLastCellNum();
        int i = 0, j = 0;
        String maxDate,minDate;
        // 循环获取excel中的所有记录
        maxDate=minDate=sheet.getRow(1).getCell(dataIndex).getStringCellValue().trim();
        for (i = 1; i <= totalData; i++) {
            try {
                    if (sheet.getRow(i).getCell(dataIndex) != null) {
                        sheet.getRow(i).getCell(dataIndex).setCellType(XSSFCell.CELL_TYPE_STRING);
                        if (sheet.getRow(i).getCell(dataIndex).getStringCellValue() != null&& !sheet.getRow(i).getCell(dataIndex).getStringCellValue().trim().equals("")) {
                            String cellValue = sheet.getRow(i).getCell(dataIndex).getStringCellValue().trim();
                            if(maxDate.compareTo(cellValue)<0){
                                maxDate=cellValue;
                            }
                            if(minDate.compareTo(cellValue)>0){
                                minDate=cellValue;
                            }
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excel data error,occur at the cell: i = "
                                + i + ", j = " + j);
            }
        }
        
        Date start,end;
        start=DateUtils.getZeroOfOneday(DateUtils.String2Date(minDate,"yyyyMMdd")) ;
        end =DateUtils.getLastMinuteOfOneday(DateUtils.String2Date(maxDate,"yyyyMMdd"));
        Map<String, Object> filters = new HashMap<>();
        filters.clear();
        filters.put("jhrq_greaterThanOrEqualTo",start);
        filters.put("jhrq_lessThanOrEqualTo",end);
        List<FlightLoadData> list2=flightLoadDataManager.findBy(filters);
        validateFlightLoadDataFromExcel.setFlightLoadDataList(list2);
    }
}
