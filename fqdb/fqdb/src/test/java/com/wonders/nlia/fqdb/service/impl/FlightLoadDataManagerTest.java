package com.wonders.nlia.fqdb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.service.impl.FlightLoadDataManager;
import com.nlia.fqdb.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class FlightLoadDataManagerTest {
    @Resource
    private FlightLoadDataManager flightLoadDataManager;
    
    @Test
    public void testgenerateFlightLoadData(){
        List<FlightLoadData> flightLoadDataList=flightLoadDataManager.generate(DateUtils.String2Date("2015-04-14 00:00"), DateUtils.String2Date("2015-04-15 00:00"));
    }
    
    // 从excel导入数据到数据库
    @Test
    public void importDataFromExcel() {

        String filePath = "D:\\test\\1.xls";
                
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<FlightLoadData> flightLoadDataList = new ArrayList<FlightLoadData>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<FlightLoadData>();
        Map<String, Object> flightLoadDataResult = new HashMap<>();
        String columnErrorMessage = "";

        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[8192];

            for (int length = 0; (length = inputStream.read(buffer)) > 0;) {
                outputStream.write(buffer, 0, length);
            }
            byte[] bytes = outputStream.toByteArray();

            // 判断excel2003　or excel2007
            if (filePath.endsWith(".xls")) {
                // flightBaseErrorList=
                flightLoadDataResult = flightLoadDataManager.importFlightLoadDataByExcel2003(bytes);
                flightLoadDataList = (List<FlightLoadData>) flightLoadDataResult.get("list");
                flightLoadDataErrorList=(List<FlightLoadData>) flightLoadDataResult.get("errorList");
                columnErrorMessage = (String) flightLoadDataResult.get("errorMessage");

            } else if (filePath.endsWith(".xlsx")) {
                flightLoadDataResult = flightLoadDataManager.importFlightLoadDataByExcel2007(bytes);
                flightLoadDataList = (List<FlightLoadData>) flightLoadDataResult.get("list");
                flightLoadDataErrorList=(List<FlightLoadData>) flightLoadDataResult.get("errorList");
                columnErrorMessage = (String) flightLoadDataResult.get("errorMessage");
                // MessageDialog.openInformation(null, FQDBMessages.FQDB_PROMPT,
                // FQDBMessages.SAVE_TO_DATABASE_SUCCESS);
            }
            // 遍历航班list，生成报错信息verifyDescription，生成报错坐标errorMessage
            Map<Integer, List<Integer>> errorMessage = new HashMap<>();
            String verifyDescription = "";
            for (FlightLoadData flightLoadData : flightLoadDataErrorList) {
                verifyDescription += flightLoadData.getVerifyDescription();
                if (flightLoadData.getErrorMessage() != null) {
                    errorMessage.putAll(flightLoadData.getErrorMessage());
                }
            }
            verifyDescription = verifyDescription.trim();

            // 判断返回消息中有无报错消息，如果没有则导入到table中，反则提示错误消息
            if (verifyDescription.equals("") && columnErrorMessage.equals("")) {
                // 清空表格、导入到table中，反则提示错误消息
//                MessageDialog.openInformation(null, FQDBMessages.FQDB_PROMPT, FQDBMessages.SAVE_TO_DATABASE_SUCCESS);
//                flightInfoTable.getTable().setVisible(false);
//                flightInfoTable.getTable().setRedraw(false);
//                flightInfoTable.getWritableList().clear();

//              for (FlightBase flightBase : flightLoadDataList) {
//                  flightInfoTable.getWritableList().add(flightBase);
//              }
//                flightInfoTable.getWritableList().addAll(flightLoadDataList);
////              flightInfoTable.setInput(flightInfoTable.getWritableList());
//                flightInfoTable.getTable().setRedraw(true);
//                flightInfoTable.getTable().setVisible(true);
                
                // terminalToolBarComposite.getToolBar().getItem(6).setEnabled(true);
                // showInTable = true;
            } else {
                // 判断文件格式，然后给报错的单元格着上红色
//                if (fileName.endsWith(".xls")) {
//                    ExcelOperatorUtils.changeExcel2003BackgroundColor(fileName, errorMessage);
//                } else if (fileName.endsWith(".xlsx")) {
//                    ExcelOperatorUtils.changeExcel2007BackgroundColor(fileName, errorMessage);
//                }
                // 去除后面的分号
                verifyDescription = verifyDescription.endsWith("；") ? verifyDescription.substring(0, verifyDescription.lastIndexOf("；")) : verifyDescription;
                String resultMessage = columnErrorMessage + "；" + verifyDescription;
//                MessageDialog.openWarning(null, FQDBMessages.FQDB_WARNING, resultMessage);
                // showInTable = false;
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                inputStream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            MessageDialog.openWarning(null, FQDBMessages.FQDB_WARNING, FQDBMessages.FQDB_SYSTEM_ERROR);
        }

    }
}
