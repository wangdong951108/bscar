
package com.wangdbysj.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.Rent;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.service.RentService;
import com.wangdbysj.dao.*;

/**
 * * Title: RentServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:06:51
 */

@Service("rentService")
public class RentServiceImpl extends BaseServiceImpl<Rent> implements RentService{
	 
	@Autowired
	private RentDao rentDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<Rent> findPager(Rent rent) {
		return rentDao.findPager(rent);
	}
	

	

}
