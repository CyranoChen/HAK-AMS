package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.AircraftType;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.AircraftTypeMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateAircraftTypeFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static AircraftType validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		AircraftType aircraftType = new AircraftType();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(AircraftTypeMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(AircraftTypeMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("机型二字码") == null ||rowValue.get("机型二字码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "机型二字码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += AircraftTypeMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += AircraftTypeMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += AircraftTypeMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = AircraftTypeMessages.getString("SEQUENCE") + (rowNumber + 1) +  AircraftTypeMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(AircraftTypeMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == AircraftTypeMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		
		errorMessage.put(rowNumber, errorCells);
		aircraftType.setErrorMessage(errorMessage);	
//		aircraftType.setAircraftTypeCode(rowValue.get("候机楼编码"));
//		aircraftType.setTermialDescription(rowValue.get("描述说明"));
		aircraftType.setAircraftTypeIATACode(rowValue.get("机型二字码"));
		aircraftType.setAircraftTypeICAOCode(rowValue.get("机型三字码"));
		aircraftType.setAircraftSubTypeIATACode(rowValue.get("机型子类二字码"));
		aircraftType.setAircraftManufactory(rowValue.get("飞机制造商"));
		aircraftType.setAircraftEngineNumber(DateUtils.StringToInt((rowValue.get("飞机引擎数"))));
		aircraftType.setAircraftHeight(DateUtils.StringToInt((rowValue.get("高度"))));
		aircraftType.setAircraftLength(DateUtils.StringToInt((rowValue.get("长度"))));
		aircraftType.setAircraftWidth(DateUtils.StringToInt((rowValue.get("宽度"))));
		aircraftType.setAircraftTakeoffWeight(DateUtils.StringToInt((rowValue.get("飞机起飞重量"))));
		
		aircraftType.setAircraftSeatCapacity(DateUtils.StringToInt((rowValue.get("飞机容量"))));
		aircraftType.setAircraftPayload(DateUtils.StringToInt((rowValue.get("负载"))));
		aircraftType.setAircraftLandingWeight(DateUtils.StringToInt((rowValue.get("飞机着陆重量"))));
		aircraftType.setAircraftTypeDescription(rowValue.get("机型描述"));
		aircraftType.setAircraftNoiseCategory(rowValue.get("噪声级别"));
		aircraftType.setBoardingBridgeRequired(rowValue.get("登机廊桥要求"));
		aircraftType.setAircraftMode(rowValue.get("飞机模式"));
		aircraftType.setAircraftSizaCategory(rowValue.get("飞机尺寸类别"));
		
		aircraftType.setCreateTime(creatDate);
		aircraftType.setModifyTime(modifyDate);
		aircraftType.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		aircraftType.setModifyUser(rowValue.get(AircraftTypeMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		aircraftType.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		aircraftType.setVerifyDescription(verifyDescription);
				
		return aircraftType;
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
			if (!"机型二字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"机型三字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"机型子类二字码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"飞机制造商".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"飞机引擎数".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"高度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"长度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 7:
			if (!"宽度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 8:
			if (!"飞机起飞重量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 9:
			if (!"飞机容量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 10:
			if (!"负载".equals(name))  
				isCorrectColumnName = false;
			break;
		case 11:
			if (!"飞机着陆重量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 12:
			if (!"机型描述".equals(name))  
				isCorrectColumnName = false;
			break;
		case 13:
			if (!"噪声级别".equals(name))  
				isCorrectColumnName = false;
			break;
		case 14:
			if (!"登机廊桥要求".equals(name))  
				isCorrectColumnName = false;
			break;
		case 15:
			if (!"飞机模式".equals(name))  
				isCorrectColumnName = false;
			break;
		case 16:
			if (!"飞机尺寸类别".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!AircraftTypeMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!AircraftTypeMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!AircraftTypeMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!AircraftTypeMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
