package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.DelayCode;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.DelayCodeMessages;
import com.nlia.fqdb.util.CommonData;

public class ValidateDelayCodeFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static DelayCode validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		DelayCode delayCode = new DelayCode();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(DelayCodeMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(DelayCodeMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("延迟编码") == null ||rowValue.get("延迟编码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "延迟编码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += DelayCodeMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += DelayCodeMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += DelayCodeMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = DelayCodeMessages.getString("SEQUENCE") + (rowNumber + 1) +  DelayCodeMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(DelayCodeMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == DelayCodeMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		delayCode.setErrorMessage(errorMessage);	
//		delayCode.setDelayCodeCode(rowValue.get("候机楼编码"));
//		delayCode.setTermialDescription(rowValue.get("描述说明"));
		delayCode.setDelayCode(rowValue.get("延迟编码"));
		delayCode.setDelayReason(rowValue.get("延迟原因"));
		delayCode.setDelaySubCode(rowValue.get("延迟子码"));
		delayCode.setDelaySubReason(rowValue.get("延迟子码原因"));
		delayCode.setCreateTime(creatDate);
		delayCode.setModifyTime(modifyDate);
		delayCode.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		delayCode.setModifyUser(rowValue.get(DelayCodeMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		delayCode.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		delayCode.setVerifyDescription(verifyDescription);
				
		return delayCode;
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
			if (!"延迟编码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"延迟原因".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"延迟子码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"延迟子码原因".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!DelayCodeMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!DelayCodeMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!DelayCodeMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!DelayCodeMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
