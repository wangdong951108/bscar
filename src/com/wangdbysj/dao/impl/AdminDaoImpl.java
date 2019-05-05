package com.wangdbysj.dao.impl;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.wangdbysj.model.Admin;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.base.dao.impl.BaseDaoImpl;
import java.util.*;
import com.wangdbysj.dao.*;

/**
 * * Title: AdminDaoImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-18 下午3:47:25
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{
	
 /**
  * 分页查询
  */
	@Override
	public Pager<Admin> findPager(Admin admin) {
		if(admin.getId() !=0 && !"".equals(admin.getId() )){
	    	   String hql = "from Admin";
	    	   Map<String,Object> alias = new HashMap<String,Object>();
	   		   alias.put("1", "%" +admin.getId()+ "%" );
	   		  return findByAlias(hql, alias);
	       }else{
	    	   String hql = "from Admin where 1=1 order by id desc ";
				return findByAlias(hql, null);
	       }
	}

    /**
     * 根据用户名查询该用户是否存在
     */
	@Override
	public Admin getByUserName(Admin admin) {
		String hql = "from Admin bean where bean.adminName =:adminName and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("adminName", admin.getAdminName());
		return (Admin)q.uniqueResult();
	}

	/**
	 * 根据用户名和密码查询
	 */
	public Admin login(Admin admin) {
		String hql = "from Admin bean where bean.adminName =:adminName and bean.passWord= :passWord and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("adminName", admin.getAdminName());
		q.setParameter("passWord", admin.getPassWord());
		return (Admin)q.uniqueResult();
	}

	
}
