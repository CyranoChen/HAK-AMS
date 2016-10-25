package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.CheckInDesk;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.CheckInDeskMessages;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;

public class ValidateCheckInDeskFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static CheckInDesk validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		CheckInDesk checkInDesk = new CheckInDesk();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(CheckInDeskMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(CheckInDeskMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("值机柜台编码") == null ||rowValue.get("值机柜台编码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "值机柜台编码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += CheckInDeskMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += CheckInDeskMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += CheckInDeskMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = CheckInDeskMessages.getString("SEQUENCE") + (rowNumber + 1) +  CheckInDeskMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(CheckInDeskMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == CheckInDeskMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		errorMessage.put(rowNumber, errorCells);
		checkInDesk.setErrorMessage(errorMessage);	
//		checkInDesk.setCheckInDeskCode(rowValue.get("候机楼编码"));
//		checkInDesk.setTermialDescription(rowValue.get("描述说明"));
		checkInDesk.setCheckInDeskCode(rowValue.get("值机柜台编码"));
		checkInDesk.setCheckInDeskTerminal(rowValue.get("柜台属于候机楼"));
		checkInDesk.setCheckInDeskType(rowValue.get("柜台类型"));
		checkInDesk.setCheckInDeskDescription(rowValue.get("柜台描述"));
		checkInDesk.setCheckInDeskCapacity(DateUtils.StringToLong(rowValue.get("柜台容积")));
		checkInDesk.setCheckInDeskServiceType(rowValue.get("柜台服务类型"));
		
		checkInDesk.setCreateTime(creatDate);
		checkInDesk.setModifyTime(modifyDate);
		checkInDesk.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		checkInDesk.setModifyUser(rowValue.get(CheckInDeskMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		checkInDesk.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		checkInDesk.setVerifyDescription(verifyDescription);
				
		return checkInDesk;
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
			if (!"值机柜台编码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"柜台属于候机楼".equals(name))  
				isCorrectColumnName = false;
			break;
		case 2:
			if (!"柜台类型".equals(name))  
				isCorrectColumnName = false;
			break;
		case 3:
			if (!"柜台描述".equals(name))  
				isCorrectColumnName = false;
			break;
		case 4:
			if (!"柜台容积".equals(name))  
				isCorrectColumnName = false;
			break;
		case 5:
			if (!"柜台服务类型".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!CheckInDeskMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!CheckInDeskMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!CheckInDeskMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!CheckInDeskMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
