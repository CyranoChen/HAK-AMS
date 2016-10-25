package com.wonders.frame.ams.service.basic;

import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.dto.basic.DiscountDto;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.ExcelUtil;
import com.wonders.frame.ams.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wangsq on 2016/9/14.
 */
@Service
public class DiscountManagerService {

    @Autowired
    private BaseDao baseDao;




    //查询subject的名称
    public Map<String,String> queryChargeSubjectName(){
        String getSubject = "select ID,NAME from charge_subject ";


        List<Map<String,Object>> subject_list = baseDao.getJdbcTemplate().queryForList(getSubject);

        Map<String,String> subjects = new HashMap<String,String>();

        for(Map<String,Object> m : subject_list){
            subjects.put(m.get("ID").toString(), StringUtil.nullToEmptyString(m.get("NAME")));
        }

        return subjects;
    }

    //查询chargeType的名称
    public Map<String,String> queryChargeTypeName(){

        String getChargeType = "select ID,NAME from charge_type ";


        List<Map<String,Object>> chargeType_list = baseDao.getJdbcTemplate().queryForList(getChargeType);

        Map<String,String> chargeTypes = new LinkedHashMap<String,String>();

        for(Map<String,Object> m : chargeType_list){
            chargeTypes.put(m.get("ID").toString(),StringUtil.nullToEmptyString(m.get("NAME")));
        }

        return chargeTypes;

    }


