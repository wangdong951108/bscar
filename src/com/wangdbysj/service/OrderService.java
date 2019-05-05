package com.wangdbysj.service;

import java.util.List;

import com.wangdbysj.dto.RateDto;
import com.wangdbysj.model.Order;
import com.wangdbysj.utils.Pager;

/**
 * * Title: OrderService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:36
 */
public interface OrderService extends BaseService<Order>{

	/**
	 * dao层分页查询
	 */
	Pager<Order> findPager(Order Order);

	List<RateDto> listAll();
}
