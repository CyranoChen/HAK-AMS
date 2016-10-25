package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Gate;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.GateMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateGateFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Gate validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Gate gate = new Gate();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(GateMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(GateMessages.getString("CHARGEUNIT_MODIFYTIME"));  
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
//				verifyDescription += GateMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += GateMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += GateMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = GateMessages.getString("SEQUENCE") + (rowNumber + 1) +  GateMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(GateMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == GateMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		gate.setErrorMessage(errorMessage);	
//		gate.setGateCode(rowValue.get("候机楼编码"));
//		gate.setTermialDescription(rowValue.get("描述说明"));
		gate.setGateCode(rowValue.get("号码"));
		gate.setGateTerminal(rowValue.get("所属候机楼"));
		gate.setGateType(rowValue.get("类型"));
		gate.setGateCapacity(DateUtils.StringToLong(rowValue.get("容积")));
		gate.setGateDescription(rowValue.get("描述说明"));
		gate.setGateIsTransfer(rowValue.get("是否中转登机门"));
		gate.setAssociatedGateCode(rowValue.get("关联登机门的号码"));
		
		gate.setCreateTime(creatDate);
		gate.setModifyTime(modifyDate);
		gate.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		gate.setModifyUser(rowValue.get(GateMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		gate.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		gate.setVerifyDescription(verifyDescription);
				
		return gate;
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
			if (!"所属候机楼".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"容积".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"是否中转登机门".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"关联登机门的号码".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!GateMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!GateMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!GateMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!GateMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
