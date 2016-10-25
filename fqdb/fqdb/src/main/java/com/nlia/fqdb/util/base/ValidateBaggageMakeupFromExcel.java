package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.BaggageMakeup;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.BaggageMakeupMessages;
import com.nlia.fqdb.util.CommonData;

public class ValidateBaggageMakeupFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static BaggageMakeup validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		BaggageMakeup baggageMakeup = new BaggageMakeup();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(BaggageMakeupMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(BaggageMakeupMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("行李分拣转盘号") == null ||rowValue.get("行李分拣转盘号").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "行李分拣转盘号不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += BaggageMakeupMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += BaggageMakeupMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += BaggageMakeupMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = BaggageMakeupMessages.getString("SEQUENCE") + (rowNumber + 1) +  BaggageMakeupMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(BaggageMakeupMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == BaggageMakeupMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		baggageMakeup.setErrorMessage(errorMessage);	
//		baggageMakeup.setBaggageMakeupCode(rowValue.get("候机楼编码"));
//		baggageMakeup.setTermialDescription(rowValue.get("描述说明"));
		baggageMakeup.setBaggageMakeupCode(rowValue.get("行李分拣转盘号"));
		baggageMakeup.setBaggageTerminal(rowValue.get("所属候机楼"));
		baggageMakeup.setBaggageDescription(rowValue.get("分拣转盘描述"));
		baggageMakeup.setBaggageMakeupCapacity(rowValue.get("分拣转盘容积"));
		baggageMakeup.setCreateTime(creatDate);
		baggageMakeup.setModifyTime(modifyDate);
		baggageMakeup.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		baggageMakeup.setModifyUser(rowValue.get(BaggageMakeupMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		baggageMakeup.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		baggageMakeup.setVerifyDescription(verifyDescription);
				
		return baggageMakeup;
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
			if (!"行李分拣转盘号".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"所属候机楼".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"分拣转盘描述".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"分拣转盘容积".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!BaggageMakeupMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!BaggageMakeupMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!BaggageMakeupMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!BaggageMakeupMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
