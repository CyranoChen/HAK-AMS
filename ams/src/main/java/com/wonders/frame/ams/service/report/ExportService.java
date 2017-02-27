package com.wonders.frame.ams.service.report;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.wonders.frame.ams.utils.ArrayUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wonders.frame.ams.utils.ResourceUtil;


@Service
public class ExportService {
	
	ResourceUtil ru = new ResourceUtil();
    public void exportExcel(HttpServletResponse response,List<List<Map<String,Object>>> list,String name, String time){
    	String tit = ru.getProperty("report/exportCols", name + "_tit");
        XSSFWorkbook wb = new XSSFWorkbook();
//		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = ru.getProperty("report/exportCols", "sheetName");
		String[] sheetNames = sheetName.split(",");
		if(list.size()>1){
			for(int i=0;i<list.size();i++){
				doExport(wb,tit,sheetNames[i],name,list.get(i),i);
			}
		}else{
			doExport(wb,tit,tit,name,list.get(0),0);
		}
		
		// 将文件上传到页面
        OutputStream ouputStream = null;
        try {
            response.setContentType("application/x-msdownloadoctet-stream");
            String wordName = URLEncoder.encode( time+tit, "UTF-8");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + wordName + ".xlsx");
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                ouputStream.flush();
                ouputStream.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void doExport(XSSFWorkbook wb,String tit,String sheetName,String name,List<Map<String,Object>> list,int sheetIndex){
    	
        String colEn = ru.getProperty("report/exportCols", name + "_cols_En");
        String[] colsEn = colEn.split(",");
    	// 在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(sheetName);
		
		XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗

        XSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 10);
        
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 左
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 竖直居中
        style.setWrapText(false);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        style.setFont(font1);

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 竖直居中
        style1.setWrapText(false);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        style1.setFont(font);
        
        //单元格合并
        
        int lastCol = colsEn.length-1 ;
        
        if("liquidationIncomeCalculation".equals(name) && sheetIndex == 2){
        	lastCol = lastCol + 3;
        }

        sheet.addMergedRegion(new CellRangeAddress(0,0,(short)0,(short)lastCol));
        
        //表头
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(tit);
        cell.setCellStyle(style1);
        
        
        if(("demand").equals(name)){
        	String colCn = ru.getProperty("report/exportCols", name + "_cols_Cn");
        	String colUnit = ru.getProperty("report/exportCols", name + "_cols_unit");
            String[] colsCn = colCn.split("-");
            String[] _colsCn = colsCn[0].split(",");
            String[] colsCn_ = colsCn[1].split(",");
            String[] colUnits = colUnit.split(",");
            
            
            row = sheet.createRow(1);
	        for(int i=0;i<_colsCn.length;i++){
	        	cell = row.createCell(i);
	            cell.setCellValue(_colsCn[i]);
	            cell.setCellStyle(style1);
	        }
	        
	        
	        
	        for(int i=0;i<colsCn_.length;i++){
	        	cell = row.createCell(_colsCn.length+(i*3));
	            cell.setCellValue(colsCn_[i]);
	            cell.setCellStyle(style1);
	            
	            cell = row.createCell(_colsCn.length+(i*3)+1);
	            cell.setCellStyle(style1);
	            
	            cell = row.createCell(_colsCn.length+(i*3)+2);
	            cell.setCellStyle(style1);

	        }
	        row = sheet.createRow(2);
	        
	        
	        for(int i=0;i<_colsCn.length;i++){
	        	cell = row.createCell(i);
	            cell.setCellStyle(style1);
	        }
	        
	        
	        for(int i=0;i<colUnits.length;i++){
	        	cell = row.createCell(_colsCn.length+(i*3));
	            cell.setCellValue(colUnits[i]);
	            cell.setCellStyle(style1);
	            
	            cell = row.createCell(_colsCn.length+(i*3)+1);
	            cell.setCellValue("单价");
	            cell.setCellStyle(style1);
	            
	            cell = row.createCell(_colsCn.length+(i*3)+2);
	            cell.setCellValue("金额");
	            cell.setCellStyle(style1);
	        }
	        
	      //添加数据
	        if(list!=null){
	        	for(int i=0;i<list.size();i++){
	        		row = sheet.createRow(i+3);
	        		for(int j=0;j<colsEn.length;j++){
	        			cell = row.createCell(j);
	        			String val = list.get(i).get(colsEn[j])==null?"":list.get(i).get(colsEn[j]).toString();
	        			try {
							double val_d = Double.parseDouble(val);
							cell.setCellValue(val_d);
						} catch (Exception e) {
							// TODO: handle exception
							cell.setCellValue(val);
						}
	        			cell.setCellStyle(style);
	        		}
	        	}
	        	
	        	for(int i=0;i<_colsCn.length;i++){
	            	sheet.addMergedRegion(new CellRangeAddress(1,2,(short)i,(short)i));
	            }
	            
	            
	            //单元格合并
	            for(int i=0;i<colsCn_.length;i++){
	            	sheet.addMergedRegion(new CellRangeAddress(1,1,(short)(_colsCn.length+(i*3)),(short)(_colsCn.length+2+(i*3))));
	            }
	        }
        }
        else if(("manifest").equals(name)){
        	
        	String colCn = ru.getProperty("report/exportCols", name + "_cols_Cn");
        	
            row = sheet.createRow(1);
            for(int i=0;i<colsEn.length;i++){
            	cell = row.createCell(i);
            	cell.setCellStyle(style1);
            }
            
            XSSFRow row2 = sheet.createRow(2);
            for(int i=0;i<colsEn.length;i++){
            	cell = row2.createCell(i);
            	cell.setCellStyle(style1);
            }
            
          //单元格合并
            for(int i=0;i<5;i++){
            	sheet.addMergedRegion(new CellRangeAddress(1,2,(short)i,(short)i));
            }
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)5,(short)8));
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)9,(short)10));
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)11,(short)12));
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)13,(short)14));
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)15,(short)17));
            sheet.addMergedRegion(new CellRangeAddress(1,1,(short)18,(short)21));
            sheet.addMergedRegion(new CellRangeAddress(1,2,(short)22,(short)22));
            
            //row = sheet.createRow(1);
        	cell = row.createCell(0);
            cell.setCellValue("计划日期");
            cell.setCellStyle(style1);
            
            cell = row.createCell(1);
            cell.setCellValue("进离港");
            cell.setCellStyle(style1);
            
            cell = row.createCell(2);
            cell.setCellValue("航空公司");
            cell.setCellStyle(style1);
            
            cell = row.createCell(3);
            cell.setCellValue("航班号");
            cell.setCellStyle(style1);
            
            cell = row.createCell(4);
            cell.setCellValue("机号");
            cell.setCellStyle(style1);
            
            cell = row.createCell(5);
            cell.setCellValue("本站出港");
            cell.setCellStyle(style1);
            
            cell = row.createCell(9);
            cell.setCellValue("出港货物");
            cell.setCellStyle(style1);
            
            cell = row.createCell(11);
            cell.setCellValue("出港邮件");
            cell.setCellStyle(style1);
            
            cell = row.createCell(13);
            cell.setCellValue("出港行李");
            cell.setCellStyle(style1);
            
            
            cell = row.createCell(15);
            cell.setCellValue("出港旅客");
            cell.setCellStyle(style1);
            
            cell = row.createCell(18);
            cell.setCellValue("实际过站旅客");
            cell.setCellStyle(style1);
            
            cell = row.createCell(22);
            cell.setCellValue("代理");
            cell.setCellStyle(style1);
            
            
            //row2 = sheet.createRow(2);
            String[] colsCn = colCn.split(",");
            for(int i=0;i<colsCn.length;i++){
	        	cell = row2.createCell(i+5);
	            cell.setCellValue(colsCn[i]);
	            cell.setCellStyle(style1);
	        }
            
          //添加数据
	        if(list!=null){
	        	for(int i=0;i<list.size();i++){
	        		row = sheet.createRow(i+3);
	        		for(int j=0;j<colsEn.length;j++){
	        			cell = row.createCell(j);
	        			String val = list.get(i).get(colsEn[j])==null?"":list.get(i).get(colsEn[j]).toString();
	        			try {
							double val_d = Double.parseDouble(val);
							cell.setCellValue(val_d);
						} catch (Exception e) {
							// TODO: handle exception
							cell.setCellValue(val);
						}
	        			cell.setCellStyle(style);
	        		}
	        	}
	        }
            
        }else{
        	String colCn = ru.getProperty("report/exportCols", name + "_cols_Cn");
            String[] colsCn = colCn.split(",");
            
	        //列名
	        row = sheet.createRow(1);
	        for(int i=0;i<colsCn.length;i++){
	        	cell = row.createCell(i);
	            cell.setCellValue(colsCn[i]);
	            cell.setCellStyle(style1);
	        }

            //20161024 fanyu
            if("liquidationIncomeCalculation".equals(name) && sheetIndex == 2){

                List<String> l = new ArrayList<String>(Arrays.asList(colsEn));
                l.add("AIRCRAFT_TAKEOFF_WEIGHT");
                l.add("AIRCRAFT_PAYLOAD");
                l.add("AIRCRAFT_SEAT_CAPACITY");



                cell = row.createCell(colsEn.length);
                cell.setCellValue("飞机全重");
                cell.setCellStyle(style1);

                cell = row.createCell(colsEn.length + 1);
                cell.setCellValue("飞机业载");
                cell.setCellStyle(style1);

                cell = row.createCell(colsEn.length + 2);
                cell.setCellValue("飞机最大座位数");
                cell.setCellStyle(style1);

                colsEn = ArrayUtil.toArray(l,String.class);

            }

	
	        //添加数据
	        if(list!=null){
	        	for(int i=0;i<list.size();i++){
	        		row = sheet.createRow(i+2);
	        		for(int j=0;j<colsEn.length;j++){
	        			cell = row.createCell(j);
	        			String val = list.get(i).get(colsEn[j])==null?"":list.get(i).get(colsEn[j]).toString();
	        			try {
							double val_d = Double.parseDouble(val);
							cell.setCellValue(val_d);
						} catch (Exception e) {
							// TODO: handle exception
							cell.setCellValue(val);
						}
	        			cell.setCellStyle(style);
	        		}
	        	}
	        }
        }
        //自适应宽度
        for(int i=0;i<colsEn.length;i++){
        	//sheet.autoSizeColumn(i);
        	sheet.autoSizeColumn((short)i,true);
        }
    }
}
