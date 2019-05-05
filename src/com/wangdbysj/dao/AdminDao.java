package com.wangdbysj.dao;

import com.wangdbysj.utils.Pager;
import com.wangdbysj.model.*;
import com.wangdbysj.base.dao.BaseDao;

/**
 * * Title: AdminDao
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:02:47
 */

public interface AdminDao extends BaseDao<Admin>{
	
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	Pager<Admin> findPager(Admin admin);

	Admin getByUserName(Admin admin);

	Admin login(Admin ad);
}
