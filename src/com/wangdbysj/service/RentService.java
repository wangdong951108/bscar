package com.wangdbysj.service;

import com.wangdbysj.model.Rent;
import com.wangdbysj.utils.Pager;

/**
 * * Title: RentService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:43
 */
public interface RentService extends BaseService<Rent>{

	/**
	 * dao层分页查询
	 */
	Pager<Rent> findPager(Rent rent);
}
