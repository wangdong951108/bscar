package com.wangdbysj.service;

import com.wangdbysj.model.User;
import com.wangdbysj.utils.Pager;

/**
 * * Title: UserService
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:07:48
 */
public interface UserService extends BaseService<User>{

	/**
	 * dao层分页查询
	 */
	Pager<User> findPager(User user);

	User getByUserName(User user);

	User login(User user);

	User getByUserNameAndQuestion(User user);

	User getByUserNameAndQuestionAndAnswer(User user);
	
	User getUserPhone(User user);
}
