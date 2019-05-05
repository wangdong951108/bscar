
package com.wangdbysj.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.Order;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.service.OrderService;
import com.wangdbysj.dao.*;
import com.wangdbysj.dto.RateDto;

/**
 * * Title: OrderServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:06:41
 */

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{
	 
	@Autowired
	private OrderDao orderDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<Order> findPager(Order order) {
		return orderDao.findPager(order);
	}
	@Override
	public List<RateDto> listAll() {
		// TODO Auto-generated method stub
		return orderDao.listAll();
	}
	

	

}
