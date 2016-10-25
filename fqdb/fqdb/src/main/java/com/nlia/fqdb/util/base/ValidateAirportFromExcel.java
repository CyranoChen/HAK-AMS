package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.AirportMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateAirportFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Airport validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Airport airport = new Airport();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(AirportMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(AirportMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("机场三字码") == null ||rowValue.get("机场三字码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "机场三字码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += AirportMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += AirportMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += AirportMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = AirportMessages.getString("SEQUENCE") + (rowNumber + 1) +  AirportMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(AirportMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == AirportMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		airport.setErrorMessage(errorMessage);	
//		airport.setAirportCode(rowValue.get("候机楼编码"));
//		airport.setTermialDescription(rowValue.get("描述说明"));
		airport.setAirportIATACode(rowValue.get("机场三字码"));
		airport.setAirportICAOCode(rowValue.get("机场四字码"));
		airport.setAirportCountry(rowValue.get("机场所在国家"));
		airport.setAirportCountryType(rowValue.get("机场国家类型"));
		airport.setAirportCity(rowValue.get("机场所在城市"));
		airport.setAirportRegion(rowValue.get("机场所在区域"));
		airport.setAirportTimezone(rowValue.get("机场时区"));
		airport.setAirportDistance(DateUtils.StringToLong((rowValue.get("机场距离"))));
		airport.setAirportDescription(rowValue.get("机场描述"));
		
		airport.setCreateTime(creatDate);
		airport.setModifyTime(modifyDate);
		airport.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		airport.setModifyUser(rowValue.get(AirportMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		airport.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		airport.setVerifyDescription(verifyDescription);
				
		return airport;
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
			if (!"机场三字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"机场四字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"机场所在国家".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"机场国家类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"机场所在城市".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"机场所在区域".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"机场时区".equals(name))  
				isCorrectColumnName = false;
			break;
		case 7:
			if (!"机场距离".equals(name))  
				isCorrectColumnName = false;
			break;
		case 8:
			if (!"机场描述".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!AirportMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!AirportMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!AirportMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!AirportMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
