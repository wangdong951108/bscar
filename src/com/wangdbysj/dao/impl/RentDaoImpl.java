package com.wangdbysj.dao.impl;

import org.springframework.stereotype.Repository;
import com.wangdbysj.model.Rent;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.base.dao.impl.BaseDaoImpl;
import java.util.*;
import com.wangdbysj.dao.*;

/**
 * * Title: RentDaoImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-18 下午3:48:41
 */

@Repository
public class RentDaoImpl extends BaseDaoImpl<Rent> implements RentDao{
	
 /**
  * 分页查询
  */
	@Override
	public Pager<Rent> findPager(Rent rent) {
		if(rent.getId() !=0 && !"".equals(rent.getId() )){
	    	   String hql = "from Rent";
	    	   Map<String,Object> alias = new HashMap<String,Object>();
	   		   alias.put("1", "%" +rent.getId()+ "%" );
	   		  return findByAlias(hql, alias);
	       }else{
	    	   String hql = "from Rent where 1=1 order by id desc ";
				return findByAlias(hql, null);
	       }
	}
	
}
