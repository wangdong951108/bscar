package com.wangdbysj.action;

/**
 * 和登陆有关的都在这里
 */

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wangdbysj.model.Car;
import com.wangdbysj.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * Title: LoginAction
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-25 下午7:17:58
 */



@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Integer role;// 传入参数值

	private String keyword;// 搜索关键字

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Autowired
	private CarService carService;

	// 注册页面(只有前台有注册)
	public String register() {
		return "userRegister";
	}

	// 登陆页面
	public String login() {
		if (role == 1) {
			return "userLogin";
		} else {
			return "manageLogin";
		}
	}

	/**
	 * 跳转到忘记密码页面
	 * 
	 * @return
	 */
	public String forgetPassWordNew() {
		return SUCCESS;

	}

	// 退出
	public String tuichu() {
		if (role == 1) {
			ActionContext ac = ActionContext.getContext();
			@SuppressWarnings("rawtypes")
			Map session = ac.getSession();
			session.remove("loginName");
			session.remove("userId");
			session.remove("user");
			return "userLogin";
		} else {
			ActionContext ac = ActionContext.getContext();
			@SuppressWarnings("rawtypes")
			Map session = ac.getSession();
			session.remove("adminName");
			session.remove("adminId");
			return "manageLogin";
		}
	}

	// 登录成功进入首页
	public String index() {
		if (role == 1) {
			// 最新车列表
			List<Car> newestList = new ArrayList<Car>();
			Map<String, Object> alias1 = new HashMap<String, Object>();
			String hql1 = "from Car where 1=1  and isDelete=0 ";
			if (!StringUtils.isEmpty(keyword)) {
				hql1 += " and carType like :carType";
				alias1.put("carType", "%" + keyword + "%");
			}
			hql1 += "  order by id desc";
			newestList = carService.getByHQL(hql1, alias1);
			if (newestList != null && newestList.size() > 0) {
				if (newestList.size() <= 8) {
					ActionContext.getContext().put("newestList", newestList);
				} else {
					ActionContext.getContext().put("newestList",
							newestList.subList(0, 8));
				}
			} else {
				ActionContext.getContext().put("newestList", newestList);
			}

			// 好车推荐列表
			List<Car> recommendList = new ArrayList<Car>();
			Map<String, Object> alias2 = new HashMap<String, Object>();
			String hql2 = "from Car where 1=1 and isRecommend=1 and isDelete=0 ";
			if (!StringUtils.isEmpty(keyword)) {
				hql2 += " and carType like :carType";
				alias2.put("carType", "%" + keyword + "%");
			}
			hql2 += "  order by id desc";
			recommendList = carService.getByHQL(hql2, alias2);
			if (recommendList != null && recommendList.size() > 0) {
				if (recommendList.size() <= 8) {
					ActionContext.getContext().put("recommendList",
							recommendList);
				} else {
					ActionContext.getContext().put("recommendList",
							recommendList.subList(0, 8));
				}
			} else {
				ActionContext.getContext().put("recommendList", recommendList);
			}

			// 折扣优惠列表
			List<Car> discountList = new ArrayList<Car>();
			Map<String, Object> alias3 = new HashMap<String, Object>();
			String hql3 = "from Car where 1=1 and isDiscount=1 and isDelete=0 ";
			if (!StringUtils.isEmpty(keyword)) {
				hql3 += " and carType like :carType";
				alias3.put("carType", "%" + keyword + "%");
			}
			hql3 += "  order by id desc";
			discountList = carService.getByHQL(hql3, alias3);
			if (discountList != null && discountList.size() > 0) {
				if (discountList.size() <= 8) {
					ActionContext.getContext()
							.put("discountList", discountList);
				} else {
					ActionContext.getContext().put("discountList",
							discountList.subList(0, 8));
				}
			} else {
				ActionContext.getContext().put("discountList", discountList);
			}

			// 个人租车列表
			List<Car> personList = new ArrayList<Car>();
			Map<String, Object> alias4 = new HashMap<String, Object>();
			String hql4 = "from Car where 1=1 and flag=2 and isDelete=0 ";
			if (!StringUtils.isEmpty(keyword)) {
				hql4 += " and carType like :carType";
				alias4.put("carType", "%" + keyword + "%");
			}
			hql4 += "  order by id desc";
			personList = carService.getByHQL(hql4, alias4);
			if (personList != null && personList.size() > 0) {
				if (personList.size() <= 8) {
					ActionContext.getContext().put("personList", personList);
				} else {
					ActionContext.getContext().put("personList",
							personList.subList(0, 8));
				}
			} else {
				ActionContext.getContext().put("personList", personList);
			}

			// 商家租车列表
			List<Car> businessList = new ArrayList<Car>();
			Map<String, Object> alias5 = new HashMap<String, Object>();
			String hql5 = "from Car where 1=1 and flag=1 and isDelete=0 ";
			if (!StringUtils.isEmpty(keyword)) {
				hql5 += " and carType like :carType";
				alias5.put("carType", "%" + keyword + "%");
			}
			hql3 += "  order by id desc";
			businessList = carService.getByHQL(hql5, alias5);
			if (businessList != null && businessList.size() > 0) {
				if (businessList.size() <= 8) {
					ActionContext.getContext()
							.put("businessList", businessList);
				} else {
					ActionContext.getContext().put("businessList",
							businessList.subList(0, 8));
				}
			} else {
				ActionContext.getContext().put("businessList", businessList);
			}

			ActionContext.getContext().put("a", keyword);

			return "userIndex";
		} else {
			return "manageIndex";
		}
	}
}
