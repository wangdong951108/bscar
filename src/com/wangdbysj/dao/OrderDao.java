package com.wangdbysj.dao;
import com.wangdbysj.utils.Pager;

import java.util.List;

import com.wangdbysj.base.dao.BaseDao;
import com.wangdbysj.dto.RateDto;
import com.wangdbysj.model.*;

/**
 * * Title: OrderDao
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:04:26
 */


public interface OrderDao extends BaseDao<Order>{
	
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	Pager<Order> findPager(Order order);

	List<RateDto> listAll();
}
