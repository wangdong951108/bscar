package com.wangdbysj.dao;
import com.wangdbysj.base.dao.BaseDao;
import com.wangdbysj.model.Rent;
import com.wangdbysj.utils.Pager;

/**
 * * Title: RentDao
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:04:43
 */

public interface RentDao extends BaseDao<Rent>{
	
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	Pager<Rent> findPager(Rent rent);
}
