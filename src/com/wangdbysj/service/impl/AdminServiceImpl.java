
package com.wangdbysj.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wangdbysj.model.Admin;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.dao.*;
import com.wangdbysj.service.*;


/**
 * * Title: AdminServiceImpl
* Description:  
* Company: 
* @author wangdong
* @date  2019-3-1 下午4:05:47
 */
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
	 
	@Autowired
	private AdminDao adminDao;
	/**
	 * dao分页查询
	 * @param user
	 * @return
	 */
	@Override
	public Pager<Admin> findPager(Admin admin) {
		return adminDao.findPager(admin);
	}
	
	@Override
	public Admin getByUserName(Admin admin) {
		// TODO Auto-generated method stub
		return adminDao.getByUserName(admin);
	}
	
	
	@Override
	public Admin login(Admin admin) {
	 Admin ad = new Admin();
	 ad.setAdminName(admin.getAdminName());
	 ad.setPassWord(admin.getPassWord());
	return adminDao.login(ad);
}
	

	

}
