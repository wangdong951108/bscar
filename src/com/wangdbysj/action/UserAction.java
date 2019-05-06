package com.wangdbysj.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wangdbysj.model.User;
import com.wangdbysj.service.UserService;
import com.wangdbysj.utils.IdcardAudit;
import com.wangdbysj.utils.IndustrySMS;
import com.wangdbysj.utils.Pager;

import net.sf.json.JSONObject;

/**
 * * Title: UserAction Description: Company:
 *
 * @author wangdong
 * @date 2019-2-18 下午3:46:22
 */

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;

	// ==========model==============
	private User user;

	@Override
	public User getModel() {
		if (user == null)
			user = new User();
		return user;
	}

	// ==========model==============
	/**
	 * 依赖注入 start dao/service/===
	 */
	@Autowired
	private UserService userService;

	// 依赖注入 end dao/service/===

	// -------------------------华丽分割线---------------------------------------------

	// ============自定义参数start=============

	// ============自定义参数end=============

	// -------------------------华丽分割线---------------------------------------------

	// ============文件上传start=======================================================
	private File file;
	// 提交过来的file的名字
	private String fileFileName;
	// 提交过来的file的MIME类型
	private String fileContentType;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	// ============文件上传end=========================================================

	// -------------------------华丽分割线---------------------------------------------//

	// =============公=======共=======方=======法==========区=========start============//

	// 注册
	public void register() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		String hql = "from User bean where bean.loginName =:loginName and bean.isDelete=0  ";
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("loginName", user.getLoginName());
		List<User> userList = userService.getByHQL(hql, alias);
		if (userList.size() != 0) {
			json.put("result", 2);// 该用户存在
		} else {
			user.setQuestion("No");
			user.setAnswer("No");
			userService.save(user);
			json.put("result", 1);// 注册成功
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	// 登录
	public void login() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		User u1 = userService.getByUserName(user);
		User u = userService.login(user);
		if (u1 == null) {
			json.put("result", 3); // 该用户不存在
		} else {
			if (u != null) {
				json.put("result", 1); // 登录成功
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.setAttribute("loginName", u.getLoginName());
				session.setAttribute("userId", u.getId());
				session.setAttribute("user", u);
			} else {
				json.put("result", 2);// 密码错误
			}
		}
		out = resp.getWriter();
		out.write(json.toString());

	}

	// 忘记密码
	public void changePassWord() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		User u1 = userService.getByUserName(user);
		if (u1 == null) {
			json.put("result", 2); // 该用户不存在
		} else {
			User u2 = userService.getByUserNameAndQuestion(user);
			if (u2 == null) {
				json.put("result", 3); // 该找回密码问题不正确
			} else {
				User u3 = userService.getByUserNameAndQuestionAndAnswer(user);
				if (u3 == null) {
					json.put("result", 4); // 该找回密码答案不正确
				} else {
					User u = userService.getById(u3.getId());
					u.setPassWord(user.getPassWord());
					userService.update(u);// 执行修改密码
					json.put("result", 1); // 密码修改成功
				}
			}

		}
		out = resp.getWriter();
		out.write(json.toString());

	}

	// 忘记密码 手机获取验证
	public void getCode() throws IOException {
		IndustrySMS aIndustrySMSa = new IndustrySMS();
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		// 验证手机是否存在
		User user1 = userService.getUserPhone(user);
		if (user1 == null) {
			json.put("data", 3); // 无此用户
		} else {
			try {

				String code = aIndustrySMSa.execute(user.getPhone());
				if (code.equals("1")) {// 验证失败
					json.put("data", 1);
				} else {// 验证成功
					session.setAttribute("code", code); // 将验证码保存在session中
					session.setAttribute("phone", user.getPhone());
					json.put("data", 2);
				}
			} catch (Exception e) {
				json.put("code", 0);
				e.printStackTrace();
			}
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	// 注册时 手机获取验证
	public void getCodeOnReg() throws IOException {
		IndustrySMS aIndustrySMSa = new IndustrySMS();
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		// 验证手机是否存在
		User user1 = userService.getUserPhone(user);
		if (user1 == null) {
			try {
				String code = aIndustrySMSa.execute(user.getPhone());
				if (code.equals("1")) {// 获取失败
					json.put("data", 1);
				} else {// 获取成功
					session.setAttribute("code", code); // 将验证码保存在session中
					session.setAttribute("phone", user.getPhone());
					json.put("data", 2);
				}
			} catch (Exception e) {
				json.put("code", 0);
				e.printStackTrace();
			}
		} else {
			json.put("data", 3); // 此手机号已经存在
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	/**
	 * 实名认证
	 * 
	 */
	public void dcardAudit() throws Exception {
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");

		String hql = "from User a where a.email=:email and a.isDelete = 0";
		HashMap<String, Object> alias = new HashMap<String, Object>();
		alias.put("email", user.getEmail());
		List<User> byHQL = userService.getByHQL(hql, alias);
		if (byHQL.size() == 1) {
			json.put("date", 3); // 此用户已注册
		} else {
			org.json.JSONObject dcardAudit = new IdcardAudit().dcardAudit(user.getEmail(), user.getRealName());
			if ((Integer) dcardAudit.get("code") == 0) {
				json.put("data", 1); // 验证成功
			} else {
				json.put("data", 2); // 验证失败
			}
		}
		out = response.getWriter();
		out.write(json.toString());
	}

	// 确认验证码的正确性
	public void changeCode12() throws Exception {
		JSONObject json = new JSONObject();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");
		String codeWeb = user.getPassWord();
		if (null == code || null == codeWeb) {
			json.put("data", 0);
		} else if (code.equals(codeWeb)) {
			json.put("data", 2);
		} else {
			json.put("data", 1);
		}
		out.write(json.toString());
	}

	public String forgetPassWord() {
		return SUCCESS;
	}

	/**
	 * 跳转到个人中心页面
	 *
	 * @return
	 */
	public String myInfo() {
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (existUser == null) {
			return "userLogin";
		}
		return SUCCESS;
	}

	/**
	 * 跳转到修改密码页面
	 *
	 * @return
	 */
	public String updatePassWord() {

		return SUCCESS;
	}

	/**
	 * 执行修改密码
	 *
	 * @return
	 */
	public void exUpdatePassWord() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (existUser != null) {
			User u = userService.getById(existUser.getId());
			u.setPassWord(user.getPassWord());
			userService.update(u);// 执行修改密码
			json.put("result", 1);// 密码修改成功
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	/**
	 * 执行修改密码
	 *
	 * @return
	 */
	public void phUpdatePassWord() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		try {
			User user1 = userService.getUserPhone(user);
			user1.setPassWord(user.getPassWord());
			userService.update(user1);
			json.put("result", 1);// 密码修改成功
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("result", 2);// 密码修改成功
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	/**
	 * 跳转到修改个人信息页面
	 *
	 * @return
	 */
	public String updateMyInfo() {

		return SUCCESS;
	}

	/**
	 * 执行修改个人信息
	 *
	 * @return
	 */
	public void exUpdateMyInfo() throws IOException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (existUser != null) {
			User u = userService.getById(existUser.getId());
			u.setPhone(user.getPhone());
			u.setEmail(user.getEmail());
			u.setQuestion(user.getQuestion());
			u.setAnswer(user.getAnswer());
			userService.update(u);// 执行修改密码
			// 修改个人信息后需要重新更新session中用户信息
			ActionContext ac = ActionContext.getContext();
			Map session1 = ac.getSession();
			session1.remove("loginName");
			session1.remove("userId");
			session1.remove("user");
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("loginName", u.getLoginName());
			session.setAttribute("userId", u.getId());
			session.setAttribute("user", u);
			json.put("result", 1);// 个人信息修改成功
		}
		out = resp.getWriter();
		out.write(json.toString());
	}

	/**
	 * 列表分页查询
	 */
	public String user() {
		Map<String, Object> alias = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from User where 1=1 and isDelete=0");
		if (user.getLoginName() != null && !"".equals(user.getLoginName())) {
			sb.append(" and loginName like :loginName");
			alias.put("loginName", "%" + user.getLoginName() + "%");
		}
		if (user.getPhone() != null && !user.getPhone().equals("")) {
			sb.append("and phone =:phone ");
			alias.put("phone", user.getPhone());
		}
		sb = sb.append(" order by id desc");
		Pager<User> pagers = userService.findByAlias(sb.toString(), alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("user", user);
		return SUCCESS;
	}

	/**
	 * 跳转到添加页面
	 *
	 * @return
	 */
	public String add() {
		return SUCCESS;
	}

	/**
	 * 执行添加
	 *
	 * @return
	 */
	public String exAdd() {
		userService.save(user);
		ActionContext.getContext().put("url", "/user_user.do");
		return "redirect";
	}

	/**
	 * 查看详情页面
	 *
	 * @return
	 */
	public String view() {
		User n = userService.getById(user.getId());
		ActionContext.getContext().put("user", n);
		return SUCCESS;
	}

	/**
	 * 跳转修改页面
	 *
	 * @return
	 */
	public String update() {
		User n = userService.getById(user.getId());
		ActionContext.getContext().put("user", n);
		return SUCCESS;
	}

	/**
	 * 执行修改
	 *
	 * @return
	 */
	public String exUpdate() {
		userService.update(user);
		ActionContext.getContext().put("url", "/user_user.do");
		return "redirect";
	}

	/**
	 * 删除
	 *
	 * @return
	 */
	public String delete() {
		User n = userService.getById(user.getId());
		n.setIsDelete(1);
		userService.update(n);
		ActionContext.getContext().put("url", "/user_user.do");
		return "redirect";
	}

	// =============公=======共=======方=======法==========区=========end============//

	// -------------------------华丽分割线---------------------------------------------//

	// =============自=======定=======义=========方=======法==========区=========start============//

	// =============自=======定=======义=========方=======法==========区=========end============//

}
