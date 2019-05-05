package com.wangdbysj.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wangdbysj.model.Car;
import com.wangdbysj.model.CarCategory;
import com.wangdbysj.model.User;
import com.wangdbysj.service.CarCategoryService;
import com.wangdbysj.service.CarService;
import com.wangdbysj.utils.Pager;
import com.wangdbysj.utils.UUIDUtils;

/**
 * * Title: CarAction
* Description:  
* Company: 
* @author wangdong
* @date  2019-2-18 下午3:45:16
 */


@Controller("carAction")
@Scope("prototype")
public class CarAction extends ActionSupport implements ModelDriven<Car> {

	private static final long serialVersionUID = 1L;

	// ==========model==============
	private Car car;

	@Override
	public Car getModel() {
		if (car == null)
			car = new Car();
		return car;
	}

	// ==========model==============
	/**
	 * 依赖注入 start dao/service/===
	 */
	@Autowired
	private CarService carService;

	@Autowired
	private CarCategoryService carCategoryService;

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

	/**
	 * 前台根据传入的参数展示的汽车列表
	 * 
	 * @return
	 */
	public String uCarList() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Car where 1=1 and isDelete=0");
		if (car.getIsRecommend() == 1) {
			sb.append(" and isRecommend =1");
		}
		if (car.getIsDiscount() == 1) {
			sb.append(" and isDiscount =1");
		}
		if (car.getFlag() == 1) {
			sb.append(" and flag =1");
		}
		if (car.getFlag() == 2) {
			sb.append(" and flag =2");
		}
		sb = sb.append(" order by id desc");
		Pager<Car> pagers = carService.findByAlias(sb.toString(), null);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("car", car);
		return SUCCESS;
	}

	/**
	 * 前台根据carId查询单个汽车信息
	 * 
	 * @return
	 */
	public String uCar() {
		String hql = "from Car where 1=1 and id = :id and isDelete=0 ";
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("id", car.getId());
		List<Car> carList = carService.getByHQL(hql, alias);
		ActionContext.getContext().put("car", carList.get(0));
		return SUCCESS;
	}

	/**
	 * 前台根据carId进入确认租车页面
	 * 
	 * @return
	 */
	public String uRentCar() {
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if (existUser == null) {
			return "userLogin";
		} else {
			String hql = "from Car where 1=1 and id = :id and isDelete=0 ";
			Map<String, Object> alias = new HashMap<String, Object>();
			alias.put("id", car.getId());
			List<Car> carList = carService.getByHQL(hql, alias);
			ActionContext.getContext().put("car", carList.get(0));
			return SUCCESS;
		}
	}

	/**
	 * 列表分页查询
	 */
	public String car() {
		Map<String, Object> alias = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Car where 1=1 and isDelete=0");
		if (car.getCarType() != null && !"".equals(car.getCarType())) {
			sb.append(" and carType like :carType");
			alias.put("carType", "%" + car.getCarType() + "%");
		}
		if (car.getCarCategory() != null && !"".equals(car.getCarCategory())) {
			if (car.getCarCategory().getId() != 0
					&& !"".equals(car.getCarCategory().getId())) {
				sb.append(" and carCategory.id = :id");
				alias.put("id", car.getCarCategory().getId());
			}
		}
		if (car.getIsRecommend() != 0 && !"".equals(car.getIsRecommend())) {
			sb.append(" and isRecommend = :isRecommend");
			alias.put("isRecommend", car.getIsRecommend());
		}
		if (car.getIsDiscount() != 0 && !"".equals(car.getIsDiscount())) {
			sb.append(" and isDiscount = :isDiscount");
			alias.put("isDiscount", car.getIsDiscount());
		}
		sb = sb.append(" order by id desc");
		Pager<Car> pagers = carService.findByAlias(sb.toString(), alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("car", car);
		Map<String, Object> alias1 = new HashMap<String, Object>();
		StringBuffer sb1 = new StringBuffer();
		sb1 = sb1.append("from CarCategory where 1=1");
		// 进入修改页面时候首先查询出品牌分类的所有信息
		List<CarCategory> carCatList = carCategoryService.getByHQL(
				sb1.toString(), alias1);
		ActionContext.getContext().put("carCatList", carCatList);
		return SUCCESS;
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	public String add() {
		Map<String, Object> alias = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from CarCategory where 1=1");
		// 进入新增页面时候首先查询出品牌分类的所有信息
		List<CarCategory> carCatList = carCategoryService.getByHQL(
				sb.toString(), alias);
		ActionContext.getContext().put("carCatList", carCatList);
		return SUCCESS;
	}

	/**
	 * 跳转到添加页面前台
	 * 
	 * @return
	 */
	public String addMyCar() {
		// 判断该用户是否已经登录，如果没登录跳入到登录页面
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if (existUser == null) {
			return "userLogin";
		} else {
			Map<String, Object> alias = new HashMap<String, Object>();
			StringBuffer sb = new StringBuffer();
			sb = sb.append("from CarCategory where 1=1");
			// 进入新增页面时候首先查询出品牌分类的所有信息
			List<CarCategory> carCatList = carCategoryService.getByHQL(
					sb.toString(), alias);
			ActionContext.getContext().put("carCatList", carCatList);
			return SUCCESS;
		}
	}

	/**
	 * 执行添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exAdd() throws Exception {
		// 图片上传
		String root = "D:/my/upload";
		InputStream is = new FileInputStream(file);
		fileFileName = UUIDUtils.create() + fileFileName;
		OutputStream os = new FileOutputStream(new File(root, fileFileName));
		System.out.println("fileFileName: " + fileFileName);
		System.out.println("file: " + file.getName());
		System.out.println("file: " + file.getPath());
		byte[] buffer = new byte[500];
		int length = 0;
		while (-1 != (length = is.read(buffer, 0, buffer.length))) {
			os.write(buffer);
		}
		os.close();
		is.close();
		car.setFlag(1);// 1商家 2：个人
		car.setCarImage("\\upload\\" + fileFileName);
		carService.save(car);
		ActionContext.getContext().put("url", "/car_car.do");
		return "redirect";

	}

	/**
	 * 执行添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exMyAdd() throws Exception {
		// 图片上传
		String root = "D:/my/upload";
		InputStream is = new FileInputStream(file); //提供写入文件
		fileFileName = UUIDUtils.create() + fileFileName;
		OutputStream os = new FileOutputStream(new File(root, fileFileName)); //提供写出文件 位置  路径
		System.out.println("fileFileName: " + fileFileName);
		System.out.println("file: " + file.getName());
		System.out.println("file: " + file.getPath());
		byte[] buffer = new byte[500];
		int length = 0;
		while (-1 != (length = is.read(buffer, 0, buffer.length))) {
			os.write(buffer);
		}
		os.close();
		is.close();
		car.setFlag(2);// 1商家 2：个人
		car.setCarImage("\\upload\\" + fileFileName);
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		car.setUser(existUser);
		carService.save(car);
		ActionContext.getContext().put("url", "/login_index.do?role=1");
		return "redirect";

	}

	/**
	 * 查看详情页面
	 * 
	 * @return
	 */
	public String view() {
		Car n = carService.getById(car.getId());
		ActionContext.getContext().put("car", n);
		return SUCCESS;
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	public String update() {
		Car n = carService.getById(car.getId());
		ActionContext.getContext().put("car", n);
		Map<String, Object> alias = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from CarCategory where 1=1");
		// 进入修改页面时候首先查询出品牌分类的所有信息
		List<CarCategory> carCatList = carCategoryService.getByHQL(
				sb.toString(), alias);
		ActionContext.getContext().put("carCatList", carCatList);
		return SUCCESS;
	}

	/**
	 * 执行修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exUpdate() throws Exception {
		Car c = carService.getById(car.getId());
		if (file != null) {
			// 图片上传
			String root = "D:/my/upload";
			InputStream is = new FileInputStream(file);
			fileFileName = UUIDUtils.create() + fileFileName;
			OutputStream os = new FileOutputStream(new File(root, fileFileName));
			System.out.println("fileFileName: " + fileFileName);
			System.out.println("file: " + file.getName());
			System.out.println("file: " + file.getPath());
			byte[] buffer = new byte[500];
			int length = 0;
			while (-1 != (length = is.read(buffer, 0, buffer.length))) {
				os.write(buffer);
			}
			os.close();
			is.close();
			c.setCarImage("\\upload\\" + fileFileName);
		} else {
			c.setCarImage(c.getCarImage());
		}
		c.setCarType(car.getCarType());
		c.setCarNumber(car.getCarNumber());
		c.setCarCategory(car.getCarCategory());
		c.setCarOilType(car.getCarOilType());
		c.setDailyPrice(car.getDailyPrice());
		c.setDistance(car.getDistance());
		c.setIsDiscount(car.getIsDiscount());
		c.setIsRecommend(car.getIsRecommend());
		carService.update(c);
		ActionContext.getContext().put("url", "/car_car.do");
		return "redirect";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		Car n = carService.getById(car.getId());
		n.setIsDelete(1);
		carService.update(n);
		ActionContext.getContext().put("url", "/car_car.do");
		return "redirect";
	}

	// =============公=======共=======方=======法==========区=========end============//

	// -------------------------华丽分割线---------------------------------------------//

	// =============自=======定=======义=========方=======法==========区=========start============//

	// =============自=======定=======义=========方=======法==========区=========end============//

}
