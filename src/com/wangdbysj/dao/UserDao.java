package com.wangdbysj.dao;
import com.wangdbysj.base.dao.BaseDao;
import com.wangdbysj.model.User;
import com.wangdbysj.utils.Pager;

/**
 * * Title: UserDao
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:04:57
 */


public interface UserDao extends BaseDao<User>{
	
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	Pager<User> findPager(User user);

	User getByUserName(User user);

	User login(User user);

	User getByUserNameAndQuestion(User user);

	User getByUserNameAndQuestionAndAnswer(User user);

	User getUserPhone(User user);

}
