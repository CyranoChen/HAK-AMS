package com.nlia.fqdb.util.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.message.base.AirportMessages;

@Component
public class ImportAirportByExcel{

	/**
	 * 读取excel2003文件流，将每条记录读取并转换成对象列表
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public List<Airport> ImportAirportByExcel2003(InputStream inputStream) throws IOException {

		List<Airport> airportList = new ArrayList<>();

		int totalData = 0;
		int columnsCount = 0;
		List<String> columns = new ArrayList<String>();
		boolean isColumnNameError = false;
		Boolean isDataError = false;

		HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workBook.getSheetAt(0);

		if(sheet.getRow(0) == null){
			isColumnNameError = true;
		}
		columnsCount = sheet.getRow(0).getLastCellNum();
		totalData = sheet.getLastRowNum();
		for (int i = 0; i < columnsCount; i++) {
			if (sheet.getRow(0).getCell(i) == null) {
				isColumnNameError = true;
				break;
			}

			sheet.getRow(0).getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
			String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();

			// 判断对应列名是否相同
			if (!ValidateAirportFromExcel.verifyColumnName(i, columnName)) {
				isColumnNameError = true;
				break;
			}
			columns.add(columnName);
		}

		if (isColumnNameError) {
			Airport airportError = new Airport();
			airportError.setVerifyDescription(AirportMessages.getString("COLUMNNAME_ERROR"));
			airportList.add(airportError);
			return airportList;
		}

		// 循环获取excel中的所有记录
		for (int i = 1; i < totalData + 1; i++) {
			Map<String, String> rowValue = new HashMap<>();

			for (int j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
				// 判断是否为空
				if (sheet.getRow(i).getCell(j) != null) {
					sheet.getRow(i).getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);
					String cellValue = sheet.getRow(i).getCell(j).getStringCellValue().trim();
					rowValue.put(columns.get(j), cellValue);
				} else {
					rowValue.put(columns.get(j), "");
				}
			}
			// 对获取的数据进行校验，并转化成实体对象
			Airport airport = ValidateAirportFromExcel.validateSingleData(rowValue, i);

			if (isDataError == false) {
				if (airport.getVerifyDescription() == null || airport.getVerifyDescription().equals("")) {
					airportList.add(airport);
				} else {
					// 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
					isDataError = true;
					airportList.clear();
					airportList.add(airport);
				}
			} else {
				if (airport.getVerifyDescription() != null && !airport.getVerifyDescription().equals("")) {
					airportList.add(airport);
				}
			}
		}
		return airportList;
	}

	/**
	 * 读取excel2007文件流，将每条记录读取并转换成对象列表
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public List<Airport> ImportAirportByExcel2007(InputStream inputStream) throws IOException {

		List<Airport> airportList = new ArrayList<>();

		int totalData = 0;
		int columnsCount = 0;
		List<String> columns = new ArrayList<String>();
		boolean isColumnNameError = false;
		Boolean isDataError = false;

		XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workBook.getSheetAt(0);

		columnsCount = sheet.getRow(0).getLastCellNum();
		totalData = sheet.getLastRowNum();
		for (int i = 0; i < columnsCount; i++) {
			if (sheet.getRow(0) == null || sheet.getRow(0).getCell(i) == null) {
				isColumnNameError = true;
				break;
			}

			sheet.getRow(0).getCell(i).setCellType(XSSFCell.CELL_TYPE_STRING);
			String columnName = sheet.getRow(0).getCell(i).getStringCellValue().trim();

			// 判断对应列名是否相同
			if (!ValidateAirportFromExcel.verifyColumnName(i, columnName)) {
				isColumnNameError = true;
				break;
			}
			columns.add(columnName);
		}

		if (isColumnNameError) {
			Airport airportError = new Airport();
			airportError.setVerifyDescription(AirportMessages.getString("COLUMNNAME_ERROR"));
			airportList.add(airportError);
			return airportList;
		}

		// 循环获取excel中的所有记录
		for (int i = 1; i < totalData + 1; i++) {
			Map<String, String> rowValue = new HashMap<>();

			for (int j = 0; j < sheet.getRow(i).getLastCellNum() && j < columnsCount; j++) {
				// 判断是否为空
				if (sheet.getRow(i).getCell(j) != null) {
					sheet.getRow(i).getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
					String cellValue = sheet.getRow(i).getCell(j).getStringCellValue().trim();
					rowValue.put(columns.get(j), cellValue);
				} else {
					rowValue.put(columns.get(j), "");
				}
			}
			// 对获取的数据进行校验，并转化成实体对象
			Airport airport = ValidateAirportFromExcel.validateSingleData(rowValue, i);

			if (isDataError == false) {
				if (airport.getVerifyDescription() == null || airport.getVerifyDescription().equals("")) {
					airportList.add(airport);
				} else {
					// 如果记录校验失败，清空list列表，然后将校验失败的这条记录加入到列表中
					isDataError = true;
					airportList.clear();
					airportList.add(airport);
				}
			} else {
				if (airport.getVerifyDescription() != null && !airport.getVerifyDescription().equals("")) {
					airportList.add(airport);
				}
			}
		}
		return airportList;
	}

}
