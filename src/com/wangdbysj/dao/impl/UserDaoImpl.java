package com.wangdbysj.dao.impl;
import org.hibernate.Query;

import org.springframework.stereotype.Repository;

import com.wangdbysj.model.User;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.base.dao.impl.BaseDaoImpl;
import java.util.*;
import com.wangdbysj.dao.*;

/**
 * * Title: UserDaoImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-25 下午6:58:59
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
	
 /**
  * 分页查询
  */
	@Override
	public Pager<User> findPager(User user) {
		if(user.getId() !=0 && !"".equals(user.getId() )){
	    	   String hql = "from User";
	    	   Map<String,Object> alias = new HashMap<String,Object>();
	   		   alias.put("1", "%" +user.getId()+ "%" );
	   		  return findByAlias(hql, alias);
	       }else{
	    	   String hql = "from User where 1=1 order by id desc ";
				return findByAlias(hql, null);
	       }
	}

	 /**
     * 根据用户名查询该用户是否存在
     */
	@Override
	public User getByUserName(User user) {
		String hql = "from User bean where bean.loginName =:loginName and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("loginName", user.getLoginName());
		return (User)q.uniqueResult();
	}
	
	/**
	 * 根据用户名和密码查询
	 */
	@Override
	public User login(User user) {
		String hql = "from User bean where bean.loginName =:loginName and bean.passWord= :passWord and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("loginName", user.getLoginName());
		q.setParameter("passWord", user.getPassWord());
		return (User)q.uniqueResult();
	}

	/**
	 * 根据用户名和问题查询判断该问题是否正确
	 */
	@Override
	public User getByUserNameAndQuestion(User user) {
		String hql = "from User bean where bean.loginName =:loginName and bean.question =:question and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("loginName", user.getLoginName());
		q.setParameter("question", user.getQuestion());
		return (User)q.uniqueResult();
	}


	/**
	 * 根据用户名和问题和答案查询判断该问题是否正确
	 */
	@Override
	public User getByUserNameAndQuestionAndAnswer(User user) {
		String hql = "from User bean where bean.loginName =:loginName and bean.question =:question  and bean.answer =:answer and bean.isDelete=0";
		Query q =this.getSession().createQuery(hql);
		q.setParameter("loginName", user.getLoginName());
		q.setParameter("question", user.getQuestion());
		q.setParameter("answer", user.getAnswer());
		return (User)q.uniqueResult();
	}
	/**
	 * 根据手机号查询用户是否存在
	 * */
	@Override
	public User getUserPhone(User user) {
		// TODO Auto-generated method stub
		String hql = "from User bean where bean.phone =:phone and bean.isDelete=0";
		Query q = this.getSession().createQuery(hql);
		q.setParameter("phone", user.getPhone());
		return (User)q.uniqueResult();
	}

	
}
