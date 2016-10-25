package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Aircraft;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.AircraftMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateAircraftFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Aircraft validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Aircraft aircraft = new Aircraft();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(AircraftMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(AircraftMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("飞机注册号") == null ||rowValue.get("飞机注册号").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "飞机注册号不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += AircraftMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += AircraftMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += AircraftMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = AircraftMessages.getString("SEQUENCE") + (rowNumber + 1) +  AircraftMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(AircraftMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == AircraftMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		aircraft.setErrorMessage(errorMessage);	
//		aircraft.setAircraftCode(rowValue.get("候机楼编码"));
//		aircraft.setTermialDescription(rowValue.get("描述说明"));
		aircraft.setAircraftRegistration(rowValue.get("飞机注册号"));
		aircraft.setAircraftType(rowValue.get("飞机类型"));
		aircraft.setAircraftAirline(rowValue.get("所属航空公司"));
		aircraft.setAircraftDescription(rowValue.get("描述说明"));
		aircraft.setAircraftEngineNumber(DateUtils.StringToLong((rowValue.get("引擎数量"))));
		aircraft.setAircraftTakeoffWeight(DateUtils.StringToLong((rowValue.get("最大起飞重量"))));
		aircraft.setAircraftLandingWeight(DateUtils.StringToLong((rowValue.get("最大落地重量"))));
		aircraft.setAircraftPayload(DateUtils.StringToLong((rowValue.get("最大配载数"))));
		aircraft.setAircraftSeatCapacity(DateUtils.StringToLong((rowValue.get("最大客座数"))));
		aircraft.setAircraftNoiseCategory(rowValue.get("噪音级别"));
		aircraft.setAircraftHeight(DateUtils.StringToLong((rowValue.get("高度"))));
		aircraft.setAircraftLength(DateUtils.StringToLong((rowValue.get("长度"))));
		aircraft.setAircraftWidth(DateUtils.StringToLong((rowValue.get("宽度"))));
		
		aircraft.setCreateTime(creatDate);
		aircraft.setModifyTime(modifyDate);
		aircraft.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		aircraft.setModifyUser(rowValue.get(AircraftMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		aircraft.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		aircraft.setVerifyDescription(verifyDescription);
				
		return aircraft;
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
			if (!"飞机注册号".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"飞机类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"所属航空公司".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"引擎数量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"最大起飞重量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"最大落地重量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 7:
			if (!"最大配载数".equals(name))  
				isCorrectColumnName = false;
			break;
		case 8:
			if (!"最大客座数".equals(name))  
				isCorrectColumnName = false;
			break;
		case 9:
			if (!"噪音级别".equals(name))  
				isCorrectColumnName = false;
			break;
		case 10:
			if (!"高度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 11:
			if (!"长度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 12:
			if (!"宽度".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!AircraftMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!AircraftMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!AircraftMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!AircraftMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
