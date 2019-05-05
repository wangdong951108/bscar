package com.wangdbysj.service;

import com.wangdbysj.model.Admin;
import com.wangdbysj.utils.Pager;

/**
 * * Title: AdminService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:12
 */
public interface AdminService extends BaseService<Admin>{

	/**
	 * dao层分页查询
	 */
	Pager<Admin> findPager(Admin admin);

	Admin getByUserName(Admin admin);

	Admin login(Admin admin);
}
