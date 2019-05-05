
package com.wangdbysj.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.Car;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.service.CarService;
import com.wangdbysj.dao.*;

/**
 * * Title: CarServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:06:30
 */

@Service("carService")
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService{
	 
	@Autowired
	private CarDao carDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<Car> findPager(Car car) {
		return carDao.findPager(car);
	}
	

	

}
