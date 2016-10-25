package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Stand;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.StandMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateStandFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Stand validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Stand stand = new Stand();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(StandMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(StandMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("机位号") == null ||rowValue.get("机位号").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "机位号不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += StandMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += StandMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += StandMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = StandMessages.getString("SEQUENCE") + (rowNumber + 1) +  StandMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(StandMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == StandMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		stand.setErrorMessage(errorMessage);	
//		stand.setStandCode(rowValue.get("候机楼编码"));
//		stand.setTermialDescription(rowValue.get("描述说明"));
		stand.setStandCode(rowValue.get("机位号"));
		stand.setStandTerminal(rowValue.get("所属候机楼"));
		stand.setStandCapacity(DateUtils.StringToLong(rowValue.get("容积")));
		stand.setStandDescription(rowValue.get("描述说明"));
		stand.setStandType(rowValue.get("类型"));
		stand.setStandMaxTailHeight(DateUtils.StringToLong(rowValue.get("最大装箱高度")));
		stand.setStandMaxWeight(DateUtils.StringToLong(rowValue.get("最大承载重量")));
		stand.setStandBridgesNumber(DateUtils.StringToLong(rowValue.get("桥位数量")));
		stand.setStandBoardingType(rowValue.get("登机类型"));
		stand.setStandTow(rowValue.get("牵引信息"));
		stand.setCreateTime(creatDate);
		stand.setModifyTime(modifyDate);
		stand.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		stand.setModifyUser(rowValue.get(StandMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		stand.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		stand.setVerifyDescription(verifyDescription);
				
		return stand;
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
			if (!"机位号".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"所属候机楼".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"容积".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"最大装箱高度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 6:
			if (!"最大承载重量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 7:
			if (!"桥位数量".equals(name))  
				isCorrectColumnName = false;
			break;
		case 8:
			if (!"登机类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 9:
			if (!"牵引信息".equals(name))  
				isCorrectColumnName = false;
			break;
		}

		return isCorrectColumnName;
	}
}
