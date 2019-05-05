
package com.wangdbysj.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.CarCategory;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.service.CarCategoryService;
import com.wangdbysj.dao.*;

/**
 * * Title: CarCategoryServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:06:18
 */
@Service("carCategoryService")
public class CarCategoryServiceImpl extends BaseServiceImpl<CarCategory> implements CarCategoryService{
	 
	@Autowired
	private CarCategoryDao carCategoryDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<CarCategory> findPager(CarCategory carCategory) {
		return carCategoryDao.findPager(carCategory);
	}
	

	

}
