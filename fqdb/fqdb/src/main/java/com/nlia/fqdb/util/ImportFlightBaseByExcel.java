package com.nlia.fqdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.nlia.fqdb.entity.FlightBase;

@Component
public class ImportFlightBaseByExcel {

   
    
    private static final boolean FlightBase = false;

    /**
     * 读取excel2003文件流，将每条记录读取并转换成对象列表
     * 
     * @param inputStream
     * @param flightBaseErrorList 存放校验不合格的航班数据
     * @return 含错误数据的HSSFWorkbook
     * 		如果列名错误就返回全部数据
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List ImportFlightBaseByExcel2003(InputStream inputStream) {

	List<FlightBase> flightBaseAllList = new ArrayList<>();
	List<FlightBase> flightBaseErrorList = new ArrayList<>();
	
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
	    System.out.println("---------------import flightBase_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
	}

	HSSFSheet sheet = workBook.getSheetAt(0);

	if (sheet.getRow(0) == null) {
	    isColumnNameError = true;
	}
	//columnsCount获得字段名数
	columnsCount = sheet.getRow(0).getLastCellNum();
	totalData = sheet.getLastRowNum();
	
	//　错误消息写入
	//sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
	//处理excel第一行的字段名
	for (int i = 0; i < columnsCount; i++) {
	    if (sheet.getRow(0).getCell(i) == null) {
		isColumnNameError = true;
		break;
	    }

	    sheet.getRow(0).getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
	    String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();

	    // 判断对应列名是否相同
	    isColumnNameError = !ValidateFlightBaseFromExcel.verifyColumnName(i, columnName);
	    /*if(!isColumnNameError){
		return null;
	    }*/
	    columns.add(columnName);
	}
	int i=0,j=0;
	// 循环获取excel中的所有记录
	for ( i = 1; i <= totalData; i++) {
	    //rowValue的格式{字段名:value;}
	    Map<String, String> rowValue = new HashMap<>();
	    
	    try{
	    for ( j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
		// 判断单元格是否为空 
		if(sheet.getRow(i).getCell(j)!=null){
		    sheet.getRow(i).getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);
		    if (sheet.getRow(i).getCell(j).getStringCellValue() != null && !sheet.getRow(i).getCell(j).getStringCellValue().trim().equals("")) {
			String cellValue = sheet.getRow(i).getCell(j).getStringCellValue().trim();
			rowValue.put(columns.get(j), cellValue);
		    } else {
			rowValue.put(columns.get(j), ""); //$NON-NLS-1$
		    }
		}
		
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println("Excel data error,occur at the cell: i = "+i+", j = "+j);
	}
	    // 对获取的数据进行校验，并转化成实体对象
	    boolean isEmptyRow =true;
	    FlightBase flightBase=ValidateFlightBaseFromExcel.validateSingleData(rowValue, i);
	    //如果该excel数据行不是空行
	    if(flightBase.getFlightScheduledDate()!=null || !flightBase.getFlightDirection().equals("") || !flightBase.getFlightIdentity().equals(""))
	    {
		isEmptyRow=false;
		flightBaseAllList.add(flightBase);
	    }
	    //如果是非空行
	     if(!isEmptyRow){
		 if (isDataError == false) 
		        {
				if (flightBase.getVerifyDescription() != null && !flightBase.getVerifyDescription().equals("")) 
				{
					// 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
					isDataError = true;
					flightBaseErrorList.clear();
					flightBaseErrorList.add(flightBase);
				}
			} 
		      else {
				if (flightBase.getVerifyDescription() != null && !flightBase.getVerifyDescription().equals("")) 
				{
				    flightBaseErrorList.add(flightBase);
				    sheet.getRow(i).createCell(columnsCount);
				    sheet.getRow(i).getCell(columnsCount).setCellValue(flightBase.getVerifyDescription());
				}
			    } 
	     }
	     isEmptyRow= true;
	}//for 结束
	
	List resultlist = new ArrayList();
	//列名为空，返回空
	if(isColumnNameError || isDataError){
	    resultlist.add(flightBaseAllList);
	    resultlist.add(flightBaseAllList);
	}else{
	    resultlist.add(flightBaseAllList);
	    resultlist.add(flightBaseErrorList);
	}
	
	
	return resultlist;
    }

    /**
     * 读取excel2007文件流，将每条记录读取并转换成对象列表
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<FlightBase> ImportFlightBaseByExcel2007(InputStream inputStream) throws IOException {
	List<FlightBase> flightBaseAllList = new ArrayList<>();
	List<FlightBase> flightBaseErrorList = new ArrayList<>();
	
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
	    System.out.println("---------------import flightBase_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
	}

	XSSFSheet sheet = workBook.getSheetAt(0);

	if (sheet.getRow(0) == null) {
	    isColumnNameError = true;
	}
	//columnsCount获得字段名数
	columnsCount = sheet.getRow(0).getLastCellNum();
	totalData = sheet.getLastRowNum();
	
	//　错误消息写入
	//sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
	//处理excel第一行的字段名
	for (int i = 0; i < columnsCount; i++) {
	    if (sheet.getRow(0).getCell(i) == null) {
		isColumnNameError = true;
		break;
	    }

	    sheet.getRow(0).getCell(i).setCellType(XSSFCell.CELL_TYPE_STRING);
	    String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();

	    // 判断对应列名是否相同
	    //isColumnNameError = !ValidateChargeUnitFromExcel.verifyColumnName(i, columnName);
	    /*if(!isColumnNameError){
		return null;
	    }*/
	    columns.add(columnName);
	}

	// 循环获取excel中的所有记录
	for (int i = 1; i < totalData + 1; i++) {
	    //rowValue的格式{字段名:value;}
	    Map<String, String> rowValue = new HashMap<>();

	    for (int j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
		// 判断单元格是否为空 
		if(sheet.getRow(i).getCell(j)!=null){
		    sheet.getRow(i).getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING); 
		    if (sheet.getRow(i).getCell(j).getStringCellValue() != null && !sheet.getRow(i).getCell(j).getStringCellValue().trim().equals("")) {
			String cellValue = sheet.getRow(i).getCell(j).getStringCellValue().trim();
			rowValue.put(columns.get(j), cellValue);
		} else {
		    rowValue.put(columns.get(j), ""); //$NON-NLS-1$
		    }
		}
	    }
	
	    // 对获取的数据进行校验，并转化成实体对象
	    boolean isEmptyRow =true;
	    FlightBase flightBase=ValidateFlightBaseFromExcel.validateSingleData(rowValue, i);
	    //如果该excel数据行不是空行
	    if(flightBase.getFlightScheduledDate()!=null || !flightBase.getFlightDirection().equals("") || !flightBase.getFlightIdentity().equals(""))
	    {
		isEmptyRow=false;
		flightBaseAllList.add(flightBase);
	    }
	    //如果是非空行
	     if(!isEmptyRow){
		 if (isDataError == false) 
		        {
				if (flightBase.getVerifyDescription() != null && !flightBase.getVerifyDescription().equals("")) 
				{
					// 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
					isDataError = true;
					flightBaseErrorList.clear();
					flightBaseErrorList.add(flightBase);
				}
			} 
		      else {
				if (flightBase.getVerifyDescription() != null && !flightBase.getVerifyDescription().equals("")) 
				{
				    flightBaseErrorList.add(flightBase);
				    sheet.getRow(i).createCell(columnsCount);
				    sheet.getRow(i).getCell(columnsCount).setCellValue(flightBase.getVerifyDescription());
				}
			    } 
	     }
	     isEmptyRow= true;
	}//for 结束
	
	List resultlist = new ArrayList();
	//列名为空，返回空
	if(isColumnNameError || isDataError){
	    resultlist.add(flightBaseAllList);
	    resultlist.add(flightBaseAllList);
	}else{
	    resultlist.add(flightBaseAllList);
	    resultlist.add(flightBaseErrorList);
	}
	
	return resultlist;
    }
    
    
    
    //add by march 20140826 start 导入配载信息
    
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
    public Map<String, Object> ImportFlightBaseLoadDataByExcel2003(InputStream inputStream) throws IOException {
        String columnNameError = "";
        List<FlightBase> flightBaseAllList = new ArrayList<>();
        List<FlightBase> flightBaseErrorList = new ArrayList<>();

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
            System.out.println("---------------import flightBase_excel to datebase goes wrong:IoException--------------------"); //$NON-NLS-1$
        }

        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet.getRow(0) == null) {
            isColumnNameError = true;
            return null;
        }
        // columnsCount获得字段名数
        columnsCount = sheet.getRow(0).getLastCellNum();
        if (columnsCount != 19) {// 列数不对
            isDataError = true;
            return null;
        }
        totalData = sheet.getLastRowNum();

        // 　错误消息写入
        // sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
        // 处理excel第一行的字段名
        for (int i = 0; i < columnsCount; i++) {
            if (sheet.getRow(0).getCell(i) == null) {
                isColumnNameError = true;
                break;
            }

            sheet.getRow(0).getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
            String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();
            isColumnNameError = !ValidateFlightBaseFromExcel.verifyLoadColumnName(i, columnName);
            if (isColumnNameError) {// 列名不一致
                columnNameError +=  "列名" + columnName + "存在错误";
                return null;
            }
            columns.add(columnName);
        }
        int i = 0, j = 0;
        // 循环获取excel中的所有记录
        for (i = 1; i <= totalData; i++) {
            // rowValue的格式{字段名:value;}
            Map<Integer, String> rowValue = new HashMap<>();

            try {
                for (j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
                    // 判断单元格是否为空
                    if (sheet.getRow(i).getCell(j) != null) {
                        sheet.getRow(i).getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);
                        if (sheet.getRow(i).getCell(j).getStringCellValue() != null && !sheet.getRow(i).getCell(j).getStringCellValue().trim().equals("")) {
                            String cellValue = sheet.getRow(i).getCell(j).getStringCellValue().trim();
                            rowValue.put(j, cellValue);
                        } else {
                            rowValue.put(j, ""); //$NON-NLS-1$
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excel data error,occur at the cell: i = " + i + ", j = " + j);
            }
            // 对获取的数据进行校验，并转化成实体对象
            boolean isEmptyRow = true;
            List<FlightBase> listFlightBase = ValidateFlightBaseFromExcel.validateLoadSingleData(rowValue, i);
            for (FlightBase flightBase : listFlightBase) {
                // 如果该excel数据行不是空行
                if (flightBase.getFlightScheduledDate() != null
                        && !flightBase.getFlightDirection().equals("")
                        && !flightBase.getFlightIdentity().equals("")) {
                    isEmptyRow = false;
                    flightBaseAllList.add(flightBase);
                }
                // 如果是非空行
                if (!isEmptyRow) {
                    if (isDataError == false) {
                        if (flightBase.getVerifyDescription() != null
                                && !flightBase.getVerifyDescription().equals("")) {
                            // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                            isDataError = true;
                            flightBaseErrorList.clear();
                            flightBaseErrorList.add(flightBase);
                        }
                    } else {
                        if (flightBase.getVerifyDescription() != null
                                && !flightBase.getVerifyDescription().equals("")) {
                            flightBaseErrorList.add(flightBase);
                            sheet.getRow(i).createCell(columnsCount);
                            sheet.getRow(i)
                                    .getCell(columnsCount)
                                    .setCellValue(flightBase.getVerifyDescription());
                        }
                    }
                }
                isEmptyRow = true;
            }
        }// for 结束

        List resultlist = new ArrayList();
        Map<String, Object> excelResult = new HashMap<>();
        // 列名为空，返回空
        if (isColumnNameError || isDataError) {
            resultlist.add(flightBaseAllList);
            resultlist.add(flightBaseAllList);
        } else {
            resultlist.add(flightBaseAllList);
            resultlist.add(flightBaseErrorList);
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
    public Map<String, Object> ImportFlightBaseLoadDataByExcel2007(InputStream inputStream) throws IOException {
        String columnNameError = "";
        List<FlightBase> flightBaseAllList = new ArrayList<>();
        List<FlightBase> flightBaseErrorList = new ArrayList<>();

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
            return null;
        }
        // columnsCount获得字段名数
        columnsCount = sheet.getRow(0).getLastCellNum();
        if (columnsCount != 19) {// 列数不对
            isDataError = true;
            return null;
        }
        totalData = sheet.getLastRowNum();

        // 　错误消息写入
        // sheet.getRow(0).createCell(columnsCount).setCellValue("错误描述");
        // 处理excel第一行的字段名
        for (int i = 0; i < columnsCount; i++) {
            if (sheet.getRow(0).getCell(i) == null) {
                isColumnNameError = true;
                break;
            }

            sheet.getRow(0).getCell(i).setCellType(XSSFCell.CELL_TYPE_STRING);
            String columnName = sheet.getRow(0).getCell(i).getStringCellValue()
                    .trim();

           
            isColumnNameError = !ValidateFlightBaseFromExcel.verifyLoadColumnName(i, columnName);
            if (isColumnNameError) {// 列名不一致
                columnNameError +=  "列名" + columnName + "存在错误";
                return null;
            }
            columns.add(columnName);
        }
        int i = 0, j = 0;
        // 循环获取excel中的所有记录
        for (i = 1; i <= totalData; i++) {
            // rowValue的格式{字段名:value;}
            Map<Integer, String> rowValue = new HashMap<>();

            try {
            for ( j = 0; j < sheet.getRow(i).getLastCellNum()
                    && j < columnsCount; j++) {
                // 判断单元格是否为空
                if (sheet.getRow(i).getCell(j) != null) {
                    sheet.getRow(i).getCell(j)
                            .setCellType(XSSFCell.CELL_TYPE_STRING);
                    if (sheet.getRow(i).getCell(j).getStringCellValue() != null
                            && !sheet.getRow(i).getCell(j).getStringCellValue()
                                    .trim().equals("")) {
                        String cellValue = sheet.getRow(i).getCell(j)
                                .getStringCellValue().trim();
                            rowValue.put(j, cellValue);
                    } else {
                            rowValue.put(j, ""); //$NON-NLS-1$
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excel data error,occur at the cell: i = " + i + ", j = " + j);
            }
            // 对获取的数据进行校验，并转化成实体对象
            boolean isEmptyRow = true;
            List<FlightBase> listFlightBase = ValidateFlightBaseFromExcel.validateLoadSingleData(rowValue, i);
            for (FlightBase flightBase : listFlightBase) {
                // 如果该excel数据行不是空行
                if (flightBase.getFlightScheduledDate() != null
                        && !flightBase.getFlightDirection().equals("")
                        && !flightBase.getFlightIdentity().equals("")) {
                    isEmptyRow = false;
                    flightBaseAllList.add(flightBase);
                }
                // 如果是非空行
                if (!isEmptyRow) {
                    if (isDataError == false) {
                        if (flightBase.getVerifyDescription() != null
                                && !flightBase.getVerifyDescription().equals("")) {
                            // 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
                            isDataError = true;
                            flightBaseErrorList.clear();
                            flightBaseErrorList.add(flightBase);
                        }
                    } else {
                        if (flightBase.getVerifyDescription() != null
                                && !flightBase.getVerifyDescription().equals("")) {
                            flightBaseErrorList.add(flightBase);
                            sheet.getRow(i).createCell(columnsCount);
                            sheet.getRow(i)
                                    .getCell(columnsCount)
                                    .setCellValue(flightBase.getVerifyDescription());
                        }
                    }
                }
                isEmptyRow = true;
            }
        }// for 结束

        List resultlist = new ArrayList();
        Map<String, Object> excelResult = new HashMap<>();
        // 列名为空，返回空
        if (isColumnNameError || isDataError) {
            resultlist.add(flightBaseAllList);
            resultlist.add(flightBaseAllList);
        } else {
            resultlist.add(flightBaseAllList);
            resultlist.add(flightBaseErrorList);
        }
        excelResult.put("result", resultlist);
        excelResult.put("resultMessage", columnNameError);
        return excelResult;
    }
    // add by march 20140826 end

}
