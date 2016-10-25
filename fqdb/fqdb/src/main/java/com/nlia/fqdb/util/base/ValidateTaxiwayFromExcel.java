package com.nlia.fqdb.util.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Taxiway;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.message.base.TaxiwayMessages;
import com.nlia.fqdb.util.CommonData;

public class ValidateTaxiwayFromExcel {

	
	/**
	 * 校验单条记录数据，并返回对象
	 * @param rowValue
	 * @return
	 */
	public static Taxiway validateSingleData(Map<String, String> rowValue,int rowNumber) {
		
//		final String INTERNATIONAL = "1";
//		final String DOMESTIC = "0";
		
		Taxiway taxiway = new Taxiway();
		String verifyDescription = "";  
		Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
		List<Integer> errorCells = new ArrayList<>();
		
//		String createTime = rowValue.get(TaxiwayMessages.getString("CHARGEUNIT_CREATETIME"));  
//		String modifyTime = rowValue.get(TaxiwayMessages.getString("CHARGEUNIT_MODIFYTIME"));  
		Date creatDate = new Date();
		Date modifyDate = null;
		
		//判断项目名称是否为空
		if(rowValue.get("候机楼编码") == null ||rowValue.get("候机楼编码").isEmpty()){  
			errorCells.add(0);
			verifyDescription += "候机楼编码不能为空";  
		}
		
//		//判断创建时间是否为空
//		if(createTime != null && !"".equals(createTime)){  
//			if(!DateUtils.isValidDate(createTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(8);
//				verifyDescription += TaxiwayMessages.getString("CREATETIME_FORMAT_ERROR");  
//			}else{
//				creatDate = DateUtils.String2Date(createTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断修改时间是否为空
//		if(modifyTime != null && !"".equals(modifyTime)){  
//			if(!DateUtils.isValidDate(modifyTime, "yyyy-MM-dd HH:mm")){  
//				errorCells.add(10);
//				verifyDescription += TaxiwayMessages.getString("MODIFYTIME_FORMAT_ERROR");  
//			}else{
//				modifyDate = DateUtils.String2Date(modifyTime, "yyyy-MM-dd HH:mm");  
//			}
//		}
//		
//		//判断创建时间和修改时间顺序是否有误
//		if (creatDate != null && modifyDate != null && creatDate.getTime() > modifyDate.getTime()) {	
//			errorCells.add(8);
//			errorCells.add(10);
//			verifyDescription += TaxiwayMessages.getString("CREATETIME_MODIFYTIME_INCORRECT");  
//		}
		
		
		if(!verifyDescription.equals("")){  
			//verifyDescription = MessageFormat.format("第{0}行:{1}", rowNumber,verifyDescription);  
			verifyDescription = TaxiwayMessages.getString("SEQUENCE") + (rowNumber + 1) +  TaxiwayMessages.getString("ROW") + verifyDescription;
		}
//		String isDomestic = rowValue.get(TaxiwayMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC"));  
//		if(isDomestic == TaxiwayMessages.getString("CHARGEUNIT_INTERNATIONAL")){  
//			isDomestic = INTERNATIONAL;  
//		}else{
//			isDomestic = DOMESTIC;  
//		}
		
		errorMessage.put(rowNumber, errorCells);
		taxiway.setErrorMessage(errorMessage);	
//		taxiway.setTaxiwayCode(rowValue.get("候机楼编码"));
//		taxiway.setTermialDescription(rowValue.get("描述说明"));
		taxiway.setCreateTime(creatDate);
		taxiway.setModifyTime(modifyDate);
		taxiway.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));  
		taxiway.setModifyUser(rowValue.get(TaxiwayMessages.getString("CHARGEUNIT_MODIFYUSER")));  
		taxiway.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
		taxiway.setVerifyDescription(verifyDescription);
				
		return taxiway;
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
			if (!"候机楼编码".equals(name))  
				isCorrectColumnName = false;
			break;
		case 1:
			if (!"描述说明".equals(name))  
				isCorrectColumnName = false;
			break;
//		case 2:
//			if (!TaxiwayMessages.getString("CHARGEUNIT_UNITDESCRIBE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 3:
//			if (!TaxiwayMessages.getString("CHARGEUNIT_CURRENCY").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 4:
//			if (!TaxiwayMessages.getString("CHARGEUNIT_CHARGETYPE").equals(name))  
//				isCorrectColumnName = false;
//			break;
//		case 5:
//			if (!TaxiwayMessages.getString("CHARGEUNIT_INTERNATIONAL_OR_DOMESTIC").equals(name))  
//				isCorrectColumnName = false;
//			break;
		}

		return isCorrectColumnName;
	}
}
