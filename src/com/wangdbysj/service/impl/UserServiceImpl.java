
package com.wangdbysj.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.User;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.service.UserService;
import com.wangdbysj.dao.*;

/**
 * * Title: UserServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:01
 */

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	 
	@Autowired
	private UserDao userDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<User> findPager(User user) {
		return userDao.findPager(user);
	}
	@Override
	public User getByUserName(User user) {
		// TODO Auto-generated method stub
		return userDao.getByUserName(user);
	}
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return userDao.login(user);
	}
	@Override
	public User getByUserNameAndQuestion(User user) {
		// TODO Auto-generated method stub
		return userDao.getByUserNameAndQuestion(user);
	}
	@Override
	public User getByUserNameAndQuestionAndAnswer(User user) {
		// TODO Auto-generated method stub
		return userDao.getByUserNameAndQuestionAndAnswer(user);
	}
	@Override
	public User getUserPhone(User user) {
		// TODO Auto-generated method stub
		return userDao.getUserPhone(user);
	}
	
}
