package com.nlia.fqdb.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.ChargeUnit;
import com.nlia.fqdb.message.ChargeUnitMessages;
import com.nlia.fqdb.message.FQDBMessages;

public class ValidateChargeUnitFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static ChargeUnit validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
		final String INTERNATIONAL = "1";
		final String DOMESTIC = "0";
		
		ChargeUnit chargeUnit = new ChargeUnit();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_NAME")) == null ||rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_NAME")).isEmpty()){  
			errorCells.add(0);
			verifyDescription += ChargeUnitMessages.getString("NAME_ISNULL_NOT_ALLOWED");  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += ChargeUnitMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += ChargeUnitMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += ChargeUnitMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = ChargeUnitMessages.getString("SEQUENCE") + (rowNumber + 1) +  ChargeUnitMessages.getString("ROW") + verifyDescription;
		}
		String isDomestic = rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
		if(isDomestic == ChargeUnitMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
			isDomestic = INTERNATIONAL;  
		}else{
			isDomestic = DOMESTIC;  
		}
		
		errorMessage.put(rowNumber, errorCells);
		chargeUnit.setErrorMessage(errorMessage);	
		chargeUnit.setName(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_NAME")));  
		chargeUnit.setDescribe(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_DESCRIBE")));  
		chargeUnit.setUnitdescribe(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_UNITDESCRIBE")));  
		chargeUnit.setCurrency(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_CURRENCY")));  
		chargeUnit.setChargeType(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_CHARGETYPE")));  
		chargeUnit.setIsDomestic(isDomestic);
		chargeUnit.setCreateTime(creatDate);
		chargeUnit.setModifyTime(modifyDate);
		chargeUnit.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		chargeUnit.setModifyUser(rowValue.get(ChargeUnitMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		chargeUnit.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		chargeUnit.setVerifyDescription(verifyDescription);
				
		return chargeUnit;
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
			if (!ChargeUnitMessages.getString("CHARGEUNIT_NAME").equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!ChargeUnitMessages.getString("CHARGEUNIT_DESCRIBE").equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!ChargeUnitMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!ChargeUnitMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!ChargeUnitMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!ChargeUnitMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
				isCorrectColumnName = false;
			break;
		}

		return isCorrectColumnName;
	}
}