    public boolean updateDiscount(String registration,String key,String discount){
        try{
            String sql = " delete from aircraft_discount where registration = ? and chargetype_id||','||chargesubject_id = ? ";
            baseDao.getJdbcTemplate().update(sql,registration,key);

            String [] keys = key.split(",");

            String typeId = keys[0];
            String subjectId = keys.length > 1 ? keys[1] : null;

            sql = "insert into aircraft_discount(id,registration,chargetype_id,chargesubject_id,discount,remove_flag,charge_type_name,charge_subject_name)" +
                    "  values (seq_insert.nextval,?,?,?,?,'1',(select name from charge_type where id = ? ),(select name from charge_subject where id = ?) )";


            baseDao.getJdbcTemplate().update(sql,registration,typeId,subjectId,discount,typeId,subjectId);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;



    }




    public List<DiscountDto> queryByGroup(String registration,String airlineOfFlight){

        String sql =
                "select res2.*,ba.airline_iata_code airline_of_flight, a.aircraft_type_iata_code,a.aircraft_seat_capacity ,ba.airline_description from ( " +
                "select distinct res.registration," +
                "LISTAGG(res.discount,'-') WITHIN GROUP( ORDER BY res.k)  over (partition by res.registration) discount," +
                "LISTAGG(res.k,'-') WITHIN GROUP( ORDER BY res.k)  over (partition by res.registration) chargetype_id " +
                " from " +
                "( select distinct rtn.registration,rtn.k,decode(d.discount,null,'$',rtrim(to_char(d.discount,'fm99990.9999'),'.')) discount" +
                " from " +
                "( select * from ( select registration from aircraft_discount  where remove_flag = '1' group by registration ) r , ( select chargetype_id||','||chargesubject_id k from aircraft_discount  where remove_flag = '1' group by chargetype_id,chargesubject_id ) g ) rtn " +
                " left join " +
                " aircraft_discount d" +
                " on d.chargetype_id||','||d.chargesubject_id = rtn.k and d.registration = rtn.registration and d.remove_flag = '1' ";

        if(Chk.spaceCheck(registration) && Chk.illegalCharacterCheck(registration)){
            sql += " where rtn.registration like '%" + registration.toUpperCase() + "%' ";
        }
          sql +=  " ) res" +
                " order by res.registration";
        sql += " ) res2 left join aircraft_airline_alteration a on res2.registration = a.aircraft_registration " +
                "   and to_char(a.end_date,'yyyy') = '2099'  ";


        sql += " left join base_airline ba  on ba.id = a.current_airline_id ";

        if(Chk.spaceCheck(airlineOfFlight) && Chk.illegalCharacterCheck(airlineOfFlight)){
            sql += " where a.airline_of_flight like '%" + airlineOfFlight.toUpperCase() + "%' ";
        }
        return baseDao.queryForList(sql,DiscountDto.class);


    }





    public String saveDiscountFromExcel(HttpServletRequest request,HSSFWorkbook wb){


        List<String> title = (List<String>) request.getSession().getAttribute("discount_report_title");

//        List<String> type = new ArrayList<String>();



        Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();

//        HSSFWorkbook wb = ExcelUtil.getTempletWorkbook(request,"/WEB-INF/classes/t2.xls");
        HSSFSheet sheet = wb.getSheetAt(0);


        HSSFRow idRow = sheet.getRow(0);
        int colsize = (int)idRow.getLastCellNum();


        int rownum = sheet.getLastRowNum();
        if(rownum < 3){
            return "导入失败,表中至少需要一行数据";
        }

        String [] fixedCols = new String [] {
                "registration","airlineDescription","airlineOfFlight","aircraftTypeIataCode","aircraftSeatCapacity"
        };
        String [] fixedColName = new String [] {
                "机号","航空公司","二字码","机型","座位数"
        };


        int fixedCol = fixedCols.length;   //前5列为固定列




        for(int i = 0 ; i < fixedCol ; i ++ ){
            HSSFCell idCell = idRow.getCell(i);
            if(idCell != null &&  Chk.spaceCheck(idCell.getStringCellValue())){
                String id = idCell.getStringCellValue();
                if( ! id.equals(fixedCols[i])){
                    return "第" + (i +1) + "列 必须为 " + fixedColName[i];
                }
            }else{
                return "第" + (i +1) + "列 必须为 " + fixedColName[i];
            }
        }

        Map<String,String> subjects = queryChargeSubjectName();

        Map<String,String> chargeTypes = queryChargeTypeName();

        for(int i = 3 ; i <= rownum ; i ++){
            HSSFRow row =  sheet.getRow(i);

            String reg = "";
            for(int j = fixedCol ; j < colsize ; j ++){
                //第一列的机号需要导入
                if( j == fixedCol){
                    HSSFCell cell = row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    reg = cell.getStringCellValue();
                    if( ! Chk.spaceCheck(reg)){
                        return "第" + (i + 1) + "行机号必须输入";
                    }
                    if( ! Chk.lengthCheck(reg, 1, 10)){
                        return "第" + (i + 1) + "行机号超过最大长度限制";
                    }


                    if(result.containsKey(result)){
                        return "第" + (i + 1) + "行机号重复";
                    }

                    result.put(reg,new LinkedHashMap<String,String>());
                }




                HSSFCell idCell = idRow.getCell(j);
                if(idCell == null || ! Chk.spaceCheck(idCell.getStringCellValue())){
                    return "第" + (j+1) + "列 ID未输入或格式不正确";
                }
                String id = idCell.getStringCellValue();
                String [] ids = id.split(",");
                if(ids.length != 1 && ids.length != 2){
                    return "第" + (j+1) + "列 ID未输入或格式不正确";
                }
                if( ! chargeTypes.containsKey(ids[0]) ){
                    return "第" + (j+1) + "列 TYPE ID不存在或格式不正确";
                }
                if(ids.length == 2 && ! subjects.containsKey(ids[1])){
                    return "第" + (j+1) + "列 SUBJECT ID不存在或格式不正确";
                }



                HSSFCell cell = row.getCell(j);
                if(cell == null){
                    return "第" + (i + 1) + "行,第" + (j+1) + "列数据必须输入";
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);

                String v = cell.getStringCellValue();
                if(!Chk.spaceCheck(v)){
                    return "第" + (i + 1) + "行,第" + (j+1) + "列数据必须输入";
                }

                if(! Chk.numberCheck(v)){
                    return "第" + (i + 1) + "行,第" +(j+1) + "列数据必须为数值";
                }

                if( v.startsWith("-")){
                    return "第" + (i + 1) + "行,第" +(j+1) + "列数据不能为负数";
                }

                String [] vs = v.split("\\.");
                if( ! Chk.maxCheck(vs[0],5)){
                    return "第" + (i + 1) + "行,第" +(j+1) + "列数据整数部分超过最大长度[5位]限制";
                }

                if(vs.length > 1){
                    if( ! Chk.maxCheck(vs[1],4)){
                        return "第" + (i + 1) + "行,第" + (j+1) + "列数据小数部分超过最大长度[4位]限制";
                    }
                }

                result.get(reg).put(id,v);

            }
        }















        String sql = "insert into aircraft_discount values(seq_insert.nextval,?,?,?,?,'1',?,?,'')";
        List<Object []> args = new ArrayList<Object []>();


        //处理表格数据前.先删除原来的旧数据
        String delSql = "delete from aircraft_discount where registration = ? and chargetype_id||','||chargesubject_id = ?  ";
        List<Object []> deleteArgs = new ArrayList<Object []>();



//        int i = 100000;
        for(Iterator<String> it = result.keySet().iterator() ; it.hasNext() ;){
            String reg = it.next();


            Map<String,String> data = result.get(reg);

            for(Iterator<String> it2 = data.keySet().iterator(); it2.hasNext() ;){
                String typeOrSubject = it2.next();
                String value = data.get(typeOrSubject);

                Long typeId = null;
                Long subjectId = null;
                String typeName = "";
                String subjectName = "";

                String [] tos = typeOrSubject.split(",");


                typeId = Long.parseLong(tos[0]);
                typeName = chargeTypes.get(tos[0]);

                if(tos.length > 1){
                    subjectId = Long.parseLong(tos[1]);
                    subjectName = subjects.get(tos[1]);
                }

                deleteArgs .add(new Object[]{reg,typeOrSubject});



                args.add(new Object [] {reg,typeId,subjectId, Chk.numberCheck(value) ? Double.parseDouble(value) : null , typeName,subjectName});
            }

        }

        baseDao.getJdbcTemplate().batchUpdate(delSql,deleteArgs);


        baseDao.getJdbcTemplate().batchUpdate(sql,args);


        return "success";


    }






    public HSSFWorkbook exp(Map<String,Object> rtn,HttpServletRequest request){
        List<Map<String,String>> data = (List<Map<String, String>>) rtn.get("data");

        List<String> title = (List<String>) request.getSession().getAttribute("discount_report_title");

        List<String> titleName = (List<String>) request.getSession().getAttribute("discount_report_title_name");

        int fixedIdx = 5;


        title.add(0,"aircraftSeatCapacity");
        title.add(0,"aircraftTypeIataCode");
        title.add(0,"airlineOfFlight");
        title.add(0,"airlineDescription");
        title.add(0,"registration");


        titleName.add(0,"座位数");
        titleName.add(0,"机型");
        titleName.add(0,"二字码");
        titleName.add(0, "航空公司");
        titleName.add(0, "机号");





        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //  中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 竖直居中

        HSSFSheet sheet = wb.createSheet("折扣表");


        if (!Chk.emptyCheck(data)){
            return wb;
        }


        HSSFRow idRow = sheet.createRow(0);
        idRow.setHeight((short)400);

        HSSFRow typeRow = sheet.createRow(1);
        typeRow.setHeight((short)400);
        HSSFRow subjectRow = sheet.createRow(2);
        subjectRow.setHeight((short)400);



        int cnt = 0;

        for(int i = 0 ; i < titleName.size() ; i ++ ){
            HSSFCell idCell = idRow.createCell(i);
            idCell.setCellStyle(style);
            idCell.setCellValue(title.get(i));

            String [] names = titleName.get(i).split(",");
            HSSFCell typeCell = typeRow.createCell(i);
            typeCell.setCellStyle(style);
            sheet.setColumnWidth(i,3500);
            typeCell.setCellValue(names[0]);

            if(i > 0){
                String [] prevNames = titleName.get(i - 1).split(",");
                String n = names[0];
                String pn = prevNames[0];
                int lastCol = i - 1;
                if(i == titleName.size() - 1){
                    //最后一列强制判断
                    pn = "$";
                    lastCol = i ;
                }
                if( ! n.equals(pn)){
                    sheet.addMergedRegion(new CellRangeAddress(1,1,cnt,lastCol));
                    cnt = i ;
                }
            }

            if(names.length == 1){
                //合并单元格
                sheet.addMergedRegion(new CellRangeAddress(1,2,i,i));
            }else if(names.length > 1){
                HSSFCell subjectCell = subjectRow.createCell(i);
                subjectCell.setCellStyle(style);
                subjectCell.setCellValue(names[1]);
            }
        }


        for(int i = 0 ; i < data.size() ; i ++ ){
            int rowidx = i + 3;
            HSSFRow r = sheet.createRow(rowidx);
            r.setHeight((short)300);

            Map<String,String> d = data.get(i);

            for(int j = 0 ; j < title.size() ; j ++){
                HSSFCell c = r.createCell(j);
                c.setCellStyle(style);
                c.setCellValue(d.get(title.get(j)));
            }
        }


        return wb;

    }






}
