package com.wangdbysj.dao;

import com.wangdbysj.utils.Pager;
import com.wangdbysj.model.*;
import com.wangdbysj.base.dao.BaseDao;

/**
 * * Title: CarCategoryDao
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:03:10
 */


public interface CarCategoryDao extends BaseDao<CarCategory>{
	
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	Pager<CarCategory> findPager(CarCategory carCategory);
}
