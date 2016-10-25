package com.wonders.frame.ams.service.permission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.utils.ArrayUtil;
import com.wonders.frame.ams.utils.Chk;

@Service
public class UserManagerService {
	
	@Autowired
	private BaseDao baseDao;
	

	public List<UserDto> queryUser(UserDto u){
		if(u == null){
			u = new UserDto();
		}

		String sql = " select a.*, y.id role_id, y.name role_name " +
							"  from af_user a,af_role y, af_relation z " +
							"  where a.id = z.n_id" +
							"  and z.n_type = 'user'" +
							"  and z.p_type = 'role'" +
							"  and z.p_id = y.id" +
							"  and a.removed = 0" +
							"  and y.removed = 0" +
							"  and z.removed = 0" ;
		sql = " select * from ( " + sql + "  )  where 1 = 1";
		
		List<Object> p = new ArrayList<Object>();
		
		if(Chk.spaceCheck(u.getId())){
			sql += " and id = ? ";
			p.add(u.getId());
		}
		
		if(Chk.spaceCheck(u.getName())){
			sql += " and name like ? ";
			p.add("%" + u.getName() + "%");
		}
		
		if(Chk.spaceCheck(u.getLoginName())){
			sql += " and login_name like ? ";
			p.add("%" + u.getLoginName() + "%");
		}
		
		if(Chk.spaceCheck(u.getGender())){
			sql += " and gender = ? ";
			p.add(u.getGender());
		}

		
		if(Chk.spaceCheck(u.getRoleId())){
			sql += " and role_id = ? ";
			p.add(u.getRoleId());
		}
		
		return baseDao.queryForList(sql, UserDto.class, ArrayUtil.toArray(p, Object.class));
		
		
		
		
		
	}
}
