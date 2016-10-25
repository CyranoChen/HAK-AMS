package com.wonders.frame.ams.controller.share;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.Content;

import com.wonders.frame.ams.utils.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/share/gridPrinter")
public class GridPrinterController {


    /**
     * 通过jqxGrid exportdata 方法请求的Grid导出功能
     * @param request   content = Grid ExcelML格式的XML、
     * @param response
     */
    @RequestMapping("excel")
    public void print(HttpServletRequest request,HttpServletResponse response){
        String fileName = request.getParameter("filename");
        String fileName2 = request.getParameter("fileName");
        System.out.println("fileName =========== " + fileName);
        System.out.println("fileName2 =========== " + fileName2);
        String xml = request.getParameter("content");
//		String format = request.getParameter("format");
//		xml = "";
//		xml += "	<?xml version=\"1.0\"?>";
//		xml += "	<?mso-application progid=\"Excel.Sheet\"?>";
//		xml += "	<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"";
//		xml += "	 xmlns:o=\"urn:schemas-microsoft-com:office:office\"";
//		xml += "	 xmlns:x=\"urn:schemas-microsoft-com:office:excel\"";
//		xml += "	 xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"";
//		xml += "	 xmlns:html=\"http://www.w3.org/TR/REC-html40\">";
//
//		xml += "	 <Worksheet ss:Name=\"Sheet1\">";
////		xml += "	  <Table ss:ExpandedColumnCount=\"3\" ss:ExpandedRowCount=\"3\" x:FullColumns=\"1\"";
////		xml += "	   x:FullRows=\"1\" ss:DefaultRowHeight=\"14.4\">";
//		xml += "	  <Table>";
//		xml += "	   <Row>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "	   </Row>";
//		xml += "	   <Row>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "	   </Row>";
//		xml += "	   <Row>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "		<Cell><Data ss:Type=\"String\">234</Data></Cell>";
//		xml += "	   </Row>";
//		xml += "	  </Table>";
////		xml += "	  <WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">";
////		xml += "	   <PageSetup>";
////		xml += "		<Header x:Margin=\"0.3\"/>";
////		xml += "		<Footer x:Margin=\"0.3\"/>";
////		xml += "		<PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/>";
////		xml += "	   </PageSetup>";
////		xml += "	   <Print>";
////		xml += "		<ValidPrinterInfo/>";
////		xml += "		<PaperSizeIndex>9</PaperSizeIndex>";
////		xml += "		<HorizontalResolution>600</HorizontalResolution>";
////		xml += "		<VerticalResolution>600</VerticalResolution>";
////		xml += "	   </Print>";
////		xml += "	   <Selected/>";
////		xml += "	   <Panes>";
////		xml += "		<Pane>";
////		xml += "		 <Number>3</Number>";
////		xml += "		 <ActiveRow>5</ActiveRow>";
////		xml += "		 <ActiveCol>3</ActiveCol>";
////		xml += "		</Pane>";
////		xml += "	   </Panes>";
////		xml += "	   <ProtectObjects>False</ProtectObjects>";
////		xml += "	   <ProtectScenarios>False</ProtectScenarios>";
////		xml += "	  </WorksheetOptions>";
//		xml += "	 </Worksheet>";
//		xml += "	</Workbook>";




        ExcelUtil.output(response, xml, fileName);
    }

}
