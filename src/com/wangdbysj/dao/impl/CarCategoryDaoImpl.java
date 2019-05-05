package com.wangdbysj.dao.impl;

import org.springframework.stereotype.Repository;
import com.wangdbysj.model.CarCategory;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.base.dao.impl.BaseDaoImpl;
import java.util.*;
import com.wangdbysj.dao.*;

/**
 * * Title: CarCategoryDaoImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-18 下午3:47:51
 */


@Repository
public class CarCategoryDaoImpl extends BaseDaoImpl<CarCategory> implements CarCategoryDao{
	
 /**
  * 分页查询
  */
	@Override
	public Pager<CarCategory> findPager(CarCategory carCategory) {
		if(carCategory.getId() !=0 && !"".equals(carCategory.getId() )){
	    	   String hql = "from CarCategory";
	    	   Map<String,Object> alias = new HashMap<String,Object>();
	   		   alias.put("1", "%" +carCategory.getId()+ "%" );
	   		  return findByAlias(hql, alias);
	       }else{
	    	   String hql = "from CarCategory where 1=1 order by id desc ";
				return findByAlias(hql, null);
	       }
	}
	
}
