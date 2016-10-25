package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.BaggageReclaim;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.BaggageReclaimMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateBaggageReclaimFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static BaggageReclaim validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		BaggageReclaim baggageReclaim = new BaggageReclaim();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(BaggageReclaimMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(BaggageReclaimMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("行李提取转盘号") == null ||rowValue.get("行李提取转盘号").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "行李提取转盘号不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += BaggageReclaimMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += BaggageReclaimMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += BaggageReclaimMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = BaggageReclaimMessages.getString("SEQUENCE") + (rowNumber + 1) +  BaggageReclaimMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(BaggageReclaimMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == BaggageReclaimMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		baggageReclaim.setErrorMessage(errorMessage);	
//		baggageReclaim.setBaggageReclaimCode(rowValue.get("候机楼编码"));
//		baggageReclaim.setTermialDescription(rowValue.get("描述说明"));
		baggageReclaim.setBaggageReclaimCode(rowValue.get("行李提取转盘号"));
		baggageReclaim.setBaggageReclaimTerminal(rowValue.get("转盘所属候机楼"));
		baggageReclaim.setBaggageReclaimCapacity(DateUtils.StringToLong((rowValue.get("提取转盘容积"))));
		baggageReclaim.setBaggageReclaimDescription(rowValue.get("转盘描述说明"));
		baggageReclaim.setBaggageMakeupArea(rowValue.get("转盘所在区域"));
		baggageReclaim.setCreateTime(creatDate);
		baggageReclaim.setModifyTime(modifyDate);
		baggageReclaim.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		baggageReclaim.setModifyUser(rowValue.get(BaggageReclaimMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		baggageReclaim.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		baggageReclaim.setVerifyDescription(verifyDescription);
				
		return baggageReclaim;
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
			if (!"行李提取转盘号".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"转盘所属候机楼".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"提取转盘容积".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"转盘描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"转盘所在区域".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!BaggageReclaimMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!BaggageReclaimMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!BaggageReclaimMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!BaggageReclaimMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
