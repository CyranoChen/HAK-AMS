package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Handler;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.HandlerMessages;
import com.nlia.fqdb.util.CommonData;

public class ValidateHandlerFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Handler validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Handler handler = new Handler();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(HandlerMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(HandlerMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("号码") == null ||rowValue.get("号码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "号码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += HandlerMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += HandlerMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += HandlerMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = HandlerMessages.getString("SEQUENCE") + (rowNumber + 1) +  HandlerMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(HandlerMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == HandlerMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		handler.setErrorMessage(errorMessage);	
//		handler.setHandlerCode(rowValue.get("候机楼编码"));
//		handler.setTermialDescription(rowValue.get("描述说明"));
		handler.setHandlerCode(rowValue.get("号码"));
		handler.setHandlerName(rowValue.get("名称"));
		handler.setHandlerDescription(rowValue.get("描述说明"));
		handler.setCreateTime(creatDate);
		handler.setModifyTime(modifyDate);
		handler.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		handler.setModifyUser(rowValue.get(HandlerMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		handler.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		handler.setVerifyDescription(verifyDescription);
				
		return handler;
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
			if (!"号码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"名称".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!HandlerMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!HandlerMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!HandlerMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!HandlerMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
