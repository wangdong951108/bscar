package com.wangdbysj.service;

import com.wangdbysj.model.Car;
import com.wangdbysj.utils.Pager;

/**
 * * Title: CarService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:30
 */
public interface CarService extends BaseService<Car>{

	/**
	 * dao层分页查询
	 */
	Pager<Car> findPager(Car Car);
}
