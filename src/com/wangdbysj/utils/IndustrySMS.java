package com.wangdbysj.utils;

import java.net.URLEncoder;
import java.util.Random;

/**
 * 验证码通知短信接口
 * 
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 *
 */
public class IndustrySMS
{
	private static String operation = "/industrySMS/sendSMS";

    private static String accountSid = Config.ACCOUNT_SID;
    static String randNum = createRandNum();
    private static int aa = 1 ;
    private static String smsContent = "【闪电汽车】尊敬的用户，您的验证码为"+randNum;

	/**
	 * 验证码通知短信
	 */
	public static String execute(String phone)
	{
		String tmpSmsContent = null;
	    try{
	      tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){
	    }
	    String url = Config.BASE_URL + operation;
	    String body = "accountSid=" + accountSid + "&to=" + phone + "&smsContent=" + tmpSmsContent
	        + HttpUtil.createCommonParam();

	    // 提交请求
	    String result = HttpUtil.post(url, body);
		System.out.println("result:" + System.getProperty("line.separator") + result);

		if (result.indexOf("00141") == -1 ){
			return randNum;
		}
		return "1";
	}

	public static String createRandNum() {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 5; i++) {
			String s = random.nextInt(10) + "";
			sb.append(s);
		}
		return sb.toString();
	}
}
