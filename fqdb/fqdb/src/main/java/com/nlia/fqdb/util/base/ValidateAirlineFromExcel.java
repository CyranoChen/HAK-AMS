package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.AirlineMessages;
import com.nlia.fqdb.util.CommonData;

public class ValidateAirlineFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Airline validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Airline airline = new Airline();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(AirlineMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(AirlineMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("公司二字码") == null ||rowValue.get("公司二字码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "公司二字码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += AirlineMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += AirlineMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += AirlineMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = AirlineMessages.getString("SEQUENCE") + (rowNumber + 1) +  AirlineMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(AirlineMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == AirlineMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		airline.setErrorMessage(errorMessage);	
//		airline.setAirlineCode(rowValue.get("候机楼编码"));
//		airline.setTermialDescription(rowValue.get("描述说明"));
		airline.setAirlineIATACode(rowValue.get("公司二字码"));
		airline.setAirlineICAOCode(rowValue.get("公司三字码"));
		airline.setAirlineName(rowValue.get("航空公司名"));
		airline.setAirlineHomeCountry(rowValue.get("公司所属国家"));
		airline.setAirlineAllianceGroup(rowValue.get("所属联盟"));
		airline.setAirlineDescription(rowValue.get("公司描述"));
		airline.setAirlineDescription(rowValue.get("公司代理"));
		
		airline.setCreateTime(creatDate);
		airline.setModifyTime(modifyDate);
		airline.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		airline.setModifyUser(rowValue.get(AirlineMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		airline.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		airline.setVerifyDescription(verifyDescription);
				
		return airline;
	}
	
	
	/**
	 * 校验对应的列名是否相同
	 * @param index
	 * @param name
	 * @return
	 */
	public static boolean verifyColumnName(int index, String name){
		boolean isCorrectColumnName = true;
		switch (index) {
		case 0:
			if (!"公司二字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"公司三字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"航空公司名".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"公司所属国家".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"所属联盟".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"公司描述".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"公司代理".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!AirlineMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!AirlineMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!AirlineMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!AirlineMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
