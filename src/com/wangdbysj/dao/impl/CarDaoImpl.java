package com.wangdbysj.dao.impl;

import org.springframework.stereotype.Repository;
import com.wangdbysj.model.Car;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.base.dao.impl.BaseDaoImpl;
import java.util.*;
import com.wangdbysj.dao.*;

/**
 * * Title: CarDaoImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-18 下午3:48:14
 */


@Repository
public class CarDaoImpl extends BaseDaoImpl<Car> implements CarDao{
	
 /**
  * 分页查询
  */
	@Override
	public Pager<Car> findPager(Car Car) {
		if(Car.getId() !=0 && !"".equals(Car.getId() )){
	    	   String hql = "from Car";
	    	   Map<String,Object> alias = new HashMap<String,Object>();
	   		   alias.put("1", "%" +Car.getId()+ "%" );
	   		  return findByAlias(hql, alias);
	       }else{
	    	   String hql = "from Car where 1=1 order by id desc ";
				return findByAlias(hql, null);
	       }
	}
	
}
