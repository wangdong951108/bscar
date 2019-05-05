package com.wangdbysj.service;

import com.wangdbysj.model.CarCategory;
import com.wangdbysj.utils.Pager;

/**
 * * Title: CarCategoryService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:23
 */
public interface CarCategoryService extends BaseService<CarCategory>{

	/**
	 * dao层分页查询
	 */
	Pager<CarCategory> findPager(CarCategory carCategory);
}
