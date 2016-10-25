package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Runway;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.RunwayMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateRunwayFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Runway validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Runway runway = new Runway();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(RunwayMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(RunwayMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("跑道号") == null ||rowValue.get("跑道号").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "跑道号不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += RunwayMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += RunwayMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += RunwayMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = RunwayMessages.getString("SEQUENCE") + (rowNumber + 1) +  RunwayMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(RunwayMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == RunwayMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		runway.setErrorMessage(errorMessage);	
//		runway.setRunwayCode(rowValue.get("候机楼编码"));
//		runway.setTermialDescription(rowValue.get("描述说明"));
		runway.setRunwayCode(rowValue.get("跑道号"));
		runway.setRunwayDescription(rowValue.get("描述说明"));
		runway.setRunwayLength(DateUtils.StringToLong(rowValue.get("长度")));
		runway.setRunwayWidth(DateUtils.StringToLong(rowValue.get("宽度")));
		runway.setCreateTime(creatDate);
		runway.setModifyTime(modifyDate);
		runway.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		runway.setModifyUser(rowValue.get(RunwayMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		runway.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		runway.setVerifyDescription(verifyDescription);
				
		return runway;
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
			if (!"跑道号".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"长度".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"宽度".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 4:
//			if (!RunwayMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!RunwayMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
